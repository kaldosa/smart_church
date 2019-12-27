package com.laonsys.springmvc.extensions.utils;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class StringUtils {
	public static String substing(String str, int length) {
		if(str == null || str.length() == 0 || str.length() <= length) {
			return str;
		}
		
		return str.substring(0, length - 3) + "...";
	}

	@SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj){
        if( obj instanceof String ) return obj==null || "".equals(obj.toString().trim());
        else if( obj instanceof List ) return obj==null || ((List)obj).isEmpty();
        else if( obj instanceof Map ) return obj==null || ((Map)obj).isEmpty();
        else if( obj instanceof Object[] ) return obj==null || Array.getLength(obj)==0;
        else return obj==null;
    }
	
	public static String doubleToString(double number, int decimalPlace) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits((decimalPlace == 0) ? 1 : decimalPlace);
		numberFormat.setGroupingUsed(false);
		return numberFormat.format(number);
	}
	
	public static String replaceCRLF(String source) {
	    String result = source.replaceAll("\r\n", "<br/>");
	    return result;
	}
	
	public static String replaceHTMLTag(String source) {
	    String regex = "</?\\w+((\\s+\\w+(\\s*=\\s*(?:\".*?\"|\'.*?\'|[^\'\">\\s]+))?)+\\s*|\\s*)/?>";
	    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	    return pattern.matcher(source).replaceAll("");
	}
	
	public static String dateTimeToString(DateTime dateTime, String pattern) {
	    if (dateTime == null) {
	        throw new IllegalArgumentException("DateTime must not null");
	    }
	    
	    if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Invalid pattern specification");
        }

	    DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.print(dateTime);
	}
	
	public static String formatFileSize(long size) {
        if (size >= 1000000000) {
            return doubleToString((double) size/ 100000000, 2) + " GB";
        }
        if (size >= 1000000) {
            return doubleToString((double) size/ 1000000, 2) + " MB";
        }
        return doubleToString((double) size/ 1000, 2) + " KB";
	}
}
