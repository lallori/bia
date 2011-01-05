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

		// "GET /fcgi-bin/iipsrv.fcgi?FIF=/data/tiled_mdp/1/MDP1702/0003_C_000_RV.tif&obj=IIP,1.0&obj=Max-size&obj=Tile-size&obj=Resolution-number& HTTP/1.1" 200 69
		// "GET /fcgi-bin/iipsrv.fcgi?WID=75&FIF=/data/tiled_mdp/1/MDP1702/0003_C_000_RV.tif&CVT=JPEG& HTTP/1.1" 200 1329
		// "GET /fcgi-bin/iipsrv.fcgi?FIF=/data/tiled_mdp/1/MDP1702/0003_C_000_RV.tif&jtl=1,1& HTTP/1.1" 200 2445
		
		if (httpServletRequest.getParameter("obj") != null) {
			// This get image tile informations
			stringBuffer.append("FIF=");
			stringBuffer.append(properties.getProperty("iipimage.image.path"));
			stringBuffer.append(httpServletRequest.getParameter("FIF"));
			stringBuffer.append("&");
			String[] values = httpServletRequest.getParameterValues("obj");
			for (int i=0; i<values.length;i++) {
				stringBuffer.append("obj");
				stringBuffer.append("=");
				stringBuffer.append(values[i]);
				stringBuffer.append("&");
			}
		} else if (httpServletRequest.getParameter("WID") != null) {
			// This get image preview
			stringBuffer.append("WID=");
			stringBuffer.append(httpServletRequest.getParameter("WID"));
			stringBuffer.append("&FIF=");
			stringBuffer.append(properties.getProperty("iipimage.image.path"));
			stringBuffer.append(httpServletRequest.getParameter("FIF"));
			stringBuffer.append("&CVT=");
			stringBuffer.append(httpServletRequest.getParameter("CVT"));
			stringBuffer.append("&");
		} else if (httpServletRequest.getParameter("jtl") != null) {
			// This get tiff section image.
			stringBuffer.append("&FIF=");
			stringBuffer.append(properties.getProperty("iipimage.image.path"));
			stringBuffer.append(httpServletRequest.getParameter("FIF"));
			stringBuffer.append("&jtl=");
			stringBuffer.append(httpServletRequest.getParameter("jtl"));
			stringBuffer.append("&");
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