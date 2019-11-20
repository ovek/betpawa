# 
##Installation and setup guide

####Prerequisites

Java (8 minimum),
 
Docker,

Node (used version 10.15.3),

npm (used version 6.4.1)

Gradle (version 6)

### SETUP

#### Setting up Postgres

In root directory run:

`docker-compose up`

#### Creating database

Go to tools directory

`cd tools/`

Run NPM install for installing node libraries:

`npm install`

Run database script for setting up database:

`node db.js clean grpc-server local`


### Building applications

#### Build grpc-server

Navigate to grpc-folder

`cd grpc-server`

Run gradle build in project directory
`gradle clean build`

Database needs to be running as integration tests are run as part of build process. Future upgrade would be
setting up a separate database dedicated for integration testing.


#### Run grpc-server
Application can be run from command line:

`java -jar grpc-server-0.0.1-SNAPSHOT.jar`


#### Build grpc-client

Navigate to grpc-client

`cd grpc-client`

Run gradle build in project directory
`gradle clean build`


#### Run grpc-server
Application can be run from command line:

`java -jar grpc-server-0.0.1-SNAPSHOT.jar <number of users> <number of threads per user> <number of round per thread>`

For example to run for 100 users with 2 threads per user 3 rounds per single thread:
`java -jar grpc-server-0.0.1-SNAPSHOT.jar 100 2 3`
