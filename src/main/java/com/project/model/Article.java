package com.project.model;

import java.util.ArrayList;
import java.util.Date;

import com.project.database.annotation.Column;
import com.project.database.annotation.Table;
import com.project.database.object.Entity;

@Table("article")
public class Article extends Entity{

	@Column(pk=true,autoComplete = true)
	Integer id;
	
	@Column
	String title;
	
	@Column
	Integer categorie;
	
	@Column
	String description;
	
	@Column
	String content;
	
	@Column
	String photo;

	@Column
	Date datePub;

	
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCategorie() {
		return categorie;
	}

	public void setCategorie(Integer categorie) {
		this.categorie = categorie;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDatePub() {
		return datePub;
	}

	public void setDatePub(Date datePub) {
		this.datePub = datePub;
	}
	
	public static String search(String recherche,int domaine) {
		String sql="select * from article where ";
		if(domaine!=0) {
			sql+="categorie="+domaine+" and title like '%"+recherche+"%' or description like '%"+recherche+"%' and categorie="+domaine;
		}
		else {
			sql+="title like '%"+recherche+"%' or description like '%"+recherche+"%'";
		}
		return sql;
	}
	
	
	@SuppressWarnings("unchecked")
	public Article getById(int id) throws Exception {
		ArrayList<Article> getArticles=getAll();
		for (Article article : getArticles) {
			if(article.getId()==id) {
				return article;
			}
		}
		return null;
	}

	public Article(String title, Integer categorie, String description, String content, Date datePub,String photo) {
		super();
		this.title = title;
		this.categorie = categorie;
		this.description = description;
		this.content = content;
		this.datePub = datePub;
		this.photo=photo;
	}

	public Article() {
		super();
	}
	
	
	
	
}
