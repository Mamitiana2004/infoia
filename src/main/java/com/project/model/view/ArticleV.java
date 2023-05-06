package com.project.model.view;

import java.util.ArrayList;
import java.util.Date;

import com.project.database.annotation.Column;
import com.project.database.annotation.Table;
import com.project.database.object.Entity;

@Table("v_article")
public class ArticleV extends Entity{
	
	@Column(pk=true,autoComplete = true)
	Integer id;
	
	@Column
	String title;

	@Column
	String description;
	
	@Column
	Date datePub;
	
	@Column
	String type;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDatePub() {
		return datePub;
	}

	public void setDatePub(Date datePub) {
		this.datePub = datePub;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	public ArrayList<ArticleV> getAllLimite(int limite) throws Exception{
		int n=0;
		ArrayList<ArticleV> value=new ArrayList<>();
		ArrayList<ArticleV> getAllList=getAll();
		for (ArticleV articleV : getAllList) {
			if(n<limite) {
				value.add(articleV);
			}
			n++;
		}
		return value;
	}
	
	
	public ArrayList<ArticleV> getAllEntre(int inf,int sup) throws Exception{
		int n=1;
		ArrayList<ArticleV> value=new ArrayList<>();
		ArrayList<ArticleV> getAllList=getAll();
		for (ArticleV articleV : getAllList) {
			if(n<=sup && n>=inf) {
				value.add(articleV);
			}
			n++;
		}
		return value;
	}
	
}
