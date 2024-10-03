package com.szabolcs.SpringbootWebshop.Repository;

import com.szabolcs.SpringbootWebshop.ExceptionHandler.Exceptions.ProductNotFoundException;
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
        Optional<Product> product = products.stream().filter(p -> p.getId() == id).findFirst();
        if(product.isEmpty()){
            throw new ProductNotFoundException("Not found Product with id :" + id);
        }
        return product;
    }

    public Product save(Product product){
        if(product.getId() == 0){
            product.setId(getNextId());
        }
        products.add(product);
        return product;
    }

    public void deleteById(long id){
        if(products.stream().filter(product -> product.getId() == id).findFirst().isEmpty()){
            throw new ProductNotFoundException("No product to delete with id :" + id);
        }
        products.removeIf(product -> product.getId() == id);
    }

    private long getNextId(){
        return products.stream().map(Product::getId).max(Comparator.naturalOrder()).get() + 1 ;
    }
}
