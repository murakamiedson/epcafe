package com.cafe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.modelo.Maquina;
import com.cafe.service.MaquinaService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;


/**
 * @author murakamiadmin
 *
 */
@Getter
@Setter
@Named
@ViewScoped
public class PesquisaMaquinaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Maquina> maquinas = new ArrayList<>();
	private Maquina maquinaSelecionada;
	
	@Inject
	private MaquinaService maquinaService;
	@Inject
	private LoginBean loginBean;

		
	@PostConstruct
	public void inicializar() {
		maquinas = maquinaService.buscarMaquinas(loginBean.getTenantId());
	}
	
	public void excluir() {
		try {
			maquinaService.excluir(maquinaSelecionada);			
			this.maquinas.remove(maquinaSelecionada);
			MessageUtil.sucesso("Máquina " + maquinaSelecionada.getNome() + " excluída com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
	
}