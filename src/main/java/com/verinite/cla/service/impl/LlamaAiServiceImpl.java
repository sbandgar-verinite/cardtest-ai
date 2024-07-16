package com.verinite.cla.service.impl;

import java.util.Map;
import java.util.logging.Logger;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.dto.GherkinFormat;
import com.verinite.cla.service.GherkinParser;
import com.verinite.cla.service.LlamaAiService;

@Service
public class LlamaAiServiceImpl implements LlamaAiService {

	private final Logger logger = Logger.getLogger(LlamaAiServiceImpl.class.getName());

	@Autowired
	private OllamaChatModel oChat;

	@Override
	public GherkinFormat generateMessage(String promptMessage, String count) {
		logger.info("Received message : " + promptMessage);
		String str = "generate {count} test cases in gherkin format as same with(Feature: , Scenario: (without count), GIVEN, WHEN, AND, THEN statements with predefined values in line) for {message}";
		PromptTemplate template = new PromptTemplate(str, Map.of("message", promptMessage, "count", count));
		Prompt genMessage = template.create();
		logger.info("Generated message : " + genMessage.getContents());
		String response = oChat.call(genMessage).getResult().getOutput().getContent();
		logger.info("Response from llama : " + response);
		return GherkinParser.parseGherkin(response);
	}

}
