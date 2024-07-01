package com.verinite.cla.service;

import java.util.List;

import com.verinite.cla.entity.EntityDefinition;


public interface EntityDefinitionService {

	public EntityDefinition addEntityDefinition(EntityDefinition entityDefinition);
	
	public EntityDefinition updateEntityDefinition(EntityDefinition entityDefinition);
	
	public EntityDefinition findEntityDefinitionById(String id);
	
	public List<EntityDefinition> findAllEntityDefinition();
}
