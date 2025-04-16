# Como usar la aplicación

## Requerimientos

Para el correcto funcionamiento de la aplicación necesitamos tener algunas cosas en cuenta.  
Debemos asegurarnos de que disponemos de todas las variables de entorno.   
En el repositorio encontrarás un archivo `.env.demo`, este contiene una plantilla de las
variables de entorno que debes tener.

Estas variables deberán estar reflejados en el archivo `.env` que debes crear.

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

> [!NOTE]
> Obviamente estos valores de las variables no son válidas y deberás de crear valores válidos.

## Configuración de base de datos

Previamente a este paso ya deberías de tener tu base de datos en PostgreSQL, como se indica en
el archivo [README](README.md).

En el archivo `application.properties` deberás de verificar si tus propiedades tiene como valor
las mismas variables de entorno que has definido en tu archivo `.env`.

```properties
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.jpa.database=postgresql
```

## Levantar la aplicación

Ejecutar los siguientes comandos:

```bash
# Limpiar los archivos generados en compilaciones anteriores
mvn clean

# Compilar el código y empaquetar el proyecto
mvn package

# Instalar el paquete en el repositorio local de Maven
mvn install

# Ejecutar la aplicación
# O de forma alternativa darle al botón de Run o Play
mvn spring-boot:run
```

## Ejecutar endpoints

Tenemos dos opciones:

1. Acceder a los endpoints desde Postman o cualquier otro cliente HTTP.
2. Utilizar la interfaz Swagger que viene integrada en la aplicación.

Esta es su interfaz:

[]{}

Como podemos observar, tenemos la interfaz de Swagger.

Para probar los endpoints deberemos de tener en cuenta que existen tres tipos de usuarios distintos:

- **Admin**: Persona con control total que permite crear, actualizar, buscar, así como eliminar
  cualquier producto, usuario o pedido.
- **User**: Persona que puede crear, actualizar y listar productos, usuarios y
  pedidos, que no puede eliminar ningún registro, tampoco cancelar un pedido.
- **Invited**: Persona que puede loguearse con un usuario previamente registrado por un usuario con
  rol de **ADMINISTRADOR**, también puede los productos y buscar por ID.

Al iniciar la aplicación por defecto se crea un usuario con rol de administrador.

Tu usuario tendrá los siguientes datos:

```
email: bytes@colaborativos.es
password: AdM¡N&20_25
```

Con este usuario deberás de realizar login a través de la interfaz de Swagger o Postman, en el 
siguiente endpoint: `api/auth/login`.

```bash
http://localhost:8080/api/auth/login
```

Al momento de hacer login se devolverá la siguiente información, entre la información tendrás el
token de acceso, que deberías de usar para hacer las peticiones a los diferentes endpoints.

```json
{
	"access_token": "eyJhbGciOiJIUzI1NiJ9.xxx.xxxx",
	"name": "Bytes Colaborativos",
	"role": "ADMIN"
}
```

## Endpoint disponibles en la aplicación 🔗

- Usuarios
  - Listar usuarios:
    ```bash
    http://localhost:8080/api/users/all
    ```
  - Buscar usuario por ID:
    ```bash
    http://localhost:8080/api/users/id
    ```
  - Crear usuario
    ```bash
    http://localhost:8080/api/users/create
    ```
  - Eliminar
    ```bash
    http://localhost:8080/api/users/delete/id
    ```
  - _Entre otros._
- Productos
  - Listar productos:
    ```bash
    http://localhost:8080/api/products/all
    ```
  - Buscar producto por nombre:
    ```bash
    http://localhost:8080/api/users/name
    ```
  - Crear producto:
    ```bash
    http://localhost:8080/api/users/create
    ```
  - Actualizar producto:
    ```bash
    http://localhost:8080/api/users/update/id
    ```
- Pedidos
  - Listar pedidos:
    ```bash
    http://localhost:8080/api/orders/all
    ```
  - Crear un pedido:
    ```bash
    http://localhost:8080/api/orders/create
    ```
  - Actualizar un pedido:
    ```bash
    http://localhost:8080/api/orders/update/id
    ```
  - Cancelar un pedido:
    ```bash
    http://localhost:8080/api/orders/cancel/id
    ```
- Roles
  - Listar roles:
    ```bash
    http://localhost:8080/api/roles/all
    ```