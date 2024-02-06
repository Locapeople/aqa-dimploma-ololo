#!/bin/bash
# SUT start script

docker-compose up --build -d &> services.log
java -jar aqa-shop.jar &> app.log &
echo $! > app.pid