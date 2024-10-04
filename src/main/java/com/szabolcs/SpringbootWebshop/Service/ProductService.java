package com.szabolcs.SpringbootWebshop.Service;

import com.szabolcs.SpringbootWebshop.Dto.ProductDto;
import com.szabolcs.SpringbootWebshop.ExceptionHandler.Exceptions.ProductNotFoundException;
import com.szabolcs.SpringbootWebshop.Model.Product;
import com.szabolcs.SpringbootWebshop.Repository.FakeProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{

    private final FakeProductRepo fakeProductRepo;

    public ProductService(FakeProductRepo fakeProductRepo) {
        this.fakeProductRepo = fakeProductRepo;
    }

    @Override
    public List<Product> getAllProducts() {
        return fakeProductRepo.findAll();
    }

    @Override
    public Product getProductById(long id) {
        Optional<Product> product = fakeProductRepo.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("No product to found with id :" + id);
        }
        return product.get();
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        Product newProduct = new Product();
        mapProductToDtoProduct(newProduct, productDto);
        return fakeProductRepo.save(newProduct);
    }

    @Override
    public void updateProduct(ProductDto productDto, long id) {
        Optional<Product> product = fakeProductRepo.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("No product to update with id :" + id);
        }
        fakeProductRepo.deleteById(product.get());
        fakeProductRepo.save(mapProductToDtoProduct(product.get(), productDto));

    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> product = fakeProductRepo.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("No product to delete with id :" + id);
        }
        fakeProductRepo.deleteById(product.get());
    }


    public Product mapProductToDtoProduct(Product product, ProductDto productDto) {
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setDescription(productDto.description());
        return product;
    }


}
