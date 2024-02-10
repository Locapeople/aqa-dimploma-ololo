#!/bin/bash
# SUT start script
# Usage: $0 [mysql|postgres]

DBTYPE=$1

case "$DBTYPE" in
  "mysql")
    DBENV="spring.datasource.url=jdbc:mysql://localhost:3306/app"
    ;;
  "postgres")
    DBENV="spring.datasource.url=jdbc:postgresql://localhost:5432/app"
    ;;
  *)
    echo "Invalid DB type, refer to README.md for details."
    exit -1
    ;;
esac

docker-compose up --build -d &> services.log
java -D${DBENV} -jar aqa-shop.jar &> app.log &
echo $! > app.pid