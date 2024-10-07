package com.szabolcs.SpringbootWebshop.Service;

import com.szabolcs.SpringbootWebshop.Dto.ProductDto;
import com.szabolcs.SpringbootWebshop.ExceptionHandler.Exceptions.ProductNotFoundException;
import com.szabolcs.SpringbootWebshop.Model.Product;
import com.szabolcs.SpringbootWebshop.Repository.ProductRepository;
import com.szabolcs.SpringbootWebshop.Utils.FakerGenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    List<Product> products = new ArrayList<>();

    @BeforeEach
    public void initialFakeDataLoader() {
        products.addAll(FakerGenerator.createProduct(5));
    }

    @Mock
    ProductRepository mockRepo;

    @InjectMocks
    ProductService productservice;

    //*** findAll test ***
    @Test
    public void find_all_products_test_should_return_5_products() {
        when(mockRepo.findAll()).thenReturn(products);
        List<Product> result = productservice.getAllProducts();

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals(3L, result.get(2).getId());
        verify(mockRepo, times(1)).findAll();
    }

    //*** findById(id) ***

    @Test
    public void find_product_by_id_test_should_return_product() {
        Product product = new Product(1, "test termék", 250.25, "Nagyon szép kis teszt termék tesztelésre");
        when(mockRepo.findById(1L)).thenReturn(Optional.of(product));

        Product result = productservice.getProductById(1L);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getPrice(), result.getPrice());

        verify(mockRepo, times(1)).findById(1L);

    }

    @Test
    public void find_product_by_id_test_should_throw_exception_when_id_not_valid() {
        long productId = 1;
        when(mockRepo.findById(productId)).thenThrow(new ProductNotFoundException("No product found with id :" + productId));

        Exception exception = assertThrows(ProductNotFoundException.class, () -> productservice.getProductById(productId));

        assertEquals("No product found with id :" + productId, exception.getMessage());
        verify(mockRepo, times(1)).findById(1L);
    }

    // *** createProduct ***

    @Test
    public void createProduct_test_should_map_dto_to_product_and_return_Product_with_ID() {
        ProductDto dto = new ProductDto("test", 100.10, "test description");

        Product product = new Product();
        product.setId(1);
        product.setName("test");
        product.setPrice(100.10);
        product.setDescription("test description");

        when(mockRepo.save(any(Product.class))).thenReturn(product);

        Product result = productservice.createProduct(dto);

        assertNotNull(result);
        assertNotEquals(0, result.getId());
        assertEquals("test", result.getName());
        assertEquals(100.1, result.getPrice());
        assertEquals("test description", result.getDescription());

        verify(mockRepo, times(1)).save(any(Product.class));

        verify(mockRepo, times(1)).save(argThat(prod ->
                "test".equals(prod.getName()) &&
                        100.10 == prod.getPrice() &&
                        "test description".equals(prod.getDescription())));

    }

    //*** updateProduct(productDto, id) ***

    @Test
    public void upDateProduct_test_should_map_dto_to_product_and_save_updated() {
        long productId = 1;
        ProductDto productDto = new ProductDto("New Product", 200.0, "New description");

        Product product = new Product();
        product.setId(productId);
        product.setName("Old Product");
        product.setPrice(150.0);
        product.setDescription("Old description");

        when(mockRepo.findById(productId)).thenReturn(Optional.of(product));

        productservice.updateProduct(productDto, productId);

        verify(mockRepo, times(1)).findById(productId);
        verify(mockRepo, times(1)).save(product);
        assertEquals("New Product", product.getName());
        assertEquals(200.0, product.getPrice());
        assertEquals("New description", product.getDescription());

    }

    @Test
    public void updateProduct_test_should_throw_exception_when_product_not_found() {
        long productId = 1;
        ProductDto productDto = new ProductDto("Old Product", 150.0, "old description");

        when(mockRepo.findById(productId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class, () -> productservice.updateProduct(productDto, productId));

        assertEquals(exception.getClass().getName(), ProductNotFoundException.class.getName());
        assertEquals("No product to update with id :" + productId, exception.getMessage());
        verify(mockRepo, times(1)).findById(1L);
        verify(mockRepo, never()).save(any(Product.class));
    }

    //*** deleteProduct(id) ***

    @Test
    public void deleteProduct_test_should_find_and_delete_product_when_products_exists() {
        long productId = 1;
        Product product = new Product();
        product.setId(productId);
        product.setName("test");
        product.setPrice(100.10);
        product.setDescription("test description");

        when(mockRepo.findById(productId)).thenReturn(Optional.of(product));

        productservice.deleteProduct(productId);

        verify(mockRepo, times(1)).findById(productId);
        verify(mockRepo, times(1)).deleteById(productId);

    }

    @Test
    public void deleteProduct_test_should_throw_exception_when_product_not_found() {
        long productId = 1;

        when(mockRepo.findById(productId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class, () -> productservice.deleteProduct(productId));


        assertEquals(exception.getClass().getName(), ProductNotFoundException.class.getName());
        assertEquals("No product to delete with id :" + productId, exception.getMessage());
        verify(mockRepo, times(1)).findById(productId);
        verify(mockRepo, never()).deleteById(productId);

    }

    @Test
    public void mapProductDtoToProduct_test_should_return_product_entity_from_dto_with_id_of_zero(){
        ProductDto productDto = new ProductDto("Test Product", 99.99, "Test Description");

        Product product = new Product();
        product.setName("Old Name");
        product.setPrice(50.00);
        product.setDescription("Old Description");

        Product result = ProductService.mapProductDtoToProduct(product, productDto);

        assertNotNull(result);
        assertEquals(0, result.getId());
        assertEquals("Test Product", result.getName());
        assertEquals(99.99, result.getPrice());
        assertEquals("Test Description", result.getDescription());

    }

}