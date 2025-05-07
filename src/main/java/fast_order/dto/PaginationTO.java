package fast_order.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Record representing pagination information for API responses.
 * @param pageNumber 0-based page number.
 * @param pageSize Number of items per page.
 * @param totalElements Total number of items in the collection.
 * @param totalPages Total number of pages in the collection.
 * @param first Indicator if the current page is the first.
 * @param last Indicator if the current page is the last one.
 * @param numberOfElements Total number of items on the current page.
 * @param sorted Indicator if the collection is in ascending order.
 * @param unsorted Indicator if the collection is in descending order.
 * *
 * It is typically included in API responses that return paginated data.
 */
public record PaginationTO(
    @Schema(description = "The current page number (0-indexed).", example = "0")
    Integer pageNumber,
    
    @Schema(description = "The number of elements per page.", example = "20")
    Integer pageSize,
    
    @Schema(description = "The total number of elements available across all pages.", example = "100")
    Long totalElements,
    
    @Schema(description = "The total number of pages available.", example = "10")
    Integer totalPages,
    
    @Schema(description = "Indicates if the current page is the first page.", example = "true")
    Boolean first,
    
    @Schema(description = "Indicates if the current page is the last page.", example = "false")
    Boolean last,
    
    @Schema(description = "The number of elements in the current page.", example = "10")
    Integer numberOfElements,
    
    @Schema(description = "Indicates if the result is sorted.", example = "false")
    Boolean sorted,
    
    @Schema(description = "Indicates if the result is unsorted.", example = "true")
    Boolean unsorted
) {}
