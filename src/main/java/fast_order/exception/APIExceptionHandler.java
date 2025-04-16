package fast_order.exception;

import fast_order.dto.ErrorResponseTO;
import fast_order.enums.APIError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseTO> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex
    )
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> errors.put(
            error.getField(),
            error.getDefaultMessage()
        ));
        return new ResponseEntity<>(
            new ErrorResponseTO(
                APIError.INVALID_REQUEST_DATA.getTitle(),
                APIError.INVALID_REQUEST_DATA.getMessage(),
                APIError.INVALID_REQUEST_DATA.getStatus().value(),
                errors
            ), HttpStatus.BAD_REQUEST
        );
    }
    
    @ExceptionHandler(APIRequestException.class)
    public ResponseEntity<ErrorResponseTO> handleApiRequestException(final APIRequestException ex) {
        ErrorResponseTO apiException = new ErrorResponseTO(
            ex.getTitle(),
                                                       ex.getMessage(),
                                                       ex.getStatusCode().value(),
                                                       ex.getReasons()
        );
        
        return new ResponseEntity<>(apiException, ex.getStatusCode());
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseTO> handleNotFoundException() {
        return new ResponseEntity<>(
            new ErrorResponseTO(
                APIError.ENDPOINT_NOT_FOUND.getTitle(),
                APIError.ENDPOINT_NOT_FOUND.getMessage(),
                APIError.ENDPOINT_NOT_FOUND.getStatus().value(),
                null
            ), APIError.ENDPOINT_NOT_FOUND.getStatus()
        );
    }
    
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseTO> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>(
            new ErrorResponseTO(
                "Authentication failed",
                              ex.getMessage(),
                              HttpStatus.UNAUTHORIZED.value(),
                              Map.of()
            ), HttpStatus.UNAUTHORIZED
        );
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseTO> handleAccessDeniedException() {
        ErrorResponseTO error = new ErrorResponseTO(
            "Access denied",
                                                "Insufficient privileges",
                                                HttpStatus.FORBIDDEN.value(),
                                                Map.of()
        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseTO> handleAllExceptions() {
        return new ResponseEntity<>(
            new ErrorResponseTO(
                APIError.INTERNAL_SERVER_ERROR.getTitle(),
                APIError.INTERNAL_SERVER_ERROR.getMessage(),
                APIError.INTERNAL_SERVER_ERROR.getStatus().value(),
                null
            ), APIError.INTERNAL_SERVER_ERROR.getStatus()
        );
    }
}
