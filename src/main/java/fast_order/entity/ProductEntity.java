package fast_order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name = "PRODUCT")
@Table(name = "PRODUCTS", schema = "fast_order_schema")
@Data
public class ProductEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "stock", nullable = false)
	private Double stock;

	@Column(name = "price", nullable = false)
	private Double price;

	@Column(name = "description", nullable = false)
	private String description;
}
