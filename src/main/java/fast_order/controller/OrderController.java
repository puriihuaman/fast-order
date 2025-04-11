package fast_order.controller;

import fast_order.dto.OrderTO;
import fast_order.service.OrderService;
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

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class OrderController {
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping("all")
    public ResponseEntity<Object> findAllOrders() {
        return ResponseEntity.ok(orderService.findAllOrders());
    }
    
    @GetMapping("id/{id}")
    public ResponseEntity<Object> findOrderById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }
    
    @PostMapping("create")
    public ResponseEntity<Object> createOrder(@Valid @RequestBody OrderTO order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<Object> updateOrder(
        @PathVariable("id") Long id,
        @Valid @RequestBody OrderTO order
    )
    {
        return ResponseEntity.ok(orderService.updateOrder(id, order));
    }
    
    @PatchMapping("cancel/{id}")
    public ResponseEntity<Object> cancelOrder(@PathVariable("id") Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
}
