FROM python:3.8.10-buster
RUN ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
RUN echo Asia/Seoul > /etc/timezone
WORKDIR /app 
ADD . .
RUN pip install --upgrade pip 
RUN pip install -r requirements_text.txt 
EXPOSE 8081
CMD python main.py