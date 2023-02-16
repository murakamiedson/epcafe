package com.cafe.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.cafe.dao.TenantDAO;
import com.cafe.modelo.Tenant;

/**
 * @author murakamiadmin
 *
 */
public class TenantService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private TenantDAO tenantDAO;

	public Tenant buscarPeloCodigo(long codigo) {
		return tenantDAO.buscarPeloCodigo(codigo);
	}	

	public List<Tenant> buscarTodos() {
		return tenantDAO.buscarTodos();
	}
	
	
	public void setManager(EntityManager manager) {
		tenantDAO.setEntityManager(manager);		
	}
}