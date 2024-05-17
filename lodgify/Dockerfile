FROM maven:3.8.1-openjdk-11

RUN apt-get update && \
    apt-get install -y curl && \
    curl -sL https://deb.nodesource.com/setup_14.x | bash - && \
    apt-get install -y nodejs

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY lodgify-frontend/lodgify-frontend ./lodgify-frontend

RUN cd lodgify-frontend && npm install && npm run build

RUN mkdir -p src/main/webapp && cp -r lodgify-frontend/dist/* src/main/webapp/

RUN mvn clean compile

CMD ["mvn", "jetty:run"]