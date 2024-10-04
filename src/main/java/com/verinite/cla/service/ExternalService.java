package com.verinite.cla.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface ExternalService {

	void storeDefect(JsonNode defectDetails);

}
