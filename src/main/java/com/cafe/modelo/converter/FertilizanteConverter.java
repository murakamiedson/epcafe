package com.cafe.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.cafe.dao.FertilizanteDAO;
import com.cafe.modelo.Fertilizante;
import com.cafe.util.cdi.CDIServiceLocator;



/**
 * @author murakamiadmin
 *
 */
@FacesConverter(forClass=Fertilizante.class)
public class FertilizanteConverter implements Converter<Object> {

	private FertilizanteDAO fertDAO;
	
	public FertilizanteConverter() {
		fertDAO = CDIServiceLocator.getBean(FertilizanteDAO.class);
	}
	
	@Override    //converte tipo String para objeto - necessário mapear do modelo relacional para obj
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Fertilizante retorno = null;

		if (value != null && !value.isEmpty()) {
			retorno = this.fertDAO.buscarPeloCodigo(Long.valueOf(value));
		}

		return retorno;
	}

	@Override  //converte de objeto para codigo - necessário mapear do modelo obj para relacional
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((Fertilizante) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}