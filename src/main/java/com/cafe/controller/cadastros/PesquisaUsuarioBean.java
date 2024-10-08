package com.cafe.controller.cadastros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;

import com.cafe.controller.LoginBean;
import com.cafe.dao.lazy.LazyUsuario;
import com.cafe.modelo.Usuario;
import com.cafe.modelo.enums.Grupo;
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
public class PesquisaUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Usuario> usuarios = new ArrayList<>();
	
	private LazyDataModel<Usuario> lazyUsuarios;
	
	private Usuario usuarioSelecionado;
	private Usuario usuarioSelecionadoExcluir;
	
	@Inject
	UsuarioService usuarioService;
	@Inject
	private LoginBean loginBean;
		
		
	@PostConstruct
	public void inicializar() {
		lazyUsuarios = new LazyUsuario( usuarioService, loginBean.getUsuario(), loginBean.getTenantId());
	}

	public void setUsuarioSelecionadoExcluir(Usuario usuarioSelecionadoExcluir) {
		this.usuarioSelecionadoExcluir = usuarioSelecionadoExcluir;
		log.info("usuarioSelecionadoExcluir setado = " + usuarioSelecionadoExcluir.getNome());
	}
	
	public void excluir() {
		try {
			usuarioService.excluir(usuarioSelecionadoExcluir);
			this.usuarios.remove(usuarioSelecionadoExcluir);
			MessageUtil.sucesso("Usuario " + usuarioSelecionadoExcluir.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
	
	public void reset() {
		try {
			usuarioService.reset(usuarioSelecionadoExcluir);	
			MessageUtil.sucesso("Senha do usuário " + usuarioSelecionadoExcluir.getNome() + " reinicializada com sucesso.");
			
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
	
	public boolean isGestor() {
		if(loginBean.getUsuario().getGrupo() == Grupo.GESTORES)
			return true;
		
		return false;
	}

}