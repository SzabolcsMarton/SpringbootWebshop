package com.szabolcs.SpringbootWebshop.Utils;

import com.github.javafaker.Faker;
import com.szabolcs.SpringbootWebshop.Model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class FakerGenerator {

    private static final Faker huFaker = new Faker(new Locale("hu-HU"));

    public static List<Product> createProduct(int amount) {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Product product = new Product();
            product.setId(i + 1);
            product.setName(huFaker.commerce().productName());
            product.setPrice(huFaker.number().randomDouble(2,1,1000));
            product.setDescription(huFaker.lorem().sentence(10));
            products.add(product);
        }
        return products;
    }

//    public static List<Product> createDummyProducts(int amount) {
//        List<Product> products = new ArrayList<>();
//        for (int i = 0; i < amount; i++) {
//            Product entity = new Product(faker.commerce().productName(), faker.lorem().sentence(4), faker.number().randomDouble(2, 100, 1000));
//            entity.getTags().add(faker.commerce().material());
//            products.add(entity);
//        }
//        return products;
//    }
}
