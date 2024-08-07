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

import com.verinite.cla.entity.EntityDefinition;
import com.verinite.cla.service.EntityDefinitionService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/entityDefinitions")
public class EntityDefinitionController {

	@Autowired
	private EntityDefinitionService entityDefinitionService;

	@PostMapping
	public EntityDefinition addNewEntityDefinition(@RequestBody EntityDefinition entityDefinition) {
		return entityDefinitionService.addEntityDefinition(entityDefinition);
	}

	@GetMapping
	public List<EntityDefinition> fetchAllEntityDefinitions() {
		return entityDefinitionService.findAllEntityDefinition();
	}

	@GetMapping(value = "/{id}")
	public EntityDefinition fetchEntityDefinitionById(@PathVariable("id") String entityDefinitionId) {
		return entityDefinitionService.findEntityDefinitionById(entityDefinitionId);
	}

	@PutMapping
	public EntityDefinition updateEntityDefinition(@RequestBody EntityDefinition entityDefinition) {
		return entityDefinitionService.updateEntityDefinition(entityDefinition);
	}
}
