package com.project.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.project.model.Article;
import com.project.model.Domaine;

@CrossOrigin
@RestController	
@RequestMapping("/domaine")
public class DomaineController {

	@GetMapping("/getAll")
	public String getAllDomaine() throws Exception {
		Gson gson=new Gson();
		ArrayList<Domaine> getDomaines=new Domaine().getAll();
		return gson.toJson(getDomaines);
	}
	
	@PostMapping("/add")
	public String insert(String domaine) throws Exception {
		Gson gson=new Gson();
		Domaine d=new Domaine();
		d.setType(domaine);
		d.save();
		return gson.toJson(true);
	}
	
	@PostMapping("/delete")
	public String delete(int id) throws Exception {
		Gson gson=new Gson();
		Domaine a=new Domaine();
		a.setId(id);	
		a.delete();
		return gson.toJson(true);
	}
	
}
