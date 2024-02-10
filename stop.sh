#!/bin/bash
# SUT stop script

[[ ! -f "app.pid" ]] && { echo "Cannot stop SUT: no pid file (is it still running?)"; exit -1; }
docker-compose down
kill -9 $(cat app.pid) && rm -f app.pid