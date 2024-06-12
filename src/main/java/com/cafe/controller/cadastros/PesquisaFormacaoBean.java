package com.cafe.controller.cadastros;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.Formacao;
import com.cafe.service.FormacaoService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;


/**
 * @author isabella
 *
 */
@Getter
@Setter
@Named
@ViewScoped
public class PesquisaFormacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Formacao> formacoes = new ArrayList<>();
	private Formacao formacaoSelecionada;
	private Part file;
	
	@Inject
	private FormacaoService formacaoService;
	@Inject
	private LoginBean loginBean;

		
	@PostConstruct
	public void inicializar() {
		formacoes = formacaoService.buscarFormacoesPorFuncionario(formacaoSelecionada.getFuncionario() ,loginBean.getTenantId());
	}
	
	public void excluir() {
		try {
			formacaoService.excluir(formacaoSelecionada);			
			this.formacoes.remove(formacaoSelecionada);
			MessageUtil.sucesso("Formação " + formacaoSelecionada.getDescricao() + " excluída com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
}