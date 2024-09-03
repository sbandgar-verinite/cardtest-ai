package com.verinite.cla.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.verinite.cla.model.MethodDetails;
import com.verinite.cla.util.PropertiesConfig;
import com.verinite.cla.utility.GitUtility;
import com.verinite.commons.controlleradvice.BadRequestException;
import com.verinite.commons.dto.StatusResponse;

@Component
public class StepDefinitionGeneratorService {

	@Autowired
	private PropertiesConfig propsConfig;

	@Autowired
	private GitUtility gitUtility; 
	
	public StatusResponse uploadFeatureFileToGit(String fileName) throws Exception {
		if (fileName == null || fileName.isBlank()) 
			throw new BadRequestException("File Not Found");
		List<MethodDetails> generatedSteps = null;
		try {
			generatedSteps = stepGeneration(fileName);
			
			String repoFileName = propsConfig.getGitStepDefinitionUrl()  + "/StepDefinitions.java";
			
			String downloadFile = gitUtility.downloadFile(repoFileName);
			
			String replaceMethods = replaceMethods(downloadFile,generatedSteps);
			
			return gitUtility.uploadFeatureFileToGit(repoFileName, replaceMethods);
			
		} catch (Exception e) {
			throw e;
		}
	}
	

    public String replaceMethods(String javaCode,List<MethodDetails> newMethods) {
        CompilationUnit compilationUnit = StaticJavaParser.parse(javaCode);
        List<MethodDeclaration> methods = compilationUnit.findAll(MethodDeclaration.class);
        ClassOrInterfaceDeclaration classDeclaration = compilationUnit
                .getClassByName("StepDefinitions")
                .orElseThrow(() -> new RuntimeException("Class not found"));

        Class<? extends CompilationUnit> class1 = compilationUnit
                .getClass();
        
        classDeclaration.getMethods().forEach(MethodDeclaration::remove);
        addMethodWithAnnotation(classDeclaration, newMethods);

        return compilationUnit.toString();
    }
    
    private static void addMethodWithAnnotation(ClassOrInterfaceDeclaration classDeclaration, List<MethodDetails> methodDetails) {
		for (MethodDetails methodDetail : methodDetails) {
			addMethodWithAnnotation(classDeclaration, methodDetails);
			MethodDeclaration method = StaticJavaParser.parseMethodDeclaration(methodDetail.getMethodBody());
			AnnotationExpr annotation = StaticJavaParser.parseAnnotation(
					methodDetail.getAnnotationName() + "(\"" + methodDetail.getAnnotationValue() + "\")");
			method.addAnnotation(annotation);
			classDeclaration.addMember(method);
		}
	}
    
	public List<MethodDetails> stepGeneration(String fileName) throws Exception {
		String wrkDir = System.getProperty("user.dir");
		String path = wrkDir + File.separator + "templates";
		if (Paths.get(path).isAbsolute()) {

			File inputFile = new File(path + File.separator + fileName);
			File glueCode = new File(path + File.separator + "glueCode.json");
			if (!inputFile.exists()) {
				throw new Exception("File not present at location " + inputFile.getAbsolutePath());
			}
			return generateStepDefinitionsFromFile(inputFile.getAbsolutePath(), glueCode);
		} else {
			throw new Exception("File not present at location " + path);
		}
	}

	public List<MethodDetails> generateStepDefinitionsFromFile(String inputFilePath, File glueCodeFile) throws Exception {
		File inputFile = new File(inputFilePath);
		List<MethodDetails> methods = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode glueCodeNode = null;
		if (glueCodeFile.exists()) 
			glueCodeNode = objectMapper.readTree(glueCodeFile);
		
		try (Scanner scanner = new Scanner(inputFile)) {
			while (scanner.hasNextLine()) {
				String step = scanner.nextLine().trim();
				if (!step.isEmpty()) {
					MethodDetails stepDefinition = writeStepDefinition(step, glueCodeNode);
					if (stepDefinition!=null) {
						methods.add(stepDefinition);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return methods;
	}

	private MethodDetails writeStepDefinition(String step, JsonNode glueCodeJsonNode) {
		String annotation = getAnnotation(step);
		if (!annotation.isEmpty()) {
			String cleanStep = removeGherkinKeyword(step);
			String parameters = extractParameters(cleanStep);
			String formattedStep = cleanStep.replaceAll("<[^>]*>", "{}").replaceAll("\\{[^}]*\\}", "{}")
					.replace("\"", "");
			String methodName = generateMethodName(cleanStep);
			StringBuilder methodBody = new StringBuilder();
			methodBody.append("public void ").append(methodName).append("(").append(parameters).append(") {\n");
			StringBuilder parametersValues = new StringBuilder();
			if (parameters != null && !parameters.isBlank()) {
				String[] split = parameters.split(", ");
				for (String param : split) {
					String[] parts = param.split(" ");
					parametersValues.append(parts[1]).append(" + \", \" + ");
				}
				parametersValues.setLength(parametersValues.length() - 9); // Remove the trailing " + \", \""
			}
			
			methodBody.append("    System.out.println(\"parameters: \" + ").append(parametersValues).append(");\n");
			if (glueCodeJsonNode != null) {
				JsonNode resultNode = glueCodeJsonNode.get(formattedStep);
				if (resultNode != null) {
					methodBody.append(resultNode.textValue());
				} else {
					methodBody.append("    // Add your Selenium code here");
				}
			} else {
				methodBody.append("    // Add your Selenium code here");
			}
			methodBody.append("\n}\n\n\\n");
			return new MethodDetails(annotation, formattedStep, methodBody.toString());
		}
		return null;
	}

	private String removeGherkinKeyword(String step) {
		// Remove the Gherkin keyword and trim the step text
		if (step.startsWith("Given"))
			return step.substring(5).trim();
		if (step.startsWith("When") || step.startsWith("Then"))
			return step.substring(4).trim();
		if (step.startsWith("And"))
			return step.substring(3).trim();
		return step;
	}

	private String generateMethodName(String step) {
		// Convert step text to camelCase for method name
		String[] words = step.replaceAll("[{}<>]", "").split("\\s+");
		StringBuilder methodName = new StringBuilder(words[0].toLowerCase());

		for (int i = 1; i < words.length; i++) { // Start from index 1 to capitalize subsequent words
			methodName.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1).toLowerCase());
		}

		return methodName.toString().replaceAll("[^a-zA-Z0-9]", ""); // Remove special characters
	}

	private String getAnnotation(String step) {
		if (step.startsWith("Given"))
			return "@Given";
		if (step.startsWith("When"))
			return "@When";
		if (step.startsWith("Then"))
			return "@Then";
		if (step.startsWith("And"))
			return "@And";
		return "";
	}

	private String extractParameters(String step) {
		// Regex to match text inside angle brackets or curly braces
		Matcher matcher = Pattern.compile("<([^>]+)>|\\{([^}]+)\\}").matcher(step);
		StringBuilder parameters = new StringBuilder();

		while (matcher.find()) {
			String parameter = matcher.group(1) != null ? matcher.group(1) : matcher.group(2); // Handle both <...> and
																								// {...}
			if (parameters.length() > 0)
				parameters.append(", ");
			parameters.append("String ").append(convertToVariableName(parameter.trim()));
		}

		return parameters.toString();
	}

	private String convertToVariableName(String parameter) {
		if (parameter == null || parameter.isEmpty()) {
			return "";
		}
		String formattedString = parameter.replaceAll("[^a-zA-Z0-9]", " ") // Replace non-alphanumeric characters with
																			// spaces
				.replaceAll("_", " "); // Replace underscores with spaces

		String[] words = formattedString.trim().split("\\s+");
		StringBuilder camelCaseName = new StringBuilder();

		for (int i = 0; i < words.length; i++) {
			if (i == 0) {
				// First word: lowercase
				camelCaseName.append(words[i].toLowerCase());
			} else {
				// Subsequent words: capitalize first letter
				camelCaseName.append(Character.toUpperCase(words[i].charAt(0)))
						.append(words[i].substring(1).toLowerCase());
			}
		}

		return camelCaseName.toString();
	}
	

}