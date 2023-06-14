package com.cafe.controller.despesa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.DespesaMaquina;
import com.cafe.modelo.PrecoCombustivel;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

/**
 * @author murakamiadmin
 *
 */
@Log
@Getter
@Setter
@Named
@ViewScoped
public class LancarDespesaMaquinaBean implements Serializable {

	private static final long serialVersionUID = 1L;
		
	private List<DespesaMaquina> despesas = new ArrayList<>();
	private List<PrecoCombustivel> precosCombustiveis = new ArrayList<>();
	private static long idTeste = 1;
	private DespesaMaquina despesaSelecionada;
	private PrecoCombustivel precoSelecionado;
	private int ano;
	
	@Inject
	private LoginBean loginBean;

	@PostConstruct
	public void inicializar() {
		ano = 2023;
	}	

    public void onAddNew() {
    	log.info("add");
    
    	DespesaMaquina novaDespesa = new DespesaMaquina();
    	novaDespesa.setId(idTeste++);
    	
        despesas.add(novaDespesa);

        FacesMessage msg = new FacesMessage("Despesa added", String.valueOf(novaDespesa.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }  
    public void onDelete() {
    	log.info("delete");   
    	
        despesas.remove(despesaSelecionada);

        FacesMessage msg = new FacesMessage("Despesa deleted", String.valueOf(despesaSelecionada.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    } 
	
	public void onRowEdit(RowEditEvent<DespesaMaquina> event) {		
		log.info("edit");
		
        FacesMessage msg = new FacesMessage("Despesa Edited", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<DespesaMaquina> event) {    	
    	log.info("cancel");
    	
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
    /* COMBUSTIVEIS */
    
    public void onAddNewCombustivel() {
    	log.info("add");
    
    	PrecoCombustivel precoCombustivel = new PrecoCombustivel();
    	precoCombustivel.setId(idTeste++);
    	
    	precosCombustiveis.add(precoCombustivel);

        FacesMessage msg = new FacesMessage("Preço added", String.valueOf(precoCombustivel.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    } 
    
    public void onDeleteCombustivel() {
    	log.info("delete");   
    	
    	precosCombustiveis.remove(precoSelecionado);

        FacesMessage msg = new FacesMessage("Despesa deleted", String.valueOf(precoSelecionado.getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onRowEditCombustivel(RowEditEvent<PrecoCombustivel> event) {		
		log.info("edit");
		
        FacesMessage msg = new FacesMessage("Preço Edited", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancelCombustivel(RowEditEvent<PrecoCombustivel> event) {    	
    	log.info("cancel");
    	
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}