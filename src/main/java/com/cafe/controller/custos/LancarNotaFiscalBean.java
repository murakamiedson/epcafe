package com.cafe.controller.custos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
	private String imagemConvertida = "teste";

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
		//notaFiscal.setItens(new ArrayList<Item>());
		notaFiscal.setTenant_id(loginBean.getTenantId());
		notaFiscal.setUnidade(loginBean.getUsuario().getUnidade());
		//nfGravada = false;
	}

	public void limparItem() {
		this.item = new Item();
	}

	public void salvarItem() {
		log.info("salvar item " + this.item);								
		
		if(this.item.getId() == null) {	
			
			log.info("item novo adicionando ..." + this.item);
			this.item.setNotaFiscal(notaFiscal);
			this.notaFiscal.getItens().add(this.item);
			log.info("size --> " + this.notaFiscal.getItens().size());
			MessageUtil.sucesso("Item adicionado com sucesso!");
			limparItem();
			log.info("item limpo " + this.item);	
		}
		else {
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

		//log.info("arquivo " + this.uploadedFile);

		try {
			//TODO
			//this.upload();
			
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
		return this.notaFiscalService.buscarNotasFiscais(loginBean.getUnidadeTemp());
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

	public void upload() {
		log.info("entrou no metodo upload");
		
		//File file = new File(uploadedFile.getFileName());

		//TODO gravar o caminho do arquivo no sistema 
		//de arquivos do servidor, fora da aplicação
		//notaFiscal.setFileName(file.getAbsolutePath());
		byte[] fileBytes = uploadedFile.getContent();
		notaFiscal.setImagem(fileBytes);

	}
	
	public String getImagemNotaFiscalURL() {
		log.info("Metodo de conversao de Imagem. Nota Fiscal selecionado: " + notaFiscal.getId());
	    if (notaFiscal != null && notaFiscal.getImagem() != null) {
	        return "data:image/png;base64," + Base64.getEncoder().encodeToString(notaFiscal.getImagem());
	    } else {
	        return null;
	    }
	}
	
	public void converterImagem() {
		log.info("Metodo de conversao de Imagem. Nota Fiscal selecionado: " + notaFiscal.getId());
	    if (notaFiscal != null && notaFiscal.getImagem() != null) {
	        this.imagemConvertida =  "data:image/png;base64," + Base64.getEncoder().encodeToString(notaFiscal.getImagem());
	    } else {
	        this.imagemConvertida = null;
	    }
	}

	
}
