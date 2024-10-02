package com.szabolcs.SpringbootWebshop.Service;

import com.szabolcs.SpringbootWebshop.Model.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAllProducts();
    Product getProductById(int id);
    Product createProduct(Product newProduct);
    void updateProduct(Product upDatedProduct, int id);
    void deleteProduct(int id);

}
