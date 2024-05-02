#!/usr/bin/env bash

echo "deploy.sh 최초 진입 성공"
REPOSITORY=/home/ubuntu/emotion_diary/server
cd $REPOSITORY

APP_NAME=emotion_diary
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME
ORIGIN_PROPERTIES_PATH=$REPOSITORY/src/main/resources/application.properties
PROPERTIES_FILE_NAME=application-dep.properties

if [[ -f $REPOSITORY/build/libs/application-dep.properties ]]; then
  echo "> 기존 파일 삭제"
  sudo rm $REPOSITORY/build/libs/application-dep.properties
fi

echo "> 배포버전 속성파일 생성"
#새로 쓰일 속성파일 복사본 생성
cp $ORIGIN_PROPERTIES_PATH $REPOSITORY/build/libs/$PROPERTIES_FILE_NAME
filename=$REPOSITORY/build/libs/$PROPERTIES_FILE_NAME
# 파일의 각 줄을 반복합니다.
for line in $(cat "$filename"); do
  # 각 줄을 처리합니다.
  echo "$line" + " : 성공" > /home/ubuntu/forTest
done


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