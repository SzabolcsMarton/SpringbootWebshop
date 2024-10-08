package com.szabolcs.SpringbootWebshop.Repository;

import com.szabolcs.SpringbootWebshop.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;


import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {

    @Override
    @NonNull
    Page<Product> findAll(@NonNull Pageable pageable);

    List<Product> findProductByPriceBetween(Double min, Double max);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByDescriptionContainingIgnoreCase(String name);
}
