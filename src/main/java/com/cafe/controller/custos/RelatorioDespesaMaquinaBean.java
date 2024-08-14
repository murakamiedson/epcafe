package com.cafe.controller.custos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.DespesaMaquina;
import com.cafe.modelo.to.DespesaAnualTO;
import com.cafe.modelo.to.DespesaMaquinaTO;
import com.cafe.modelo.to.TotalDespesaTO;
import com.cafe.service.RelatorioMaquinaService;
import com.cafe.service.RelatoriosUtilService;

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

	private LocalDate dataInicio;
	private LocalDate dataFim;
	private DespesaMaquina despesaMaquina;
	private List<DespesaMaquinaTO> despesasTO = new ArrayList<>();
	private List<BigDecimal> despesaTotal1 = new ArrayList<>(13);
	private TotalDespesaTO despesaTotal;
	private List<String> anos = new ArrayList<>();
	private String periodoSelecionado;
	private int ano1;
	private int ano2;
		
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private RelatorioMaquinaService relatorioService;
	
	@Inject
	private RelatoriosUtilService relatorioUtil;

	@PostConstruct
	public void inicializar() {
	
		
		Integer diff = LocalDate.now().getMonthValue() < 8 ? 1 : 0;
		
		dataInicio 	= LocalDate.now().withMonth(Month.AUGUST.getValue()).withDayOfMonth(1).plusYears(0 - diff);
		dataFim 	= LocalDate.now().withMonth(Month.JULY.getValue()).withDayOfMonth(31).plusYears((diff+1)%2);
		
		despesasTO = relatorioService.buscarDespesasTO(dataInicio, dataFim, loginBean.getUsuario().getUnidade());
		List<DespesaAnualTO> nova = despesasTO.stream()
				.map(DespesaMaquinaTO::getTotaisMensais)
				.collect(Collectors.toList());
		despesaTotal = relatorioUtil.calcTotal(nova);
		
		
		anos = relatorioService.buscarAnosComRegistros(loginBean.getUsuario().getUnidade());
		
		log.info("finalizar...");
	}
	
	public void alterarAnosHeader() {
		ano1 = dataInicio.getYear();
		ano2 = dataFim.getYear();
		
	}
	
	public void filtrarPorAno() {
		
		dataInicio = LocalDate.of(Integer.parseInt(periodoSelecionado.substring(0, 4)), Month.AUGUST, 1);
		dataFim = LocalDate.of(Integer.parseInt(periodoSelecionado.substring(5, 9)), Month.JULY, 31);
		this.alterarAnosHeader();
		
		despesasTO = relatorioService.buscarDespesasTO(dataInicio, dataFim, loginBean.getUsuario().getUnidade());
		List<DespesaAnualTO> nova = despesasTO.stream().map(DespesaMaquinaTO::getTotaisMensais).collect(Collectors.toList());
		despesaTotal = relatorioUtil.calcTotal(nova);
		
	}
	
	
	
}