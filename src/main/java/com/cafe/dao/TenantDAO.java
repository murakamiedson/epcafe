package com.cafe.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.Tenant;
import com.cafe.util.NegocioException;

/**
 * @author murakamiadmin
 *
 */
public class TenantDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;	
	
	
	/*
	 * Buscas
	 */
	
	
	public Tenant buscarPeloCodigo(Long codigo) {
		return manager.find(Tenant.class, codigo);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tenant> buscarTodos() {
		return manager.createNamedQuery("Tenant.buscarTodos")
				.getResultList();
	}
		
	
	// criado para realização de testes unitários com JIntegrity
	public void setEntityManager(EntityManager manager) {
		this.manager = manager;
	}

	public Tenant alterar(Tenant tenant) throws NegocioException {
		
		try {
			Tenant t = manager.merge(tenant);
			return t;
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
}