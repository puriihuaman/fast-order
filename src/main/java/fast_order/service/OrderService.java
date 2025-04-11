package fast_order.service;

import fast_order.dto.OrderTO;
import fast_order.dto.ProductTO;
import fast_order.dto.UserTO;
import fast_order.entity.OrderEntity;
import fast_order.enums.OrderStatus;
import fast_order.mapper.OrderMapper;
import fast_order.mapper.ProductMapper;
import fast_order.mapper.UserMapper;
import fast_order.repository.OrderRepository;
import fast_order.service.use_case.OrderServiceUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderServiceUseCase {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserService userService;
    private final ProductService productService;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    
    public OrderService(
        OrderRepository orderRepository,
        OrderMapper orderMapper,
        UserService userService,
        ProductService productService,
        UserMapper userMapper,
        ProductMapper productMapper
    )
    {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.productService = productService;
        this.userMapper = userMapper;
        this.productMapper = productMapper;
    }
    
    @Override
    public List<OrderTO> findAllOrders() {
        return orderMapper.toDTOList(orderRepository.findAll());
    }
    
    @Override
    public OrderTO findOrderById(Long id) {
        Optional<OrderEntity> orderExisting = orderRepository.findById(id);
        
        if (orderExisting.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        
        return orderMapper.toDTO(orderExisting.get());
    }
    
    @Override
    public OrderTO createOrder(OrderTO order) {
        // Buscar usuario, producto
        UserTO existingUser = userService.findUserById(order.getUserId());
        ProductTO existingProduct = productService.findProductById(order.getProductId());
        
        // Validar existencia del producto, y usuario
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        
        // Validar stock suficiente
        Boolean hasStock = productService.checkStock(existingProduct, order.getAmount());
        if (!hasStock) {
            System.out.println("-----");
            System.out.println("No hay stock suficiente.");
            System.out.println("-----");
            throw new RuntimeException("Stock not enough");
        }
        
        OrderEntity orderEntity = orderMapper.toEntity(order);
        
        orderEntity.setUser(userMapper.toEntity(existingUser));
        orderEntity.setProduct(productMapper.toEntity(existingProduct));
        orderEntity.setCreatedAt(LocalDateTime.now());
        orderEntity.setStatus(OrderStatus.PENDING);
        
        // Guardar
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        
        // Actualizar stock de producto
        productService.decreaseProductStock(existingProduct.getId(), order.getAmount());
        
        // Retornar
        return orderMapper.toDTO(savedOrderEntity);
    }
    
    @Override
    public OrderTO updateOrder(Long id, OrderTO order) {
        OrderTO existingOrder = this.findOrderById(id);
        
        UserTO existingUser = userService.findUserById(order.getUserId());
        ProductTO existingProduct = productService.findProductById(order.getProductId());
        
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        
        if (existingProduct == null) {
            throw new RuntimeException("Order not found");
        }
        
        existingOrder.setAmount(order.getAmount());
        existingOrder.setUserId(existingOrder.getUserId());
        existingOrder.setProductId(existingProduct.getId());
        existingOrder.setStatus(order.getStatus());
        existingOrder.setCreatedAt(LocalDateTime.now());
        
        OrderEntity orderEntity = orderMapper.toEntity(existingOrder);
        
        return orderMapper.toDTO(orderRepository.save(orderEntity));
    }
    
    @Override
    public String cancelOrder(Long id) {
        // buscar la orden
        OrderTO existingOrder = this.findOrderById(id);
        // buscar el producto
        ProductTO existingProduct = productService.findProductById(id);
        
        // actualizar estado de la orden
        existingOrder.setStatus(OrderStatus.CANCELLED);
        int affectedRecords = orderRepository.cancelOrder(
            existingOrder.getId(),
            existingOrder.getStatus()
        );
        
        if (affectedRecords == 0) {
            System.out.println("La orden no se pudo cancelar");
            throw new RuntimeException("The order was not canceled.");
        }
        // restaurar el stock del producto
        ProductTO updatedProduct = productService.updateProductStock(
            existingProduct.getId(),
            existingOrder.getAmount()
        );
        System.out.println("--------------------");
        System.out.println(updatedProduct.getId());
        System.out.println(updatedProduct.getStock());
        System.out.println("--------------------");
        
        return "Order cancelled";
    }
}
