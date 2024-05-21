package com.cafe.controller.custos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.DespesaMaquina;
import com.cafe.modelo.to.DespesaTO;
import com.cafe.modelo.to.TotalDespesaTO;
import com.cafe.service.RelatorioMaquinaService;

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
public class RelatorioDespesaMaquinaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDate mesAno;
	private DespesaMaquina despesaMaquina;
	private List<DespesaTO> despesasTO = new ArrayList<>();
	private List<BigDecimal> despesaTotal1 = new ArrayList<>(13);
	private TotalDespesaTO despesaTotal;
		
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private RelatorioMaquinaService relatorioService;

	@PostConstruct
	public void inicializar() {
	
		log.info("inicializar login = " + loginBean.getUsuario());
		mesAno = LocalDate.now();		
		despesasTO = relatorioService.buscarDespesasTO(mesAno, loginBean.getTenantId());
		log.info("TESTE DESSA PORRAAAA");
		despesaTotal = relatorioService.calcTotal(despesasTO);
		
		log.info("finalizar...");
	}
	
	
	
}