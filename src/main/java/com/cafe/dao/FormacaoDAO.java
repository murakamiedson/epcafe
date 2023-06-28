package com.cafe.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.Formacao;
import com.cafe.modelo.Funcionario;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

/**
 * @author isabella
 *
 */

public class FormacaoDAO implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	
	@Transactional
	public void salvar(Formacao formacao) throws PersistenceException, NegocioException {
		try {
			manager.merge(formacao);	
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Error e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		}
	}	
		
	@Transactional
	public void excluir(Formacao formacao) throws NegocioException {
			
		try {
			Formacao f = this.buscarPeloCodigo(formacao.getId());
			manager.remove(f);
			manager.flush();
		} catch (PersistenceException e) {			
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Error e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		}
	}
	
	
	
	/*
	 * Buscas
	 */
	
	public Formacao buscarPeloCodigo(Long id) {
		return manager.find(Formacao.class, id);
	}	
	
	@SuppressWarnings("unchecked")
	public List<Formacao> buscarFormacoes(Long tenantId) {
		return manager.createNamedQuery("Formacao.buscarFormacoes")
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
	
	public List<Formacao> buscarFormacoesPorFuncionario(Funcionario funcionario, Long tenantId) {
		return manager.createNamedQuery("Formacao.buscarPorFuncionario", Formacao.class)				
				.setParameter("funcionario", funcionario)
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
	
	// criado para realização de testes unitários com JIntegrity
	public void setEntityManager(EntityManager manager) {
		this.manager = manager;
	}


}
