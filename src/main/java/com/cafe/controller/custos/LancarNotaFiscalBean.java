package com.cafe.controller.custos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
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
import com.cafe.modelo.Fertilizante;
import com.cafe.modelo.Item;
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
	private List<NotaFiscal> notas = new ArrayList<NotaFiscal>();
	private Item item;
	private UploadedFile uploadedFile;
	private String yearRange;
	private List<Fertilizante> fertilizantes = new ArrayList<Fertilizante>();
	private boolean nfGravada = false;

	@Inject
	private LoginBean loginBean;
	@Inject
	private NotaFiscalService notaFiscalService;
	@Inject
	private CalculoUtil calcUtil;

	@PostConstruct
	public void inicializar() {

		log.info("inicializando lancarNotaFiscalBean...");
		log.info("nfGravada  = " + nfGravada);
		this.yearRange = this.calcUtil.getAnoCorrente();

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
		notaFiscal.setItens(new ArrayList<Item>());
		notaFiscal.setTenant_id(loginBean.getTenantId());
		nfGravada = false;
	}

	public void limparItem() {
		log.info("limpar item");
		item = new Item();
	}
	public void editarNota() {
		nfGravada = true;
		log.info("editarNota : " + nfGravada);
		
	}

	public void salvarItem() {
		try {
			log.info("nf id = " + notaFiscal.getId());
			this.item.setNotaFiscal(notaFiscal);
			this.notaFiscal.getItens().add(item);
			log.info("size item --> " + this.notaFiscal.getItens().size());
			MessageUtil.sucesso("Item adicionado com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.erro("Um problema ocorreu, o item não foi adicionado!");
		}
		limparItem();
	}

	public void salvar() {

		log.info("ID nota fiscal " + notaFiscal.getId());
		log.info("NR nota fiscal " + notaFiscal.getNumero());

		// log.info(this.uploadedFile.getFileName());

		try {
			notaFiscal = this.notaFiscalService.salvar(notaFiscal);
			this.notas = buscarNotas();
			log.info("notas fiscais = " + notas.size());
			nfGravada = true;

			MessageUtil.sucesso("Nota Fiscal salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}

		log.info("gravada nf id = " + this.notaFiscal.getId());
		log.info("nfGravada  = " + nfGravada);
	}
	public void atualizarNota() {

		log.info("atualizar nota " + notaFiscal.getId());
		
		try {
			this.notaFiscalService.atualizarNota(notaFiscal);
			this.notas = buscarNotas();
			nfGravada = true;

			MessageUtil.sucesso("Nota Fiscal atualizada com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}
	}

	private List<NotaFiscal> buscarNotas() {
		return this.notaFiscalService.buscarNotasFiscais(loginBean.getTenantId());
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
			log.info("uploaded file:" + event.getFile().getFileName());
			File file = new File(uploadedFile.getFileName());

			OutputStream out = new FileOutputStream(file);
			out.write(uploadedFile.getContent());
			out.close();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Upload completo", "O arquivo " + uploadedFile.getFileName() + " foi salvo!"));
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", e.getMessage()));
		}
	}

	public void teste() {
		log.info("uploaded file name: " + this.uploadedFile.getFileName());
	}

}
