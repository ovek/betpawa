# Readme

## Prerequisites for installing and running

Java (8 minimum),
 
Docker,

Node (used version 10.15.3),

npm (used version 6.4.1)

Gradle (version 6)

## Setting up Postgres

In root directory run:

`docker-compose up`

## Creating database

Go to tools directory

`cd tools/`

Run NPM install for installing node libraries:

`npm install`

Run database script for setting up database:

`node db.js clean grpc-server local`


## Building applications

### Build grpc-server

Navigate to grpc-folder

`cd grpc-server`

Run gradle build in project directory
`gradle clean build`

Database needs to be running as integration tests are run as part of build process. Future upgrade would be
setting up a separate database dedicated for integration testing.


### Run grpc-server
Application can be run from command line:

`java -jar grpc-server-0.0.1-SNAPSHOT.jar`


### Build grpc-client

Navigate to grpc-client

`cd grpc-client`

Run gradle build in project directory
`gradle clean build`


### Run grpc-server
Application can be run from command line:

`java -jar grpc-server-0.0.1-SNAPSHOT.jar <number of users> <number of threads per user> <number of round per thread>`

For example to run for 100 users with 2 threads per user 3 rounds per single thread:
`java -jar grpc-server-0.0.1-SNAPSHOT.jar 100 2 3`


## Explanation of important choices in your solution
Used technologies:

* Java 8
* Spring Boot 2.2.1 (includes Spring 5.2)
* Liquibase 3.8
* Postgres 9.6 (see explanation below)
* Grpc Spring Boot starter
* Gradle 6
* JUnit 5
* JPA 2.2.1 (users Hibernate 5.3)
* Docker (Database run in container)

Reason for replacing MySQL with Postgres:
* Developer has years of experience using Postgres for financial services domain
* Prepared node scripts for quickly setting up database together with triggers, logging schema, users for liquibase and application
* Postgres uses Multiversion Concurrency Control without read locks Postgres supports parallel query plans that can use multiple CPUs/cores Postgres can create indexes in a non-blocking way 
* Data integrity protection on transaction level (suitable for financial applications)


## Estimate on how many transactions your wallet can handle per second on your development machine (and an explanation on how you reached that number)

Development machine used for testing
* Processor: 2,4 GHz Intel Core i7
* Memory: 16 GB 1867 MHz LPDDR3

Measurements were done on grpc-client which initiates rounds containing multiple transactions and estimated to single
transaction level.

On average **client does around 50 - 60 operations (rounds) per second**. Measurements where done by changing number of parallel users.

For example: 100 users, 1 thread per user, 1 round per user or 1000 users, 1 thread per user, 1 round per user

Operations include running round A, round B or round C.

Round A has 7 operations, round B 5 and round C 8 operations. On average it is **6.67 transactions per operation**.

This gives 50 * 6.67 ~ 334 transactions/second and 60 * 6.67 ~ 400 transactions/second

Currently we can say that **wallet handles about 334 to 400 transactions/second without issues**.

Note 1: Round A contains 2 read operations and 5 update operations, round B 5 update operations, round C 3 read operations
and 5 update operations. All update operations contain in them the same read operation.

Note 2: Finishing time was measured until all ExecutorService threads were finished which introduces inaccuracy because 
all operations finish earlier then ExecutorService is shut down. Approach works for rough estimation, but not exact 
measurement.