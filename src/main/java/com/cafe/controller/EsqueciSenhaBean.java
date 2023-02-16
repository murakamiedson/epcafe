package com.cafe.controller;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import com.cafe.service.EsqueciSenhaService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;
import com.cafe.util.email.EMailException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class EsqueciSenhaBean implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Inject
	private EsqueciSenhaService esqueciSenhaService;
	@Inject
	private LoginBean loginBean;

	private String novaSenha1;
	private String novaSenha2;
	private String email;
	private String token;
	private String senha;
	private Boolean valido = false;
	
	@PostConstruct
	public void inicializar() {	 		
		
		log.info("EsqueciSenhaBean inicializando...");
		log.info("ServerId " + loginBean.getServerId());		
		
	}

	public void esqueciSenhaEnviar()  {

		try {	
			
			esqueciSenhaService.novoToken(this.email, getContextPath());
			MessageUtil.sucesso("E-mail enviado. Verifique sua caixa de postal.");

		} catch (PersistenceException e) {
			MessageUtil.erro(e.getMessage());
			e.printStackTrace();
		} catch (EMailException e) {
			MessageUtil.erro(e.getMessage());
			e.printStackTrace();
		} catch (NegocioException e) {
			MessageUtil.erro(e.getMessage());
			e.printStackTrace();
		}	
	}

	public void validarToken() {
		try {
			log.info("token valido? " + valido);
			if (!this.valido) {
				Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
						.getRequestParameterMap();
				String token = params.get("token");
				if (esqueciSenhaService.esqueciSenhaValidar(token)) {
					this.token = token;
					this.valido = true;
				} else {
					throw new NegocioException("Token inválido e/ou expirado.");
				}
			}
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}

	public void enviar() {

		try {
			if (novaSenha1.compareTo(this.novaSenha2) == 0) {
				if (esqueciSenhaService.esqueciSenhaValidar(token)) {
					esqueciSenhaService.alterarSenhaBanco(this.token, this.novaSenha1);
					MessageUtil.sucesso("Senha redefinida com sucesso!");

				} else {
					throw new NegocioException("Token inválido e/ou expirado.");
				}

			} else {
				throw new NegocioException("As senhas não coincidem.");

			}
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());

		}
	}

	public String getContextPath() {
		
		return pegarDomainInstancia() + FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();		
	}
	
	public String pegarDomainInstancia() {
		
		HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		String host = null;
		String url = null;
		
		try {
			host = InetAddress.getLocalHost().getHostName();			
			url = origRequest.getScheme() + "://" + origRequest.getServerName() + ":" + origRequest.getServerPort();	
			log.info("LocalHost url inetadress: " + host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			host = System.getenv("HOSTNAME");
			url = origRequest.getScheme() + "://" + origRequest.getServerName() + ":" + origRequest.getServerPort();
			log.info("LocalHost url getEnv: " + host);
		}
		log.info("LocalHost url email: " + url);	
		

		return url;
	}
}
