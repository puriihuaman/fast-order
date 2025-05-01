package fast_order.utils;

import fast_order.commons.enums.APISuccess;
import org.springframework.http.ResponseEntity;

/**
 * Utility class for building standardized API responses.
 * *
 * Provides a consistent mechanism to wrap successful operation results with appropriate metadata
 * and HTTP status codes.
 * Used across all API controllers to maintain uniform response formatting.
 * *
 * This class cannot be instantiated and contains only static methods.
 *
 * @see APIResponseData Structure of the response body.
 * @see APISuccess Predefined HTTP message source and codes
 */
public class APIResponseHandler {
    public static ResponseEntity<APIResponseData> handleResponse(APISuccess success, Object data)
    {
        APIResponseData responseData = new APIResponseData(success, data);
        return new ResponseEntity<>(responseData, success.getStatus());
    }
}
