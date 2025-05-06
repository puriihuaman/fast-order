package fast_order.utils;

import fast_order.commons.enums.APISuccess;
import fast_order.dto.PaginationTO;
import org.springframework.data.domain.Page;
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
    public static <T> ResponseEntity<APIResponseData<T>> handleResponse(APISuccess success, T data)
    {
        APIResponseData<T> responseData = new APIResponseData<>(success, data);
        return new ResponseEntity<>(responseData, success.getStatus());
    }
    
    public static <T> ResponseEntity<APIResponseDataPagination<T>> handleResponse(
        APISuccess success,
        Page<T> page
    )
    {
        PaginationTO pagination = new PaginationTO(
            page.getPageable().getPageNumber(),
            page.getPageable().getPageSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast(),
            page.getNumberOfElements(),
            page.getSort().isSorted(),
            page.getSort().isUnsorted()
        );
        APIResponseDataPagination<T> responseData = new APIResponseDataPagination<>(
            success,
            pagination,
            page.getContent()
        );
        return new ResponseEntity<>(responseData, success.getStatus());
    }
}
