package com.szabolcs.SpringbootWebshop.Service;

import com.szabolcs.SpringbootWebshop.Dto.ProductDto;
import com.szabolcs.SpringbootWebshop.ExceptionHandler.Exceptions.ProductNotFoundException;
import com.szabolcs.SpringbootWebshop.Model.Product;
import com.szabolcs.SpringbootWebshop.Repository.FakeProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Productservice implements IProductService{

    private final FakeProductRepo fakeProductRepo;

    public Productservice(FakeProductRepo fakeProductRepo) {
        this.fakeProductRepo = fakeProductRepo;
    }

    @Override
    public List<Product> getAllProducts() {
        return fakeProductRepo.findAll();
    }

    @Override
    public Optional<Product> getProductById(long id) {
        return fakeProductRepo.findById(id);
    }

    @Override
    public Optional<Product> createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setDescription(productDto.description());
        return Optional.of(fakeProductRepo.save(product));
    }

    @Override
    public void updateProduct(ProductDto productDto, long id) {
        Optional<Product> product = fakeProductRepo.findById(id);
        fakeProductRepo.deleteById(product.get().getId());
        fakeProductRepo.save(mapProductToDtoProduct(product.get(), productDto));

    }

    @Override
    public void deleteProduct(long id) {
        fakeProductRepo.deleteById(id);
    }

    private Product mapProductToDtoProduct(Product product, ProductDto productDto) {
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setDescription(productDto.description());
        return product;
    }


}
