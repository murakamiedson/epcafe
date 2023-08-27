package com.cafe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.modelo.Fertilizante;
import com.cafe.service.FertilizanteService;
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
public class PesquisaFertilizanteBean implements Serializable {

private static final long serialVersionUID = 1L;
	
	private List<Fertilizante> fertilizantes = new ArrayList<>();
	private Fertilizante insumoSelecionado;
	
	@Inject
	private FertilizanteService fertilizanteService;
	@Inject
	private LoginBean loginBean;
	
	@PostConstruct
	public void inicializar() {
		
		fertilizantes = fertilizanteService.buscarFertilizantes(loginBean.getTenantId());
	}
	
	public void excluir() {
		
		try {
			
			this.fertilizanteService.excluir(insumoSelecionado);
			this.fertilizantes.remove(insumoSelecionado);
			
			MessageUtil.sucesso("Insumo " + insumoSelecionado.getId() + " excluído com sucesso.");
			
		} catch (NegocioException e) {
			
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
}
