package com.cafe.dao;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.DespesaCusteioOutras;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.to.OutrasDespesasDTO;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
public class DespesaCusteioOutrasDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	@Transactional
	public DespesaCusteioOutras salvar(DespesaCusteioOutras despesaCusteioOutras)
			throws PersistenceException, NegocioException {
		try {
			return manager.merge(despesaCusteioOutras);
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
	public void excluir(DespesaCusteioOutras despesaCusteioOutras) throws NegocioException {

		try {
			log.info("excluir dao");
			DespesaCusteioOutras m = this.buscarPeloCodigo(despesaCusteioOutras.getId());
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
	
	public DespesaCusteioOutras buscarPeloCodigo(Long id) {
		return manager.find(DespesaCusteioOutras.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<DespesaCusteioOutras> buscarOutrasDespesasCusteio(Unidade unidade) {
		return manager.createNamedQuery("DespesaCusteioOutras.buscarDespesasCusteioOutras")
				.setParameter("codigo_unidade", unidade)
				.setParameter("tipo_despesa", true)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<DespesaCusteioOutras> buscarOutrasDespesas(Unidade unidade) {
		return manager.createNamedQuery("DespesaCusteioOutras.buscarDespesasCusteioOutras")
				.setParameter("codigo_unidade", unidade)
				.setParameter("tipo_despesa", false)
				.getResultList();
	}
	
	public List<DespesaCusteioOutras> buscarOutrasDespesasPorAnoAgricola(
			LocalDate dataInicio, 
			LocalDate dataFim, 
			Unidade unidade
			){
		return manager.createQuery(
				"SELECT d FROM DespesaCusteioOutras d "
				+ "WHERE d.data "
				+ "BETWEEN :dataInicio AND :dataFim "
				+ "AND d.unidade = :codigo_unidade "
				+ "AND d.eCusteio = :tipo_despesa "
				+ "ORDER BY d.data", DespesaCusteioOutras.class)
				.setParameter("dataInicio", dataInicio)
				.setParameter("dataFim", dataFim)
				.setParameter("codigo_unidade", unidade)
				.setParameter("tipo_despesa", false)
				.getResultList();
	}
	public List<DespesaCusteioOutras> buscarOutrasDespesasCusteioPorAnoAgricola(
			LocalDate dataInicio, 
			LocalDate dataFim, 
			Unidade unidade
			){
		return manager.createQuery(
				"SELECT d FROM DespesaCusteioOutras d "
				+ "WHERE d.data "
				+ "BETWEEN :dataInicio AND :dataFim "
				+ "AND d.unidade = :codigo_unidade "
				+ "AND d.eCusteio = :tipo_despesa "
				+ "ORDER BY d.data", DespesaCusteioOutras.class)
				.setParameter("dataInicio", dataInicio)
				.setParameter("dataFim", dataFim)
				.setParameter("codigo_unidade", unidade)
				.setParameter("tipo_despesa", true)
				.getResultList();
	}
	
	/*
	 * RELATORIO OUTRAS DESPESAS
	 */
	
	
	
	public List<String> buscarAnosComRegistro (Unidade unidade) {
		
		log.info("Buscando anos com registro outras despesas");
		List<String> anos = manager.createQuery(
				"SELECT DISTINCT " +
						"CASE " +
						"  WHEN FUNCTION('MONTH', c.data) >= 8 THEN CONCAT(FUNCTION('YEAR', c.data), '/', FUNCTION('YEAR', c.data) + 1) " +
						"  ELSE CONCAT(FUNCTION('YEAR', c.data) - 1, '/', FUNCTION('YEAR', c.data)) " +
						"END " +
						"FROM DespesaCusteioOutras c " +
						"WHERE c.eCusteio = false " +
						"ORDER BY " +
						"CASE " +
						"  WHEN FUNCTION('MONTH', c.data) >= 8 THEN CONCAT(FUNCTION('YEAR', c.data), '/', FUNCTION('YEAR', c.data) + 1) " +
						"  ELSE CONCAT(FUNCTION('YEAR', c.data) - 1, '/', FUNCTION('YEAR', c.data)) " +
						"END", String.class).getResultList();
		
		log.info("Lista anos size: " + anos.size());
		return anos;
	}
	
	public List<OutrasDespesasDTO> buscarDespesasDTO(LocalDate dataInicio, LocalDate dataFim, Unidade unidade){
		
		List<OutrasDespesasDTO> lista = manager.createQuery(
				"SELECT new com.cafe.modelo.to.OutrasDespesasDTO( "
				+ "d.data, "
				+ "d.valorTotal, "
				+ "d.tipo) "
			+ "FROM DespesaCusteioOutras d "
			+ "WHERE "
			+ " d.eCusteio = false "
			+ " and d.data BETWEEN :dataInicio AND :dataFim "
			+ " and d.unidade = :unidade "
			+ " ORDER BY d.tipo"
				, OutrasDespesasDTO.class)
				.setParameter("dataInicio", dataInicio)
				.setParameter("dataFim", dataFim)
				.setParameter("unidade", unidade)
				.getResultList();
		
		return lista;
	}
	
	/*
	 * RELATORIO OUTRAS DESPESAS CUSTEIO
	 */
	
	
	public List<String> buscarAnosComRegistroCusteio (Unidade unidade) {
		
		List<String> anos = manager.createQuery(
				"SELECT DISTINCT " +
						"CASE " +
						"  WHEN FUNCTION('MONTH', c.data) >= 8 THEN CONCAT(FUNCTION('YEAR', c.data), '/', FUNCTION('YEAR', c.data) + 1) " +
						"  ELSE CONCAT(FUNCTION('YEAR', c.data) - 1, '/', FUNCTION('YEAR', c.data)) " +
						"END " +
						"FROM DespesaCusteioOutras c " +
						"WHERE c.eCusteio = true " +
						"ORDER BY " +
						"CASE " +
						"  WHEN FUNCTION('MONTH', c.data) >= 8 THEN CONCAT(FUNCTION('YEAR', c.data), '/', FUNCTION('YEAR', c.data) + 1) " +
						"  ELSE CONCAT(FUNCTION('YEAR', c.data) - 1, '/', FUNCTION('YEAR', c.data)) " +
						"END", String.class).getResultList();
		
		log.info("Lista anos size: " + anos.size());
		return anos;
	}
	
	public List<OutrasDespesasDTO> buscarDespesasDTOCusteio(LocalDate dataInicio, LocalDate dataFim, Unidade unidade){
		
		List<OutrasDespesasDTO> lista = manager.createQuery(
				"SELECT new com.cafe.modelo.to.OutrasDespesasDTO( "
				+ "d.data, "
				+ "d.valorTotal, "
				+ "d.tipo) "
			+ "FROM DespesaCusteioOutras d "
			+ "WHERE "
			+ " d.eCusteio = true "
			+ " and d.data BETWEEN :dataInicio AND :dataFim "
			+ " and d.unidade = :unidade "
			+ " ORDER BY d.tipo"
				, OutrasDespesasDTO.class)
				.setParameter("dataInicio", dataInicio)
				.setParameter("dataFim", dataFim)
				.setParameter("unidade", unidade)
				.getResultList();
		
		return lista;
	}
	

}
