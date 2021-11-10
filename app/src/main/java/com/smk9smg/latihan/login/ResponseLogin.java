package com.smk9smg.latihan.login;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin{

	@SerializedName("hasil")
	private String hasil;

	public String getHasil(){
		return hasil;
	}
}