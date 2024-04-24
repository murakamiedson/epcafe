package com.cafe.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.Talhao;
import com.cafe.modelo.Unidade;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

/**
 * @author joao
 *
 */

public class TalhaoDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Transactional
	public void salvar(Talhao talhao) throws PersistenceException, NegocioException {
		try {
			manager.merge(talhao);	
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
	public void excluir(Talhao talhao) throws NegocioException {
			
		talhao = buscarPeloCodigo(talhao.getId());
		try {
			manager.remove(talhao);
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
	
	public Talhao buscarPeloCodigo(Long id) {
		return manager.find(Talhao.class, id);
	}	
	
	@SuppressWarnings("unchecked")
	public List<Talhao> buscarTalhoes(Long tenantId) {
		return manager.createNamedQuery("Talhao.buscarTalhoes")
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
	
	public List<Talhao> buscarTalhoesPorUnidade(Unidade unidade, Long tenantId) {
		return manager.createNamedQuery("Talhao.buscarPorUnidade", Talhao.class)				
				.setParameter("unidade", unidade)
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
	
	// criado para realização de testes unitários com JIntegrity
	public void setEntityManager(EntityManager manager) {
		this.manager = manager;
	}
}
