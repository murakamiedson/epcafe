package com.cafe.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.DespesaCusteioOutrasDAO;
import com.cafe.modelo.DespesaCusteioOutras;
import com.cafe.modelo.Unidade;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

@Log4j
public class DespesaCusteioOutrasService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DespesaCusteioOutrasDAO despesaCusteioDAO;
	
	public DespesaCusteioOutras salvar(DespesaCusteioOutras despesaCusteioOutras) throws NegocioException {

		log.info("salvando outra despesa...");
		
		return this.despesaCusteioDAO.salvar(despesaCusteioOutras);
	}
	
	public void excluir(DespesaCusteioOutras despesaCusteioOutras) throws NegocioException {
		this.despesaCusteioDAO.excluir(despesaCusteioOutras);
	}	

	public DespesaCusteioOutras buscarPeloCodigo(long codigo) {
		return despesaCusteioDAO.buscarPeloCodigo(codigo);
	}

	
	public List<DespesaCusteioOutras> buscarOutrasDespesas(Unidade unidade) {

		log.info("Primeiro acesso a banco... buscar outras despesas");
		return despesaCusteioDAO.buscarOutrasDespesas(unidade);
	}
	public List<DespesaCusteioOutras> buscarOutrasDespesaCusteio(Unidade unidade) {

		log.info("Primeiro acesso a banco... buscar despesas com Fertilizantes");
		return despesaCusteioDAO.buscarOutrasDespesasCusteio(unidade);
	}
	
	public List<String> buscarAnosComRegistro(Unidade unidade){
		return this.despesaCusteioDAO.buscarAnosComRegistro(unidade);
	}
	
	public List<DespesaCusteioOutras> buscarOutrasDespesasCusteitoPorAnoAgricola(
			LocalDate dataInicio, 
			LocalDate dataFim, 
			Unidade unidade
			){
		return this.despesaCusteioDAO.buscarOutrasDespesasCusteioPorAnoAgricola(dataInicio, dataFim, unidade);
	}
	public List<DespesaCusteioOutras> buscarOutrasDespesasPorAnoAgricola(
			LocalDate dataInicio, 
			LocalDate dataFim, 
			Unidade unidade
			){
		return this.despesaCusteioDAO.buscarOutrasDespesasPorAnoAgricola(dataInicio, dataFim, unidade);
	}

}
