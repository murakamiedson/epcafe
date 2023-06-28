package com.cafe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.modelo.Maquina;
import com.cafe.modelo.enums.EnumUtil;
import com.cafe.modelo.enums.TipoAuxiliarInsumos;
import com.cafe.modelo.enums.TipoCombustivel;
import com.cafe.modelo.enums.TipoInsumo;
import com.cafe.service.MaquinaService;
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
public class CadastroMaquinaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Maquina maquina;	
	private List<TipoInsumo> tiposInsumo;
	private List<TipoCombustivel> tiposCombustivel;
	private List<TipoAuxiliarInsumos> tiposMaquina;
	private List<Maquina> maquinas = new ArrayList<>();
	private Long tenantId;	
	
	@Inject
	private MaquinaService maquinaService;
	
	@Inject
	private LoginBean usuarioLogado;	
	
	@PostConstruct
	public void inicializar() {
		tenantId = usuarioLogado.getUsuario().getTenant().getCodigo();
		log.info("Bean : tenant = " + tenantId + "-" + usuarioLogado.getUsuario().getTenant().getTenant());		
		this.limpar();

		this.tiposInsumo = Arrays.asList(TipoInsumo.MAQUINA, TipoInsumo.IMPLEMENTO);
		this.tiposCombustivel = Arrays.asList(TipoCombustivel.values());
		
		
		
		this.tiposMaquina = Arrays.asList(TipoAuxiliarInsumos.values());
	}
	
	
	public void carregarTipos() {
		
		if(maquina.getTipoInsumo() == TipoInsumo.MAQUINA) {
			this.tiposMaquina = EnumUtil.getTiposMaquinas();

		}else {
			this.tiposMaquina = EnumUtil.getTiposImplementos();

		}

	}	
	
	public void salvar() {
		try {			
			this.maquinaService.salvar(maquina);
			MessageUtil.sucesso("Maquina salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}		
		this.limpar();
	}
	
	public void limpar() {
		this.maquina = new Maquina();
		this.maquina.setTenant_id(tenantId);
	}	 
	
	public void carregarMaquinas() {		
		maquinas = maquinaService.buscarMaquinas(usuarioLogado.getTenantId());	
	}
}
