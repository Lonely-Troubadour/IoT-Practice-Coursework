package com.serialport.ui;

import java.util.Date;

public class Message {
	private Date date;
	private String header;
	private String shortAddress;
	private String MAC;
	private String nodeVoltage;
	private String nodeFunction;
	private String nodeError;
	private String temperature;
	private String humiliation;
	private String illumination;
	private String tail;
	
	public Message(Date date, String header, String shortAddress, String MAC, String nodeVoltage, String nodeFunction, String nodeError,
			String temperature, String humiliation, String illumination, String tail) {
		super();
		this.date = date;
		this.header = header;
		this.shortAddress = shortAddress;
		this.MAC = MAC;
		this.nodeVoltage = nodeVoltage;
		this.nodeFunction = nodeFunction;
		this.nodeError = nodeError;
		this.temperature = temperature;
		this.humiliation = humiliation;
		this.illumination = illumination;
		this.tail = tail;
	}
	
	public Date getDate() {
		return date;
	}

	public String getHeader() {
		return header;
	}

	public String getShortAddress() {
		return shortAddress;
	}

	public String getMAC() {
		return MAC;
	}

	public String getNodeVoltage() {
		return nodeVoltage;
	}

	public String getNodeFunction() {
		return nodeFunction;
	}

	public String getNodeError() {
		return nodeError;
	}

	public String getTemperature() {
		return temperature;
	}

	public String getHumiliation() {
		return humiliation;
	}

	public String getIllumination() {
		return illumination;
	}

	public String getTail() {
		return tail;
	}
	
	
}
