/*
 * DateUtils.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
 * 
 * This file is part of DocSources.
 * 
 * DocSources is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * DocSources is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 */
package org.medici.docsources.common.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.medici.docsources.domain.Month;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class DateUtils {
	public static Integer MAX_DATE = 20121221;
	public static Integer MIN_DATE = 10101;

	/**
	 * 
	 * @param nextToken
	 * @return
	 */
	public static Integer getDateDayFromString(String nextToken) {
		if (nextToken == null)
			return null;
		if (nextToken.equals(""))
			return null;
		
		return NumberUtils.createInteger(nextToken);
	}
	
	public static String getDateForSQLQuery(Integer year, Integer month, Integer day){
		StringBuffer stringBuffer = new StringBuffer("");
		StringBuffer dateFormat = new StringBuffer("");
		stringBuffer.append("STR_TO_DATE('");
		if(year != null){
			stringBuffer.append(year);
			dateFormat.append("%Y");
		}
		if(month != null){
			if(stringBuffer.length() > 13){
				stringBuffer.append(",");
				dateFormat.append(",");
			}
			stringBuffer.append(month);
			dateFormat.append("%m");
		}
		if(day != null){
			if(stringBuffer.length() > 13){
				stringBuffer.append(",");
				dateFormat.append(",");
			}
			stringBuffer.append(day);
			dateFormat.append("%d");
		}
		stringBuffer.append("','");
		stringBuffer.append(dateFormat);
		stringBuffer.append("')");
		
		return stringBuffer.toString();
	}

	/**
	 * 
	 * @param nextToken
	 * @return
	 */
	public static Integer getDateMonthFromString(String nextToken) {
		if (nextToken == null)
			return null;
		if (nextToken.equals(""))
			return null;
		
		return NumberUtils.createInteger(nextToken);
	}

	/**
	 * 
	 * @param nextToken
	 * @return
	 */
	public static Integer getDateYearFromString(String nextToken) {
		if (nextToken == null)
			return null;
		if (nextToken.equals(""))
			return null;
		
		return NumberUtils.createInteger(nextToken);
	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Integer getNumberDate(Integer year, Integer month, Integer day) {
		return getLuceneDate(year, month, day);
	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Integer getLuceneDate(Integer year, Integer month, Integer day) {
		StringBuffer stringBuffer = new StringBuffer("");
		
		if (year != null) {
			stringBuffer.append(year);
		} else {
			stringBuffer.append("0001");
		}

		if (month != null) {
			if (month <10) {
				stringBuffer.append("0");
			}
			stringBuffer.append(month);
		} else {
			stringBuffer.append("01");
		}
		
		if (day != null) {
			if (day<10) {
				stringBuffer.append("0");
			}
			stringBuffer.append(day);
		} else {
			stringBuffer.append("01");
		}
		
		return NumberUtils.toInt(stringBuffer.toString());
	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Integer getLuceneDate(Integer year, Month month, Integer day) {
		StringBuffer stringBuffer = new StringBuffer("");
		
		if (year != null) {
			stringBuffer.append(year);
		} else {
			stringBuffer.append("0001");
		}

		if (!ObjectUtils.toString(month).equals("")) {
			if (month.getMonthNum()<10) {
				stringBuffer.append("0");
			}
			stringBuffer.append(month.getMonthNum());
		} else {
			stringBuffer.append("01");
		}
		
		if (day != null) {
			if (day<10) {
				stringBuffer.append("0");
			}
			stringBuffer.append(day);
		} else {
			stringBuffer.append("01");
		}
		
		return NumberUtils.toInt(stringBuffer.toString());
	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getStringDate(Integer year, Month month, Integer day) {
		StringBuffer returnValue = new StringBuffer("");
		
		if (year != null) {
			returnValue.append(year);
		}

		if (!ObjectUtils.toString(month).equals("")) {
			if (returnValue.length() > 0 ) {
				returnValue.append(" ");
			}
			returnValue.append(month);
		}

		if (day != null) {
			if (returnValue.length() > 0 ) {
				returnValue.append(" ");
			}
			returnValue.append(day);
		}
		
		return returnValue.toString();
	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getStringDateHTMLForTable(Integer year, Month month, Integer day) {
		StringBuffer returnValue = new StringBuffer("");
		
		if (year != null) {
			returnValue.append(year);
		}

		if (!ObjectUtils.toString(month).equals("")) {
			if (returnValue.length() > 0 ) {
				returnValue.append("<br />");
			}
			returnValue.append(month);
		}

		if (day != null) {
			if (returnValue.length() > 0 ) {
				returnValue.append("<br />");
			}
			returnValue.append(day);
		}
		
		return returnValue.toString();
	}

}
