-- Crear la tabla 'users'
CREATE TABLE "users" (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
	role VARCHAR(255) NOT NULL
);

-- Crear la tabla 'image'
CREATE TABLE image (
    id SERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_user
      FOREIGN KEY(user_id) 
	  REFERENCES "users"(id)
	  ON DELETE CASCADE
);

-- Crear la tabla 'Person'

CREATE TABLE Person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    ci VARCHAR(50),
    sexo CHAR(1) CHECK (sexo IN ('M', 'F')),
    contact_number VARCHAR(15) NOT NULL,
	tipo_user SMALLINT NOT NULL,
	titulo VARCHAR(255),
	birth_date DATE, 
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);