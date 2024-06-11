package com.cafe.controller.custos;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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
import com.cafe.service.NotaFiscalService;
import com.cafe.util.CalculoUtil;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;
import com.cafe.util.pdf.PdfUtil;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class LancarNotaFiscalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private NotaFiscal notaFiscal;
	private NotaFiscal notaFiscalSelecionada;
	private List<NotaFiscal> notas = new ArrayList<NotaFiscal>();
	private Item item;
	private String yearRange;
	private List<Fertilizante> fertilizantes = new ArrayList<Fertilizante>();
	private String periodoSelecionado;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private List<String> anos = new ArrayList<>();

	private Unidade unidade;
	private UploadedFile file;

	@Inject
	private LoginBean loginBean;
	@Inject
	private NotaFiscalService notaFiscalService;
	@Inject
	private CalculoUtil calcUtil;


	@PostConstruct
	public void inicializar() {

		log.info("inicializando lancarNotaFiscalBean...");
		this.yearRange = this.calcUtil.getAnoCorrente();
		this.unidade = loginBean.getUsuario().getUnidade();

		fertilizantes = notaFiscalService.buscarFertilizantes(loginBean.getTenantId());
		
		//filtrar por ano
		anos = notaFiscalService.buscarAnosComRegistros(loginBean.getUsuario().getUnidade());
		dataInicio = LocalDate.now().withMonth(Month.AUGUST.getValue()).withDayOfMonth(1).minusYears(1);
		dataFim = LocalDate.now().withMonth(Month.JULY.getValue()).withDayOfMonth(31);
		notas = notaFiscalService.buscarNotasFiscaisPorAno(dataInicio, dataFim, loginBean.getUsuario().getUnidade());
		
		
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

		if (this.item.getId() == null) {

			log.info("item novo adicionando ..." + this.item);
			this.item.setNotaFiscal(notaFiscal);
			this.notaFiscal.getItens().add(this.item);
			log.info("size --> " + this.notaFiscal.getItens().size());
			MessageUtil.sucesso("Item adicionado com sucesso!");
			limparItem();
			log.info("item limpo " + this.item);
		} else {
			MessageUtil.sucesso("Item alterado com sucesso!");
		}

		log.info("size item --> " + this.notaFiscal.getItens().size());
		log.info("itens --> " + this.notaFiscal.getItens());
	}

	public void excluirItem() {
		try {
			log.info("remover item nr  = " + this.item.getId());
			this.notaFiscal.getItens().remove(this.item);
			log.info("qde  = " + this.notaFiscal.getItens().size());

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
        		MessageUtil.sucesso("O arquivo '" + this.file.getFileName() + "' foi enviado. Salve a NF para gravar o arquivo.");
                
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
			
			File arquivoPDF = new File(arquivoPath);
			
	        if (arquivoPDF.exists()) {
	
	            // Abra o arquivo PDF com o aplicativo padrão associado
	            Desktop.getDesktop().open(arquivoPDF);
	        }
    	}
	}
	
	public String getNomeArquivo() {
		 if (this.notaFiscal.getUrl() != null)
			 return "Já existe NF gravada. O upload de nova NF substituirá a anterior."; 
		 
		 return "Nenhuma nota gravada ainda.";
	}
	
	
	
	
	/**
	 * 
	 * UPLOAD E DOWNLOAD DE IMAGEM GRAVADA NO BANCO
	 * 
	 *
	 *
	 *
	 *
	// atributo entity
	private byte[] imagem;
	
	
	// upload bean
	
	private UploadedFile originalImageFile;
	
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
	
	// download - ver
	
	public StreamedContent getImage() {
		log.info("getImageBd... = ");
	
		StreamedContent file;
		
		InputStream in = new ByteArrayInputStream(this.notaFiscal.getImagem());
		        
        file = DefaultStreamedContent.builder()
                .stream(() -> in)
                .build(); 

        return file;
	}
	

	public void filtrarPorAno() {
		
		dataInicio = LocalDate.of(Integer.parseInt(periodoSelecionado.substring(0, 4)), Month.AUGUST, 1);
		dataFim = LocalDate.of(Integer.parseInt(periodoSelecionado.substring(5, 9)), Month.JULY, 31);
		notas = notaFiscalService.buscarNotasFiscaisPorAno(dataInicio, dataFim, loginBean.getUsuario().getUnidade());
		
	}


	// upload

	<p:outputLabel value="Imagem NF"/>
	<h:panelGroup>
		<p:fileUpload mode="advanced"
			multiple="false"
			sizeLimit="1024000" allowTypes="/(\.|\/)(gif|jpe?g|png)$/"
			invalidSizeMessage="Maximum file size allowed is 1 MB"
			invalidFileMessage="only gif | jpg | jpeg | png is allowed"
			update="messages"
			listener="#{lancarNotaFiscalBean.handleFileUpload}"/>
		<p:outputLabel id="imgnf" value="#{lancarNotaFiscalBean.notaFiscal.imagem}"/>			        
	</h:panelGroup>


	// download - ver
	
	<p:column headerText="Arquivo" style="width: 30px; text-align: center">
		<p:commandButton icon="pi pi-eye" title="Ver Imagem" 
			value="Ver NF" 
			oncomplete="PF('imagemDialog').show()" 
			update=":form:imagemDialog"
			process="@this">
			<f:setPropertyActionListener 
				target="#{lancarNotaFiscalBean.notaFiscal}" 
				value="#{nota}" />
		</p:commandButton>
	</p:column>
	
	
	 *
	 *
	 */
	
}
