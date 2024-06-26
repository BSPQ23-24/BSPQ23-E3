# LODGIFY
## BSPQ23-E3
#### By Matas Gedziunas, Sara Hernandez, Unai Alonso, Jon Ander Gallastegui, Iker Ordoñez and Markel Retes

## References
### Repository
https://github.com/BSPQ23-24/BSPQ23-E3

### GitHub Pages
https://bspq23-24.github.io/BSPQ23-E3/jacoco/

https://bspq23-24.github.io/BSPQ23-E3/doxygen/

## Overview
This project is an application which consists of renting residences, providing different services for both travelers and hosts.

## Getting started

Access lodgify folder:
```bash
cd .\lodgify\
```

Build everything and enhance the DB classes:
```bash
mvn clean compile
```

### Setting Up the Database

Use the contents of the file _create-db.sql_ to create the database and grant privileges.

Check access to database:

```bash
mysql –u root -p
```

### Running the application
There are two ways for running the application.

#### 1.- Locally
Set connection URL in datanucleus.properties to:

javax.jdo.option.ConnectionURL=jdbc:mysql://localhost/lodgifyDB

Launch the server:
```bash
mvn jetty:run
```

Execute client sample:
Add new command window
```bash
cd .\lodgify\
```
```bash
mvn exec:java -Pclient
```
Access link after lodgify is run locally:

http://localhost:8080/

#### 2.- Docker

Set connection URL in datanucleus.properties to:

javax.jdo.option.ConnectionURL=jdbc:mysql://mysql/lodgifyDB

Create docker images and run application through docker compose:
```bash
docker compose up
```

Make sure you run the following commands to enable communication, chatting, between users.

```bash
cd .\lodgify\lodgify-frontend\lodgify-frontend\
```
```bash
node server.js
```

Access link after lodgify is run on docker:

http://localhost:8080/

### Testing
The process for running the test will be described in this section.

#### 1.- Unit tests
```bash
cd .\lodgify\
```

```bash
mvn clean compile
```

```bash
mvn jetty:run
```
Add new command window
```bash
cd .\lodgify\
```
```bash
mvn test
```

Thanks to this process we will now have the jacoco generated document, _index.html_ showing the coverage of our code.

Access to document:
```bash
cd .\lodgify\target\site\jacoco\
```

#### 2.- Performing and Integration tests
```bash
cd .\lodgify\
```

```bash
mvn clean compile
```

```bash
mvn jetty:run
```
Add new command window
```bash
cd .\lodgify\
```
```bash
mvn datanucleus:enhance
```
```bash
mvn datanucleus:schema-create 
```
```bash
mvn verify -Pintegration-tests
```
```bash
mvn verify -Pperformance-tests
```

Thanks to this process we will now have the junitperf generated document, _report.html_ showing the coverage of our code.

Access to document:
```bash
cd .\lodgify\target\junitperf\
```

### Doxygen documentation

This is the process that needs to be followed in order to generate the documentation of the code.

Access lodgify folder:
```bash
cd .\lodgify\
```

Create the report with the documentation:
```bash
mvn doxygen:report
```

Thanks to this process we will now have the document that doxygen generated, _index.html_ showing the documentation of our code.

Access to document:
```bash
cd .\lodgify\target\doxygen\html
```

Copy all the doxygen files into the docs folder located into the root directory of the project:
```bash
mvn validate
```
