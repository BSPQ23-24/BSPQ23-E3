# BSPQ23-E3
BSPQ23-E3

This example relies on the DataNucleus Maven plugin. Check the database configuration in the *datanucleus.properties* file and the JDBC driver dependency specified in the *pom.xml* file.

Run the following command to build everything and enhance the DB classes:

      mvn clean compile

Make sure that the database was correctly configured. Use the contents of the file *create-db.sql* to create the database and grant privileges. For example,

      mysql –uroot -p 
      
Type your root user password and then

      source sql/create-db.sql

To make the migrations to the local database, use command

      mvn clean flyway:migrate

To launch the server run the command

    mvn jetty:run

Now, the client sample code can be executed in a new command window with

    mvn exec:java -Pclient
