package com.verinite.cla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.service.ExcelDataService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/excel")
public class ExcelController {
	
	 @Autowired
	 private ExcelDataService excelDataService;

	    @Operation(summary = "Imports data from the specified Excel file and saves it to the database.")
	    @GetMapping("/import")
	    public ResponseEntity<String> importData() {
	        if(excelDataService.importDataFromExcel()) {
	        	return ResponseEntity.ok("Data imported successfully");
	        }else
	        	return new ResponseEntity<>("Data importe failed", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
