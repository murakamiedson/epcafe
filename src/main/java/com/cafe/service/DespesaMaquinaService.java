package com.cafe.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.DespesaMaquinaDAO;
import com.cafe.modelo.DespesaMaquina;
import com.cafe.modelo.Unidade;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;

/**
 * @author isabella
 *
 */

@Log4j
public class DespesaMaquinaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DespesaMaquinaDAO despesaMaquinaDAO;

	public DespesaMaquina salvar(DespesaMaquina despesaMaquina) throws NegocioException {

		log.info("salvando despesa...");
		despesaMaquina.setValorTotal(this.calcularValorTotal(despesaMaquina));
		log.info("valor total = " + despesaMaquina.getValorTotal());
		return this.despesaMaquinaDAO.salvar(despesaMaquina);
	}

	public void excluir(DespesaMaquina despesaMaquina) throws NegocioException {
		despesaMaquinaDAO.excluir(despesaMaquina);
	}

	public DespesaMaquina buscarPeloCodigo(long codigo) {
		return despesaMaquinaDAO.buscarPeloCodigo(codigo);
	}

	public List<DespesaMaquina> buscarDespesasMaquinas(Unidade unidade) {

		log.info("Primeiro acesso a banco... buscar maquinas");
		return despesaMaquinaDAO.buscarDespesasMaquinas(unidade);
	}
	
	public List<DespesaMaquina> buscarDespesasMaquinasPorAno(LocalDate dataInicio, LocalDate dataFim, Unidade unidade){
		return despesaMaquinaDAO.buscarDespesasMaquinasPorAno(dataInicio, dataFim, unidade);
	}
	

	private BigDecimal calcularValorTotal(DespesaMaquina despesaMaquina) {

		BigDecimal valor = new BigDecimal(0);
		var tipoCalculo = despesaMaquina.getMaquina().getTipoCalculo();
		BigDecimal tempoConvertido = despesaMaquina.isUnidadeHoras() 
			    ? this.converterHoraParaMinuto(despesaMaquina) 
			    : despesaMaquina.getTempoTrabalhado();

		switch (tipoCalculo) {
		case TRATOR:
			valor = despesaMaquina.getMaquina().getPotencia()
					.multiply(despesaMaquina.getFatorPotencia().getValor()
							.divide(new BigDecimal(100)))
					.multiply(new BigDecimal(0.15))
					.multiply(despesaMaquina.getPrecoUnitarioCombustivel())
					.multiply(tempoConvertido
							.divide(new BigDecimal(60), RoundingMode.DOWN));
			break;
			
		case NAO_TRATOR:
			// MAQUINAS QUE NAO SAO TRATOR
			// Potencia * 0,15 * Preco Combustivel * Tempo Trabalhado
			valor = despesaMaquina.getMaquina().getPotencia()
					.multiply(new BigDecimal(0.15))
					.multiply(despesaMaquina.getPrecoUnitarioCombustivel())
					.multiply(tempoConvertido
							.divide(new BigDecimal(60), RoundingMode.DOWN));
			
			break;
			
		case ENERGIA_ELETRICA: 
			// Potencia * 0,735 * Preco Energia * Tempo Trabalhado
			valor = despesaMaquina.getMaquina().getPotencia()
					.multiply(new BigDecimal(0.735))
					.multiply(despesaMaquina.getPrecoUnitarioCombustivel())
					.multiply(tempoConvertido
							.divide(new BigDecimal(60), RoundingMode.DOWN));
			break;
			
		case DISTANCIA:
			
			// Litros Consumidos * ( Distancia Trabalhada / Consumo Medio ) * Preco
			// Combustivel
			valor = despesaMaquina.getDistanciaTrabalhada()
					.divide(despesaMaquina.getMaquina().getConsumoMedio(), RoundingMode.DOWN)
					.multiply(despesaMaquina.getPrecoUnitarioCombustivel());

			break;
		case NENHUM:
			// Não altera valor
		}
		return valor;

	}
	
	private BigDecimal converterHoraParaMinuto(DespesaMaquina despesaMaquina) {
			return despesaMaquina.getTempoTrabalhado().multiply(new BigDecimal(60));
	}

}
