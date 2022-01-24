FROM maven:3.6.3-jdk-17

WORKDIR /Password-Manager
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run