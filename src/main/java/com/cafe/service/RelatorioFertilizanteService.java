package com.cafe.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.DespesaFertilizanteDAO;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.to.DespesaFertilizanteDTO;
import com.cafe.modelo.to.DespesaFertilizanteTO;

import lombok.extern.log4j.Log4j;

@Log4j
public class RelatorioFertilizanteService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DespesaFertilizanteDAO despesaFertilizanteDAO;
	
	public List<String> buscarAnosComRegistros(Unidade unidade) {
		
		List<String> anos  = despesaFertilizanteDAO.buscarAnosComRegistro(unidade);
		return anos;
	}
	
	public List<DespesaFertilizanteTO> buscarDespesasTO(LocalDate dataInicio, LocalDate dataFim, Unidade unidade){
		
		log.info("entrou no service de relatorio fertilizante");
		List<DespesaFertilizanteDTO> despesasDTO = despesaFertilizanteDAO.buscarDespesasDTO(dataInicio, dataFim, unidade);
		
		List<DespesaFertilizanteTO> despesasTO = new ArrayList<>();
		log.info("despesasDTO Size: " + despesasDTO.size());
		for(int i = 0; i < despesasDTO.size(); i++) {
			
			DespesaFertilizanteDTO dto = despesasDTO.get(i);
			
			log.info("DTO:: " + i + "Talhao: " + dto.getNomeTalhao() + "Valor: " + dto.getValorDespesaFerTalhao());
		}
		
		
		
		return despesasTO;
	}
}
