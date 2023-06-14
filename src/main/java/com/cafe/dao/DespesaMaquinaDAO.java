package com.cafe.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.DespesaMaquina;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

/**
 * @author isabella
 *
 */


public class DespesaMaquinaDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Transactional
	public void salvar(DespesaMaquina despesaMaquina) throws PersistenceException, NegocioException {
		try {
			manager.merge(despesaMaquina);	
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
	public void excluir(DespesaMaquina despesaMaquina) throws NegocioException {
			
		try {
			DespesaMaquina m = this.buscarPeloCodigo(despesaMaquina.getId());
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
	
	public DespesaMaquina buscarPeloCodigo(Long id) {
		return manager.find(DespesaMaquina.class, id);
	}	
	
	@SuppressWarnings("unchecked")
	public List<DespesaMaquina> buscarDespesasMaquinas(Long tenantId) {
		return manager.createNamedQuery("DespesaMaquina.buscarDespesasMaquinas")
				.setParameter("tenantId", tenantId)
				.getResultList();
	}


}
