# Sistema de Gestión de Reservaciones de Hotel

## Descripción
Aplicación de escritorio desarrollada en Java con JavaFX para administrar habitaciones, huéspedes y reservaciones de un hotel.

## Autores
- 

## Tecnologías
- Java 17+
- JavaFX 17
- MySQL 8
- Maven

## Requisitos
- JDK 17 o superior
- MySQL 8.0
- Maven 3.8+

## Pasos para ejecutar
1. Clonar el repositorio
2. Importar `src/main/resources/db/schema.sql` en MySQL
3. Configurar credenciales en `DatabaseConnection.java`
4. Ejecutar `mvn javafx:run`

## Patrones de diseño implementados
1. Singleton — DatabaseConnection
2. DAO — GenericDAO<T>
3. Factory Method — ReporteService
4. Observer — EstadoHabitacion
5. Strategy — TarifaStrategy
6. Decorator — ReservacionDecorator
