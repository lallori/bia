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
package org.medici.bia.service.recaptcha;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.medici.bia.common.property.ApplicationPropertyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Service
@Transactional(readOnly=true)
public class ReCaptchaServiceImpl implements ReCaptchaService {
	@Autowired
	private ReCaptcha reCaptcha;

	/**
	 * {@inheritDoc}
	 */
	public ReCaptchaResponse checkReCaptcha(String remoteAddress, String challenge, String response) {
		return getReCaptcha().checkAnswer(remoteAddress, challenge, response);
	}

	/**
	 * @return the reCaptcha
	 */
	public ReCaptcha getReCaptcha() {
		return reCaptcha;
	}

	/**
	 * {@inheritDoc}
	 */
	public ReCaptcha getReCaptchaObjectNoSSL() {
		return ReCaptchaFactory.newReCaptcha(ApplicationPropertyManager.getApplicationProperty("recaptcha.publicKey"),
											 ApplicationPropertyManager.getApplicationProperty("recaptcha.privateKey"),
											 false);
	}

	/**
	 * {@inheritDoc}
	 */
	public ReCaptcha getReCaptchaObjectSSL() {
		return ReCaptchaFactory.newSecureReCaptcha(ApplicationPropertyManager.getApplicationProperty("recaptcha.publicKey"),
				 								   ApplicationPropertyManager.getApplicationProperty("recaptcha.privateKey"),
												   false);
	}

	/**
	 * @param reCaptcha the reCaptcha to set
	 */
	public void setReCaptcha(ReCaptcha reCaptcha) {
		this.reCaptcha = reCaptcha;
	}
}
