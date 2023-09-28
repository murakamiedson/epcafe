package com.cafe.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.DespesaFertilizanteDAO;
import com.cafe.modelo.DespesaFertilizante;
import com.cafe.modelo.QuantidadeTalhao;
import com.cafe.modelo.Talhao;
import com.cafe.modelo.Unidade;
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
	private TalhaoService talhaoService;
	@Inject
	private DespesaFertilizanteDAO despesaFertilizanteDAO;

	public DespesaFertilizante salvar(DespesaFertilizante despesaFertilizante) throws NegocioException {

		log.info("salvando despesa...");
		return this.despesaFertilizanteDAO.salvar(despesaFertilizante);
	}
	public DespesaFertilizante criarDistribuicao(DespesaFertilizante despesaFertilizante, Unidade unidade) {
		
		log.info("criar Distribuição de qdes por talhao ");
		
		List<Talhao> talhoesUnidade = talhaoService.buscarTalhoesPorUnidade(unidade, unidade.getTenant_id());
		
		// se não existir distribuicao buscar os talhoes e criar uma qdeTalhao para cada
		if(despesaFertilizante.getQdesTalhoes() == null && talhoesUnidade.size() > 0) {
						
			despesaFertilizante.setQdesTalhoes(new ArrayList<QuantidadeTalhao>());
			
			for(Talhao t : talhoesUnidade) {
				QuantidadeTalhao quantidadeTalhao = new QuantidadeTalhao();
				quantidadeTalhao.setDespesaFertilizante(despesaFertilizante);
				quantidadeTalhao.setTalhao(t);
				despesaFertilizante.getQdesTalhoes().add(quantidadeTalhao);
				log.info("quantidadeTalhao adicionado");
			}
			
			
			log.info("talhoes adicionados " + despesaFertilizante.getQdesTalhoes().size());
		}
		return despesaFertilizante;
	}

	public void excluir(DespesaFertilizante despesaFertilizante) throws NegocioException {
		despesaFertilizanteDAO.excluir(despesaFertilizante);
	}

	public QuantidadeTalhao salvarQuantidadeTalhao(QuantidadeTalhao quantidadeTahaoDespesa)
			throws NegocioException {
		return this.despesaFertilizanteDAO.salvarQuantidadeTalhao(quantidadeTahaoDespesa);
	}

	public void excluirQuantidadeTalhao(QuantidadeTalhao quantidadeTalhao) throws NegocioException {
		despesaFertilizanteDAO.excluirQuantidadeTalhao(quantidadeTalhao);
	}

	public DespesaFertilizante buscarPeloCodigo(long codigo) {
		return despesaFertilizanteDAO.buscarPeloCodigo(codigo);
	}

	public List<DespesaFertilizante> buscarDespesasFertilizantes(Long tenantId) {

		log.info("Primeiro acesso a banco... buscar despesas com Fertilizantes");
		return despesaFertilizanteDAO.buscarDespesasFertilizantes(tenantId);
	}

}
