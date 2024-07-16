package com.verinite.cla.service;

import com.verinite.cla.dto.GherkinFormat;

public interface LlamaAiService {

	GherkinFormat generateMessage(String prompt, String count);

}
