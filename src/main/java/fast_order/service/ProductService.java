package fast_order.service;

import fast_order.dto.PriceUpdateTO;
import fast_order.dto.ProductTO;
import fast_order.entity.ProductEntity;
import fast_order.enums.APIError;
import fast_order.exception.APIRequestException;
import fast_order.mapper.ProductMapper;
import fast_order.repository.ProductRepository;
import fast_order.service.use_case.ProductServiceUseCase;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return productMapper.toDTOList(productRepository.findAll());
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ProductTO findProductById(Long id) {
        try {
            Optional<ProductEntity> existingProduct = productRepository.findById(id);
            
            if (existingProduct.isEmpty()) {
                APIError.RECORD_NOT_FOUND.setTitle("Producto no encontrado");
                APIError.RECORD_NOT_FOUND.setMessage(
                    "El producto al que intentas acceder no existe.");
                
                throw new APIRequestException(APIError.RECORD_NOT_FOUND);
            }
            
            return productMapper.toDTO(existingProduct.get());
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ProductTO findProductByName(String name) {
        try {
            Optional<ProductEntity> existingProduct = productRepository.findProductByName(name);
            
            if (existingProduct.isEmpty()) {
                APIError.RECORD_NOT_FOUND.setTitle("Producto no encontrado");
                APIError.RECORD_NOT_FOUND.setMessage(
                    "Producto no encontrado. Por favor verifique el nombre.");
                throw new APIRequestException(APIError.RECORD_NOT_FOUND);
            }
            
            return productMapper.toDTO(existingProduct.get());
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ProductTO createProduct(@Valid ProductTO product) {
        try {
            ProductEntity productEntity = productMapper.toEntity(product);
            ProductEntity productSaved = productRepository.save(productEntity);
            return productMapper.toDTO(productSaved);
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            Throwable cause = ex.getCause();
            Throwable rootCause = cause.getCause();
            if (cause instanceof ConstraintViolationException ||
                rootCause instanceof DataIntegrityViolationException) throw new APIRequestException(
                APIError.UNIQUE_CONSTRAINT_VIOLATION);
            else throw new APIRequestException(APIError.RESOURCE_ASSOCIATED_EXCEPTION);
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ProductTO updateProduct(Long id, ProductTO product) {
        try {
            ProductTO existingProduct = this.findProductById(id);
            
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setStock(product.getStock());
            
            ProductEntity productEntity = productMapper.toEntity(existingProduct);
            ProductEntity productUpdated = productRepository.save(productEntity);
            
            return productMapper.toDTO(productUpdated);
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            Throwable cause = ex.getCause();
            Throwable rootCause = cause.getCause();
            if (cause instanceof ConstraintViolationException ||
                rootCause instanceof DataIntegrityViolationException) throw new APIRequestException(
                APIError.UNIQUE_CONSTRAINT_VIOLATION);
            else throw new APIRequestException(APIError.RESOURCE_ASSOCIATED_EXCEPTION);
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public void deleteProduct(Long id) {
        try {
            ProductTO existingProduct = this.findProductById(id);
            
            productRepository.deleteById(existingProduct.getId());
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataIntegrityViolationException ex) {
            Throwable cause = ex.getCause();
            Throwable rootCause = cause.getCause();
            if (cause instanceof ConstraintViolationException ||
                rootCause instanceof DataIntegrityViolationException) throw new APIRequestException(
                APIError.UNIQUE_CONSTRAINT_VIOLATION);
            else throw new APIRequestException(APIError.RESOURCE_ASSOCIATED_EXCEPTION);
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ProductTO updateProductPrice(Long id, PriceUpdateTO newPrice) {
        try {
            ProductTO existingProduct = this.findProductById(id);
            
            int productUpdated = productRepository.updateProductPrice(
                existingProduct.getId(),
                newPrice.getPrice()
            );
            
            if (productUpdated == 0) {
                APIError.INTERNAL_SERVER_ERROR.setTitle("Error al actualizar");
                APIError.INTERNAL_SERVER_ERROR.setMessage(
                    "Error al actualizar el precio del producto.");
                throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
            }
            
            return this.findProductById(existingProduct.getId());
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public ProductTO updateProductStock(Long id, Integer amount) {
        try {
            ProductTO existingProduct = this.findProductById(id);
            
            int updated = productRepository.increaseProductStock(existingProduct.getId(), amount);
            
            if (updated == 0) {
                APIError.INTERNAL_SERVER_ERROR.setTitle("Error al actualizar");
                APIError.INTERNAL_SERVER_ERROR.setMessage(
                    "Error al actualizar el stock del producto.");
                throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
            }
            
            return this.findProductById(existingProduct.getId());
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Override
    public void decreaseProductStock(Long id, Integer amount) {
        try {
            ProductTO existingProduct = this.findProductById(id);
            
            int updated = productRepository.decreaseProductStock(existingProduct.getId(), amount);
            
            if (updated == 0) {
                APIError.INTERNAL_SERVER_ERROR.setTitle("Error al actualizar");
                APIError.INTERNAL_SERVER_ERROR.setMessage("Ocurrió un error al actualizar el stock.");
                throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
            }
        } catch (APIRequestException ex) {
            throw ex;
        } catch (DataAccessException ex) {
            throw new APIRequestException(APIError.DATABASE_ERROR);
        } catch (Exception ex) {
            throw new APIRequestException(APIError.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    public Boolean checkStock(ProductTO product, Integer amount) {
        return product.getStock() >= amount;
    }
}
