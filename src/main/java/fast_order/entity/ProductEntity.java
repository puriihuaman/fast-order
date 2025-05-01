package fast_order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "PRODUCT")
@Table(name = "PRODUCTS", schema = "fast_order_schema")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true)
    private Long id;
    
    @NotNull(message = "{field.null}")
    @NotEmpty(message = "{field.empty}")
    @Size(min = 4, max = 60, message = "{product.name.size}")
    @Column(name = "name", unique = true, nullable = false, length = 60)
    private String name;
    
    @NotNull(message = "{field.null}")
    @PositiveOrZero(message = "{product.stock.positive}")
    @Min(value = 0, message = "{product.stock.min}")
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;
    
    @NotNull(message = "{field.null}")
    @Positive(message = "{product.price.positive}")
    @DecimalMin(value = "0.01", message = "{product.price.min}")
    @Column(name = "price", nullable = false)
    private Double price;
    
    @NotNull(message = "{field.null}")
    @Size(min = 10, max = 200, message = "{product.description.size}")
    @Column(name = "description", nullable = false, length = 200)
    private String description;
}
