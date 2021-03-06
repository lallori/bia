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
package org.medici.bia.common.util;

import java.util.Collection;
import java.util.List;

import org.medici.bia.domain.UserAuthority.Authority;
import org.springframework.security.core.GrantedAuthority;

/**
 * Utility class to work on GrantedAuthority object (spring security).
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class GrantedAuthorityUtils {

	/**
	 * 
	 * @param authority
	 * @return
	 */
	public static Boolean isGranted(Authority authority, List<String> userAuthorities) {
		for (String userAuthority : userAuthorities) {
			if (userAuthority.equals(authority.toString())) {
				return Boolean.TRUE;
			}
		}

		return Boolean.FALSE;
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
		StringBuilder stringBuilder = new StringBuilder("[");
		for (GrantedAuthority currentGrantedAutorithy : grantedAuthorities) {
			stringBuilder.append(currentGrantedAutorithy);
			stringBuilder.append(',');
		}
		stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(),
		"]");
		return stringBuilder.toString();
	}
}
