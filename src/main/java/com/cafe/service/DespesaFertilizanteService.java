package com.cafe.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.DespesaFertilizanteDAO;
import com.cafe.modelo.DespesaFertilizante;
import com.cafe.modelo.QuantidadeTalhaoDespesa;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author isabella
 *
 */

@Log4j
public class DespesaFertilizanteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DespesaFertilizanteDAO despesaFertilizanteDAO;

	public DespesaFertilizante salvar(DespesaFertilizante despesaFertilizante) throws NegocioException {

		log.info("salvando despesa...");
		return this.despesaFertilizanteDAO.salvar(despesaFertilizante);
	}

	public void excluir(DespesaFertilizante despesaFertilizante) throws NegocioException {
		despesaFertilizanteDAO.excluir(despesaFertilizante);
	}

	public QuantidadeTalhaoDespesa salvarQuantidadeTalhao(QuantidadeTalhaoDespesa quantidadeTahaoDespesa)
			throws NegocioException {
		return this.despesaFertilizanteDAO.salvarQuantidadeTalhao(quantidadeTahaoDespesa);
	}

	public void excluirQuantidadeTalhao(QuantidadeTalhaoDespesa quantidadeTalhaoDespesa) throws NegocioException {
		despesaFertilizanteDAO.excluirQuantidadeTalhao(quantidadeTalhaoDespesa);
	}

	public DespesaFertilizante buscarPeloCodigo(long codigo) {
		return despesaFertilizanteDAO.buscarPeloCodigo(codigo);
	}

	public List<DespesaFertilizante> buscarDespesasFertilizantes(Long tenantId) {

		log.info("Primeiro acesso a banco... buscar despesas com Fertilizantes");
		return despesaFertilizanteDAO.buscarDespesasFertilizantes(tenantId);
	}

}
