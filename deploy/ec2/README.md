# EC2 SSM CD 가이드

## 개요

- 배포 브랜치: `develop`
- 이미지 빌드/푸시: GitHub Actions
- 실행 서버: EC2
- 실행 방식: Docker 이미지 `pull + run`
- 원격 명령: AWS Systems Manager Run Command
- 프록시/TLS: Caddy

기존 `Nginx Blue/Green` 배포는 더 이상 기본 경로가 아니다. 현재 기준 배포 진입점은 `deploy/ec2/ssm_deploy.sh`다.

## 서버 1회 초기 설정

1. Docker 설치
2. Caddy 설치
3. EC2 IAM Role에 아래 권한 부여
   - `ssm:GetParameter`
   - `ssm:GetParameters`
   - `kms:Decrypt` (SecureString 사용 시)
4. 서버 디렉토리 준비

```bash
sudo mkdir -p /opt/ecogod/.runtime
```

5. Caddy upstream 구성

```caddy
api.ecogod.kr {
    reverse_proxy 127.0.0.1:8080
}
```

## GitHub Secrets

- `AWS_ACCESS_KEY_ID`
- `AWS_SECRET_ACCESS_KEY`
- `AWS_REGION`
- `EC2_INSTANCE_ID`
- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`
- `DOCKERHUB_REPO`

## SSM Parameter Store

필수:
- `/ecogod/server/db/host`
- `/ecogod/server/db/name`
- `/ecogod/server/db/username`
- `/ecogod/server/db/password`
- `/ecogod/server/jwt/secret`

선택:
- `/ecogod/server/mail/host`
- `/ecogod/server/mail/port`
- `/ecogod/server/mail/username`
- `/ecogod/server/mail/password`
- `/ecogod/server/mail/from`
- `/ecogod/server/mail/inquiry-to`

## 동작 방식

1. `develop` 브랜치에 push
2. Actions가 테스트 수행
3. Actions가 Jib로 Docker Hub에 이미지 push
4. Actions가 `ssm_deploy.sh`를 Base64로 인코딩해 EC2에 전송
5. EC2가 최신 이미지를 `pull`
6. 기존 `ecogod-api` 컨테이너 교체 실행
7. `http://127.0.0.1:8080/actuator/health` 통과 시 성공
8. 실패 시 직전 성공 이미지로 롤백 시도
