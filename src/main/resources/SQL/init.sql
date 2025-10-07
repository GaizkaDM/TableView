-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS tableview_db
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_general_ci;

-- Crear la tabla directamente con referencia completa
DROP TABLE IF EXISTS tableview_db.person;

CREATE TABLE tableview_db.person (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL
);

-- Insertar algunos registros de ejemplo
INSERT INTO tableview_db.person (nombre, apellido, fecha_nacimiento) VALUES
('Ana', 'García', '1995-04-12'),
('Luis', 'Martínez', '1988-11-23'),
('María', 'Fernández', '2001-07-05'),
('Carlos', 'López', '1982-02-15'),
('Elena', 'Rodríguez', '1993-09-30');