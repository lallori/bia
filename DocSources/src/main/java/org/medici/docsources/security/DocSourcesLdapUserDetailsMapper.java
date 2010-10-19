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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
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
	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx,String username, Collection<GrantedAuthority> authorities) {
		UserDetails ud = super.mapUserFromContext(ctx, username, authorities);
		LdapUserDetailsImpl.Essence essence = new LdapUserDetailsImpl.Essence((LdapUserDetailsImpl) ud);

		essence.setEnabled(!Boolean.valueOf(ctx.getStringAttribute("krb5AccountDisabled")));
		essence.setAccountNonLocked(!Boolean.valueOf(ctx.getStringAttribute("krb5AccountLockedOut")));
		try {
			// 20101030214433+0100
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZZZZ");
			Date expirationDate = dateFormat.parse(ctx.getStringAttribute("krb5AccountExpirationTime"));
			essence.setAccountNonExpired(!expirationDate.before(new Date()));
		} catch (ParseException pex) {
			pex.printStackTrace();
		}


		LdapUserDetails ldapUserDetails = essence.createUserDetails();

		if (!ldapUserDetails.isEnabled()) 
			throw new DisabledException("User is not activated");
		
		if (!ldapUserDetails.isAccountNonExpired()) 
			throw new AccountExpiredException("User is expired");

		if (!ldapUserDetails.isAccountNonLocked()) 
			throw new LockedException("User is locked");

		return ldapUserDetails;
	}

}
