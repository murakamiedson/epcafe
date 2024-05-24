package com.cafe.controller.custos;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Setter
@Named
@SessionScoped
public class LancarNotaFiscalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private NotaFiscal notaFiscal;
	private NotaFiscal notaFiscalSelecionada;
	private List<NotaFiscal> notas = new ArrayList<NotaFiscal>();
	private Item item;
	private String yearRange;
	private List<Fertilizante> fertilizantes = new ArrayList<Fertilizante>();

	private Unidade unidade;
	private UploadedFile originalImageFile;

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
		this.notas = buscarNotas();

		for (NotaFiscal n : notas) {
			log.info(n.getItens());
		}

		fertilizantes = notaFiscalService.buscarFertilizantes(loginBean.getTenantId());
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

		// log.info("arquivo " + this.uploadedFile);

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

	/*
	public StreamedContent getImage() {
		log.info("getImage... = ");
		
		return DefaultStreamedContent.builder()
				.contentType(originalImageFile == null ? null : originalImageFile.getContentType()).stream(() -> {
					
					if (originalImageFile == null || originalImageFile.getContent() == null
							|| originalImageFile.getContent().length == 0) {
						return null;
					}

					try {
						return new ByteArrayInputStream(originalImageFile.getContent());
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}).build();
	}
	*/
	
	/*
	 * public void upload() {
	 * 
	 * log.info("qde notas fiscais = " + notas.size());
	 * 
	 * try { File file = new File("/notasfiscais/", nomeArquivo());
	 * 
	 * log.info(file.getAbsolutePath());
	 * this.notaFiscal.setUrlNf(file.getAbsolutePath());
	 * 
	 * OutputStream out = new FileOutputStream(file);
	 * out.write(uploadedFile.getContent()); out.close();
	 * 
	 * MessageUtil.sucesso("Upload completo. O arquivo " +
	 * uploadedFile.getFileName() + " foi salvo!"); } catch(IOException e) {
	 * e.printStackTrace(); MessageUtil.erro("Erro: " + e.getMessage()); } } private
	 * String nomeArquivo() { LocalDate localDate = LocalDate.now(); String
	 * nomeArquivo = notaFiscal.getUnidade().getCodigo() + "-" +
	 * notaFiscal.getNumero().toString() + "-" + localDate.getYear(); return
	 * nomeArquivo; }
	 */
}
