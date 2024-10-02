package com.szabolcs.SpringbootWebshop.Controller;

import com.szabolcs.SpringbootWebshop.Model.Product;
import com.szabolcs.SpringbootWebshop.Repository.FakeProductRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

}
