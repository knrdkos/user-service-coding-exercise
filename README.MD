
## QUICKSTART
1. start the db `docker-compose up -d`
1. create db `usermicroservice` 
1. init db schema `./mvnw -Dflyway.configFiles=flyway.conf flyway:migrate`
1. run program `./mvnw spring-boot:run`

---

## API
#### start registration process ( email is not sent currently ) :
```
curl --location --request POST 'localhost:8080/api/v1/register' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "name" : "Konrad Kostrzewa",
   "email" : "knrd.kostrzewa@gmail.com",
   "password" : "qwerty12"
   }'
```


#### login:
```
curl --location --request POST 'localhost:8080/api/v1/login' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "email" : "knrd.kostrzewa@gmail.com",
   "password" : "qwerty12"
   }' | jq .jwt
```

#### get logged user info ( paste in token from login req ):
```
curl --location --request GET 'localhost:8080/api/v1/user' \
   --header 'Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrbnJkLmtvc3RyemV3YUBnbWFpbC5jb20iLCJpYXQiOjE2NjU1MjI1MDQsImV4cCI6MTY2NTU2NTcwNH0.7bq7c2Zcoh-dRvOhlxAWXNlhX2Mg12hk6T3tmzkJsMQ'
``` 

#### update user ( paste in token from login req )
```
curl --location --request PUT 'localhost:8080/api/v1/user' \
--header 'Content-Type: application/json' \
--header 'Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrbnJkLmtvc3RyemV3YUBnbWFpbC5jb20iLCJpYXQiOjE2NjU1MjI1MDQsImV4cCI6MTY2NTU2NTcwNH0.7bq7c2Zcoh-dRvOhlxAWXNlhX2Mg12hk6T3tmzkJsMQ' \
--data-raw '{
    "name" : "Panda ",
    "email" : "knrd.kostrzewa@gmail.com"
}'
```


#### get user by id ( paste in token from login req ):
```
curl --location --request GET 'localhost:8080/api/v1/admin/users/1' \
--header 'Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrbnJkLmtvc3RyemV3YUBnbWFpbC5jb20iLCJpYXQiOjE2NjU1MjI1MDQsImV4cCI6MTY2NTU2NTcwNH0.7bq7c2Zcoh-dRvOhlxAWXNlhX2Mg12hk6T3tmzkJsMQ'
```

#### delete user ( paste in token from login req ):
```
curl --location --request DELETE 'localhost:8080/api/v1/admin/users/2' \
--header 'Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrbnJkLmtvc3RyemV3YUBnbWFpbC5jb20iLCJpYXQiOjE2NjU1MjI1MDQsImV4cCI6MTY2NTU2NTcwNH0.7bq7c2Zcoh-dRvOhlxAWXNlhX2Mg12hk6T3tmzkJsMQ'
```

#### get all users ( paste in token from login req ):
```
curl --location --request GET 'localhost:8080/api/v1/admin/users/' \
--header 'Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrbnJkLmtvc3RyemV3YUBnbWFpbC5jb20iLCJpYXQiOjE2NjU2MTAyNTcsImV4cCI6MTY2NTY1MzQ1N30.ptQd4fQMJJ_PlCmYXlNpLMF_8Z-wPllG0U2kkAgSAz8'
```

--- 
COMMANDS:
#### all tests `./mvnw test`

#### integration tests `./mvnw test -Pintegration-test`

#### unit tests `./mvnw test -Punit-test`

#### mutation coverage test report `./mvnw test-compile org.pitest:pitest-maven:mutationCoverage`

#### run app command `./mvnw spring-boot:run`

#### flyway db migration `./mvnw -Dflyway.configFiles=flyway.conf flyway:migrate`

--
MISC:
Find mutation testing result in `./target/pit-reports/{date}/index.html`
