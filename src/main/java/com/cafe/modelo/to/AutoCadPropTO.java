package com.cafe.modelo.to;

import com.cafe.modelo.Tenant;
import com.cafe.modelo.Unidade;
import com.cafe.modelo.Usuario;
import com.cafe.modelo.UsuarioTemp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutoCadPropTO {
	
	private Tenant tenant;
	private Unidade unidade;
	private Usuario usuario;
	private UsuarioTemp usuarioTemp;

}
