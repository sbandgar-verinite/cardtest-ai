package com.verinite.cla.dto;

import java.util.List;

public class TenantDto {

	private String name;
	private String product;	
	private List<String> features;
	public TenantDto(String name, String product, List<String> features) {
		super();
		this.name = name;
		this.product = product;
		this.features = features;
	}
	public TenantDto() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public List<String> getFeatures() {
		return features;
	}
	public void setFeatures(List<String> features) {
		this.features = features;
	}
	
}
