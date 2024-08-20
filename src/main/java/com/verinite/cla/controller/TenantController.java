package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.entity.Tenant;
import com.verinite.cla.service.TenantService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/tenants")
public class TenantController {

	@Autowired
	private TenantService tenantService;

	@PostMapping
	public Tenant addNewTenant(@RequestBody Tenant tenant) {
		return tenantService.addTenant(tenant);
	}

	@GetMapping
	public List<Tenant> fetchAllTenants() {
		return tenantService.findAllTenant();
	}

	@GetMapping(value = "/{id}")
	public Tenant fetchTenantById(@PathVariable("id") String tenantId) {
		return tenantService.findTenantById(tenantId);
	}

	@PutMapping
	public Tenant updateTenant(@RequestBody Tenant tenant) {
		return tenantService.updateTenant(tenant);
	}
}
