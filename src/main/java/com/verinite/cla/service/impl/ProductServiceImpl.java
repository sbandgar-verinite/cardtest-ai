package com.verinite.cla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.entity.Product;
import com.verinite.cla.repository.ProductRepository;
import com.verinite.cla.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(String id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public List<Product> findAllProduct() {
		return productRepository.findAll();
	}

}
