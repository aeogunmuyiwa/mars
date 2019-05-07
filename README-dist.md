# Mars

## Required Software

1. Java 8
2. MySQL 5.5 to 5.7

## Configuration

To configure the application:

1. Create database schema for the application with the name of your preference (the tables are created and populated automatically during the first run)
2. Open the configuration file `config/application-dev.yml` and set:
   1. `spring.datasource.url` to the database host, port and schema (defaults to **localhost**, **3306** and **mars** respectively).
   2. `spring.datasource.username` to the database username (defaults to **mars**)
   3. `spring.datasource.password` to the password of the database user set on previous step (defaults to **mars**)
   4. `server.port` with server port (defaults to **8080**)

## Running

To run the distribution packaged application execute the following command:

`java -jar mars-1.0.0.war`

Note that sudo might be necessary if the application is configured to run on a port below 1024.

Finally navigate to [http://localhost:8080](http://localhost:8080) in your browser (note the port number might be different according to your configuration and deployment environment).
