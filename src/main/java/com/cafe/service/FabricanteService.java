package com.cafe.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.cafe.dao.FabricanteDAO;
import com.cafe.modelo.Fabricante;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
public class FabricanteService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FabricanteDAO fabricanteDAO;

	public void salvar(Fabricante fabricante) throws NegocioException {
		
		log.info("Service : tenant = " + fabricante.getTenant_id());	
		
		this.fabricanteDAO.salvar(fabricante);
	}
	
	
	public Fabricante buscarPeloCodigo(long codigo) {
		return fabricanteDAO.buscarPeloCodigo(codigo);
	}
	

	public List<Fabricante> buscarTodos(Long tenantId) {
		return fabricanteDAO.buscarTodos(tenantId);
	}	
	
	public void excluir(Fabricante fabricante) throws NegocioException {
		fabricanteDAO.excluir(fabricante);
		
	}
	
	public List<Fabricante> buscarFabricantes(Long tenantId) {	
		log.info("Primeiro acesso a banco... buscar unidades");
					
		return fabricanteDAO.buscarTodos(tenantId);
	}

	public FabricanteDAO getFabricanteDAO() {
		return fabricanteDAO;
	}

	public void setManager(EntityManager manager) {

		fabricanteDAO = new FabricanteDAO();
		fabricanteDAO.setEntityManager(manager);
		
	}

}