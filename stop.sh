#!/bin/bash
# SUT stop script

docker-compose down
kill -9 $(cat app.pid) && rm -f app.pid