package com.cafe.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;

import com.cafe.dao.CadastroTenantDAO;
import com.cafe.modelo.Usuario;
import com.cafe.modelo.to.CadastroTenantTO;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
public class CadastroTenantService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CadastroTenantDAO autocadDAO;
	
	@PostConstruct
	public void init() {
		log.info("AutocadService");
	}	
	
	//salvar no banco
	public Usuario salvar(CadastroTenantTO autocadTO) throws NegocioException {
		
		Usuario usuario = autocadTO.getUsuario();
		
		if(usuario.getSenha() == null || usuario.getSenha().equals(""))
			usuario.setSenha(BCrypt.hashpw("123456", BCrypt.gensalt()));
		
		autocadTO.setUsuario(usuario);
		
		return this.autocadDAO.salvar(autocadTO);
	}
	
}