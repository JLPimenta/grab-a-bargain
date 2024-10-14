#!/bin/bash

set -a
source .env
set +a

docker-compose --profile prod up -d
