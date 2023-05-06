package com.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.project.database.util.Connect;
import com.project.service.TokenManager;

public class Token {
	
	Integer id;
	String token;
	Integer idClient;
	Timestamp dateExp;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getIdClient() {
		return idClient;
	}
	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
	}
	public Timestamp getDateExp() {
		return dateExp;
	}
	public void setDateExp(Timestamp dateExp) {
		this.dateExp = dateExp;
	}
	
	public Token(Integer id, String token, Integer idClient, Timestamp dateExp) {
		super();
		this.id = id;
		this.token = token;
		this.idClient = idClient;
		this.dateExp = dateExp;
	}
	
	public Token() {
		super();
	}
	
	
	private void insert(int idClient) throws SQLException {
		Connection con=Connect.getConnection();
		Timestamp now=new Timestamp(System.currentTimeMillis());
		@SuppressWarnings("deprecation")
		Timestamp dateExp=new Timestamp(now.getYear(), now.getMonth(), now.getDate(), now.getHours()+2, now.getMinutes(), now.getSeconds(), now.getNanos());
		String req="insert into token(token,idClient,dateExp) values (?,?,?) returning id";
		try {
			PreparedStatement stmt=con.prepareStatement(req);
			stmt.setString(1, TokenManager.generateToken(idClient+""));
			stmt.setInt(2, idClient);
			stmt.setTimestamp(3, dateExp);
			System.out.println("Insert token  : "+stmt);
			ResultSet res=stmt.executeQuery();
			int id=0;
	        if(res.next()){
	            id=res.getInt("id");
	        }
	        stmt.close();
	        setId(id);
	        setDateExp(dateExp);
	        
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
		finally {
			con.close();
		}
	}
	
	private void update(int idClient) throws SQLException {
		Connection con=Connect.getConnection();
		Timestamp now=new Timestamp(System.currentTimeMillis());
		@SuppressWarnings("deprecation")
		Timestamp dateExp=new Timestamp(now.getYear(), now.getMonth(), now.getDate(), now.getHours()+2, now.getMinutes(), now.getSeconds(), now.getNanos());
		String req="update token set token=?,dateExp=? where idClient=?";
		try {
			PreparedStatement stmt=con.prepareStatement(req);
			stmt.setString(1, TokenManager.generateToken(idClient+""));
			stmt.setTimestamp(2, dateExp);
			stmt.setInt(3, idClient);
			stmt.execute();
			System.out.println("update token  : "+stmt);
	        stmt.close();
	        setDateExp(dateExp);
	        
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
		finally {
			con.close();
		}
	}
	
	public void save(int idClient) throws SQLException {
		Token token=select(idClient);
		if(token!=null) {
			token.update(idClient);
		}
		else {
			new Token().insert(idClient);
		}
	}
	
	
	
	public static Token select(String token) throws SQLException {
		Connection con=Connect.getConnection();
		Token value=null;
		token=token.replace("Bearer ", "");
        String req="select * from token where token=?";
        try {
			PreparedStatement stmt=con.prepareStatement(req);
			stmt.setString(1, token);
			System.out.println("Select :"+stmt);
			ResultSet res=stmt.executeQuery();
			while(res.next()) {
				value=new Token();
				value.setId(res.getInt("id"));
				value.setIdClient(res.getInt("idClient"));
				value.setToken(token);
				value.setDateExp(res.getTimestamp("dateExp"));
				System.out.println("Trouver");
			}
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
        finally {
			con.close();
		}
		return value;
	}
	
	public static Token select(int idClient) throws SQLException {
		Connection con=Connect.getConnection();
		Token value=null;
        String req="select * from token where idclient=?";
        try {
			PreparedStatement stmt=con.prepareStatement(req);
			stmt.setInt(1, idClient);
			System.out.println("Select :"+stmt);
			ResultSet res=stmt.executeQuery();
			while(res.next()) {
				value=new Token();
				value.setId(res.getInt("id"));
				value.setIdClient(res.getInt("idClient"));
				value.setToken(res.getString("token"));
				value.setDateExp(res.getTimestamp("dateExp"));
				System.out.println("Trouver");
			}
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
        finally {
			con.close();
		}
		return value;
	}
	
	public void delete() throws SQLException {
		Connection con=Connect.getConnection();
		String req="delete from token where token=?";
        try{
	        PreparedStatement stmt=con.prepareStatement(req);
	        stmt.setString(1,this.getToken());
	        stmt.execute();
	        stmt.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
		finally {
			con.close();
		}
	}
	
	
	
	public static boolean verifiExp(String token) throws SQLException {
		Token value=select(token);
        if(value==null) return false;
        
        Timestamp now= new Timestamp(System.currentTimeMillis());
    
        System.out.println("IL EST MAINTENANT ="+now.toString());
        System.out.println("IL SERA EXPIRE A  ="+value.getDateExp());
        if(now.before(value.getDateExp())){
            return true;  
        }
        else {
        	value.delete();
        }
		return false;
	}
	
	
	

}
