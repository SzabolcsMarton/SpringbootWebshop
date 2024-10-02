package com.szabolcs.SpringbootWebshop.Service;

import com.szabolcs.SpringbootWebshop.Dto.ProductDto;
import com.szabolcs.SpringbootWebshop.Model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<Product> getAllProducts();
    Optional<Product> getProductById(long id);
    Optional<Product> createProduct(ProductDto productDto);
    boolean updateProduct(ProductDto productDto, long id);
    void deleteProduct(long id);

}
