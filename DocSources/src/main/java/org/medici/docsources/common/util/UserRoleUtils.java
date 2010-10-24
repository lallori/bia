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
package org.medici.docsources.common.util;

import java.util.List;
import org.apache.commons.lang.WordUtils;
import org.medici.docsources.domain.User.UserRole;

/**
 * Utility Class for {@link org.medici.docsources.domain.User$UserRole}, it 
 * contains some methods helpful in management user role.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class UserRoleUtils {
	/**
	 * Give an email address in input it checks the correct email format.
	 * 
	 * @param input Email address to check.
	 * @return true if the input parameter is in email format, false otherwise.
	 */
	public static String toDescriptionString(UserRole userRole) {
		if (userRole == null)
			return "";

		String descriptionString =  WordUtils.capitalize(userRole.toString().replaceAll("[_]", " ").toLowerCase());
		
		if (descriptionString.endsWith("s")) {
			descriptionString = descriptionString.substring(0, descriptionString.length()-1); 
		}
		
		return descriptionString;
	}

	/**
	 * This method will return the most significant role present in input
	 * parameter userRoles.
	 * 
	 * @param userRoles Input parameter rapresenting the user's roles.
	 * @return 
	 */
	public static UserRole getMostSignificantRole(List<UserRole> userRoles){
		if (userRoles == null)
			return null;

		if (userRoles.contains(UserRole.ADMINISTRATORS))
			return UserRole.ADMINISTRATORS;
		else if (userRoles.contains(UserRole.ONSITE_FELLOWS))
			return UserRole.ONSITE_FELLOWS;
		else if (userRoles.contains(UserRole.SENIOR_DISTANCE_FELLOWS))
			return UserRole.SENIOR_DISTANCE_FELLOWS;
		else if (userRoles.contains(UserRole.DISTANCE_FELLOWS))
			return UserRole.DISTANCE_FELLOWS;
		else if (userRoles.contains(UserRole.DIGITIZATION_TECHNICIANS))
			return UserRole.DIGITIZATION_TECHNICIANS;
		else if (userRoles.contains(UserRole.COMMUNITY_USERS))
			return UserRole.COMMUNITY_USERS;
		else
			return UserRole.GUESTS;
	}
	/**
	 * Main method to test static methods.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(UserRole.SENIOR_DISTANCE_FELLOWS + " : " + UserRoleUtils.toDescriptionString(UserRole.SENIOR_DISTANCE_FELLOWS));
		System.out.println(UserRole.ADMINISTRATORS + " : " + UserRoleUtils.toDescriptionString(UserRole.ADMINISTRATORS));
		System.out.println(UserRole.DIGITIZATION_TECHNICIANS + " : " + UserRoleUtils.toDescriptionString(UserRole.DIGITIZATION_TECHNICIANS));
	}
}
