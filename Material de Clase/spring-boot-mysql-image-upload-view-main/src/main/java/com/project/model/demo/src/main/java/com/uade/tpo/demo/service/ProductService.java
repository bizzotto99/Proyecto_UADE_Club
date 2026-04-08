package com.uade.tpo.demo.service;

import com.uade.tpo.demo.entity.Product;
import com.uade.tpo.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProductWithImage(Product product, MultipartFile file) throws IOException {
        product.setImageName(file.getOriginalFilename());
        product.setImageData(file.getBytes());
        return productRepository.save(product);
    }
}
