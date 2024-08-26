package com.cafe.controller.custos;

import java.io.File;
import java.io.IOException;
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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.Fertilizante;
import com.cafe.modelo.Item;
import com.cafe.modelo.NotaFiscal;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.enums.Medida;
import com.cafe.modelo.enums.TipoInsumo;
import com.cafe.service.FertilizanteService;
import com.cafe.service.NotaFiscalService;
import com.cafe.util.CalculoUtil;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;
import com.cafe.util.pdf.PdfUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Named
@Getter
@Setter
@ViewScoped
public class LancarNotaFiscalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private NotaFiscal notaFiscal;
	private NotaFiscal notaFiscalSelecionada;
	private List<TipoInsumo> tiposInsumo;
	private TipoInsumo auxiliar;
	private List<NotaFiscal> notas = new ArrayList<NotaFiscal>();
	private Item item;
	private List<Medida> medidas;
	private String yearRange;
	private List<Fertilizante> fertilizantes = new ArrayList<Fertilizante>();
	private String periodoSelecionado;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private List<String> anos = new ArrayList<>();
	private boolean semItens = true;

	private Unidade unidade;
	private UploadedFile file;

	@Inject
	private LoginBean loginBean;
	@Inject
	private NotaFiscalService notaFiscalService;
	@Inject
	private CalculoUtil calcUtil;
	@Inject
	private FertilizanteService fertilizanteService;


	@PostConstruct
	public void inicializar() {

		log.info("inicializando lancarNotaFiscalBean...");
		this.yearRange = this.calcUtil.getAnoCorrente();
		this.unidade = loginBean.getUsuario().getUnidade();

		fertilizantes = notaFiscalService.buscarFertilizantes();
		
		//filtrar por ano
		anos = notaFiscalService.buscarAnosComRegistros(loginBean.getUsuario().getUnidade());
		dataInicio = LocalDate.now().withMonth(Month.AUGUST.getValue()).withDayOfMonth(1).minusYears(1);
		dataFim = LocalDate.now().withMonth(Month.JULY.getValue()).withDayOfMonth(31);
		notas = notaFiscalService.buscarNotasFiscaisPorAno(dataInicio, dataFim, loginBean.getUsuario().getUnidade());
		
		this.tiposInsumo = Arrays.asList(TipoInsumo.FERTILIZANTE, TipoInsumo.FUNGICIDA, TipoInsumo.HERBICIDA,
				TipoInsumo.INSETICIDA, TipoInsumo.ADJUVANTE);
		
		this.medidas = Arrays.asList(Medida.values());
		
		limpar();
		limparItem();
	}

	public void limpar() {
		log.info("limpar nf");
		notaFiscal = new NotaFiscal();
		// notaFiscal.setItens(new ArrayList<Item>());
		notaFiscal.setTenant_id(loginBean.getTenantId());
		notaFiscal.setUnidade(loginBean.getUsuario().getUnidade());
		// nfGravada = false;
	}

	public void limparItem() {
		this.item = new Item();
	}

	public void salvarItem() {
		log.info("salvar item " + this.item);

		try {
			if (this.item.getId() == null) {
	
				log.info("item novo adicionando ..." + this.item);
				this.item.setNotaFiscal(notaFiscal);
				this.notaFiscal.getItens().add(this.item);
				log.info("size --> " + this.notaFiscal.getItens().size());
				MessageUtil.sucesso("Item adicionado com sucesso!");
				limparItem();
				semItens = this.notaFiscal.getItens().isEmpty();
				log.info("item limpo " + this.item);
			} else {
				MessageUtil.sucesso("Item alterado com sucesso!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}

		log.info("size item --> " + this.notaFiscal.getItens().size());
		log.info("itens --> " + this.notaFiscal.getItens());
	}

	public void excluirItem() {
		try {
			log.info("remover item nr  = " + this.item.getId());
			this.notaFiscal.getItens().remove(this.item);
			log.info("qde  = " + this.notaFiscal.getItens().size());
			semItens = this.notaFiscal.getItens().isEmpty();			

			MessageUtil.sucesso("Item excluído com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.erro("Um problema ocorreu, o item não foi excluído!");
		}
		limparItem();
	}

	public void salvar() {

		try {

			notaFiscal = this.notaFiscalService.salvar(notaFiscal);
			semItens = this.notaFiscal.getItens().isEmpty();
			this.notas = buscarNotas();
			log.info("qde notas fiscais = " + notas.size());

			MessageUtil.sucesso("Nota Fiscal salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
		log.info("gravada nf id = " + this.notaFiscal.getId());
	}

	private List<NotaFiscal> buscarNotas() {
		return this.notaFiscalService.buscarNotasFiscais(unidade);
	}

	public void excluir() {

		log.info("exlcuindo nota fiscal " + notaFiscal.getNumero());

		try {
			notaFiscalService.excluir(notaFiscalSelecionada);
			this.notas = buscarNotas();
			MessageUtil.sucesso("Nota Fiscal " + notaFiscalSelecionada.getNumero() + " excluída com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}

	
	public void upload(FileUploadEvent event) {
		
		this.file = event.getFile();
        if (this.file != null) {
        	
        	try {
        		File pdf = PdfUtil.escrever(this.file.getFileName(), this.file.getContent());
        		notaFiscal.setUrl(pdf.getAbsolutePath());

			} catch (IOException e) {
				MessageUtil.erro("Houve um problema para salvar o pdf.");	        	
				e.printStackTrace();
			}      	
        	log.info("uploaded... = " + this.file.getFileName());
        }
    }
	
	public void download(NotaFiscal nf) throws IOException {
    	
    	log.info(nf.getId());
		
    	if(nf.getUrl() != null && !nf.getUrl().isEmpty()) {
    		
			String arquivoPath = nf.getUrl();
			
			log.info(arquivoPath);
			
			try {
				PdfUtil.downloadDesktop(arquivoPath);				
			}
			catch(Exception e) {
				MessageUtil.erro(e.getMessage());
			}	
    	}
	}
	public void download2(NotaFiscal nf) throws IOException {
    	
    	log.info(nf.getId());
		
    	if(nf.getUrl() != null && !nf.getUrl().isEmpty()) {
    		
			String arquivoPath = nf.getUrl();
			
			log.info(arquivoPath);
			
			try {
				PdfUtil.downloadStream(arquivoPath);				
			}
			catch(Exception e) {
				MessageUtil.erro(e.getMessage());
			}	
    	}
	}
	
	public String getNomeArquivo() {
		 if (this.notaFiscal.getUrl() != null)
			 return "Já existe NF gravada. O upload de nova NF substituirá a anterior."; 
		 
		 return "Nenhuma nota gravada ainda.";
	}
	
	public void filtrarPorAno() {
		
		dataInicio = LocalDate.of(Integer.parseInt(periodoSelecionado.substring(0, 4)), Month.AUGUST, 1);
		dataFim = LocalDate.of(Integer.parseInt(periodoSelecionado.substring(5, 9)), Month.JULY, 31);
		notas = notaFiscalService.buscarNotasFiscaisPorAno(dataInicio, dataFim, loginBean.getUsuario().getUnidade());
		
	}
	
	public void carregarTipos() {

		this.fertilizantes = this.fertilizanteService.buscarFertilizantePorTipo(auxiliar);
	}
	
}
