package com.cafe.controller.cadastros;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.Formacao;
import com.cafe.modelo.Funcionario;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.enums.NivelEscolaridade;
import com.cafe.service.FuncionarioService;
import com.cafe.service.PropriedadeService;
import com.cafe.util.CalculoUtil;
import com.cafe.util.MessageUtil;
import com.cafe.util.NegocioException;
import com.cafe.util.pdf.PdfUtil;

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
public class CadastroFuncionarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Funcionario funcionario;
	private Formacao formacao;

	private List<Funcionario> funcionarios;
	private List<Unidade> unidades;
	private List<NivelEscolaridade> niveisEscolaridade;
	private Long tenantId;
	private String yearRange;
	// private UploadedFile originalImageFile;
	private UploadedFile file;

	@Inject
	private CalculoUtil calcUtil;

	@Inject
	private FuncionarioService funcionarioService;

	@Inject
	private PropriedadeService propriedadeService;

	@Inject
	private LoginBean usuarioLogado;

	@PostConstruct
	public void inicializar() {
		tenantId = usuarioLogado.getUsuario().getTenant().getCodigo();
		this.yearRange = this.calcUtil.getAnoCorrente();
		this.niveisEscolaridade = Arrays.asList(NivelEscolaridade.values());
		log.info("Bean : tenant = " + tenantId + "-" + usuarioLogado.getUsuario().getTenant().getTenant());
		this.limpar();
		this.limparFormacao();
	}

	public void salvar() {

		Period periodo = Period.between(funcionario.getDataNascimento(), LocalDate.now());

		if (periodo.getYears() < 18) {
			MessageUtil.erro("Funcionario menor de 18 anos");
			// PrimeFaces.current().executeScript("PF('avisoDialog').show();");
		} else {
			try {
				log.info("FORMAÇÕES:: " + this.funcionario.getFormacoes().stream().map(Formacao::getDescricao)
						.collect(Collectors.joining(", ")));
				this.funcionarioService.salvar(funcionario);
				log.info("Salvando funcionario... ");
				MessageUtil.sucesso("Funcionario salvo com sucesso!");
			} catch (NegocioException e) {
				e.printStackTrace();
				MessageUtil.erro(e.getMessage());
			}
			this.limpar();
		}
	}

	public void limpar() {
		this.funcionario = new Funcionario();
		this.funcionario.setUnidade(usuarioLogado.getUsuario().getUnidade());
		this.funcionario.setTenant_id(tenantId);
		this.funcionario.setFormacoes(new ArrayList<>());

	}

	public void limparFormacao() {
		this.formacao = new Formacao();
		this.formacao.setTenant_id(usuarioLogado.getTenantId());
	}

	public void salvarFormacao() {
		if (this.formacao.getId() == null) {

			log.info("formacao nova adicionando ..." + this.formacao);
			this.formacao.setFuncionario(funcionario);
			log.info("teste formacao: " + this.formacao.getDescricao());
			this.funcionario.getFormacoes().add(this.formacao);

			log.info("size --> " + this.funcionario.getFormacoes().size());
			MessageUtil.alerta("Formação adicionada com sucesso!  SALVE O FUNCIONÁRIO PARA EFETIVAR AS FORMAÇÕES ADICIONADAS.");
		} else {
			MessageUtil.alerta("Formacao alterada com sucesso! SALVE O FUNCIONÁRIO PARA EFETIVAR AS FORMAÇÕES ADICIONADAS.");
		}
		limparFormacao();
		log.info("item limpo " + this.formacao);
	}

	public void upload(FileUploadEvent event) {

		this.file = event.getFile();
		if (this.file != null) {

			try {

				File pdf = PdfUtil.escrever(this.file.getFileName(), this.file.getContent());
				formacao.setUrl(pdf.getAbsolutePath());
				MessageUtil.sucesso("O arquivo '" + this.file.getFileName()
						+ "' foi enviado. Salve o funcionário para gravar o arquivo.");

			} catch (IOException e) {
				MessageUtil.erro("Houve um problema para salvar o arquivo.");
				e.printStackTrace();
			}

			log.info("uploaded... = " + this.file.getFileName());
		}
	}

	/* Descobrir configuração no servidor X11 - não funciona apenas no IFSuldeMinas*/
	public void download(Formacao form) throws IOException {

		log.info(form.getId());

		if (form.getUrl() != null && !form.getUrl().isEmpty()) {

			String arquivoPath = form.getUrl();

			try {
				PdfUtil.downloadDesktop(arquivoPath);				
			}
			catch(Exception e) {
				MessageUtil.erro(e.getMessage());
			}	
		}
	}
	
	public void download2(Formacao form)  {

		log.info(form.getId());

		if (form.getUrl() != null && !form.getUrl().isEmpty()) {

			String arquivoPath = form.getUrl();

			try {
				PdfUtil.downloadStream(arquivoPath);				
			}			
			catch(Exception e) {
				MessageUtil.erro(e.getMessage());
			}		
		}
	}


	public String getNomeArquivo() {
		if (this.formacao.getUrl() != null)
			return "Já existe formação gravada. O upload de nova formação substituirá a anterior.";

		return "Nenhuma formação gravada ainda.";
	}

	/*
	 * public void handleFileUpload(FileUploadEvent event) {
	 * log.info("upload... = ");
	 * 
	 * this.originalImageFile = null;
	 * 
	 * UploadedFile file = event.getFile();
	 * 
	 * if (file != null && file.getContent() != null && file.getContent().length > 0
	 * && file.getFileName() != null) { this.originalImageFile = file;
	 * 
	 * this.formacao.setImagem(file.getContent());
	 * 
	 * MessageUtil.sucesso("Sucesso! " + this.originalImageFile.getFileName() +
	 * " foi enviado."); }
	 * 
	 * log.info("uploaded... = " + this.originalImageFile.getFileName()); }
	 * 
	 * public StreamedContent getImage() { log.info("getImageBd... = ");
	 * 
	 * StreamedContent file;
	 * 
	 * InputStream in = new ByteArrayInputStream(this.formacao.getImagem());
	 * 
	 * file = DefaultStreamedContent.builder() .stream(() -> in) .build();
	 * 
	 * return file; }
	 */

}
