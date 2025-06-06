package fast_order.service.use_case;

import fast_order.dto.PriceUpdateTO;
import fast_order.dto.ProductTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface ProductServiceUseCase {
    Page<ProductTO> findAllProducts(Pageable pageable, Map<String, String> keywords);
    
    ProductTO findProductById(UUID id);
    
    ProductTO findProductByName(String name);
    
    ProductTO createProduct(ProductTO product);
    
    ProductTO updateProduct(UUID id, ProductTO product);
    
    void deleteProduct(UUID id);
    
    ProductTO updateProductPrice(UUID id, PriceUpdateTO newPrice);
    
    ProductTO updateProductStock(UUID id, Integer amount);
    
    void decreaseProductStock(UUID id, Integer amount);
}
