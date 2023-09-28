package com.cafe.controller.cadastros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.Propriedade;
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
public class PesquisaPropriedadeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Propriedade> propriedades = new ArrayList<>();
	private Propriedade unidadeSelecionada;
	private Propriedade unidadeTemp;
	
	@Inject
	PropriedadeService propriedadeService;
	@Inject
	private LoginBean loginBean;

		
	@PostConstruct
	public void inicializar() {
		propriedades = propriedadeService.buscarTodos(loginBean.getTenantId());
		if(loginBean.getUsuario().getPropriedade().getCodigo().equals(loginBean.getUnidadeTemp().getCodigo())) {
			unidadeTemp = loginBean.getUnidadeTemp();
			log.info("prop = unidadeTemp");
		}
	}
	
	public void excluir() {
		try {
			propriedadeService.excluir(unidadeSelecionada);			
			this.propriedades.remove(unidadeSelecionada);
			MessageUtil.sucesso("Propriedade " + unidadeSelecionada.getNome() + " excluída com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
	
}