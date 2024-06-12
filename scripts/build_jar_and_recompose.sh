#!/bin/sh
docker compose stop
./gradlew bootJar
sleep 3
docker build -t esep/outdoora .
docker compose up
