FROM openjdk:8-jre-alpine
COPY ./build/libs/group-accountant-bot-*.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java"]
CMD ["-Dserver.port=8084","-Ddebug=true","-jar","app.jar"]