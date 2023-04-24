package com.cafe.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.Fabricante;
import com.cafe.modelo.Insumo;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

/**
 * @author joao
 *
 */
public class InsumoDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Transactional
	public void salvar(Insumo insumo) throws PersistenceException, NegocioException {
		try {
			manager.merge(insumo);	
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
	public void excluir(Insumo insumo) throws NegocioException {
			
		try {
			manager.remove(insumo);
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
	
	public Insumo buscarPeloCodigo(Long id) {
		return manager.find(Insumo.class, id);
	}	
	
	@SuppressWarnings("unchecked")
	public List<Insumo> buscarInsumos(Long tenantId) {
		return manager.createNamedQuery("Insumo.buscarInsumos")
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
	
	public List<Insumo> buscarInsumosPorFabricante(Fabricante fabricante, Long tenantId) {
		return manager.createNamedQuery("Insumo.buscarPorFabricante", Insumo.class)				
				.setParameter("fabricante", fabricante)
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
}
