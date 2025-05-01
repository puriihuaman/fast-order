package fast_order.controller;

import fast_order.dto.PriceUpdateTO;
import fast_order.dto.ProductTO;
import fast_order.dto.StockUpdateTO;
import fast_order.commons.enums.APISuccess;
import fast_order.service.ProductService;
import fast_order.utils.APIResponseData;
import fast_order.utils.APIResponseHandler;
import fast_order.commons.annotation.SwaggerApiResponses;
import fast_order.utils.SwaggerResponseExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

/**
 * REST controller for product management.
 * *
 * Exposes endpoints for:
 * - Gets a list of all products.
 * - Get a product by an ID.
 * - Obtain a product through its name.
 * - Register a new product in the system.
 * - Updates an existing product in the system.
 * - Delete a product in the system.
 * - Updates the price of a product in the system.
 * - Updates the stock of a product in the system.
 * *
 * All responses follow the standard format defined in {@link APIResponseData}.
 * *
 * @see ProductService Product management service.
 */
@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "Product", description = "Endpoints responsible for managing products.")
public class ProductController {
    private final ProductService productService;
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    /**
     * Gets all products registered in the system.
     * @return ResponseEntity with a list of products in a standardized format.
     * *
     * @see ProductService#findAllProducts()
     */
    @Operation(summary = "Product list", description = "Get a list of all products.")
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Products successfully obtained.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_ALL_RESOURCE
            )
        )
    )
    @GetMapping("all")
    public ResponseEntity<APIResponseData> findAllProducts() {
        List<ProductTO> products = productService.findAllProducts();
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, products);
    }
    
    /**
     * Search for a specific product by its unique ID.
     * @param id Unique product identifier (required).
     * @return ResponseEntity with data from the product found.
     * *
     * @see ProductTO Product data structure.
     * @see ProductService#findProductById(Long)
     */
    @Operation(
        summary = "Get a product",
        description = "Get a product through its ID.",
        parameters = @Parameter(
            name = "id",
            description = "Product ID to search for.",
            example = "10",
            required = true,
            in = ParameterIn.PATH
        )
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Successfully obtained product.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_RESOURCE
            )
        )
    )
    @GetMapping("id/{id}")
    public ResponseEntity<APIResponseData> findProductById(@PathVariable("id") Long id) {
        ProductTO product = productService.findProductById(id);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, product);
    }
    
    /**
     * Search for a specific product by its unique name.
     * @param name Product name (required).
     * @return ResponseEntity with data from the product found.
     * *
     * @see ProductTO Product data structure.
     * @see ProductService#findProductByName(String)
     */
    @Operation(
        summary = "Get a product",
        description = "Obtain a product through its name.",
        parameters = @Parameter(
            name = "name",
            description = "Name of the product to search for.",
            example = "Laptop M1",
            required = true,
            in = ParameterIn.PATH
        )
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Successfully obtained product.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_RESOURCE
            )
        )
    )
    @GetMapping("name/{name}")
    public ResponseEntity<APIResponseData> findProductByName(@PathVariable("name") String name) {
        ProductTO product = productService.findProductByName(name);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_RETRIEVED, product);
    }
    
    /**
     * Create a new product in the system.
     * @param product DTO with new product data (automatically validated).
     * @return ResponseEntity with the created product.
     * *
     * @see ProductTO Product data structure.
     * @see ProductService#createProduct(ProductTO)
     */
    @Operation(
        summary = "Create a product", description = "Create a product with all the required data."
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "201",
        description = "Product successfully registered.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.CREATED_EXAMPLE
            )
        )
    )
    @PostMapping("create")
    public ResponseEntity<APIResponseData> createProduct(@Valid @RequestBody ProductTO product) {
        ProductTO createdProduct = productService.createProduct(product);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_CREATED, createdProduct);
    }
    
    /**
     * Update an existing product.
     * @param id Unique identifier of the product to be updated.
     * @param product Updated product data.
     * @return ResponseEntity with the updated product.
     * *
     * @see ProductTO Product data structure.
     * @see ProductService#updateProduct(Long, ProductTO)
     */
    @Operation(
        summary = "Update a product",
        description = "Update a specific product.",
        parameters = @Parameter(
            name = "id",
            description = "Product ID to search for to update.",
            example = "10",
            required = true,
            in = ParameterIn.PATH
        )
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Product updated successfully.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_RESOURCE
            )
        )
    )
    @PutMapping("update/{id}")
    public ResponseEntity<APIResponseData> updateProduct(
        @PathVariable("id") Long id,
        @Valid @RequestBody ProductTO product
    )
    {
        ProductTO updatedProduct = productService.updateProduct(id, product);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_UPDATED, updatedProduct);
    }
    
    /**
     * Delete an existing product.
     * @param id Unique identifier of the product to be removed.
     * @return Empty ResponseEntity with code 204.
     * *
     * @see ProductService#deleteProduct(Long)
     */
    @Operation(
        summary = "Delete a product",
        description = "Delete a product using its ID.",
        parameters = @Parameter(
            name = "id",
            description = "Product ID to be removed.",
            example = "10",
            required = true,
            in = ParameterIn.PATH
        )
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "204",
        description = "Product successfully removed.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_DELETE_RESOURCE
            )
        )
    )
    @DeleteMapping("delete/{id}")
    public ResponseEntity<APIResponseData> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_REMOVED, null);
    }
    
    /**
     * Updates the price of an existing product.
     * @param id Unique identifier of the product to be removed.
     * @param price DTO with new price.
     * @return ResponseEntity with the updated product.
     * *
     * @see PriceUpdateTO PriceUpdate data structure.
     * @see ProductService#updateProductPrice(Long, PriceUpdateTO)
     */
    @Operation(
        summary = "Update the price of a product",
        description = "Update the current price of a product.",
        parameters = @Parameter(
            name = "id",
            description = "Product ID to update its price.",
            example = "10",
            required = true,
            in = ParameterIn.PATH
        )
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Product price updated successfully.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_RESOURCE
            )
        )
    )
    @PatchMapping("update/price/{id}")
    public ResponseEntity<APIResponseData> updateProductPrice(
        @PathVariable("id") Long id,
        @Valid @RequestBody
        PriceUpdateTO price
    )
    {
        ProductTO updateProductPrice = productService.updateProductPrice(id, price);
        APISuccess.RESOURCE_UPDATED.setMessage("Product price updated successfully.");
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_UPDATED, updateProductPrice);
    }
    
    /**
     * Increases the available stock of a product.
     * @param id Unique identifier of the product to be removed.
     * @param amount DTO with quantity to be increased.
     * @return ResponseEntity with the updated product
     * *
     * @see StockUpdateTO StockUpdate data structure.
     * @see ProductService#updateProductStock(Long, Integer)
     */
    @Operation(
        summary = "Increase the stock of a product",
        description = "Update the current stock of a product.",
        parameters = @Parameter(
            name = "id",
            description = "Product ID to increase stock.",
            example = "10",
            required = true,
            in = ParameterIn.PATH
        )
    )
    @SwaggerApiResponses
    @ApiResponse(
        responseCode = "200",
        description = "Product stock successfully increased.",
        content = @Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = APIResponseData.class),
            examples = @ExampleObject(
                value = SwaggerResponseExample.EXAMPLE_GET_RESOURCE
            )
        )
    )
    @PatchMapping("update/stock/{id}")
    public ResponseEntity<APIResponseData> updateProductStock(
        @PathVariable("id") Long id,
        @Valid @RequestBody StockUpdateTO amount
    )
    {
        ProductTO updatedProductStock = productService.updateProductStock(id, amount.amount());
        APISuccess.RESOURCE_UPDATED.setMessage("Product stock successfully increased.");
        return APIResponseHandler.handleResponse(APISuccess.RESOURCE_UPDATED, updatedProductStock);
    }
}
