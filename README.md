# club-mileage-service
๐ฐ ํด๋ฝ ๋ง์ผ๋ฆฌ์ง ์๋น์ค API

## ์คํ ๋ฐฉ๋ฒ
### โ  repository clone ๋ฐ๊ธฐ 
```
git clone https://github.com/soyeon207/club-mileage-service.git
cd club-mileage-service
```

### โก docker-compose ์คํ<br>
Docker Desktop ์ ์คํํด์ฃผ์  ๋ค์ ๋ฃจํธ ๋๋ ํ ๋ฆฌ์์ ์๋ ๋ช๋ น์ด๋ฅผ ์คํํด์ฃผ์ธ์
```
docker-compose -p club-mileage up -d
```

### โข DB ์์ฑ<br>
Host = localhost<br>
Port = 3306<br>
User = soyeon<br>
Password = 1234 ๋ก mysql ์ ์ ๊ทผ ํ ๋ค์ ์๋ ๋ช๋ น์ด๋ฅผ ์คํํด์ triple-db ๋ฅผ ๋ง๋ค์ด์ฃผ์ธ์.

```sql
CREATE DATABASE `mileage-db` 
DEFAULT CHARACTER SET utf8mb4 
```

### โฃ ์คํ๋ง ๋ถํธ ์คํํ๊ธฐ 
```
./gradlew bootRun
```


## API ๋ช์ธ์ 
### ํฌ์ธํธ ์ ๋ฆฝ API 
```curl 
curl -X POST \
  http://localhost:8080/events \
  -H 'content-type: application/json' \
  -d '{
	"type": "REVIEW",
	"action": "ADD",
	"reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
	"content": "์ข์์!",
	"attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-
851d-4a50-bb07-9cc15cbdc332"],
	"userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
	"placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}'
```

### ํฌ์ธํธ ์กฐํ API 
```curl
curl -X GET \
  http://localhost:8080/user/3ede0ef2-92b7-4817-a5f3-0c575361f745/review-points
```
