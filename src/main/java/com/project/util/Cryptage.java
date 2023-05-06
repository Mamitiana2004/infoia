package com.project.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mamit
 */
public class Cryptage    {
    
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
    
    public static String SHA1(String text)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
    
    public static String Md5(String input)  {  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            byte[] messageDigest = md.digest(input.getBytes());  
            BigInteger no = new BigInteger(1, messageDigest);  
            String hashtext = no.toString(16);  
            while (hashtext.length() < 32)   {  
            hashtext = "0" + hashtext;  
        }  
            return hashtext;  
        }  
        catch (NoSuchAlgorithmException e)   {  
            throw new RuntimeException(e);  
        }  
    } 
    
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
