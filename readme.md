**Installation and setup guide**

Prerequisites:
Java 8, 
Docker,
Node

## SETUP

###### Setting up Postgres

In root directory run:

`docker-compose up`

###### Creating database

Go to tools directory

`cd tools/`

Run NPM install for installing node libraries:

`npm install`

Run database script for setting up database:

`node db.js clean grpc-server local`


## Building application

###### Build grpc-server

Navigate to grpc-folder

`cd grpc-server`

Run gradle build
`gradle clean build`

Database needs to be running as integration tests are run as part of building process.
