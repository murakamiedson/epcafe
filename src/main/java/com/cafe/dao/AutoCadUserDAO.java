package com.cafe.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.cafe.modelo.Usuario;
import com.cafe.modelo.UsuarioTemp;
import com.cafe.util.NegocioException;
import com.cafe.util.jpa.Transactional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutoCadUserDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    
    public UsuarioTemp buscarPeloCodigo(Long id) {
		return manager.find(UsuarioTemp.class, id);
	}
    
    public UsuarioTemp buscaEmailTemp (UsuarioTemp tempUser) {
    	UsuarioTemp usuarioTemp = null;
        try {
            usuarioTemp = manager.createQuery("SELECT u"
                            + " from UsuarioTemp u"
                            + " WHERE u.email = :email", UsuarioTemp.class)
                    .setParameter("email", tempUser.getEmail())
                    .getSingleResult();
            
        } catch (PersistenceException e) {
        	e.printStackTrace();           
        }

        return usuarioTemp;
    }
    public Usuario buscaEmail (UsuarioTemp tempUser) {
    	Usuario usuario = null;
        try {
            usuario = manager.createNamedQuery("Usuario.buscarPorEmail", Usuario.class)
                    .setParameter("email", tempUser.getEmail())
                    .getSingleResult();
           
        } catch (PersistenceException e) {
        	e.printStackTrace();
        }

        return usuario;
    }
   
    @Transactional
    public UsuarioTemp salvar(UsuarioTemp tempUser) throws PersistenceException, NegocioException {
        try {
        	tempUser = manager.merge(tempUser); 
        	manager.flush();
        	return tempUser;
            
        } catch (PersistenceException e) {
            e.printStackTrace();
            throw e;
        } catch (Error | Exception e) {
            e.printStackTrace();
            throw new NegocioException("Não foi possível executar a operação.");
        }
    }
    @Transactional
    public void excluir(UsuarioTemp tempUser) throws NegocioException {

        try {
            manager.merge(tempUser);
        } catch (Exception | Error e) {
            e.printStackTrace();
            throw new NegocioException("Não foi possível executar a operação.");
        }
    }
	
}
