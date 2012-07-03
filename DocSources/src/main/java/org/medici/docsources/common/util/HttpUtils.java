/*
 * HttpUtils.java
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
package org.medici.docsources.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Utility class to work on http protocol.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class HttpUtils {
	public final static String ENCODING_SCHEME = "UTF-8";
	public final static String UNSUPPORTED_SCHEME = "UNSUPPORTED_SCHEME";

	/**
	 * This method retrieves all request cookies from an input
	 * HttpServletRequest and returns a string these cookies.
	 * 
	 * @param req Http Request containing cookies to extract.
	 * @return String rappresentation of all cookies contained in http request
	 *         (format is cookie1Name=cookie1Value&cookie2Name=cookie2Value).
	 */
	public static String retrieveCookiesAsString(HttpServletRequest req) {
		if (req == null)
			return "";

		StringBuilder buffer = new StringBuilder();
		Cookie[] cookieArray = req.getCookies();

		if (cookieArray == null)
			buffer.append("(no cookie present in http request)");

		else {
			for (int i = 0; i < cookieArray.length; i++) {
				buffer.append(cookieArray[i].getName());
				buffer.append("=");
				buffer.append(cookieArray[i].getValue());
				if (i < (cookieArray.length - 1))
					buffer.append(", ");
			}
		}

		return buffer.toString();
	}

	/**
	 * This method retrieves all request parameteres from an input
	 * HttpServletRequest and returns a string rapresenting all parameters.
	 * 
	 * @param req  Http Request containing parameters to extract.
	 * @return String rappresentation of all http parameters (format is
	 *         parameter1Name=parameter1Value&parameter2Name=parameter2Value).
	 */
	@SuppressWarnings("unchecked")
	public static String retrieveHttpParametersAsString(HttpServletRequest req) {
		if (req == null)
			return "";

		Enumeration<String> enumer = req.getParameterNames();
		StringBuilder buffer = new StringBuilder();
		buffer.setLength(0);
		while (enumer.hasMoreElements()) {
			String paramName = enumer.nextElement();
			buffer.append(paramName + "=" + req.getParameter(paramName) + "&");
		}

		return buffer.toString();
	}

	/**
	 * This method retrieves all request parameteres from an input
	 * HttpServletRequest and returns a string rapresenting all parameters. This
	 * method supports single parameter encoding (name and value) to provide an
	 * useful method used also on frontend layer. Please set input boolean
	 * encoding parameters to false in case of using in a context of application
	 * logging.
	 * 
	 * @param req  Http Request containing parameters to extract.
	 * @param encodeParameterName Boolean value to enable name parameter encoding
	 * @param encodeParameterValue Boolean value to enable value parameter encoding
	 * @return String rappresentation of all http parameters (format is 
	 *         parameter1Name=parameter1Value&parameter2Name=parameter2Value).
	 */
	@SuppressWarnings("unchecked")
	public static String retrieveHttpParametersAsString(HttpServletRequest req, boolean encodeParameterName, boolean encodeParameterValue) {
		if (req == null)
			return "";

		Enumeration<String> enumer = req.getParameterNames();
		StringBuilder buffer = new StringBuilder();
		buffer.setLength(0);
		while (enumer.hasMoreElements()) {
			String paramName = enumer.nextElement();
			try {
				if (encodeParameterName)
					buffer.append(URLEncoder.encode(paramName, HttpUtils.ENCODING_SCHEME));
				else
					buffer.append(paramName);
				buffer.append("=");

				if (encodeParameterValue)
					buffer.append(URLEncoder.encode(
							req.getParameter(paramName),
							HttpUtils.ENCODING_SCHEME));
				else
					buffer.append(req.getParameter(paramName));
				buffer.append("&");
			} catch (UnsupportedEncodingException ueex) {
				buffer.append(paramName + "=" + HttpUtils.UNSUPPORTED_SCHEME);
			}
		}

		return buffer.toString();
	}
}
