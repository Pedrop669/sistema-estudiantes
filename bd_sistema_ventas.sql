CREATE DATABASE IF NOT EXISTS sistema_ventas;
USE sistema_ventas;

-- tabla de personas (clase base)
CREATE TABLE persona (
    id_persona INT AUTO_INCREMENT PRIMARY KEY,
    tipo_documento VARCHAR(20),
    numero_documento VARCHAR(30),
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(100),
    direccion VARCHAR(200),
    activo BOOLEAN DEFAULT TRUE
);

-- tabla cliente hereda de persona
CREATE TABLE cliente (
    id_cliente INT PRIMARY KEY,
    codigo_cliente VARCHAR(20),
    limite_credito DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (id_cliente) REFERENCES persona(id_persona)
);

-- tabla vendedor hereda de persona
CREATE TABLE vendedor (
    id_vendedor INT PRIMARY KEY,
    codigo_vendedor VARCHAR(20),
    zona VARCHAR(50),
    comision_porcentaje DECIMAL(5,2) DEFAULT 0,
    FOREIGN KEY (id_vendedor) REFERENCES persona(id_persona)
);

-- tabla proveedor
CREATE TABLE proveedor (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nit VARCHAR(20),
    nombre_comercial VARCHAR(100) NOT NULL,
    contacto VARCHAR(100),
    telefono VARCHAR(15),
    email VARCHAR(100),
    direccion VARCHAR(200),
    activo BOOLEAN DEFAULT TRUE
);

-- tabla producto
CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20),
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio_venta DECIMAL(10,2) NOT NULL,
    stock_actual INT DEFAULT 0,
    stock_minimo INT DEFAULT 0,
    activo BOOLEAN DEFAULT TRUE
);

-- tabla orden de venta
CREATE TABLE orden_venta (
    id_orden_venta INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    estado VARCHAR(20) DEFAULT 'REGISTRADA',
    observaciones VARCHAR(255),
    total DECIMAL(10,2) DEFAULT 0,
    id_cliente INT NOT NULL,
    id_vendedor INT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    FOREIGN KEY (id_vendedor) REFERENCES vendedor(id_vendedor)
);

-- detalle de la orden de venta
CREATE TABLE detalle_orden_venta (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_orden_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    descuento DECIMAL(10,2) DEFAULT 0,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_orden_venta) REFERENCES orden_venta(id_orden_venta),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- tabla compra
CREATE TABLE compra (
    id_compra INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    estado VARCHAR(20) DEFAULT 'REGISTRADA',
    total DECIMAL(10,2) DEFAULT 0,
    id_proveedor INT NOT NULL,
    FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor)
);

-- detalle de compra
CREATE TABLE detalle_compra (
    id_detalle_compra INT AUTO_INCREMENT PRIMARY KEY,
    id_compra INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_compra) REFERENCES compra(id_compra),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- historial de movimientos de inventario
CREATE TABLE movimiento_inventario (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    fecha DATETIME NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    referencia_id INT,
    referencia_tipo VARCHAR(30),
    cantidad INT NOT NULL,
    stock_anterior INT NOT NULL,
    stock_nuevo INT NOT NULL,
    observaciones VARCHAR(255),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);
