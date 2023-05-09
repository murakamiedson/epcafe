package com.cafe.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.Maquina;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

/**
 * @author murakamiadmin
 *
 */
public class MaquinaDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	
	@Transactional
	public void salvar(Maquina maquina) throws PersistenceException, NegocioException {
		try {
			manager.merge(maquina);	
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
	public void excluir(Maquina maquina) throws NegocioException {
			
		try {
			Maquina m = this.buscarPeloCodigo(maquina.getId());
			manager.remove(m);
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
	
	public Maquina buscarPeloCodigo(Long id) {
		return manager.find(Maquina.class, id);
	}	
	
	@SuppressWarnings("unchecked")
	public List<Maquina> buscarMaquinas(Long tenantId) {
		return manager.createNamedQuery("Maquina.buscarMaquinas")
				.setParameter("tenantId", tenantId)
				.getResultList();
	}

	
	
	
	// criado para realização de testes unitários com JIntegrity
	public void setEntityManager(EntityManager manager) {
		this.manager = manager;
	}
}