package com.cafe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.modelo.Funcionario;
import com.cafe.service.FuncionarioService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @author isabella
 *
 */
@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class CadastroFuncionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Funcionario funcionario;	
	private List<Funcionario> funcionarios = new ArrayList<>();
	private Long tenantId;	
	
	@Inject
	private FuncionarioService funcionarioService;
	
	@Inject
	private LoginBean usuarioLogado;	
	
	@PostConstruct
	public void inicializar() {
		tenantId = usuarioLogado.getUsuario().getTenant().getCodigo();
		log.info("Bean : tenant = " + tenantId + "-" + usuarioLogado.getUsuario().getTenant().getTenant());		
		this.limpar();
	}
		
	
	public void salvar() {
		try {			
			this.funcionarioService.salvar(funcionario);
			MessageUtil.sucesso("Funcionario salvo com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}		
		this.limpar();
	}
	
	public void limpar() {
		this.funcionario = new Funcionario();
		this.funcionario.setTenant_id(tenantId);
	}	 
	
	public void carregarFuncionarios() {		
		funcionarios = funcionarioService.buscarFuncionarios(usuarioLogado.getTenantId());	
	}
}
