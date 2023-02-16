package com.cafe.service;

import java.io.Serializable;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import com.cafe.dao.UsuarioDAO;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.Usuario;
import com.cafe.modelo.enums.Role;
import com.cafe.modelo.enums.Status;
import com.cafe.modelo.enums.TipoPlano;
import com.cafe.util.NegocioException;

/**
 * @author murakamiadmin
 *
 */
public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Logger log = Logger.getLogger(UsuarioService.class);
	
	@Inject
	private UsuarioDAO usuarioDAO;
	@Inject
	private UnidadeService unidadeService;
	
	public void salvar(Usuario usuario, Long tenantId) throws NegocioException, SQLIntegrityConstraintViolationException {
				
		validaDados(usuario, tenantId);
		
		if(usuario.getSenha() == null || usuario.getSenha().equals(""))
			usuario.setSenha(BCrypt.hashpw("123456", BCrypt.gensalt()));		
					
		this.usuarioDAO.salvar(usuario);
	}
	
	private void validaDados(Usuario usuario, Long tenantId) throws NegocioException {
		
		if (usuario.getRole() == null) 
			throw new NegocioException("O papel (role) é obrigatório.");
		if (usuario.getGrupo() == null) 
			throw new NegocioException("O grupo é obrigatório.");
		if (usuario.getStatus() == null) 
			throw new NegocioException("O status é obrigatório.");
		
		/*
		 * verifica se é free, profissional ou especial
		 * free = 1 unidade e 3 usuarios
		 * profissional = 5 unidades e 50 usuarios
		 * especial = ilimitado
		 */
		TipoPlano plano = usuario.getTenant().getTipoPlano();
		
		int qde = usuarioDAO.verificaPlanoTenant(tenantId, plano).intValue();
		
		log.info("plano qde usuarios = " + qde);
		if(plano == TipoPlano.FREE) {			
			if (qde > 2) {
				throw new NegocioException("A quantidade de usuários do Plano Free é limitado a três usuários. Faça o upgrade para o Plano Profissional.");
			}
		} 
		else {
			if(plano == TipoPlano.PROFISSIONAL) {
				if (qde > 50) {
					throw new NegocioException("A quantidade de usuários do Plano Profissional é limitado a 50 usuários. Faça o upgrade para o Plano Especial.");
				}
			}
		}
	}		


	public void excluir(Usuario usuario) throws NegocioException {
		usuario.setStatus(Status.INATIVO);
		usuarioDAO.excluir(usuario);
		
	}
	
	public void reset(Usuario usuario) throws NegocioException, SQLIntegrityConstraintViolationException {			
		
		usuario.setSenha(BCrypt.hashpw("123456", BCrypt.gensalt()));		
		this.usuarioDAO.salvar(usuario);
	}	

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}


	public Usuario buscarPeloEmail(String email) throws NoResultException {
		return usuarioDAO.buscarPeloEmail(email);
	}

	public void trocarSenha(Usuario usuario, String senhaAntiga) throws NegocioException, PersistenceException {
					
		Usuario u = buscarPeloEmail(usuario.getEmail());
		
		if(u != null) {
			if(!BCrypt.checkpw(senhaAntiga, u.getSenha()))
				throw new NegocioException("Senha não confere! Digite a senha antiga corretamente.");
			else {
				usuario.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
				this.usuarioDAO.salvar(usuario);
			}				
		}
			
	}
	
	public void alterarPerfil(Usuario usuario) throws NegocioException, PersistenceException {		
		this.usuarioDAO.salvar(usuario);
	}



	public List<Usuario> buscarTecnicos(Unidade unidade, Long tenantId) {		
		return usuarioDAO.buscarTecnicos(unidade, tenantId);
	}
	public List<Usuario> buscarTecnicosRole(Role role, Unidade unidade, Long tenantId) {
		return usuarioDAO.buscarTecnicosRole(role, unidade, tenantId);
	}
	public List<Usuario> buscarUsuarios(Unidade unidade, Long tenantId) {		
		return usuarioDAO.buscarUsuarios(unidade, tenantId);
	}


	public List<Unidade> buscarUnidades(Long tenantId) {	
		log.info("Primeiro acesso a banco... buscar unidades");
		
		return unidadeService.buscarTodos(tenantId);
	}
	
	
}
