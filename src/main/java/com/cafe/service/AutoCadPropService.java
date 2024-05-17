package com.cafe.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.cafe.dao.AutoCadPropDAO;
import com.cafe.dao.AutoCadUserDAO;
import com.cafe.modelo.Usuario;
import com.cafe.modelo.UsuarioTemp;
import com.cafe.modelo.to.AutoCadPropTO;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
public class AutoCadPropService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AutoCadPropDAO autocadDAO;
	@Inject
	private AutoCadUserDAO userDAO;

	@PostConstruct
	public void init() {
		log.info("AutocadService");
	}	
	
	//salvar no banco
	public Usuario salvar(AutoCadPropTO autocadTO) throws NegocioException {
		return this.autocadDAO.salvar(autocadTO);
	}
	
	public UsuarioTemp buscarUserPeloCodigo(Long id) {
		return userDAO.buscarPeloCodigo(id);
	}
	
}