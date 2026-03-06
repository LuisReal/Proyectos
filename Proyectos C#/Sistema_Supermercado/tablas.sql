CREATE DATABASE supermercado; /*crea la base de datos*/
USE supermercado; /*especifica que se usara esta base de datos*/

CREATE TABLE clientes(   /*crea la tabla de clientes*/
 id_cliente INT AUTO_INCREMENT PRIMARY KEY,
 nombre VARCHAR(10),
 edad INT,
 sexo char,
 nit INT
);

SELECT * FROM clientes;

SET foreign_key_checks = 0; /*deshabilita las llaves foraneas de la tabla ventas*/
TRUNCATE TABLE clientes;
SET foreign_key_checks = 1; /*habilita las llaves foraneas de la tabla ventas*/

ALTER TABLE clientes MODIFY COLUMN imagen VARCHAR(200);
ALTER TABLE productos MODIFY COLUMN imagen VARCHAR(200);

SELECT * FROM ventas;
SET foreign_key_checks = 0; /*deshabilita las llaves foraneas de la tabla ventas*/
TRUNCATE TABLE ventas;
SET foreign_key_checks = 1; /*habilita las llaves foraneas de la tabla ventas*/

SELECT v.cantidad_comprada, v.id_producto, p.precio
FROM ventas v
JOIN productos p ON v.id_producto = p.id_producto;

ALTER TABLE ventas
ADD COLUMN codigo_venta INT
AFTER id_venta;

SELECT * FROM productos;

SET foreign_key_checks = 0; /*deshabilita las llaves foraneas de la tabla ventas*/
TRUNCATE TABLE productos;
SET foreign_key_checks = 1; /*habilita las llaves foraneas de la tabla ventas*/



SELECT * FROM usuarios; 
TRUNCATE TABLE usuarios;

ALTER TABLE clientes ADD imagen VARCHAR(50); /*para agregar una nueva columna llamada imagen a la tabla clientes*/


CREATE TABLE productos(
 id_producto INT AUTO_INCREMENT PRIMARY KEY,
 nombre VARCHAR(10),
 precio DECIMAL(10,2),
 cantidad INT,
 imagen VARCHAR(50)
);

CREATE TABLE ventas (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    id_producto INT,
    nit INT,
    nombre_producto VARCHAR(50),
    cantidad_comprada INT,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto)
);

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    usuario VARCHAR(50),
    contrasena VARCHAR(50),
    id_venta INT,
    FOREIGN KEY (id_venta) REFERENCES ventas(id_venta)
    
);

INSERT INTO ventas (id_cliente, id_producto, fecha_venta, cantidad, total)
VALUES (
  (SELECT id_cliente FROM clientes WHERE nombre = 'Juan'),
  (SELECT id_producto FROM productos WHERE nombre = 'Teclado'),
  CURDATE(), /*devuelve la fecha actual del sistema*/
  2,
  100.00
);

SET foreign_key_checks = 0; /*deshabilita las llaves foraneas*/
TRUNCATE TABLE clientes;
SET foreign_key_checks = 1; /*habilita las llaves foraneas*/

