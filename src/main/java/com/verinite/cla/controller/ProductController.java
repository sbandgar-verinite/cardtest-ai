package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.entity.Product;
import com.verinite.cla.service.ProductService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public Product addNewProduct(@RequestBody Product product) {
		return productService.addProduct(product);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public List<Product> fetchAllProducts() {
		return productService.findAllProduct();
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public Product fetchProductById(@PathVariable ("id") String productId) {
		return productService.findProductById(productId);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.PUT)
	public Product updateProduct(@RequestBody Product product) {
		return productService.updateProduct(product);
	}
}
