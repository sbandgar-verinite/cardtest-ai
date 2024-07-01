package com.verinite.cla.model;

import java.io.Serializable;

public class Attribute implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2854546283671386076L;

	private String name;
	private String type;
	private String length;
	public Attribute(String name, String type, String length) {
		super();
		this.name = name;
		this.type = type;
		this.length = length;
	}
	public Attribute() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	
}
