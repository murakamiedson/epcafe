package com.cafe.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.NotaFiscalDAO;
import com.cafe.modelo.NotaFiscal;
import com.cafe.util.NegocioException;

public class NotaFiscalService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private NotaFiscalDAO notaFiscalDAO;

	public void salvar(NotaFiscal notaFiscal) throws NegocioException {
		this.notaFiscalDAO.salvar(notaFiscal);
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


}
