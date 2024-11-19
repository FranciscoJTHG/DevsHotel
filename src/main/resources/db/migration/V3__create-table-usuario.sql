
-- Se crea tabla usuario para implementar la Autenticación con Spring Security
CREATE TABLE usuario (
    id BIGSERIAL NOT NULL,
    email VARCHAR(100) NOT NULL,
    clave VARCHAR(300) NOT NULL,
    PRIMARY KEY(id)
);