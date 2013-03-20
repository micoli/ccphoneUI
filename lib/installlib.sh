#!/bin/sh
DIR="$( cd "$( dirname "$0" )" && pwd )"
cd $DIR/
pwd
mvn install:install-file -Dfile=JNativeHook-1.1.4.jar -DgroupId=org.jnativehook -DartifactId=jnativehook -Dversion=1.1.4 -Dpackaging=jar
