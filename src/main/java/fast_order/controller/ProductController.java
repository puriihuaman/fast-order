package fast_order.controller;

import fast_order.dto.PriceUpdateTO;
import fast_order.dto.ProductTO;
import fast_order.dto.StockUpdateTO;
import fast_order.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ProductController {
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping("all")
    public ResponseEntity<Object> findAllProducts() {
        return ResponseEntity.ok(productService.findAllProducts());
    }
    
    @GetMapping("id/{id}")
    public ResponseEntity<Object> findProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }
    
    @GetMapping("name/{name}")
    public ResponseEntity<Object> findProductByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(productService.findProductByName(name));
    }
    
    @PostMapping("create")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody ProductTO product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<Object> updateProduct(
        @PathVariable("id") Long id,
        @Valid @RequestBody ProductTO product
    )
    {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("update/price/{id}")
    public ResponseEntity<Object> updateProductPrice(
        @PathVariable("id") Long id,
        @Valid @RequestBody PriceUpdateTO price
    )
    {
        System.out.println("----------");
        System.out.println(id);
        System.out.println(price.getPrice());
        System.out.println("----------");
        //if (!this.checkPositiveNumber(price)) {
        //    //throw new IllegalArgumentException("Price must be a positive number");
        //    throw new IllegalArgumentException("Error: El precio deber ser un número positivo.");
        //}
        return ResponseEntity.ok(productService.updateProductPrice(id, price.getPrice()));
    }
    
    @PatchMapping("update/stock/{id}")
    public ResponseEntity<Object> updateProductStock(
        @PathVariable("id") Long id,
        @Valid @RequestBody StockUpdateTO amount
    )
    {
        //if (!this.checkPositiveNumber(amount)) {
            // throw new RuntimeException("Error: amount must be a positive integer");
            // throw new RuntimeException("Error: La cantidad debe ser mayor que 0");
        //}
        System.out.println("-----------");
        System.out.println(id);
        System.out.println(amount.getAmount());
        System.out.println("-----------");
        return ResponseEntity.ok(productService.updateProductStock(id, amount.getAmount()));
    }
    
    private Boolean checkPositiveNumber(Number amount) {
        return amount.doubleValue() >= 0;
    }
}
