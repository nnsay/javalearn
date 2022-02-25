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

remote() {
  ssh root@master01 $*
}

retmoe docker-compose down
#remote docker rmi root_sentinel -f
remote docker system prune -f

remote docker-compose up -d
remote docker ps -a
remote  docker-compose logs -f
