# 개요

##  프로젝트 개요
<br>
우리는 사진은 그렇게 많이 찍으면서 왜 일기는 쓰지 않을까

<br>

## 프로젝트 사용 도구	
<br>
이슈 관리 : 

- JIRA

형상 관리 : 
- Gitlab


커뮤니케이션 : 
- Notion 
- Mattermost

디자인 : 
- Figma

UCC : 
- VLLO
- MOBABI 

CI/CD : 
- Jenkins 
- Docker 
- Gitlab

<br>

## 개발환경	


**Fe**  
-	Vue.js (2.), 
-	Vuetify, (2.)
-	Vuex, (2.0)
-	Node.js (18.14.2)
-	Npm.(latest)

**Be**
-	Java (11)
-	SpringBoot (2.7.9)
-	Swagger (2.9.2)
-	JPA
-	Python (3.8.10)
-	Anaconda (23.1.0)
-	FastAPI (0.92.0)
-	SQLAlchemy (1.4.46)

**Infra**
-	Docker
-	Nginx
-	Jenkins
-	GitLab

**Server**
-	EC2
-	TomCat
-	MariaDB
-	Colab
-	GPU server

**AI** 
-	Tensorflow (2.10.0)
-	PyTorch (1.13.1)
-	Numpy(1.22.4)

**외부 서비스**
-	Google Speech Recognition (3.10.0)
-   Kakao OAuth 
-   Google Cloud Storage 
<br>



# 빌드	
## GIT에 없는 환경변수 파일
<br>

### Vue - .env

```
VUE_APP_KAKAO_APP_KEY=
VUE_APP_KAKAO_REDIRECT_URI=
VUE_APP_KAKAO_LOGOUT_REDIRECT_URI
VUE_APP_API_BASE_URL =
```

### SpringBoot - application-db.yml

```
spring:
    jpa:
        hibernate:
            ddl-auto: 
        properties:
            hibernate:
                format_sql: 
                show_sql: 
        defer-datasource-initialization: 
    datasource:
        driverClassName: 
        url: 
        username: 
        password: 
```

### springBoot - application-oauth.yml

```
spring:
  security:
    oauth2:
      client:
        registration:
          kakao:
            client_id: 
            client_secret: 
            redirect_uri: 
            scope: 
            client_name: kakao
            authorization-grant-type: 
            client-authentication-method: POST
            redirect-uri: 
        provider:
          kakao:
            authorization-uri: 
            token-uri: 
            user-info-uri: 
            user-name-attribute: 
```
## 빌드하기	

**Vue** 

```
npm i 
npm run build  
```

**Java**

```
greadlew clean build
```

**Python**

```
# google cloud 에서 모델 파일을 다운
$ curl -c ./cookie -s -L "https://drive.google.com/uc?export=download&id={GoogleCloudFileId}" > /dev/null

#KoBERT 가중치
$ curl -Lb ./cookie "https://drive.google.com/uc?export=download&confirm=`awk '/download/ {print $NF}' ./cookie`&id=<GoogleCloudFileIdText>" -o /backend/python/experiments/emotion_text/<modelFileName>.pt

#음성기반 분류모델 가중치 
$ curl -Lb ./cookie "https://drive.google.com/uc?export=download&confirm=`awk '/download/ {print $NF}' ./cookie`&id=<GoogleCloudFileIdVoice>" -o /backend/python/ser/<modelFileName>.h5

pip install -r requirements_voice_text.txt

```

## 실행

**Vue**
```
npm run serve
```

**SpringBoot**
```
java -jar {filename}.jar
```

**FastAPI**
```
cd /backend/python/
python main.py 
```


