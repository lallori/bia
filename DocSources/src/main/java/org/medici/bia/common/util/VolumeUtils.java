/*
 * VolumeUtils.java
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

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class VolumeUtils {

	/**
	 * 
	 * @param volume
	 * @return
	 */
	public static String extractVolLetExt(String volume) {
		if (StringUtils.isEmpty(volume)) {
			return null;
		}
		
		String volumeToExtract = volume.trim();

		if (StringUtils.isNumeric(volumeToExtract)){
			return null;
		} else {
			if (StringUtils.isAlphanumeric(volumeToExtract)) {
				return volumeToExtract.substring(volumeToExtract.length()-1);
			}
			
			return null;
		}
	}

	/**
	 * This method extract volNum from a complete volume string.
	 * @param volume
	 * @return
	 */
	public static Integer extractVolNum(String volume) {
		if (StringUtils.isEmpty(volume)) {
			return null;
		}
		
		String volumeToExtract = volume.trim();

		if (StringUtils.isNumeric(volumeToExtract)){
			try {
				return new Integer(volumeToExtract);
			} catch (NumberFormatException nfx){
				return null;
			}
		} else {
			if (StringUtils.isAlphanumeric(volumeToExtract)) {
				try {
					return new Integer(volumeToExtract.substring(0, volumeToExtract.length()-1));
				} catch (NumberFormatException nfx){
					return null;
				}
			}
			
			return null;
		}
	}

	/**
	 * This method return a string in format volNum + volLetExt.
	 * 
	 * @param volNum Volume Number
	 * @param volLetExt Volume Letter Extension
	 * @return
	 */
	public static String toMDPFormat(Integer volNum, String volLetExt) {
		String returnValue = ObjectUtils.toString(volNum);

		if (!StringUtils.isEmpty(volLetExt)) {
			returnValue += volLetExt;
		}

		return returnValue;
	}
	/**
	 * This method check if a string is in volume Format
	 * 
	 * @param text
	 * @return
	 */
	public static Boolean isVolumeFormat(String text) {
		if (StringUtils.isEmpty(text)) {
			return Boolean.FALSE;
		}
		
		String trimmedText = text.trim();

		/**We put this control here because often a word for search is not a volume
		 * E.g. Michele (it's not a volume)
		 * E.g. a  (word with a single letter cannot be consider as volume Letter Extension),
		 * 
		 * Previously we have two check :
		 * if (StringUtils.isAlpha(text) && text.length() > 1)
		 * if (StringUtils.isAlpha(trimmedText) && trimmedText.length() == 1)
		 **/
		if (StringUtils.isAlpha(trimmedText)) {
			return Boolean.FALSE;
		}

		// E.g. 23
		if (StringUtils.isNumeric(trimmedText)) {
			return Boolean.TRUE;
		}

		// E.g. 23a
		if (StringUtils.isAlphanumeric(trimmedText)) {
			// A correct volumeNumber E.g. 23a
			if (NumberUtils.isNumber(trimmedText.substring(0, trimmedText.length()-1))) {
				return Boolean.TRUE;	
			} else {
				// A correct volumeNumber E.g. 23da, volume Letter must be a single letter 
				return Boolean.FALSE;	
			}
		}

		// It 's not a volume format string
		return Boolean.FALSE;
	}
}