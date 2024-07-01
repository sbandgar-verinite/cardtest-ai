package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.entity.Tenant;



public interface TenantService {

	public Tenant addTenant(Tenant tenant);
	
	public Tenant updateTenant(Tenant tenant);
	
	public Tenant findTenantById(String id);
	
	public List<Tenant> findAllTenant();
}
