package com.cafe.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.cafe.modelo.Item;
import com.cafe.modelo.NotaFiscal;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

import lombok.extern.log4j.Log4j;


@Log4j
public class NotaFiscalDAO implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@Transactional
	public NotaFiscal alterar(NotaFiscal notaFiscal) throws NegocioException {
		log.info("alterando na dao...");
		NotaFiscal nfBanco = manager.find(NotaFiscal.class, notaFiscal.getId());
		
		if( notaFiscal.getItens().size() < nfBanco.getItens().size() ) {
			removerItem(notaFiscal, nfBanco);
		}
	
		return manager.merge(notaFiscal);
	}	
	
	private void removerItem(NotaFiscal notaFiscal, NotaFiscal nfBanco) throws NegocioException {

		 
		 for(Item ib : notaFiscal.getItens()) {
			 if (nfBanco.getItens().contains(ib)) {
				 nfBanco.getItens().remove(ib);
			 }
		 }
		 
		 for(Item ib : nfBanco.getItens()) {
			log.info("excluindo item..." + ib.getId());
			Item i = manager.find(Item.class, ib.getId());
			
			Query query = manager.createQuery(
					"DELETE FROM Item it WHERE it.fertilizante = :fertilizante and it.notaFiscal = :notaFiscal");
			
			int deletedCount = query.setParameter("fertilizante", i.getFertilizante())
					.setParameter("notaFiscal", i.getNotaFiscal()).executeUpdate();

			log.info(deletedCount);
			//manager.remove(i);				
		 }
		 manager.flush();		 
	}

	@Transactional
	public NotaFiscal salvar(NotaFiscal notaFiscal) throws NegocioException {
		try {	
			log.info("salvando na dao...");
							
			return manager.merge(notaFiscal);

		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Error e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		}		
	}
	
		
	@Transactional
	public void excluir(NotaFiscal notaFiscal) throws NegocioException {
			
		try {
			log.info("excluindo nf...");
			NotaFiscal m = this.buscarNotaFiscalPeloCodigo(notaFiscal.getId());
			manager.remove(m);
			manager.flush();
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			if(e.getMessage().contains("ConstraintViolationException"))
				throw new NegocioException("Não foi possível excluir pois já existem"
						+ " despesas cadastradas com essa Nota Fiscal.");
			throw new NegocioException("Não foi possível executar a operação.1");
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.2");
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.3");
		} catch (Error e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.4");
		}
	}
	
	
	/*
	 * Buscas NF
	 */
	
	public NotaFiscal buscarNotaFiscalPeloCodigo(Long id) {
		return manager.find(NotaFiscal.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<NotaFiscal> buscarNotasFiscais(Long tenantId) {
		return manager.createNamedQuery("NotaFiscal.buscarNotasFiscais")
				.setParameter("tenantId", tenantId)
				.getResultList();
	}
	
	public NotaFiscal buscarNotaFiscalPorNumero(String numero, Long tenantId) {
		return manager.createNamedQuery("NotaFiscal.buscarNotaFiscalPorNumero", NotaFiscal.class)
				.setParameter("numero", numero)
				.setParameter("tenantId", tenantId)
				.getSingleResult();
	}
	
	public List<NotaFiscal> buscarNotasFiscaisPorFertilizante(Long codigoFertilizante, Long tenantId) {
		/*
		 * SELECT * FROM epcafe.notafiscal nf INNER JOIN item i ON i.codigo_nota = nf.id
		 * WHERE i.codigo_fertilizante = 4;
		 */		
		
		List<NotaFiscal> lista = manager.createQuery(
			    "SELECT DISTINCT n " + 
			    "FROM NotaFiscal n " +
			    "INNER JOIN n.itens i " +
			    "WHERE i.fertilizante.id = :codigoFertilizante " +
			    "AND n.tenant_id = :tenantId", NotaFiscal.class)
			.setParameter("codigoFertilizante", codigoFertilizante)
			.setParameter("tenantId", tenantId)
			.getResultList();
		
		log.info(lista);
		
		return lista;
	}
	
	
	
	
	public Item buscarItemPeloCodigo(Long id) {
		return manager.find(Item.class, id);
	}
	

	@Transactional
	public void excluirItem(Item item) throws NegocioException {
		log.info("entrou no metodo dao");	
		try {
			Item i = this.buscarItemPeloCodigo(item.getId());
			log.info("EXCLUSÃO: " + i.getId());
			manager.remove(i);
			manager.flush();
			
		} catch (PersistenceException e) {			
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		} catch (Error e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível executar a operação.");
		}
	}

}
