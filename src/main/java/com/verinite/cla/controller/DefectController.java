package com.verinite.cla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.verinite.cla.entity.Defect;
import com.verinite.cla.service.DefectService;

@RestController
@RequestMapping("/defects")
public class DefectController {

    @Autowired
    private DefectService defectService;
    
    @PostMapping
    public Defect createNewDefect(@RequestBody Defect defect) {
        return defectService.createDefect(defect);
    }
        
    @GetMapping
    public List<Defect> fetchAllDefects() {
        return defectService.getAllDefects();
    }
    
    @GetMapping("/{id}")
    public Defect fetchDefectById(@PathVariable("id") String defectId) {
        return defectService.getDefectById(defectId);
    }
    
    @PutMapping
    public Defect updateDefect(@RequestBody Defect defect) {
        return defectService.updateDefect(defect);
    }
    
    @DeleteMapping("/{id}")
    public void deleteDefect(@PathVariable String id) {
        defectService.deleteDefect(id); 
 
    }
}
