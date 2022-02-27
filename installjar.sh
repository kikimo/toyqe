#!/bin/bash

mvn install:install-file -Dfile=libs/evallib-1.0.jar -DgroupId=net.sf.jsqlparser -DartifactId=evallib -Dversion=1.0 -Dpackaging=jar
mvn install:install-file -Dfile=libs/jsqlparser-1.0.0.jar -DgroupId=net.sf.jsqlparser -DartifactId=jsqlparser -Dversion=1.0.0 -Dpackaging=jar

