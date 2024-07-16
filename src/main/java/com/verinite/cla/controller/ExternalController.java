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
	public ResponseEntity<GherkinFormat> generate(@RequestParam(value = "promptMessage") String promptMessage,
			@RequestParam(value = "count", defaultValue = "") String count) throws Exception {
		if (promptMessage == null) {
			throw new Exception("Please enter a valid prompt message");
		}
		final GherkinFormat aiResponse = llamaAiService.generateMessage(promptMessage, count);
		return ResponseEntity.status(HttpStatus.OK).body(aiResponse);
	}
}
