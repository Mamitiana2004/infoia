package com.project.model;

import java.util.ArrayList;
import java.util.Date;

import com.project.database.annotation.Column;
import com.project.database.annotation.Table;
import com.project.database.object.Entity;

@Table("utilisateur")
public class Utilisateur extends Entity{

	@Column(pk = true,autoComplete = true)
	Integer id;
	
	@Column
	String nom;
	
	@Column
	String prenom;
	
	@Column
	Integer genre;
	
	@Column
	Date dateNaissance;
	
	@Column
	String email;
	
	@Column
	String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Integer getGenre() {
		return genre;
	}

	public void setGenre(Integer genre) {
		this.genre = genre;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Utilisateur() {
		super();
	}

	public Utilisateur(String nom, String prenom, Integer genre, Date dateNaissance, String email, String password) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.genre = genre;
		this.dateNaissance = dateNaissance;
		this.email = email;
		this.password = password;
	}

	public Utilisateur getByEmail(String email) throws Exception {
		@SuppressWarnings("unchecked")
		ArrayList<Utilisateur> getUtilisateurs=getAll();
		for (Utilisateur utilisateur : getUtilisateurs) {
			if(utilisateur.getEmail().compareTo(email)==0) {
				return utilisateur;
			}
		}
		return null;
	}
	
	public Utilisateur getById(Integer id) throws Exception {
		@SuppressWarnings("unchecked")
		ArrayList<Utilisateur> getUtilisateurs=getAll();
		for (Utilisateur utilisateur : getUtilisateurs) {
			if(utilisateur.getId()==id) {
				return utilisateur;
			}
		}
		return null;
	}
	
}
