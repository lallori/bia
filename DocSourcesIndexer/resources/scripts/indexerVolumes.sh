#!/bin/bash
export JAVA_HOME=/usr/lib/jvm/java-1.6.0-openjdk
export JAVA_OPTS="-Xms128m -Xmx1024"

java -classpath './*:./libs/*' org.medici.docsources.indexer.DocSourcesIndexer volumes

