package com.cafe.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.cafe.dao.PropriedadeDAO;
import com.cafe.modelo.Tenant;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.enums.TipoPlano;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
public class PropriedadeService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PropriedadeDAO propriedadeDAO;
	@Inject
	private TenantService tenantService;


	public void salvar(Unidade unidade) throws NegocioException {
		
		log.info("Service : tenant = " + unidade.getTenant_id());		
		
		validaDados(unidade);		
		
		this.propriedadeDAO.salvar(unidade);
	}
	
	private void validaDados(Unidade unidade) throws NegocioException {
		/*
		 * Verifica se existe agendamento em aberto	
		 */		
		if (unidade.getTipo() == null) 
			throw new NegocioException("O tipo é obrigatório.");
		
		/*
		 * verifica se é free, profissional ou especial
		 * free = 1 Unidade e 3 usuarios
		 * profissional = 5 unidades e 50 usuarios
		 * especial = ilimitado
		 */
		
		Long tenantId = unidade.getTenant_id();
		Tenant tenant = tenantService.buscarPeloCodigo(tenantId);
		TipoPlano plano = tenant.getTipoPlano();
		 
		int qde = propriedadeDAO.verificaTipoUnidadeTenant(tenantId, plano).intValue();
		
		log.info("plano qde unidades = " + qde);
		if(plano == TipoPlano.FREE) {			
			if (qde > 1) {
				throw new NegocioException("A quantidade de unidades do Plano Free é limitado a uma unidades. Faça o upgrade para o Plano Profissional.");
			}
		} 
		else {
			if(plano == TipoPlano.PROFISSIONAL) {
				if (qde > 5) {
					throw new NegocioException("A quantidade de unidades do Plano Profissional é limitado a 5 unidades. Faça o upgrade para o Plano Especial.");
				}
			}
		}
	}		

	public Unidade buscarPeloCodigo(long codigo) {
		return propriedadeDAO.buscarPeloCodigo(codigo);
	}
	

	public List<Unidade> buscarTodos(Long tenantId) {
		return propriedadeDAO.buscarTodos(tenantId);
	}	
	
	public void excluir(Unidade unidade) throws NegocioException {
		propriedadeDAO.excluir(unidade);
		
	}
	
	
	public List<Unidade> buscarUnidades(Long tenantId) {	
		log.info("Primeiro acesso a banco... buscar unidades");
		
		return propriedadeDAO.buscarTodos(tenantId);
	}
	


	public PropriedadeDAO getUnidadeDAO() {
		return propriedadeDAO;
	}

	public void setManager(EntityManager manager) {
		
		// usado pelo scheduler de geração de RMAs
		propriedadeDAO = new PropriedadeDAO();
		propriedadeDAO.setEntityManager(manager);
		
	}

}