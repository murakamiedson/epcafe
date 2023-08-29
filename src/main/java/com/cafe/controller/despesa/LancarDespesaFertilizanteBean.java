package com.cafe.controller.despesa;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.DespesaFertilizante;
import com.cafe.modelo.Fertilizante;
import com.cafe.modelo.QuantidadeTalhao;
import com.cafe.service.DespesaFertilizanteService;
import com.cafe.service.FertilizanteService;
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
public class LancarDespesaFertilizanteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDate mesAno;
	private List<Fertilizante> fertilizantes;
	private DespesaFertilizante despesaFertilizante;
	private List<DespesaFertilizante> despesas = new ArrayList<>();
	private QuantidadeTalhao quantidadeTalhao;

	@Inject
	private LoginBean loginBean;

	@Inject
	private FertilizanteService fertilizanteService;

	@Inject
	private DespesaFertilizanteService despesaService;

	@PostConstruct
	public void inicializar() {

		log.info("inicializar login = " + loginBean.getUsuario());
		// mesAno = LocalDate.now();
		fertilizantes = fertilizanteService.buscarFertilizantes(loginBean.getTenantId());

		despesas = despesaService.buscarDespesasFertilizantes(loginBean.getTenantId());

		limpar();
	}

	public void salvar() {

		despesaFertilizante.setMesAno(mesAno);
		log.info("mesAno: " + mesAno);
		log.info("salvar ..." + despesaFertilizante);
		
		if(despesaFertilizante.getQdesTalhoes() == null) {
			despesaFertilizante = this.despesaService.criarDistribuicao(despesaFertilizante, loginBean.getUsuario().getUnidade());
		}

		try {
			despesaFertilizante = this.despesaService.salvar(despesaFertilizante);
			this.despesas = despesaService.buscarDespesasFertilizantes(loginBean.getTenantId());
			MessageUtil.sucesso("Despesa salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
		this.limpar();

	}

	public void excluirDespesa() {
		try {
			log.info("excluindo...");
			despesaService.excluir(despesaFertilizante);
			this.despesas = despesaService.buscarDespesasFertilizantes(loginBean.getTenantId());
			MessageUtil.sucesso("Despesa " + despesaFertilizante.getId() + " excluído com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}

	public void salvarQuantidadeTalhao() {
		
		try {
			quantidadeTalhao = this.despesaService.salvarQuantidadeTalhao(quantidadeTalhao);
			MessageUtil.sucesso("Quantidades de talhões salvas com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
		this.limpar();
	}
	
	public void excluirQuantidadeTalhao() {
		try {
			log.info("excluindo quantidades de talhoes...");
			despesaService.excluirQuantidadeTalhao(quantidadeTalhao);
			this.despesas = despesaService.buscarDespesasFertilizantes(loginBean.getTenantId());
			MessageUtil.sucesso("Quantidade " + quantidadeTalhao.getId() + " excluída com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}

	public void limpar() {
		log.info("limpar");
		despesaFertilizante = new DespesaFertilizante();
		despesaFertilizante.setTenant_id(loginBean.getUsuario().getTenant().getCodigo());
	}
	
	
	public void onRowEdit(RowEditEvent<QuantidadeTalhao> event) {
		log.info("editado");
		MessageUtil.info("editado");
		try {
			despesaFertilizante = this.despesaService.salvar(despesaFertilizante);    			
			MessageUtil.sucesso("Qde salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
    }

    public void onRowCancel(RowEditEvent<QuantidadeTalhao> event) {
    	log.info("cancelado");
    	MessageUtil.info("cancelado");
    }
    
    public void onCellEdit(CellEditEvent<?> event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
        	log.info("gravado");
        	try {
    			despesaFertilizante = this.despesaService.salvar(despesaFertilizante);    			
    			MessageUtil.sucesso("Qde salva com sucesso!");
    		} catch (NegocioException e) {
    			e.printStackTrace();
    			MessageUtil.erro(e.getMessage());
    		}
        }
    }
    
}
