/*
 * OpenAnnotationsViewController.java
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

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.openannotation.AnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller to view the open annotations serialization.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Controller
public class OpenAnnotationsViewController {
	@Autowired
	private AnnotationService annotationService;
	
	public AnnotationService getAnnotationService() {
		return annotationService;
	}

	public void setAnnotationService(AnnotationService annotationService) {
		this.annotationService = annotationService;
	}

	@RequestMapping(value = "/src/openannotation/annotationsView.do", method = RequestMethod.GET)
	public ModelAndView viewAnnotations() {
		Map<String, Object> model = new HashMap<String, Object>();
		String content = "";
		try {
			content = getAnnotationService().readJsonLDFile();
		} catch (ApplicationThrowable e) {
		}
		model.put("jsonannotations", content);
		return new ModelAndView("openannotation/ShowOpenAnnotations", model);
	}
	
	@RequestMapping(value = "src/openannotation/downloadOAFile.do", method = RequestMethod.GET)
	public void doDownload (HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream outputStream = null;
		
		// set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=\"BiaAnnotations.json\"";
        response.setHeader(headerKey, headerValue);
        
		try {
			outputStream = response.getOutputStream();
			long fileLength = getAnnotationService().writeJsonLDFileToOutputStream(outputStream);
			
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

}
