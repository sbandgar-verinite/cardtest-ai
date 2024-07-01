package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.entity.Tenant;
import com.verinite.cla.service.TenantService;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/tenants")
public class TenantController {

	@Autowired
	private TenantService tenantService;
	
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public Tenant addNewTenant(@RequestBody Tenant tenant) {
		return tenantService.addTenant(tenant);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public List<Tenant> fetchAllTenants() {
		return tenantService.findAllTenant();
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public Tenant fetchTenantById(@PathVariable ("id") String tenantId) {
		return tenantService.findTenantById(tenantId);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.PUT)
	public Tenant updateTenant(@RequestBody Tenant tenant) {
		return tenantService.updateTenant(tenant);
	}
}
