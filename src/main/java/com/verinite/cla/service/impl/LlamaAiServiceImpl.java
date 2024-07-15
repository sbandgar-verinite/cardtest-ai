package com.verinite.cla.service.impl;

import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verinite.cla.dto.GherkinFormat;
import com.verinite.cla.service.GherkinParser;
import com.verinite.cla.service.LlamaAiService;

@Service
public class LlamaAiServiceImpl implements LlamaAiService {

	@Autowired
	private ChatClient chatClient;

	@Override
	public GherkinFormat generateMessage(String promptMessage) {
		final String llamaMessage = chatClient.call(promptMessage);

		return GherkinParser.parseGherkin(llamaMessage);

	}

}
