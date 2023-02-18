package com.cafe.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.Fabricante;
import com.cafe.modelo.enums.TipoPlano;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
public class FabricanteDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;	
	
	@Transactional
	public void salvar(Fabricante fabricante) throws NegocioException {
		log.info("DAO : tenant = " + fabricante.getTenant_id());
		try {
			manager.merge(fabricante);
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
	public void excluir(Fabricante fabricante) throws NegocioException {
		fabricante = buscarPeloCodigo(fabricante.getId());
		try {
			manager.remove(fabricante);
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
	
	
	public Fabricante buscarPeloCodigo(Long codigo) {
		return manager.find(Fabricante.class, codigo);
	}
	
	@SuppressWarnings("unchecked")
	public List<Fabricante> buscarTodos(Long tenantId) {
		return manager.createNamedQuery("Fabricante.buscarTodos")
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
		
	@SuppressWarnings("unchecked")
	public List<Fabricante> buscarComPaginacao(int first, int pageSize, Long tenantId) {
		return manager.createNamedQuery("Fabricante.buscarTodos")
				.setParameter("tenantId", tenantId)
				.setFirstResult(first)
				.setMaxResults(pageSize)
				.getResultList();
	}

	public Long encontrarQuantidadeDeUnidades(Long tenantId) {
		return manager.createQuery("select count(u) from Fabricante u where u.tenant_id = :tenantId", Long.class)
				.setParameter("tenantId", tenantId)
				.getSingleResult();
	}
	
	// verifica se o fabricante é free pelo tenantId
	public Long verificaTipoUnidadeTenant(Long tenantId, TipoPlano plano) {
		
		return manager.createQuery("SELECT count(u) FROM Fabricante u "
				+ "INNER JOIN Tenant t ON t.codigo = u.tenant_id "
				+ "where t.codigo = :tenantId "
				+ "and t.tipoPlano = :plano", Long.class)
				.setParameter("tenantId", tenantId)
				.setParameter("plano", plano)
				.getSingleResult();
	}
	
	
	
	
	// criado para realização de testes unitários com JIntegrity
	public void setEntityManager(EntityManager manager) {
		this.manager = manager;
	}

	
}