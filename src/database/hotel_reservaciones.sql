create database hotel_reservaciones;

use hotel_reservaciones;

create table puestos (
                         id_puesto int auto_increment primary key,
                         nombre_puesto varchar(100) unique not null,
                         descripcion varchar(255)
);

create table roles (
                       id_rol int auto_increment primary key,
                       nombre_rol varchar(50) unique not null
);

create table empleados (
                           id_empleado int auto_increment primary key,
                           nombre varchar(100) not null,
                           apellido varchar(100) not null,
                           id_puesto int not null,
                           salario decimal(10,2) not null,
                           turno varchar(50),
                           fecha_contratacion date not null,
                           foreign key (id_puesto) references puestos(id_puesto)
                               on delete restrict
);

create table usuarios_sistema (
                                  id_usuario int auto_increment primary key,
                                  id_empleado int not null,
                                  usuario varchar(50) unique not null,
                                  password_hash varchar(255) not null,
                                  id_rol int not null,
                                  foreign key (id_empleado) references empleados(id_empleado)
                                      on delete cascade,
                                  foreign key (id_rol) references roles(id_rol)
                                      on delete restrict
);

create table clientes (
                          id_cliente int auto_increment primary key,
                          nombre varchar(100) not null,
                          apellido varchar(100) not null,
                          correo varchar(150) unique,
                          telefono varchar(20),
                          calle varchar(150),
                          ciudad varchar(100),
                          estado varchar(100),
                          cp varchar(10)
);

create table tipos_habitacion (
                                  id_tipo int auto_increment primary key,
                                  nombre varchar(100) not null,
                                  capacidad int not null,
                                  precio_noche decimal(10,2) not null
);

create table estados_habitacion (
                                    id_estado_habitacion int auto_increment primary key,
                                    nombre varchar(50) unique not null
);

create table habitaciones (
                              id_habitacion int auto_increment primary key,
                              numero varchar(10) unique not null,
                              piso int not null,
                              descripcion varchar(255),
                              id_tipo int not null,
                              id_estado_habitacion int not null,
                              foreign key (id_tipo) references tipos_habitacion(id_tipo)
                                  on delete restrict,
                              foreign key (id_estado_habitacion) references estados_habitacion(id_estado_habitacion)
                                  on delete restrict
);

create table servicios (
                           id_servicio int auto_increment primary key,
                           nombre varchar(100) not null,
                           precio decimal(10,2) not null,
                           descripcion varchar(255)
);

create table habitacion_servicios (
                                      id_habitacion int not null,
                                      id_servicio int not null,
                                      primary key (id_habitacion, id_servicio),
                                      foreign key (id_habitacion) references habitaciones(id_habitacion)
                                          on delete cascade,
                                      foreign key (id_servicio) references servicios(id_servicio)
                                          on delete cascade
);

create table estados_reservacion (
                                     id_estado_reservacion int auto_increment primary key,
                                     nombre varchar(50) unique not null
);

create table reservaciones (
                               id_reservacion int auto_increment primary key,
                               id_cliente int not null,
                               id_habitacion int not null,
                               fecha_reservacion datetime not null,
                               fecha_entrada date not null,
                               fecha_salida date not null,
                               numero_huespedes int not null,
                               total decimal(10,2),
                               id_estado_reservacion int not null,
                               foreign key (id_cliente) references clientes(id_cliente)
                                   on delete cascade,
                               foreign key (id_habitacion) references habitaciones(id_habitacion)
                                   on delete restrict,
                               foreign key (id_estado_reservacion) references estados_reservacion(id_estado_reservacion)
                                   on delete restrict
);

create table check_in (
                          id_check_in int auto_increment primary key,
                          id_reservacion int not null,
                          id_empleado int not null,
                          fecha_hora datetime not null,
                          observaciones varchar(255),
                          foreign key (id_reservacion) references reservaciones(id_reservacion)
                              on delete cascade,
                          foreign key (id_empleado) references empleados(id_empleado)
                              on delete restrict
);

create table check_out (
                           id_check_out int auto_increment primary key,
                           id_reservacion int not null,
                           id_empleado int not null,
                           fecha_hora datetime not null,
                           foreign key (id_reservacion) references reservaciones(id_reservacion)
                               on delete cascade,
                           foreign key (id_empleado) references empleados(id_empleado)
                               on delete restrict
);

create table consumos (
                          id_consumo int auto_increment primary key,
                          id_reservacion int not null,
                          id_servicio int not null,
                          cantidad int not null,
                          fecha datetime not null,
                          foreign key (id_reservacion) references reservaciones(id_reservacion)
                              on delete cascade,
                          foreign key (id_servicio) references servicios(id_servicio)
                              on delete restrict
);

create table metodos_pago (
                              id_metodo_pago int auto_increment primary key,
                              nombre varchar(50) unique not null
);

create table historial_estado_habitacion (
                                             id_historial int auto_increment primary key,
                                             id_habitacion int not null,
                                             id_estado_anterior int not null,
                                             id_estado_nuevo int not null,
                                             id_empleado int,
                                             fecha_cambio datetime not null,
                                             motivo varchar(255),
                                             foreign key (id_habitacion) references habitaciones(id_habitacion)
                                                 on delete cascade,
                                             foreign key (id_estado_anterior) references estados_habitacion(id_estado_habitacion)
                                                 on delete restrict,
                                             foreign key (id_estado_nuevo) references estados_habitacion(id_estado_habitacion)
                                                 on delete restrict,
                                             foreign key (id_empleado) references empleados(id_empleado)
                                                 on delete set null
);

create table pagos (
                       id_pago int auto_increment primary key,
                       id_reservacion int not null,
                       id_metodo_pago int not null,
                       monto decimal(10,2) not null,
                       fecha_pago datetime not null,
                       foreign key (id_reservacion) references reservaciones(id_reservacion)
                           on delete cascade,
                       foreign key (id_metodo_pago) references metodos_pago(id_metodo_pago)
                           on delete restrict
);

-- ═══════════════════════════════════════════════════════════════════════
-- DATOS INICIALES (SEED DATA)
-- ═══════════════════════════════════════════════════════════════════════

-- Roles del sistema
insert into roles (nombre_rol) values
('Administrador'),
('Recepcionista'),
('Limpieza'),
('Mantenimiento');

-- Puestos de trabajo
insert into puestos (nombre_puesto, descripcion) values
('Recepcionista',   'Atención al cliente y gestión de check-in/check-out'),
('Gerente',         'Administración general del hotel'),
('Camarero/a',      'Limpieza y mantenimiento de habitaciones'),
('Chef',            'Preparación de alimentos en el restaurante'),
('Técnico',         'Mantenimiento de instalaciones y equipos');

-- Servicios adicionales del hotel
insert into servicios (nombre, precio, descripcion) values
('Desayuno',          180.00, 'Desayuno buffet incluido para todos los huéspedes'),
('Estacionamiento',   120.00, 'Uso del estacionamiento privado del hotel por día'),
('Spa',               500.00, 'Acceso completo al área de spa y relajación'),
('Lavandería',        150.00, 'Servicio de lavado y planchado de ropa'),
('Room Service',      200.00, 'Servicio de alimentos a la habitación disponible 24h'),
('Internet Premium',   80.00, 'Acceso a internet de alta velocidad en la habitación');

-- Métodos de pago
insert into metodos_pago (nombre) values
('Efectivo'),
('Tarjeta de Crédito'),
('Tarjeta de Débito'),
('Transferencia Bancaria');

-- Estados de habitación base
insert into estados_habitacion (nombre) values
('disponible'),
('ocupada'),
('mantenimiento'),
('limpieza');

-- Estados de reservación base
insert into estados_reservacion (nombre) values
('pendiente'),
('confirmada'),
('en curso'),
('completada'),
('cancelada');

-- Tipos de habitación base
insert into tipos_habitacion (nombre, capacidad, precio_noche) values
('Sencilla',   1, 800.00),
('Doble',      2, 1200.00),
('Suite',      4, 2500.00),
('Familiar',   6, 1800.00);

-- Empleados de ejemplo
insert into empleados (nombre, apellido, id_puesto, salario, turno, fecha_contratacion) values
('Carlos',    'González',  2, 25000.00, 'Matutino',   '2022-01-15'),
('Ana',       'Martínez',  1, 12000.00, 'Matutino',   '2023-03-10'),
('Luis',      'Ramírez',   3, 10000.00, 'Vespertino', '2023-06-01'),
('Sofía',     'López',     5, 11000.00, 'Matutino',   '2024-01-20');

-- Usuarios del sistema (contraseñas SHA-256)
-- admin123  → 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a
-- hotel123  → 2b4da0b6dca4d1c33fa48eadac28085f07c11a1bca5a8c28c0ada27d04a0cc9
-- limp123   → 8f4ca6b2f5b74a3dac11b49cf2c7640f24e01d6c93c8e32399c2dda07073978d
-- mant123   → d26c5f81bb24ce4c9ef81f66e96addc77e26459bc1a8bd2a35a2c3cf9e97bb06
insert into usuarios_sistema (id_empleado, usuario, password_hash, id_rol) values
(1, 'admin',     '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a', 1),
(2, 'recepcion', '2b4da0b6dca4d1c33fa48eadac28085f07c11a1bca5a8c28c0ada27d04a0cc9', 2),
(3, 'limpieza',  '8f4ca6b2f5b74a3dac11b49cf2c7640f24e01d6c93c8e32399c2dda07073978d', 3),
(4, 'manto',     'd26c5f81bb24ce4c9ef81f66e96addc77e26459bc1a8bd2a35a2c3cf9e97bb06', 4);