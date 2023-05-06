package com.project.service;

import com.project.model.Admin;
import com.project.util.Cryptage;

public class AdminService {

	public static int login(String user,String password) throws Exception {
		Admin admin=new Admin().getByNom(user);
		if(admin!=null) {
			System.out.println(Cryptage.Md5(password));
			if(admin.getPassword().compareTo(Cryptage.Md5(password))==0) {
				return 0;
			}
			else {
				return 1;
			}
		}
		return 2;
	}
	
}
