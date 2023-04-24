package com.cafe.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.cafe.dao.InsumoDAO;
import com.cafe.modelo.Insumo;
import com.cafe.util.cdi.CDIServiceLocator;

/**
 * @author joao
 *
 */
@FacesConverter(forClass = Insumo.class)
public class InsumoConverter implements Converter<Object> {
	
private InsumoDAO insumoDAO;
	
	public InsumoConverter() {
		
		this.insumoDAO = CDIServiceLocator.getBean(InsumoDAO.class);
	}
	
	@Override    //converte tipo String para objeto - necessário mapear do modelo relacional para obj
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		Insumo retorno = null;

		if (value != null && !value.isEmpty()) {
			
			retorno = this.insumoDAO.buscarPeloCodigo(Long.valueOf(value));
		}

		return retorno;
	}

	@Override  //converte de objeto para codigo - necessário mapear do modelo obj para relacional
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (value != null) {
			Long codigo = ((Insumo) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}
}
