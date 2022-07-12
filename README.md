# club-mileage-service
💰 클럽 마일리지 서비스 API

## 실행 방법
### ① repository clone 받기 
```
git clone https://github.com/soyeon207/club-mileage-service.git
cd club-mileage-service
```

### ② docker-compose 실행<br>
Docker Desktop 을 실행해주신 다음 루트 디렉토리에서 아래 명령어를 실행해주세요
```
docker-compose -p club-mileage up -d
```

### ③ DB 생성<br>
Host = localhost<br>
Port = 3306<br>
User = soyeon<br>
Password = 1234 로 mysql 에 접근 한 다음 아래 명령어를 실행해서 triple-db 를 만들어주세요.

```sql
CREATE DATABASE `mileage-db` 
DEFAULT CHARACTER SET utf8mb4 
```

### ④ 스프링 부트 실행하기 
```
./gradlew bootRun
```


## API 명세서 
### 포인트 적립 API 
```curl 
curl -X POST \
  http://localhost:8080/events \
  -H 'content-type: application/json' \
  -d '{
	"type": "REVIEW",
	"action": "ADD",
	"reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
	"content": "좋아요!",
	"attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-
851d-4a50-bb07-9cc15cbdc332"],
	"userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
	"placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}'
```

### 포인트 조회 API 
```curl
curl -X GET \
  http://localhost:8080/user/3ede0ef2-92b7-4817-a5f3-0c575361f745/review-points
```
