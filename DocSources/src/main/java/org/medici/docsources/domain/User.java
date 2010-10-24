/*
 * User.java
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
package org.medici.docsources.domain;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * User entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public class User implements Serializable {

	/**
	 * Class for mapping role application informations.<p><br>
	 * 
	 * 
	 * User Groups The defined user groups are: Administrators, On-site Fellows,
	 * Senior Distant Fellows, Distant Fellows, Digitization Technicians,
	 * Community users and Guests.<br> Some special role users are also present:
	 * Distant Fellow Coordinator, Community Coordinator and Digitization
	 * Coordinator.<br> For a detailed description of the each user group see the
	 * 2.4 chapter of Phase1_TLSRL.pdf document.<p>
	 * 
	 * Administrators Users belonging to this group are able to use all the
	 * Administration Module’s sub-modules: User Management, Reports and
	 * Revisions and System Management.<p>
	 * 
	 * On-site Fellows Users belonging to this group are able to use the Data
	 * Entry Module and have the permission to correct and vet other On-site and
	 * the Distant Fellows entered documents. They are part of the Scholarly
	 * Community and have also access to all the other modules, with the
	 * exception of the Digitization Module (see 2.7) and the Administration
	 * Module (see 2.6). The On-site Fellows can be assigned (or “flagged”) as
	 * Distant Fellows Coordinator or Community Coordinator or Digitization
	 * Coordinator.<p>
	 * 
	 * Senior Distant Fellows Users belonging to this group are experienced
	 * scholars that have been trained by the On-site Fellows on how to use the
	 * system. They able to use the Data Entry Module (see 2.4) and have the
	 * permission to correct and vet other Distant Fellows entered documents.
	 * They are part of the Scholarly Community and have also access to all the
	 * other modules, with the exception of the Digitization Module and the
	 * Administration Module. The Senior Distant Fellows can be assigned (or
	 * “flagged”) as Distant Fellows Coordinator or Community Coordinator.<p>
	 * 
	 * Distant Fellows Users belonging to this group are able to use the Data
	 * Entry Module. Unlike the On-site Fellows or the Senior Distant Fellows,
	 * the Distant Fellows are able to enter data but not to vet any document or
	 * volume or delete any content, which has not been created by them. They
	 * are part of the Scholarly Community and have also access to all the other
	 * modules, with the exception of the Digitization Module and the
	 * Administration Module.<p>
	 * 
	 * Community Users Users belonging to this group can access the Search
	 * Module. They can also access the Community module’s three sub-modules:
	 * comments, profiles and personal messages.<p>
	 * 
	 * Digitization Technicians Users belonging to this group are allowed to
	 * access the Digitization Module and are in charge of uploading the
	 * digitized documents to the system. The Digitization Technician is also
	 * belongs to the Community User Group so has a read-only access al the
	 * items contained into the database and can post comments.<p>
	 * 
	 * Guests These are the users that browse MAP’s system without logging with
	 * username and password. They can access the Search Module and the access
	 * to the Manuscript Viewer module though is forbidden. They can though fill
	 * in the User Registration Form to become regular Community Users. Every
	 * request is checked by the Community Coordinator and if accepted the user
	 * become a regular Community User.<p>
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 * 
	 */
	public static enum UserRole {
		ADMINISTRATORS("ADMINISTRATORS"), 
		COMMUNITY_USERS("COMMUNITY_USERS"), 
		DIGITIZATION_TECHNICIANS("DIGITIZATION_TECHNICIANS"), 
		DIGITIZATION_USERS("DIGITIZATION_USERS"), 
		DISTANT_FELLOWS("DISTANT_FELLOWS"), 
		GUESTS("GUESTS"), 
		ONSITE_FELLOWS("ONSITE_FELLOWS"), 
		SENIOR_DISTANT_FELLOWS("SENIOR_DISTANT_FELLOWS");

		private final String role;

		private UserRole(String value) {
			role = value;
		}

		@Override
		public String toString() {
			return role;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4232226019305595581L;
	private String account;
	private Boolean active;
	private String address;
	private String city;
	private String country;
	private Date expirationDate;
	private Date expirationPasswordDate;
	private String firstName;
	private String interests;
	private Integer invalidAccess;
	private Integer invalidAccessMax;
	private String lastName;
	private Boolean locked;
	private String mail;
	private String organization;
	private String password;
	private BufferedImage photo;
	private Date registrationDate;
	private String title;

	private List<UserRole> userRoles;

	public String getAccount() {
		return account;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @return the expirationPasswordDate
	 */
	public Date getExpirationPasswordDate() {
		return expirationPasswordDate;
	}

	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the interests
	 */
	public String getInterests() {
		return interests;
	}

	/**
	 * @return the invalidAccess
	 */
	public Integer getInvalidAccess() {
		return invalidAccess;
	}

	/**
	 * @return the invalidAccessMax
	 */
	public Integer getInvalidAccessMax() {
		return invalidAccessMax;
	}

	/**
	 * @return the surname
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the locked
	 */
	public Boolean getLocked() {
		return locked;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * @return the photo
	 */
	public BufferedImage getPhoto() {
		return photo;
	}

	/**
	 * @return the registrationDate
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @return person title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the userRole
	 */
	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @param expirationPasswordDate the expirationPasswordDate to set
	 */
	public void setExpirationPasswordDate(Date expirationPasswordDate) {
		this.expirationPasswordDate = expirationPasswordDate;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public void setInterests(String interests) {
		this.interests = interests;
	}

	/**
	 * @param invalidAccess the invalidAccess to set
	 */
	public void setInvalidAccess(Integer invalidAccess) {
		this.invalidAccess = invalidAccess;
	}

	/**
	 * @param invalidAccessMax the invalidAccessMax to set
	 */
	public void setInvalidAccessMax(Integer invalidAccessMax) {
		this.invalidAccessMax = invalidAccessMax;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	/**
	 * @param mail
	 *            the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(BufferedImage photo) {
		this.photo = photo;
	}

	/**
	 * @param registrationDate the registrationDate to set
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
}
