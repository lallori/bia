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

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class VolumeUtils {

	/**
	 * 
	 * @param ldapConfiguration
	 * @param account
	 * @return
	 */
	public static Integer extractVolNum(String volume) {
		String volumeToExtract = volume.trim();
		if (StringUtils.isEmpty(volumeToExtract)) {
			return null;
		}
		
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

	public static String extractVolLetExt(String volume) {
		String volumeToExtract = volume.trim();
		if (StringUtils.isEmpty(volumeToExtract)) {
			return null;
		}
		
		if (StringUtils.isNumeric(volumeToExtract)){
			return null;
		} else {
			if (StringUtils.isAlphanumeric(volumeToExtract)) {
				return volumeToExtract.substring(volumeToExtract.length()-1);
			}
			
			return null;
		}
	}
}