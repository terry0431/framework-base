package com.os.framework.core.util;

import java.text.DecimalFormat;


import com.os.framework.core.exception.BSException;

public class NumberUtil {

	static DecimalFormat moneydf = new DecimalFormat("0.00000"); //TODO 加到配置项中  以后可能会细分到 吨、千克等
	static DecimalFormat weightdf = new DecimalFormat("0.00000"); //TODO 加到配置项中
	public static String convertNumberToWeightString(Object o){
		if(o == null){
			return "";
		}
		return weightdf.format(o);
	}

	public static String convertNumberToMoneyString(Object o){
		if(o == null){
			return "";
		}
		return moneydf.format(o);
	}
	
	public static String convertNumberToString(Object o,String format){
		DecimalFormat df = new DecimalFormat(format);
		if(o == null){
			return "";
		}
		return df.format(o);
	}
	
	public static String formatZero(Object o){
		String revalue = String.valueOf(o);
		try {
			if(revalue.contains(".")) { 
				while(true)  
				{  
					if(revalue.lastIndexOf("0") == (revalue.length() - 1)){  
						revalue = revalue.substring(0,revalue.lastIndexOf("0"));  
					}else{  
						break;  
					}  
				}  
				if(revalue.lastIndexOf(".") == (revalue.length() - 1)){  
					revalue = revalue.substring(0, revalue.lastIndexOf("."));  
				}  
			}
		} catch (Exception e) {
			throw new BSException(e);
		}  
		return revalue;
	}
}
