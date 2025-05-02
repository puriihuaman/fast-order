package fast_order.service.use_case;

import fast_order.dto.PriceUpdateTO;
import fast_order.dto.ProductTO;

import java.util.List;
import java.util.UUID;

public interface ProductServiceUseCase {
    List<ProductTO> findAllProducts();
    
    ProductTO findProductById(UUID id);
    
    ProductTO findProductByName(String name);
    
    ProductTO createProduct(ProductTO product);
    
    ProductTO updateProduct(UUID id, ProductTO product);
    
    void deleteProduct(UUID id);
    
    ProductTO updateProductPrice(UUID id, PriceUpdateTO newPrice);
    
    ProductTO updateProductStock(UUID id, Integer amount);
    
    void decreaseProductStock(UUID id, Integer amount);
}
