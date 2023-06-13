package com.cafe.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.cafe.dao.FormacaoDAO;
import com.cafe.modelo.Formacao;
import com.cafe.util.cdi.CDIServiceLocator;



/**
 * @author murakamiadmin
 *
 */
@FacesConverter(forClass=Formacao.class)
public class FormacaoConverter implements Converter<Object> {

	private FormacaoDAO formacaoDAO;
	
	public FormacaoConverter() {
		this.formacaoDAO = CDIServiceLocator.getBean(FormacaoDAO.class);
	}
	
	@Override    //converte tipo String para objeto - necessário mapear do modelo relacional para obj
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Formacao retorno = null;

		if (value != null && !value.isEmpty()) {
			retorno = this.formacaoDAO.buscarPeloCodigo(Long.valueOf(value));
		}

		return retorno;
	}

	@Override  //converte de objeto para codigo - necessário mapear do modelo obj para relacional
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Formacao) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}