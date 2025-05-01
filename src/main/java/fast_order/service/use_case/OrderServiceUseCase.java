package fast_order.service.use_case;

import fast_order.dto.OrderTO;

import java.util.List;

public interface OrderServiceUseCase {
    List<OrderTO> findAllOrders();
    
    OrderTO findOrderById(Long id);
    
    OrderTO createOrder(OrderTO order);
    
    OrderTO updateOrder(Long id, OrderTO order);
    
    String cancelOrder(Long id);
}
