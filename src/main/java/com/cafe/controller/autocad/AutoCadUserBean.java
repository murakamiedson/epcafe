package com.cafe.controller.autocad;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.mail.EmailException;
import org.mindrot.jbcrypt.BCrypt;

import com.cafe.modelo.UsuarioTemp;
import com.cafe.service.AutoCadUserService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class AutoCadUserBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private UsuarioTemp usuarioTemp;

	@Inject
	private AutoCadUserService autoCadUserService;
	private String password;
	private String senha;
	private boolean ok = false;
	private boolean termoLido = false;

	@PostConstruct
	public void init() {
		usuarioTemp = new UsuarioTemp();
	}

	public void enviaEmail() throws EmailException {

		if (termoLido == false) {
			MessageUtil.erro("Por favor, confirme a leitura dos termos de serviço.");
		} else {
			log.info("iniciando envio de email");
			if (!password.equals(senha)) {
				MessageUtil.erro("As senhas não conferem!");
			} else {
				// verifica se já existe usuario. Se sim, direciona para login
				// (svsaHome)
				if (autoCadUserService.buscaEmail(usuarioTemp) != null) {
					MessageUtil.alerta("Esse e-mail já possui cadastro no sistema! Saia e faça login.");
					
				} else {
					// verifica se já existe usuarioTemp
					if (autoCadUserService.buscaEmailTemp(usuarioTemp) != null) {
						MessageUtil.alerta("E-mail já está cadastrado! Tente outro. O sistema não permite e-mails repetidos.");
					} else {
						try {
							// gera o token - chave de acesso
							usuarioTemp.setValidacao(RandomStringUtils.randomAlphanumeric(8));

							log.info("Token gerado: " + usuarioTemp.getValidacao());

							autoCadUserService.envia(usuarioTemp);
							MessageUtil.sucesso("E-mail com a chave de acesso enviado. A chave permanecerá válida por 30 minutos.");
							ok = true;
						} catch (EmailException e) {
							MessageUtil.erro(e.getMessage());
							e.printStackTrace();
						} catch (Exception e) {
							MessageUtil.erro("Problema no envio do e-mail. Tente mais tarde.");
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public void verificaToken() throws NegocioException {

		if (autoCadUserService.verificaToken(usuarioTemp)) {

			usuarioTemp.setSenha(BCrypt.hashpw(password, BCrypt.gensalt()));

			log.info("bean " + usuarioTemp.getEmail());
			log.info("bean -> service " + autoCadUserService);

			salvarEmail();

			direcionaParaCadSec();

		} else {
			MessageUtil.alerta("Chave de acesso inserida é inválida! Tente novamente.");
		}

	}

	private void salvarEmail() throws NegocioException {

		log.info("Salvando usuarioTemp..." + usuarioTemp.getId());
		log.info("email..." + usuarioTemp.getEmail());
		
		
		usuarioTemp = autoCadUserService.salva(usuarioTemp);
		
		MessageUtil.sucesso("Ok! Agora cadastre sua unidade produtiva.");
	}

	public void direcionaPolitica() {
		termoLido = true;

		try {
			getExternalContext().redirect(getExternalContext().getRequestContextPath()
					+ "/unrestricted/PoliticaPrivacidade.xhtml");
		} catch (IOException e) {
			MessageUtil
					.erro("Não foi possível abrir a política. Tente novamente.");
			e.printStackTrace();
		}
	}

	private void direcionaParaCadSec() {
		log.info("redirecionado para cadastro de propriedade, usuarioTemp id=" + getUsuarioTemp().getId());

		try {
			getExternalContext().redirect(getExternalContext().getRequestContextPath()
					+ "/unrestricted/AutoCadProp.xhtml?id=" + usuarioTemp.getId());
		} catch (IOException e) {
			MessageUtil.erro(
					"Não foi possível redirecionar para a pagina de cadastro de propriedade. Acesse a página inicial e conclua o cadastro.");
			e.printStackTrace();
		}
	}
	
	public String sair() {
		log.info("Sair...");   
		
		try {
			getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/Home.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/Home.xhtml";
	}
	

	private ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

}