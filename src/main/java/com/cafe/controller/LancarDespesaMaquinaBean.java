package com.cafe.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.cafe.modelo.DespesaMaquina;

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
public class LancarDespesaMaquinaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	/*
	private List<DespesaCusteio> despesas = new ArrayList<>();
	private List<DespesaCusteio> despesasSelecionadas = new ArrayList<>();
	private DespesaCusteio despesaSelecionada;*/
	
	private List<DespesaMaquina> despesas = new ArrayList<>();
	private List<DespesaMaquina> despesasSelecionadas = new ArrayList<>();
	private DespesaMaquina despesaSelecionada;

	@Inject
	private LoginBean loginBean;

	@PostConstruct
	public void inicializar() {

	}

	public boolean hasDespesasSelecionadas() {
		return this.despesasSelecionadas != null && !this.despesasSelecionadas.isEmpty();
	}
	
	public void openNew() {
        this.despesaSelecionada = new DespesaMaquina();
    }
	
	public void saveDespesa() {
        if (this.despesaSelecionada.getId() == null) {            
            this.despesas.add(this.despesaSelecionada);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Despesa adicionada"));
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Despesa Atualizada"));
        }

        PrimeFaces.current().executeScript("PF('despesaDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:custeioTable");
    }
	
	public void deleteDespesa() {
        this.despesas.remove(this.despesaSelecionada);
        this.despesasSelecionadas.remove(this.despesaSelecionada);
        this.despesaSelecionada = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Despesa excluída"));
        PrimeFaces.current().ajax().update("form:messages", "form:custeioTable");
    }
	
	public void deleteDespesasSelecionadas() {
        this.despesas.removeAll(this.despesasSelecionadas);
        this.despesasSelecionadas = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Despesas removidas"));
        PrimeFaces.current().ajax().update("form:messages", "form:custeioTable");
        PrimeFaces.current().executeScript("PF('despesaDialog').clearFilters()");
    }
	
	public String getDeleteButtonMessage() {
        if (hasDespesasSelecionadas()) {
            int size = this.despesasSelecionadas.size();
            return size > 1 ? size + " despesas selecionadas" : "1 despesa selecionada";
        }

        return "Delete";
    }

}