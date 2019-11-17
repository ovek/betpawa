DROP DATABASE IF EXISTS wallet_db;

DROP OWNED BY wallet_owner CASCADE;

DROP ROLE IF EXISTS wallet_owner;
DROP USER IF EXISTS wallet_app;
DROP USER IF EXISTS wallet_migrator;
DROP ROLE IF EXISTS wallet_rw;

