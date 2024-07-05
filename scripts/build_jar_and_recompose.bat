docker compose stop
call gradlew bootJar
timeout /t 1
docker build -t esep/outdoora .
docker compose up
