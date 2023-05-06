package com.project.model;

import java.util.ArrayList;

import com.project.database.annotation.Column;
import com.project.database.annotation.Table;
import com.project.database.object.Entity;

@Table("genre")
public class Genre extends Entity{

	@Column(pk = true)
	Integer id;
	
	@Column
	String genre;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	
	public Genre getGenre(int id) {
		try {
			@SuppressWarnings("unchecked")
			ArrayList<Genre> allGenres=getAll();
			for (Genre genre : allGenres) {
				if(genre.getId()==id) {
					return genre;
				}
			}
			return null;
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
			return null;
		}
	}
}
