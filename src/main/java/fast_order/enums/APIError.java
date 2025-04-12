package fast_order.enums;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum APIError {
    INVALID_REQUEST_DATA(
        HttpStatus.BAD_REQUEST,
        "Datos inválidos",
        "Los datos solicitados contienen valores no válidos o un formato incorrecto."
    ),
    INVALID_CREDENTIALS(
        HttpStatus.UNAUTHORIZED,
        "Credenciales inválidas",
        "Nombre de usuario o contraseña incorrectos. Inténtelo de nuevo con las credenciales correctas."
    ),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request", "La solicitud no es válida."),
    BAD_FORMAT(
        HttpStatus.BAD_REQUEST,
               "Formato no válido",
               "El mensaje no tiene un formato válido."
    ),
    RECORD_NOT_FOUND(
        HttpStatus.NOT_FOUND,
                     "Recurso no encontrado",
                     "El recurso solicitado no existe."
    ),
    ENDPOINT_NOT_FOUND(
        HttpStatus.NOT_FOUND,
                       "Endpoint no encontrado",
                       "El endpoint solicitado no existe."
    ),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Error de autenticación", "La autenticación ha fallado."),
    FORBIDDEN(
        HttpStatus.FORBIDDEN,
        "Acceso no autorizado",
        "No tienes los permisos necesarios para realizar esta acción."
    ),
    INTERNAL_SERVER_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Error del servidor",
        "Se produjo un error interno inesperado en el servidor. Inténtelo de nuevo más tarde."
    ),
    METHOD_NOT_ALLOWED(
        HttpStatus.METHOD_NOT_ALLOWED,
        "Método no permitido",
        "Método HTTP no compatible con este endpoint."
    ),
    UNPROCESSABLE_ENTITY(
        HttpStatus.UNPROCESSABLE_ENTITY,
        "Entidad no procesable",
        "La solicitud estaba bien formulada, pero no se puede procesar debido a errores semánticos."
    ),
    RESOURCE_CONFLICT(HttpStatus.CONFLICT, "", ""),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "", ""),
    UNIQUE_CONSTRAINT_VIOLATION(HttpStatus.CONFLICT, "", ""),
    RESOURCE_ASSOCIATED_EXCEPTION(HttpStatus.CONFLICT, "", ""),
    DATABASE_ERROR(
        HttpStatus.INTERNAL_SERVER_ERROR,
        "Error de base de datos",
        "No se pudo completar la operación de la base de datos."
    ),
    TIMEOUT_ERROR(HttpStatus.REQUEST_TIMEOUT, "", ""),
    EXTERNAL_API_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "", "");
    
    private HttpStatus status;
    private String title;
    private String message;
    
    @JsonSetter
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    
    @JsonGetter
    public void setTitle(String title) {
        this.title = title;
    }
    
    @JsonGetter
    public void setMessage(String message) {
        this.message = message;
    }
}
