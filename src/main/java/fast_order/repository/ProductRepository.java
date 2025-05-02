package fast_order.repository;

import fast_order.entity.ProductEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findProductByName(
        @NotNull(message = "{field.null}") @NotEmpty(message = "{field.empty}")
        @Size(min = 4, max = 60, message = "{product.name.size}") String name
    );
    
    @Modifying
    @Transactional
    @Query("UPDATE PRODUCT prod SET prod.price = :price WHERE prod.id = :id")
    int updateProductPrice(@Param("id") UUID id, @Param("price") Double price);
    
    @Modifying
    @Transactional
    @Query("UPDATE PRODUCT prod SET prod.stock = prod.stock + :amount WHERE prod.id = :id")
    int increaseProductStock(@Param("id") UUID id, @Param("amount") Integer amount);
    
    @Modifying
    @Transactional
    @Query("UPDATE PRODUCT prod SET prod.stock = prod.stock - :amount WHERE prod.id = :id")
    int decreaseProductStock(@Param("id") UUID id, @Param("amount") Integer amount);
}
