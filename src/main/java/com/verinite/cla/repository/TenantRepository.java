package com.verinite.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verinite.cla.entity.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, String> {

}
