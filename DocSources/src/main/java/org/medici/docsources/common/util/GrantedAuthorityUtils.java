/*
 * GrantedAuthorityUtils.java
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

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * Utility class to work on GrantedAuthority object (spring security).
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class GrantedAuthorityUtils {

	/**
	 * This method convert an array of GrantedAuthority in a string
	 * rappresentation.
	 * 
	 * @param grantedAuthorities List of GrantedAuthority object to convert
	 * @return String rappresentation of input parameter
	 * 
	 * @see org.springframework.security.GrantedAuthority
	 **/
	public static String toString(GrantedAuthority[] grantedAuthorities) {
		StringBuffer stringBuffer = new StringBuffer("[");
		for (int i = 0; i < grantedAuthorities.length; i++) {
			stringBuffer.append(grantedAuthorities[i].getAuthority());
			stringBuffer.append(",");
		}
		stringBuffer.replace(stringBuffer.length() - 1, stringBuffer.length(),
		"]");
		return stringBuffer.toString();
	}
	
	/**
	 * This method convert an array of GrantedAuthority in a string
	 * rappresentation.
	 * 
	 * @param grantedAuthorities List of GrantedAuthority object to convert
	 * @return String rappresentation of input parameter
	 * 
	 * @see org.springframework.security.GrantedAuthority
	 **/
	public static String toString(Collection<GrantedAuthority> grantedAuthorities) {
		StringBuffer stringBuffer = new StringBuffer("[");
		for (GrantedAuthority currentGrantedAutorithy : grantedAuthorities) {
			stringBuffer.append(currentGrantedAutorithy);
			stringBuffer.append(",");
		}
		stringBuffer.replace(stringBuffer.length() - 1, stringBuffer.length(),
		"]");
		return stringBuffer.toString();
	}
}
