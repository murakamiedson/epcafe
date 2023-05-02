package com.cafe.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.modelo.Instalacao;
import com.cafe.modelo.Unidade;
import com.cafe.service.InstalacaoService;
import com.cafe.service.UnidadeService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @author isabella
 *
 */
@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class CadastroInstalacaoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Instalacao instalacao;	
	
	private List<Instalacao> instalacoes;
	private List<Unidade> unidades;
	
	@Inject
	private InstalacaoService instalacaoService;
	
	@Inject
	private UnidadeService unidadeService;	
	
	@Inject
	private LoginBean usuarioLogado;
	private Long tenantId;		
	
	@PostConstruct
	public void inicializar() {
		
		tenantId = usuarioLogado.getUsuario().getTenant().getCodigo();
		log.info("Bean : tenant = " + tenantId + "-" + usuarioLogado.getUsuario().getTenant().getTenant());	
		
		//this.unidades = unidadeService.buscarUnidades(tenantId);
		
		this.limpar();
	}
		
	
	public void salvar() {
		try {			
			this.instalacaoService.salvar(instalacao);
			MessageUtil.sucesso("Instalacao salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}		
		this.limpar();
	}
	
	public void limpar() {
		this.instalacao = new Instalacao();
		this.instalacao.setUnidade(usuarioLogado.getUsuario().getUnidade());
		this.instalacao.setTenant_id(tenantId);
	}	 
}
