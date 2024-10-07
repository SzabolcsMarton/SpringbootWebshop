package com.szabolcs.SpringbootWebshop.Repository;

import com.szabolcs.SpringbootWebshop.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    List<Product> findProductByPriceBetween(Double min, Double max);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByDescriptionContainingIgnoreCase(String name);
}
