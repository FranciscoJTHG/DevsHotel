
-- Se agrega columna activo con valor por defecto true para manejar la parte activar/desactivar un registro
ALTER TABLE reserva
ADD COLUMN activo boolean DEFAULT true;

-- Actualiza todos los registros existentes
UPDATE reserva SET activo = true;