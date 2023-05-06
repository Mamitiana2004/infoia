package com.project.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
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
	
	
	
}
