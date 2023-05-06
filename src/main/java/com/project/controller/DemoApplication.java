package com.project.controller;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.project.model.Token;
import com.project.model.Utilisateur;
import com.project.responseHandler.Error;
import com.project.service.UtilisateurService;


@CrossOrigin
@RestController
@SpringBootApplication
@ComponentScan(basePackages = "com.project.controller")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@GetMapping("/verifiSession")
	public String verifiSession(@RequestHeader(name="authorization") String token) throws SQLException {
		Gson gson=new Gson();
		if(Token.verifiExp(token)) {
			return gson.toJson(true);
		}
		else {
			return gson.toJson(new Error(401, "Pas d'autoriation ou session expiré"));
		}
	}
	
	@PostMapping("/login")
	public String loginClient(String email,String password) throws Exception {
		Gson gson=new Gson();
		switch (UtilisateurService.login(email, password)) {
			case 0: 
				Token token=new Token();
				Utilisateur client=new Utilisateur().getByEmail(email);
				token.save(client.getId());
				return gson.toJson(Token.select(client.getId()));
			case 1:
				return gson.toJson(new Error(100,"Mot de passe incorrect"));
			case 2:
				return gson.toJson(new Error(101,"Email inconnue"));
			default:
				throw new IllegalArgumentException("Unexpected value");
		}
	}
	
	@PostMapping("/sign")
	public String inscription(String nom,String prenom,int genre,String dateNaissance,String email,String password) throws Exception {
		Gson gson=new Gson();
		System.out.println(email+" "+password);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date dateN=format.parse(dateNaissance);
		switch (UtilisateurService.inscription(nom, prenom, genre, dateN, email, password)) {
			case 0: 
				Token token=new Token();
				Utilisateur client=new Utilisateur().getByEmail(email);
				token.save(client.getId());
				return gson.toJson(Token.select(client.getId()));
			case 1:
				return gson.toJson(new Error(100,"Email existant"));
			default:
				throw new IllegalArgumentException("Unexpected value");
		}
	}
	



}
