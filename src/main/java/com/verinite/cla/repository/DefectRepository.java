package com.verinite.cla.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verinite.cla.entity.Defect;

public interface DefectRepository extends JpaRepository<Defect, String> {

}
