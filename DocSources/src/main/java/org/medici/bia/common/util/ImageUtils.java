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
package org.medici.bia.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * Image name contains some informations, some of them are mandatory while others are optional.
 * Every single information starts with an underscore &quot;<code>_</code>&quot;, only the first one do not follow this rule 
 * (<code>imageOrder</code>).<br>
 * This is the image name specification:<br><br>
 * 
 * <code>{imgageOrder}(_insert)_{type}_{folioNumber}(_folioExtension)_{recto/verso}.ext</code><br><br>
 * 
 * where the braces mean mandatory informations and the parenthesis mean optional informations.
 * 
 * <ul>
 * <li><b>imageOrder</b> is the progressive number of the image --> linked to <i>tblImages.imageOrder</i> field</li>
 * <li><b>insert</b> contains the insert's informations</li>
 * <li><b>type</b> is the type of the image document -&gt; linked to <i>tblImages.imageType</i> field</li>
 * <li><b>folioNumber</b> is the number of the folio -&gt; linked to <i>tblImages.imageProgTypeNum</i> field</li>
 * <li><b>folioExtension</b> is the extended information of the folio -&gt; linked to <i>tblImages.missedNumbering</i> field</li>
 * <li><b>recto/verso</b> contains the recto (R) or verso (V) information -&gt; lined to <i>tblImages.imageRectoVerso</i> field</li>
 * <li><b>.ext</b> is the extension of the file</li>
 * </ul>
 * 
 * Furthermore the insert's informations are written between brackets and have the following format:<br><br>
 * <code>[{insertNumber}(-insertExtension)]</code><br><br>
 * where
 * <ul>
 * <li><b>insertNumber</b> is the number of the insert -&gt; linked to <i>tblImages.insertNum</i> field</li>
 * <li><b>insertExtension</b> is the extended information of the insert -&gt; linked to <i>tblImages.insertLet</i> field</li>
 * </ul>
 * 
 * These are some images name examples:
 * <ul>
 * <li><i>standard name</i> -&gt; <code>0594_C_416_R.tif</code></li>
 * <li><i>folio with extension (extended folio)</i> -&gt; <code>0586_C_410_BIS_V.tif</code></li>
 * <li><i>insert</i> -&gt; <code>0422_[1]_C_175_R.tif</code></li>
 * <li><i>insert with extension (extended insert)</i> -&gt; <code>0426_[A-13]_C_179_R.tif</code></li>
 * <li><i>insert with extended folio</i> -&gt; <code>0426_[A]_C_179_BIS_R.tif</code></li>
 * <li><i>extended insert with extended folio</i> -&gt; <code>0425_[A-12]_C_178_BIS_R.tif</code></li>
 * </ul>
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 */
public class ImageUtils {
	private static Logger logger = Logger.getLogger(ImageUtils.class);

	/**
	 * Extract the folio number from the image file name
	 * @param fileName the image file name
	 * @return the folio number
	 */
	public static Integer extractFolioNumber(String fileName) {
		String folioNumber = fileName.trim();
		if (StringUtils.isEmpty(folioNumber)) {
			return null;
		}
		
		boolean insert = StringUtils.contains(folioNumber,"[");
		
		int beforeIdx = indexOfOccurrence(folioNumber, "_", insert ? 2 : 1);
		int afterIdx = indexOfOccurrence(folioNumber, "_", insert ? 3 : 2);
		if (beforeIdx == -1 || afterIdx == -1) {
			logger.debug("Unable to find folio number. Image file " + fileName);
			return null;
		}
		folioNumber = folioNumber.substring(beforeIdx + 1, afterIdx);
		
		if (StringUtils.isNumeric(folioNumber)){
			try {
				return new Integer(folioNumber);
			} catch (NumberFormatException numberFormatException){
				logger.debug("Unable to convert folio number. Image file " + fileName, numberFormatException);
				return null;
			}
		} else {
			if (StringUtils.isAlphanumeric(folioNumber)) {
				try {
					return new Integer(folioNumber.substring(0, folioNumber.length()-1));
				} catch (NumberFormatException numberFormatException){
					logger.error("Unable to convert folio number. Image file " + fileName, numberFormatException);
					return null;
				}
			}
			
			return null;
		}
	}

	/**
	 * Extract the folio extension from the image file name
	 * @param fileName the image file name
	 * @return the folio extension
	 */
	public static String extractFolioExtension(String fileName) {
		String folio = fileName.trim();
		if (StringUtils.isEmpty(folio)) {
			return null;
		}
		
		boolean insert = StringUtils.contains(folio,"[");
		int beforeIdx = indexOfOccurrence(folio, "_", insert ? 3 : 2);
		int afterIdx = indexOfOccurrence(folio, "_", insert ? 4 : 3);
		if (afterIdx == -1 || beforeIdx == -1) {
			logger.debug("Unable to find folio extension. Image file " + fileName);
			return null;
		}

		return folio.substring(beforeIdx + 1, afterIdx);
	}

	/**
	 * This method will format an input id for document identifier.
	 * 
	 * @param folioNum
	 * @param folioMod
	 * @return
	 */
	public static Object formatFolioNumber(Integer folioNum, String folioMod) {
		String value = "00" + folioNum.toString();
		
		return value.substring(value.length()-3);
	}
	
	/**
	 * Returns the <i>n-th</i> occurrence of the searched string in the provided string
	 * @param str provided string
	 * @param searchStr searched string
	 * @param idx index of the occurrence (0 for the first, 1 for the second...)
	 * @return index of the <i>n-th</i> occurrence
	 */
	private static int indexOfOccurrence(String str, String searchStr, int idx) {
		int res = -1;
		for (int i = 0; i < idx + 1; i++) {
			res = str.indexOf(searchStr, res+1);
			if (res == -1)
				break;
		}
		return res;
	}
}