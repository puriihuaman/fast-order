package fast_order.service.use_case;

import fast_order.dto.OrderTO;

import java.util.List;

public interface OrderServiceUseCase {
    List<OrderTO> findAllOrders();
    
    OrderTO findOrderById(Long id);
    
    //Crear un pedido: validar existencia de productos y stock suficiente.
    OrderTO createOrder(OrderTO order);
    
    // Modificar pedido: ajustar cantidades y stock
    OrderTO updateOrder(Long id, OrderTO order);
    
    // Cancelar pedido: restaurar stock.
    String cancelOrder(Long id);
}
