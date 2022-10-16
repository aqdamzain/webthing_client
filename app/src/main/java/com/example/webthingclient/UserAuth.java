package com.example.webthingclient;

import com.google.gson.annotations.SerializedName;

public class UserAuth{

	public UserAuth(String username, String password) {
		this.password = password;
		this.username = username;
	}

	@SerializedName("password")
	private String password;

	@SerializedName("username")
	private String username;

	public String getPassword(){
		return password;
	}

	public String getUsername(){
		return username;
	}
}