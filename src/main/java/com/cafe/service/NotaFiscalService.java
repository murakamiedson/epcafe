package com.cafe.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.FertilizanteDAO;
import com.cafe.dao.NotaFiscalDAO;
import com.cafe.modelo.Fertilizante;
import com.cafe.modelo.Item;
import com.cafe.modelo.NotaFiscal;
import com.cafe.util.NegocioException;

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
		return this.notaFiscalDAO.salvar(notaFiscal);
	}	

	public void excluir(NotaFiscal notaFiscal) throws NegocioException {
		this.notaFiscalDAO.excluir(notaFiscal);
	}

	public List<NotaFiscal> buscarNotasFiscais(Long tenantId) {
		return notaFiscalDAO.buscarNotasFiscais(tenantId);
	}

	public NotaFiscal buscarNotaFiscalPorNumero(String numero, Long tenantId) {
		return notaFiscalDAO.buscarNotaFiscalPorNumero(numero, tenantId);
	}
	
	public NotaFiscal buscarNotaFiscalPeloCodigo(long id) {
		return notaFiscalDAO.buscarNotaFiscalPeloCodigo(id);
	}
	
	/*
	 * Item
	 */
	
	public void excluirItem(Item item) throws NegocioException {
		notaFiscalDAO.excluirItem(item);		
	}

	
	
	
	/*
	 * Nota Fiscal / Fertilizante
	 */
	
	public List<Fertilizante> buscarFertilizantes(Long tenantId) {

		return fertilizanteDAO.buscarFertilizantes(tenantId);
	}
	
	public List<NotaFiscal> buscarNotaFiscalPorFertilizante(Long codigoFertilizante, Long tenantId){
		
		return this.notaFiscalDAO.buscarNotasFiscaisPorFertilizante(codigoFertilizante, tenantId);
	}
	
}
