package com.verinite.cla.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.stereotype.Service;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
public class TemplateService {

//	public String createRunScenarioFile(String fileName, String templateName, String template,
//			Map<String, Map<String, String>> featureData) throws TemplateNotFoundException,
//			MalformedTemplateNameException, ParseException, IOException, TemplateException {
//		StringTemplateLoader stringLoader = new StringTemplateLoader();
//		stringLoader.putTemplate(templateName, template);
//
//		Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
//		cfg.setTemplateLoader(stringLoader);
//		Template fTemplate = cfg.getTemplate(templateName);
//
//		/* Merge data-model with template */
//		File featureFile = new File(fileName + ".txt");
//		featureFile.createNewFile();
//		FileOutputStream file = new FileOutputStream(featureFile, true);
//		Writer out = new OutputStreamWriter(file);
//		out.write('\n');
//		fTemplate.process(featureData, out);
//		return "Feature file created";
//	}

	public String createRunScenarioFile(String fileName, String templateName, String template) throws TemplateNotFoundException,
			MalformedTemplateNameException, ParseException, IOException, TemplateException {
//		StringTemplateLoader stringLoader = new StringTemplateLoader();
//		stringLoader.putTemplate(templateName, template);
//
//		Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
//		cfg.setTemplateLoader(stringLoader);
//		Template fTemplate = cfg.getTemplate(templateName);

		/* Merge data-model with template */
		File featureFile = new File(fileName + ".txt");
		featureFile.createNewFile();
		FileOutputStream file = new FileOutputStream(featureFile, true);
		Writer out = new OutputStreamWriter(file);
		out.write('\n');
		out.write(template);
		out.close();
//		fTemplate.process(out);
		return "Feature file created";
	}

}
