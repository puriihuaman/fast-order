package fast_order.exception;

import fast_order.commons.enums.APIError;
import fast_order.dto.ErrorResponseTO;
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
            ErrorResponseTO.builder()
                           .title(APIError.INVALID_REQUEST_DATA.getTitle())
                           .message(APIError.INVALID_REQUEST_DATA.getMessage())
                           .statusCode(APIError.INVALID_REQUEST_DATA.getStatus().value())
                           .reasons(errors)
                           .build(), HttpStatus.BAD_REQUEST
        );
    }
    
    @ExceptionHandler(APIRequestException.class)
    public ResponseEntity<ErrorResponseTO> handleApiRequestException(final APIRequestException ex) {
        return new ResponseEntity<>(
            ErrorResponseTO.builder()
                           .title(ex.getTitle())
                           .message(ex.getMessage())
                           .statusCode(ex.getStatusCode().value())
                           .reasons(ex.getReasons())
                           .build(), ex.getStatusCode()
        );
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseTO> handleNotFoundException() {
        return new ResponseEntity<>(
            ErrorResponseTO.builder()
                           .title(APIError.ENDPOINT_NOT_FOUND.getTitle())
                           .message(APIError.ENDPOINT_NOT_FOUND.getMessage())
                           .statusCode(APIError.ENDPOINT_NOT_FOUND.getStatus().value())
                           .reasons(null)
                           .build(), APIError.ENDPOINT_NOT_FOUND.getStatus()
        );
    }
    
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseTO> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>(
            ErrorResponseTO.builder()
                           .title("Authentication failed")
                           .message(ex.getMessage())
                           .statusCode(HttpStatus.UNAUTHORIZED.value())
                           .reasons(null)
                           .build(), HttpStatus.UNAUTHORIZED
        );
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseTO> handleAccessDeniedException() {
        return new ResponseEntity<>(
            ErrorResponseTO.builder()
                           .title(APIError.FORBIDDEN.getTitle())
                           .message(APIError.FORBIDDEN.getMessage())
                           .statusCode(APIError.FORBIDDEN.getStatus().value())
                           .reasons(null)
                           .build(), APIError.FORBIDDEN.getStatus()
        );
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseTO> handleAllExceptions() {
        return new ResponseEntity<>(
            ErrorResponseTO.builder()
                           .title(APIError.INTERNAL_SERVER_ERROR.getTitle())
                           .message(APIError.INTERNAL_SERVER_ERROR.getMessage())
                           .statusCode(APIError.INTERNAL_SERVER_ERROR.getStatus().value())
                           .reasons(null)
                           .build(), APIError.INTERNAL_SERVER_ERROR.getStatus()
        );
    }
}
