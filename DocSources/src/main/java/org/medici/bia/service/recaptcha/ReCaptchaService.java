/*
 * ReCaptchaService.java
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
package org.medici.bia.service.recaptcha;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;

/**
 * This class implements business method to work with Recaptcha platform.
 * It's used to implements application antispam controls.
 *  
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public interface ReCaptchaService {

	/**
	 * Given in input, the address of client requester, the challenge string 
	 * specified by user, and response channel string, this method will query
	 * Recaptcha Servers to obtain the match between the recaptcha object
	 * and the string specified by the user.
	 * 
	 * @param remoteAddress {@link java.lang.String} client ip address
	 * @param challenge {@link java.lang.String} recaptcha string created by the 
	 * user
	 * @param response {@link java.lang.String} response string channel for 
	 * recaptcha
	 * @return {@link net.tanesha.recaptcha.ReCaptchaResponse} rapresenting the 
	 * remote response from Recaptcha Servers.
	 */
	public ReCaptchaResponse checkReCaptcha(String remoteAddress, String challenge, String response);

	/**
	 * This method return an RecaptchaObject without Secure Socket Layer.
	 * 
	 * @return {@link net.tanesha.recaptcha.ReCaptcha } object containing the 
	 * antispam control with link to http protocol
	 */
	public ReCaptcha getReCaptchaObjectNoSSL();

	/**
	 * This method return an RecaptchaObject with Secure Socket Layer.
	 * 
	 * @return {@link net.tanesha.recaptcha.ReCaptcha } object containing the 
	 * antispam control with link to https protocol.
	 */
	public ReCaptcha getReCaptchaObjectSSL();
}
