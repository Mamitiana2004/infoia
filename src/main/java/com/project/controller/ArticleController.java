package com.project.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.project.model.Article;
import com.project.model.view.ArticleV;
import com.project.responseHandler.Success;
import com.project.service.ArticleService;

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
	
	@GetMapping("/getById/{id}")
	public String getById(@PathVariable("id") int id) throws Exception {
		Gson gson=new Gson();
		return gson.toJson(new ArticleV().getById(id));
	}
	
	
	
	
	@GetMapping("/count")
	public String count() throws Exception {
		Gson gson=new Gson();
		return gson.toJson(new Success(new ArticleV().count()));
	}
	
	
	@GetMapping("/search")
	public String article(String s,int d) throws SQLException {
		Gson gson=new Gson();
		ArrayList<Article> getArticles=ArticleService.search(s, d);
		return gson.toJson(getArticles);
	}
	
	@PostMapping("/add")
	public String insert(String title,String description,String content,String datePub,Integer domaine,@RequestParam("photo") MultipartFile photo) throws Exception {
		Gson gson=new Gson();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date dateN=format.parse(datePub);
		try {
			  byte[] bytes =photo.getBytes();
		      String base64 = Base64.encodeBase64String(bytes);
		      // Traitez le fichier ici
		      System.out.println(domaine);
		      Article a=new Article(title, domaine, description, content, dateN, base64);
		      a.save();
		      return gson.toJson(true);
		} 
		catch (Exception e) {
		      return gson.toJson(e.getMessage());
		}
	}
	
	@PostMapping("/delete")
	public String delete(int id) throws Exception {
		Gson gson=new Gson();
		Article a=new Article().getById(id);
		a.delete();
		return gson.toJson(true);
	}
	
	
}
