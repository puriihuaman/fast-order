# 📦 Sistema de Gestión de Pedidos - _Fast Order_

**Fast Order** es una API Rest que permite gestionar pedidos de productos de manera eficiente y
segura.  
Está pensada para facilitar operaciones como _registrar usuarios_, _gestionar productos_ y
_realizar pedidos_, todo en tipo real y de forma asincrónica.

---

## 🎯 Objetivos del proyecto

- Desarrollar un sistema de gestión de pedidos con operaciones CRUD (Crear, Leer, Actualizar y
  Eliminar).
- Organizar el código utilizando la arquitectura **MVC** para lograr una organización clara del
  código.
- Proteger los datos y endpoints, integrando un mecanismo de seguridad con **JWT** y **Spring
  Security**.
- Asincronía y comunicación eficiente mediante **Apache Kafka**.

---

## 🧩 Requisitos funcionales

- **Gestión de usuarios**: registro, login y autenticación con JWT.
- **Gestión de productos**: operaciones CRUD para productos.
- **Gestión de pedidos**: creación, modificación y consulta de pedidos.
- Notificaciones asincrónicas de cambios en los pedidos mediante Kafka.

---

## 🛠 Tecnologías utilizadas

- **Spring Boot**: _Framework principal para el desarrollo del backend._
- **JPA (Hibernate)**: _Mapeo objeto-relacional para la persistencia de datos._
- **Apache Kafka**: _Comunicación asincrónica y notificaciones._
- **Spring Security y JWT**: _Autenticación y autorización seguras._
- **PostgreSQL**: _Motor de base de datos._
- **Postman**: _Testing y documentación de endpoints._
- **Swagger / Open API**: _Documentación de endpoints._

---

## 🧱 Arquitectura del proyecto

El proyecto sigue una estructura organizada por capas:

- **Controladores**: Manejan la lógica de las peticiones HTTP.
- **Servicios**: Contienen la lógica de negocio.
- **Repositorios**: Administran la persistencia de datos.

### 📂 Estructura de carpetas

```
fast-order/
├── src/
│   ├── main/
│   │   ├── java
│   │   │   └── fast_order/
│   │   │       ├── controller/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── resources/
│   └── test/
├── README.md
├── .env.demo
├── .gitignore
└── pom.xml
```

### 🏷 Tablas o entidades

Entidad `role`.

| Campo       | Tipo            | Descripción         |
|-------------|-----------------|---------------------|
| id          | Long            | Identificador único |
| role_name   | Enum (RoleType) | Nombre del rol      |
| description | String          | Descripción del rol |

Entidad `user`.

| Campo        | Tipo              | Descripción           |
|--------------|-------------------|-----------------------|
| id           | Long              | Identificador único   |
| name         | String            | Nombre completo       |
| email        | String            | Correo electrónico    |
| password     | String            | Contraseña de usuario |
| sign_up_date | Date              | Fecha de registro     |
| total_spent  | Double            | Total de gastos       |
| role         | Role (RoleEntity) | Rol del usuario       |

Entidad `product`.

| Campo       | Tipo   | Descripción           |
|-------------|--------|-----------------------|
| id          | Long   | Identificador único   |
| name        | String | Nombre del producto   |
| stock       | String | Número de existencias |
| price       | Double | Precio del producto   |
| description | String | Detalles del producto |

Entidad `order`.

| Campo      | Tipo                   | Descripción                |
|------------|------------------------|----------------------------|
| id         | Long                   | Identificador único        |
| amount     | Integer                | Cantidad de productos      |
| user       | User(UserEntity)       | Identificador del usuario  |
| product    | Product(ProductEntity) | Identificador del producto |
| status     | Enum(OrderStatus)      | Estado del pedido          |
| created_at | Date                   | Fecha de creación          |

---

## ⚙ Instalación y Configuración

1. **Clonar repositorio**
   ```bash
   git clone https://github.com/puriihuaman/fast-order.git
   cd fast-order
   ```

2. **Crear base de datos y esquema en PostgreSQL**
    - Crear la base de datos en local:
       ```sql
       CREATE DATABASE db_name WITH OWNER = postgres ENCODING = 'UTF8';
       ``` 
    - Crear el esquema:
       ```sql
      CREATE SCHEMA schema_name;
      ```
3. **Tener instalado Apache Kafka**

   [Descargar Apache Kafka](https://dlcdn.apache.org/kafka/3.9.0/kafka_2.13-3.9.0.tgz)  
   _Abrir una terminal y ejecutar los siguientes comandos en ventas distintas_.
    - Iniciar el servidor de Zookeeper:
      ```bash
      .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
      ```
    - Iniciar el servidor de Kafka:
      ```bash
      .\bin\windows\kafka-server-start.bat .\config\server.properties
      ```

4. **Configurar variables de entorno**

   📄 Crear archivo `.env` con las siguientes variables de entorno (revisar el archivo `.env.demo`):

    ```dotenv
    # Configuración de la base de datos
    SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/db_name?currentSchema=schema_name
    SPRING_DATASOURCE_USERNAME=your_user
    SPRING_DATASOURCE_PASSWORD=your_password

    # Seguridad
    SECURITY_USER_NAME=admin
    SECURITY_USER_PASSWORD=admin

    # JWT
    SECRET_KEY=62d84588dd8e4b359...
    TIME_EXPIRATION=86400000

    # KAFKA
    KAFKA_TOPIC_NAME=fast-order-topic
    KAFKA_TOPIC_GROUP=kafka-fast-order-group
    ```
5. **Ejecutar la aplicación.**
   ```bash
   mvn spring-boot:run
   ```
6. **Probar la API**
    - Autenticación desde Postman:  
      `POST http://localhost:8080/api/auth/login`
    - Documentación Swagger (en el navegador):  
      [http://localhost:8080/swagger-ui.html](http://localhost:8080/api/swagger-ui.html)

---

## 📃Manual de usuario

> [!NOTE]
> Para más información de como consumir la API, ver el siguiente
> archivo [Manual de usuario](FastOrderDoc.md).
---

## 🤝 Contribución

Este es un proyecto de la
comunidad [Bytes Colaborativos](https://www.twitch.tv/bytescolaborativos). Si deseas aportar:

- Crea un _issue_ para sugerir mejoras.
- Abre un _pull request_ con tu propuesta.
- Código fuente: https://github.com/puriihuaman/fast-order

---

## 🌐 Comunidad

¿Quieres unirte o seguir las iniciativas
de [Bytes Colaborativos](https://www.twitch.tv/bytescolaborativos)?

- Twitch: [Bytes Colaborativos](https://www.twitch.tv/bytescolaborativos)
- Discord: [Bytes Colaborativos](https://discord.gg/6A8ZRerS)

## Desarrollado

*Autor: Pedro Purihuaman*

- GitHub: https://github.com/puriihuaman
- LinkedIn: https://www.linkedin.com/in/pedropurihuaman/
- Código fuente: https://github.com/puriihuaman/fast-order