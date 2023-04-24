package com.cafe.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.cafe.dao.InstalacaoDAO;
import com.cafe.modelo.Instalacao;
import com.cafe.util.cdi.CDIServiceLocator;



/**
 * @author isabella
 *
 */
@FacesConverter(forClass=Instalacao.class)
public class InstalacaoConverter implements Converter<Object> {

	private InstalacaoDAO instalacaoDAO;
	
	public InstalacaoConverter() {
		this.instalacaoDAO = CDIServiceLocator.getBean(InstalacaoDAO.class);
	}
	
	@Override    //converte tipo String para objeto - necessário mapear do modelo relacional para obj
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Instalacao retorno = null;

		if (value != null && !value.isEmpty()) {
			retorno = this.instalacaoDAO.buscarPeloCodigo(Long.valueOf(value));
		}

		return retorno;
	}

	@Override  //converte de objeto para codigo - necessário mapear do modelo obj para relacional
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Instalacao) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}