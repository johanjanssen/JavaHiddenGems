DROP TABLE IF EXISTS CARPART;
DROP TABLE IF EXISTS CAR;

CREATE TABLE CAR (
  ID INT PRIMARY KEY,
  BRAND varchar(200) NOT NULL,
  MODEL varchar(200) NOT NULL
);

CREATE TABLE CARPART (
  ID INT PRIMARY KEY,
  CAR_ID INT NOT NULL,
  NAME varchar(200) NOT NULL,
  PRICE decimal(10,2)  NOT NULL,
  FOREIGN KEY (CAR_ID) REFERENCES CAR(ID)
);