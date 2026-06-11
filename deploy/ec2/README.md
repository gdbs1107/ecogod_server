# EC2 Blue/Green 배포 가이드

## 1) 서버 1회 초기 설정

1. Nginx 설치
```bash
sudo apt-get update
sudo apt-get install -y nginx
```

2. 업스트림 파일 생성 (`/etc/nginx/conf.d/ecogod-upstream.conf`)
```nginx
upstream ecogod_backend {
    server 127.0.0.1:8080;
}
```

3. 서버 블록 예시 (`/etc/nginx/sites-available/ecogod`)
```nginx
server {
    listen 80;
    server_name _;

    location / {
        proxy_pass http://ecogod_backend;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

4. Nginx 적용
```bash
sudo ln -sf /etc/nginx/sites-available/ecogod /etc/nginx/sites-enabled/ecogod
sudo nginx -t
sudo systemctl restart nginx
```

## 2) GitHub Secrets

- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`
- `DOCKERHUB_REPO` (예: `username/ecogod`, Docker Hub 저장소)
- `EC2_HOST`
- `EC2_USER` (예: `ubuntu`)
- `EC2_SSH_KEY`
- `APPLICATION_DEV` (실제 값이 들어간 `application-dev.yml` 전체 텍스트)

`APPLICATION_DEV` 예시는 아래 형식이다.
```yaml
spring:
  config:
    activate:
      on-profile: dev
  datasource:
    url: jdbc:mysql://...
    username: ...
    password: ...
```

## 3) 동작 방식

- `main` 브랜치 push 시 CD 실행
- GitHub Actions가 `APPLICATION_DEV` 시크릿으로 `src/main/resources/application-dev.yml` 생성
- Docker Hub로 이미지 push (ECR 미사용)
- Docker 이미지를 `:<commit_sha>`와 `:main-latest`로 push
- EC2에서 비활성 색상 컨테이너를 먼저 실행 후 `/actuator/health` 헬스체크
- 통과 시 Nginx upstream을 새 포트로 전환 후 reload
- 이전 색상 컨테이너 종료

포트 기본값:
- blue: `8080`
- green: `8081`
