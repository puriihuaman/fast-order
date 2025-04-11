package fast_order.service;

import fast_order.dto.ProductTO;
import fast_order.entity.ProductEntity;
import fast_order.mapper.ProductMapper;
import fast_order.repository.ProductRepository;
import fast_order.service.use_case.ProductServiceUseCase;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceUseCase {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }
    
    @Override
    public List<ProductTO> findAllProducts() {
        return productMapper.toDTOList(productRepository.findAll());
    }
    
    @Override
    public ProductTO findProductById(Long id) {
        Optional<ProductEntity> existingProduct = productRepository.findById(id);
        
        if (existingProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        
        return productMapper.toDTO(existingProduct.get());
    }
    
    @Override
    public ProductTO findProductByName(String name) {
        Optional<ProductEntity> existingProduct = productRepository.findProductByName(name);
        if (existingProduct.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        
        return productMapper.toDTO(existingProduct.get());
    }
    
    @Override
    public ProductTO createProduct(@Valid ProductTO product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        ProductEntity productSaved = productRepository.save(productEntity);
        return productMapper.toDTO(productSaved);
    }
    
    @Override
    public ProductTO updateProduct(Long id, ProductTO product) {
        ProductTO existingProduct = this.findProductById(id);
        
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        
        ProductEntity productEntity = productMapper.toEntity(existingProduct);
        ProductEntity productUpdated = productRepository.save(productEntity);
        
        return productMapper.toDTO(productUpdated);
    }
    
    @Override
    public void deleteProduct(Long id) {
        ProductTO existingProduct = this.findProductById(id);
        
        productRepository.deleteById(existingProduct.getId());
    }
    
    @Override
    public ProductTO updateProductPrice(Long id, Double price) {
        ProductTO existingProduct = this.findProductById(id);
        
        int productUpdated = productRepository.updateProductPrice(
            existingProduct.getId(),
            price
        );
        
        if (productUpdated == 0) {
            throw new RuntimeException("Product price update failed");
        }
        
        return this.findProductById(existingProduct.getId());
    }
    
    @Override
    public ProductTO updateProductStock(Long id, Integer amount) {
        ProductTO existingProduct = this.findProductById(id);
        
        int updated = productRepository.increaseProductStock(existingProduct.getId(), amount);
        
        if (updated == 0) {
            throw new RuntimeException("Product stock update failed");
        }
        
        return this.findProductById(existingProduct.getId());
    }
    
    @Override
    public int decreaseProductStock(Long id, Integer amount) {
        ProductTO existingProduct = this.findProductById(id);
        
        int updated = productRepository.decreaseProductStock(existingProduct.getId(), amount);
        
        if (updated == 0) {
            throw new RuntimeException("Product stock update failed");
        }
        
        return 1;
    }
    
    
    public Boolean checkStock(ProductTO product, Integer amount) {
        return product.getStock() >= amount;
    }
}
