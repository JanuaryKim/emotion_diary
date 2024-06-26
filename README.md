# 📘 감정 일기장
오늘 하루의 감정과 일과를 기록하는 일기 앱

## 💾 프로젝트 소개
서버와 연동하여 어디서든 내 일기를 기록, 조회 할 수 있는 버전과
서버와 연동하지 않고 개인기록으로 기록, 조회 할 수 있는 버전의 일기 앱.
하루의 일과를 감정, 사진, 텍스트로 저장합니다.

## 🔨 사용 된 스택
### 백엔드
* **프레임워크** : Spring Boot
* **데이터베이스** : AWS RDS, MySQL
* **서버** : AWS EC2, AWS S3
* **CI/CD** : Git Hub Action

### 프론트엔드
* **프레임워크** : React
* **데이터베이스** : IndexedDB
* **배포** : Firebase

## 🕹️ 주요 기능  
### 일기 등록 
* 입력한 날짜, 감정, 사진, 텍스트를 백엔드 서버로 전송하여 저장. 
* 서버와의 비연동시 브라우저의 IndexedDB에 저장 가능. 

### 일기 수정
* 저장한 일기의 날짜, 감정, 사진, 텍스트 수정 가능

### 일기 삭제
* 저장한 일기의 삭제 

### 일기 조회
* 일기 월별 조회 가능. 
* 감정별 조회 가능 

## 📑 API 명세서
[링크](https://documenter.getpostman.com/view/24349489/2sA3JM8gyD)

## 🔩 배포
[링크](https://emotion-diary-f22ed.web.app)
