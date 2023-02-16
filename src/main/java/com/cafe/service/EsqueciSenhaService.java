package com.cafe.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.mindrot.jbcrypt.BCrypt;

import com.cafe.dao.EsqueciSenhaDAO;
import com.cafe.dao.UsuarioDAO;
import com.cafe.modelo.EsqueciSenha;
import com.cafe.modelo.Usuario;
import com.cafe.util.DateUtils;
import com.cafe.util.NegocioException;
import com.cafe.util.email.EMailException;
import com.cafe.util.email.EMailUtil;
import com.cafe.util.email.MessageHtmlUtil;

import lombok.extern.log4j.Log4j;

@Log4j
public class EsqueciSenhaService implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EsqueciSenhaDAO esqueciSenhaDAO;
	@Inject 
	private UsuarioDAO usuarioDAO;

	
	public void salvar(EsqueciSenha esqueciSenha) throws NegocioException {
		this.esqueciSenhaDAO.salvar(esqueciSenha);
	}
	
	public void novoToken(String email, String contextPath) throws PersistenceException, NegocioException, EMailException {
		
		EsqueciSenha esqc = null;
		
		try {
			log.info("novo token");
			try {
				this.usuarioDAO.buscarPeloEmail(email);
			} catch(NoResultException e) {
				throw new NegocioException("Este e-mail não está cadastrado na base!");
			}
			
			esqc = esqueciSenhaDAO.buscarEsqueciSenhaPeloEmail(email);
			
			if(this.verificaTokenValido(esqc)) {
				throw new NegocioException("Já existe um token válido para este email, verifique sua caixa de entrada.");
			}else {
				this.criaTokenMandaEmail(esqc, contextPath);
			}
		} catch(NoResultException e) {

			esqc = new EsqueciSenha();
			esqc.setEmail(email);
			
			this.criaTokenMandaEmail(esqc, contextPath);	
			
		} catch(NegocioException e) {
			throw e;
		}

	}
	
	public void criaTokenMandaEmail(EsqueciSenha esqc, String contextPath) throws PersistenceException, NegocioException, EMailException {
		
		log.info("MÉTODO DE ENVIAR EMAILS.");
		UUID token = UUID.randomUUID();
		esqc.setToken(token.toString());
		esqc.setIsExpired(false);
		this.esqueciSenhaDAO.salvar(esqc);
		
		List<String> destinatario = new ArrayList<>();
		destinatario.add(esqc.getEmail());
		String assunto = "Redefinir senha";		
		String msg = MessageHtmlUtil.msgEsqueciSenhaHtml(esqc.getToken(), contextPath);		
		
		log.info(contextPath);
		
		EMailUtil.sendHtmlEmail("SSL", destinatario, assunto, msg);
		
	}
	
	public long calcularMinutos(LocalDateTime data) {
		long diff = ChronoUnit.MINUTES.between(data, LocalDateTime.now());
			return diff;
	}
	
	public Boolean verificaTokenValido(EsqueciSenha esqc) {
		
		log.info("ESTÁ EXPIRADO? " + esqc.getIsExpired() 
			+ "TEMPO VALIDO?: " + (this.calcularMinutos(DateUtils.asLocalDateTime(esqc.getDataReferencia())) < 15) );
		
		if ((esqc.getIsExpired() == false) && 
				(this.calcularMinutos(DateUtils.asLocalDateTime(esqc.getDataReferencia())) < 15)) {
			return true;
		}
		else {
			return false; 
		}
	}
	
	public Boolean esqueciSenhaValidar(String token){
		try {
			EsqueciSenha esqc = this.esqueciSenhaDAO.buscarEsqueciSenhaPeloToken(token);
			
			if(this.verificaTokenValido(esqc)) {
				return true;
			}
			else {
				return false;
			}
		} catch(NoResultException e) {
			return false;
		}
	}
	
	public void alterarSenhaBanco(String token, String novaSenha) throws PersistenceException, NegocioException {
		
		Usuario userAlterado = this.usuarioDAO.buscarPeloEmail(this.esqueciSenhaDAO.buscarEsqueciSenhaPeloToken(token).getEmail());
		userAlterado.setSenha(BCrypt.hashpw(novaSenha, BCrypt.gensalt()));
		
		usuarioDAO.salvar(userAlterado);
		
		EsqueciSenha esqc = esqueciSenhaDAO.buscarEsqueciSenhaPeloEmail(esqueciSenhaDAO.buscarEmailPeloToken(token));
		
		esqc.setIsExpired(true);
		esqueciSenhaDAO.salvar(esqc);
	}
	

}
