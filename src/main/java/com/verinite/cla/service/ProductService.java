package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.entity.Product;


public interface ProductService {

	public Product addProduct(Product product);
	
	public Product updateProduct(Product product);
	
	public Product findProductById(String id);
	
	public List<Product> findAllProduct();
}
