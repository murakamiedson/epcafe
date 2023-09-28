package com.cafe.controller.cadastros;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.Talhao;
import com.cafe.service.TalhaoService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;

/**
* @joao
*
*/
@Getter
@Setter
@Named
@ViewScoped
public class PesquisaTalhaoBean implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private List<Talhao> talhoes = new ArrayList<>();
	private Talhao talhaoSelecionado;
	
	@Inject
	TalhaoService talhaoService;
	@Inject
	private LoginBean loginBean;

		
	@PostConstruct
	public void inicializar() {
		talhoes = talhaoService.buscarTalhoesPorUnidade(loginBean.getUsuario().getUnidade(), loginBean.getTenantId());
	}
	
	public void excluir() {
		try {
			talhaoService.excluir(talhaoSelecionado);			
			this.talhoes.remove(talhaoSelecionado);
			MessageUtil.sucesso("Talhão " + talhaoSelecionado.getNome() + " excluído com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
}
