package com.verinite.cla.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RunConfigs implements Serializable {

	private static final long serialVersionUID = 1L;

	List<RunConfig> runConfigs = new ArrayList<>();

	public List<RunConfig> getRunConfigs() {
		return runConfigs;
	}

	public void setRunConfigs(List<RunConfig> runConfigs) {
		this.runConfigs = runConfigs;
	}

}
