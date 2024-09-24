Proyecto de Gestión de Inventario
Este proyecto es un sistema de gestión de inventario que incluye autenticación JWT para proteger los recursos. El sistema permite gestionar compañías, clientes, productos, categorías, órdenes y el inventario. Está construido con Spring Boot 3.3.3 para el backend y React con TypeScript para el frontend.

Tabla de Contenidos
Características
Requisitos Previos
Configuración del Backend
Instalación
Configuración
Ejecución
Endpoints Principales
Configuración del Frontend
Instalación
Configuración
Ejecución
Comandos Útiles
Estructura del Proyecto
Contribuciones
Licencia
Características
Autenticación JWT: Seguridad basada en tokens para proteger los recursos del backend.
Gestión de Entidades:
Compañías
Clientes
Productos
Categorías
Órdenes
Inventario
Frontend en React: Interfaz de usuario intuitiva desarrollada con React y TypeScript.
Backend en Spring Boot: API RESTful robusta y eficiente.
Base de Datos: Implementación con PostgreSQL.
Requisitos Previos
Antes de iniciar, asegúrate de tener instalados los siguientes requisitos en tu máquina:

Java 17
Node.js (versión 16 o superior)
Maven (para el backend)
PostgreSQL
Git

Configuración del Backend
Instalación
Clonar el repositorio:

git clone https://github.com/usuario/proyecto-inventario.git
cd proyecto-inventario/backend

Instalar dependencias:

mvn clean install

Configuración
Crear un archivo .env o configurar el archivo application.properties en src/main/resources/ con los siguientes parámetros:

spring.datasource.url=jdbc:postgresql://localhost:5432/inventario
spring.datasource.username=usuario
spring.datasource.password=contraseña
spring.jpa.hibernate.ddl-auto=update

spring.security.jwt.secret-key=mi_clave_secreta_para_jwt
spring.security.jwt.expiration=86400000

Ejecución
Ejecutar la aplicación:

mvn spring-boot:run

Rutas principales:
![image](https://github.com/user-attachments/assets/4093ef92-1936-4b74-a915-5d2b95a4547e)


