package com.cafe.modelo.to;

import com.cafe.modelo.Tenant;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.Usuario;
import com.cafe.modelo.UsuarioTemp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastroTenantTO {
	
	private Tenant proprietario;
	private Unidade unidade;
	private Usuario usuario;
	private UsuarioTemp usuarioTemp;
}
