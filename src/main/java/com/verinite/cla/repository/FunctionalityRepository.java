package com.verinite.cla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.verinite.cla.model.Functionality;

public interface FunctionalityRepository extends JpaRepository<Functionality, String> {

	@Query("select c from case_details c where :tag IN (tags)")
	List<Functionality> findByTag(String tag);

}
