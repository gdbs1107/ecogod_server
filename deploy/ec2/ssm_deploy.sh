#!/usr/bin/env bash
set -euo pipefail

APP_NAME="${APP_NAME:-ecogod}"
AWS_REGION="${AWS_REGION:?AWS_REGION is required}"
DOCKER_REPO="${DOCKER_REPO:?DOCKER_REPO is required}"
IMAGE_TAG="${IMAGE_TAG:?IMAGE_TAG is required}"
IMAGE="${DOCKER_REPO}:${IMAGE_TAG}"
DOCKER_USERNAME="${DOCKER_USERNAME:-}"
DOCKER_PASSWORD="${DOCKER_PASSWORD:-}"

LOCK_FILE="${LOCK_FILE:-/var/lock/${APP_NAME}-deploy.lock}"
RUNTIME_DIR="${RUNTIME_DIR:-/opt/${APP_NAME}/.runtime}"
CURRENT_IMAGE_FILE="${CURRENT_IMAGE_FILE:-${RUNTIME_DIR}/current_image}"
LAST_SUCCESS_IMAGE_FILE="${LAST_SUCCESS_IMAGE_FILE:-${RUNTIME_DIR}/last_success_image}"

HEALTH_PATH="${HEALTH_PATH:-/actuator/health}"
HEALTH_URL="${HEALTH_URL:-http://127.0.0.1:8080${HEALTH_PATH}}"
HEALTH_TIMEOUT_SECONDS="${HEALTH_TIMEOUT_SECONDS:-180}"
HEALTH_REQUEST_TIMEOUT_SECONDS="${HEALTH_REQUEST_TIMEOUT_SECONDS:-5}"
HEALTH_CONNECT_TIMEOUT_SECONDS="${HEALTH_CONNECT_TIMEOUT_SECONDS:-3}"
HEALTH_RETRY_SLEEP_SECONDS="${HEALTH_RETRY_SLEEP_SECONDS:-5}"

SSM_PREFIX="${SSM_PREFIX:-/ecogod/server}"
MAIL_HOST_PARAM="${MAIL_HOST_PARAM:-${SSM_PREFIX}/mail/host}"
MAIL_PORT_PARAM="${MAIL_PORT_PARAM:-${SSM_PREFIX}/mail/port}"
MAIL_USERNAME_PARAM="${MAIL_USERNAME_PARAM:-${SSM_PREFIX}/mail/username}"
MAIL_PASSWORD_PARAM="${MAIL_PASSWORD_PARAM:-${SSM_PREFIX}/mail/password}"
MAIL_FROM_PARAM="${MAIL_FROM_PARAM:-${SSM_PREFIX}/mail/from}"
MAIL_INQUIRY_TO_PARAM="${MAIL_INQUIRY_TO_PARAM:-${SSM_PREFIX}/mail/inquiry-to}"
DB_HOST_PARAM="${DB_HOST_PARAM:-${SSM_PREFIX}/db/host}"
DB_NAME_PARAM="${DB_NAME_PARAM:-${SSM_PREFIX}/db/name}"
DB_USERNAME_PARAM="${DB_USERNAME_PARAM:-${SSM_PREFIX}/db/username}"
DB_PASSWORD_PARAM="${DB_PASSWORD_PARAM:-${SSM_PREFIX}/db/password}"
JWT_SECRET_PARAM="${JWT_SECRET_PARAM:-${SSM_PREFIX}/jwt/secret}"

mkdir -p "$(dirname "${LOCK_FILE}")" "${RUNTIME_DIR}"
exec 200>"${LOCK_FILE}"
flock -n 200 || { echo "[ERROR] Another deployment is running"; exit 1; }

ensure_dependencies() {
  if ! command -v aws >/dev/null 2>&1; then
    echo "[INFO] Installing awscli"
    export DEBIAN_FRONTEND=noninteractive
    apt-get update
    apt-get install -y curl unzip
    curl -fsSL "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o /tmp/awscliv2.zip
    rm -rf /tmp/aws
    unzip -q /tmp/awscliv2.zip -d /tmp
    /tmp/aws/install --bin-dir /usr/local/bin --install-dir /usr/local/aws-cli --update
  fi
}

get_ssm_parameter() {
  local name="$1"
  local decrypt="${2:-false}"

  aws ssm get-parameter \
    --region "${AWS_REGION}" \
    --name "${name}" \
    $( [[ "${decrypt}" == "true" ]] && printf '%s' '--with-decryption' ) \
    --query 'Parameter.Value' \
    --output text
}

docker_login() {
  if [[ -n "${DOCKER_USERNAME}" && -n "${DOCKER_PASSWORD}" ]]; then
    echo "[INFO] Docker registry login"
    echo "${DOCKER_PASSWORD}" | docker login --username "${DOCKER_USERNAME}" --password-stdin >/dev/null
  fi
}

wait_for_health() {
  local elapsed=0
  local attempt=0

  while true; do
    attempt=$((attempt + 1))

    if curl -fsS \
      --connect-timeout "${HEALTH_CONNECT_TIMEOUT_SECONDS}" \
      --max-time "${HEALTH_REQUEST_TIMEOUT_SECONDS}" \
      "${HEALTH_URL}" >/dev/null 2>&1; then
      return 0
    fi

    sleep "${HEALTH_RETRY_SLEEP_SECONDS}"
    elapsed=$((elapsed + HEALTH_REQUEST_TIMEOUT_SECONDS + HEALTH_RETRY_SLEEP_SECONDS))

    if (( elapsed >= HEALTH_TIMEOUT_SECONDS )); then
      echo "[ERROR] Health check timeout (${HEALTH_TIMEOUT_SECONDS}s, attempts=${attempt})"
      return 1
    fi
  done
}

rollback() {
  if [[ ! -f "${LAST_SUCCESS_IMAGE_FILE}" ]]; then
    echo "[WARN] No rollback image recorded"
    return 1
  fi

  local previous_image
  previous_image="$(cat "${LAST_SUCCESS_IMAGE_FILE}")"

  if [[ -z "${previous_image}" ]]; then
    echo "[WARN] Rollback image file is empty"
    return 1
  fi

  echo "[WARN] Rolling back to ${previous_image}"
  docker rm -f "${APP_NAME}-api" >/dev/null 2>&1 || true

  docker run -d \
    --name "${APP_NAME}-api" \
    --restart unless-stopped \
    -p "127.0.0.1:8080:8080" \
    -e SPRING_PROFILES_ACTIVE="local" \
    -e LOCAL_DB_URL="${LOCAL_DB_URL}" \
    -e LOCAL_DB_USERNAME="${LOCAL_DB_USERNAME}" \
    -e LOCAL_DB_PASSWORD="${LOCAL_DB_PASSWORD}" \
    -e LOCAL_JPA_DDL_AUTO="${LOCAL_JPA_DDL_AUTO}" \
    -e LOCAL_MAIL_HOST="${LOCAL_MAIL_HOST}" \
    -e LOCAL_MAIL_PORT="${LOCAL_MAIL_PORT}" \
    -e LOCAL_MAIL_USERNAME="${LOCAL_MAIL_USERNAME}" \
    -e LOCAL_MAIL_PASSWORD="${LOCAL_MAIL_PASSWORD}" \
    -e LOCAL_MAIL_FROM="${LOCAL_MAIL_FROM}" \
    -e LOCAL_INQUIRY_ALERT_TO="${LOCAL_INQUIRY_ALERT_TO}" \
    -e APP_TIME_ZONE="Asia/Seoul" \
    -e CORS_ALLOWED_ORIGINS="https://www.ecogod.kr,http://localhost:5173" \
    -e JWT_SECRET="${JWT_SECRET}" \
    "${previous_image}" >/dev/null

  wait_for_health
}

ensure_dependencies

LOCAL_DB_HOST="$(get_ssm_parameter "${DB_HOST_PARAM}")"
LOCAL_DB_NAME="$(get_ssm_parameter "${DB_NAME_PARAM}")"
LOCAL_DB_USERNAME="$(get_ssm_parameter "${DB_USERNAME_PARAM}")"
LOCAL_DB_PASSWORD="$(get_ssm_parameter "${DB_PASSWORD_PARAM}" true)"
JWT_SECRET="$(get_ssm_parameter "${JWT_SECRET_PARAM}" true)"

LOCAL_MAIL_HOST="$(get_ssm_parameter "${MAIL_HOST_PARAM}" 2>/dev/null || printf '%s' 'localhost')"
LOCAL_MAIL_PORT="$(get_ssm_parameter "${MAIL_PORT_PARAM}" 2>/dev/null || printf '%s' '1025')"
LOCAL_MAIL_USERNAME="$(get_ssm_parameter "${MAIL_USERNAME_PARAM}" 2>/dev/null || printf '%s' '')"
LOCAL_MAIL_PASSWORD="$(get_ssm_parameter "${MAIL_PASSWORD_PARAM}" true 2>/dev/null || printf '%s' '')"
LOCAL_MAIL_FROM="$(get_ssm_parameter "${MAIL_FROM_PARAM}" 2>/dev/null || printf '%s' 'no-reply@ecogod.kr')"
LOCAL_INQUIRY_ALERT_TO="$(get_ssm_parameter "${MAIL_INQUIRY_TO_PARAM}" 2>/dev/null || printf '%s' 'admin@ecogod.kr')"
LOCAL_JPA_DDL_AUTO="${LOCAL_JPA_DDL_AUTO:-update}"
LOCAL_DB_URL="jdbc:mysql://${LOCAL_DB_HOST}:3306/${LOCAL_DB_NAME}?serverTimezone=Asia/Seoul&characterEncoding=UTF-8"

docker_login

echo "[INFO] Pull image: ${IMAGE}"
docker pull "${IMAGE}"

echo "[INFO] Stop legacy jar process if exists"
pkill -f 'ecogod-api-.*\.jar' >/dev/null 2>&1 || true

CURRENT_IMAGE=""
if docker ps -a --format '{{.Names}}' | grep -qx "${APP_NAME}-api"; then
  CURRENT_IMAGE="$(docker inspect --format='{{.Config.Image}}' "${APP_NAME}-api" 2>/dev/null || true)"
fi

if [[ -n "${CURRENT_IMAGE}" ]]; then
  echo "${CURRENT_IMAGE}" > "${LAST_SUCCESS_IMAGE_FILE}"
fi

echo "[INFO] Start ${APP_NAME}-api from ${IMAGE}"
docker rm -f "${APP_NAME}-api" >/dev/null 2>&1 || true
docker run -d \
  --name "${APP_NAME}-api" \
  --restart unless-stopped \
  -p "127.0.0.1:8080:8080" \
  -e SPRING_PROFILES_ACTIVE="local" \
  -e LOCAL_DB_URL="${LOCAL_DB_URL}" \
  -e LOCAL_DB_USERNAME="${LOCAL_DB_USERNAME}" \
  -e LOCAL_DB_PASSWORD="${LOCAL_DB_PASSWORD}" \
  -e LOCAL_JPA_DDL_AUTO="${LOCAL_JPA_DDL_AUTO}" \
  -e LOCAL_MAIL_HOST="${LOCAL_MAIL_HOST}" \
  -e LOCAL_MAIL_PORT="${LOCAL_MAIL_PORT}" \
  -e LOCAL_MAIL_USERNAME="${LOCAL_MAIL_USERNAME}" \
  -e LOCAL_MAIL_PASSWORD="${LOCAL_MAIL_PASSWORD}" \
  -e LOCAL_MAIL_FROM="${LOCAL_MAIL_FROM}" \
  -e LOCAL_INQUIRY_ALERT_TO="${LOCAL_INQUIRY_ALERT_TO}" \
  -e APP_TIME_ZONE="Asia/Seoul" \
  -e CORS_ALLOWED_ORIGINS="https://www.ecogod.kr,http://localhost:5173" \
  -e JWT_SECRET="${JWT_SECRET}" \
  "${IMAGE}" >/dev/null

if ! wait_for_health; then
  docker logs "${APP_NAME}-api" --tail 200 || true
  docker rm -f "${APP_NAME}-api" >/dev/null 2>&1 || true
  rollback || true
  exit 1
fi

echo "${IMAGE}" > "${CURRENT_IMAGE_FILE}"
echo "${IMAGE}" > "${LAST_SUCCESS_IMAGE_FILE}"
docker image prune -f >/dev/null 2>&1 || true

echo "[INFO] Deploy success: image=${IMAGE}"
