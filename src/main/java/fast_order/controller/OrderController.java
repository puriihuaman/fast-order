package fast_order.controller;

import fast_order.dto.OrderTO;
import fast_order.enums.APISuccess;
import fast_order.service.OrderService;
import fast_order.utils.APIResponseData;
import fast_order.utils.APIResponseHandler;
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

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class OrderController {
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping("all")
    public ResponseEntity<APIResponseData> findAllOrders() {
        List<OrderTO> orders = orderService.findAllOrders();
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, orders);
    }
    
    @GetMapping("id/{id}")
    public ResponseEntity<APIResponseData> findOrderById(@PathVariable("id") Long id) {
        OrderTO order = orderService.findOrderById(id);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, order);
    }
    
    @PostMapping("create")
    public ResponseEntity<APIResponseData> createOrder(@Valid @RequestBody OrderTO order) {
        OrderTO createdOrder = orderService.createOrder(order);
        
        APISuccess.RESOURCE_RETRIEVED.setMessage("Order created successfully.");
        
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, createdOrder);
    }
    
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
    
    @PatchMapping("cancel/{id}")
    public ResponseEntity<APIResponseData> cancelOrder(@PathVariable("id") Long id) {
        String responseText = orderService.cancelOrder(id);
        
        APISuccess.RESOURCE_RETRIEVED.setMessage(responseText);
        
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, null);
    }
}
