/*
 * ProxyIIPImageController.java
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
package org.medici.docsources.controller.manuscriptviewer;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller is a proxy to IIPImage Server. It's used to not expose 
 * IIPImage server.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Controller
@RequestMapping("/mview/ProxyIIPImage")
public class ProxyIIPImageController {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired(required = false)
	@Qualifier("iipImageConfiguration")
	private Properties properties;

	/**
	 * 
	 * 
	 * @param volumeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public void proxyIIPImage(HttpServletRequest httpServletRequest, HttpServletResponse response) {
		// Create an instance of HttpClient.
		HttpClient client = new HttpClient(); 	
		
		StringBuffer stringBuffer = new StringBuffer("");
		stringBuffer.append(properties.getProperty("iipimage.protocol"));
		stringBuffer.append("://");
		stringBuffer.append(properties.getProperty("iipimage.host"));
		stringBuffer.append(":");
		stringBuffer.append(properties.getProperty("iipimage.port"));
		stringBuffer.append(properties.getProperty("iipimage.fcgi.path"));
		stringBuffer.append("?");

		Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
		
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			if (parameterName.equals("FIF")) {
				stringBuffer.append(parameterName);
				stringBuffer.append("=");
				stringBuffer.append(properties.getProperty("iipimage.image.path"));
				stringBuffer.append(httpServletRequest.getParameter("FIF"));
				stringBuffer.append("&");
			} else {
				String[] values = httpServletRequest.getParameterValues(parameterName);
				for (int i=0; i<values.length;i++) {
					stringBuffer.append(parameterName);
					stringBuffer.append("=");
					stringBuffer.append(values[i]);
					stringBuffer.append("&");
				}
			}
		}
		
		// Create a method instance.
		GetMethod method = new GetMethod(stringBuffer.toString());

		try {
			// Execute the method.
			client.executeMethod(method);
			logger.debug("Proxying IIPImage Url : " + stringBuffer.toString() + " (Status Line" + method.getStatusLine() + ")");

			// Redirecting proxed output to client 
			response.setContentType(method.getResponseHeader("Content-Type").getValue());
			response.getOutputStream().write(method.getResponseBody());

			// Flushing request
			response.getOutputStream().flush();
		} catch (HttpException httpException) {
			logger.error("Fatal protocol violation: " + httpException.getMessage());
		} catch (IOException e) {
			logger.error("Fatal transport error: " + e.getMessage());
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

}