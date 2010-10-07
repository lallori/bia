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

import javax.naming.Name;

import org.medici.docsources.security.LdapConfiguration;
import org.springframework.ldap.core.DistinguishedName;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class LdapUtils {

	/**
	 * 
	 * @param ldapConfiguration
	 * @param account
	 * @return
	 */
	public static Name fullUserDistinguishedName(LdapConfiguration ldapConfiguration, String account) {
		if ((ldapConfiguration == null) || (account == null))
			return new DistinguishedName("");

		StringBuffer fullUserDistinguishedName = new StringBuffer(ldapConfiguration.getUserAttribute());
		fullUserDistinguishedName.append("=");
		fullUserDistinguishedName.append(account);
		fullUserDistinguishedName.append(",");
		fullUserDistinguishedName.append(ldapConfiguration.getUsersDN());
		fullUserDistinguishedName.append(",");
		fullUserDistinguishedName.append(ldapConfiguration.getBaseDN());

		return new DistinguishedName(fullUserDistinguishedName.toString());
	}

	/**
	 * 
	 * @param ldapConfiguration
	 * @param role
	 * @return
	 */
	public static Name fullUserRoleDistinguishedName(LdapConfiguration ldapConfiguration, String role) {
		if ((ldapConfiguration == null) || (role == null))
			return new DistinguishedName("");

		StringBuffer fullUserRoleDistinguishedName = new StringBuffer(
				ldapConfiguration.getRoleAttribute());
		fullUserRoleDistinguishedName.append("=");
		fullUserRoleDistinguishedName.append(role);
		fullUserRoleDistinguishedName.append(",");
		fullUserRoleDistinguishedName.append(ldapConfiguration.getUsersDN());
		fullUserRoleDistinguishedName.append(",");
		fullUserRoleDistinguishedName.append(ldapConfiguration.getBaseDN());

		return new DistinguishedName(fullUserRoleDistinguishedName.toString());
	}

	/**
	 * 
	 * @param ldapConfiguration
	 * @param account
	 * @return
	 */
	public static Name userDistinguishedName(LdapConfiguration ldapConfiguration, String account) {
		if ((ldapConfiguration == null) || (account == null))
			return new DistinguishedName("");

		StringBuffer userDistinguishedName = new StringBuffer(ldapConfiguration.getUserAttribute());
		userDistinguishedName.append("=");
		userDistinguishedName.append(account);
		userDistinguishedName.append(",");
		userDistinguishedName.append(ldapConfiguration.getUsersDN());

		return new DistinguishedName(userDistinguishedName.toString());
	}

	/**
	 * 
	 * @param ldapConfiguration
	 * @param role
	 * @return
	 */
	public static Name userRoleDistinguishedName(LdapConfiguration ldapConfiguration, String role) {
		if ((ldapConfiguration == null) || (role == null))
			return new DistinguishedName("");

		StringBuffer userRoleDistinguishedName = new StringBuffer(
				ldapConfiguration.getRoleAttribute());
		userRoleDistinguishedName.append("=");
		userRoleDistinguishedName.append(role);
		userRoleDistinguishedName.append(",");
		userRoleDistinguishedName.append(ldapConfiguration.getUsersDN());

		return new DistinguishedName(userRoleDistinguishedName.toString());
	}
}