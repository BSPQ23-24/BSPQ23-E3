/* DELETE 'logifyDB' database*/
DROP SCHEMA IF EXISTS logifyDB;
/* DELETE USER 'spq' AT LOCAL SERVER*/
DROP USER IF EXISTS 'spq'@'localhost';

/* CREATE 'logifyDB' DATABASE */
CREATE SCHEMA logifyDB;
/* CREATE THE USER 'spq' AT LOCAL SERVER WITH PASSWORD 'spq' */
CREATE USER IF NOT EXISTS 'spq'@'localhost' IDENTIFIED BY 'spq';

GRANT ALL ON logifyDB.* TO 'spq'@'localhost';

