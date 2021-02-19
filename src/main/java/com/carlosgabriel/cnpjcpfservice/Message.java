package com.carlosgabriel.cnpjcpfservice;

public class Message {
	
	private final double id;
	private final String content;
	
	public Message(double id, String content) {
		this.id = id;
		this.content = content;
	}

	public double getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

}
