package com.cafe.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import com.cafe.dao.DespesaMaquinaDAO;
import com.cafe.modelo.DespesaMaquina;
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
	
	
	public void salvar(DespesaMaquina despesaMaquina) throws NegocioException {
		
		log.info("Service : tenant = " + despesaMaquina.getTenant_id());		
		this.despesaMaquinaDAO.salvar(despesaMaquina);
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
		BigDecimal valorTotal;
		//switch()
		if(despesaMaquina.getMaquina().getTipoCombustivel().toString() == "GASOLINA") {
			valorTotal = new BigDecimal(0);
		}
		else if(despesaMaquina.getMaquina().getTipoCombustivel().toString() == "DIESEL"){
			valorTotal = despesaMaquina.getMaquina().getPotencia();
			valorTotal = valorTotal.multiply(new BigDecimal(0.15));
			valorTotal = valorTotal.multiply(despesaMaquina.getFatorPotencia().getValor());
			
		}else if(despesaMaquina.getMaquina().getTipoCombustivel().toString() == "ENERGIA_ELETRICA") {
			valorTotal = despesaMaquina.getMaquina().getPotencia();
			valorTotal = valorTotal.multiply(new BigDecimal(0.735));
			
		}else if(despesaMaquina.getMaquina().getTipoCombustivel().toString() == "ETANOL") {
			valorTotal = new BigDecimal(0);
			
		}else {
			valorTotal = new BigDecimal(0);
		}
		
		return valorTotal;
	}
}
