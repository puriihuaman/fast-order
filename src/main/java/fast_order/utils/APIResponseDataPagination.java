package fast_order.utils;

import fast_order.commons.enums.APISuccess;
import fast_order.dto.PaginationTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(
    name = "APIResponseDataPagination",
    description = """
      DTO representing a standard successful API response with pagination.
      It contains the operation result, status, and response metadata.
    """,
    example = """
           {
             "pagination": {
               "pageNumber": 0,
               "pageSize": 20,
               "totalElements": 23,
               "totalPages": 2,
               "first": true,
               "last": false,
               "numberOfElements": 20,
               "sorted": false,
               "unsorted": true
             },
             "data": {
               "name": "Jorge Suarez",
               "email": "jorge@gmail.com",
               "password": "XSS.XSS.XSS....XSS",
               "signUpDate": "2025-04-12",
               "totalSpent": 150.5,
               "roleId": 2
             },
             "hasError": false,
             "message": "Resource successfully recovered.",
             "statusCode": 200,
             "timestamp": "2025-04-13T15:42:00"
           }
       """
)
@Getter
public class APIResponseDataPagination<T> extends APIResponseData<T> {
    private final PaginationTO pagination;
    
    public APIResponseDataPagination(APISuccess success, PaginationTO pagination, List<T> data) {
        super(success, data);
        this.pagination = pagination;
    }
}
