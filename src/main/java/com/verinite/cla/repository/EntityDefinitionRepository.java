package com.verinite.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verinite.cla.entity.EntityDefinition;

public interface EntityDefinitionRepository extends JpaRepository<EntityDefinition, String> {

}
