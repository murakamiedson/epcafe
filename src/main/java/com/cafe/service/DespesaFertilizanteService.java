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
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author isabella
 *
 */

@Log4j
public class DespesaFertilizanteService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal quantidadeFertilizanteNF;
	
	
	@Inject
	private DespesaFertilizanteDAO despesaFertilizanteDAO;

	public DespesaFertilizante salvar(DespesaFertilizante despesaFertilizante) throws NegocioException {

		log.info("salvando despesa...");
		return this.despesaFertilizanteDAO.salvar(despesaFertilizante);
	}
	

	public void excluir(DespesaFertilizante despesaFertilizante) throws NegocioException {
		despesaFertilizanteDAO.excluir(despesaFertilizante);
	}	

	public DespesaFertilizante buscarPeloCodigo(long codigo) {
		return despesaFertilizanteDAO.buscarPeloCodigo(codigo);
	}

	public List<DespesaFertilizante> buscarDespesasFertilizantes(Long tenantId) {

		log.info("Primeiro acesso a banco... buscar despesas com Fertilizantes");
		return despesaFertilizanteDAO.buscarDespesasFertilizantes(tenantId);
	}
	
	//verifica se a notafiscal selecionada contém o fertilizante selecionado
	public boolean validarNotaSelecionada(NotaFiscal nf, Fertilizante fertilizante) {
		for(Item item : nf.getItens()) {
			if(item.getFertilizante() == fertilizante) {
				return true;
			}
		}
		return false;
	}
	
	public boolean validarQuantidadesTalhoesComNF(DespesaFertilizante despesaFertilizante) {
		//BigDecimal total = despesaFertilizante.getNotaFiscal().getItens()
		log.info("entrou no metodo de validação");
		BigDecimal total = new BigDecimal(0);
		BigDecimal totalFerNF = new BigDecimal(0);
		for(DespesaFerTalhao qdeTalhao : despesaFertilizante.getDespesasTalhoes()) {
			total = total.add(qdeTalhao.getQuantidade());
			log.info("TOTAL: " + total);
		}
		for(Item item : despesaFertilizante.getNotaFiscal().getItens()) {
			if(item.getFertilizante() == despesaFertilizante.getFertilizante()) {
				totalFerNF = item.getQuantidade();
			}
		}
		if(total.compareTo(totalFerNF) == 1) {
			log.info("Quantidade informada ultrapassa nota fiscal");
			return false;
		}
		return true;
	}
	
	//calcula o valor equivalente gasto com cada talhao da despesa
	public void calculaValorPorTalhao(DespesaFertilizante despesaFertilizante) {
		BigDecimal valorFertilizanteNF = new BigDecimal(0);
		
		for(Item itens : despesaFertilizante.getNotaFiscal().getItens()) {
			if(itens.getFertilizante() == despesaFertilizante.getFertilizante()) {
				valorFertilizanteNF = itens.getValor();
				this.quantidadeFertilizanteNF = itens.getQuantidade();
				log.info("Quantidade fertilizante nota fiscal: " + this.quantidadeFertilizanteNF);
			}
		}
		
		for(DespesaFerTalhao qdeTalhao : despesaFertilizante.getDespesasTalhoes()) {
			qdeTalhao.setValor(valorFertilizanteNF.multiply(qdeTalhao.getQuantidade()));
			
		}
		
	}
	
	
	
	

}
