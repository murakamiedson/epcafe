package com.cafe.dao;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.DespesaFerTalhao;
import com.cafe.modelo.DespesaFertilizante;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.to.DespesaFertilizanteDTO;
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
	public DespesaFerTalhao salvarQuantidadeTalhao(DespesaFerTalhao despesaFerTalhao)
			throws PersistenceException, NegocioException {
		try {
			return manager.merge(despesaFerTalhao);
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
	public void excluirQuantidadeTalhao(DespesaFerTalhao despesaFerTalhao) throws NegocioException {

		try {
			log.info("excluir quantidade talhao");
			DespesaFertilizante m = this.buscarPeloCodigo(despesaFerTalhao.getId());
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
	public List<DespesaFertilizante> buscarDespesasFertilizantes(Unidade unidade) {
		return manager.createNamedQuery("DespesaFertilizante.buscarDespesasFertilizantes")
				.setParameter("codigo_unidade", unidade).getResultList();
	}
	
	public List<DespesaFertilizante> buscarDespesasFertilizantesPorAno(LocalDate dataInicio, LocalDate dataFim, Unidade unidade){
		return manager.createQuery(
				"SELECT d FROM DespesaFertilizante d "
				+ "WHERE d.data "
				+ "BETWEEN :dataInicio AND :dataFim "
				+ "AND d.unidade = :codigo_unidade "
				+ "ORDER BY d.data", DespesaFertilizante.class)
				.setParameter("dataInicio", dataInicio)
				.setParameter("dataFim", dataFim)
				.setParameter("codigo_unidade", unidade)
				.getResultList();
	}
	
	public List<String> buscarAnosComRegistro (Unidade unidade) {
		
		List<String> anos = manager.createQuery(
				"SELECT DISTINCT " +
	                      "CASE " +
	                      "  WHEN FUNCTION('MONTH', c.data) >= 8 THEN CONCAT(FUNCTION('YEAR', c.data), '/', FUNCTION('YEAR', c.data) + 1) " +
	                      "  ELSE CONCAT(FUNCTION('YEAR', c.data) - 1, '/', FUNCTION('YEAR', c.data)) " +
	                      "END " +
	                      "FROM DespesaFertilizante c " +
	                      "ORDER BY " +
	                      "CASE " +
	                      "  WHEN FUNCTION('MONTH', c.data) >= 8 THEN CONCAT(FUNCTION('YEAR', c.data), '/', FUNCTION('YEAR', c.data) + 1) " +
	                      "  ELSE CONCAT(FUNCTION('YEAR', c.data) - 1, '/', FUNCTION('YEAR', c.data)) " +
	                      "END", String.class).getResultList();
		return anos;
	}
	
	/*
	 * Relatório
	 */
	
	public List<DespesaFertilizanteDTO> buscarDespesasDTO(LocalDate dataInicio, LocalDate dataFim, Unidade unidade){
		log.info("dataInicio: " + dataInicio);
		log.info("dataFim: " + dataFim);
		log.info("Unidade: " + unidade);
		List<DespesaFertilizanteDTO> lista = manager.createQuery(
				"SELECT new com.cafe.modelo.to.DespesaFertilizanteDTO( "
				+ "d.data, "
				+ "m.valor,"
				+ "t.nome,"
				+ "t.id) "
			+ "FROM DespesaFertilizante d "
			+ "  INNER JOIN DespesaFerTalhao m on d.id = m.despesaFertilizante "
			+ "	 INNER JOIN Talhao t on t.id = m.talhao "
			+ " WHERE "
			+ "  d.data BETWEEN :dataInicio AND :dataFim "
			+ "  and d.unidade = :unidade "
			+ " ORDER BY t.id"
				, DespesaFertilizanteDTO.class)
				.setParameter("dataInicio", dataInicio)
				.setParameter("dataFim", dataFim)
				.setParameter("unidade", unidade)
				.getResultList();
		
		return lista;
		
		
	}
	

}
