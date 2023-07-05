package com.cafe.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.DespesaMaquinaDAO;
import com.cafe.modelo.DespesaMaquina;
import com.cafe.modelo.enums.TipoCombustivel;
import com.cafe.modelo.to.DespesaDTO;
import com.cafe.modelo.to.DespesaTO;
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
	
	private BigDecimal calcularValorTotal(DespesaMaquina despesaMaquina) {
		/*
	    Diesel 15%
	    Valor = Potencia (CV) x Fator de Potência x Consumo médio do combustível 
		Ex.:  134 * 0,55  * 0,15 * 5,00 * 10 = 11,055 l/h * preco combustivel

		Energia eletrica 73,5%
		Valor = Potencia * 0,735
		Ex.: 134 * 0,735 * 0,35 * 10 = 98,49 kwh * preco combustivel
	 */
		BigDecimal valor = new BigDecimal(0);
		if(despesaMaquina.getMaquina().getTipoCombustivel() == TipoCombustivel.DIESEL) {
			valor = despesaMaquina.getMaquina().getPotencia()
					.multiply(despesaMaquina.getFatorPotencia().getValor().multiply(new BigDecimal(0.01)))
					.multiply(new BigDecimal(0.15))
					.multiply(despesaMaquina.getPrecoUnitarioCombustivel())
					.multiply(despesaMaquina.getHorasTrabalhadas());
		} else if(despesaMaquina.getMaquina().getTipoCombustivel() == TipoCombustivel.ENERGIA_ELETRICA) {
			valor = despesaMaquina.getMaquina().getPotencia()
						.multiply(new BigDecimal(0.735))
						.multiply(despesaMaquina.getPrecoUnitarioCombustivel())
						.multiply(despesaMaquina.getHorasTrabalhadas());
		}
		
		return valor;
	}
	
	

	/* 
	 * RelatorioDespesaMaquina
	 */
	
	
	public List<DespesaTO> buscarDespesasTO(LocalDate mesAno, Long tenantId){		
		
		log.info("montando TO...");
		List<DespesaDTO> despesasDTO = despesaMaquinaDAO.buscarDespesasDTO(mesAno, tenantId);
		List<DespesaTO> despesasTO = new ArrayList<>();
		
		//TODO criar a logica para criar os TOs a partir dos DTOs
		// usado só para teste
		
		BigDecimal totalMaquina = new BigDecimal(0);		
		BigDecimal totalDespesa = new BigDecimal(0);

		DespesaDTO dto = despesasDTO.get(0);
		totalDespesa = totalDespesa.add(dto.getValorTotal());
		
		DespesaTO to = new DespesaTO();
		to.setMaquinaId(dto.getMaquinaId());
		to.setMaquinaNome(dto.getMaquinaNome());
		totalMaquina = totalMaquina.add(totalDespesa);		
		//TODO tem que identificar o mes para atribuir
		to.setValorTotalJan(totalDespesa);
		
		despesasTO.add(to);

		
		log.info("qde TO..." + despesasTO.size());
		
		return despesasTO;
	}
	
}
