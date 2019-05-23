package br.com.alura.forum.controller.dto.output;

public class AuthenticationTokenOutputDto {

	private String tokenType;
	private String token;

	public AuthenticationTokenOutputDto(String tokenType, String token) {
		super();
		this.tokenType = tokenType;
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public String getTokenType() {
		return tokenType;
	}

}
