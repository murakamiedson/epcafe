package com.cafe.controller.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.Talhao;
import com.cafe.modelo.Unidade;
import com.cafe.service.TalhaoService;
import com.cafe.service.PropriedadeService;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @author joao
 *
 */
@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class CadastroTalhaoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Talhao talhao;	
	
	private List<Talhao> talhoes;
	private List<Unidade> unidades;
	
	@Inject
	private TalhaoService talhaoService;
	
	@Inject
	private PropriedadeService propriedadeService;	
	
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
			this.talhaoService.salvar(talhao);
			MessageUtil.sucesso("Talhao salvo com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}		
		this.limpar();
	}
	
	public void limpar() {
		this.talhao = new Talhao();
		this.talhao.setUnidade(usuarioLogado.getUsuario().getUnidade());
		this.talhao.setTenant_id(tenantId);
	}	 
}
