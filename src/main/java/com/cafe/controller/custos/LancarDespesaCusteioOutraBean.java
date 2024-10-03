package com.cafe.controller.custos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.DespesaCusteioOutra;
import com.cafe.modelo.enums.TipoCusteioOutros;
import com.cafe.service.DespesaCusteioOutraService;
import com.cafe.util.CalculoUtil;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class LancarDespesaCusteioOutraBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<DespesaCusteioOutra> despesas = new ArrayList<>();
	private List<TipoCusteioOutros> tipos;
	private List<TipoCusteioOutros> tipoCusteio;
	private List<String> anos;
	private DespesaCusteioOutra despesaCusteioOutra;
	private String yearRange;
	private String periodoSelecionado;
	private LocalDate dataInicio;
	private LocalDate dataFim;

	@Inject
	private DespesaCusteioOutraService despesaService;

	@Inject
	private LoginBean loginBean;

	@Inject
	private CalculoUtil calcUtil;

	@PostConstruct
	public void inicializar() {

		this.tipos = Arrays.asList(TipoCusteioOutros.values());

		this.yearRange = this.calcUtil.getAnoCorrente();
		
		

		// filtrar por ano
		Integer diff = LocalDate.now().getMonthValue() < 8 ? 1 : 0;
		this.anos = despesaService.buscarAnosComRegistro(loginBean.getUsuario().getUnidade());
		this.dataInicio 	= LocalDate.now().withMonth(Month.AUGUST.getValue()).withDayOfMonth(1).plusYears(0 - diff);
		this.dataFim 	= LocalDate.now().withMonth(Month.JULY.getValue()).withDayOfMonth(31).plusYears((diff+1)%2);
		this.despesas = this.despesaService.buscarOutrasDespesasPorAnoAgricola(dataInicio, dataFim,
				this.loginBean.getUsuario().getUnidade());

		limpar();
	}

	public void limpar() {
		this.despesaCusteioOutra = new DespesaCusteioOutra();
		this.despesaCusteioOutra.setTenantId(this.loginBean.getTenantId());
		this.despesaCusteioOutra.setUnidade(this.loginBean.getUsuario().getUnidade());
	}

	public void salvar() {

		log.info("salvando outra despesa");
		
		/* Identifica se trata de outras despesas de custeio (relacionada diretamente a custeio)
		 * e outras despesas
		 */
		this.despesaCusteioOutra.setECusteio(this.despesaCusteioOutra.getTipo().getValor() == 1 ? true : false);

		try {
			this.despesaCusteioOutra = this.despesaService.salvar(this.despesaCusteioOutra);

			this.despesas = despesaService.buscarOutrasDespesasPorAnoAgricola(this.dataInicio, this.dataFim,
					this.loginBean.getUsuario().getUnidade());

			MessageUtil.sucesso("Despesa salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
		this.limpar();

	}

	public void excluir() {
		try {
			log.info("excluindo...");
			despesaService.excluir(this.despesaCusteioOutra);
			this.despesas = despesaService.buscarOutrasDespesasPorAnoAgricola(this.dataInicio, this.dataFim,
					this.loginBean.getUsuario().getUnidade());
			MessageUtil.sucesso("Despesa " + this.despesaCusteioOutra.getId() + " excluída com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}

	public void filtrarPorAno() {

		this.dataInicio = LocalDate.of(Integer.parseInt(periodoSelecionado.substring(0, 4)), Month.AUGUST, 1);
		this.dataFim = LocalDate.of(Integer.parseInt(periodoSelecionado.substring(5, 9)), Month.JULY, 31);
		this.despesas = despesaService.buscarOutrasDespesasPorAnoAgricola(dataInicio, dataFim,
				loginBean.getUsuario().getUnidade());

	}
}
