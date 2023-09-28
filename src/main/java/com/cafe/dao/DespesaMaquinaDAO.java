package com.cafe.dao;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.DespesaMaquina;
import com.cafe.modelo.to.DespesaDTO;
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
	public List<DespesaMaquina> buscarDespesasMaquinas(Long tenantId) {
		return manager.createNamedQuery("DespesaMaquina.buscarDespesasMaquinas")
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
	
	
	/*
	 * Para Relatorio Despesas Máquina
	 */
	public List<DespesaDTO> buscarDespesasDTO(LocalDate mesAno, Long tenantId){
		
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
				
		List<DespesaDTO> lista = manager.createQuery(
				"SELECT new com.cafe.modelo.to.DespesaDTO( "
				+ "d.mesAno, "
				+ "d.valorTotal, "
				+ "m.id, "
				+ "m.nome, "
				+ "m.tipoCombustivel) "
			+ "FROM DespesaMaquina d "
			+ "  INNER JOIN Maquina m on d.maquina = m.id "
			+ "WHERE "
			+ "  mesAno <= :mesAno "  //TODO alterar, usado so para teste
			+ "  and d.tenant_id = :tenantId "
			+ "ORDER BY m.id", DespesaDTO.class)
			.setParameter("mesAno", mesAno)
			.setParameter("tenantId", tenantId)
			.getResultList();
			
		log.info("qde DTO..." + lista.size());
		
				
		return lista;
	}



}
