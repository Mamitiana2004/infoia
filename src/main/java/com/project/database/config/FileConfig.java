/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.database.config;

import com.project.database.exception.DatabaseFileException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author mamit
 */
public class FileConfig {
    private String path;
    
    private void findFile(File directory){
        boolean find=false;
        File[] files=directory.listFiles();
        for (File file : files) {
            if(file.isDirectory()){
                if(!file.getName().contains(".")){
                    findFile(file);
                }
            }
            else{
                if(file.getName().equalsIgnoreCase("database.ini")){
                    find=true;
                    path=file.getAbsolutePath();
                }
            }
        }
    }
    
    public FileConfig() throws DatabaseFileException{
        File directory=new File(".");
        findFile(directory);
        if(path==null){
            throw new DatabaseFileException("The file database.ini is not in this project");
        }
    }
    
	public String get(String property) throws DatabaseFileException{
	    File file=new File(path);
	    try {
	        Scanner scan=new Scanner(file);
	        while(scan.hasNext()){
	            String line=scan.next();
	            if(line.split("=")[0].equalsIgnoreCase(property)){
	                return line.split("=")[1];
	            }
	        }
	    } catch (FileNotFoundException e) {
	        System.err.println(e.getMessage());
	    }
	    throw new DatabaseFileException("The property '"+property+"' is not in the file database.ini");
	}

    
}
