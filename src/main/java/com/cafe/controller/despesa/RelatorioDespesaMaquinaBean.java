package com.cafe.controller.despesa;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.cafe.controller.LoginBean;
import com.cafe.modelo.DespesaMaquina;
import com.cafe.modelo.to.DespesaTO;
import com.cafe.service.DespesaMaquinaService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class RelatorioDespesaMaquinaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDate mesAno;
	private DespesaMaquina despesaMaquina;
	private List<DespesaTO> despesasTO = new ArrayList<>();
		
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private DespesaMaquinaService despesaService;

	@PostConstruct
	public void inicializar() {
	
		log.info("inicializar login = " + loginBean.getUsuario());
		mesAno = LocalDate.now();		
		despesasTO = despesaService.buscarDespesasTO(mesAno, loginBean.getTenantId());
		
		
		log.info("finalizar...");
	}
	
	
	
}