package com.cafe.controller.custos;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.DespesaFerTalhao;
import com.cafe.modelo.DespesaFertilizante;
import com.cafe.modelo.Fertilizante;
import com.cafe.modelo.NotaFiscal;
import com.cafe.modelo.Talhao;
import com.cafe.modelo.Unidade;
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
@SessionScoped
public class LancarDespesaFertilizanteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Fertilizante> fertilizantes;
	private List<TipoInsumo> tiposInsumo;
	private List<Talhao> talhoesPorUnidade;
	private List<NotaFiscal> notasDisponiveis;
	private TipoInsumo auxiliar;
	private DespesaFertilizante despesaFertilizante;
	private List<DespesaFertilizante> despesas = new ArrayList<>();
	private List<DespesaFerTalhao> listaQdeTalhoes;
	private DespesaFerTalhao despesaFerTalhao;
	private NotaFiscal notaFiscal;
	private NotaFiscal notaSelecionada;
	private UploadedFile originalImageFile;
	private String numeroNF;
	private String yearRange;
	private boolean despesaGravada = false;
	private BigDecimal qtdItem;
	private Unidade unidade;
	private String periodoSelecionado;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private List<String> anos = new ArrayList<>();

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

		unidade = loginBean.getUsuario().getUnidade();
		log.info("conseguiu buscar as despesas");
		this.yearRange = this.calcUtil.getAnoCorrente();

		this.tiposInsumo = Arrays.asList(TipoInsumo.FERTILIZANTE, TipoInsumo.FUNGICIDA, TipoInsumo.HERBICIDA,
				TipoInsumo.INSETICIDA, TipoInsumo.ADJUVANTE);

		this.fertilizantes = this.fertilizanteService.buscarFertilizantes(loginBean.getTenantId());
		
		
		//filtrar por ano
		anos = despesaService.buscarAnosComRegistros(loginBean.getUsuario().getUnidade());
		dataInicio = LocalDate.now().withMonth(Month.AUGUST.getValue()).withDayOfMonth(1).minusYears(1);
		dataFim = LocalDate.now().withMonth(Month.JULY.getValue()).withDayOfMonth(31);
		despesas = despesaService.buscarDespesasFertilizantesPorAno(dataInicio, dataFim, unidade);
		limpar();
		//limparDesFerTalhao();
	}

	public void salvar() {
		log.info("salvando despesa ..." + despesaFertilizante);

		if (numeroNF != null && !numeroNF.isEmpty()) {
			despesaFertilizante
					.setNotaFiscal(this.notaFiscalService.buscarNotaFiscalPorNumero
							(numeroNF, unidade));
		}



		if(despesaFertilizante.getId() != null){

			try {
				if (!this.despesaService.validarNotaSelecionada(despesaFertilizante.getNotaFiscal(),
						despesaFertilizante.getFertilizante())) {
					throw new NegocioException("Escolha uma nota fiscal que contenha o fertilizante selecionado");
				}
				if(!this.despesaService.validarQuantidadesTalhoesComNF(despesaFertilizante)) {
					throw new NegocioException("Quantidade de insumo usada por talhão "
							+ "ultrapassa a informada na Nota Fiscal");
				}
				log.info("salvando despesa com id nao nulo");

				this.qtdItem = this.despesaService.getItemDaDespesa(despesaFertilizante).getQuantidade();
				
				this.despesaService.calculaValorPorTalhao(despesaFertilizante);
				
				despesaFertilizante = this.despesaService.salvar(despesaFertilizante);
				

	
				despesaFertilizante.getDespesasTalhoes().forEach(t -> log.info(t.getValor()));
				this.despesas = despesaService.buscarDespesasFertilizantes(unidade);
	
				this.despesaGravada = true;
				MessageUtil.sucesso("Despesa salva com sucesso!");
			} catch (NegocioException e) {
				e.printStackTrace();
				MessageUtil.erro(e.getMessage());
			}
		}else{

			try {
				if (!this.despesaService.validarNotaSelecionada(despesaFertilizante.getNotaFiscal(),
						despesaFertilizante.getFertilizante())) {
					throw new NegocioException("Escolha uma nota fiscal que contenha o fertilizante selecionado");
				}
				
				log.info("salvando despesa com id nulo");

				despesaFertilizante = this.despesaService.salvar(despesaFertilizante);

				this.qtdItem = this.despesaService.getItemDaDespesa(despesaFertilizante).getQuantidade();
				this.carregarTalhoes(despesaFertilizante);

				this.despesas = despesaService.buscarDespesasFertilizantes(unidade);
	
				this.despesaGravada = true;
				MessageUtil.sucesso("Despesa salva com sucesso!");
			} catch (NegocioException e) {
				e.printStackTrace();
				MessageUtil.erro(e.getMessage());
			} 
		}


	}
	
	public void despesaGravadaBool() {
		log.info("Update despesaGravada");
		despesaGravada = true;
		
	}

	public void carregarTipos() {

		this.fertilizantes = this.fertilizanteService.buscarFertilizantePorTipoInsumo(auxiliar,
				loginBean.getTenantId());
	}
	
	public void carregarNotasFiscais() {
		this.notasDisponiveis = this.notaFiscalService.buscarNotaFiscalPorFertilizante(
				despesaFertilizante.getFertilizante().getId(), unidade);
	}
	
	public void selecionarNotaFiscal(NotaFiscal nota) {
	    this.numeroNF = nota.getNumero();
	    log.info("NUMERONF SELECIONADA: " + numeroNF);
	}

	public void excluirDespesa() {
		try {
			log.info("excluindo...");
			despesaService.excluir(despesaFertilizante);
			this.despesas = despesaService.buscarDespesasFertilizantes(unidade);
			MessageUtil.sucesso("Despesa " + despesaFertilizante.getId() + " excluído com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}

	
	public List<String> completeText(String query) {
		String queryLowerCase = query.toLowerCase();
		List<String> notasFiscaisList = new ArrayList<>();
		Long fertilizanteId = despesaFertilizante.getFertilizante().getId();
		
		List<NotaFiscal> notasFiscais = this.notaFiscalService.buscarNotaFiscalPorFertilizante(fertilizanteId,
				unidade);
		for (NotaFiscal notaFiscal : notasFiscais) {
			notasFiscaisList.add(notaFiscal.getNumero());
		}

		return notasFiscaisList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase))
				.collect(Collectors.toList());
	}
	

	public void limpar() {
		log.info("limpar");
		auxiliar = null;
		numeroNF = null;
//		this.despesaGravada = true;

		despesaFertilizante = new DespesaFertilizante();
		despesaFertilizante.setDespesasTalhoes(new ArrayList<DespesaFerTalhao>());
		despesaFertilizante.setUnidade(loginBean.getUsuario().getUnidade());
		despesaFertilizante.setTenantId(loginBean.getTenantId());
		//notaFiscal = new NotaFiscal();
	}

	public void carregarTalhoes(DespesaFertilizante despesaFertilizante) {
		log.info("carregando talhoes");

		this.talhoesPorUnidade = this.talhaoService.buscarTalhoesPorUnidade(unidade,
				loginBean.getTenantId());

		for (Talhao talhao : talhoesPorUnidade) {
			log.info("entrou no for");
			DespesaFerTalhao qtdTalhao = new DespesaFerTalhao();
			qtdTalhao.setTalhao(talhao);
			qtdTalhao.setUnidade(unidade);
			qtdTalhao.setTenantId(loginBean.getTenantId());

			qtdTalhao.setDespesaFertilizante(despesaFertilizante);


			despesaFertilizante.getDespesasTalhoes().add(qtdTalhao);
		}
		log.info(despesaFertilizante.getDespesasTalhoes());
		this.listaQdeTalhoes = despesaFertilizante.getDespesasTalhoes();
		if(despesaFertilizante.getDespesasTalhoes().size()>0)
			this.despesaGravada = true;
		// this.limpar();
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
	
	public void editarDespesa() {
		log.info("editar despesa");
		auxiliar = despesaFertilizante.getFertilizante().getTipoInsumo();
		numeroNF = despesaFertilizante.getNotaFiscal().getNumero();
	}
	

	public void filtrarPorAno() {
		
		dataInicio = LocalDate.of(Integer.parseInt(periodoSelecionado.substring(0, 4)), Month.AUGUST, 1);
		dataFim = LocalDate.of(Integer.parseInt(periodoSelecionado.substring(5, 9)), Month.JULY, 31);
		despesas = despesaService.buscarDespesasFertilizantesPorAno(dataInicio, dataFim, loginBean.getUsuario().getUnidade());
		
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		log.info("upload... = ");

		this.originalImageFile = null;

		UploadedFile file = event.getFile();

		if (file != null && file.getContent() != null && file.getContent().length > 0 && file.getFileName() != null) {
			this.originalImageFile = file;
			
			this.notaFiscal.setImagem(file.getContent());
			 
			MessageUtil.sucesso("Sucesso! " + this.originalImageFile.getFileName() + " foi enviado.");
		}

		log.info("uploaded... = " + this.originalImageFile.getFileName());
	}
	
	public StreamedContent getImage() {
		log.info("getImageBd... = ");
	
		StreamedContent file;
		
		InputStream in = new ByteArrayInputStream(this.notaFiscal.getImagem());
		        
        file = DefaultStreamedContent.builder()
                .stream(() -> in)
                .build(); 

        return file;
	}

}
