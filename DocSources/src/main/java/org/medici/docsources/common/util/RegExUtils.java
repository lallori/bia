/*
 * RegExUtils.java
 * 
 * Developed by Medici Archive Project (2010-2012).
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Utility Class for regular expression, it contains some methods helpful in
 * development class that use regular expression language.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public class RegExUtils {
	private static Logger logger = Logger.getLogger(RegExUtils.class.getName());

	/**
	 * Give an email address in input it checks the correct email format.
	 * 
	 * @param input Email address to check.
	 * @return true if the input parameter is in email format, false otherwise.
	 */
	public static Boolean checkMail(String mail) {
		String regExEmail = "^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$";

		return searchPattern(regExEmail, mail);
	}

	/**
	 * This method returns the input string without any not unicode letters.
	 * 
	 * @param inputString The input string to be clean.
	 * @return The input string with only unicode letters.
	 */
	public static String trimNonAlphaChars(String inputString) {
		return inputString.replaceAll("[^a-zA-Z]", "");
	}

	/**
	 * This method returns string array splitted by any punctuation chars and blank space.
	 * 
	 * @param inputString The input string to be clean.
	 * @return The input string without punctuation.
	 */
	public static String[] splitPunctuationAndSpaceChars(String inputString) {
		if (StringUtils.isEmpty(inputString)) {
			return new String[0];
		}

		String[] retValue = inputString.split("([.,!?:;'\"-]|\\s)+");
		return retValue;
	}
	
	/**
	 * This method returns string array splitted by any punctuation chars.
	 * 
	 * @param inputString The input string to be clean.
	 * @return The input string without punctuation.
	 */
	public static String[] splitPunctuationChars(String inputString) {
		//return inputString.split("[\\p{P},\\s]");
		String[] retValue = inputString.split("([.,!?:;'\"-])+");
		
		return retValue;
	}

	/**
	 * Generic simple method to search a regular expression pattern in a string.
	 * 
	 * @param regex Regular expression to search
	 * @param inputString String where search the regular expression.
	 * @return true if the inputString contains regular expression, false otherwise.
	 */
	private static boolean searchPattern(String regex, String inputString) {
		Pattern pattern = null;
		try {
			pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		} catch (Exception ex) {
			logger.error("Espressione regolare non valida.", ex);
			return false;
		}
		Matcher matcher = pattern.matcher(inputString);

		return matcher.matches();
	}
}
