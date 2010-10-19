/*
 * SendUserActivationCodeCommand.java
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
package org.medici.docsources.command.user;

import javax.validation.constraints.NotNull;

/**
 * Command bean for action "send user activation code".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.docsources.controller.user.SendUserActivationCodeController
 */
public class SendUserActivationCodeCommand {
	@NotNull
	private String mail;
	@NotNull
	private String recaptcha_challenge_field;
	@NotNull
	private String recaptcha_response_field;
	@NotNull
	private String remoteAddress;

	/**
	 * This method returns mail property.
	 * 
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * This method returns recaptcha_challenge_field property.
	 * 
	 * @return the recaptcha_challenge_field
	 */
	public String getRecaptcha_challenge_field() {
		return recaptcha_challenge_field;
	}

	/**
	 * This method returns recaptcha_response_field property.
	 * 
	 * @return the recaptcha_response_field
	 */
	public String getRecaptcha_response_field() {
		return recaptcha_response_field;
	}

	/**
	 * This method returns the remote address of user client. It's used by
	 * ReCaptcha (antispam) control to validate captcha image.
	 * 
	 * @return the remoteAddress (Internet Address) of user client.
	 */
	public String getRemoteAddress() {
		return remoteAddress;
	}

	/**
	 * This method sets mail property.
	 * 
	 * @param mail
	 *            the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * This method sets recaptcha_challenge_field property. It's used by
	 * ReCaptcha (antispam) control to validate captcha image.
	 * 
	 * @param recaptcha_challenge_field
	 *            the recaptcha_challenge_field to set
	 */
	public void setRecaptcha_challenge_field(String recaptcha_challenge_field) {
		this.recaptcha_challenge_field = recaptcha_challenge_field;
	}

	/**
	 * This method sets recaptcha_response_field property. It's used by
	 * ReCaptcha (antispam) control to validate captcha image.
	 * 
	 * @param recaptcha_response_field
	 *            the recaptcha_response_field to set
	 */
	public void setRecaptcha_response_field(String recaptcha_response_field) {
		this.recaptcha_response_field = recaptcha_response_field;
	}

	/**
	 * This method sets remoteAddress property. It's used by ReCaptcha
	 * (antispam) control to validate captcha image.
	 * 
	 * @param remoteAddress
	 *            the remoteAddress to set
	 */
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
}
