/*
 * BiaDaoAuthenticationProvider.java
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
package org.medici.docsources.security;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.medici.docsources.common.property.ApplicationPropertyManager;
import org.medici.docsources.dao.user.UserDAO;
import org.medici.docsources.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
@Transactional(readOnly=true)
public  class BiaDaoAuthenticationProvider extends DaoAuthenticationProvider implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5824046280716934036L;

	@Autowired
	private UserDAO userDAO;

	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		try {
			super.additionalAuthenticationChecks(userDetails, authentication);

			User user = getUserDAO().findUser(userDetails.getUsername());

			if (!user.getActive()) 
				throw new DisabledException("User is not activated");
			
			if (!user.getApproved()) 
				throw new AccountNotApprovedException("User is not approved");
	
			if (!user.getExpirationDate().after(new Date())) 
				throw new AccountExpiredException("User is expired");
	
			if (user.getLocked())
				throw new LockedException("User is locked");
			
			user.setLastLoginDate(user.getCurrentLoginDate());
			user.setCurrentLoginDate(new Date());
			user.setBadLogin(0);
		
			getUserDAO().merge(user);
		} catch (AuthenticationException authenticationException) {
			User user = getUserDAO().findUser(userDetails.getUsername());

			if (user != null) {
				if (!user.getActive()) 
					throw new DisabledException("User is not activated");
				
				if (!user.getExpirationDate().after(new Date())) 
					throw new AccountExpiredException("User is expired");
		
				if (user.getLocked())
					throw new LockedException("User is locked");
				
				user.setBadLogin(user.getBadLogin()+1);
				
				Integer badLogin = NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("user.badLogin"));
				
				if (user.getBadLogin() > badLogin) {
					user.setLocked(true);
				}
				getUserDAO().merge(user);
			}
			
			throw authenticationException;
		}
	}
}