/*
 * SimpleSearchUtil.java
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class SimpleSearchUtils {
	/**
	 * 
	 * @param dayFields
	 * @param words
	 * @return
	 */
	public static StringBuffer constructConditionOnDayFields(String[] dayFields, String[] words) {
		StringBuffer stringBuffer = new StringBuffer();
		// We add conditions on numeric fields only for input word which are numbers
		for (int i=0; i<words.length; i++) {
			if (!NumberUtils.isNumber(words[i])) {
				continue;
			}
			Integer number = NumberUtils.toInt(words[i]);
			
			if ((number<1) || (number>31)) {
				continue;
			}
			
			for (int j=0; j<dayFields.length; j++) {
				stringBuffer.append("(");
				stringBuffer.append(dayFields[j]);
				stringBuffer.append(": ");
				stringBuffer.append(words[i]);
				stringBuffer.append(") ");
			}
		}
		
		return stringBuffer;
	}

	/**
	 * 
	 * @param monthFields
	 * @param words
	 * @return
	 */
	public static StringBuffer constructConditionOnMonthFields(String[] monthFields, String[] words) {
		StringBuffer stringBuffer = new StringBuffer();
		// We add conditions on month fields only for input word which are numbers
		for (int i=0; i<words.length; i++) {
			if (!NumberUtils.isNumber(words[i])) {
				continue;
			}
			Integer number = NumberUtils.toInt(words[i]);
			
			if ((number<1) || (number>12)) {
				continue;
			}
			
			for (int j=0; j<monthFields.length; j++) {
				stringBuffer.append("(");
				stringBuffer.append(monthFields[j]);
				stringBuffer.append(": ");
				stringBuffer.append(words[i]);
				stringBuffer.append(") ");
			}
		}
		
		return stringBuffer;
	}

	/**
	 * 
	 * @param numericFields
	 * @param words
	 * @return
	 */
	public static StringBuffer constructConditionOnNumericFields(String[] numericFields, String[] words) {
		StringBuffer stringBuffer = new StringBuffer();
		// We add conditions on numeric fields only for input word which are numbers
		for (int i=0; i<words.length; i++) {
			if (!NumberUtils.isNumber(words[i])) {
				continue;
			}
			
			for (int j=0; j<numericFields.length; j++) {
				stringBuffer.append("(");
				stringBuffer.append(numericFields[j]);
				stringBuffer.append(": ");
				stringBuffer.append(words[i]);
				stringBuffer.append(") ");
			}
		}
		
		return stringBuffer;
	}

	/**
	 * 
	 * @param stringFields
	 * @param words
	 * @return
	 */
	public static StringBuffer constructConditionOnStringFields(String[] stringFields, String[] words) {
		StringBuffer stringBuffer = new StringBuffer();
		// We add conditions on string fields
		for (int i=0; i<stringFields.length; i++) {
			// volume.serieList.title
			stringBuffer.append("(");
			stringBuffer.append(stringFields[i]);
			stringBuffer.append(": (");
			for (int j=0; j<words.length; j++) {
				stringBuffer.append("+");
				stringBuffer.append( words[j]);
				stringBuffer.append(" ");
			}
			stringBuffer.append(")) ");
		}
		
		return stringBuffer;
	}

	/**
	 * 
	 * @param volumeFields
	 * @param words
	 * @return
	 */
	public static StringBuffer constructConditionOnVolumeFields(String[] volumeFields, String[] words) {
		StringBuffer stringBuffer = new StringBuffer();
		// We add conditions on volume 
		for (int i=0; i<words.length; i++) {
			// if word is not in volume format we skip
			if (!VolumeUtils.isVolumeFormat(words[i])) {
				continue;
			}
			// if word contains volLetExt we manage with a specific condition
			if (StringUtils.isAlphanumeric(words[i])) {
				stringBuffer.append("(volume.volNum:");
				stringBuffer.append(VolumeUtils.extractVolNum(words[i]));
				stringBuffer.append(") ");
			} else {
				stringBuffer.append("(+(volume.volNum:");
				stringBuffer.append(VolumeUtils.extractVolNum(words[i]));
				stringBuffer.append(") +(volume.volLetExt:");
				stringBuffer.append(VolumeUtils.extractVolLetExt(words[i]));
				stringBuffer.append("))");
			}
		}
		
		return stringBuffer;
	}

	/**
	 * 
	 * @param yearFields
	 * @param words
	 * @return
	 */
	public static StringBuffer constructConditionOnYearFields(String[] yearFields, String[] words) {
		StringBuffer stringBuffer = new StringBuffer();
		// We add conditions on numeric fields only for input word which are numbers
		for (int i=0; i<words.length; i++) {
			if (!NumberUtils.isNumber(words[i])) {
				continue;
			}
			Integer number = NumberUtils.toInt(words[i]);
			
			if ((number<1200) || (number>1800)) {
				continue;
			}
			
			for (int j=0; j<yearFields.length; j++) {
				stringBuffer.append("(");
				stringBuffer.append(yearFields[j]);
				stringBuffer.append(": ");
				stringBuffer.append(words[i]);
				stringBuffer.append(") ");
			}
		}
		
		return stringBuffer;
	}
}
