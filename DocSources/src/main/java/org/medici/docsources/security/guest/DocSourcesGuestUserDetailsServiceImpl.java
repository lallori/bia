/*
 * GuestUserDetailsServiceImpl.java
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
package org.medici.docsources.security.guest;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.memory.InMemoryDaoImpl;

/**
 * This class retrieves user details from an in-memory list created in the 
 * application context and extends the UserDetails object with a custom object
 * which contains additional fields. This is necessary to permit an unique use of
 * Spring Security TagLibs without a differentation base on guest role
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class DocSourcesGuestUserDetailsServiceImpl extends InMemoryDaoImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		UserDetails userDetails = super.loadUserByUsername(username);
		DocSourcesGuestUserDetailsImpl docSourcesGuestUserDetailsImpl = new DocSourcesGuestUserDetailsImpl();
		docSourcesGuestUserDetailsImpl.setFirstName("");
		docSourcesGuestUserDetailsImpl.setLastName("Guest");
		docSourcesGuestUserDetailsImpl.setUsername(userDetails.getUsername());
		docSourcesGuestUserDetailsImpl.setPassword(userDetails.getPassword());
		docSourcesGuestUserDetailsImpl.setAuthorities(userDetails.getAuthorities());

		return docSourcesGuestUserDetailsImpl;
	}

}
