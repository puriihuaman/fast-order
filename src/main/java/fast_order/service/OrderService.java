package fast_order.service;

import fast_order.dto.OrderTO;
import fast_order.dto.ProductTO;
import fast_order.dto.UserTO;
import fast_order.entity.OrderEntity;
import fast_order.enums.APIError;
import fast_order.enums.OrderStatus;
import fast_order.exception.APIRequestException;
import fast_order.mapper.OrderMapper;
import fast_order.mapper.ProductMapper;
import fast_order.mapper.UserMapper;
import fast_order.repository.OrderRepository;
import fast_order.service.use_case.OrderServiceUseCase;
import org.springframework.dao.DataAccessException;
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
        try {
            return orderMapper.toDTOList(orderRepository.findAll());
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public OrderTO findOrderById(Long id) {
        try {
            Optional<OrderEntity> orderExisting = orderRepository.findById(id);
            
            if (orderExisting.isEmpty()) {
                APIError.RECORD_NOT_FOUND.setTitle("Orden no encontrado");
                APIError.RECORD_NOT_FOUND.setMessage("El orden al que intentas acceder no existe.");
                throw new APIRequestException(APIError.RECORD_NOT_FOUND);
            }
            
            return orderMapper.toDTO(orderExisting.get());
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public OrderTO createOrder(OrderTO order) {
        try {
            UserTO existingUser = userService.findUserById(order.getUserId());
            ProductTO existingProduct = productService.findProductById(order.getProductId());
            
            if (existingUser == null && existingProduct == null) {
                return null;
            }
            
            Boolean hasStock = productService.checkStock(existingProduct, order.getAmount());
            if (!hasStock) {
                APIError.RESOURCE_CONFLICT.setTitle("Stock insuficiente");
                APIError.RESOURCE_CONFLICT.setMessage(
                    "Stock insuficiente para el producto solicitado.");
                
                throw new APIRequestException(APIError.RESOURCE_CONFLICT);
            }
            
            OrderEntity orderEntity = orderMapper.toEntity(order);
            
            orderEntity.setUser(userMapper.toEntity(existingUser));
            orderEntity.setProduct(productMapper.toEntity(existingProduct));
            orderEntity.setCreatedAt(LocalDateTime.now());
            orderEntity.setStatus(OrderStatus.PENDING);
            
            OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
            
            productService.decreaseProductStock(existingProduct.getId(), order.getAmount());
            
            return orderMapper.toDTO(savedOrderEntity);
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public OrderTO updateOrder(Long id, OrderTO order) {
        try {
            OrderTO existingOrder = this.findOrderById(id);
            
            UserTO existingUser = userService.findUserById(order.getUserId());
            ProductTO existingProduct = productService.findProductById(order.getProductId());
            
            if (existingUser == null && existingProduct == null) {
                return null;
            }
            
            existingOrder.setAmount(order.getAmount());
            existingOrder.setUserId(existingOrder.getUserId());
            existingOrder.setProductId(existingProduct.getId());
            existingOrder.setStatus(order.getStatus());
            existingOrder.setCreatedAt(LocalDateTime.now());
            
            OrderEntity orderEntity = orderMapper.toEntity(existingOrder);
            
            return orderMapper.toDTO(orderRepository.save(orderEntity));
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public String cancelOrder(Long id) {
        try {
            OrderTO existingOrder = this.findOrderById(id);
            ProductTO existingProduct = productService.findProductById(id);
            
            existingOrder.setStatus(OrderStatus.CANCELLED);
            int affectedRecords = orderRepository.cancelOrder(
                existingOrder.getId(),
                existingOrder.getStatus()
            );
            
            if (affectedRecords == 0) {
                APIError.RESOURCE_CONFLICT.setTitle("Error al cancelar la orden");
                APIError.RESOURCE_CONFLICT.setMessage(
                    "Ocurrió un error al momento de cancelar la orden.");
                
                throw new APIRequestException(APIError.RESOURCE_CONFLICT);
            }
            
            productService.updateProductStock(existingProduct.getId(), existingOrder.getAmount());
            
            return "La orden fue cancelada exitosamente.";
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
}
