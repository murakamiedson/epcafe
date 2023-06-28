package com.cafe.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.DespesaMaquinaDAO;
import com.cafe.modelo.DespesaMaquina;
import com.cafe.modelo.enums.TipoCombustivel;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j;


/**
 * @author isabella
 *
 */

@Log4j
public class DespesaMaquinaService implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Inject
	private DespesaMaquinaDAO despesaMaquinaDAO;
	
	
	public DespesaMaquina salvar(DespesaMaquina despesaMaquina) throws NegocioException {
		
		log.info("salvando despesa...");
		despesaMaquina.setValorTotal(calcularValorTotal(despesaMaquina));
		log.info("valor total = " + despesaMaquina.getValorTotal());
		return this.despesaMaquinaDAO.salvar(despesaMaquina);
	}
	
	public void excluir(DespesaMaquina despesaMaquina) throws NegocioException {
		despesaMaquinaDAO.excluir(despesaMaquina);		
	}
		
	public DespesaMaquina buscarPeloCodigo(long codigo) {
		return despesaMaquinaDAO.buscarPeloCodigo(codigo);
	}	
	
	public List<DespesaMaquina> buscarDespesasMaquinas(Long tenantId) {
		
		log.info("Primeiro acesso a banco... buscar maquinas");					
		return despesaMaquinaDAO.buscarDespesasMaquinas(tenantId);
	}
	
	
	
	public BigDecimal calcularValorTotal(DespesaMaquina despesaMaquina) {
		/*
	    Diesel 15%
	    Valor = Potencia (CV) x Fator de Potência x Consumo médio do combustível 
		Ex.:  134 * 0,55  * 0,15 = 11,055 l/h * preco combustivel

		Energia eletrica 73,5%
		Valor = Potencia * 0,735
		Ex.: 134 * 0,75 = 98,49 kwh * preco combustivel
	 */
		BigDecimal valor = new BigDecimal(0);
		if(despesaMaquina.getMaquina().getTipoCombustivel() == TipoCombustivel.DIESEL) {
			valor = despesaMaquina.getMaquina().getPotencia()
					.multiply(despesaMaquina.getFatorPotencia().getValor())
					.multiply(new BigDecimal(0.15))
					.multiply(despesaMaquina.getPrecoUnitarioCombustivel());
		} else if(despesaMaquina.getMaquina().getTipoCombustivel() == TipoCombustivel.ENERGIA_ELETRICA) {
			valor = despesaMaquina.getMaquina().getPotencia()
						.multiply(new BigDecimal(0.735))
						.multiply(despesaMaquina.getPrecoUnitarioCombustivel());
		}
		
		return valor;
	}
}
