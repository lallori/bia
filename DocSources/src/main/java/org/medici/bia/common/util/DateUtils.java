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
package org.medici.bia.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.medici.bia.domain.Month;
import org.medici.bia.security.BiaUserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class DateUtils {
	public static Logger logger = Logger.getLogger(DateUtils.class);
	public static Integer MAX_DATE = 20121221;

	public static Integer MIN_DATE = 10101;
	/**
	 * 
	 * @param nextToken
	 * @return
	 */
	public static Integer getDateDayFromString(String nextToken) {
		if (nextToken == null) {
			return null;
		}
		if (nextToken.equals("")) {
			return null;
		}
		
		return NumberUtils.createInteger(nextToken);
	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getDateForSQLQuery(Integer year, Integer month, Integer day){
		StringBuilder stringBuilder = new StringBuilder("");
		StringBuilder dateFormat = new StringBuilder("");
		stringBuilder.append("STR_TO_DATE('");
		if(year != null){
			if(year >= 1000){
				stringBuilder.append(year);
				dateFormat.append("%Y");
			}else if(year >= 100){
				stringBuilder.append("0" + year);
				dateFormat.append("%Y");
			}else if(year < 100){
				stringBuilder.append("00" + year);
				dateFormat.append("%Y");
			}
		}
		if(month != null){
			if(stringBuilder.length() > 13){
				stringBuilder.append(',');
				dateFormat.append(',');
			}
			stringBuilder.append(month);
			dateFormat.append("%m");
		}
		if(day != null){
			if(stringBuilder.length() > 13){
				stringBuilder.append(',');
				dateFormat.append(',');
			}
			stringBuilder.append(day);
			dateFormat.append("%d");
		}
		stringBuilder.append("','");
		stringBuilder.append(dateFormat);
		stringBuilder.append("')");
		
		return stringBuilder.toString();
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public static Date getDateFromString(String dateString) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		
		try {
			return simpleDateFormat.parse(dateString);
		} catch (ParseException parseException) {
			logger.error(parseException);
			Calendar calendar = Calendar.getInstance();
			calendar.set(0, 0, 0);
			return calendar.getTime();
		}
	}

	/**
	 * 
	 * @param nextToken
	 * @return
	 */
	public static Integer getDateMonthFromString(String nextToken) {
		if (nextToken == null) {
			return null;
		}
		if (nextToken.equals("")) {
			return null;
		}
		
		return NumberUtils.createInteger(nextToken);
	}
	
	/**
	 * 
	 * @param nextToken
	 * @return
	 */
	public static Integer getDateYearFromString(String nextToken) {
		if (nextToken == null) {
			return null;
		}

		if (nextToken.equals("")) {
			return null;
		}
		
		return NumberUtils.createInteger(nextToken);
	}
	
	/**
	 * 
	 * @return
	 */
	public static Date getFirstDayOfCurrentMonth() {
	    Calendar nowCal = Calendar.getInstance();
	    int month = nowCal.get(Calendar.MONTH);
	    int year = nowCal.get(Calendar.YEAR);

	    Calendar calendar = Calendar.getInstance();
	    calendar.clear();
	    calendar.set(Calendar.YEAR, year);
	    calendar.set(Calendar.MONTH, month);
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    return new Date(calendar.getTimeInMillis());
	}

	/**
	 * 
	 * @return
	 */
	public static Date getFirstDayOfCurrentWeek() {
		// Get calendar set to current date and time
		Calendar calendar = Calendar.getInstance();
	
		// Set the calendar to monday of the current week
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		return new Date(calendar.getTimeInMillis());
	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Integer getIntegerDate(Integer year, Integer month, Integer day) {
		StringBuilder stringBuilder = new StringBuilder("");
		
		if (year != null) {
			stringBuilder.append(year);
		} else {
			stringBuilder.append("0000");
		}

		if (!ObjectUtils.toString(month).equals("")) {
			if (month<10) {
				stringBuilder.append('0');
			}
			stringBuilder.append(month);
		} else {
			stringBuilder.append("00");
		}
		
		if (day != null) {
			if (day<10) {
				stringBuilder.append('0');
			}
			stringBuilder.append(day);
		} else {
			stringBuilder.append("00");
		}
		
		return NumberUtils.toInt(stringBuilder.toString());
	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Integer getIntegerDate(Integer year, Month month, Integer day) {
		StringBuilder stringBuilder = new StringBuilder("");
		
		if (year != null) {
			stringBuilder.append(year);
		} else {
			stringBuilder.append("0000");
		}

		if (!ObjectUtils.toString(month).equals("")) {
			if (month.getMonthNum()<10) {
				stringBuilder.append('0');
			}
			stringBuilder.append(month.getMonthNum());
		} else {
			stringBuilder.append("00");
		}
		
		if (day != null) {
			if (day<10) {
				stringBuilder.append('0');
			}
			stringBuilder.append(day);
		} else {
			stringBuilder.append("00");
		}
		
		return NumberUtils.toInt(stringBuilder.toString());
	}

	/**
	 * 
	 * @return
	 */
	public static Date getLastLogonDate() {
		try {
			Date lastLoginDate = ((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getLastLoginDate();
			
			if (lastLoginDate != null) {
				return lastLoginDate;
			} else {
				return ((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCurrentLoginDate();
			}
		} catch (ClassCastException classCastException) {
			logger.debug(classCastException);
			return new Date();
		}
	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Integer getLuceneDate(Integer year, Integer month, Integer day) {
		StringBuilder stringBuilder = new StringBuilder("");
		
		if (year != null) {
			stringBuilder.append(year);
		} else {
			stringBuilder.append("0001");
		}

		if (month != null) {
			if (month <10) {
				stringBuilder.append('0');
			}
			stringBuilder.append(month);
		} else {
			stringBuilder.append("01");
		}
		
		if (day != null) {
			if (day<10) {
				stringBuilder.append('0');
			}
			stringBuilder.append(day);
		} else {
			stringBuilder.append("01");
		}
		
		return NumberUtils.toInt(stringBuilder.toString());
	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Integer getLuceneDate(Integer year, Month month, Integer day) {
		StringBuilder stringBuilder = new StringBuilder("");
		
		if (year != null) {
			stringBuilder.append(year);
		} else {
			stringBuilder.append("0001");
		}

		if (!ObjectUtils.toString(month).equals("")) {
			if (month.getMonthNum()<10) {
				stringBuilder.append('0');
			}
			stringBuilder.append(month.getMonthNum());
		} else {
			stringBuilder.append("01");
		}
		
		if (day != null) {
			if (day<10) {
				stringBuilder.append('0');
			}
			stringBuilder.append(day);
		} else {
			stringBuilder.append("01");
		}
		
		return NumberUtils.toInt(stringBuilder.toString());
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
	public static String getStringDate(Integer year, Month month, Integer day) {
		StringBuilder returnValue = new StringBuilder("");
		
		if (year != null) {
			returnValue.append(year);
		}

		if (!ObjectUtils.toString(month).equals("")) {
			if (returnValue.length() > 0 ) {
				returnValue.append(' ');
			}
			returnValue.append(month);
		}

		if (day != null) {
			if (returnValue.length() > 0 ) {
				returnValue.append(' ');
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
	public static String getStringDate(Date inputDate) {
		if (inputDate == null) {
			inputDate = new Date(0);
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

		return simpleDateFormat.format(inputDate);
	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getStringDateHTMLForTable(Integer year, Month month, Integer day) {
		StringBuilder returnValue = new StringBuilder("");
		
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

	/**
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getStringForSortableDate(Integer year, Month month, Integer day) {
		StringBuilder stringBuilder = new StringBuilder("");
		
		if (year != null) {
			stringBuilder.append(year);
		} else {
			stringBuilder.append("0000");
		}

		if (!ObjectUtils.toString(month).equals("")) {
			if (month.getMonthNum()<10) {
				stringBuilder.append(" 0");
				stringBuilder.append(month.getMonthNum());
			} else {
				stringBuilder.append(' ');
				stringBuilder.append(month.getMonthNum());
			}
		} else {
			stringBuilder.append(" 00");
		}
		
		if (day != null) {
			if (day<10) {
				stringBuilder.append(" 0");
				stringBuilder.append(day);
			}else {
				stringBuilder.append(' ');
				stringBuilder.append(day);
			}
		} else {
			stringBuilder.append(" 00");
		}
		
		return stringBuilder.toString();
	}

	/**
	 * 
	 * @param datesLastUpdate
	 * @return
	 */
	public static String getMYSQLDate(Date inputDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		if (inputDate == null) {
			inputDate = Calendar.getInstance().getTime();
		}
		
		return simpleDateFormat.format(inputDate);
	}

	/**
	 * 
	 * @param datesLastUpdate
	 * @return
	 */
	public static String getMYSQLDateTime(DateTime dateTime) {
		if (dateTime == null) {
			dateTime = new DateTime();
		}

		return dateTime.toString("yyyy-MM-dd HH:mm:ss");
	}
}
