/*
 * HistoryLogAction.java
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
import org.medici.docsources.common.util.HistoryLogUtils;
import org.medici.docsources.common.util.HttpUtils;
import org.medici.docsources.domain.HistoryLog;
import org.medici.docsources.domain.HistoryLog.ActionCategory;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.log.LogService;
import org.springframework.context.ApplicationEvent;
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
public class HistoryLogAction extends HandlerInterceptorAdapter {
	private LogService logService;
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @return
	 */
	public LogService getLogService() {
		return logService;
	}

	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HistoryLog historyLog = (HistoryLog) request.getAttribute("historyLog");

		if (modelAndView != null) {
			if (modelAndView.getModelMap().get("org.springframework.validation.BindingResult.command") != null) {
				List errors = ((BeanPropertyBindingResult) modelAndView.getModelMap().get("org.springframework.validation.BindingResult.command")).getAllErrors();

				if (errors.size() > 0) {
					StringBuffer stringBuffer = new StringBuffer(
							historyLog.getInformations());
					for (int i = 0; i < errors.size(); i++) {
						stringBuffer.append(errors.get(i).toString());
					}
					historyLog.setInformations(stringBuffer.toString());
				}
			}
		}

		/*try {
			getLogService().traceHistoryLog(historyLog);
		} catch (ApplicationThrowable ex) {
		}*/

		//logger.info(historyLog.toString());
		super.postHandle(request, response, handler, modelAndView);
	}

	/**
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// Nel pre-handle prepara l'oggetto prima dell'esecuzione
		// dell'operazione
		HistoryLog historyLog = new HistoryLog();

		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			if (SecurityContextHolder.getContext().getAuthentication().getClass().getName().endsWith("AnonymousAuthenticationToken")) {
				historyLog.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			} else if (SecurityContextHolder.getContext().getAuthentication().getClass().getName().endsWith("UsernamePasswordAuthenticationToken")) {
				UsernamePasswordAuthenticationToken userNamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
				historyLog.setUsername(userNamePasswordAuthenticationToken.getName());
				historyLog.setAuthorities(GrantedAuthorityUtils.toString(((UserDetails) userNamePasswordAuthenticationToken.getPrincipal()).getAuthorities()));
			} else if (SecurityContextHolder.getContext().getAuthentication().getClass().getName().endsWith("RememberMeAuthenticationToken")) {
				RememberMeAuthenticationToken rememberMeAuthenticationToken = (RememberMeAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
				historyLog.setUsername(rememberMeAuthenticationToken.getName());
				historyLog.setAuthorities(GrantedAuthorityUtils.toString(((UserDetails) rememberMeAuthenticationToken.getPrincipal()).getAuthorities()));
			}
		} else {
			historyLog.setUsername("");
			historyLog.setInformations("");
		}

		historyLog.setDateAndTime(new Date(System.currentTimeMillis()));
		historyLog.setIpAddress(request.getRemoteAddr());
		historyLog.setActionCategory(HistoryLogUtils.extractActionCategory(request.getRequestURI().toString()));
		historyLog.setAction(HistoryLogUtils.extractAction(request, historyLog.getActionCategory()));
		historyLog.setActionUrl(HistoryLogUtils.extractActionUrl(request, historyLog.getActionCategory()));
		historyLog.setInformations(HttpUtils.retrieveHttpParametersAsString(request, false, true));
		request.setAttribute("historyLog", historyLog);

		//logger.info(historyLog.toString());
		return true;
	}

	/**
	 * 
	 * @param logService
	 */
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}