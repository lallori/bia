/*
 * LdapUtils.java
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
import org.apache.log4j.Logger;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class ImageUtils {
	private static Logger logger = Logger.getLogger(ImageUtils.class);

	/**
	 * 
	 * @param ldapConfiguration
	 * @param account
	 * @return
	 */
	public static Integer extractFolioNumber(String fileName) {
		String folioNumber = fileName.trim();
		if (StringUtils.isEmpty(folioNumber)) {
			return null;
		}
		
		folioNumber = StringUtils.substringBetween(folioNumber, "_C_", "_");
		if (StringUtils.isNumeric(folioNumber)){
			try {
				return new Integer(folioNumber);
			} catch (NumberFormatException nfx){
				logger.error("Unable to convert folio number. Image file " + fileName);
				return null;
			}
		} else {
			if (StringUtils.isAlphanumeric(folioNumber)) {
				try {
					return new Integer(folioNumber.substring(0, folioNumber.length()-1));
				} catch (NumberFormatException nfx){
					logger.error("Unable to convert folio number. Image file " + fileName);
					return null;
				}
			}
			
			return null;
		}
	}

	/**
	 * 
	 * @param volume
	 * @return
	 */
	public static String extractFolioExtension(String fileName) {
		String folio = fileName.trim();
		if (StringUtils.isEmpty(folio)) {
			return null;
		}
		if (StringUtils.countMatches(folio, "_") != 4) {
			return null;
		}

		// If we have 4 underscore, we have mod information :
		return StringUtils.substringBetween(StringUtils.substringBetween(folio, "_C_", ".tif"), "_", "_");
	}

	/**
	 * This method will format an input id for document identifier.
	 * 
	 * @param entryId
	 * @return
	 */
	public static Object formatFolioNumber(Integer folioNum, String folioMod) {
		String value = "00" + folioNum.toString();
		
		return value.substring(value.length()-3);
	}
}