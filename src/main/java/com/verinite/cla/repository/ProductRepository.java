package com.verinite.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verinite.cla.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String>  {

}
