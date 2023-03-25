  package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductRequest;
import com.example.demo.dto.ProductResponse;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
	private final ProductRepository productRepository;
	
	
	
	public void createProduct(ProductRequest  productRequest) {
		Product product =Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
	
		productRepository.save(product);
		log.info("Product {} is saved",product.getId());
	}



	public List<ProductResponse> getAllProducts() {
		List<Product> products =productRepository.findAll();
		return products.stream().map(this::mapToProductResponse).toList();
		
	}

	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}
}
