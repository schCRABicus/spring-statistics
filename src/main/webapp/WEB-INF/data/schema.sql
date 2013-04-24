DROP TABLE IF EXISTS user;

CREATE TABLE user(
  id INT NOT NULL AUTO_INCREMENT ,
  login VARCHAR(50) NOT NULL ,
  password VARCHAR(50) NOT NULL,
  first_name VARCHAR(50) NOT NULL ,
  last_name VARCHAR(50) NOT NULL ,
  birth_date DATETIME ,
  description VARCHAR(255) ,
  photo BLOB ,
  version INT NOT NULL DEFAULT 0,
  account_non_expired BOOLEAN DEFAULT TRUE,
  account_non_locked BOOLEAN DEFAULT TRUE,
  credentials_non_expired BOOLEAN DEFAULT TRUE,
  enabled  BOOLEAN DEFAULT TRUE,
  PRIMARY KEY (id)
);

CREATE TABLE role(
  id INT NOT NULL AUTO_INCREMENT ,
  role VARCHAR(50) NOT NULL ,
  PRIMARY KEY (id),
  CONSTRAINT uc_role_1 UNIQUE (role)
);

CREATE TABLE user_role(
  user_id INT NOT NULL ,
  role_id INT NOT NULL ,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY fk_user_1 REFERENCES user(id),
  FOREIGN KEY fk_role_1 REFERENCES role(id)
);