package com.cafe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.modelo.Insumo;
import com.cafe.service.InsumoService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;

/**
 * @author joao
 *
 */
@Getter
@Setter
@Named
@ViewScoped
public class PesquisaInsumoBean implements Serializable {

private static final long serialVersionUID = 1L;
	
	private List<Insumo> insumos = new ArrayList<>();
	private Insumo insumoSelecionado;
	
	@Inject
	private InsumoService insumoService;
	@Inject
	private LoginBean loginBean;
	
	@PostConstruct
	public void inicializar() {
		
		insumos = insumoService.buscarInsumos(loginBean.getTenantId());
	}
	
	public void excluir() {
		
		try {
			
			this.insumoService.excluir(insumoSelecionado);
			this.insumos.remove(insumoSelecionado);
			
			MessageUtil.sucesso("Insumo " + insumoSelecionado.getNome() + " excluído com sucesso.");
			
		} catch (NegocioException e) {
			
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
}
