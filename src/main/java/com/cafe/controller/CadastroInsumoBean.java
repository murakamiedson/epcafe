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
import com.cafe.modelo.Insumo;
import com.cafe.modelo.enums.Medida;
import com.cafe.modelo.enums.TipoInsumo;
import com.cafe.service.InsumoService;
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
public class CadastroInsumoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Insumo insumo;
	
	private List<TipoInsumo> tiposInsumo;
	private List<Medida> medidas;
	
	private List<Insumo> insumos = new ArrayList<>();
	private List<Fabricante> fabricantes = new ArrayList<>();
	
	private Long tenantId;
	
	@Inject
	private InsumoService insumoService;
	
	@Inject
	private LoginBean usuarioLogado;
	
	@PostConstruct
	public void inicializar() {
		
		this.tenantId = this.usuarioLogado.getUsuario().getTenant().getCodigo();
		log.info("Bean : tenant = " + tenantId + "-" + usuarioLogado.getUsuario().getTenant().getTenant());
		
		this.limpar();
		
		this.tiposInsumo = Arrays.asList(TipoInsumo.values());
		this.medidas = Arrays.asList(Medida.values());
		
		this.fabricantes = insumoService.buscarFabricantes(tenantId);
	}
	
	public void salvar() {
		
		try {		
			
			this.insumoService.salvar(insumo);
			MessageUtil.sucesso("Insumo salvo com sucesso!");
			
		} catch (NegocioException e) {
			
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
		
		this.limpar();
	}
	
	public void limpar() {
		
		this.insumo = new Insumo();
		this.insumo.setTenant_id(this.tenantId);
	}	 
	
	public void carregarInsumos() {
		
		insumos = insumoService.buscarInsumos(usuarioLogado.getTenantId());	
	}
}