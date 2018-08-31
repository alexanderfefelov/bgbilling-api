@echo off

git clone https://github.com/eed3si9n/scalaxb.git
pushd scalaxb
sed -i "s/1.5.2/local-SNAPSHOT/" build.sbt
sbt "project app" "+ publishLocal"
sbt "project ascalaxbPlugin" "+ publishLocal"
popd
