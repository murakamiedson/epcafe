package com.cafe.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.cafe.dao.MaquinaDAO;
import com.cafe.modelo.Maquina;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
public class MaquinaService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MaquinaDAO maquinaDAO;

	public void salvar(Maquina maquina) throws NegocioException {
		
		log.info("Service : tenant = " + maquina.getTenant_id());		
		this.maquinaDAO.salvar(maquina);
	}
	
	public void excluir(Maquina maquina) throws NegocioException {
		maquinaDAO.excluir(maquina);		
	}
		
	public Maquina buscarPeloCodigo(long codigo) {
		return maquinaDAO.buscarPeloCodigo(codigo);
	}	
	
	public List<Maquina> buscarMaquinas(Long tenantId) {
		
		log.info("Primeiro acesso a banco... buscar maquinas");					
		return maquinaDAO.buscarMaquinas(tenantId);
	}

	
	/* testes */
	
	public MaquinaDAO getMaquinaDAO() {
		return maquinaDAO;
	}

	public void setManager(EntityManager manager) {

		maquinaDAO = new MaquinaDAO();
		maquinaDAO.setEntityManager(manager);
		
	}

	

}