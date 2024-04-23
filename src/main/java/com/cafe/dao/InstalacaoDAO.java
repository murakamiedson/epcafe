package com.cafe.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.Instalacao;
import com.cafe.modelo.Unidade;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

/**
 * @author isabella
 *
 */

public class InstalacaoDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Transactional
	public void salvar(Instalacao instalacao) throws PersistenceException, NegocioException {
		try {
			manager.merge(instalacao);	
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
	public void excluir(Instalacao instalacao) throws NegocioException {
			
		instalacao = buscarPeloCodigo(instalacao.getId());
		try {
			manager.remove(instalacao);
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
	
	public Instalacao buscarPeloCodigo(Long id) {
		return manager.find(Instalacao.class, id);
	}	
	
	@SuppressWarnings("unchecked")
	public List<Instalacao> buscarInstalacoes(Long tenantId) {
		return manager.createNamedQuery("Instalacao.buscarInstalacoes")
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
	
	public List<Instalacao> buscarInstalacoesPorUnidade(Unidade unidade, Long tenantId) {
		return manager.createNamedQuery("Instalacao.buscarPorUnidade", Instalacao.class)				
				.setParameter("unidade", unidade)
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
	
	// criado para realização de testes unitários com JIntegrity
	public void setEntityManager(EntityManager manager) {
		this.manager = manager;
	}
}
