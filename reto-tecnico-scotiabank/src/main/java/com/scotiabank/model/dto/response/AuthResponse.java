package com.scotiabank.model.dto.response;

import com.scotiabank.model.dto.response.AuthResponse;

import lombok.Data;

@Data
public class AuthResponse {

	private String token;

    public AuthResponse(String token) {
        this.token = token;
    }
	
}
