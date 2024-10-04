package com.szabolcs.SpringbootWebshop.Repository;


import com.szabolcs.SpringbootWebshop.Model.Product;
import com.szabolcs.SpringbootWebshop.Utils.FakerGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class FakeProductRepo {

    List<Product> products = new ArrayList<>();

    @PostConstruct
    public void initialFakeDataLoader() {
        products.addAll(FakerGenerator.createProduct(10));
    }

    public List<Product> findAll(){
        return products;
    }

    public Optional<Product> findById(long id){
        return products.stream().filter(p -> p.getId() == id).findFirst();
    }

    public Product save(Product product){
        if(product.getId() == 0){
            product.setId(this.getNextId());
        }
        products.add(product);
        return product;
    }

    public void deleteById(Product product){
        products.remove(product);
    }

    private long getNextId(){
        return products.stream().map(Product::getId).max(Comparator.naturalOrder()).get() + 1 ;
    }
}
