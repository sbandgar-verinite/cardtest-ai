package com.verinite.cla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.verinite.cla.dto.GherkinFormat;
import com.verinite.cla.service.LlamaAiService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ExternalController {

	@Autowired
	private LlamaAiService llamaAiService;

	@GetMapping("api/v1/ai/generate")
	public ResponseEntity<GherkinFormat> generate(
			@RequestParam(value = "promptMessage", defaultValue = "Why is the sky blue?") String promptMessage) {
		final GherkinFormat aiResponse = llamaAiService.generateMessage(promptMessage);
		return ResponseEntity.status(HttpStatus.OK).body(aiResponse);
	}
}
