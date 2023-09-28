package com.cafe.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.DespesaFertilizante;
import com.cafe.modelo.QuantidadeTalhao;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

import lombok.extern.log4j.Log4j;

/**
 * @author isabella
 *
 */

@Log4j
public class DespesaFertilizanteDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Transactional
	public DespesaFertilizante salvar(DespesaFertilizante despesaFertilizante)
			throws PersistenceException, NegocioException {
		try {
			return manager.merge(despesaFertilizante);
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
	public void excluir(DespesaFertilizante despesaFertilizante) throws NegocioException {

		try {
			log.info("excluir dao");
			DespesaFertilizante m = this.buscarPeloCodigo(despesaFertilizante.getId());
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

	@Transactional
	public QuantidadeTalhao salvarQuantidadeTalhao(QuantidadeTalhao quantidadeTalhao)
			throws PersistenceException, NegocioException {
		try {
			return manager.merge(quantidadeTalhao);
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
	public void excluirQuantidadeTalhao(QuantidadeTalhao quantidadeTalhao) throws NegocioException {

		try {
			log.info("excluir quantidade talhao");
			DespesaFertilizante m = this.buscarPeloCodigo(quantidadeTalhao.getId());
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

	public DespesaFertilizante buscarPeloCodigo(Long id) {
		return manager.find(DespesaFertilizante.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<DespesaFertilizante> buscarDespesasFertilizantes(Long tenantId) {
		return manager.createNamedQuery("DespesaFertilizante.buscarDespesasFertilizantes")
				.setParameter("tenantId", tenantId).getResultList();
	}

}
