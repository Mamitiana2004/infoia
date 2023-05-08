package com.project.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.project.database.util.Connect;
import com.project.model.Article;

public class ArticleService {

	public static ArrayList<Article> search(String recherche,int domaine) throws SQLException{
		ArrayList<Article> value=new ArrayList<>();
		Connection con=Connect.getConnection();
		try {
			PreparedStatement stmt=con.prepareStatement(Article.search(recherche, domaine));
			ResultSet res=stmt.executeQuery();
			while(res.next()) {
				Article article=new Article().getById(res.getInt("id"));
				value.add(article);
			}
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			con.close();
		}
		return value;
	}
	
}
