/*
 * BiaLdapUserDetailsMapper.java
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
package org.medici.bia.security;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.PersistenceException;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.medici.docsources.common.property.ApplicationPropertyManager;
import org.medici.docsources.dao.user.UserDAO;
import org.medici.docsources.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.ppolicy.PasswordPolicyControl;
import org.springframework.security.ldap.ppolicy.PasswordPolicyResponseControl;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class extends LdapUserDetailsMapper to provide full support to our LDAP
 * server. The purpose of extending LdapUserDetailsMapper is very important beacause
 * with this configuration we can use Spring Security taglibs to print in Java
 * Server Pages not only username but every fields defined in BiaLdapUserDetailsImpl.
 * This class overriding follows logical model of extended class. 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.springframework.security.ldap.userdetails.LdapUserDetailsMapper
 * 
 * @see org.medici.bia.security.BiaLdapUserDetailsImpl
 */
@Transactional(readOnly=true)
public class BiaLdapUserDetailsMapper extends LdapUserDetailsMapper {
	@Autowired
	private UserDAO userDAO;

	private final Log logger = LogFactory.getLog(BiaLdapUserDetailsMapper.class);
	private String passwordAttributeName = "userPassword";
	private String[] roleAttributes = null;

	/**
	 * 
	 * @param ctx
	 * @param username
	 * @param authorities
	 * @return UserDetails 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx,String username, Collection<GrantedAuthority> authorities) {
		BiaLdapUserDetailsImpl.Essence essence = new BiaLdapUserDetailsImpl.Essence(ctx);
		Object passwordValue = ctx.getObjectAttribute(passwordAttributeName);
		User user = null;

		if (passwordValue != null) {
			essence.setPassword(mapPassword(passwordValue));
		}

		essence.setUsername(username);

		// Map the roles
		for (int i = 0; (roleAttributes != null) && (i < roleAttributes.length); i++) {
			String[] rolesForAttribute = ctx.getStringAttributes(roleAttributes[i]);

			if (rolesForAttribute == null) {
				logger.debug("Couldn't read role attribute '" + roleAttributes[i] + "' for user " + ctx.getNameInNamespace() );
				continue;
			}

			for (int j = 0; j < rolesForAttribute.length; j++) {
				GrantedAuthority authority = createAuthority(rolesForAttribute[j]);

				if (authority != null) {
					essence.addAuthority(authority);
				}
			}
		}

		// Add the supplied authorities

		for (GrantedAuthority authority : authorities) {
			essence.addAuthority(authority);
		}

		// Check for PPolicy data
		PasswordPolicyResponseControl ppolicy = (PasswordPolicyResponseControl) ctx.getObjectAttribute(PasswordPolicyControl.OID);

		if (ppolicy != null) {
			essence.setTimeBeforeExpiration(ppolicy.getTimeBeforeExpiration());
			essence.setGraceLoginsRemaining(ppolicy.getGraceLoginsRemaining());
		}

		BiaLdapUserDetailsImpl docSourcesLdapUserDetailsImpl = essence.createUserDetails();

		try {
			user = getUserDAO().findUser(docSourcesLdapUserDetailsImpl.getUsername());
			if (user != null) {
				user.setLastLoginDate(user.getCurrentLoginDate());
				user.setCurrentLoginDate(new Date());
				user.setBadLogin(0);
			
				getUserDAO().merge(user);
			} else {
				// If user is null, we need to create user record...
				user = new User(docSourcesLdapUserDetailsImpl.getUsername());
				Calendar expirationDate = Calendar.getInstance();
				expirationDate.add(Calendar.MONTH, NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("user.expiration.user.months")));
				user.setExpirationDate(expirationDate.getTime());
				Calendar expirationPasswordDate = Calendar.getInstance();
				expirationPasswordDate.add(Calendar.MONTH, NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("user.expiration.password.months")));
				user.setExpirationPasswordDate(expirationPasswordDate.getTime());
				user.setBadLogin(0);
				user.setActive(true);
				user.setLocked(false);
				user.setRegistrationDate(new Date());
				user.setActivationDate(new Date());
				user.setLastLoginDate(user.getCurrentLoginDate());
				user.setCurrentLoginDate(new Date());
				user.setForumNumberOfPost(new Long(0));
				getUserDAO().persist(user);
			}
		} catch (PersistenceException persistenceException) {
			logger.error("Exception during user update", persistenceException);

		}
		
		if (!user.getActive()) 
			throw new DisabledException("User is not activated");
		
		//MD: This code isn't implemented
		/*if (!user.getApproved()) 
			throw new AccountNotApprovedException("User is not approved");*/

		if (!user.getExpirationDate().after(new Date())) 
			throw new AccountExpiredException("User is expired");

		if (user.getLocked())
			throw new LockedException("User is locked");

		return docSourcesLdapUserDetailsImpl;
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

}
