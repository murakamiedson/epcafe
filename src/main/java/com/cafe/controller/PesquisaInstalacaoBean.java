package com.cafe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.modelo.Instalacao;
import com.cafe.service.InstalacaoService;
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
public class PesquisaInstalacaoBean implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private List<Instalacao> instalacoes = new ArrayList<>();
	private Instalacao instalacaoSelecionado;
	
	@Inject
	InstalacaoService instalacaoService;
	@Inject
	private LoginBean loginBean;

		
	@PostConstruct
	public void inicializar() {
		instalacoes = instalacaoService.buscarInstalacoesPorUnidade(loginBean.getUsuario().getUnidade() ,loginBean.getTenantId());
	}
	
	public void excluir() {
		try {
			instalacaoService.excluir(instalacaoSelecionado);			
			this.instalacoes.remove(instalacaoSelecionado);
			MessageUtil.sucesso("Instalação " + instalacaoSelecionado.getNome() + " excluída com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
}
