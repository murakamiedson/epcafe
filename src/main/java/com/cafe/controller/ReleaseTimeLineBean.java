package com.cafe.controller;

import java.io.Serializable;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import com.cafe.util.MessageUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * @author murakamiadmin
 *
 */

@Getter
@Setter
@Named
@ViewScoped
public class ReleaseTimeLineBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private TimelineModel<String, ?> model;

    private boolean selectable = true;
    private boolean zoomable = true;
    private boolean moveable = true;
    private boolean stackEvents = true;
    private String eventStyle = "box";
    private boolean showCurrentTime = true;
    private boolean showNavigation = false;
    
    @PostConstruct
    protected void initialize() {
        model = new TimelineModel<>();
        model.add(TimelineEvent.<String>builder().data("v0.13.0")
        		.startDate(LocalDate.of(2024, 10, 03)).build());
        model.add(TimelineEvent.<String>builder().data("v0.12.1")
        		.startDate(LocalDate.of(2024, 8, 25)).build());
        model.add(TimelineEvent.<String>builder().data("v0.12.0")
        		.startDate(LocalDate.of(2024, 8, 8)).build());
        model.add(TimelineEvent.<String>builder().data("v0.11.0")
        		.startDate(LocalDate.of(2024, 7, 8)).build());
    }

    /* LAST VERSION */
    private String lastVersion = "v0.13.0";
    /* LAST VERSION */
    
    public void onSelect(TimelineSelectEvent<String> e) {
        TimelineEvent<String> timelineEvent = e.getTimelineEvent();
        String versao = timelineEvent.getData();
        
        switch(versao) {
        case "v0.13.0": 
	    	MessageUtil.sucesso("03/out/2024");
	    	MessageUtil.sucesso(
	    	"Adicionado o Lançar Outras Despesas de Custeio e Outras Despesas na mesma funcionalidade.");
	    	break;
        case "v0.12.1": 
	    	MessageUtil.sucesso("25/ago/2024");
	    	MessageUtil.sucesso(
	    	"Correção: download de arquivos");
	    	MessageUtil.sucesso(
	    	"Correção: aumento de casas decimais no cadastro de potência");
	    	break;
	    case "v0.12.0": 
	    	MessageUtil.sucesso("08/ago/2024");
	    	MessageUtil.sucesso(
	        "Correção: número da nota fiscal não pode ser repetido");
	        MessageUtil.sucesso(
	        "Inversão do botão Lancar NF com o Lancar Despesa Fertilizante");
	        MessageUtil.sucesso(
	    	"Melhoria das notas de release, agora com linha do tempo");
	        break;
	    case "v0.11.0": 
	    	MessageUtil.sucesso("08/jul/2024");
	    	MessageUtil.sucesso(
	        "Alteração no cadastro de fertilizantes");
	    	MessageUtil.sucesso(
	    	"Alteração no cadastro de despesas fertilizantes");
	        break;	    
	    default:
	    	MessageUtil.sucesso("Versão desconhecida");
	        break;
        } 
    }
}

/**
 * HISTÓRICO VS ANTERIORES
<b><h:outputText value="Versão 0.11.0 (08/07/2024)"/></b> <br/>
<h:outputText value="Alteração no cadastro de fertilizantes"/> <br/>
<h:outputText value="Alteração no cadastro de despesas fertilizantes"/> <br/>

<b><h:outputText value="Versão 0.10.0"/></b> <br/>
<h:outputText value="Upload/downaload de pdf/img de NF em Lançar Nota Fiscal"/> <br/>
<h:outputText value="Upload/downaload de pdf/img de Certificados em Cadastro de Funcionários"/> <br/>
<b><h:outputText value="Versão 0.9.0"/></b> <br/>
<h:outputText value="Upload de imagem de NF em Lançar Nota Fiscal"/> <br/>
<h:outputText value="Correções nos lançamentos de despesas, NF e cadastro de Unidade"/> <br/>
<h:outputText value="Ajustado para tratar ano agrícola de Ago a Jul"/> <br/>
<b><h:outputText value="Versão 0.8.1"/></b> <br/>
<h:outputText value="Auto Cadastro de Propriedade/Proprietário"/> <br/>
<h:outputText value="Criado campo tipo de cálculo no cadastro de máquinas"/> <br/>	
<b><h:outputText value="Versão 0.8.0"/></b> <br/>
<h:outputText value="Criação de Cadastro de Proprietários"/> <br/>
<h:outputText value="Lançamento de despesas por unidade"/> <br/>			
<b><h:outputText value="Versão 0.7.2"/></b> <br/>
<h:outputText value="Criação de Cadastro de Nota Fiscal"/> <br/>
<h:outputText value="Adequação do Lançar Despesas Fertilizantes"/> <br/>
<b><h:outputText value="Versão 0.7.1"/></b> <br/>
<h:outputText value="Ajustes em Despesas Fertilizantes e Nota Fiscal"/> <br/>
<b><h:outputText value="Versão 0.7.0"/></b> <br/>
<h:outputText value="Criação de usuário administrador do sistema èpCafé"/> <br/>
<h:outputText value="Funcionalidade para criar novos proprietários com multiplas Unidades"/> <br/>
<b><h:outputText value="Versão 0.6.0"/></b> <br/>
<h:outputText value="Reestruturação dos itens de menu."/> <br/>
<b><h:outputText value="Versão 0.5.0"/></b> <br/>
<h:outputText value="Inclusão do Lançar Despesas Fertilizantes e Defensivos."/> <br/>
<b><h:outputText value="Versão 0.4.0"/></b> <br/>
<h:outputText value="Inclusão do Relatório de Despesas Máquinas e Implementos."/> <br/>
<b><h:outputText value="Versão 0.3.0"/></b> <br/>
<h:outputText value="Inclusão do Lançar Despesas Máquinas e Implementos."/> <br/>
<b><h:outputText value="Versão 0.2.0"/></b> <br/>
<h:outputText value="Inclusão dos cadastro de funcionários."/> <br/>
<h:outputText value="Exclusão do cadastro de fabricantes."/> <br/>
<h:outputText value="Alteração dos tipos de máquinas e fertilizantes."/> <br/>
<b><h:outputText value="Versão 0.1.1"/></b> <br/>
<h:outputText value="Inclusão dos cadastros básicos."/> <br/>
<b><h:outputText value="Versão 0.1.0"/></b> <br/>
<h:outputText value="Criação do projeto èpCafé."/> <br/>
 */
