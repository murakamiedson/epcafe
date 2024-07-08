package com.cafe.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.FertilizanteDAO;
import com.cafe.dao.NotaFiscalDAO;
import com.cafe.modelo.Fertilizante;
import com.cafe.modelo.Item;
import com.cafe.modelo.NotaFiscal;
import com.cafe.modelo.Unidade;
import com.cafe.util.NegocioException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class NotaFiscalService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private NotaFiscalDAO notaFiscalDAO;
	@Inject
	private FertilizanteDAO fertilizanteDAO;

	
	/*
	 * Nota Fiscal
	 */
	
	public NotaFiscal salvar(NotaFiscal notaFiscal) throws NegocioException {
			
		log.info("salvando...chamando dao");
		
		validarTotal(notaFiscal);
				
		if(notaFiscal.getId() != null) {
			notaFiscal = this.notaFiscalDAO.alterar(notaFiscal);
		}
		else {
			notaFiscal = this.notaFiscalDAO.salvar(notaFiscal);
		}

		return notaFiscal;
	}
	
	private void validarTotal(NotaFiscal notaFiscal) throws NegocioException {
		log.info("validando total ...");
		BigDecimal total = new BigDecimal(0);
		
		for(Item i : notaFiscal.getItens()) {
			total = total.add(i.getValor().multiply(i.getQuantidade()));			
		}		
		if(total.compareTo(notaFiscal.getValorTotal()) == 1) {			
			throw new NegocioException("Valor total dos itens é maior que o total da nota fiscal!");
		}
	}

	public void excluir(NotaFiscal notaFiscal) throws NegocioException {
		this.notaFiscalDAO.excluir(notaFiscal);
	}

	public List<NotaFiscal> buscarNotasFiscais(Unidade unidade) {
		return notaFiscalDAO.buscarNotasFiscais(unidade);
	}
	
	public List<NotaFiscal> buscarNotasFiscaisPorAno(LocalDate dataInicio, LocalDate dataFim, Unidade unidade){
		return notaFiscalDAO.buscarNotasFiscaisPorAno(dataInicio, dataFim, unidade);
	}
	
	public List<String> buscarAnosComRegistros(Unidade unidade) {
		
		List<String> anos  = notaFiscalDAO.buscarAnosComRegistro(unidade);
		return anos;
	}

	public NotaFiscal buscarNotaFiscalPorNumero(String numero, Unidade unidade) {
		return notaFiscalDAO.buscarNotaFiscalPorNumero(numero, unidade);
	}
	
	public NotaFiscal buscarNotaFiscalPeloCodigo(long id) {
		return notaFiscalDAO.buscarNotaFiscalPeloCodigo(id);
	}
	
	/*
	 * Item
	 */
	
	public void excluirItem(Item item) throws NegocioException {
		log.info("Entrou no service");
		notaFiscalDAO.excluirItem(item);		
	}

	
	/*
	 * Nota Fiscal / Fertilizante
	 */
	
	public List<Fertilizante> buscarFertilizantes() {

		return fertilizanteDAO.buscarFertilizantes();
	}
	
	public List<NotaFiscal> buscarNotaFiscalPorFertilizante(Long codigoFertilizante, Unidade unidade){
		
		return this.notaFiscalDAO.buscarNotasFiscaisPorFertilizante(codigoFertilizante, unidade);
	}
	
}
