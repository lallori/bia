/*
 * RegisterUserCommand.java
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
import javax.validation.constraints.Size;

/**
 * Command bean for action "register user".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.merdici.docsources.controller.user.RegisterUserController
 */
public class RegisterUserCommand {
	private String address;
	@NotNull
	private Boolean agree;
	private String city;
	@NotNull
	@Size(min = 8, max = 15)
	private String confirmPassword;
	/**
	 * @TODO :
	 * @NotNull
	 * @Size(min=2, max=2)
	 */
	private String countryCode;
	@NotNull
	private String countryDescription;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String mail;
	private String organization;
	@NotNull
	@Size(min = 8, max = 15)
	private String password;
	@NotNull
	private String recaptcha_challenge_field;
	@NotNull
	private String recaptcha_response_field;
	@NotNull
	private String remoteAddress;
	private String title;


	/**
	 * This method returns address property.
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * This method returns agreement property.
	 * 
	 * @return the agree
	 */
	public Boolean getAgree() {
		return agree;
	}

	/**
	 * This method returns city property.
	 * 
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * This method returns confirmPassword property.
	 * 
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * This method returns countryCode property.
	 * 
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * This method returns countryDescription property.
	 * 
	 * @return the countryDescription
	 */
	public String getCountryDescription() {
		return countryDescription;
	}

	/**
	 * This method returns firstName property.
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * This method returns surname property.
	 * 
	 * @return the surname
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * This method returns mail property.
	 * 
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * This method returns organization property.
	 * 
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * This method returns password property.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * This method returns recaptcha_challenge_field property.It's used by
	 * ReCaptcha (antispam) control to validate captcha image.
	 * 
	 * @return the recaptcha_challenge_field
	 */
	public String getRecaptcha_challenge_field() {
		return recaptcha_challenge_field;
	}

	/**
	 * This method returns recaptcha_response_field property.It's used by
	 * ReCaptcha (antispam) control to validate captcha image.
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
	 * This method returns title property.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method sets address property.
	 * 
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * This method sets agreement property.
	 * 
	 * @param agree the agree to set
	 */
	public void setAgree(Boolean agree) {
		this.agree = agree;
	}

	/**
	 * This method sets city property.
	 * 
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * This method sets confirmation password property.
	 * 
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * This method sets country code property.
	 * 
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * This method sets country description property.
	 * 
	 * @param countryDescription the countryDescription to set
	 */
	public void setCountryDescription(String countryDescription) {
		this.countryDescription = countryDescription;
	}

	/**
	 * This method sets first name property.
	 * 
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * This method sets surname property.
	 * 
	 * @param surname the surname to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * This method sets mail property.
	 * 
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * This method sets organization property.
	 * 
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * This method sets password property.
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * This method sets recaptcha_challenge_field property.It's used by
	 * ReCaptcha (antispam) control to validate captcha image.
	 * 
	 * @param recaptcha_challenge_field the recaptcha_challenge_field to set
	 */
	public void setRecaptcha_challenge_field(String recaptcha_challenge_field) {
		this.recaptcha_challenge_field = recaptcha_challenge_field;
	}

	/**
	 * This method sets recaptcha_response_field property.It's used by ReCaptcha
	 * (antispam) control to validate captcha image.
	 * 
	 * @param recaptcha_response_field the recaptcha_response_field to set
	 */
	public void setRecaptcha_response_field(String recaptcha_response_field) {
		this.recaptcha_response_field = recaptcha_response_field;
	}

	/**
	 * This method sets remote address property.It's used by ReCaptcha
	 * (antispam) control to validate captcha image.
	 * 
	 * @param remoteAddress the remoteAddress to set
	 */
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	/**
	 * This method sets title property.
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
