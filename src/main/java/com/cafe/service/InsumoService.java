package com.cafe.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.FabricanteDAO;
import com.cafe.dao.InsumoDAO;
import com.cafe.modelo.Fabricante;
import com.cafe.modelo.Insumo;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author joao
 *
 */
@Log4j
public class InsumoService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private InsumoDAO insumoDAO;
	
	@Inject
	private FabricanteDAO fabricanteDAO;
	
	public void salvar(Insumo insumo) throws NegocioException {
		
		log.info("Service : tenant = " + insumo.getTenant_id());
		
		this.insumoDAO.salvar(insumo);
	}
	
	public void excluir(Insumo insumo) throws NegocioException {
		
		insumoDAO.excluir(insumo);		
	}
		
	public Insumo buscarPeloCodigo(long codigo) {
		
		return insumoDAO.buscarPeloCodigo(codigo);
	}	
	
	public List<Insumo> buscarInsumos(Long tenantId) {
		
		log.info("Primeiro acesso a banco... buscar insumos");	
		
		return insumoDAO.buscarInsumos(tenantId);
	}

	/* Fabricantes */
	
	public List<Fabricante> buscarFabricantesDeInsumos(Long tenantId) {	
		
		return fabricanteDAO.buscarFabricantesDeInsumos(tenantId);
	}
}
