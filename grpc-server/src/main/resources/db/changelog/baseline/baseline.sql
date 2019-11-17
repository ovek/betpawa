-----------------------
-- ACCOUNT
-----------------------
CREATE TABLE account
(
  acco_id                VARCHAR(100)             NOT NULL,
  user_id                VARCHAR(100)             NOT NULL,
  CONSTRAINT pk_account_id PRIMARY KEY (acco_id)
);

-----------------------
-- BALANCE
-----------------------
CREATE TABLE balance
(
  bala_id             VARCHAR(100)             NOT NULL,
  acco_id             VARCHAR(100)             NOT NULL,
  ccy                 VARCHAR(3)               NOT NULL,
  amt                 NUMERIC(16, 2)           NOT NULL,
  CONSTRAINT pk_balance_id PRIMARY KEY (bala_id)
);