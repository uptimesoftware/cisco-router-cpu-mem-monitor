#!/bin/bash
JAVA_HOME=../jre/bin/

$JAVA_HOME/java -jar ciscorouter.jar $UPTIME_HOSTNAME $UPTIME_COMMUNITY
