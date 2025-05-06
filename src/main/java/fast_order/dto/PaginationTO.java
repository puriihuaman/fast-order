package fast_order.dto;

public record PaginationTO(
    Integer pageNumber,
    Integer pageSize,
    Long totalElements,
    Integer totalPages,
    Boolean first,
    Boolean last,
    Integer numberOfElements,
    Boolean sorted,
    Boolean unsorted
) {}
