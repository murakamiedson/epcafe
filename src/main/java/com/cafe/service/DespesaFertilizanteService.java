package com.cafe.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.DespesaFertilizanteDAO;
import com.cafe.modelo.DespesaFerTalhao;
import com.cafe.modelo.DespesaFertilizante;
import com.cafe.modelo.Fertilizante;
import com.cafe.modelo.Item;
import com.cafe.modelo.NotaFiscal;
import com.cafe.modelo.Propriedade;
import com.cafe.modelo.Talhao;
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
	public DespesaFertilizante criarDistribuicao(DespesaFertilizante despesaFertilizante, Propriedade propriedade) {
		
		log.info("criar Distribuição de qdes por talhao ");
		
		List<Talhao> talhoesUnidade = talhaoService.buscarTalhoesPorUnidade(propriedade, propriedade.getTenant_id());
		
		// se não existir distribuicao buscar os talhoes e criar uma qdeTalhao para cada
		/*if(despesaFertilizante.getQdesTalhoes() == null && talhoesUnidade.size() > 0) {
						
			despesaFertilizante.setQdesTalhoes(new ArrayList<DespesaFerTalhao>());
			
			for(Talhao t : talhoesUnidade) {
				DespesaFerTalhao despesaFerTalhao = new DespesaFerTalhao();
				despesaFerTalhao.setDespesaFertilizante(despesaFertilizante);
				despesaFerTalhao.setTalhao(t);
				despesaFertilizante.getQdesTalhoes().add(despesaFerTalhao);
				log.info("quantidadeTalhao adicionado");
			}
			
			
			log.info("talhoes adicionados " + despesaFertilizante.getQdesTalhoes().size());
		}*/
		return despesaFertilizante;
	}

	public void excluir(DespesaFertilizante despesaFertilizante) throws NegocioException {
		despesaFertilizanteDAO.excluir(despesaFertilizante);
	}

	public DespesaFerTalhao salvarQuantidadeTalhao(DespesaFerTalhao quantidadeTahaoDespesa)
			throws NegocioException {
		return this.despesaFertilizanteDAO.salvarQuantidadeTalhao(quantidadeTahaoDespesa);
	}

	public void excluirQuantidadeTalhao(DespesaFerTalhao despesaFerTalhao) throws NegocioException {
		despesaFertilizanteDAO.excluirQuantidadeTalhao(despesaFerTalhao);
	}
	
	

	public DespesaFertilizante buscarPeloCodigo(long codigo) {
		return despesaFertilizanteDAO.buscarPeloCodigo(codigo);
	}

	public List<DespesaFertilizante> buscarDespesasFertilizantes(Long tenantId) {

		log.info("Primeiro acesso a banco... buscar despesas com Fertilizantes");
		return despesaFertilizanteDAO.buscarDespesasFertilizantes(tenantId);
	}
	
	public BigDecimal calcularValorTalhao(DespesaFerTalhao qdeTalhao) {
		
		BigDecimal valor = new BigDecimal(0);
		
		
		return valor;
	}
	
	public boolean validarNotaSelecionada(NotaFiscal nf, Fertilizante fertilizante) {
		for(Item item : nf.getItens()) {
			if(item.getFertilizante() == fertilizante) {
				return true;
			}
		}
		return false;
	}
	
	
	
	

}
