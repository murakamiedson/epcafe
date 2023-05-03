package com.cafe.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.Fabricante;
import com.cafe.modelo.Fertilizante;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

/**
 * @author joao
 *
 */
public class FertilizanteDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Transactional
	public void salvar(Fertilizante fertilizante) throws PersistenceException, NegocioException {
		try {
			manager.merge(fertilizante);	
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
	public void excluir(Fertilizante fertilizante) throws NegocioException {
			
		try {
			manager.remove(fertilizante);
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
	
	public Fertilizante buscarPeloCodigo(Long id) {
		return manager.find(Fertilizante.class, id);
	}	
	
	@SuppressWarnings("unchecked")
	public List<Fertilizante> buscarFertilizantes(Long tenantId) {
		return manager.createNamedQuery("Fertilizante.buscarFertilizantes")
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
	
	public List<Fertilizante> buscarFertilizantesPorFabricante(Fabricante fabricante, Long tenantId) {
		return manager.createNamedQuery("Fertilizante.buscarPorFabricante", Fertilizante.class)				
				.setParameter("fabricante", fabricante)
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
}
