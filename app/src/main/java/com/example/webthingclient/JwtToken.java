package com.example.webthingclient;

import com.google.gson.annotations.SerializedName;

public class JwtToken {

	@SerializedName("token")
	private String token;

	public String getToken(){
		return token;
	}
}