SET search_path = wallet, pg_catalog;

CREATE DATABASE wallet_db
WITH ENCODING = 'UTF8'
LC_COLLATE = 'en_US.UTF-8'
LC_CTYPE = 'en_US.UTF-8';

GRANT ALL ON DATABASE wallet_db TO "betpawaAdmin";

CREATE ROLE wallet_owner NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;
GRANT wallet_owner TO "betpawaAdmin";

CREATE ROLE wallet_rw NOLOGIN;
GRANT CONNECT ON DATABASE wallet_db TO wallet_rw;

CREATE USER wallet_migrator PASSWORD 'wallet_migrator' INHERIT;
ALTER USER wallet_migrator SET search_path TO public,wallet;
GRANT wallet_owner TO wallet_migrator;
GRANT ALL PRIVILEGES ON DATABASE wallet_db TO wallet_migrator;

CREATE USER wallet_app PASSWORD 'wallet_app' INHERIT;
ALTER USER wallet_app SET search_path TO wallet;
GRANT wallet_rw TO wallet_app;
