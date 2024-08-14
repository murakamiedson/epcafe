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
import com.cafe.modelo.to.TotalDespesaTO;

import lombok.extern.log4j.Log4j;

@Log4j
public class RelatorioFertilizanteService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DespesaFertilizanteDAO despesaFertilizanteDAO;
	
	@Inject
	private RelatoriosUtilService relatorioUtil;

	
	public List<String> buscarAnosComRegistros(Unidade unidade) {
		
		List<String> anos  = despesaFertilizanteDAO.buscarAnosComRegistro(unidade);
		return anos;
	}
	
	public List<DespesaFertilizanteTO> buscarDespesasTO(LocalDate dataInicio, LocalDate dataFim, Unidade unidade){
		
		log.info("entrou no service de relatorio fertilizante");
		List<DespesaFertilizanteDTO> despesasDTO = despesaFertilizanteDAO.buscarDespesasDTO(dataInicio, dataFim, unidade);
		
		List<DespesaFertilizanteTO> despesasTO = new ArrayList<>();
		
		List<DespesaFertilizanteTO> auxiliar = new ArrayList<>();
		log.info("despesasDTO Size: " + despesasDTO.size());
		
		int cont = -1;
		for(int i = 0; i < despesasDTO.size(); i++) {
			
			DespesaFertilizanteDTO dto = despesasDTO.get(i);
			
			log.info("DTO:: " + i + "Talhao: " + dto.getNomeTalhao() + "Valor: " + dto.getValorDespesaFerTalhao());
			
			DespesaFertilizanteTO to = new DespesaFertilizanteTO();
			
			if (to.getTotaisMensais() == null) {
		        to.setTotaisMensais(new TotalDespesaTO());
		    }
			
			if(i==0 || (dto.getIdTalhao() != auxiliar.get(cont).getIdTalhao())) {
				cont++;
				
				to.setNomeTalhao(dto.getNomeTalhao());
				to.setIdTalhao(dto.getIdTalhao());
				
				this.relatorioUtil.verificaMesAno(dto.getData(), to.getTotaisMensais(), dto.getValorDespesaFerTalhao());
				
				auxiliar.add(to);
			} else {
				this.relatorioUtil.verificaMesAno(dto.getData(), auxiliar.get(cont).getTotaisMensais(), dto.getValorDespesaFerTalhao());
			}
			
			log.info("qde TO..." + auxiliar.size());
		}
		
		despesasTO = auxiliar;
		
		for (int i = 0; i < despesasTO.size(); i++) {
			this.relatorioUtil.calcValorAnual(despesasTO.get(i).getTotaisMensais());
		}
		
		return despesasTO;
	}
	
	
	
	
}
