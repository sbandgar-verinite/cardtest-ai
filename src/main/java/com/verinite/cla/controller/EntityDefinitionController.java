package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.entity.EntityDefinition;
import com.verinite.cla.service.EntityDefinitionService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/entityDefinitions")
public class EntityDefinitionController {

	@Autowired
	private EntityDefinitionService entityDefinitionService;
	
	@RequestMapping(value = "/", method=RequestMethod.POST)
	public EntityDefinition addNewEntityDefinition(@RequestBody EntityDefinition entityDefinition) {
		return entityDefinitionService.addEntityDefinition(entityDefinition);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public List<EntityDefinition> fetchAllEntityDefinitions() {
		return entityDefinitionService.findAllEntityDefinition();
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public EntityDefinition fetchEntityDefinitionById(@PathVariable ("id") String entityDefinitionId) {
		return entityDefinitionService.findEntityDefinitionById(entityDefinitionId);
	}
	
	@RequestMapping(value = "/", method=RequestMethod.PUT)
	public EntityDefinition updateEntityDefinition(@RequestBody EntityDefinition entityDefinition) {
		return entityDefinitionService.updateEntityDefinition(entityDefinition);
	}
}
