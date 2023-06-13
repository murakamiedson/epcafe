package com.cafe.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.FormacaoDAO;
import com.cafe.modelo.Formacao;
import com.cafe.modelo.Funcionario;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
public class FormacaoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FormacaoDAO formacaoDAO;

	public void salvar(Formacao formacao) throws NegocioException {
		
		log.info("Service : tenant = " + formacao.getTenant_id());		
		this.formacaoDAO.salvar(formacao);
	}
	
	public void excluir(Formacao formacao) throws NegocioException {
		formacaoDAO.excluir(formacao);		
	}
		
	public Formacao buscarPeloCodigo(long codigo) {
		return formacaoDAO.buscarPeloCodigo(codigo);
	}	
	
	public List<Formacao> buscarFormacoes(Long tenantId) {
		
		log.info("Primeiro acesso a banco... buscar formacoes");					
		return formacaoDAO.buscarFormacoes(tenantId);
	}
	
	public List<Formacao> buscarFormacoesPorFuncionario(Funcionario funcionario, Long tenantId) {
		
		return this.formacaoDAO.buscarFormacoesPorFuncionario(funcionario, tenantId);
	}

	

}