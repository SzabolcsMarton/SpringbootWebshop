package com.szabolcs.SpringbootWebshop.Service;

import com.szabolcs.SpringbootWebshop.Dto.ProductDto;
import com.szabolcs.SpringbootWebshop.ExceptionHandler.Exceptions.ProductNotFoundException;
import com.szabolcs.SpringbootWebshop.ExceptionHandler.Exceptions.UserNotFoundException;
import com.szabolcs.SpringbootWebshop.Model.Product;
import com.szabolcs.SpringbootWebshop.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{

    private final ProductRepository productRepository;

    private final PagedResourcesAssembler<Product> pagedResourcesAssembler;

    public ProductService(ProductRepository productrepo, PagedResourcesAssembler<Product> pagedResourcesAssembler) {
        this.productRepository = productrepo;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    public static Product mapProductDtoToProduct(Product product, ProductDto productDto) {
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setDescription(productDto.description());
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new UserNotFoundException("No products at all");
        }
        return products;
    }

    @Override
    public Product getProductById(long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("No product found with id :" + id);
        }
        return product.get();
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        Product newProduct = new Product();
        mapProductDtoToProduct(newProduct, productDto);
        return productRepository.save(newProduct);
    }

    @Override
    public void updateProduct(ProductDto productDto, long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("No product to update with id :" + id);
        }
        productRepository.save(mapProductDtoToProduct(product.get(), productDto));
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("No product to delete with id :" + id);
        }
        productRepository.deleteById(id);
    }

    //not in the interface yet methods


    public PagedModel<EntityModel<Product>> gelAllProductPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        System.out.println(pageable);
        Page<Product> pageResult = productRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(pageResult);
    }

    public PagedModel<EntityModel<Product>> gelAllProductPaginatedSortedBy(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Product> pageResult = productRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(pageResult);
    }

    public List<Product> getProductsByPriceRange(double min, double max) {
        List<Product> products = productRepository.findProductByPriceBetween(min, max);
        if(products.isEmpty()){
            throw new ProductNotFoundException("No products between price: " + min + " and " + max);
        }
       return products;
    }

    public List<Product> getProductsByNamePart(String namePart) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(namePart);
        if(products.isEmpty()){
            throw new ProductNotFoundException("No products with name: " + namePart);
        }
        return products;
    }

    public List<Product> getProductsByDescriptionPart(String descriptionPart) {
        List<Product> products = productRepository.findByDescriptionContainingIgnoreCase(descriptionPart);
        if(products.isEmpty()){
            throw new ProductNotFoundException("No products with description: " + descriptionPart);
        }
        return products;
    }



}
