/*
 * AccessLogAction.java
 * Developed by Medici Archive Project (2010-2012).
 * 
 * This file is part of Bia.
 * 
 * Bia is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Bia is distributed in the hope that it will be useful,
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
package org.medici.bia.audit;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.medici.bia.common.access.AccessDetailType;
import org.medici.bia.common.access.ApplicationAccessContainer;
import org.medici.bia.common.util.GrantedAuthorityUtils;
import org.medici.bia.common.util.HttpUtils;
import org.medici.bia.domain.AccessLog;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * One of the many strengths of AOP is that it allows you to separate your
 * non-business concerns from your business logic. It also helps you in mundane
 * tasks such putting a logging logic in each of your method or place a
 * try-catch statement on each method. It's better to have a slower app that is
 * maintainable and scalable than having a faster app that would give you hell
 * in maintenance. Slowness could be compensated in many ways, such as upgrading
 * hardware and etc.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class AccessLogAction extends HandlerInterceptorAdapter {
	/**
	 * 
	 */
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ApplicationAccessContainer applicationAccessContainer;
	@Autowired
	private LogService logService;

	public ApplicationAccessContainer getApplicationAccessContainer() {
		return applicationAccessContainer;
	}

	public void setApplicationAccessContainer(
			ApplicationAccessContainer applicationAccessContainer) {
		this.applicationAccessContainer = applicationAccessContainer;
	}

	/**
	 * @return the logService
	 */
	public LogService getLogService() {
		return logService;
	}

	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		AccessLog accessLog = (AccessLog) httpServletRequest.getAttribute("accessLog");

		if (modelAndView != null) {
			if (modelAndView.getModelMap().get("org.springframework.validation.BindingResult.command") != null) {
				List errors = ((BeanPropertyBindingResult) modelAndView.getModelMap().get("org.springframework.validation.BindingResult.command")).getAllErrors();

				if (errors.size() > 0) {
					StringBuilder stringBuilderErrors = new StringBuilder("");
					for (int i = 0; i < errors.size(); i++) {
						stringBuilderErrors.append(errors.get(i).toString());
					}
					accessLog.setErrors(stringBuilderErrors.toString());
				}
			}
			if (modelAndView.getModelMap().get("applicationThrowable") != null) {
				ApplicationThrowable applicationThrowable = (ApplicationThrowable) modelAndView.getModelMap().get("applicationThrowable");
				if (accessLog.getErrors() != null) {
					accessLog.setErrors(accessLog.getErrors() + " - " + applicationThrowable.getMessage());
				} else {
					accessLog.setErrors(applicationThrowable.getMessage());
				}
			}
		}

		accessLog.setExecutionTime(System.currentTimeMillis() - accessLog.getExecutionTime());

		if (httpServletRequest.getAttribute("persistentAccessLogDisabled") == null) {
			try {
				getLogService().traceAccessLog(accessLog);
				
				if (!accessLog.getAccount().equalsIgnoreCase("anonymoususer")) {
					// update user access detail in the application context
					if (accessLog.getAction().contains("/community/")) {
						applicationAccessContainer.setActiveUserAccessDetail(accessLog.getAccount(), AccessDetailType.COMMUNITY);
					} else if (accessLog.getAction().contains("/teaching/")) {
						applicationAccessContainer.setActiveUserAccessDetail(accessLog.getAccount(), AccessDetailType.TEACHING);
					} else {
						applicationAccessContainer.setActiveUserAccessDetail(accessLog.getAccount(), AccessDetailType.DEFAULT);
					}
				} else {
					// update guest access detail in the application context
					if (accessLog.getAction().contains("/community/")) {
						applicationAccessContainer.addGuestUser(accessLog.getIpAddress(), AccessDetailType.COMMUNITY);
					} else if (accessLog.getAction().contains("/teaching/")) {
						applicationAccessContainer.addGuestUser(accessLog.getIpAddress(), AccessDetailType.TEACHING);
					} else {
						applicationAccessContainer.addGuestUser(accessLog.getIpAddress(), AccessDetailType.DEFAULT);
					}
				}
			} catch (ApplicationThrowable applicationThrowable) {
				logger.debug(applicationThrowable);
			}
		}

		StringBuilder stringBuilder = new StringBuilder(10);
		stringBuilder.append(accessLog.getHttpMethod());
		stringBuilder.append(' ');
		stringBuilder.append(accessLog.getAction());
		if (accessLog.getErrors() != null) {
			stringBuilder.append("KO ");
			stringBuilder.append(accessLog.getErrors());
		} else {
			stringBuilder.append(" OK ");
		}
		stringBuilder.append(accessLog.getExecutionTime());
		stringBuilder.append("ms ");
		if (accessLog.getErrors() != null) {
			logger.error(stringBuilder.toString());
		} else {
			logger.info(stringBuilder.toString());
		}
		
		super.postHandle(httpServletRequest, response, handler, modelAndView);
	}

	/**
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// Nel pre-handle prepara l'oggetto prima dell'esecuzione
		// dell'operazione
		AccessLog accessLog = new AccessLog();
		StringBuilder stringBuilder = new StringBuilder(10);

		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			if (SecurityContextHolder.getContext().getAuthentication().getClass().getName().endsWith("AnonymousAuthenticationToken")) {
				accessLog.setAccount(SecurityContextHolder.getContext().getAuthentication().getName());
			} else if (SecurityContextHolder.getContext().getAuthentication().getClass().getName().endsWith("UsernamePasswordAuthenticationToken")) {
				UsernamePasswordAuthenticationToken userNamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
				accessLog.setAccount(userNamePasswordAuthenticationToken.getName());
				accessLog.setAuthorities(GrantedAuthorityUtils.toString(((UserDetails) userNamePasswordAuthenticationToken.getPrincipal()).getAuthorities()));
			} else if (SecurityContextHolder.getContext().getAuthentication().getClass().getName().endsWith("RememberMeAuthenticationToken")) {
				RememberMeAuthenticationToken rememberMeAuthenticationToken = (RememberMeAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
				accessLog.setAccount(rememberMeAuthenticationToken.getName());
				accessLog.setAuthorities(GrantedAuthorityUtils.toString(((UserDetails) rememberMeAuthenticationToken.getPrincipal()).getAuthorities()));
			}
		} else {
			accessLog.setAccount("");
			accessLog.setInformations("");
		}
		MDC.put("account", accessLog.getAccount());

		accessLog.setDateAndTime(new Date(System.currentTimeMillis()));
		accessLog.setIpAddress(request.getRemoteAddr());
		accessLog.setAction(request.getRequestURI());
		accessLog.setHttpMethod(HttpMethod.valueOf(request.getMethod()).toString());
		accessLog.setInformations(HttpUtils.retrieveHttpParametersAsString(request, false, true));
		accessLog.setExecutionTime(System.currentTimeMillis());
		request.setAttribute("accessLog", accessLog);

		stringBuilder.append(accessLog.getHttpMethod());
		stringBuilder.append(' ');
		stringBuilder.append(accessLog.getAction());
		stringBuilder.append(" START (Http Parameters ");
		stringBuilder.append(accessLog.getInformations());
		stringBuilder.append(')');
		logger.info(stringBuilder.toString());

		return true;
	}

	/**
	 * @param logService the logService to set
	 */
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}