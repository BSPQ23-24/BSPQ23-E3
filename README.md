# BSPQ23-E3

BSPQ23-E3

Run the following command to build everything and enhance the DB classes:

      mvn clean compile

Make sure that the database was correctly configured. Use the contents of the file _create-db.sql_ to create the database and grant privileges. For example,

      mysql –u root -p

To launch the server run the command

    mvn jetty:run

Now, the client sample code can be executed in a new command window with

    mvn exec:java -Pclient

# Docker

To run docker compose:

      docker compose up

Access link after lodgify is run on docker:

      http://localhost:8080/

# Local

To run the web application locally with:

      mvn clean compile
      mvn jetty:run

You must change the following line in datanucleus.properties:

      javax.jdo.option.ConnectionURL=jdbc:mysql://mysql/lodgifyDB

Change to:

      javax.jdo.option.ConnectionURL=jdbc:mysql://localhost/lodgifyDB
