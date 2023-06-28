package com.cafe.controller;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

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
	/*
	public void gravarPdf(){
		try {
			if(getFile() != null) {
				formacaoSelecionada = formacaoService.gravaPdfCadUnico(formacaoSelecionada, getFile());
				// grava a chave de acesso ao arquivo no s3
				formacaoSelecionada = this.formacaoService.salvarComPdf(formacaoSelecionada);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	/*
	public String gravarPdf() throws IOException{
		
		try {
			if(getFile() != null) {
				formacaoSelecionada = s3.gravaPdfCadUnico(prontuarioSelecionado, getFile());
				// grava a chave de acesso ao arquivo no s3
				formacaoSelecionada = this.prontuarioService.salvarComPdf(prontuarioSelecionado);
			}
		}
		return"";
	}
	
	public void redirectPdf(Formacao formacao) throws IOException {

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(formacao.getUrlAnexo());
    }*/
	
}