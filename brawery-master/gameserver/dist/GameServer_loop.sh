#!/bin/sh

while :;
do
    [ -f logs/stdout.log ] && mv logs/stdout.log "logs/`date +%Y-%m-%d_%H-%M-%S`_stdout.log"
    java -Duser.timezone=Europe/Moscow -server -Dfile.encoding=UTF-8 -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector -Xmx6G -cp config:./lib/* org.mmocore.gameserver.GameServer > logs/stdout.log 2>&1
    [ $? -ne 2 ] && break
    sleep 10;
done