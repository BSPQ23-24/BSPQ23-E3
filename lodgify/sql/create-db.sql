/* DELETE 'lodgifyDB' database*/
DROP SCHEMA IF EXISTS lodgifyDB;
/* DELETE USER 'spq' AT LOCAL SERVER*/
DROP USER IF EXISTS 'spq'@'localhost';

/* CREATE 'lodgifyDB' DATABASE */
CREATE SCHEMA lodgifyDB;
/* CREATE THE USER 'spq' AT LOCAL SERVER WITH PASSWORD 'spq' */
CREATE USER IF NOT EXISTS 'spq'@'localhost' IDENTIFIED BY 'spq';

GRANT ALL ON lodgifyDB.* TO 'spq'@'localhost';