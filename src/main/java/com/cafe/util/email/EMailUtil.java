package com.cafe.util.email;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import lombok.extern.log4j.Log4j;


/**
 * Componente para envio de e-mails
 * Titan
 * GMail
 * @author murak
 *
 */
@Log4j
public class EMailUtil {
	
	
	private static Session getSession(String authenticator) {

		Session session;

		if (authenticator.equals("TLS"))
			session = getSessionTLS();
		else if (authenticator.equals("SSL"))
			session = getSessionSSL();
		else {
			/* sem autenticação */
			Properties props = System.getProperties();
			props.put("mail.smtp.host", smtpHostServer);
			session = Session.getInstance(props, null);
		}

		return session;
	}
	
	

	/**
	 * Utility method to send simple HTML email
	 *
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @throws EMailException 
	 */
	public static void sendEmail(String authenticator, String toEmail, String subject, String body) throws EMailException {

		try {
			MimeMessage msg = new MimeMessage(getSession(authenticator));
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));
			msg.setReplyTo(InternetAddress.parse(fromEmail, false));
			msg.setSubject(subject, "UTF-8");
			msg.setText(body, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			log.info("Message is ready");
			Transport.send(msg);

			log.info("EMail Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new EMailException("Não foi possível enviar o e-mail. Tente mais tarde. (Message: " + e.getMessage());
		}
	}

	public static void sendEmail(String authenticator, List<String> destinatarios, String subject, String body) throws EMailException {

		try {
			MimeMessage msg = new MimeMessage(getSession(authenticator));
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));
			msg.setReplyTo(InternetAddress.parse(fromEmail, false));
			msg.setSubject(subject, "UTF-8");
			msg.setText(body, "UTF-8");
			msg.setSentDate(new Date());

			for (String destinatario : destinatarios) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			}

			// msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail,
			// false));
			log.info("Message is ready");
			Transport.send(msg);

			log.info("EMails Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new EMailException("Não foi possível enviar o e-mail. Tente mais tarde. (Message: " + e.getMessage());
		}
	}

	/**
	 * Utility method to send HTML code as email content
	 * @throws NegocioException 
	 * @throws EMailException 
	 */
	public static void sendHtmlEmail(String authenticator, List<String> destinatarios, String subject, String body) throws EMailException {

		try {
			MimeMessage msg = new MimeMessage(getSession(authenticator));
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));
			msg.setReplyTo(InternetAddress.parse(fromEmail, false));
			msg.setSubject(subject, "UTF-8");
			msg.setContent(body, "text/html");
			msg.setSentDate(new Date());

			for (String destinatario : destinatarios) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			}

			// msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail,
			// false));
			log.info("Message is ready");
			Transport.send(msg);

			log.info("EMails Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new EMailException("Não foi possível enviar o e-mail. Tente mais tarde. (Message: " + e.getMessage());
		}
	}

	/**
	 * Utility method to send email with attachment
	 *
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @throws EMailException 
	 */
	public static void sendAttachmentEmail(String authenticator, String toEmail, String subject, String body,
			String filename) throws EMailException {

		try {
			MimeMessage msg = new MimeMessage(getSession(authenticator));
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));
			msg.setReplyTo(InternetAddress.parse(fromEmail, false));
			msg.setSubject(subject, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(body);

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Second part is attachment
			messageBodyPart = new MimeBodyPart();
			// String filename = "abc.txt";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			msg.setContent(multipart);

			// Send message
			Transport.send(msg);
			log.info("EMail Sent Successfully with attachment!!");
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new EMailException("Não foi possível enviar o e-mail. Tente mais tarde. (Message: " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new EMailException("Não foi possível enviar o e-mail. Tente mais tarde. (Message: " + e.getMessage());
		}
	}

	/**
	 * Utility method to send email with attachment
	 *
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @throws EMailException 
	 */
	public static void sendAttachmentEmail(String authenticator, List<String> destinatarios, String subject,
			String body, String filename) throws EMailException {
		try {
			MimeMessage msg = new MimeMessage(getSession(authenticator));
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));
			msg.setReplyTo(InternetAddress.parse(fromEmail, false));
			msg.setSubject(subject, "UTF-8");
			msg.setSentDate(new Date());

			for (String destinatario : destinatarios) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			}

			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(body);

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Second part is attachment
			messageBodyPart = new MimeBodyPart();
			// String filename = "abc.txt";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			msg.setContent(multipart);

			// Send message
			Transport.send(msg);
			log.info("EMails Sent Successfully with attachment!!");
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new EMailException("Não foi possível enviar o e-mail. Tente mais tarde. (Message: " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new EMailException("Não foi possível enviar o e-mail. Tente mais tarde. (Message: " + e.getMessage());
		}
	}

	/**
	 * Utility method to send image in email body
	 *
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @throws EMailException 
	 */
	public static void sendImageEmail(String authenticator, String toEmail, String subject, String body,
			String filename) throws EMailException {

		try {
			MimeMessage msg = new MimeMessage(getSession(authenticator));

			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

			msg.setReplyTo(InternetAddress.parse(fromEmail, false));

			msg.setSubject(subject, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText(body);

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Second part is image attachment
			messageBodyPart = new MimeBodyPart();
			// String filename = "image.png";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			// Trick is to add the content-id header here
			messageBodyPart.setHeader("Content-ID", "image_id");
			multipart.addBodyPart(messageBodyPart);

			// third part for displaying image in the email body
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent("<h1>Attached Image</h1>" + "<img src='cid:image_id'>", "text/html");
			multipart.addBodyPart(messageBodyPart);

			// Set the multipart message to the email message
			msg.setContent(multipart);

			// Send message
			Transport.send(msg);
			log.info("EMail Sent Successfully with image!!");
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new EMailException("Não foi possível enviar o e-mail. Tente mais tarde. (Message: " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new EMailException("Não foi possível enviar o e-mail. Tente mais tarde. (Message: " + e.getMessage());
		}
	}

	public static void sendImageEmail(String authenticator, List<String> destinatarios, String subject, String body,
			String filename) throws EMailException {

		try {
			MimeMessage msg = new MimeMessage(getSession(authenticator));

			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");

			msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

			msg.setReplyTo(InternetAddress.parse(fromEmail, false));

			msg.setSubject(subject, "UTF-8");

			msg.setSentDate(new Date());

			for (String destinatario : destinatarios) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			}

			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText(body);

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Second part is image attachment
			messageBodyPart = new MimeBodyPart();
			// String filename = "image.png";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			// Trick is to add the content-id header here
			messageBodyPart.setHeader("Content-ID", "image_id");
			multipart.addBodyPart(messageBodyPart);

			// third part for displaying image in the email body
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent("<h1>Attached Image</h1>" + "<img src='cid:image_id'>", "text/html");
			multipart.addBodyPart(messageBodyPart);

			// Set the multipart message to the email message
			msg.setContent(multipart);

			// Send message
			Transport.send(msg);
			log.info("EMails Sent Successfully with image!!");
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new EMailException("Não foi possível enviar o e-mail. Tente mais tarde. (Message: " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new EMailException("Não foi possível enviar o e-mail. Tente mais tarde. (Message: " + e.getMessage());
		}
	}

	
	/*
	 * Configurations Titan
	 * 
	 * Outgoing Mail (SMTP) Server requires TLS or SSL: 
	 * Use Authentication: Yes Port for SSL: 465
	 * Use Authentication: Yes Port for TLS/STARTTLS: 587
	 *
	 
	private static final String fromEmail = "no_reply@gaian.com.br"; // requires valid id
	private static final String password = "R35rB@r9EQb7#Qs"; // correct password for id
	private static final String smtpHostServer = "smtp.titan.email"; // smtp host server
		

	private static Session getSessionTLS() {

		log.info("TLSEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHostServer); // SMTP Host
		props.put("mail.smtp.port", "587"); // TLS Port
		props.put("mail.smtp.auth", "true"); // enable authentication
		props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

		// create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};

		return Session.getInstance(props, auth);
	}
	
	private static Session getSessionSSL() {

		log.info("SSLEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHostServer); // SMTP Host
		props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
		props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
		props.put("mail.smtp.port", "465"); // SMTP Port

		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};

		return Session.getDefaultInstance(props, auth);
	}
	*/
	
	
	/*
	 * Configurations GMail É necessário ter uma conta com senha de App
	 * https://support.google.com/accounts/answer/185833?p=InvalidSecondFactor
	 * Outgoing Mail (SMTP) Server requires TLS or SSL: 
	 * Use Authentication: Yes Port for SSL: 465
	 * Use Authentication: Yes Port for TLS/STARTTLS: 587
	 */
	private static final String fromEmail = "svsadesk@gmail.com"; // requires valid id
	private static final String password = "cygbeuzwliarwurv"; // correct password for id
	private static final String smtpHostServer = "smtp.gmail.com"; // smtp host server

	private static Session getSessionTLS() {

		Properties prop = new Properties();
		prop.put("mail.smtp.host", smtpHostServer);
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); // TLS

		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};

		return Session.getDefaultInstance(prop, auth);
	}

	private static Session getSessionSSL() {

		Properties prop = new Properties();
		prop.put("mail.smtp.host", smtpHostServer);
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Authenticator auth = new Authenticator() {
			// override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};

		return Session.getDefaultInstance(prop, auth);
	}
	

}