package core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import core.config.Config;
import core.exception.BSException;

public class DateUtil {
        
	public static Date convertStringToDate(String str){
		SimpleDateFormat dateformat = new SimpleDateFormat(Config.getInstance().getDef_date_format() );
		Date date = null;
		try {
			date = dateformat.parse(str);
		} catch (ParseException e) {
			throw new BSException(e);
		}
		return date;
	}

	public static Date convertStringToDatetime(String str){
		SimpleDateFormat dateformat = new SimpleDateFormat(Config.getInstance().getDef_datetime_format());
		Date date = null;
		try {
			date = dateformat.parse(str);
		} catch (ParseException e) {
			throw new BSException(e);
		}
		return date;
	}

	public static Date convertStringToTime(String str){
		SimpleDateFormat dateformat = new SimpleDateFormat(Config.getInstance().getDef_time_format());
		Date date = null;
		try {
			date = dateformat.parse(str);
		} catch (ParseException e) {
			throw new BSException(e);
		}
		return date;
	}

	public static String convertDateToString(Date date){
		SimpleDateFormat dateformat;
		String datestr;
		try {
			dateformat = new SimpleDateFormat(Config.getInstance().getDef_date_format());
			datestr = dateformat.format(date);
		} catch (Exception e) {
			throw new BSException(e);
		}
		return datestr;
	}
	public static String convertDatetimeToString(Date date){
		String datestr;
		try {
			SimpleDateFormat dateformat = new SimpleDateFormat(Config.getInstance().getDef_datetime_format());
			datestr = dateformat.format(date);
		} catch (Exception e) {
			throw new BSException(e);
		}
		return datestr;
	}
	public static String convertTimeToString(Date date){
		String datestr;
		try {
			SimpleDateFormat dateformat = new SimpleDateFormat(Config.getInstance().getDef_time_format());
			datestr = dateformat.format(date);
		} catch (Exception e) {
			throw new BSException(e);
		}
		return datestr;
	}

	public static String convertDBDateToString(java.sql.Date sqldate){
		String datestr;
		try {
			Date date = new Date(sqldate.getTime());
			SimpleDateFormat dateformat = new SimpleDateFormat(Config.getInstance().getDef_dbdate_format());
			datestr = dateformat.format(date);
		} catch (Exception e) {
			throw new BSException(e);
		}
		return datestr;
	}

	public static String convertDBDatetimeToString(Timestamp timestamp){
		String datestr;
		try {
			Date date = new Date();
			date.setTime(timestamp.getTime());
			SimpleDateFormat dateformat = new SimpleDateFormat(Config.getInstance().getDef_dbdatetime_format());
			datestr = dateformat.format(date);
		} catch (Exception e) {
			throw new BSException(e);
		}
		return datestr;
	}

	public static String convertDBTimeToString(Timestamp timestamp){
		String datestr;
		Date date = new Date();
		try {
			date.setTime(timestamp.getTime());
			SimpleDateFormat dateformat = new SimpleDateFormat(Config.getInstance().getDef_dbtime_format());
			datestr = dateformat.format(date);
		} catch (Exception e) {
			throw new BSException(e);
		}
		return datestr;
	}

	public static Date convertDBStringToDate(String str){
		SimpleDateFormat dateformat = new SimpleDateFormat(Config.getInstance().getDef_dbdate_format());
		Date date = null;
		try {
			date = dateformat.parse(str);
		} catch (ParseException e) {
			throw new BSException(e);
		}
		return date;
	}

	public static Date convertDBStringToDatetime(String str){

		SimpleDateFormat dateformat = new SimpleDateFormat(Config.getInstance().getDef_dbdatetime_format());
		Date date = null;
		try {
			date = dateformat.parse(str);
		} catch (ParseException e) {
			throw new BSException(e);
		}
		return date;
	}

	public static Date convertDBStringToTime(String str){

		SimpleDateFormat dateformat = new SimpleDateFormat(Config.getInstance().getDef_dbtime_format());
		Date date = null;
		try {
			date = dateformat.parse(str);
		} catch (ParseException e) {
			throw new BSException(e);
		}
		return date;
	}

	public static Date convertStringToDate(String str,String format){
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateformat.parse(str);
		} catch (ParseException e) {
			throw new BSException(e);
		}
		return date;
	}

	public static String convertDateToString(Date date,String format){
		String datestr;
		if(date == null){
			return "";
		}
		try {
			SimpleDateFormat dateformat = new SimpleDateFormat(format);
			datestr = dateformat.format(date);
		} catch (Exception e) {
			throw new BSException(e);
		}
		return datestr;
	}

	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df ;
		String returnValue = "";
		try {
			if (aDate != null) {
				df = new SimpleDateFormat(aMask);
				returnValue = df.format(aDate);
			}
		} catch (Exception e) {
			throw new BSException(e);
		}
		return (returnValue);
	}
	
	public static final Date add(Date date,int field,int amount){
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(date);
			calendar.add(field, amount);
		} catch (Exception e) {
			throw new BSException(e);
		}
		return calendar.getTime();
	}
}
