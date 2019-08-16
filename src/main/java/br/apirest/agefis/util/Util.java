package br.apirest.agefis.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	
	public Date DataCorrente() throws ParseException {	 
		String formatoData = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatoData);		
		String data = simpleDateFormat.format(new Date());
		//System.out.println(data);
		return simpleDateFormat.parse(data);   	
		//return new Date();
	}
	
	public Date HoraCorrente() throws ParseException {			
			String formatoHora= "HH:mm";
			SimpleDateFormat simpleHoraFormat = new SimpleDateFormat(formatoHora);
			String hora = simpleHoraFormat.format(new Date());
			//System.out.println(hora);
			return simpleHoraFormat.parse(hora);
			//return new Date();
	}

}
