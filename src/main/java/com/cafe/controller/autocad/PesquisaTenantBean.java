package com.cafe.controller.autocad;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.modelo.Tenant;
import com.cafe.service.TenantService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;


/**
 * @author murakamiadmin
 *
 */
@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class PesquisaTenantBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Tenant> tenants = new ArrayList<>();
	private Tenant tenant;
	
	@Inject
	TenantService tenantService;

		
	@PostConstruct
	public void inicializar() {
		tenants = tenantService.buscarTodos();		
	}
	
	public String sair() {
		log.info("Session invalidate");		
		
		getExternalContext().invalidateSession();	    
		
		try {
			getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/Home.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/Home.xhtml";
	}
	
	public String cadastrar() {
		log.info("indo para cadastrar novo proprietario");
		try {
			getExternalContext().redirect(getExternalContext().getRequestContextPath() 
					+ "/restricted/cadastros/CadastroTenant.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/restricted/cadastros/CadastroTenant.xhtml";
	}
	
	private ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public void alterar() {
		try {			
			this.tenantService.alterar(tenant);
			MessageUtil.sucesso("Talhao salvo com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}		
	}
	
}