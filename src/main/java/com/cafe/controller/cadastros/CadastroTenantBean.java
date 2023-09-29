package com.cafe.controller.cadastros;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FlowEvent;

import com.cafe.modelo.Endereco;
import com.cafe.modelo.Propriedade;
import com.cafe.modelo.Tenant;
import com.cafe.modelo.Usuario;
import com.cafe.modelo.enums.Grupo;
import com.cafe.modelo.enums.Role;
import com.cafe.modelo.enums.Status;
import com.cafe.modelo.enums.TipoPlano;
import com.cafe.modelo.enums.TipoPropriedade;
import com.cafe.modelo.enums.Uf;
import com.cafe.modelo.to.CadastroTenantTO;
import com.cafe.modelo.to.EnderecoTO;
import com.cafe.service.CadastroTenantService;
import com.cafe.service.UsuarioService;
import com.cafe.service.rest.BuscaCEPService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @author murakami
 *
 */
@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class CadastroTenantBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private CadastroTenantTO autocadTO;
	private EnderecoTO enderecoTO;
	private Usuario usuario;
	private List<Uf> ufs;
	private boolean sucesso = false;
	private int progressBar = 0;
	
	
	@Inject
	private UsuarioService usuarioService;
	@Inject
	private CadastroTenantService autocadService;
	@Inject
	private BuscaCEPService buscaCEPService;	

	@PostConstruct
	public void inicializar() throws Exception{
		
		log.info("Chegou no cadastro de Secretaria");		
		this.ufs = Arrays.asList(Uf.values());
		
		inicializarUsuario();		
	}	

	public void inicializarUsuario() {
		this.autocadTO = new CadastroTenantTO();

		autocadTO.setProprietario(new Tenant());
		autocadTO.getProprietario().setTipoPlano(TipoPlano.FREE);

		autocadTO.setPropriedade(new Propriedade());
		
		autocadTO.getPropriedade().setEndereco(new Endereco());
		autocadTO.getPropriedade().setTipo(TipoPropriedade.FAZENDA);

		Usuario usuario = new Usuario();
		
			
		usuario.setRole(Role.GESTOR);
		usuario.setGrupo(Grupo.GESTORES);
		usuario.setStatus(Status.ATIVO);		
		
		autocadTO.setUsuario(usuario);
	}
	
	public String onFlowProcess(FlowEvent event) {
		log.info("Id da fase: " + event.getPhaseId());
		progressBar = progressBar + 25;
		return event.getNewStep();
	}

	public void buscaEnderecoPorCEP() {

		log.info("Buscar endereço cep = " + autocadTO.getPropriedade().getEndereco().getCep());		
		try {
			enderecoTO = buscaCEPService.buscaEnderecoPorCEP(autocadTO.getPropriedade().getEndereco().getCep());

			/*
			 * Preenche o Endereco com os dados buscados
			 */
			autocadTO.getPropriedade().getEndereco().setUf(enderecoTO.getEstado());
			autocadTO.getPropriedade().getEndereco().setMunicipio(enderecoTO.getCidade());
			autocadTO.getPropriedade().getEndereco().setBairro(enderecoTO.getBairro());
			autocadTO.getPropriedade().getEndereco().setCep(enderecoTO.getCep());
			autocadTO.getPropriedade().getEndereco()
					.setEndereco(enderecoTO.getTipoLogradouro().concat(" ").concat(enderecoTO.getLogradouro()));
			autocadTO.getPropriedade().getEndereco().setUf(enderecoTO.getEstado());

			if (enderecoTO.getResultado() != 1) {
				MessageUtil.erro("Endereço não encontrado para o CEP fornecido.");
			}
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
	
	public void salvar() {
		try {
			usuario = this.autocadService.salvar(autocadTO);
			
			sucesso = true;
			
			log.info("cadastro concluido com sucesso!");
			log.info("usuario = " + usuario.getCodigo() + " - nome = " + usuario.getNome());
			log.info("unidade = " + usuario.getPropriedade().getCodigo());
			log.info("tenant = " + usuario.getTenant().getCodigo());
			
			MessageUtil.info("Cadastro realizado com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
	
	public String sair() {
		log.info("Sair...");   
		
		try {
			getExternalContext().redirect(getExternalContext().getRequestContextPath() 
					+ "/restricted/cadastros/PesquisaTenant.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/restricted/cadastros/PesquisaTenant.xhtml";
	}
	

	private ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
}