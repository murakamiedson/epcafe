package com.cafe.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.cafe.dao.FuncionarioDAO;
import com.cafe.modelo.Funcionario;
import com.cafe.util.cdi.CDIServiceLocator;

/**
 * @author isabella
 *
 */
@FacesConverter(forClass=Funcionario.class)
public class FuncionarioConverter implements Converter<Object>{
	
	private FuncionarioDAO funcionarioDAO;
	
	public FuncionarioConverter() {
		this.funcionarioDAO = CDIServiceLocator.getBean(FuncionarioDAO.class);
	}
	
	@Override    //converte tipo String para objeto - necessário mapear do modelo relacional para obj
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Funcionario retorno = null;

		if (value != null && !value.isEmpty()) {
			retorno = this.funcionarioDAO.buscarPeloCodigo(Long.valueOf(value));
		}

		return retorno;
	}

	@Override  //converte de objeto para codigo - necessário mapear do modelo obj para relacional
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Funcionario) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}
