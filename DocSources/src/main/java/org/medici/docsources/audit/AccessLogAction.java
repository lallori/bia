/*
 * AccessLogAction.java
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
package org.medici.docsources.audit;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.medici.docsources.common.util.GrantedAuthorityUtils;
import org.medici.docsources.common.util.HttpUtils;
import org.medici.docsources.domain.AccessLog;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
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
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private LogService logService;

	/**
	 * @return the logService
	 */
	public LogService getLogService() {
		return logService;
	}

	/**
	 * 
	 * @param event
	 */
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AuthorizationFailureEvent) {
			logger.info("User not authenticated. ");
		} else if (event instanceof AuthenticationSuccessEvent) {
			UsernamePasswordAuthenticationToken userNamePasswordAuthenticationToken = ((UsernamePasswordAuthenticationToken) event.getSource());
			AccessLog accessLog = new AccessLog();
			accessLog.setUsername(userNamePasswordAuthenticationToken.getCredentials().toString());
			accessLog.setDateAndTime(new Date(System.currentTimeMillis()));
			accessLog.setIpAddress(((WebAuthenticationDetails) userNamePasswordAuthenticationToken.getDetails()).getRemoteAddress());
			accessLog.setAction("/loginProcess");
			accessLog.setAuthorities(GrantedAuthorityUtils.toString(((UserDetails) userNamePasswordAuthenticationToken.getPrincipal()).getAuthorities()));

			try {
				getLogService().traceAccessLog(accessLog);
			} catch (ApplicationThrowable ex) {
			}

			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(accessLog.getUsername());
			stringBuffer.append(" Authentication OK");
			logger.info(stringBuffer.toString());
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		AccessLog accessLog = (AccessLog) request.getAttribute("accessLog");

		if (modelAndView != null) {
			if (modelAndView.getModelMap().get("org.springframework.validation.BindingResult.command") != null) {
				List errors = ((BeanPropertyBindingResult) modelAndView.getModelMap().get("org.springframework.validation.BindingResult.command")).getAllErrors();

				if (errors.size() > 0) {
					StringBuffer stringBufferErrors = new StringBuffer(accessLog.getInformations());
					for (int i = 0; i < errors.size(); i++) {
						stringBufferErrors.append(errors.get(i).toString());
					}
					accessLog.setErrors(stringBufferErrors.toString());
				} else {
				}
			}
		}

		accessLog.setExecutionTime(System.currentTimeMillis() - accessLog.getExecutionTime());

		try {
			getLogService().traceAccessLog(accessLog);
		} catch (ApplicationThrowable ex) {
		}

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(accessLog.getUsername());
		stringBuffer.append(" - ");
		stringBuffer.append(accessLog.getHttpMethod());
		stringBuffer.append(" ");
		stringBuffer.append(accessLog.getAction());
		if (accessLog.getErrors() != null) {
			stringBuffer.append("KO ");
			stringBuffer.append(accessLog.getErrors());
		} else {
			stringBuffer.append(" OK ");
		}
		stringBuffer.append(accessLog.getExecutionTime());
		stringBuffer.append("ms ");
		if (accessLog.getErrors() != null) {
			logger.error(stringBuffer.toString());
		} else {
			logger.info(stringBuffer.toString());
		}
		
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// Nel pre-handle prepara l'oggetto prima dell'esecuzione
		// dell'operazione
		AccessLog accessLog = new AccessLog();

		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			if (SecurityContextHolder.getContext().getAuthentication().getClass().getName().endsWith("AnonymousAuthenticationToken")) {
				accessLog.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			} else if (SecurityContextHolder.getContext().getAuthentication().getClass().getName().endsWith("UsernamePasswordAuthenticationToken")) {
				UsernamePasswordAuthenticationToken userNamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
				accessLog.setUsername(userNamePasswordAuthenticationToken.getName());
				accessLog.setAuthorities(GrantedAuthorityUtils.toString(((UserDetails) userNamePasswordAuthenticationToken.getPrincipal()).getAuthorities()));
			} else if (SecurityContextHolder.getContext().getAuthentication().getClass().getName().endsWith("RememberMeAuthenticationToken")) {
				RememberMeAuthenticationToken rememberMeAuthenticationToken = (RememberMeAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
				accessLog.setUsername(rememberMeAuthenticationToken.getName());
				accessLog.setAuthorities(GrantedAuthorityUtils.toString(((UserDetails) rememberMeAuthenticationToken.getPrincipal()).getAuthorities()));
			}
		} else {
			accessLog.setUsername("");
			accessLog.setInformations("");
		}

		accessLog.setDateAndTime(new Date(System.currentTimeMillis()));
		accessLog.setIpAddress(request.getRemoteAddr());
		accessLog.setAction(request.getRequestURI().toString());
		accessLog.setHttpMethod(HttpMethod.valueOf(request.getMethod()).toString());
		accessLog.setInformations(HttpUtils.retrieveHttpParametersAsString(request, false, true));
		accessLog.setExecutionTime(System.currentTimeMillis());
		request.setAttribute("accessLog", accessLog);

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(accessLog.getUsername());
		stringBuffer.append(" - ");
		stringBuffer.append(accessLog.getHttpMethod());
		stringBuffer.append(" ");
		stringBuffer.append(accessLog.getAction());
		stringBuffer.append(" START (Http Parameters ");
		stringBuffer.append(accessLog.getInformations());
		stringBuffer.append(")");
		logger.info(stringBuffer.toString());

		return true;
	}

	/**
	 * @param logService the logService to set
	 */
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}