FROM openjdk:11

RUN addgroup --system zeal
RUN adduser --system --group zeal

USER zeal:zeal

ARG JAR_FILE=zeal-main/target/*.jar
COPY ${JAR_FILE} zeal.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/zeal.jar"]