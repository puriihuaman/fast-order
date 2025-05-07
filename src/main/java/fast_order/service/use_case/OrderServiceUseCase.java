package fast_order.service.use_case;

import fast_order.dto.OrderTO;

import java.util.List;
import java.util.UUID;

public interface OrderServiceUseCase {
    List<OrderTO> findAllOrders();
    
    OrderTO findOrderById(UUID id);
    
    OrderTO createOrder(OrderTO order);
    
    OrderTO updateOrder(UUID id, OrderTO order);
    
    String cancelOrder(UUID id);
}
