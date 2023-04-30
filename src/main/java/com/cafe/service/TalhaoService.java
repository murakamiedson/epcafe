package com.cafe.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.TalhaoDAO;
import com.cafe.dao.UnidadeDAO;
import com.cafe.modelo.Talhao;
import com.cafe.modelo.Unidade;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author joao
 *
 */
@Log4j
public class TalhaoService implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Inject
	private TalhaoDAO talhaoDAO;
	@Inject
	private UnidadeDAO unidadeDAO;

	public void salvar(Talhao talhao) throws NegocioException {
		
		log.info("Service : tenant = " + talhao.getTenant_id());		
		this.talhaoDAO.salvar(talhao);
	}
	
	public void excluir(Talhao talhao) throws NegocioException {
		this.talhaoDAO.excluir(talhao);		
	}
		
	public Talhao buscarPeloCodigo(long codigo) {
		return this.talhaoDAO.buscarPeloCodigo(codigo);
	}	
	
	public List<Talhao> buscarTalhoes(Long tenantId) {
		
		log.info("Primeiro acesso a banco... buscar talhões");					
		return this.talhaoDAO.buscarTalhoes(tenantId);
	}
	
	public List<Talhao> buscarTalhoesPorUnidade(Unidade unidade, Long tenantId) {
					
		return this.talhaoDAO.buscarTalhoesPorUnidade(unidade, tenantId);
	}

	/* Unidades */
	
	public List<Unidade> buscarUnidades(Long tenantId) {		
		return unidadeDAO.buscarTodos(tenantId);
	}

	
}
