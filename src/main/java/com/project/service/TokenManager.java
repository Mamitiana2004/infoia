package com.project.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.project.util.Cryptage;

public class TokenManager {

    static String[] req;
    
    public static  int genererInt(int borneInf, int borneSup){
        Random random = new Random();
        int nb;
        nb = borneInf+random.nextInt(borneSup-borneInf);
        return nb;
    }
			
	public static String generateToken(String id) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        String url="strumph/age de glace/alerte rouge/barea/election/hahahhahha/fifa/oshiiiie/odye/bisous";
        req=url.split("/");
        int numero= genererInt(0, req.length-1);
        return Cryptage.SHA1(req[numero]+id+"  "+System.currentTimeMillis());
    }
	
	
}
