/*
 * DocSourcesLdapUserDetailsMapper.java
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
package org.medici.docsources.security;

import java.util.Collection;
import java.util.Enumeration;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

/**
 * This class extends LdapUserDetailsMapper to provide full support to our LDAP
 * server.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class DocSourcesLdapUserDetailsMapper extends LdapUserDetailsMapper {

	/**
	 * 
	 * @param ctx
	 *            ,
	 * @param username
	 * @param authorities
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx,String username, Collection<GrantedAuthority> authorities) {
		UserDetails ud = super.mapUserFromContext(ctx, username, authorities);
		LdapUserDetails ldapUserDetails = (LdapUserDetailsImpl) ud;
		DirContextAdapter dca = (DirContextAdapter) ctx;
		Collection<GrantedAuthority> ldapUserGrantedAuthority = ldapUserDetails.getAuthorities();
		Enumeration e = dca.getAttributes().getAll();
		while (e.hasMoreElements()) {
			ldapUserGrantedAuthority.add((GrantedAuthority) e.nextElement());
		}
		return ud;
	}

}
