package com.cafe.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.DespesaFertilizanteDAO;
import com.cafe.modelo.DespesaFerTalhao;
import com.cafe.modelo.DespesaFertilizante;
import com.cafe.modelo.Fertilizante;
import com.cafe.modelo.Item;
import com.cafe.modelo.NotaFiscal;
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
	private DespesaFertilizanteDAO despesaFertilizanteDAO;

	public DespesaFertilizante salvar(DespesaFertilizante despesaFertilizante) throws NegocioException {

		log.info("salvando despesa...");
		despesaFertilizante.setValorTotal(this.calculaValorTotal(despesaFertilizante));
		log.info("VALOR TOTAL DESPESA:: " + despesaFertilizante.getValorTotal());
		despesaFertilizante.setMedida(this.getItemDaDespesa(despesaFertilizante).getMedida());
		return this.despesaFertilizanteDAO.salvar(despesaFertilizante);
	}
	

	public void excluir(DespesaFertilizante despesaFertilizante) throws NegocioException {
		despesaFertilizanteDAO.excluir(despesaFertilizante);
	}	

	public DespesaFertilizante buscarPeloCodigo(long codigo) {
		return despesaFertilizanteDAO.buscarPeloCodigo(codigo);
	}

	public List<DespesaFertilizante> buscarDespesasFertilizantes(Unidade unidade) {

		log.info("Primeiro acesso a banco... buscar despesas com Fertilizantes");
		return despesaFertilizanteDAO.buscarDespesasFertilizantes(unidade);
	}
	
	public List<DespesaFertilizante> buscarDespesasFertilizantesPorAno(
			LocalDate dataInicio, LocalDate dataFim, Unidade unidade){
		return despesaFertilizanteDAO.buscarDespesasFertilizantesPorAno(dataInicio, dataFim, unidade);
	}
	
	public List<String> buscarAnosComRegistros(Unidade unidade) {
		
		List<String> anos  = despesaFertilizanteDAO.buscarAnosComRegistro(unidade);
		return anos;
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
		log.info("entrou no metodo de validação");
		BigDecimal total = new BigDecimal(0);
		BigDecimal totalFerNF = new BigDecimal(0);
		for(DespesaFerTalhao qdeTalhao : despesaFertilizante.getDespesasTalhoes()) {
			total = total.add(qdeTalhao.getQuantidade());
			
		}

		totalFerNF = this.getItemDaDespesa(despesaFertilizante).getQuantidade();
		log.info("TOTAL FER NF: " + totalFerNF);
		if(total.compareTo(totalFerNF) == 1) {
			log.info("Quantidade informada ultrapassa nota fiscal");
			return false;
		}
		return true;
	}
	
	public Item getItemDaDespesa(DespesaFertilizante despesaFertilizante) {
		for(Item i : despesaFertilizante.getNotaFiscal().getItens()) {
			if(i.getFertilizante() == despesaFertilizante.getFertilizante()) {
				return i;
			}
		}
		return null;
	}
	
	//calcula o valor equivalente gasto com cada talhao da despesa
	public void calculaValorPorTalhao(DespesaFertilizante despesaFertilizante) {
		BigDecimal valorFertilizanteNF = new BigDecimal(0);

		
		valorFertilizanteNF = this.getItemDaDespesa(despesaFertilizante).getValor();
		
		for(DespesaFerTalhao qdeTalhao : despesaFertilizante.getDespesasTalhoes()) {
			qdeTalhao.setValor(valorFertilizanteNF.multiply(qdeTalhao.getQuantidade()));
			
		}
		
	}
	
	public BigDecimal calculaValorTotal(DespesaFertilizante despesaFertilizante) {
		BigDecimal total = new BigDecimal(0);
		for(DespesaFerTalhao qdeTalhao : despesaFertilizante.getDespesasTalhoes()) {
			log.info(qdeTalhao.getValor());
			total = total.add(qdeTalhao.getValor());
		}
		log.info("TOTAL DA DESPESA NO METODO:: " + total);
		return total;
	}

}
