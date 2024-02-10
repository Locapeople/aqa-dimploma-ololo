#!/bin/bash
# SUT start script
# Usage: $0 [mysql|postgres]

DBTYPE=$1

case "$DBTYPE" in
  "mysql")
    DBENV="db.url=jdbc:mysql://localhost:3306/app"
    ;;
  "postgres")
    DBENV="db.url=jdbc:postgresql://localhost:5432/app"
    ;;
  *)
    echo "Invalid DB type, refer to README.md for details."
    exit -1
    ;;
esac

./gradlew -D${DBENV} clean test
