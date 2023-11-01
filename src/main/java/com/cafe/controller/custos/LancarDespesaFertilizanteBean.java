package com.cafe.controller.custos;

import java.io.Serializable;
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
import com.cafe.modelo.QuantidadeTalhao;
import com.cafe.modelo.enums.TipoInsumo;
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

	private List<Fertilizante> fertilizantes;
	private List<TipoInsumo> tiposInsumo;
	private TipoInsumo auxiliar;
	private DespesaFertilizante despesaFertilizante;
	private List<DespesaFertilizante> despesas = new ArrayList<>();
	private QuantidadeTalhao quantidadeTalhao;
	private NotaFiscal notaFiscal;
	private String numeroNF;

	@Inject
	private LoginBean loginBean;

	@Inject
	private FertilizanteService fertilizanteService;

	@Inject
	private DespesaFertilizanteService despesaService;

	@PostConstruct
	public void inicializar() {

		log.info("inicializar login = " + loginBean.getUsuario());
		
		despesas = despesaService.buscarDespesasFertilizantes(loginBean.getTenantId());

		this.tiposInsumo = Arrays.asList(TipoInsumo.FERTILIZANTE, TipoInsumo.FUNGICIDA, TipoInsumo.HERBICIDA,
				TipoInsumo.INSETICIDA, TipoInsumo.ADJUVANTE);
		
		this.fertilizantes = this.fertilizanteService.buscarFertilizantes(loginBean.getTenantId());

		limpar();
	}

	public void salvar() {
		log.info("salvar ...1" + despesaFertilizante);
		if(numeroNF != null && !numeroNF.isEmpty()) {
			despesaFertilizante
				.setNotaFiscal(this.despesaService.buscarNotaFiscalPorNumero(numeroNF, loginBean.getTenantId()));
		}

		log.info("salvar ...2" + despesaFertilizante);

		if (despesaFertilizante.getQdesTalhoes() == null) {
			despesaFertilizante = this.despesaService.criarDistribuicao(despesaFertilizante,
					loginBean.getUsuario().getPropriedade());
		}

		log.info("salvar ...3" + despesaFertilizante);
		try {
			despesaFertilizante = this.despesaService.salvar(despesaFertilizante);
			log.info("salvar ...4" + despesaFertilizante);
			this.despesas = despesaService.buscarDespesasFertilizantes(loginBean.getTenantId());
			log.info("salvar ...5" + despesaFertilizante);
			MessageUtil.sucesso("Despesa salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
		this.limpar();

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
		List<NotaFiscal> notasFiscais = this.despesaService.buscarNotasFiscais(loginBean.getTenantId());
		for (NotaFiscal notaFiscal : notasFiscais) {
			notasFiscaisList.add(notaFiscal.getNumero());
		}

		return notasFiscaisList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
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

	public void salvarNotaFiscal() {

		try {
			log.info("numero da nf:" + notaFiscal);
			notaFiscal.setTenant_id(loginBean.getTenantId());
			notaFiscal = this.despesaService.salvarNotaFiscal(notaFiscal);
			MessageUtil.sucesso("Nota Fiscal salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
		this.limpar();
	}

	public void excluirNotaFiscal() {
		try {
			log.info("excluindo nota fiscal...");
			despesaService.excluirNotaFiscal(notaFiscal);
			MessageUtil.sucesso("Nota Fiscal " + notaFiscal.getId() + " excluída com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}

	public void limpar() {
		log.info("limpar");
		auxiliar = null;
		
		despesaFertilizante = new DespesaFertilizante();
		despesaFertilizante.setTenant_id(loginBean.getUsuario().getTenant().getCodigo());
		notaFiscal = new NotaFiscal();
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
