package com.project.controller;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.project.model.Token;
import com.project.model.Utilisateur;

@CrossOrigin
@RestController	
@RequestMapping("/client")
public class ClientController {
	
	
	@GetMapping("/getClientConnecter")
	public String getClient(@RequestHeader(name="authorization") String token) throws Exception {
		Gson gson=new Gson();
		Token t=Token.select(token);
		Utilisateur utilisateur=new Utilisateur().getById(t.getIdClient());
		return gson.toJson(utilisateur);
	}
	
	

}
