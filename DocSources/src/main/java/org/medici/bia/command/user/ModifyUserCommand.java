/*
 * ModifyUserCommand.java
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
package org.medici.bia.command.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Command bean for action "modify user".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.merdici.docsources.controller.user.ModifyUserController
 */
public class ModifyUserCommand {
	@NotNull
	@Size(min = 4, max = 15)
	private String account;
	private String address;
	private String city;
	@Size(min = 8, max = 15)
	private String confirmPassword;
	/*
	 * @NotNull
	 * 
	 * @Size(min=2, max=2)
	 */
	private String countryCode;
	@NotNull
	private String countryDescription;
	@NotNull
	private String firstName;
	@NotNull
	private String interests;
	@NotNull
	private String lastName;
	@NotNull
	private String mail;
	private String organization;
	@NotNull
	private String password;
	private CommonsMultipartFile photo;

	private String title;

	/**
	 * This method returns account property.
	 * 
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * This method returns account property.
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * This method returns account property.
	 * 
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * This method returns account property.
	 * 
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * This method returns account property.
	 * 
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * This method returns account property.
	 * 
	 * @return the countryDescription
	 */
	public String getCountryDescription() {
		return countryDescription;
	}

	/**
	 * This method returns account property.
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * This method returns account property.
	 * 
	 * @return the interests
	 */
	public String getInterests() {
		return interests;
	}

	/**
	 * This method returns account property.
	 * 
	 * @return the surname
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * This method returns account property.
	 * 
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * This method returns account property.
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
	 * This method returns account property.
	 * 
	 * @return the photo
	 */
	public CommonsMultipartFile getPhoto() {
		return photo;
	}

	/**
	 * This method returns account property.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method sets account property.
	 * 
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * This method sets address property.
	 * 
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * This method sets city name property.
	 * 
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * This method sets confirmPassword property.
	 * 
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * This method sets countryCode property.
	 * 
	 * @param countryCode
	 *            the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * This method sets country descritpion property.
	 * 
	 * @param countryDescription
	 *            the countryDescription to set
	 */
	public void setCountryDescription(String countryDescription) {
		this.countryDescription = countryDescription;
	}

	/**
	 * This method sets firstName property.
	 * 
	 * @param firstName
	 *            the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * This method sets interets property.
	 * 
	 * @param interests
	 *            interests to set
	 */
	public void setInterests(String interests) {
		this.interests = interests;
	}

	/**
	 * This method sets account property.
	 * 
	 * @param surname
	 *            the surname to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * This method sets organization property.
	 * 
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * This method sets password property.
	 * 
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * This method sets photo property.
	 * 
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(CommonsMultipartFile photo) {
		this.photo = photo;
	}

	/**
	 * This method sets user's title property.
	 * 
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
