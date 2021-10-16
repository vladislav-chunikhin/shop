#!/usr/bin/env bash

cd "$(dirname "$0")"
. ./vars.sh

sudo mkdir -p $CURRENT_DIR/elasticsearch/data
sudo chmod 777 -R $CURRENT_DIR/elasticsearch/data

docker-compose -f $CURRENT_DIR/docker-compose-local.yml up -d