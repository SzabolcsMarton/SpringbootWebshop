package com.szabolcs.SpringbootWebshop.Service;

import com.szabolcs.SpringbootWebshop.Dto.ProductDto;
import com.szabolcs.SpringbootWebshop.ExceptionHandler.Exceptions.ProductNotFoundException;
import com.szabolcs.SpringbootWebshop.Model.Product;
import com.szabolcs.SpringbootWebshop.Repository.ProductRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{

    private final ProductRepository productrepo;

    public ProductService(ProductRepository productrepo) {
        this.productrepo = productrepo;
    }

    public static Product mapProductDtoToProduct(Product product, ProductDto productDto) {
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setDescription(productDto.description());
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productrepo.findAll();
    }

    @Override
    public Product getProductById(long id) {
        Optional<Product> product = productrepo.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("No product found with id :" + id);
        }
        return product.get();
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        Product newProduct = new Product();
        mapProductDtoToProduct(newProduct, productDto);
        return productrepo.save(newProduct);
    }

    @Override
    public void updateProduct(ProductDto productDto, long id) {
        Optional<Product> product = productrepo.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("No product to update with id :" + id);
        }
        productrepo.save(mapProductDtoToProduct(product.get(), productDto));
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> product = productrepo.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("No product to delete with id :" + id);
        }
        productrepo.deleteById(id);
    }

    public List<Product> getProductsByPriceRange(double min, double max) {
        List<Product> products = productrepo.findProductByPriceBetween(min, max);
        if(products.isEmpty()){
            throw new ProductNotFoundException("No products with between price: " + min + " and " + max);
        }
       return products;
    }

    public List<Product> getProductsByNamePart(String namePart) {
        List<Product> products = productrepo.findByNameContainingIgnoreCase(namePart);
        if(products.isEmpty()){
            throw new ProductNotFoundException("No products with name: " + namePart);
        }
        return products;
    }

    public List<Product> getProductsByDescriptionPart(String descriptionPart) {
        List<Product> products = productrepo.findByDescriptionContainingIgnoreCase(descriptionPart);
        if(products.isEmpty()){
            throw new ProductNotFoundException("No products with description: " + descriptionPart);
        }
        return products;
    }


}
