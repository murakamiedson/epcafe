package com.cafe.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.Funcionario;
import com.cafe.modelo.Unidade;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

/**
 * @author isabella
 *
 */

public class FuncionarioDAO implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	
	@Transactional
	public void salvar(Funcionario funcionario) throws PersistenceException, NegocioException {
		try {
			manager.merge(funcionario);	
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
	public void excluir(Funcionario funcionario) throws NegocioException {
			
		try {
			Funcionario f = this.buscarPeloCodigo(funcionario.getId());
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
	
	public Funcionario buscarPeloCodigo(Long id) {
		return manager.find(Funcionario.class, id);
	}	
	
	@SuppressWarnings("unchecked")
	public List<Funcionario> buscarFuncionarios(Long tenantId) {
		return manager.createNamedQuery("Funcionario.buscarFuncionarios")
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
	
	public List<Funcionario> buscarFuncionariosPorUnidade(Unidade unidade, Long tenantId) {
		return manager.createNamedQuery("Funcionario.buscarPorUnidade", Funcionario.class)				
				.setParameter("unidade", unidade)
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
	
	// criado para realização de testes unitários com JIntegrity
	public void setEntityManager(EntityManager manager) {
		this.manager = manager;
	}


}
