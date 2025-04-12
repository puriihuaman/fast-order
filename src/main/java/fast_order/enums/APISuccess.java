package fast_order.enums;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum APISuccess {
    RESOURCE_RETRIEVED("Operación exitosa.", HttpStatus.OK),
    RESOURCE_CREATED("Recurso creado satisfactoriamente.", HttpStatus.CREATED),
    RESOURCE_UPDATED("Recurso actualizado con éxito.", HttpStatus.OK),
    RESOURCE_REMOVED("Recurso eliminado.", HttpStatus.NO_CONTENT);
    
    private String message;
    private final HttpStatus status;
    
    @JsonSetter
    public void setMessage(String message) {
        this.message = message;
    }
}
