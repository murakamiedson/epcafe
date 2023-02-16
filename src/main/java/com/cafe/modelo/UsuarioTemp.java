package com.cafe.modelo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Setter
@Getter
@Entity
@NamedQueries({
	@NamedQuery(name="UsuarioTemp.buscarPorEmail", query="select u from UsuarioTemp u "
			+ "where u.email = :email")
})	
public class UsuarioTemp implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Email
    @Column(unique = true)
    private String email;
    private String senha;
    private String validacao;
    private String token;
    
    /*
	 * Datas de Criação e Modificação
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCriacao;	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataModificacao;

	@PrePersist
	@PreUpdate
	public void configuraDatasCriacaoAlteracao() {
		this.setDataModificacao( new Date() );
				
		if (this.getDataCriacao() == null) {
			this.setDataCriacao( new Date() );
		}		
	}
}
