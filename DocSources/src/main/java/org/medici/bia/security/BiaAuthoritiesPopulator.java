/*
 * DocSources.java
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
package org.medici.bia.security;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;

/**
 * This class implements authorities population.
 * It extends DefaultLdapAuthoritiesPopulator and populate application authorities
 * in getAdditionalRoles(DirContextOperations user, String username). 
 *   
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class BiaAuthoritiesPopulator extends DefaultLdapAuthoritiesPopulator {

    private Boolean convertToUpperCase;
    private String rolePrefix = "ROLE_";
    private String userAttributeForRole;

    /**
     * Class constructor 
     * @param contextSource Ldap contextSource
     * @param groupSearchBase LDap Group DN containing roles definition.
     */
    public BiaAuthoritiesPopulator(ContextSource contextSource, String groupSearchBase) {
        super(contextSource, groupSearchBase);
    }

    /**
     * This method implements the logic to obtains application's roles-
     * 
     */
	@Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Set getAdditionalRoles(DirContextOperations user, String username) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        Set roleName = user.getAttributeSortedStringSet(this.getUserAttributeForRole());

        Iterator it = roleName.iterator();

        while (it.hasNext()) {
            String role = (String) it.next();
            role = role.toUpperCase();
            role = role.substring(3,role.length());
            authorities.add(new GrantedAuthorityImpl(rolePrefix + role));
        }
        return authorities;
    }

	/**
	 * @return the convertToUpperCase
	 */
	public Boolean getConvertToUpperCase() {
		return convertToUpperCase;
	}

	/**
	 * @return the rolePrefix
	 */
	public String getRolePrefix() {
		return rolePrefix;
	}

	/**
	 * @return the userAttributeForRole
	 */
	public String getUserAttributeForRole() {
		return userAttributeForRole;
	}

	/**
	 * @param convertToUpperCase the convertToUpperCase to set
	 */
	public void setConvertToUpperCase(Boolean convertToUpperCase) {
		this.convertToUpperCase = convertToUpperCase;
	}

	/**
	 * @param rolePrefix the rolePrefix to set
	 */
	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

    /**
	 * @param userAttributeForRole the userAttributeForRole to set
	 */
	public void setUserAttributeForRole(String userAttributeForRole) {
		this.userAttributeForRole = userAttributeForRole;
	}
}