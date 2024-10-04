package com.szabolcs.SpringbootWebshop.Controller;

import com.szabolcs.SpringbootWebshop.Dto.ProductDto;
import com.szabolcs.SpringbootWebshop.Model.Product;
import com.szabolcs.SpringbootWebshop.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productservice;

    public ProductController(ProductService productservice) {
        this.productservice = productservice;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productservice.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productservice.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping
    public ResponseEntity<Product> addEmployee(@RequestBody ProductDto productDto) {
        Product product = productservice.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDto productDto, @PathVariable long id) {
        productservice.updateProduct(productDto, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneProductById(@PathVariable long id) {
        productservice.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
