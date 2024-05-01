#!/usr/bin/env bash

echo "deploy.sh 최초 진입" | sudo tee -a /home/ubuntu/startlog
sudo touch /home/ubuntu/testfile

REPOSITORY=/home/ubuntu/emotion_diary/server
cd $REPOSITORY

APP_NAME=emotion_diary
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 애플리케이션이 없습니다."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> Deploy - $JAR_PATH "
#nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &
nohup java -jar $JAR_PATH > /home/ubuntu/emotion_diary_log 2> /home/ubuntu/emotion_diary_log < /home/ubuntu/emotion_diary_log &