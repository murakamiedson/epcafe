package com.cafe.util;

import java.io.Serializable;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

/**
 * @author murakamiadmin
 *
 */


public class CalculoUtil implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static int calcularIdade(Date dataNasc) {

		Calendar dataNascimento = Calendar.getInstance();
		dataNascimento.setTime(dataNasc);
		Calendar hoje = Calendar.getInstance();

		int idade = hoje.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR);

		if (hoje.get(Calendar.MONTH) < dataNascimento.get(Calendar.MONTH)) {
			idade--;
		} else {
			if (hoje.get(Calendar.MONTH) == dataNascimento.get(Calendar.MONTH)
					&& hoje.get(Calendar.DAY_OF_MONTH) < dataNascimento.get(Calendar.DAY_OF_MONTH)) {
				idade--;
			}
		}

		return idade;
	}

	public String getAnoCorrente() {

		int anoCorrente = Year.now().getValue();
		int dezAnosAntes = anoCorrente - 100;

		String yearRange = dezAnosAntes + ":" + anoCorrente;
		return yearRange;
	}

}