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