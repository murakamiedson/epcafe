package com.cafe.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.Tenant;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.Usuario;
import com.cafe.modelo.UsuarioTemp;
import com.cafe.modelo.to.AutoCadPropTO;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

import lombok.extern.log4j.Log4j;

/**
 * @author murakamiadmin
 *
 */
@Log4j
public class AutoCadPropDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;


	@Transactional
	public Usuario salvar(AutoCadPropTO autocadTO) throws NegocioException {		
		
		log.info("DAO : salvando cadastro competo ");
				
		try {
			Tenant sec = autocadTO.getTenant();
			sec = manager.merge(sec);
			
			Unidade unid = autocadTO.getUnidade();
			unid.setTenant_id(sec.getCodigo());
			unid = manager.merge(unid);
			
			Usuario usu = autocadTO.getUsuario();
			usu.setTenant(sec);
			usu.setUnidade(unid);
			usu = manager.merge(usu);
			
			removeUsuarioTemp(autocadTO.getUsuarioTemp());
			
			manager.flush();
			
			return usu;			
			
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
	
	private void removeUsuarioTemp(UsuarioTemp temp) throws NegocioException {
		try {
			
			log.info("DAO : excluindo usuario temp ");
			
			temp = manager.find(UsuarioTemp.class, temp.getId());
			
			// remove o usuario temporario
			manager.remove(temp);
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível excluir o usuario temporário.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível excluir o usuario temporário.");
		}
	}
}