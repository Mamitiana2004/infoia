package com.project.controller;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.project.model.Admin;
import com.project.model.TokenAdmin;
import com.project.responseHandler.Error;
import com.project.service.AdminService;

@CrossOrigin
@RestController
@RequestMapping("/back")
public class AdminController {

	@GetMapping("/verifiSession")
	public String verifiSession(@RequestHeader(name="authorization") String token) throws SQLException {
		Gson gson=new Gson();
		if(TokenAdmin.verifiExp(token)) {
			return gson.toJson(true);
		}
		else {
			return gson.toJson(new Error(401, "Pas d'autoriation ou session expiré"));
		}
	}
	
	@PostMapping("/login")
	public String loginAdmin(String nom,String password) throws Exception {
		Gson gson=new Gson();
		switch (AdminService.login(nom, password)) {
			case 0: 
				TokenAdmin token=new TokenAdmin();
				Admin client=new Admin().getByNom(nom);
				token.save(client.getId());
				return gson.toJson(TokenAdmin.select(client.getId()));
			case 1:
				return gson.toJson(new Error(100,"Mot de passe incorrect"));
			case 2:
				return gson.toJson(new Error(101,"Admin inconnue"));
			default:
				throw new IllegalArgumentException("Unexpected value");
		}
	}
	
	
	
	
}
