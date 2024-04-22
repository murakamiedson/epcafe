package com.cafe.modelo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.cafe.dao.NotaFiscalDAO;
import com.cafe.modelo.NotaFiscal;
import com.cafe.util.cdi.CDIServiceLocator;



/**
 * @author murakamiadmin
 *
 */
@FacesConverter(forClass=NotaFiscal.class)
public class NotaFiscalConverter implements Converter<Object> {

	private NotaFiscalDAO nfDAO;
	
	public NotaFiscalConverter() {
		nfDAO = CDIServiceLocator.getBean(NotaFiscalDAO.class);
	}
	
	@Override    //converte tipo String para objeto - necessário mapear do modelo relacional para obj
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		NotaFiscal retorno = null;

		if (value != null && !value.isEmpty()) {
			retorno = this.nfDAO.buscarNotaFiscalPeloCodigo(Long.valueOf(value));
		}

		return retorno;
	}

	@Override  //converte de objeto para codigo - necessário mapear do modelo obj para relacional
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Long codigo = ((NotaFiscal) value).getId();
			String retorno = (codigo == null ? null : codigo.toString());
			
			return retorno;
		}
		
		return "";
	}

}