package com.cafe.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.FuncionarioDAO;
import com.cafe.dao.UnidadeDAO;
import com.cafe.modelo.Funcionario;
import com.cafe.modelo.Unidade;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author isabella
 *
 */

@Log4j
public class FuncionarioService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FuncionarioDAO funcionarioDAO;
	@Inject
	private UnidadeDAO unidadeDAO;

	public void salvar(Funcionario funcionario) throws NegocioException {
		
		log.info("Service : tenant = " + funcionario.getTenant_id());		
		this.funcionarioDAO.salvar(funcionario);
	}
	
	public void excluir(Funcionario funcionario) throws NegocioException {
		funcionarioDAO.excluir(funcionario);		
	}
		
	public Funcionario buscarPeloCodigo(long codigo) {
		return funcionarioDAO.buscarPeloCodigo(codigo);
	}	
	
	public List<Funcionario> buscarFuncionarios(Long tenantId) {
		
		log.info("Primeiro acesso a banco... buscar funcionarios");					
		return funcionarioDAO.buscarFuncionarios(tenantId);
	}
	
	public List<Funcionario> buscarFuncionariosPorUnidade(Unidade unidade, Long tenantId) {
		
		return this.funcionarioDAO.buscarFuncionariosPorUnidade(unidade, tenantId);
	}
	
/* Unidades */
	
	public List<Unidade> buscarUnidades(Long tenantId) {		
		return unidadeDAO.buscarTodos(tenantId);
	}

}
