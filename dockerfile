# Dockerfile con la configuración para un ambiente de Desarrollo
# Usa una imagen base de Java
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor
COPY target/DevsHotel-0.0.1-SNAPSHOT.jar DevsHotel-0.0.1-SNAPSHOT.jar

# Usa un script para ejecutar la aplicación en modo de desarrollo
# Copiar el wrapper de Maven y su directorio, para ejecutar Maven dentro del Contenedor
COPY mvnw .
COPY .mvn .mvn

# Copia el archivo pom.xml para que maven pueda resolver las dependencias
COPY pom.xml .

# Descarga las dependencias de Maven para que estén disponibles sin conexión
RUN ./mvnw dependency:go-offline -B

# Copia el codigo fuente al contenedor
COPY src src
# Copia el archivo de entorno para configurar las variables de entorno
COPY .env .env

# Expone el puerto en el que la aplicación escucha
EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]

# Dockerfile con la configuración para un ambiente de Producción
# Usa una imagen base de Java
#FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo
#WORKDIR /app

# Copia los archivos necesarios para la construcción de la aplicación
#COPY mvnw ./
#COPY .mvn .mvn
#COPY pom.xml .

# Descarga las dependencias de Maven sin conexión
#RUN ./mvnw dependency:go-offline -B

# Copia el código fuente de la aplicación
#COPY src ./src
#COPY .env .env

# Compila la aplicación
#RUN ./mvnw package -DskipTests

# Expone el puerto en el que la aplicación escucha
#EXPOSE 8080

# Comando para ejecutar la aplicación
#CMD ["java", "-jar", "target/DevsHotel-0.0.1-SNAPSHOT.jar"]