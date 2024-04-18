package com.cafe.controller.custos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
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
		//nfGravada = false;
	}

	public void limparItem() {
		item = new Item();
	}
	public void editarNota() {
		nfGravada = true;
	}

	public void salvarItem() {
		try {
			log.info("salvar item ");
			
			validarTotal(item);						
			
			if(item.getId() == null) {
				this.item.setNotaFiscal(notaFiscal);
				this.notaFiscal.getItens().add(item);
				MessageUtil.sucesso("Item adicionado com sucesso!");
				limparItem();
			}
			else {
				MessageUtil.sucesso("Item alterado com sucesso!");
			}
			log.info("size item --> " + this.notaFiscal.getItens().size());
		} catch (NegocioException e) {
			
			notaFiscal = this.notaFiscalService.buscarNotaFiscalPeloCodigo(notaFiscal.getId());
			
			e.printStackTrace();
			MessageUtil.alerta(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			MessageUtil.erro("Um problema ocorreu, o item não foi adicionado!");
		}
		
	}
	public void validarTotal(Item itemAdicionado) throws NegocioException {
		
		BigDecimal total = new BigDecimal(0);
		total = total.add(itemAdicionado.getValor().multiply(itemAdicionado.getQuantidade()));
		for(Item i : notaFiscal.getItens()) {
			total = total.add(i.getValor().multiply(i.getQuantidade()));
			
		}
		
		//total > notaFiscal.getValorTotal()
		if(total.compareTo(notaFiscal.getValorTotal()) == 1) {
			
			throw new NegocioException("Valor total dos itens é maior que o total da nota fiscal!"
					+ "(Valor restante = " + (total.subtract(notaFiscal.getValorTotal())) + ")");
		}	
	}
		
	public void excluirItem() {
		try {
			log.info("remover item nr  = " + item.getId());			
			this.notaFiscal.getItens().remove(item);
			log.info("qde  = " + this.notaFiscal.getItens().size());
			
			this.notaFiscalService.excluirItem(item);

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
			this.upload();
			notaFiscal = this.notaFiscalService.salvar(notaFiscal);
			this.notas = buscarNotas();
			log.info("qde notas fiscais = " + notas.size());
			nfGravada = true;

			MessageUtil.sucesso("Nota Fiscal salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}

		log.info("gravada nf id = " + this.notaFiscal.getId());
	}	

	private List<NotaFiscal> buscarNotas() {
		return this.notaFiscalService.buscarNotasFiscais(loginBean.getTenantId());
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
