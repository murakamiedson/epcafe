package com.cafe.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.FertilizanteDAO;
import com.cafe.modelo.Fertilizante;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author joao
 *
 */
@Log4j
public class FertilizanteService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private FertilizanteDAO fertilizanteDAO;
	
	
	public void salvar(Fertilizante fertilizante) throws NegocioException {
		
		log.info("Service : tenant = " + fertilizante.getTenant_id());
		
		this.fertilizanteDAO.salvar(fertilizante);
	}
	
	public void excluir(Fertilizante fertilizante) throws NegocioException {
		
		fertilizanteDAO.excluir(fertilizante);		
	}
		
	public Fertilizante buscarPeloCodigo(long codigo) {
		
		return fertilizanteDAO.buscarPeloCodigo(codigo);
	}	
	
	public List<Fertilizante> buscarFertilizantes(Long tenantId) {
		
		log.info("Primeiro acesso a banco... buscar fertilizantes");	
		
		return fertilizanteDAO.buscarFertilizantes(tenantId);
	}

}
