package com.cafe.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.cafe.dao.TalhaoDAO;
import com.cafe.modelo.Talhao;
import com.cafe.util.cdi.CDIServiceLocator;



/**
 * @author joao
 *
 */
@FacesConverter(forClass=Talhao.class)
public class TalhaoConverter implements Converter<Object> {

	private TalhaoDAO talhaoDAO;
	
	public TalhaoConverter() {
		this.talhaoDAO = CDIServiceLocator.getBean(TalhaoDAO.class);
	}
	
	@Override    //converte tipo String para objeto - necessário mapear do modelo relacional para obj
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Talhao retorno = null;

		if (value != null && !value.isEmpty()) {
			retorno = this.talhaoDAO.buscarPeloCodigo(Long.valueOf(value));
		}

		return retorno;
	}

	@Override  //converte de objeto para codigo - necessário mapear do modelo obj para relacional
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Talhao) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}