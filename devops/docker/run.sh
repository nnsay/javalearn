#!/bin/bash

SCRIPT_DIR="$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )"
cd $SCRIPT_DIR

codes_path=$SCRIPT_DIR/../../codes

pushd $codes_path/components/stars
  ./gradlew build -x test
popd

pushd $codes_path/apps/point
  ./gradlew build -x test
popd

docker-compose up
