package com.verinite.cla.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.entity.Defect;
import com.verinite.cla.repository.DefectRepository;
import com.verinite.cla.service.DefectService;

@Service
public class DefectServiceImpl implements DefectService {

    @Autowired
    private DefectRepository defectRepository;
    
    @Override
    public Defect createDefect(Defect defect) {
        return defectRepository.save(defect);
    }
    
    @Override
    public Defect getDefectById(String id) {
        return defectRepository.findById(id).orElse(null);
    }
    
    @Override
    public void deleteDefect(String id) {
        defectRepository.deleteById(id);
    }
    
    @Override
    public Defect updateDefect(Defect defect) {
        return defectRepository.save(defect);
    }

    @Override
    public List<Defect> getAllDefects() {
        return defectRepository.findAll();
    }
}
