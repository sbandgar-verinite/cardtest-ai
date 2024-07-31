package com.verinite.cla.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.verinite.cla.entity.RunData;
import com.verinite.cla.repository.RunDataRepository;
import com.verinite.cla.service.ExcelDataService;

@Service
public class ExcelDataServiceImpl implements ExcelDataService {

	private static final Logger logger = LoggerFactory.getLogger(ExcelDataServiceImpl.class);
	private RunDataRepository runDataRepository;
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Autowired
	public ExcelDataServiceImpl(RunDataRepository runDataRepository) {
		this.runDataRepository = runDataRepository;
	}

	@Override
	public boolean importDataFromExcel() {
		List<RunData> runDataList = new ArrayList<>();
		File file = new File("example.xlsx");
//		  ClassPathResource resource = new ClassPathResource("example.xlsx");
//		  if (!resource.exists()) {
//	            return false; 
//	        }
		if (file.isFile()) {
			try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
				Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
				Row headerRow = sheet.getRow(0);

				// Find column indices
				int codeColIdx = findColumnIndex(headerRow, "code");
				int entityNameColIdx = findColumnIndex(headerRow, "entity_name");
				int attributesColIdx = findColumnIndex(headerRow, "attributes");

				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row != null) {
						RunData runData = new RunData();
						runData.setCode(getCellValue(row, codeColIdx));
						runData.setEntityName(getCellValue(row, entityNameColIdx));
						runData.setAttributes(parseAttributes(getCellValue(row, attributesColIdx)));
						runDataList.add(runData);
					}
				}
				if (!runDataList.isEmpty()) {
					runDataRepository.saveAll(runDataList);
					runDataRepository.flush();
				}
				return true;
			} catch (IOException e) {
				logger.info("Message : {}, cause : {}", e.getMessage(), e.getCause());
			}
		}
		return false;
	}

	private int findColumnIndex(Row headerRow, String columnName) {
		for (Cell cell : headerRow) {
			if (CellType.STRING.equals(cell.getCellType()) && columnName.equals(cell.getStringCellValue().trim())) {
				return cell.getColumnIndex();
			}
		}
		throw new IllegalArgumentException("Column " + columnName + " not found");
	}

	private String getCellValue(Row row, int columnIndex) {
		Cell cell = row.getCell(columnIndex);
		return cell == null ? "" : cell.getStringCellValue();
	}

	private Map<String, String> parseAttributes(String attributesJson) {
		try {
			return OBJECT_MAPPER.readValue(attributesJson, HashMap.class);
		} catch (JsonProcessingException e) {
			logger.info("Message : {}, cause : {}", e.getMessage(), e.getCause());
			throw new IllegalArgumentException("wrong attributes formate");
		}
	}

}
