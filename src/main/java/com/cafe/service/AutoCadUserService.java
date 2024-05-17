package com.cafe.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.mail.EmailException;

import com.cafe.dao.AutoCadUserDAO;
import com.cafe.modelo.Usuario;
import com.cafe.modelo.UsuarioTemp;
import com.cafe.util.NegocioException;
import com.cafe.util.email.EMailException;
import com.cafe.util.email.EMailUtil;
import com.cafe.util.email.MessageHtmlUtil;

public class AutoCadUserService implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Inject
	private AutoCadUserDAO autoCadUserDAO;
    String msgCorpo;
    String email;
    List<String> destinatario = new ArrayList<>();

    public UsuarioTemp buscaEmailTemp(UsuarioTemp usuarioTemp) {
        return autoCadUserDAO.buscaEmailTemp(usuarioTemp);
    }
    public Usuario buscaEmail(UsuarioTemp usuarioTemp) {
        return autoCadUserDAO.buscaEmail(usuarioTemp);
    }

    public void envia(UsuarioTemp usuarioTemp) throws EmailException, EMailException {
    	
    	try {
	        msgCorpo = MessageHtmlUtil.msgAutoCadHtml(usuarioTemp.getValidacao());
	        email = usuarioTemp.getEmail();
	        destinatario.add(email);        
        
        	EMailUtil.sendHtmlEmail("SSL", destinatario, "Confirmação de Cadastro", msgCorpo);
        } catch(EMailException e) {
        	throw e;
        }
    }
    public Boolean verificaToken(UsuarioTemp usuarioTemp){
        return usuarioTemp.getValidacao().equals(usuarioTemp.getToken());
    }
    public UsuarioTemp salva(UsuarioTemp usuarioTemp) throws NegocioException {
        return autoCadUserDAO.salvar(usuarioTemp);
    }
   
}
