/*
 * EuropeanaViewController.java
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
package org.medici.bia.controller.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.medici.bia.common.context.ApplicationContextVariableManager;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.europeana.EuropeanaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Controller
public class EuropeanaViewController {
	
	@Autowired
	private EuropeanaService europeanaService;
	@Autowired
	private ApplicationContextVariableManager applicationContextVariableManager;

	public EuropeanaService getEuropeanaService() {
		return europeanaService;
	}

	public void setEuropeanaService(EuropeanaService europeanaService) {
		this.europeanaService = europeanaService;
	}
	
	public ApplicationContextVariableManager getApplicationContextVariableManager() {
		return applicationContextVariableManager;
	}

	public void setApplicationContextVariableManager(
			ApplicationContextVariableManager applicationContextVariableManager) {
		this.applicationContextVariableManager = applicationContextVariableManager;
	}
	
	@RequestMapping(value = "europeana/getProgress.do", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getProgress(HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (getApplicationContextVariableManager().get(ApplicationContextVariableManager.EUROPEANA_JOB) != null) {
			model.put("running", true);
			Float progress = (Float)getApplicationContextVariableManager().get(EuropeanaService.PROGRESS);
			model.put("progress", progress != null ? (Math.round(progress)) : "unknown");
			model.put("phases", (Integer)getApplicationContextVariableManager().get(EuropeanaService.PHASES));
			model.put("currentPhase", (Integer)getApplicationContextVariableManager().get(EuropeanaService.CURRENT_PHASE));
		} else if (getApplicationContextVariableManager().get(EuropeanaService.ERROR) != null) {
			model.put("error", getApplicationContextVariableManager().get(EuropeanaService.ERROR));
		}
		return model;
	}

	@RequestMapping(value = "europeana/downloadEuropeanaFile.do", method = RequestMethod.GET)
	public void doDownload (HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream outputStream = null;
		
		// set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=\"Europeana.xml\"";
        response.setHeader(headerKey, headerValue);
        
		try {
			outputStream = response.getOutputStream();
			long fileLength = getEuropeanaService().downladFile(outputStream);
			
			// set content attributes for the response
			response.setContentType("application/octet-stream");
			response.setContentLength((int) fileLength);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ApplicationThrowable e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@RequestMapping(value = "europeana/fireJob.do", method = RequestMethod.POST)
	public ModelAndView fireEuropeanaJob() {
		if (getApplicationContextVariableManager().get(ApplicationContextVariableManager.EUROPEANA_JOB) == null) {
			getEuropeanaService().writeEuropeanaFileAsync();
		} else {
		}
		return setupEuropeanaManager();
	}
	
	@RequestMapping(value = "europeana/europeanaView.do", method = RequestMethod.GET)
	public ModelAndView setupEuropeanaManager() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (getApplicationContextVariableManager().get(ApplicationContextVariableManager.EUROPEANA_JOB) != null) {
				model.put("europeanaJob", "running");
			}
			if (getApplicationContextVariableManager().get(EuropeanaService.ERROR) != null) {
				model.put("error", getApplicationContextVariableManager().get(EuropeanaService.ERROR));
			}
			String readableSize = getEuropeanaService().getEuropeanaFileSize();
			if (readableSize != null) {
				model.put("europeanaSize", readableSize);
			}
		} catch (ApplicationThrowable e) {
			model.put("europeanaSize", "error");
		}
		return new ModelAndView("europeana/ShowEuropeanaView", model);
	}
	
	@RequestMapping(value = "europeana/stopJob.do", method = RequestMethod.GET)
	public ModelAndView stopEuropeanaJob() {
		getApplicationContextVariableManager().remove(ApplicationContextVariableManager.EUROPEANA_JOB);
		getApplicationContextVariableManager().add(EuropeanaService.ERROR, "Europeana Job was interrupted!");
		return setupEuropeanaManager();
	}
	

}
