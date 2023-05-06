package com.project.model;

import java.util.ArrayList;

import com.project.database.annotation.Column;
import com.project.database.annotation.Table;
import com.project.database.object.Entity;

@Table("admin")
public class Admin extends Entity{

	@Column(pk = true,autoComplete = true)
	Integer id;
	
	@Column
	String nom;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Admin getByNom(String user) throws Exception {
		@SuppressWarnings("unchecked")
		ArrayList<Admin> getAdmins=getAll();
		for (Admin admin : getAdmins) {
			if(admin.getNom().compareTo(user)==0) {
				return admin;
			}
		}
		return null;
	}
	
}
