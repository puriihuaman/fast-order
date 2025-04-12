package fast_order.service.use_case;

import fast_order.dto.PriceUpdateTO;
import fast_order.dto.ProductTO;

import java.util.List;

public interface ProductServiceUseCase {
    List<ProductTO> findAllProducts();
    
    ProductTO findProductById(Long id);
    
    ProductTO findProductByName(String name);
    
    ProductTO createProduct(ProductTO product);
    
    ProductTO updateProduct(Long id, ProductTO product);
    
    void deleteProduct(Long id);
    
    ProductTO updateProductPrice(Long id, PriceUpdateTO newPrice);
    
    ProductTO updateProductStock(Long id, Integer amount);
    
    void decreaseProductStock(Long id, Integer amount);
}
