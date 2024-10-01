package com.szabolcs.SpringbootWebshop.Service;

import com.szabolcs.SpringbootWebshop.Model.Product;

public interface IProductService {

    Product getAllProducts();
    Product getProductById(int id);
    Product createProduct(Product newProduct);
    void updateProduct(Product upDatedProduct, int id);
    void deleteProduct(int id);

}
