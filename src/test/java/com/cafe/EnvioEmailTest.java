package com.cafe;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.cafe.util.email.EMailUtil;
import com.cafe.util.email.MessageHtmlUtil;

import lombok.extern.log4j.Log4j;

@Log4j
public class EnvioEmailTest {
   	
	String msg;
    String email;
    List<String> destinatario = new ArrayList<>();
    
	@BeforeAll
	static void setUp() {
		log.info("Initializing ... ");
		
		

	}
	
	@AfterAll
	static void tearDown() {
		log.info("Closing ... ");

	}
	
	
	//@Test
	public void enviarEmailAutoCad() throws Exception {
		
		try {		
			
			msg = MessageHtmlUtil.msgAutoCadHtml("http://localhost:8080/unrestricted/ResetSenha.xhtml");
	        email = "murakami.edson@gmail.com";
	        destinatario.add(email);        
        
        	EMailUtil.sendHtmlEmail("SSL", destinatario, "Confirmação de Cadastro", msg);
			log.info("enviando... " );
	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void enviarEmailEsqueciSenha() throws Exception {
		
		try {		
			
			msg = MessageHtmlUtil.msgEsqueciSenhaHtml("Esqueci Senha", "http://localhost:8080");
	        email = "murakami.edson@gmail.com";
	        destinatario.add(email);        
        
        	EMailUtil.sendHtmlEmail("SSL", destinatario, "Confirmação de Senha", msg);
			log.info("enviando... " );
	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}