package com.project.service;

import java.util.Date;

import com.project.model.Token;
import com.project.model.Utilisateur;
import com.project.util.Cryptage;

public class UtilisateurService {

	
	
	public static int login(String email,String password) throws Exception {
		Utilisateur utilisateur=new Utilisateur().getByEmail(email);
		if(utilisateur!=null) {
			if(utilisateur.getPassword().compareTo(Cryptage.Md5(password))==0) {
				return 0;
			}
			else {
				return 1;
			}
		}
		return 2;
	}
	
	public static int inscription(String nom,String prenom,int genre,Date dateNaissance,String email,String password) throws Exception {
		Utilisateur utilisateur=new Utilisateur().getByEmail(email);
		if(utilisateur!=null) {
			return 1;
		}
		else {
			Utilisateur newUtilisateur=new Utilisateur(nom, prenom, genre, dateNaissance, email, Cryptage.Md5(password));
			newUtilisateur.save();
			Token token=new Token();
			Utilisateur client=new Utilisateur().getByEmail(email);
			token.save(client.getId());
			return 0;
		}
	}
	
}
