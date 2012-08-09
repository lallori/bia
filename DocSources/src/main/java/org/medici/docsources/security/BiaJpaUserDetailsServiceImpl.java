/*
 * BiaJpaUserDetailsServiceImpl.java
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.docsources.common.util.UserRoleUtils;
import org.medici.docsources.dao.user.UserDAO;
import org.medici.docsources.dao.userrole.UserRoleDAO;
import org.medici.docsources.domain.User;
import org.medici.docsources.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class BiaJpaUserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		User user = getUserDAO().findUser(username);
	    if (user == null)
	    	throw new UsernameNotFoundException("user not found");
	    
	    List<UserRole> userRoles = new ArrayList<UserRole>(0);
	    try {
	    	userRoles = getUserRoleDAO().findUserRoles(user.getAccount());
	    } catch(PersistenceException persistenceException) {
	    	
	    }

	    UserDetailsBuilder userDetailsBuilder = new UserDetailsBuilder();
		
	    return userDetailsBuilder.buildUserDetailsFromUserEntity(user, userRoles);
	}

	/**
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	public UserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	private class UserDetailsBuilder implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -266550775009403486L;

		public UserDetails buildUserDetailsFromUserEntity(User user, List<UserRole> userRoles) {
			BiaUserDetailsImpl userDetails = new BiaUserDetailsImpl();
			userDetails.setUsername(user.getAccount());
			userDetails.setPassword(user.getPassword());
			userDetails.setActive(user.getActive());
			userDetails.setFirstName(user.getFirstName());
			userDetails.setLastName(user.getLastName());
			userDetails.setInitials(user.getInitials());
			
			userDetails.setAuthorities(getAuthorities(userRoles));
			userDetails.setSignificantRoleDescription(UserRoleUtils.toDescriptionString(UserRoleUtils.getMostSignificantRole(userRoles)));
           
            return userDetails;
		}

		/**
		 * 
		 * @param userRoles
		 * @return
		 */
		private Collection<GrantedAuthority> getAuthorities(List<UserRole> userRoles) {
			String rolePrefix = "ROLE_";
			List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(0);
			for (UserRole userRole : userRoles) {
				authList.add(new GrantedAuthorityImpl(rolePrefix + userRole.getUserAuthority().toString()));
			}

			return authList;
		}
	}
}
