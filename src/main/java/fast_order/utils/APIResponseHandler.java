package fast_order.utils;

import fast_order.enums.APISuccess;
import org.springframework.http.ResponseEntity;

public class APIResponseHandler {
    public static ResponseEntity<APIResponseData> handleResponse(APISuccess success, Object data)
    {
        APIResponseData responseData = new APIResponseData(success, data);
        return new ResponseEntity<>(responseData, success.getStatus());
    }
}
