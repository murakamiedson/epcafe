package com.cafe.controller.cadastros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.Unidade;
import com.cafe.service.PropriedadeService;
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
public class PesquisaUnidadeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Unidade> unidades = new ArrayList<>();
	private Unidade unidadeSelecionada;
	private Unidade unidade;
	
	@Inject
	PropriedadeService propriedadeService;
	@Inject
	private LoginBean loginBean;

		
	@PostConstruct
	public void inicializar() {
		unidades = propriedadeService.buscarTodos(loginBean.getTenantId());
		
		unidade = loginBean.getUsuario().getUnidade();
		log.info("prop = unidade");
	}

	public void excluir() {
		try {
			propriedadeService.excluir(unidadeSelecionada);			
			this.unidades.remove(unidadeSelecionada);
			MessageUtil.sucesso("Unidade " + unidadeSelecionada.getNome() + " excluída com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
	
}