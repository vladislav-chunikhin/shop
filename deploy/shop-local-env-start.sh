#!/usr/bin/env bash

cd "$(dirname "$0")"
. ./vars.sh

docker-compose -f $CURRENT_DIR/docker-compose-local.yml up -d