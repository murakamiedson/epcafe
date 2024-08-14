package com.cafe.service;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.DespesaMaquinaDAO;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.to.DespesaMaquinaDTO;
import com.cafe.modelo.to.DespesaMaquinaTO;
import com.cafe.modelo.to.TotalDespesaTO;

import lombok.extern.log4j.Log4j;

@Log4j
public class RelatorioMaquinaService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private DespesaMaquinaDAO despesaMaquinaDAO;
	
	@Inject
	private RelatoriosUtilService relatorioUtil;
	
	public List<String> buscarAnosComRegistros(Unidade unidade) {
		
		List<String> anos  = despesaMaquinaDAO.buscarAnosComRegistro(unidade);
		return anos;
	}

	public List<DespesaMaquinaTO> buscarDespesasTO(LocalDate dataInicio, LocalDate dataFim, Unidade unidade) {

		log.info("montando TO...");
		List<DespesaMaquinaDTO> despesasDTO = despesaMaquinaDAO.buscarDespesasDTO(dataInicio, dataFim, unidade);

		List<DespesaMaquinaTO> despesasTO = new ArrayList<>();

		List<DespesaMaquinaTO> auxiliar = new ArrayList<>();

		int cont = -1;

		for (int i = 0; i < despesasDTO.size(); i++) {

			DespesaMaquinaDTO dto = despesasDTO.get(i);

			log.info("DTO" + i + ": mesAno: " + dto.getMesAno() + " valortotal: " + dto.getValorTotal() + " maquinaID: "
					+ dto.getMaquinaId() + " maquinaNome: " + dto.getMaquinaNome() + " tipoCombustivel: "
					+ dto.getTipoCombustivel());

			DespesaMaquinaTO to = new DespesaMaquinaTO();
			
			if (to.getTotaisMensais() == null) {
		        to.setTotaisMensais(new TotalDespesaTO());
		    }

			if (i == 0 || (dto.getMaquinaId() != auxiliar.get(cont).getMaquinaId())) {
				cont++;

				to.setMaquinaId(dto.getMaquinaId());
				to.setMaquinaNome(dto.getMaquinaNome());

				this.relatorioUtil.verificaMesAno(dto.getMesAno(), to.getTotaisMensais(), dto.getValorTotal());

				auxiliar.add(to);

				log.info("IF: maquinaID: " + to.getMaquinaId() + " cont: " + cont + "despesasTOcont: "
						+ auxiliar.get(cont).getMaquinaId());

			} else {

				this.relatorioUtil.verificaMesAno(dto.getMesAno(), auxiliar.get(cont).getTotaisMensais(), dto.getValorTotal());

				log.info("ELSE: maquinaID: " + to.getMaquinaId() + " cont: " + cont + "despesasTOcont: "
						+ auxiliar.get(cont).getMaquinaId());
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
