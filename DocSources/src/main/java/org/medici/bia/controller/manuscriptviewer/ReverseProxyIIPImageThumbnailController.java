/*
 * ReverseProxyIIPImageThumbnailController.java
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
package org.medici.bia.controller.manuscriptviewer;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller is a reverse proxy to IIPImage Server for thumbnail service. It's used to not expose 
 * IIPImage server.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Controller
@RequestMapping("/mview/ReverseProxyIIPImageThumbnail")
public class ReverseProxyIIPImageThumbnailController {
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
	@RequestMapping(method = RequestMethod.GET)
	public void reverseProxyIIPImage(HttpServletRequest httpServletRequest, HttpServletResponse response) {
		// Create an instance of HttpClient.
		HttpClient client = new HttpClient(); 	
		
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append(properties.getProperty("iipimage.protocol"));
		stringBuilder.append("://");
		stringBuilder.append(properties.getProperty("iipimage.host"));
		stringBuilder.append(':');
		stringBuilder.append(properties.getProperty("iipimage.port"));
		stringBuilder.append(properties.getProperty("iipimage.fcgi.path"));
		stringBuilder.append('?');

		stringBuilder.append("WID=120&");
		stringBuilder.append("FIF=");
		stringBuilder.append(properties.getProperty("iipimage.image.path"));
		stringBuilder.append(httpServletRequest.getParameter("imageName"));
		stringBuilder.append("&CVT=JPEG");

		// Create a method instance.
		GetMethod method = new GetMethod(stringBuilder.toString());

		try {
			// Execute the method.
			client.executeMethod(method);
			logger.debug("Reverse Proxying IIPImageThumbnail Url : " + stringBuilder.toString() + " (Status Line" + method.getStatusLine() + ")");

			// Set content type 
			response.setContentType(method.getResponseHeader("Content-Type").getValue());
			IOUtils.copy(method.getResponseBodyAsStream(),response.getOutputStream());  

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