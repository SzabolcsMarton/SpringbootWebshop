package com.szabolcs.SpringbootWebshop.Repository;

import com.szabolcs.SpringbootWebshop.Model.Product;
import com.szabolcs.SpringbootWebshop.Utils.FakerGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FakeProductRepo {

    List<Product> products = new ArrayList<>();

    @PostConstruct
    public void initialFakeDataLoader() {
        products.addAll(FakerGenerator.createProduct(10));
        //System.out.println(products);
    }

    public List<Product> findAll(){
        return products;
    }

    public Optional<Product> findById(long id){
        return products.stream().filter(product -> product.getId() == id).findFirst();
    }

    public Product save(Product product){
        products.add(product);
        return product;
    }

    public void deleteById(long id){
        products.removeIf(product -> product.getId() == id);
    }
}
