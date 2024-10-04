package com.szabolcs.SpringbootWebshop.Service;

import com.szabolcs.SpringbootWebshop.Dto.ProductDto;
import com.szabolcs.SpringbootWebshop.ExceptionHandler.Exceptions.ProductNotFoundException;
import com.szabolcs.SpringbootWebshop.Model.Product;
import com.szabolcs.SpringbootWebshop.Repository.FakeProductRepo;
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
    FakeProductRepo mockRepo;

    @InjectMocks
    ProductService productservice;


    //findAll test
    @Test
    public void find_all_products_test_should_return_5_products() {
        when(mockRepo.findAll()).thenReturn(products);
        List<Product> result = productservice.getAllProducts();

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals(3L, result.get(2).getId());
        verify(mockRepo, times(1)).findAll();
    }

    //findById(id) test
    @Test
    public void find_product_by_id_test_should_return_product() {
        Product product = new Product(1,"test termék",250.25, "Nagyon szép kis teszt termék tesztelésre");
        when(mockRepo.findById(1L)).thenReturn(Optional.of(product));

        Product result = productservice.getProductById(1L);

        assertNotNull(result);
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getPrice(), result.getPrice());

        verify(mockRepo, times(1)).findById(1L);

    }

    @Test
    public void find_product_by_name_test_should_throw_exception_when_id_not_valid() {
        when(mockRepo.findById(1L)).thenThrow(new ProductNotFoundException("Not found Product with id: 1"));

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {productservice.getProductById(1L);
        });

        assertTrue(exception.getMessage().contains("Not found Product with id: 1"));
        verify(mockRepo, times(1)).findById(1L);
    }





}