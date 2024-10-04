package com.cafe.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.DespesaCusteioOutrasDAO;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.to.OutrasDespesasDTO;
import com.cafe.modelo.to.OutrasDespesasTO;
import com.cafe.modelo.to.TotalDespesaTO;

import lombok.extern.log4j.Log4j;

@Log4j
public class RelatorioOutrasDespesasService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private RelatoriosUtilService relatorioUtil;
	
	@Inject
	private DespesaCusteioOutrasDAO outrasDespesasDAO;
	
	public List<String> buscarAnosComRegistros(Unidade unidade) {
		
		List<String> anos  = this.outrasDespesasDAO.buscarAnosComRegistro(unidade);
		return anos;
	}
	
	public List<OutrasDespesasTO> buscarDespesasTO(LocalDate dataInicio, LocalDate dataFim, Unidade unidade){
		
		log.info("entrou no service de relatorio outras despesas");
		
		List<OutrasDespesasDTO> despesasDTO = this.outrasDespesasDAO.buscarDespesasDTO(dataInicio, dataFim, unidade);
		
		List<OutrasDespesasTO> despesasTO = new ArrayList<>();
		List<OutrasDespesasTO> auxiliar = new ArrayList<>();
		log.info("despesasDTO size: " + despesasDTO.size());
		
		
		int cont = -1;
		for(int i = 0; i < despesasDTO.size(); i++) {
			
			OutrasDespesasDTO dto = despesasDTO.get(i);
			
			log.info("Despesa dto: DATA - " + dto.getData() + "VALOR - " + dto.getValorDespesa() + "TIPO - " + dto.getTipo());
			
			OutrasDespesasTO to = new OutrasDespesasTO();
			
			if (to.getTotaisMensais() == null) {
		        to.setTotaisMensais(new TotalDespesaTO());
		    }
			
			if (i == 0 || (dto.getTipo() != auxiliar.get(cont).getTipo())) {
				cont++;
				
				to.setTipo(dto.getTipo());
				
				this.relatorioUtil.verificaMesAno(dto.getData(), to.getTotaisMensais(), dto.getValorDespesa());
				
				auxiliar.add(to);
				
				
				
			} else {
				
				this.relatorioUtil.verificaMesAno(dto.getData(), auxiliar.get(cont).getTotaisMensais(), dto.getValorDespesa());
				
				
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
