package com.cafe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.modelo.Endereco;
import com.cafe.modelo.Fabricante;
import com.cafe.modelo.enums.TipoInsumo;
import com.cafe.modelo.enums.TipoPropriedade;
import com.cafe.modelo.enums.Uf;
import com.cafe.modelo.to.EnderecoTO;
import com.cafe.service.FabricanteService;
import com.cafe.service.rest.BuscaCEPService;
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
public class CadastroFabricanteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Fabricante fabricante;
	private List<TipoInsumo> tiposFabricante;
	
	private List<Uf> ufs;
	private String cep = null;
	private EnderecoTO enderecoTO;	
	private List<TipoPropriedade> tipos;
	private List<Fabricante> fabricantes = new ArrayList<>();
	private boolean scfv = false;
	private Long tenantId;	
	
	@Inject
	private FabricanteService fabricanteService;
	@Inject
	private BuscaCEPService buscaCEPService;
	@Inject
	private LoginBean usuarioLogado;
	
	
	@PostConstruct
	public void inicializar() {
		tenantId = usuarioLogado.getUsuario().getTenant().getCodigo();
		log.info("Bean : tenant = " + tenantId + "-" + usuarioLogado.getUsuario().getTenant().getTenant());		
		this.limpar();
		
		this.tiposFabricante = Arrays.asList(TipoInsumo.INSUMO, TipoInsumo.MAQUINA);
		this.tipos = Arrays.asList(TipoPropriedade.values());
		this.ufs = Arrays.asList(Uf.values());
	}
		
	
	public void salvar() {
		try {
			
			fabricante.getEndereco().setMunicipio(fabricante.getEndereco().getMunicipio());
			this.fabricanteService.salvar(fabricante);
			MessageUtil.sucesso("Fabricante salvo com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
		
		this.limpar();
	}
	
	public void limpar() {
		this.fabricante = new Fabricante();
		this.fabricante.setEndereco(new Endereco());
		this.fabricante.setTenant_id(tenantId);
	}
	
	public void buscaEnderecoPorCEP() {
		
        try {
			enderecoTO  = buscaCEPService.buscaEnderecoPorCEP(fabricante.getEndereco().getCep());
			fabricante.getEndereco().setEndereco(enderecoTO.getTipoLogradouro().
	        		                concat(" ").concat(enderecoTO.getLogradouro()));
			fabricante.getEndereco().setBairro(enderecoTO.getBairro());
			fabricante.getEndereco().setMunicipio(enderecoTO.getCidade());
			fabricante.getEndereco().setUf(enderecoTO.getEstado());
	        
	        if (enderecoTO.getResultado() != 1) {
	        	MessageUtil.erro("Endereço não encontrado para o CEP fornecido.");		            
	        }
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());		            
		}       
	}
	
	public void carregarFabricantes() {
		
		fabricantes = fabricanteService.buscarFabricantes(usuarioLogado.getTenantId());	
	}

}
