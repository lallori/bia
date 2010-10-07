/*
 * ReCaptchaServiceImpl.java
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
package org.medici.docsources.service.recaptcha;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.medici.docsources.support.recaptcha.ReCaptchaConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Service
public class ReCaptchaServiceImpl implements ReCaptchaService {
	@Autowired
	private ReCaptcha reCaptcha;
	@Autowired
	private ReCaptchaConfiguration reCaptchaConfiguration;

	/**
	 * 
	 */
	public ReCaptchaResponse checkReCaptcha(String remoteAddress,
			String challenge, String response) {
		return getReCaptcha().checkAnswer(remoteAddress, challenge, response);
	}

	/**
	 * @return the reCaptcha
	 */
	public ReCaptcha getReCaptcha() {
		return reCaptcha;
	}

	/**
	 * 
	 * @return
	 */
	private ReCaptchaConfiguration getReCaptchaConfiguration() {
		return reCaptchaConfiguration;
	}

	/**
	 * 
	 */
	public ReCaptcha getReCaptchaObjectNoSSL() {
		return ReCaptchaFactory.newReCaptcha(getReCaptchaConfiguration()
				.getPublicKey(), getReCaptchaConfiguration().getPrivateKey(),
				false);
	}

	/**
	 * 
	 */
	public ReCaptcha getReCaptchaObjectSSL() {
		return ReCaptchaFactory.newSecureReCaptcha(getReCaptchaConfiguration()
				.getPublicKey(), getReCaptchaConfiguration().getPrivateKey(),
				false);
	}

	/**
	 * @param reCaptcha
	 *            the reCaptcha to set
	 */
	public void setReCaptcha(ReCaptcha reCaptcha) {
		this.reCaptcha = reCaptcha;
	}

	/**
	 * 
	 * @param reCaptchaConfiguration
	 */
	@SuppressWarnings("unused")
	private void setReCaptchaConfiguration(
			ReCaptchaConfiguration reCaptchaConfiguration) {
		this.reCaptchaConfiguration = reCaptchaConfiguration;
	}

}
