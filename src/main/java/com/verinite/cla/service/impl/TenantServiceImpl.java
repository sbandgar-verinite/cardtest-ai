package com.verinite.cla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.entity.Tenant;
import com.verinite.cla.repository.TenantRepository;
import com.verinite.cla.service.TenantService;



@Service
public class TenantServiceImpl implements TenantService {

	@Autowired
	private TenantRepository tenantRepository;
	
	@Override
	public Tenant addTenant(Tenant tenant) {
		return tenantRepository.save(tenant);
	}

	@Override
	public Tenant updateTenant(Tenant tenant) {
		return tenantRepository.save(tenant);
	}

	@Override
	public Tenant findTenantById(String id) {
		return tenantRepository.findById(id).orElse(null);
	}

	@Override
	public List<Tenant> findAllTenant() {
		return tenantRepository.findAll();
	}

}
