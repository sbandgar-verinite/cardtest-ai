package com.verinite.cla.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

@Service
public class FileGenerationService {

	private static final String TEMPLATE_DIR = "classpath:/";

    public Map<String, String> loadTemplates() {
        Map<String, String> templates = new HashMap<>();
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(TEMPLATE_DIR + "*.txt");

            for (Resource resource : resources) {
                String fileName = resource.getFilename();
                String content = readFileContent(resource);
                templates.put(fileName, content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return templates;
    }

    public void updateTemplate(String fileName, String newContent) {
        try {
            Resource resource = new PathMatchingResourcePatternResolver().getResource(TEMPLATE_DIR + fileName);
            if (resource.exists()) {
                Path path = Path.of(resource.getURI());
                Files.write(path, newContent.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            } else {
                throw new FileNotFoundException("File not found: " + fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFileContent(Resource resource) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
	
}
