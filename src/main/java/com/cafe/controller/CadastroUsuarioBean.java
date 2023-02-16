package com.cafe.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import com.cafe.modelo.Unidade;
import com.cafe.modelo.Usuario;
import com.cafe.modelo.enums.Grupo;
import com.cafe.modelo.enums.Role;
import com.cafe.modelo.enums.Status;
import com.cafe.service.UnidadeService;
import com.cafe.service.UsuarioService;
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
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private Unidade unidade;
	private List<Role> roles;
	private List<Status> status;
	
	
	private List<Grupo> grupos;
	private List<Unidade> unidades;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private UnidadeService unidadeService;	
	
	@Inject
	private LoginBean loginBean;
	
	@PostConstruct
	public void inicializar() {		
		
		this.grupos = Arrays.asList(Grupo.values());
		this.roles = Arrays.asList(Role.values());
		this.status = Arrays.asList(Status.values());
		this.unidade = loginBean.getUsuario().getUnidade();
		this.unidades = this.unidadeService.buscarTodos(loginBean.getTenantId());
		
		this.limpar();
		
	}	

	public void salvar() {
		try {	
			
			log.info("unidade usuario" + usuario.getUnidade().getCodigo());
			log.info("unidade logado" + unidade.getCodigo());							
			
			this.usuarioService.salvar(usuario, loginBean.getTenantId());				
			
			MessageUtil.sucesso("Usuario salvo com sucesso!");
			
			this.limpar();
			
		} catch (PersistenceException e) {
			MessageUtil.erro("E-mail já cadastrado! Tente outro. O sistema não permite e-mails repetidos.");
			e.printStackTrace();
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.erro("Erro desconhecido. Contatar o administrador"); 
		}
		
		
	}
	
	public void limpar() {
		this.usuario = new Usuario();
		this.usuario.setUnidade(unidade);
		this.usuario.setStatus(Status.ATIVO);
		this.usuario.setTenant(loginBean.getUsuario().getTenant());
	}
	
}