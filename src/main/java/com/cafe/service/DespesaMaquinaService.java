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
					.multiply(despesaMaquina.getFatorPotencia().getValor().divide(new BigDecimal(100)))
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
		
		List<DespesaTO> auxiliar = new ArrayList<>();
		
		
		int cont=-1;
		
		for(int i=0; i<despesasDTO.size(); i++) {
				
			
			DespesaDTO dto = despesasDTO.get(i);

			log.info("DTO"+i+": mesAno: "+dto.getMesAno()+" valortotal: "+dto.getValorTotal()+" maquinaID: "+
						dto.getMaquinaId()+" maquinaNome: "+dto.getMaquinaNome()+" tipoCombustivel: "+
						dto.getTipoCombustivel());
			

			DespesaTO to = new DespesaTO();
			

			if(i==0 || (dto.getMaquinaId() != auxiliar.get(cont).getMaquinaId())) {
				cont++;
				
				to.setMaquinaId(dto.getMaquinaId());
				to.setMaquinaNome(dto.getMaquinaNome());				
				
				verificaMesAno(dto.getMesAno(), to, dto.getValorTotal());
				
				
				
				auxiliar.add(to);
				
				log.info("IF: maquinaID: "+to.getMaquinaId()+" cont: "+cont+"despesasTOcont: "
						+auxiliar.get(cont).getMaquinaId());
				
			}else {
				
								
				verificaMesAno(dto.getMesAno(), auxiliar.get(cont), dto.getValorTotal());
				

				log.info("ELSE: maquinaID: "+to.getMaquinaId()+" cont: "+cont+"despesasTOcont: "+auxiliar.get(cont).getMaquinaId());
			}
			
			
			log.info("qde TO..." + auxiliar.size());
			
		}
		
		despesasTO = auxiliar;
		
		for(int i=0; i<despesasTO.size(); i++) {
			calcValorAnual(despesasTO.get(i));
		}
		
		return despesasTO;
	}

	
	public void verificaMesAno(LocalDate mesAno, DespesaTO to, BigDecimal valorDespesa) {
		
		int data = mesAno.getMonthValue();
		
		switch(data) {
		
			case 1:
				
				if(to.getValorTotalJan()== new BigDecimal(0)) {
					
					to.setValorTotalJan(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalJan();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalJan(novoTotal);
				}
				
				break;
			
			case 2:
				
				if(to.getValorTotalFev()== new BigDecimal(0)) {
					
					to.setValorTotalFev(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalFev();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalFev(novoTotal);
				}
				break;
				
			case 3:
				if(to.getValorTotalMar()== new BigDecimal(0)) {
					
					to.setValorTotalMar(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalMar();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalMar(novoTotal);
				}
				break;
				
			case 4:
				if(to.getValorTotalAbr() == new BigDecimal(0)) {
					to.setValorTotalAbr(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalAbr();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalAbr(novoTotal);
				}
				break;
				
			case 5:
				if(to.getValorTotalMai() == new BigDecimal(0)) {
					to.setValorTotalMai(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalMai();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalMai(novoTotal);
				}
				break;
				
			case 6:
				if(to.getValorTotalJun() == new BigDecimal(0)) {
					to.setValorTotalJun(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalJun();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalJun(novoTotal);
				}
				break;
				
			case 7:				
				if(to.getValorTotalJul() == new BigDecimal(0)) {
					to.setValorTotalJul(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalJul();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalJul(novoTotal);
				}
				break;
				
			case 8:
				if(to.getValorTotalAgo() == new BigDecimal(0)) {
					to.setValorTotalAgo(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalAgo();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalAgo(novoTotal);
				}
				break;
				
			case 9:
				if(to.getValorTotalSet() == new BigDecimal(0)) {
					to.setValorTotalSet(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalSet();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalSet(novoTotal);
				}
				break;
				
			case 10:
				if(to.getValorTotalOut() == new BigDecimal(0)) {
					to.setValorTotalOut(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalOut();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalOut(novoTotal);
				}
				break;
				
			case 11:
				if(to.getValorTotalNov() == new BigDecimal(0)) {
					to.setValorTotalNov(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalNov();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalNov(novoTotal);
				}
				break;
				
			case 12:
				if(to.getValorTotalDez() == new BigDecimal(0)) {
					to.setValorTotalDez(valorDespesa);
				}else {
					BigDecimal totalExistente = to.getValorTotalDez();
	                BigDecimal novoTotal = totalExistente.add(valorDespesa);
	                to.setValorTotalDez(novoTotal);
				}
				break;
		}
	}
	
	public void calcValorAnual(DespesaTO to) {
		
		log.info("calcValorAnual...");
		
		
		BigDecimal valor = to.getValorTotalJan()
				.add(to.getValorTotalFev())
				.add(to.getValorTotalMar())
				.add(to.getValorTotalAbr())
				.add(to.getValorTotalMai())
				.add(to.getValorTotalJun())
				.add(to.getValorTotalJul())
				.add(to.getValorTotalAgo())
				.add(to.getValorTotalSet())
				.add(to.getValorTotalOut())
				.add(to.getValorTotalNov())
				.add(to.getValorTotalDez());
				
				
		
		to.setValorTotalAnual(valor);
		
		
	
	}
}
