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
package org.medici.bia.security;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.medici.bia.common.access.ApplicationAccessContainer;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.util.UserRoleUtils;
import org.medici.bia.dao.lockeduser.LockedUserDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userrole.UserRoleDAO;
import org.medici.bia.domain.AccessLog;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserRole;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.admin.AdminService;
import org.medici.bia.service.log.LogService;
import org.medici.bia.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
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
	private ApplicationAccessContainer applicationAccessContainer;

	@Autowired
	private LockedUserDAO lockedUserDAO;
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserRoleDAO userRoleDAO;
	
	public ApplicationAccessContainer getApplicationAccessContainer() {
		return applicationAccessContainer;
	}

	public void setApplicationAccessContainer(
			ApplicationAccessContainer applicationAccessContainer) {
		this.applicationAccessContainer = applicationAccessContainer;
	}

	/**
	 * @return the userRoleDAO
	 */
	public UserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	/**
	 * @param userRoleDAO the userRoleDAO to set
	 */
	public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private MailService mailService;

	/**
	 * @return the adminService
	 */
	public AdminService getAdminService() {
		return adminService;
	}

	/**
	 * @param adminService the adminService to set
	 */
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * @return the logService
	 */
	public LogService getLogService() {
		return logService;
	}

	/**
	 * @param logService the logService to set
	 */
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	/**
	 * @return the mailService
	 */
	public MailService getMailService() {
		return mailService;
	}

	/**
	 * @param mailService the mailService to set
	 */
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	/**
	 * @return the lockedUserDAO
	 */
	public LockedUserDAO getLockedUserDAO() {
		return lockedUserDAO;
	}

	/**
	 * @param lockedUserDAO the lockedUserDAO to set
	 */
	public void setLockedUserDAO(LockedUserDAO lockedUserDAO) {
		this.lockedUserDAO = lockedUserDAO;
	}

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
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
		try {
			Long startTime = System.currentTimeMillis();
			super.additionalAuthenticationChecks(userDetails, usernamePasswordAuthenticationToken);

			User user = getUserDAO().findUser(userDetails.getUsername());

			if (!user.getActive()) { 
				throw new DisabledException("User is not activated");
			}
			
			if (!user.getApproved()) { 
				throw new AccountNotApprovedException("User has not been approved yet. Wait for an approvation email before logging");
			}
	
			if (!user.getExpirationDate().after(new Date())) {
				throw new AccountExpiredException("User is expired");
			}
	
			if (user.getLocked()) {
				throw new LockedException("User is locked");
			}
			
			user.setLastLoginDate(user.getCurrentLoginDate());
			user.setCurrentLoginDate(new Date());
			user.setBadLogin(0);
		
			getUserDAO().merge(user);

			AccessLog accessLog = new AccessLog();
			accessLog.setAccount(userDetails.getUsername());
			accessLog.setDateAndTime(new Date(System.currentTimeMillis()));
			accessLog.setIpAddress(((WebAuthenticationDetails) usernamePasswordAuthenticationToken.getDetails()).getRemoteAddress());
			accessLog.setAction("/loginProcess");
			
			List<UserRole> userRoles = getUserRoleDAO().findUserRoles(user.getAccount());
			accessLog.setAuthorities(UserRoleUtils.toString(userRoles));
			accessLog.setExecutionTime(System.currentTimeMillis() - startTime);
			accessLog.setHttpMethod(HttpMethod.POST.toString());
			
			try {
				getLogService().traceAccessLog(accessLog);
				
				// Update the online users in "application context variable"
				applicationAccessContainer.addOnlineUser(user);
				
			} catch (ApplicationThrowable applicationThrowable) {
				logger.error(applicationThrowable);
			}

			logger.info(" Authentication OK");
		} catch (AuthenticationException authenticationException) {
			User user = getUserDAO().findUser(userDetails.getUsername());

			if (user != null) {
				if (!user.getActive()) {
					throw new DisabledException("User is not activated",authenticationException);
				}
				
				if (!user.getApproved()) { 
					throw new AccountNotApprovedException("User has not been approved yet. Wait for an approvation email before logging");
				}
				
				if (!user.getExpirationDate().after(new Date())) { 
					throw new AccountExpiredException("User is expired", authenticationException);
				}
		
				if (user.getLocked()) {
					throw new LockedException("User is locked", authenticationException);
				}
				
				user.setBadLogin(user.getBadLogin()+1);
				
				Integer badLogin = NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("user.maxBadLogin"));
				
				if (user.getBadLogin() > badLogin) {
					user.setLocked(true);
					getUserDAO().merge(user);
					try {
						getAdminService().addLockedUser(user);
						
					} catch (ApplicationThrowable ath) {
						
					}
				}

				getUserDAO().merge(user);
			}
			
			throw authenticationException;
		}
	}
}