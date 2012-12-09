/*
 * UserRoleUtils.java
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

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.WordUtils;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserRole;

/**
 * Utility Class for {@link org.medici.bia.domain.User$UserAuthority}, it 
 * contains some methods helpful in management user role.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class UserRoleUtils {
	/**
	 * This method will return the most significant role present in input
	 * parameter userRoles.
	 * 
	 * @param userRoles Input parameter rapresenting the user's roles.
	 * @return 
	 */
	public static UserRole getMostSignificantRole(List<UserRole> userRoles){
		if (userRoles == null) {
			return null;
		}

		UserRole mostSignificantRole = null;
		
		for (UserRole currentRole : userRoles) {
			if (currentRole.containsAuthority(UserAuthority.Authority.ADMINISTRATORS)) {
				mostSignificantRole = currentRole;
				break;
			} else if (currentRole.containsAuthority(UserAuthority.Authority.ONSITE_FELLOWS)) {
				mostSignificantRole = currentRole;
				break;
			} else if (currentRole.containsAuthority(UserAuthority.Authority.SENIOR_DISTANCE_FELLOWS)) {
				mostSignificantRole = currentRole;
				break;
			} else if (currentRole.containsAuthority(UserAuthority.Authority.DISTANCE_FELLOWS)) {
				mostSignificantRole = currentRole;
				break;
			} else if (currentRole.containsAuthority(UserAuthority.Authority.DIGITIZATION_TECHNICIANS)) {
				mostSignificantRole = currentRole;
				break;
			} else if (currentRole.containsAuthority(UserAuthority.Authority.COMMUNITY_USERS)) {
				mostSignificantRole = currentRole;
				break;
			} else {
				mostSignificantRole = currentRole;
				break;
			}
		}
		
		return mostSignificantRole;
	}

	/**
	 * Give an email address in input it checks the correct email format.
	 * 
	 * @param input Email address to check.
	 * @return true if the input parameter is in email format, false otherwise.
	 */
	public static String toDescriptionString(UserRole userRole) {
		if (userRole == null) {
			return "";
		}

		String descriptionString =  WordUtils.capitalize(userRole.toString().replaceAll("[_]", " ").toLowerCase());
		
		if (descriptionString.endsWith("s")) {
			descriptionString = descriptionString.substring(0, descriptionString.length()-1); 
		}
		
		return descriptionString;
	}

	/**
	 * 
	 * @param userRoles
	 * @return
	 */
	public static String toString(List<UserRole> userRoles) {
		StringBuilder stringBuilder = new StringBuilder("[");
		for (UserRole userRole : userRoles) {
			stringBuilder.append(userRole.getUserAuthority().toString());
			stringBuilder.append(',');
		}
		stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "]");

		return stringBuilder.toString();
	}
}
