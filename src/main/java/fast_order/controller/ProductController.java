package fast_order.controller;

import fast_order.dto.PriceUpdateTO;
import fast_order.dto.ProductTO;
import fast_order.dto.StockUpdateTO;
import fast_order.enums.APISuccess;
import fast_order.service.ProductService;
import fast_order.utils.APIResponseData;
import fast_order.utils.APIResponseHandler;
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

import java.util.List;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ProductController {
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping("all")
    public ResponseEntity<APIResponseData> findAllProducts() {
        List<ProductTO> products = productService.findAllProducts();
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, products);
    }
    
    @GetMapping("id/{id}")
    public ResponseEntity<APIResponseData> findProductById(@PathVariable("id") Long id) {
        ProductTO product = productService.findProductById(id);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, product);
    }
    
    @GetMapping("name/{name}")
    public ResponseEntity<APIResponseData> findProductByName(@PathVariable("name") String name) {
        ProductTO product = productService.findProductByName(name);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, product);
    }
    
    @PostMapping("create")
    public ResponseEntity<APIResponseData> createProduct(@Valid @RequestBody ProductTO product) {
        ProductTO createdProduct = productService.createProduct(product);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_CREATED, createdProduct);
    }
    
    @PutMapping("update/{id}")
    public ResponseEntity<APIResponseData> updateProduct(
        @PathVariable("id") Long id,
        @Valid @RequestBody ProductTO product
    )
    {
        ProductTO updatedProduct = productService.updateProduct(id, product);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_UPDATED, updatedProduct);
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<APIResponseData> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_REMOVED, null);
    }
    
    @PatchMapping("update/price/{id}")
    public ResponseEntity<APIResponseData> updateProductPrice(
        @PathVariable("id") Long id,
        @Valid @RequestBody
        PriceUpdateTO price
    )
    {
        ProductTO updateProductPrice = productService.updateProductPrice(id, price);
        APISuccess.RESOURCE_UPDATED.setMessage("Precio del producto actualizado correctamente");
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_UPDATED, updateProductPrice);
    }
    
    @PatchMapping("update/stock/{id}")
    public ResponseEntity<APIResponseData> updateProductStock(
        @PathVariable("id") Long id,
        @Valid @RequestBody
        StockUpdateTO amount
    )
    {
        ProductTO updatedProductStock = productService.updateProductStock(id, amount.getAmount());
        APISuccess.RESOURCE_UPDATED.setMessage("Stock de producto actualizado con éxito");
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_UPDATED, updatedProductStock);
    }
}
