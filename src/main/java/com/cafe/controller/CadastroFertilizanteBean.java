package com.cafe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.modelo.Fabricante;
import com.cafe.modelo.Fertilizante;
import com.cafe.modelo.enums.Medida;
import com.cafe.modelo.enums.TipoInsumo;
import com.cafe.service.FertilizanteService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @author joao
 *
 */
@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class CadastroFertilizanteBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Fertilizante fertilizante;
	
	private List<TipoInsumo> tiposInsumo;
	private List<Medida> medidas;
	
	private List<Fertilizante> fertilizantes = new ArrayList<>();
	private List<Fabricante> fabricantes = new ArrayList<>();
	
	private Long tenantId;
	
	@Inject
	private FertilizanteService fertilizanteService;
	
	@Inject
	private LoginBean usuarioLogado;
	
	@PostConstruct
	public void inicializar() {
		
		this.tenantId = this.usuarioLogado.getUsuario().getTenant().getCodigo();
		log.info("Bean : tenant = " + tenantId + "-" + usuarioLogado.getUsuario().getTenant().getTenant());
		
		this.limpar();
		
		this.tiposInsumo = Arrays.asList(TipoInsumo.DEFENSIVO, TipoInsumo.FERTILIZANTE);
		this.medidas = Arrays.asList(Medida.values());
		
		this.fabricantes = fertilizanteService.buscarFabricantesDeFertilizantes(tenantId);
	}
	
	public void salvar() {
		
		try {		
			
			this.fertilizanteService.salvar(fertilizante);
			MessageUtil.sucesso("Insumo salvo com sucesso!");
			
		} catch (NegocioException e) {
			
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
		
		this.limpar();
	}
	
	public void limpar() {
		
		this.fertilizante = new Fertilizante();
		this.fertilizante.setTenant_id(this.tenantId);
	}	 
	
	public void carregarInsumos() {
		
		fertilizantes = fertilizanteService.buscarFertilizantes(usuarioLogado.getTenantId());	
	}
}
