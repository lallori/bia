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
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Table ( name = "\"tblUser\"" ) 
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4232226019305595581L;
	@Id
	@Column (name="\"account\"", length=50, nullable=false)
	private String account;
	@Column (name="\"address\"", length=200, nullable=true)
	private String address;
	@Column (name="\"city\"", length=50, nullable=false)
	private String city;
	@Column (name="\"country\"", length=3, nullable=false)
	private String country;
	@Column (name="\"firstName\"", length=50, nullable=false)
	private String firstName;
	@Column (name="\"initials\"", length=5, nullable=false)
	private String initials;
	@Column (name="\"interests\"", length=500, nullable=false)
	private String interests;
	@Column (name="\"lastName\"", length=50, nullable=false)
	private String lastName;
	@Column (name="\" mail\"", length=50, nullable=false)
	private String mail;
	@Column (name="\"organization\"", length=50, nullable=false)
	private String organization;
	@Column (name="\"password\"", length=64, nullable=false)
	private String password;
	@Column (name="\"title\"", length=50, nullable=true)
	private String title;
	@Column (name="\"photo\"", nullable=true)
	@Lob
	private BufferedImage photo;

	@Column (name="\"active\"", nullable=false, columnDefinition="BIT default 0")
	private Boolean active;
	@Column (name="\"approved\"", nullable=false, columnDefinition="BIT default 0")
	private Boolean approved;
	@Column (name="\"locked\"", nullable=false, columnDefinition="BIT default 0")
	private Boolean locked;
	@Column (name="\"badLogin\"", nullable=false, columnDefinition="INT default '0'")
	private Integer badLogin;
	@Column (name="\"activationDate\"")
	private Date activationDate;
	@Column (name="\"currentLoginDate\"")
	private Date currentLoginDate;
	@Column (name="\"lastLoginDate\"")
	private Date lastLoginDate;
	@Column (name="\"expirationDate\"", nullable=false)
	private Date expirationDate;
	@Column (name="\"expirationPasswordDate\"", nullable=false)
	private Date expirationPasswordDate;
	@Column (name="\"lastPasswordChangeDate\"")
	private Date lastPasswordChangeDate;
	@Column (name="\"registrationDate\"", nullable=false)
	private Date registrationDate;
	@Column (name="\"forumNumberOfPost\"", nullable=false, columnDefinition="BIGINT default '0'")
	private Long forumNumberOfPost;
	@Column (name="\"forumJoinedDate\"", nullable=true)
	private Date forumJoinedDate;
	@Column (name="\"lastForumPostDate\"", nullable=true)
	private Date lastForumPostDate;
	@Column (name="\"lastActiveForumDate\"", nullable=true)
	private Date lastActiveForumDate;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private Set<UserRole> userRoles;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private Set<ForumTopic> forumTopics;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private Set<ForumPost> forumPosts;
	/**
	 * Default constructor.
	 */
	public User() {
		super();
	}

	/**
	 * 
	 * @param newAccount
	 */
	public User(String newAccount) {
		super();
		setAccount(newAccount);
	}

	public String getAccount() {
		return account;
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
	 * 
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the initials
	 */
	public String getInitials() {
		return initials;
	}

	/**
	 * @return the interests
	 */
	public String getInterests() {
		return interests;
	}

	/**
	 * @return the surname
	 */
	public String getLastName() {
		return lastName;
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
	 * @return person title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the userRole
	 */
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setAccount(String account) {
		this.account = account;
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
	 * 
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param initial the initial to set
	 */
	public void setInitials(String initials) {
		this.initials = initials;
	}

	/**
	 * @param info the info to set
	 */
	public void setInterests(String interests) {
		this.interests = interests;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the activationDate
	 */
	public Date getActivationDate() {
		return activationDate;
	}

	/**
	 * @param activationDate the activationDate to set
	 */
	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the approved
	 */
	public Boolean getApproved() {
		return approved;
	}

	/**
	 * @param approved the approved to set
	 */
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	/**
	 * @return the badLogin
	 */
	public Integer getBadLogin() {
		return badLogin;
	}

	/**
	 * @param badLogin the badLogin to set
	 */
	public void setBadLogin(Integer badLogin) {
		this.badLogin = badLogin;
	}

	/**
	 * @return the currentLoginDate
	 */
	public Date getCurrentLoginDate() {
		return currentLoginDate;
	}

	/**
	 * @param currentLoginDate the currentLoginDate to set
	 */
	public void setCurrentLoginDate(Date currentLoginDate) {
		this.currentLoginDate = currentLoginDate;
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the expirationPasswordDate
	 */
	public Date getExpirationPasswordDate() {
		return expirationPasswordDate;
	}

	/**
	 * @param expirationPasswordDate the expirationPasswordDate to set
	 */
	public void setExpirationPasswordDate(Date expirationPasswordDate) {
		this.expirationPasswordDate = expirationPasswordDate;
	}

	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the lastPasswordChangeDate
	 */
	public Date getLastPasswordChangeDate() {
		return lastPasswordChangeDate;
	}

	/**
	 * @param lastPasswordChangeDate the lastPasswordChangeDate to set
	 */
	public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
		this.lastPasswordChangeDate = lastPasswordChangeDate;
	}

	/**
	 * @return the locked
	 */
	public Boolean getLocked() {
		return locked;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	/**
	 * @return the registrationDate
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate the registrationDate to set
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @return the forumNumberOfPost
	 */
	public Long getForumNumberOfPost() {
		return forumNumberOfPost;
	}

	/**
	 * @param forumNumberOfPost the forumNumberOfPost to set
	 */
	public void setForumNumberOfPost(Long forumNumberOfPost) {
		this.forumNumberOfPost = forumNumberOfPost;
	}

	/**
	 * @return the forumJoinedDate
	 */
	public Date getForumJoinedDate() {
		return forumJoinedDate;
	}

	/**
	 * @param forumJoinedDate the forumJoinedDate to set
	 */
	public void setForumJoinedDate(Date forumJoinedDate) {
		this.forumJoinedDate = forumJoinedDate;
	}

	/**
	 * @return the lastForumPostDate
	 */
	public Date getLastForumPostDate() {
		return lastForumPostDate;
	}

	/**
	 * @param lastForumPostDate the lastForumPostDate to set
	 */
	public void setLastForumPostDate(Date lastForumPostDate) {
		this.lastForumPostDate = lastForumPostDate;
	}

	/**
	 * @return the lastActiveForumDate
	 */
	public Date getLastActiveForumDate() {
		return lastActiveForumDate;
	}

	/**
	 * @param lastActiveForumDate the lastActiveForumDate to set
	 */
	public void setLastActiveForumDate(Date lastActiveForumDate) {
		this.lastActiveForumDate = lastActiveForumDate;
	}

	/**
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
}
