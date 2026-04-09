package com.in.Entitty;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

public record ErrorResponce(
		String message,
	HttpStatus http     )

{
	public ErrorResponce(String message, HttpStatus http) {
		this.message = message;
		this.http= http;
	}
	

}
