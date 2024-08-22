package com.cafe.dao;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.DespesaMaquina;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.to.DespesaMaquinaDTO;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

import lombok.extern.log4j.Log4j;

/**
 * @author isabella
 *
 */

@Log4j
public class DespesaMaquinaDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Transactional
	public DespesaMaquina salvar(DespesaMaquina despesaMaquina) throws PersistenceException, NegocioException {
		try {
			return manager.merge(despesaMaquina);	
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
			log.info("excluir dao");
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
	public List<DespesaMaquina> buscarDespesasMaquinas(Unidade unidade) {
		return manager.createNamedQuery("DespesaMaquina.buscarDespesasMaquinas")
				.setParameter("codigo_unidade", unidade)
				.getResultList();
	}
	
	public List<DespesaMaquina> buscarDespesasMaquinasPorAno(LocalDate dataInicio, LocalDate dataFim, Unidade unidade){
		return manager.createQuery(
				"SELECT d FROM DespesaMaquina d "
				+ "WHERE d.data "
				+ "BETWEEN :dataInicio AND :dataFim "
				+ "AND d.unidade = :codigo_unidade "
				+ "ORDER BY d.data", DespesaMaquina.class)
				.setParameter("dataInicio", dataInicio)
				.setParameter("dataFim", dataFim)
				.setParameter("codigo_unidade", unidade)
				.getResultList();
	}
	
	
	/*
	 * Para Relatorio Despesas Máquina
	 */
	public List<DespesaMaquinaDTO> buscarDespesasDTO(LocalDate dataInicio, LocalDate dataFim, Unidade unidade){
		
		log.info("consultando DTO...");
		
		/*
		select d.mesAno AS data, 
		d.valorTotal AS total,
        m.id as idMaquina,
        m.nome as nomeMaquina,
        m.tipoCombustivel as combustivel
        from despesaMaquina d inner join maquina m on (d.maquina_id=m.id)
        where mesAno between "2023-06-01" and "2023-07-01"
        order by m.id;
        */
				
		List<DespesaMaquinaDTO> lista = manager.createQuery(
				"SELECT new com.cafe.modelo.to.DespesaMaquinaDTO( "
				+ "d.data, "
				+ "d.valorTotal, "
				+ "m.id, "
				+ "m.nome, "
				+ "m.tipoCombustivel) "
			+ "FROM DespesaMaquina d "
			+ "  INNER JOIN Maquina m on d.maquina = m.id "
			+ "WHERE "
			+ "  d.data BETWEEN :dataInicio AND :dataFim "  //TODO alterar, usado so para teste
			+ "  and d.unidade = :unidade "
			+ "ORDER BY m.id", DespesaMaquinaDTO.class)
			.setParameter("dataInicio", dataInicio)
			.setParameter("dataFim", dataFim)
			.setParameter("unidade", unidade)
			.getResultList();
			
		log.info("qde DTO..." + lista.size());
		
				
		return lista;
	}

	public List<String> buscarAnosComRegistro (Unidade unidade) {
		
		List<String> anos = manager.createQuery(
				"SELECT DISTINCT " +
	                      "CASE " +
	                      "  WHEN FUNCTION('MONTH', c.data) >= 8 THEN CONCAT(FUNCTION('YEAR', c.data), '/', FUNCTION('YEAR', c.data) + 1) " +
	                      "  ELSE CONCAT(FUNCTION('YEAR', c.data) - 1, '/', FUNCTION('YEAR', c.data)) " +
	                      "END " +
	                      "FROM DespesaMaquina c " +
	                      "ORDER BY " +
	                      "CASE " +
	                      "  WHEN FUNCTION('MONTH', c.data) >= 8 THEN CONCAT(FUNCTION('YEAR', c.data), '/', FUNCTION('YEAR', c.data) + 1) " +
	                      "  ELSE CONCAT(FUNCTION('YEAR', c.data) - 1, '/', FUNCTION('YEAR', c.data)) " +
	                      "END", String.class).getResultList();
		return anos;
	}

}
