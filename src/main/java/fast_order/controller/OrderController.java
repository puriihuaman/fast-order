package fast_order.controller;

import fast_order.dto.OrderTO;
import fast_order.enums.APISuccess;
import fast_order.service.OrderService;
import fast_order.utils.APIResponseData;
import fast_order.utils.APIResponseHandler;
import fast_order.annotation.SwaggerApiResponses;
import fast_order.utils.SwaggerResponseExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for order management.
 * *
 * Exposes endpoints for:
 * - Gets a list of all orders.
 * - Gets an order by means of an ID.
 * - Registering new orders in the system.
 * - Updates an existing order in the system.
 * - Cancel a pending order in the system.
 * *
 * All responses follow the standard format defined in {@link APIResponseData}.
 * *
 * @see OrderService Order management service.
 */
@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "Order", description = "Endpoints responsible for managing orders.")
public class OrderController {
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    /**
     * Gets all orders registered in the system.
     * @return ResponseEntity with a list of orders in a standardized format.
     * *
     * @see OrderService#findAllOrders()
     */
    @Operation(summary = "Order list", description = "Gets a list of all orders.")
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Orders successfully obtained.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_ALL_RESOURCE
            )
        )
    )
    @GetMapping("all")
    public ResponseEntity<APIResponseData> findAllOrders() {
        List<OrderTO> orders = orderService.findAllOrders();
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, orders);
    }
    
    /**
     * Search for a specific order by its unique ID.
     * @param id Unique order identifier (required).
     * @return ResponseEntity with data from the order found.
     * *
     * @see OrderTO Order data structure.
     * @see OrderService#findOrderById(Long) 
     */
    @Operation(
        summary = "Get an order",
        description = "Get an order through your ID.",
        parameters = @Parameter(
            name = "id",
            description = "Order ID to search for.",
            example = "10",
            required = true,
            in = ParameterIn.PATH
        )
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Order successfully obtained.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_RESOURCE
            )
        )
    )
    @GetMapping("id/{id}")
    public ResponseEntity<APIResponseData> findOrderById(@PathVariable("id") Long id) {
        OrderTO order = orderService.findOrderById(id);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, order);
    }
    
    /**
     * Create a new order in the system.
     * @param order DTO with new order data (automatically validated).
     * @return ResponseEntity with the created order.
     * *
     * @see OrderTO Order data structure.
     * @see OrderService#createOrder(OrderTO)
     */
    @Operation(
        summary = "Create an order", description = "Create an order with all the required data."
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "201",
        description = "Order successfully registered.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.CREATED_EXAMPLE
            )
        )
    )
    @PostMapping("create")
    public ResponseEntity<APIResponseData> createOrder(@Valid @RequestBody OrderTO order) {
        OrderTO createdOrder = orderService.createOrder(order);
        
        APISuccess.RESOURCE_RETRIEVED.setMessage("Order created successfully.");
        
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, createdOrder);
    }
    
    /**
     * Update an existing order.
     * @param id Unique identifier of the order to be updated.
     * @param order Updated order data.
     * @return ResponseEntity with the updated order.
     * *
     * @see OrderTO Order data structure.
     * @see OrderService#updateOrder(Long, OrderTO)
     */
    @Operation(
        summary = "Update an order",
        description = "Update a specific order.",
        parameters = @Parameter(
            name = "id",
            description = "Order ID to search for and update.",
            example = "10",
            required = true,
            in = ParameterIn.PATH
        )
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Order updated successfully.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_RESOURCE
            )
        )
    )
    @PutMapping("update/{id}")
    public ResponseEntity<APIResponseData> updateOrder(
        @PathVariable("id") Long id,
        @Valid @RequestBody OrderTO order
    )
    {
        OrderTO updatedOrder = orderService.updateOrder(id, order);
        
        APISuccess.RESOURCE_RETRIEVED.setMessage("Order updated successfully.");
        
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, updatedOrder);
    }
    
    /**
     * Cancels an existing order (status changes to CANCELLED).
     * @param id Unique identifier of the order to be cancelled.
     * @return ResponseEntity with confirmation message.
     * *
     * @see OrderService#cancelOrder(Long)
     */
    @Operation(
        summary = "Cancel order",
        description = "Cancel an order using your ID.",
        parameters = @Parameter(
            name = "id",
            description = "ID of the order to be cancelled.",
            example = "10",
            required = true,
            in = ParameterIn.PATH
        )
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Order successfully cancelled.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_RESOURCE
            )
        )
    )
    @PatchMapping("cancel/{id}")
    public ResponseEntity<APIResponseData> cancelOrder(@PathVariable("id") Long id) {
        String responseText = orderService.cancelOrder(id);
        
        APISuccess.RESOURCE_RETRIEVED.setMessage(responseText);
        
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, null);
    }
}
