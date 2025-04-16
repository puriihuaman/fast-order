package fast_order.enums;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum APISuccess {
    RESOURCE_RETRIEVED("Successful operation.", HttpStatus.OK),
    RESOURCE_CREATED("Resource created successfully.", HttpStatus.CREATED),
    RESOURCE_UPDATED("Resource successfully updated.", HttpStatus.OK),
    RESOURCE_REMOVED("Resource successfully deleted.", HttpStatus.NO_CONTENT);
    
    private String message;
    private final HttpStatus status;
    
    @JsonSetter
    public void setMessage(String message) {
        this.message = message;
    }
}
