package fast_order.utils;

import fast_order.enums.APISuccess;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class APIResponseData {
    private final Object data;
    private final Boolean hasError = false;
    private final String message;
    private final Integer statusCode;
    private final LocalDateTime timestamp = LocalDateTime.now();
    
    public APIResponseData(APISuccess success, Object data) {
        this.data = data;
        this.message = success.getMessage();
        this.statusCode = success.getStatus().value();
    }
}
