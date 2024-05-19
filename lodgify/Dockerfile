FROM maven:3.8.1-openjdk-11

WORKDIR /app

EXPOSE 8080

COPY pom.xml .
COPY src ./src
COPY lodgify-frontend ./lodgify-frontend/

RUN apt-get update && \
    apt-get install -y curl && \
    curl -sL https://deb.nodesource.com/setup_14.x | bash - && \
    apt-get install -y nodejs && \
    npm install -g npm@7 

RUN mkdir -p src/main/webapp && \
    cd lodgify-frontend/lodgify-frontend && \
    npm install && \
    npm run build && \
    cp -r dist/* /app/src/main/webapp/ && \
    cd ../..

RUN mvn clean compile
    
CMD ["mvn", "jetty:run"]