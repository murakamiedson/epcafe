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


/**
 * @author isabella
 *
 */
@Getter
@Setter
@Named
@ViewScoped
public class PesquisaFuncionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Funcionario> funcionarios = new ArrayList<>();
	private Funcionario funcionarioSelecionado;
	
	@Inject
	private FuncionarioService funcionarioService;
	@Inject
	private LoginBean loginBean;

		
	@PostConstruct
	public void inicializar() {
		funcionarios = funcionarioService.buscarFuncionariosPorUnidade(loginBean.getUsuario().getUnidade() ,loginBean.getTenantId());
	}
	
	public void excluir() {
		try {
			funcionarioService.excluir(funcionarioSelecionado);			
			this.funcionarios.remove(funcionarioSelecionado);
			MessageUtil.sucesso("Funcionário " + funcionarioSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
	
}