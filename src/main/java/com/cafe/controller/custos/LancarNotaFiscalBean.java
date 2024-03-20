package com.cafe.controller.custos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.NotaFiscal;
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
@ViewScoped
public class LancarNotaFiscalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private NotaFiscal notaFiscal;
	private NotaFiscal notaFiscalSelecionada;
	private List<NotaFiscal> notas;
	
	private UploadedFile uploadedFile;
	
	@Inject
	private LoginBean loginBean;

	@Inject
	private NotaFiscalService notaFiscalService;

	@Inject
	private CalculoUtil calcUtil;

	private String yearRange;

	@PostConstruct
	public void inicializar() {
		
		log.info("inicializando lancarNotaFiscalBean...");
		this.yearRange = this.calcUtil.getAnoCorrente();
		
		notas = this.notaFiscalService.buscarNotasFiscais(loginBean.getTenantId());

		limpar();
	}

	public void limpar() {
		log.info("limpar");
		notaFiscal = new NotaFiscal();

		notaFiscal.setTenant_id(loginBean.getTenantId());
	}

	public void salvar() {

		log.info("salvando nota fiscal " + notaFiscal.getNumero());
		log.info(this.uploadedFile.getFileName());
		try {
			this.notaFiscalService.salvar(notaFiscal);
			this.notas = this.notaFiscalService.buscarNotasFiscais(loginBean.getTenantId());
			MessageUtil.sucesso("Nota Fiscal salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
		this.limpar();
	}

	public void excluir() {
		
		log.info("exlcuindo nota fiscal " + notaFiscal.getNumero());
		
		try {
			notaFiscalService.excluir(notaFiscalSelecionada);
			this.notas = this.notaFiscalService.buscarNotasFiscais(loginBean.getTenantId());
			MessageUtil.sucesso("Nota Fiscal " + notaFiscalSelecionada.getNumero() + " excluída com sucesso.");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}
	
	public void upload(FileUploadEvent event) {
		log.info("entrou no metodo upload");
		try {
			this.uploadedFile = event.getFile();
			log.info("uploaded file:"+event.getFile().getFileName());
			File file = new File(uploadedFile.getFileName());
		 
			OutputStream out = new FileOutputStream(file);
	        out.write(uploadedFile.getContent());
	        out.close();
		 
	        FacesContext.getCurrentInstance().addMessage(
	                   null, new FacesMessage("Upload completo", 
	                   "O arquivo " + uploadedFile.getFileName() + " foi salvo!"));
		  } catch(IOException e) {
		    FacesContext.getCurrentInstance().addMessage(
		              null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", e.getMessage()));
		  }
	}
	
	public void teste() {
		log.info("uploaded file name: " + this.uploadedFile.getFileName());
	}
	
}
