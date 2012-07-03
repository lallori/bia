/*
 * ReverseProxyIIPImageController.java
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.medici.docsources.common.property.ApplicationPropertyManager;
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
@RequestMapping("/mview/ReverseProxyIIPImage")
public class ReverseProxyIIPImageController {
	private Logger logger = Logger.getLogger(this.getClass());

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
		String versionServer = ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.version");
		String connectUrl = null;
		
		if (versionServer.equals("0.9.8")) {
			connectUrl = getConnectUrlServer098(httpServletRequest);
		} else if (versionServer.equals("0.9.9")) {
			connectUrl = getConnectUrlServer099(httpServletRequest);
		}
		
		// Create a method instance.
		GetMethod method = new GetMethod(connectUrl);

		try {
			// Execute the method.
			client.executeMethod(method);
			logger.debug("Proxying IIPImage Url : " + connectUrl + " (Status Line" + method.getStatusLine() + ")");

			// Set content type 
			response.setContentType(method.getResponseHeader("Content-Type").getValue());
			//response.getOutputStream().write(method.getResponseBody());
			// Redirecting proxed output to client
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
	 * This method creates url for Server version 0.9.9
	 * @param httpServletRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getConnectUrlServer099(HttpServletRequest httpServletRequest) {
		// "GET /fcgi-bin/iipsrv.fcgi?FIF=1/MDP5/0899_C_494_R.tif&obj=IIP,1.0&obj=Max-size&obj=Tile-size&obj=Resolution-number
		// "GET http://localhost/fcgi-bin/iipsrv.fcgi?FIF=1/MDP5/0899_C_494_R.tif&CNT=1&SDS=0,90&JTL=3,2
		// "GET /fcgi-bin/iipsrv.fcgi?FIF=/data/tiled_mdp/1/MDP1702/0003_C_000_RV.tif&jtl=1,1& HTTP/1.1" 200 2445
		
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.protocol"));
		stringBuilder.append("://");
		stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.host"));
		stringBuilder.append(":");
		stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.port"));
		stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.fcgi.path"));
		stringBuilder.append("?");
		stringBuilder.append("FIF=");
		stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path"));
		stringBuilder.append(httpServletRequest.getParameter("FIF"));
		stringBuilder.append("&");

		Enumeration<String> enumeration = httpServletRequest.getParameterNames();
		
		while (enumeration.hasMoreElements()) {
			String httpParameter = enumeration.nextElement();
			
			if (httpParameter.equals("FIF")) {
				continue;
			} else {
				String[] values = httpServletRequest.getParameterValues(httpParameter);
				for (int i=0; i<values.length;i++) {
					stringBuilder.append(httpParameter);
					stringBuilder.append("=");
					stringBuilder.append(values[i]);
					stringBuilder.append("&");
				}
			}
		}

		return stringBuilder.toString();
	}

	/**
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	private String getConnectUrlServer098(HttpServletRequest httpServletRequest) {
		// "GET /fcgi-bin/iipsrv.fcgi?FIF=/data/tiled_mdp/1/MDP1702/0003_C_000_RV.tif&obj=IIP,1.0&obj=Max-size&obj=Tile-size&obj=Resolution-number& HTTP/1.1" 200 69
		// "GET /fcgi-bin/iipsrv.fcgi?WID=75&FIF=/data/tiled_mdp/1/MDP1702/0003_C_000_RV.tif&CVT=JPEG& HTTP/1.1" 200 1329
		// "GET /fcgi-bin/iipsrv.fcgi?FIF=/data/tiled_mdp/1/MDP1702/0003_C_000_RV.tif&jtl=1,1& HTTP/1.1" 200 2445
		
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.protocol"));
		stringBuilder.append("://");
		stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.host"));
		stringBuilder.append(":");
		stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.port"));
		stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.fcgi.path"));
		stringBuilder.append("?");

		if (httpServletRequest.getParameter("obj") != null) {
			// This get image tile informations
			stringBuilder.append("FIF=");
			stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path"));
			stringBuilder.append(httpServletRequest.getParameter("FIF"));
			stringBuilder.append("&");
			String[] values = httpServletRequest.getParameterValues("obj");
			for (int i=0; i<values.length;i++) {
				stringBuilder.append("obj");
				stringBuilder.append("=");
				stringBuilder.append(values[i]);
				stringBuilder.append("&");
			}
		} else if (httpServletRequest.getParameter("WID") != null) {
			// This get image preview
			stringBuilder.append("WID=");
			stringBuilder.append(httpServletRequest.getParameter("WID"));
			stringBuilder.append("&FIF=");
			stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path"));
			stringBuilder.append(httpServletRequest.getParameter("FIF"));
			stringBuilder.append("&CVT=");
			stringBuilder.append(httpServletRequest.getParameter("CVT"));
			stringBuilder.append("&");
		} else if (httpServletRequest.getParameter("jtl") != null) {
			// This get tiff section image.
			stringBuilder.append("&FIF=");
			stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("iipimage.image.path"));
			stringBuilder.append(httpServletRequest.getParameter("FIF"));
			stringBuilder.append("&jtl=");
			stringBuilder.append(httpServletRequest.getParameter("jtl"));
			stringBuilder.append("&");
		}
		
		return stringBuilder.toString();
	}
}