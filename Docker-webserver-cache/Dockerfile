FROM eclipse-temurin:17.0.3_7-jdk as java
WORKDIR /OWASP
RUN apt-get update && apt-get install wget

# Maven Dependency Check cache:
RUN wget https://github.com/stevespringett/nist-data-mirror/releases/download/nist-data-mirror-1.6.0/nist-data-mirror.jar
RUN java -jar nist-data-mirror.jar mirror

# Old GroupIds Alerter
RUN wget https://raw.githubusercontent.com/jonathanlermitage/oga-maven-plugin/master/uc/og-definitions.json --no-check-certificate -O mirror/og-definitions.json

FROM nginx:1.23.3-alpine
COPY --from=java /OWASP/mirror /usr/share/nginx/html