package com.cafe.controller.cadastros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.Endereco;
import com.cafe.modelo.Propriedade;
import com.cafe.modelo.enums.TipoPropriedade;
import com.cafe.modelo.enums.Uf;
import com.cafe.modelo.to.EnderecoTO;
import com.cafe.service.PropriedadeService;
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
public class CadastroPropriedadeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Propriedade propriedade;
	private List<Uf> ufs;
	private String cep = null;
	private EnderecoTO enderecoTO;	
	private List<TipoPropriedade> tipos;
	private List<Propriedade> propriedades = new ArrayList<>();
	private boolean scfv = false;
	
	@Inject
	private PropriedadeService propriedadeService;
	@Inject
	private BuscaCEPService buscaCEPService;
	
	
	
	/*
	 * INI tratamento Tenant
	 */
	@Inject
	private LoginBean usuarioLogado;
	private Long tenantId;	
	
	@PostConstruct
	public void inicializar() {
		tenantId = usuarioLogado.getUsuario().getTenant().getCodigo();
		log.info("Bean : tenant = " + tenantId + "-" + usuarioLogado.getUsuario().getTenant().getTenant());		
		this.limpar();
		this.tipos = Arrays.asList(TipoPropriedade.values());
		this.ufs = Arrays.asList(Uf.values());
	}
	/*
	 * FIM tratamento Tenant
	 */
	
	
	public void salvar() {
		try {
			
			propriedade.getEndereco().setMunicipio(propriedade.getEndereco().getMunicipio());
			this.propriedadeService.salvar(propriedade);
			MessageUtil.sucesso("propriedade salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
		
		this.limpar();
	}
	
	public void limpar() {
		this.propriedade = new Propriedade();
		this.propriedade.setEndereco(new Endereco());
		this.propriedade.setTenant_id(tenantId);
	}
	
	public void buscaEnderecoPorCEP() {
		
        try {
			enderecoTO  = buscaCEPService.buscaEnderecoPorCEP(propriedade.getEndereco().getCep());
			
			/*
	         * Preenche o Endereco com os dados buscados
	         */	 
			propriedade.getEndereco().setEndereco(enderecoTO.getTipoLogradouro().
	        		                concat(" ").concat(enderecoTO.getLogradouro()));
			propriedade.getEndereco().setBairro(enderecoTO.getBairro());
			propriedade.getEndereco().setMunicipio(enderecoTO.getCidade());
			propriedade.getEndereco().setUf(enderecoTO.getEstado());
	        
	        if (enderecoTO.getResultado() != 1) {
	        	MessageUtil.erro("Endereço não encontrado para o CEP fornecido.");		            
	        }
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());		            
		}       
	}
	
	public void carregarPropriedades() {
		
		propriedades = propriedadeService.buscarUnidades(usuarioLogado.getTenantId());	
	}

}
