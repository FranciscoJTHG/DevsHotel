CREATE TABLE huesped (
    id BIGSERIAL PRIMARY KEY,
    nombre_completo VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefono VARCHAR(20)
);

CREATE TABLE reserva (
    id BIGSERIAL PRIMARY KEY,
    fecha_check_in DATE NOT NULL,
    fecha_check_out DATE NOT NULL,
    valor VARCHAR(20),
    forma_de_pago VARCHAR(100),
    huesped_id BIGINT NOT NULL,
    CONSTRAINT fk_huesped FOREIGN KEY (huesped_id) REFERENCES huesped (id),
    CONSTRAINT check_fechas CHECK (fecha_check_out > fecha_check_in) -- Restricción que verifica que la fecha de check-out sea posterior a la fecha de check-in
);