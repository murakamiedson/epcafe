package com.cafe.controller.custos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.DespesaFertilizante;
import com.cafe.modelo.Fertilizante;
import com.cafe.modelo.NotaFiscal;
import com.cafe.modelo.Talhao;
import com.cafe.modelo.DespesaFerTalhao;
import com.cafe.modelo.enums.TipoInsumo;
import com.cafe.service.DespesaFertilizanteService;
import com.cafe.service.FertilizanteService;
import com.cafe.service.NotaFiscalService;
import com.cafe.service.TalhaoService;
import com.cafe.util.CalculoUtil;
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

	private List<Fertilizante> fertilizantes;
	private List<TipoInsumo> tiposInsumo;
	private List<Talhao> talhoesPorUnidade;
	private TipoInsumo auxiliar;
	private DespesaFertilizante despesaFertilizante;
	private List<DespesaFertilizante> despesas = new ArrayList<>();
	private List<DespesaFerTalhao> listaQdeTalhoes;
	private DespesaFerTalhao despesaFerTalhao;
	private NotaFiscal notaFiscal;
	private String numeroNF;
	private String yearRange;
	private boolean despesaGravada = false;

	@Inject
	private LoginBean loginBean;

	@Inject
	private FertilizanteService fertilizanteService;

	@Inject
	private DespesaFertilizanteService despesaService;

	@Inject
	private NotaFiscalService notaFiscalService;

	@Inject
	private TalhaoService talhaoService;

	@Inject
	private CalculoUtil calcUtil;

	@PostConstruct
	public void inicializar() {

		log.info("inicializar login = " + loginBean.getUsuario());

		despesas = despesaService.buscarDespesasFertilizantes(loginBean.getTenantId());

		this.yearRange = this.calcUtil.getAnoCorrente();

		this.tiposInsumo = Arrays.asList(TipoInsumo.FERTILIZANTE, TipoInsumo.FUNGICIDA, TipoInsumo.HERBICIDA,
				TipoInsumo.INSETICIDA, TipoInsumo.ADJUVANTE);

		this.fertilizantes = this.fertilizanteService.buscarFertilizantes(loginBean.getTenantId());

		limpar();
	}

	public void salvar() {
		log.info("salvar ...1" + despesaFertilizante);
			
			
			if (numeroNF != null && !numeroNF.isEmpty()) {
				despesaFertilizante.setNotaFiscal(
						this.notaFiscalService.buscarNotaFiscalPorNumero(numeroNF, loginBean.getTenantId()));
			}

		try {
			if (!this.despesaService.validarNotaSelecionada(despesaFertilizante.getNotaFiscal(),
					despesaFertilizante.getFertilizante())) {
				throw new NegocioException("Escolha uma nota fiscal que contenha o fertilizante selecionado");
				//MessageUtil.erro("Escolha uma nota fiscal que contenha o fertilizante selecionado");
			} 
			despesaFertilizante = this.despesaService.salvar(despesaFertilizante);
			this.carregarTalhoes(despesaFertilizante);
			this.despesas = despesaService.buscarDespesasFertilizantes(loginBean.getTenantId());

			MessageUtil.sucesso("Despesa salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}

	}

	public void carregarTipos() {

		this.fertilizantes = this.fertilizanteService.buscarFertilizantePorTipoInsumo(auxiliar,
				loginBean.getTenantId());
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

	public List<String> completeText(String query) {
		String queryLowerCase = query.toLowerCase();
		// List<String> countryList = new ArrayList<>();
		List<String> notasFiscaisList = new ArrayList<>();
		// List<Country> countries = countryService.getCountries();
		Long fertilizanteId = despesaFertilizante.getFertilizante().getId();
		log.info(fertilizanteId);
		List<NotaFiscal> notasFiscais = this.notaFiscalService.buscarNotaFiscalPorFertilizante(fertilizanteId,
				loginBean.getTenantId());
		for (NotaFiscal notaFiscal : notasFiscais) {
			notasFiscaisList.add(notaFiscal.getNumero());
		}

		return notasFiscaisList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
	}

	public void salvarQuantidadeTalhao() {

		try {
			for (DespesaFerTalhao despesaTalhao : listaQdeTalhoes) {
				despesaTalhao.setValor(this.despesaService.calcularValorTalhao(despesaTalhao));
				despesaService.salvarQuantidadeTalhao(despesaTalhao);
			}
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
			despesaService.excluirQuantidadeTalhao(despesaFerTalhao);
			this.despesas = despesaService.buscarDespesasFertilizantes(loginBean.getTenantId());
			MessageUtil.sucesso("Quantidade " + despesaFerTalhao.getId() + " excluída com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}

	public void limpar() {
		log.info("limpar");
		auxiliar = null;

		despesaFertilizante = new DespesaFertilizante();
		despesaFertilizante.setDespesasTalhoes(new ArrayList<DespesaFerTalhao>());
		despesaFertilizante.setTenant_id(loginBean.getUsuario().getTenant().getCodigo());
		notaFiscal = new NotaFiscal();
		log.info(despesaGravada);
	}

	public void carregarTalhoes(DespesaFertilizante despesaFertilizante) {
		log.info("carregando talhoes");

		this.talhoesPorUnidade = this.talhaoService.buscarTalhoesPorUnidade(loginBean.getUnidadeTemp(),
				loginBean.getTenantId());

		for (Talhao talhao : talhoesPorUnidade) {
			log.info("entrou no for");
			DespesaFerTalhao qtdTalhao = new DespesaFerTalhao();
			qtdTalhao.setTalhao(talhao);
			qtdTalhao.setTenantId(loginBean.getTenantId());
			log.info(despesaFertilizante.getId());
			qtdTalhao.setDespesafertilizante(despesaFertilizante);
			qtdTalhao.setValor(new BigDecimal(3));

			despesaFertilizante.getDespesasTalhoes().add(qtdTalhao);
		}
		log.info(despesaFertilizante.getDespesasTalhoes());
		this.listaQdeTalhoes = despesaFertilizante.getDespesasTalhoes();
		this.limpar();
	}

	public void onRowEdit(RowEditEvent<DespesaFerTalhao> event) {
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

	public void onRowCancel(RowEditEvent<DespesaFerTalhao> event) {
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
