package com.cafe.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.DespesaCusteioOutraDAO;
import com.cafe.modelo.DespesaCusteioOutra;
import com.cafe.modelo.Unidade;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

@Log4j
public class DespesaCusteioOutraService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DespesaCusteioOutraDAO despesaCusteioDAO;
	
	public DespesaCusteioOutra salvar(DespesaCusteioOutra despesaCusteioOutra) throws NegocioException {

		log.info("salvando outra despesa...");
		
		return this.despesaCusteioDAO.salvar(despesaCusteioOutra);
	}
	
	public void excluir(DespesaCusteioOutra despesaCusteioOutra) throws NegocioException {
		this.despesaCusteioDAO.excluir(despesaCusteioOutra);
	}	

	public DespesaCusteioOutra buscarPeloCodigo(long codigo) {
		return despesaCusteioDAO.buscarPeloCodigo(codigo);
	}

	public List<DespesaCusteioOutra> buscarDespesasOutrasDespesas(Unidade unidade) {

		log.info("Primeiro acesso a banco... buscar despesas com Fertilizantes");
		return despesaCusteioDAO.buscarDespesasOutrasDespesas(unidade);
	}
	
	public List<String> buscarAnosComRegistro(Unidade unidade){
		return this.despesaCusteioDAO.buscarAnosComRegistro(unidade);
	}
	
	public List<DespesaCusteioOutra> buscarOutrasDespesasPorAnoAgricola(
			LocalDate dataInicio, 
			LocalDate dataFim, 
			Unidade unidade
			){
		return this.despesaCusteioDAO.buscarOutrasDespesasPorAnoAgricola(dataInicio, dataFim, unidade);
	}

}
