# Missio Worship - Backend

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=aaronmbdev_missioworship-backend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=aaronmbdev_missioworship-backend)

### Especificaciones y requerimientos

1. Java 17+
2. Maven (Incluye versión portable)
3. Docker
4. Docker compose

### Variables de entorno

Es necesario configurar las siguientes variables de
entorno para que el backend pueda ejecutarse una vez compilado.
En caso que falte alguna, habrá un error al ejecutarse.

1. MYSQL_USERNAME
2. MYSQL_PASSWORD
3. MYSQL_URL
4. JWT_SECRET (Puede ser cualquier string random)
5. RECAPTCHA_SECRET (Puede ser cualquier string random)

### Instrucciones para ejecutar

#### Para lanzar los tests unitarios y de integración

``mvn clean test verify``

Si no tienes maven instalado, puedes usar el ejecutable del repositorio ``./mvnw``

#### Para ejecutar el servidor

Primero debes configurar el entorno de trabajo.

``mvn clean spring-boot:run``

Esto lanza el servidor en el puerto :8080

### Configurar el entorno de trabajo

Para probar el backend en local, necesitamos una base de datos de desarrollo. 
Para esto debes ejecutar docker-compose usando el yaml que está en el root del proyecto.
Esto va a lanzar una imagen de docker con una base de datos.

Antes de ejecutar el servidor debes configurar tus variables de entorno para que Spring Boot pueda contectarse a la BBDD.