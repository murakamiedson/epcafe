package com.cafe.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.cafe.dao.FabricanteDAO;
import com.cafe.modelo.Fabricante;
import com.cafe.util.cdi.CDIServiceLocator;



/**
 * @author murakamiadmin
 *
 */
@FacesConverter(forClass=Fabricante.class)
public class FabricanteConverter implements Converter<Object> {

	private FabricanteDAO fabricanteDAO;
	
	public FabricanteConverter() {
		this.fabricanteDAO = CDIServiceLocator.getBean(FabricanteDAO.class);
	}
	
	@Override    //converte tipo String para objeto - necessário mapear do modelo relacional para obj
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Fabricante retorno = null;

		if (value != null && !value.isEmpty()) {
			retorno = this.fabricanteDAO.buscarPeloCodigo(Long.valueOf(value));
		}

		return retorno;
	}

	@Override  //converte de objeto para codigo - necessário mapear do modelo obj para relacional
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Fabricante) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}