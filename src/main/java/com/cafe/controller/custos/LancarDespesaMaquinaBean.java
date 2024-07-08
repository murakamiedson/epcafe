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
import com.cafe.modelo.DespesaMaquina;
import com.cafe.modelo.Maquina;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.enums.FatorPotencia;
import com.cafe.service.DespesaMaquinaService;
import com.cafe.service.MaquinaService;
import com.cafe.service.RelatorioMaquinaService;
import com.cafe.util.CalculoUtil;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;

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
public class LancarDespesaMaquinaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Maquina> maquinas;
	private DespesaMaquina despesaMaquina;
	private DespesaMaquina despesaMaquinaSelecionada;
	private List<FatorPotencia> fatorPotencias;
	private List<DespesaMaquina> despesas = new ArrayList<>();
	private Long tenantId;
	private Unidade unidade;
	private String periodoSelecionado;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private List<String> anos = new ArrayList<>();
	
	private String nomeMaquina;
	
	private String yearRange;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private MaquinaService maquinaService;
	
	@Inject
	private DespesaMaquinaService despesaService;
	
	@Inject
	private CalculoUtil calcUtil;
	
	@Inject
	private RelatorioMaquinaService relatorioService;

	@PostConstruct
	public void inicializar() {
		
		log.info("inicializar login = " + loginBean.getUsuario());
		dataInicio = LocalDate.now().withMonth(Month.AUGUST.getValue()).withDayOfMonth(1).minusYears(1);
		dataFim = LocalDate.now().withMonth(Month.JULY.getValue()).withDayOfMonth(31);
		unidade = loginBean.getUsuario().getUnidade();
		this.yearRange = this.calcUtil.getAnoCorrente();
		maquinas = maquinaService.buscarMaquinasAlfabetico(loginBean.getTenantId());
		fatorPotencias = Arrays.asList(FatorPotencia.values());
		despesas = despesaService.buscarDespesasMaquinasPorAno(dataInicio, dataFim, loginBean.getUsuario().getUnidade());
		anos = relatorioService.buscarAnosComRegistros(loginBean.getUsuario().getUnidade());
		
		limpar();
	}
	
    public void salvar() {
    	
    	log.info("salvaR ..." + despesaMaquina);

    	try {
    		this.despesaMaquina = this.despesaService.salvar(despesaMaquina);
    		log.info("salvaDO ..." + despesaMaquina);
    		this.despesas = despesaService.buscarDespesasMaquinasPorAno(dataInicio, dataFim, unidade);
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
			despesaService.excluir(despesaMaquinaSelecionada);			
			this.despesas = despesaService.buscarDespesasMaquinasPorAno(dataInicio, dataFim, unidade);
			MessageUtil.sucesso("Despesa " + despesaMaquinaSelecionada.getId() + " excluída com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
    }
    

	public void limpar() {
		log.info("limpar");
		despesaMaquina = new DespesaMaquina();

		despesaMaquina.setUnidade(loginBean.getUsuario().getUnidade());
		despesaMaquina.setTenant_id(loginBean.getUsuario().getTenant().getCodigo());
	}
	
	public void filtrarPorAno() {
		
		dataInicio = LocalDate.of(Integer.parseInt(periodoSelecionado.substring(0, 4)), Month.AUGUST, 1);
		dataFim = LocalDate.of(Integer.parseInt(periodoSelecionado.substring(5, 9)), Month.JULY, 31);
		despesas = despesaService.buscarDespesasMaquinasPorAno(dataInicio, dataFim, loginBean.getUsuario().getUnidade());
		
	}
	
	
}