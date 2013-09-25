/*
 * StringUtils.java
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

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class StringUtils {
	
	/**
	 * It returns trimmed string. If the string is empty or contains only white spaces it returns null.
	 * 
	 * @param s the string that has to be trimmed
	 * @return the trimmed string
	 */
	public static String nullTrim(String s) {
		if (s == null || "".equals(s.trim()))
			return null;
		return s.trim();
	}
	
	/**
	 * It returns true if the provided string is null, empty or it only contains white spaces.
	 * 
	 * @param s the checked string
	 * @return true if the string is null, empty or it only contains white spaces, false otherwise.
	 */
	public static Boolean isNullableString(String s) {
		return s == null || "".equals(s.trim());
	}
	
}
