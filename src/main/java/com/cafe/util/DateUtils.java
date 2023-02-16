package com.cafe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.cafe.modelo.enums.Mes;
import com.cafe.modelo.to.DatasIniFimTO;


/**
 * @author murakamiadmin
 *
 */
public class DateUtils {
	
	private static Logger log = Logger.getLogger(DateUtils.class);
	
	/* Date to LocatDate e vice-versa */

	public static Date asDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static Date asDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate asLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}	

	public static LocalDateTime asLocalDateTime(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	/* Date To Calendar e vice-versa */

	public static Calendar asCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static Date asDate(Calendar cal) {		
		return cal.getTime();
	}
	
	/* Conversor Data to String e vice-versa */
	public static Date parseStringToDate(String data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        return sdf.parse(data);
    }

    public static String parseDateToString(Date data) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        return sdf.format(data);
    }

    
    /* Soma um dia em Date */
    public static Date plusDay(Date data) {
    	// menos um minuto para não pegar datas com hora = 00:00:00
    	LocalDateTime ldt = asLocalDateTime(data).plusDays(1).minusMinutes(1);
    	
    	return asDate(ldt);    	
    }
    public static Date minusDay(Date data) {
    	
    	return asDate(asLocalDateTime(data).minusDays(1));
    }
    
    /* Soma n dias em Date */
    public static Date plusDays(Date data, int numeroDias) {
    	
    	return asDate(asLocalDateTime(data).plusDays(numeroDias));    	
    }
    public static Date minusDays(Date data, int numeroDias) {
    	
    	return asDate(asLocalDateTime(data).minusDays(numeroDias));
    }
    
    public static Date getDataIMesCorrente() {
		LocalDate data = LocalDate.now();
		Date date = DateUtils.asDate(LocalDate.of(data.getYear(), data.getMonthValue(), 1));
		//logUtil.info("ini: " + date);
		return date;		
	}
    
    public static Date getDataFMesCorrente() {
		LocalDate data = LocalDate.now();
		Date date = DateUtils.asDate(LocalDate.of(data.getYear(), data.getMonthValue(), data.lengthOfMonth()));
		//logUtil.info("fim: " + date);
		return date;
	}
    
    /*
     * 
     * Recuperação da tas ini e fim a partir de mes e ano
     * 
     */
    public static DatasIniFimTO getDatasIniFim(Integer ano, Mes mes) {
		
		DatasIniFimTO to = new DatasIniFimTO();
		LocalDate dataCorrente = LocalDate.now();  // pega ano corrente
		
		if(ano == null) {		
			
			if(mes == null) {				
				to.setIni(DateUtils.asDate(LocalDate.of(dataCorrente.getYear(), dataCorrente.getMonthValue(), 1)));
				to.setFim(DateUtils.asDate(LocalDate.of(dataCorrente.getYear(), dataCorrente.getMonthValue(), dataCorrente.lengthOfMonth())));
				log.debug(to.getIni() + " - " + to.getFim());
				
			}
			else {				
				LocalDate dt = LocalDate.of(dataCorrente.getYear(), mes.ordinal()+1, 1);
				to.setIni(DateUtils.asDate(dt));
				to.setFim(DateUtils.asDate(LocalDate.of(dataCorrente.getYear(), dt.getMonthValue(), dt.lengthOfMonth())));
				log.debug(to.getIni() + " - " + to.getFim());
				
			}
		} else {			

			if(mes == null) {
				to.setIni(DateUtils.asDate(LocalDate.of(ano, dataCorrente.getMonthValue(), 1)));
				to.setFim(DateUtils.asDate(LocalDate.of(ano, dataCorrente.getMonthValue(), dataCorrente.lengthOfMonth())));
				log.debug(to.getIni() + " - " + to.getFim());
				
			}
			else {
				LocalDate dt = LocalDate.of(ano, mes.ordinal()+1, 1);
				to.setIni(DateUtils.asDate(dt));
				to.setFim(DateUtils.asDate(LocalDate.of(ano, dt.getMonthValue(), dt.lengthOfMonth())));
				log.debug(to.getIni() + " - " + to.getFim());

			}
		}

		return to;
	}

	public static DatasIniFimTO getDatasIniFim(Integer ano, Mes m1, Mes mes2) {

		DatasIniFimTO to = new DatasIniFimTO();
		LocalDate dataCorrente = LocalDate.now();  // pega ano corrente

		if(ano == null) {

			if(m1 == null) {
				to.setIni(DateUtils.asDate(LocalDate.of(dataCorrente.getYear(), dataCorrente.getMonthValue(), 1)));
				to.setFim(DateUtils.asDate(LocalDate.of(dataCorrente.getYear(), dataCorrente.getMonthValue(), dataCorrente.lengthOfMonth())));
				log.debug(to.getIni() + " - " + to.getFim());

			} else {
				if (mes2 == null) {
					LocalDate dt1 = LocalDate.of(dataCorrente.getYear(), m1.ordinal()+1, 1);
					to.setIni(DateUtils.asDate(dt1));
					to.setFim(DateUtils.asDate(LocalDate.of(dataCorrente.getYear(), dt1.getMonthValue(), dt1.lengthOfMonth())));
					log.debug(to.getIni() + " - " + to.getFim());
				} else {
					LocalDate dt1 = LocalDate.of(dataCorrente.getYear(), m1.ordinal()+1, 1);
					to.setIni(DateUtils.asDate(dt1));
					LocalDate dt2 = LocalDate.of(dataCorrente.getYear(), mes2.ordinal()+1, 1);
					to.setFim(DateUtils.asDate(LocalDate.of(dataCorrente.getYear(), dt2.getMonthValue(), dt2.lengthOfMonth())));
					log.debug(to.getIni() + " - " + to.getFim());
				}
			}
		} else {

			if(m1 == null) {
				to.setIni(DateUtils.asDate(LocalDate.of(ano, dataCorrente.getMonthValue(), 1)));
				to.setFim(DateUtils.asDate(LocalDate.of(ano, dataCorrente.getMonthValue(), dataCorrente.lengthOfMonth())));
				log.debug(to.getIni() + " - " + to.getFim());

			} else {

				if (mes2 == null) {
					LocalDate dt = LocalDate.of(ano, m1.ordinal()+1, 1);
					to.setIni(DateUtils.asDate(dt));
					to.setFim(DateUtils.asDate(LocalDate.of(ano, dt.getMonthValue(), dt.lengthOfMonth())));
					log.debug(to.getIni() + " - " + to.getFim());
				} else {
					LocalDate dt1 = LocalDate.of(ano, m1.ordinal()+1, 1);
					to.setIni(DateUtils.asDate(dt1));
					LocalDate dt2 = LocalDate.of(ano, mes2.ordinal()+1, 1);
					to.setFim(DateUtils.asDate(LocalDate.of(ano, dt2.getMonthValue(), dt2.lengthOfMonth())));
					log.debug(to.getIni() + " - " + to.getFim());
				}
			}
		}

		return to;
	}
}