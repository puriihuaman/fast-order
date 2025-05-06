package fast_order.utils;

import fast_order.commons.enums.APISuccess;
import fast_order.dto.PaginationTO;
import lombok.Getter;

import java.util.List;

@Getter
public class APIResponseDataPagination<T> extends APIResponseData<T> {
    private final PaginationTO pagination;
    
    public APIResponseDataPagination(APISuccess success, PaginationTO pagination, List<T> data) {
        super(success, data);
        this.pagination = pagination;
    }
}
