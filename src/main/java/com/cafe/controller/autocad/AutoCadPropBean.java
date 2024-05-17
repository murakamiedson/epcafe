package com.cafe.controller.autocad;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FlowEvent;

import com.cafe.modelo.Endereco;
import com.cafe.modelo.Tenant;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.Usuario;
import com.cafe.modelo.UsuarioTemp;
import com.cafe.modelo.enums.Grupo;
import com.cafe.modelo.enums.Role;
import com.cafe.modelo.enums.Status;
import com.cafe.modelo.enums.TipoPlano;
import com.cafe.modelo.enums.TipoPropriedade;
import com.cafe.modelo.enums.Uf;
import com.cafe.modelo.to.AutoCadPropTO;
import com.cafe.modelo.to.EnderecoTO;
import com.cafe.service.AutoCadPropService;
import com.cafe.service.UsuarioService;
import com.cafe.service.rest.BuscaCEPService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @author isadora
 *
 */
@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class AutoCadPropBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private AutoCadPropTO autocadTO;
	private EnderecoTO enderecoTO;
	private UsuarioTemp usuarioTemp;
	private Usuario usuario;
	private List<Uf> ufs;
	private boolean sucesso = false;
	private int progressBar = 0;
	
	
	@Inject
	private UsuarioService usuarioService;
	@Inject
	private AutoCadPropService autocadService;
	@Inject
	private BuscaCEPService buscaCEPService;	

	@PostConstruct
	public void inicializar() throws Exception{
		log.info("Chegou no cadastro de Propriedade");		
		this.ufs = Arrays.asList(Uf.values());		
		
		try {
			Map<String, String> map;
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			map = externalContext.getRequestParameterMap();
			
			Long id = Long.valueOf(map.get("id"));
			log.info("parametro id do request= " + id);
			usuarioTemp = autocadService.buscarUserPeloCodigo(id);
			
			if(usuarioTemp == null) {
				throw new NegocioException("ocorreu um problema! Volte para a página inicial e tente novamente.");
			}
			this.inicializarUsuario();
			
			log.info("usuarioTemp = " + usuarioTemp.getEmail());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;			
		}
	}	

	public void inicializarUsuario() {
		this.autocadTO = new AutoCadPropTO();

		autocadTO.setTenant(new Tenant());
		autocadTO.getTenant().setTipoPlano(TipoPlano.PROFISSIONAL);

		autocadTO.setUnidade(new Unidade());
		
		autocadTO.getUnidade().setEndereco(new Endereco());
		autocadTO.getUnidade().setTipo(TipoPropriedade.FAZENDA);

		Usuario usuario = new Usuario();
		
		usuario.setEmail(getUsuarioTemp().getEmail());
		usuario.setSenha(getUsuarioTemp().getSenha());		
		usuario.setRole(Role.GESTOR);
		usuario.setGrupo(Grupo.GESTORES);
		usuario.setStatus(Status.ATIVO);		
		
		autocadTO.setUsuario(usuario);
		autocadTO.setUsuarioTemp(usuarioTemp);
	}
	
	public String onFlowProcess(FlowEvent event) {
		log.info("Id da fase: " + event.getPhaseId());
		progressBar = progressBar + 25;
		return event.getNewStep();
	}

	public void buscaEnderecoPorCEP() {

		try {
			enderecoTO = buscaCEPService.buscaEnderecoPorCEP(autocadTO.getUnidade().getEndereco().getCep());

			/*
			 * Preenche o Endereco com os dados buscados
			 */
			autocadTO.getUnidade().getEndereco().setUf(enderecoTO.getEstado());
			autocadTO.getUnidade().getEndereco().setMunicipio(enderecoTO.getCidade());
			autocadTO.getUnidade().getEndereco().setBairro(enderecoTO.getBairro());
			autocadTO.getUnidade().getEndereco().setCep(enderecoTO.getCep());
			autocadTO.getUnidade().getEndereco()
					.setEndereco(enderecoTO.getTipoLogradouro().concat(" ").concat(enderecoTO.getLogradouro()));
			autocadTO.getUnidade().getEndereco().setUf(enderecoTO.getEstado());

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
			log.info("unidade = " + usuario.getUnidade().getCodigo());
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
			getExternalContext().redirect(getExternalContext().getRequestContextPath() + "/Home.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/Home.xhtml";
	}
	

	private ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
}