package com.project.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	public static void sendConfirmationEmail(String recipientEmail, String messageText) throws Exception {
	      // Paramètres SMTP pour l'envoi d'email
	      String host = "smtp.gmail.com";
	      String username = "mamitianafaneva2004@gmail.com";
	      String password = "wykfjivxbtkdqkin";
	      int port = 587;

	      // Propriétés pour la session SMTP
	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", port);

	      // Création de la session SMTP	
	      Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	         }
	      });
	      
	       // Création du message email
	      Message message = new MimeMessage(session);
	      message.setFrom(new InternetAddress(username,"Project"));
	      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
	      message.setText(messageText);
	      message.setSubject("Confirmation de votre inscription");
	      
	      // Envoi du message email
	      Transport.send(message);
	      System.out.println("Le message a été envoyé avec succès à " + recipientEmail);
	   }	
	
	
}
