# DevsHotel
¡Bienvenido al Challenge 2! Este desafío corresponde al desarrollo de una aplicación diseñada para gestionar y agendar citas en un hotel. Mediante la integración de diversas tecnologías, buscamos lograr una máxima eficiencia, una funcionalidad robusta y una implementación sencilla en el lado del backend. 

Esta aplicación combina herramientas modernas para ofrecer una solución confiable y fácil de mantener, optimizando la gestión de citas.

## Características:
* Seguridad: Implementación de la seguridad en Spring Boot utilizando spring-boot-starter-security, lo que permite proteger los endpoints de la API y gestionar roles y permisos de usuarios.
* Gestión de JWT: Implementación y manejo de JSON Web Tokens utilizando la librería java-jwt, asegurando una autenticación segura y eficiente.
* Autenticación: Acceso seguro a los Endpoints de la API.
* Crear: Añadir nuevas citas para servicios del hotel.
* Leer: Consultar citas existentes.
* Actualizar: Modificar detalles de citas.
* Eliminar: Eliminar citas según sea necesario.
* Validaciones de Negocio:
    * Verificación de Registro: Comprobación de si un usuario o huésped ya está registrado antes de crear una nueva cuenta.
    * Reserva Existente: Validación para asegurarse de que un huésped no tenga reservas duplicadas o conflictivas.
    * Validación de Campos: Validaciones de los campos al momento de realizar una reserva o registrar un usuario utilizando spring-boot-starter-validation, garantizando la integridad y el formato correcto de los datos ingresados.
* Pruebas Automatizadas: Implementación de pruebas automatizadas para controladores y repositorios utilizando spring-security-test, lo que simplifica y asegura las pruebas de aplicaciones protegidas con Spring Security.
* Manejo de Datos Sensibles: Creación de un archivo .env para almacenar contraseñas y otros datos sensibles, gestionado con la extensión spring-dotenv. Esto asegura que la información confidencial se mantenga segura y facilita la configuración de variables de entorno sin exponer datos sensibles en el código fuente.
* Gestión de Migraciones de Base de Datos: Uso de flyway-database-postgresql para gestionar y llevar el historial de los cambios en la base de datos, asegurando que las migraciones sean consistentes y rastreables.
* Documentación de API: Interfaz interactiva de Swagger para probar y documentar la API.

## Tecnologías Utilizadas
* Java & Spring Boot: Lenguaje y Framework de desarrollo backend que permite una rápida y eficiente creación de APIs.
* Docker: Plataforma de contenedorización que asegura entornos consistentes entre desarrollo y producción.
* Docker Compose: Herramienta para definir y gestionar múltiples contenedores Docker.
* Maven: Gestión de dependencias y construcción del proyecto.
* Spring-boot-starter-validation: Dependencia para implementar validaciones de datos en las solicitudes.
* Spring-boot-starter-security: Dependencia para implementar la seguridad en Spring Boot, permitiendo proteger los endpoints de la API y gestionar roles y permisos de usuarios.
* Spring-security-test: Biblioteca para simplificar las pruebas de aplicaciones aseguradas con Spring Security Instalación
* java-jwt: Librería utilizada para la creación y manejo de JSON Web Tokens, facilitando la implementación de autenticación segura.
* Spring-dotenv: Extensión para gestionar variables de entorno a través de archivos .env, facilitando el manejo seguro de configuraciones sensibles.
* Swagger (Springdoc OpenAPI): Herramienta para la documentación y prueba de APIs.
* PostgreSQL: Base de datos relacional utilizada para almacenar datos de citas y usuarios.
* Flyway-database-postgresql: Herramienta para gestionar migraciones de bases de datos PostgreSQL, permitiendo un historial de cambios claro y consistente.

## **Instalación**

Sigue estos pasos para configurar el proyecto localmente:

1. **Clonar el Repositorio:**
    ```sh
    git clone https://github.com/tu-usuario/tu-repositorio.git
    ```
   
2. **Ingresar al Directorio del Proyecto:**
    ```sh
    cd tu-repositorio
    ```
   
3. **Configurar y Ejecutar Contenedores con Docker:**
    ```sh
    docker-compose up --build
    ```
   
4. **Configurar las Propiedades de la Aplicación:**
    Crea un archivo `.env` en el directorio raíz del proyecto y agrega las siguientes variables:
    ```env
    SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-hotel:5432/hotel_db
    SPRING_DATASOURCE_USERNAME=postgres
    SPRING_DATASOURCE_PASSWORD=tu_password
    SPRING_JPA_HIBERNATE_DDL_AUTO=update
    JWT_SECRET=tu_secreto_jwt
    ```
    **Nota:** Asegúrate de que el archivo `.env` esté incluido en tu `.gitignore` para evitar que se suba al repositorio por error.
   
5. **Construir y Ejecutar la Aplicación (Opcional):**
    ```sh
    mvn clean install
    mvn spring-boot:run
    ```
    **Nota:** Asegúrate de que PostgreSQL esté corriendo y que las propiedades de la aplicación estén correctamente configuradas antes de ejecutar la aplicación localmente.

## **Uso**

Una vez que la aplicación esté en funcionamiento, puedes interactuar con ella de la siguiente manera:

- **Acceder a la Documentación de la API:**
    Navega a `http://localhost:8080/swagger-ui.html` para ver y probar la documentación interactiva de la API.

- **Endpoints de la API:**
    Utiliza la interfaz de Swagger para explorar los endpoints disponibles para crear, leer, actualizar y eliminar citas.

## **Documentación de la API**

La API está documentada utilizando **Swagger**, lo que te permite probar fácilmente los endpoints y entender las operaciones disponibles sin necesidad de documentación manual.

## **Capturas de Pantalla**

* Ejecución de Contenedor Docker: 

![docker](/img/image-3.png)

* Creación de Usuario:

![crear usuario](/img/image.png)

* Login de Usuario:

![Login usuario](/img/image-1.png)

* Token invalido:

![Token invalido](/img/image-2.png)

* Validación de Negocio: 

![validacion de negocio](/img/image-4.png)

* Registro de una Reserva:

![registro reserva](/img/image-5.png)

* Listar reservas activas y con paginación:

![reservas paginacion](/img/image-6.png)
* Actualizar reserva:

![actualizar reserva](/img/image-7.png)

* Listar reserva por Id:

![reserva id](/img/image-8.png)
* Eliminar una reserva:

![eliminar reserva](/img/image-9.png)

* Documentación API:

![documentacion api](/img/image-10.png)

* Prueba automatizada del Repository Reserva:

![prueba repository](/img/image-13.png)

* Interfaz Swagger:

![interfaz swagger](/img/image-11.png)

### Challenge 02 correspondiente al BootCamp Back-End en Java impartido por Alura Latam.  