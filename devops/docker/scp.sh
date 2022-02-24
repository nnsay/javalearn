#!/bin/bash

items=(
  sentinel.Dockerfile
  docker-compose.yaml
  base
  mysql
)

for item in ${items[@]}; do
  if [[ -f $item ]]; then
    scp $item root@master01:/root
  else
    scp -r $item root@master01:/root
  fi
done

ssh root@master01 docker-compose up -d

ssh root@master01 docker ps -a

ssh root@master01

mbind: Operation not permitted
