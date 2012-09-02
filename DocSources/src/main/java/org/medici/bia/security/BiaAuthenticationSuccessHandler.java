/*
 * BiaAuthenticationSuccessHandler.java
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

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class BiaAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	private UserService userService;
	private String defaultTargetUrl;
	private String changeExpiratedUserPasswordUrl;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		try {
			User user = getUserService().findUser(((BiaUserDetailsImpl)authentication.getPrincipal()).getUsername());
			
			if (!user.getExpirationPasswordDate().after(new Date())) {
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + getChangeExpiratedUserPasswordUrl()));
				return;
			}
		}catch (ApplicationThrowable applicationThrowable) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + getDefaultTargetUrl()));
			return;
		}
		
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + getDefaultTargetUrl()));		
	}

	/**
	 * @return the defaultTargetUrl
	 */
	public String getDefaultTargetUrl() {
		return defaultTargetUrl;
	}

	/**
	 * @param defaultTargetUrl the defaultTargetUrl to set
	 */
	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	/**
	 * @return the changeExpiratedUserPasswordUrl
	 */
	public String getChangeExpiratedUserPasswordUrl() {
		return changeExpiratedUserPasswordUrl;
	}

	/**
	 * @param changeExpiratedUserPasswordUrl the changeExpiratedUserPasswordUrl to set
	 */
	public void setChangeExpiratedUserPasswordUrl(String changeExpiratedUserPasswordUrl) {
		this.changeExpiratedUserPasswordUrl = changeExpiratedUserPasswordUrl;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}



}
