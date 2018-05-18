#!/bin/bash
set -e
base_dir=`pwd`
app_dir=${base_dir}/git/${app_name}
out_dir=${base_dir}/out

echo "PWD=${base_dir}"

cd $app_dir
./mvnw clean package

timestamp=`date -u +%Y%m%d%H%M`

jar_name=$(basename `ls $app_dir/target/*.jar`)
cp -a ${base_dir}/git/ci/docker/* ${out_dir}
cp $app_dir/target/*.jar ${out_dir}/fatjar.jar
