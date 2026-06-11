# CD 마이그레이션 메모

## 변경 이유

- `SSH/SCP`는 네트워크 의존성이 크다.
- 현재 서버 프록시는 `Nginx`가 아니라 `Caddy`다.
- 런타임 시크릿은 파일보다 `SSM Parameter Store`가 더 일관적이다.
- 서버에서는 이미지 빌드가 아니라 `이미지 실행`만 해야 한다.

## 이전 구조

- GitHub Actions
  - `main` push 트리거
  - `application-dev.yml` 생성
  - Docker Hub push
  - `scp-action`으로 배포 스크립트 전송
  - `ssh-action`으로 EC2 접속
- EC2
  - Nginx upstream 전환 기반 blue/green

## 현재 구조

- GitHub Actions
  - `develop` push 트리거
  - `test`
  - `:ecogad-api:jib`
  - `aws ssm send-command`
- EC2
  - `Caddy -> 127.0.0.1:8080`
  - `ecogod-api` 단일 컨테이너 교체
  - SSM Parameter Store에서 런타임 값 조회

## 후속 개선 후보

1. Docker Hub 대신 GHCR 전환
2. OIDC 기반 AWS 인증 전환
3. `docker compose` 도입
4. 컨테이너 로그를 CloudWatch Logs로 수집
5. 배포 성공 후 Swagger/주요 API smoke test 추가
