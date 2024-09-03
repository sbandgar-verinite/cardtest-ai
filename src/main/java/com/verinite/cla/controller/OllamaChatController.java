package com.verinite.cla.controller;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/llama3")
public class OllamaChatController {
	private final OllamaChatModel chatModel;

	public OllamaChatController(OllamaChatModel chatModel) {
		this.chatModel = chatModel;
	}

	@GetMapping("/ai/generate")
	public String generate(@RequestParam(value = "message",
	                defaultValue = "Tell me a joke") String message,
			@RequestParam(value = "testCount",
            defaultValue = "") String testCount) {
		String str ="generate {testCount} test cases in gherkin format (single liner FEATURE, SCENARIO (without count), GIVEN, WHEN, AND, THEN statements with predefined values) for {messasge}";
		PromptTemplate template = new PromptTemplate(str,Map.of("message", message,"testCount",testCount));
		String call = chatModel.call(template.getTemplate());
		return call;
	}

	@GetMapping("/ai/generateStream")
	public Flux<String> generateStream(
			@RequestParam(value = "message", defaultValue = "Tell me a joke") String message,
			@RequestParam(value = "count", defaultValue = "text") String count) {
		    String str = "I want {count} test cases for {message} feature in well Gherkin format. Please provide the different scenarios in a good format.";
		 PromptTemplate template = new PromptTemplate(str,Map.of("message", message,"count",count));
		 //Prompt prompt = new Prompt(new UserMessage( message));
		 AtomicBoolean start = new AtomicBoolean(false);
		 return chatModel.stream(template.create()).map(x -> x.getResult().getOutput().getContent()).filter(x -> isGherkinFormat(x, start));
		//return chatModel.stream(str + ". " + message);
	}
	  @Operation(summary = "Stream events")
	    @ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Successful operation",
	                content = @Content(mediaType = "text/event-stream",
	                        schema = @Schema(type = "string"))),
	        @ApiResponse(responseCode = "400", description = "Invalid input")
	    })
	    @GetMapping(value = "/api/stream")
	    public Flux<String> streamEvents(
				@RequestParam(value = "message", defaultValue = "Tell me a joke") String message,
				@RequestParam(value = "count", defaultValue = "") String count) {
			    String str = "generate {count} test cases in gherkin format (single liner FEATURE, SCENARIO (without count), GIVEN, WHEN, AND, THEN statements with predefined values) for {message}";
				PromptTemplate template = new PromptTemplate(str, Map.of("message", message, "count", count));
			 AtomicBoolean start = new AtomicBoolean(false);
			 return chatModel.stream(template.create()).map(x -> x.getResult().getOutput().getContent()).filter(x -> isGherkinFormat(x, start));
	    }

	@GetMapping("/ai/testcases")
	public Flux<String> generateTestCases(
			@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
		return chatModel.stream(new UserMessage(message));
	}
	private boolean isGherkinFormat(String content, AtomicBoolean start) {
		System.out.println(content);
		if (content.startsWith("**") || content.startsWith("Feature") || content.startsWith("Scenario")) {
			 start.set(true);
		}else if(start.get() && content.startsWith("``"))
			 start.set(false);
		else if(!start.get() && content.startsWith("``"))
			 start.set(true);
		return start.get();
	}
}