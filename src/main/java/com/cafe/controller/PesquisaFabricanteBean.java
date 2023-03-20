package com.cafe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.modelo.Fabricante;
import com.cafe.service.FabricanteService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;


/**
 * @author murakamiadmin
 *
 */
@Getter
@Setter
@Named
@ViewScoped
public class PesquisaFabricanteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Fabricante> fabricantes = new ArrayList<>();
	private Fabricante fabricanteSelecionado;
	
	@Inject
	FabricanteService fabricanteService;
	@Inject
	private LoginBean loginBean;

		
	@PostConstruct
	public void inicializar() {
		fabricantes = fabricanteService.buscarFabricantes(loginBean.getTenantId());
	}
	
	public void excluir() {
		try {
			fabricanteService.excluir(fabricanteSelecionado);			
			this.fabricantes.remove(fabricanteSelecionado);
			MessageUtil.sucesso("Fabricante " + fabricanteSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
	
}