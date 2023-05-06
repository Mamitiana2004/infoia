package com.project.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.project.model.view.ArticleV;
import com.project.responseHandler.Success;

@CrossOrigin
@RestController	
@RequestMapping("/article")
public class ArticleController {

	@GetMapping("/getAll")
	public String getAll() throws Exception {
		Gson gson=new Gson();
		ArrayList<ArticleV> getArticles=new ArticleV().getAll();
		return gson.toJson(new Success(getArticles));
	}
	
	@GetMapping("/getAll/{id}")
	public String getAllLimite(@PathVariable("id") int id) throws Exception {
		Gson gson=new Gson();
		ArrayList<ArticleV> getArticles=new ArticleV().getAllLimite(id);
		return gson.toJson(new Success(getArticles));
	}
	
	@GetMapping("/get/{id}")
	public String getAllLimite(@PathVariable("id") String id) throws Exception {
		Gson gson=new Gson();
		int sup=Integer.parseInt(id.split("-")[1]);
		int inf=Integer.parseInt(id.split("-")[0]);
		ArrayList<ArticleV> getArticles=new ArticleV().getAllEntre(inf,sup);
		return gson.toJson(getArticles);
	}
	
	
	@GetMapping("/count")
	public String count() throws Exception {
		Gson gson=new Gson();
		return gson.toJson(new Success(new ArticleV().count()));
	}
	
	
	
	
}
