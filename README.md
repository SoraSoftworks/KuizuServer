(kuizu logo)[http://sora.eliteheberg.fr/wp-content/uploads/logo.png]

# KuizuServer

Kuizu game server

## About the game

Kuizu (which means quiz in japanese) in a PVP dueling game. The game generates random questions about random topics and measure 
every player's knowledge. It uses Elo rating for ranking.

## About the server

Kuizu game server respects the game specifications 
which you can read about it here (http://sora.eliteheberg.fr/uploads/kuizu_spec.pdf!)[http://sora.eliteheberg.fr/uploads/kuizu_spec.pdf]

The server also adds a very basic monitoring functionalities, the monitoring
client can be found here (https://github.com/SoraSoftworks/KuizuMonitor!)[https://github.com/SoraSoftworks/KuizuMonitor]

## Installing

Prepare your database by creating an empty database, and import (or follow the same structure) the SQL file under `db_export/kuizu_db`
Under the class db.DBHelper, the constantes
```java
private static final String DATABASE_NAME = "foo";
private static final String DATABASE_USER = "bar";
private static final String DATABASE_PASSWORD = "foobar";
```

Should be replaced with your database name, user and password respectively.

## Compile & Run

This repo is an eclipse project, you can easily import it to Eclipse 
or net beans IDE. 