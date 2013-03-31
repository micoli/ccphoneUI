#!/bin/bash
if [[ $1 == "mvn" ]]
then
mvn clean package
fi
java -cp "target/deploy/*" org.micoli.phone.ccphoneUI.Main