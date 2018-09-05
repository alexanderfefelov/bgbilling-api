@echo off

git clone https://github.com/eed3si9n/scalaxb.git
pushd scalaxb
sed -i "s/1.5.2/local-SNAPSHOT/g" build.sbt
call sbt "project app" "+ publishLocal"
call sbt "project scalaxbPlugin" "+ publishLocal"
popd
