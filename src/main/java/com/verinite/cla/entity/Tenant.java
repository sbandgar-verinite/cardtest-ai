//package com.verinite.cla.entity;
//
//import java.util.List;
//
//import jakarta.persistence.CollectionTable;
//import jakarta.persistence.Column;
//import jakarta.persistence.ElementCollection;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name="tenant")
//public class Tenant {
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.UUID)
//	@Column(name="id", columnDefinition="VARCHAR(255)")
//	private String id;
//	
//	private String name;
//	private String product;
//	
//	@ElementCollection
//	@CollectionTable(name="tenant-features")
//	private List<String> features;
//
//	public Tenant(String id, String name, String product, List<String> features) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.product = product;
//		this.features = features;
//	}
//
//	public Tenant() {
//		super();
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getProduct() {
//		return product;
//	}
//
//	public void setProduct(String product) {
//		this.product = product;
//	}
//
//	public List<String> getFeatures() {
//		return features;
//	}
//
//	public void setFeatures(List<String> features) {
//		this.features = features;
//	}
//		
//}
