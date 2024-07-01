package com.verinite.cla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.entity.EntityDefinition;
import com.verinite.cla.repository.EntityDefinitionRepository;
import com.verinite.cla.service.EntityDefinitionService;

@Service
public class EntityDefinitionServiceImpl implements EntityDefinitionService {

	@Autowired
	private EntityDefinitionRepository entityDefinitionRepository;
	
	@Override
	public EntityDefinition addEntityDefinition(EntityDefinition entityDefinition) {
		return entityDefinitionRepository.save(entityDefinition);
	}

	@Override
	public EntityDefinition updateEntityDefinition(EntityDefinition entityDefinition) {
		return entityDefinitionRepository.save(entityDefinition);
	}

	@Override
	public EntityDefinition findEntityDefinitionById(String id) {
		return entityDefinitionRepository.findById(id).orElse(null);
	}

	@Override
	public List<EntityDefinition> findAllEntityDefinition() {
		return entityDefinitionRepository.findAll();
	}

}
