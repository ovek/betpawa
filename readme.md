**Installation and setup guide**

Prerequisites:
Java 8, 
Docker,
Node

***Setting up Postgres***

In root directory run:

`docker-compose up`

***Setting up database in Postgres***
Go to tools directory

`cd tools/`

Run NPM install:

`npm install`

Run database script for setting up database:

`node db.js clean grpc-server local`