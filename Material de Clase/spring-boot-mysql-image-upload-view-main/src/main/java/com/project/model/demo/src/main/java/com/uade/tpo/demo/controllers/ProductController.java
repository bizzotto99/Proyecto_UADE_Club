package com.uade.tpo.demo.controllers;


import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.entity.dto.ProductDTO;
import com.uade.tpo.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadProductWithImage(@ModelAttribute ProductDTO productDTO,
                                                         @RequestParam("file") MultipartFile file) {
        try {
            Product product = new Product();
            product.setDescription(productDTO.getDescription());
            
            Category category = new Category();
            category.setId(productDTO.getCategoryId());
            product.setCategory(category);

            productService.saveProductWithImage(product, file);
            return new ResponseEntity<>("Product uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
