package com.cafe.controller.cadastros;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.Instalacao;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.enums.TipoInstalacao;
import com.cafe.service.InstalacaoService;
import com.cafe.service.PropriedadeService;
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
	private List<TipoInstalacao> tiposInstalacao;
	
	@Inject
	private InstalacaoService instalacaoService;
	
	@Inject
	private PropriedadeService propriedadeService;	
	
	@Inject
	private LoginBean usuarioLogado;
	private Long tenantId;		
	
	@PostConstruct
	public void inicializar() {
		
		tenantId = usuarioLogado.getUsuario().getTenant().getCodigo();
		log.info("Bean : tenant = " + tenantId + "-" + usuarioLogado.getUsuario().getTenant().getTenant());	
		
		
		this.tiposInstalacao = Arrays.asList(TipoInstalacao.values());
		
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
	
	public void atualizaNome() {
		this.instalacao.setNome(instalacao.getTipo().toString());
	}
	
	public void carregaVidaUtil() {
		this.instalacao.setVidaUtil(instalacao.getTipo().getValor());
	}
	
	public void limpar() {
		this.instalacao = new Instalacao();
		this.instalacao.setUnidade(usuarioLogado.getUsuario().getUnidade());
		this.instalacao.setTenant_id(tenantId);
	}	 
}
