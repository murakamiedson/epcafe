package com.cafe.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import com.cafe.modelo.Formacao;
import com.cafe.modelo.Funcionario;
import com.cafe.modelo.enums.NivelEscolaridade;
import com.cafe.service.FormacaoService;
import com.cafe.service.FuncionarioService;
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
@ViewScoped
public class CadastroFormacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Formacao formacao;
	
	private List<NivelEscolaridade> niveisEscolaridade;
	private List<Funcionario> funcionarios;
	private List<Formacao> formacoes;
	
	//private PesquisaFuncionarioBean funcionarioSelecionado;
	private Funcionario funcionarioSelecionado;
	private Long tenantId;	
	
	@Inject
	private FuncionarioService funcionarioService;
	
	@Inject
	private FormacaoService formacaoService;
	
	@Inject
	private LoginBean usuarioLogado;	
	
	@PostConstruct
	public void inicializar() {
		tenantId = usuarioLogado.getUsuario().getTenant().getCodigo();
		log.info("Bean : tenant = " + tenantId + "-" + usuarioLogado.getUsuario().getTenant().getTenant());
		
		this.niveisEscolaridade = Arrays.asList(NivelEscolaridade.values());
		
		this.limpar();
	}
		
	
	public void salvar() {
		try {
			//Funcionario funcionario = funcionarioService.buscarPeloCodigo(funcionarioSelecionado.getId());
	        formacao.setFuncionario(funcionarioSelecionado);
			this.formacaoService.salvar(formacao);
			MessageUtil.sucesso("Formação salva com sucesso!");
		} catch (NegocioException e) {
			e.printStackTrace();
			MessageUtil.erro(e.getMessage());
		}		
		this.limpar();
	}
	
	public void limpar() {
		this.formacao = new Formacao();
		//this.formacao.setFuncionario();
		//this.formacao.setFuncionario(funcionarioSelecionado.getFuncionarioSelecionado());
		this.formacao.setTenant_id(tenantId);
		//this.formacao.getFuncionario().getId();
	}
	/*
	public void setFuncionario(Funcionario funcionarioSelecionado) {
		this.formacao.setFuncionario(funcionarioSelecionado);
	}*/
	
	public void gravarPdf(FileUploadEvent evento) {
		UploadedFile uploadedFile = evento.getFile();
        String fileName = uploadedFile.getFileName();

        // Diretório onde o arquivo será armazenado
        String directory = "/webapp/restricted/files";
        
        try {
            // Cria o arquivo no diretório
            File file = new File(directory + fileName);
            OutputStream output = new FileOutputStream(file);

            // Copia o conteúdo do arquivo para o diretório
            InputStream input = uploadedFile.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            // Fecha os streams
            input.close();
            output.close();

            // Exibe uma mensagem de sucesso
            FacesMessage message = new FacesMessage("Sucesso", "Arquivo '" + fileName + "' foi enviado com sucesso!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException e) {
            // Exibe uma mensagem de erro
            FacesMessage message = new FacesMessage("Erro", "Ocorreu um erro durante o envio do arquivo.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
	}
	
}
