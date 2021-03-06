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
package org.medici.bia.domain;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.bridge.builtin.BooleanBridge;

/**
 * User entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Entity
@Table ( name = "\"tblUser\"" ) 
public class User implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4232226019305595581L;
	@Id
	@Column (name="\"account\"", length=50, nullable=false)
	private String account;
	@Column (name="\"activationDate\"")
	private Date activationDate;
	@Column (name="\"active\"", nullable=false, columnDefinition="BIT default 0")
	private Boolean active;
	@Column (name="\"address\"", length=200, nullable=true)
	private String address;
	@Column (name="\"approved\"", nullable=false, columnDefinition="BIT default 0")
	private Boolean approved;
	@ManyToOne
	@JoinColumn(name="\"approvedBy\"")
	private User approvedBy;
	@Column (name="\"badLogin\"", nullable=false, columnDefinition="INT default '0'")
	private Integer badLogin;
	@Column (name="\"city\"", length=50, nullable=true)
	private String city;
	@Column (name="\"country\"", length=3, nullable=false)
	private String country;
	@Column (name="\"currentLoginDate\"")
	private Date currentLoginDate;
	@Column (name="\"expirationDate\"", nullable=false)
	private Date expirationDate;
	@Column (name="\"expirationPasswordDate\"", nullable=false)
	private Date expirationPasswordDate;
	@Column (name="\"firstName\"", length=50, nullable=false)
	private String firstName;
	@Column (name="\"forumJoinedDate\"", nullable=true)
	private Date forumJoinedDate;

	@Column (name="\"forumNumberOfPost\"", nullable=false, columnDefinition="BIGINT default '0'")
	private Long forumNumberOfPost;
	@Column (name="\"initials\"", length=5)
	private String initials;
	@Column (name="\"interests\"", length=500)
	private String interests;
	@Column (name="\"lastActiveForumDate\"", nullable=true)
	private Date lastActiveForumDate;
	@Column (name="\"lastForumPostDate\"", nullable=true)
	private Date lastForumPostDate;
	@Column (name="\"lastLoginDate\"")
	private Date lastLoginDate;
	@Column (name="\"lastLogoutDate\"")
	private Date lastLogoutDate;
	@Column (name="\"lastName\"", length=50, nullable=false)
	private String lastName;
	@Column (name="\"lastPasswordChangeDate\"")
	private Date lastPasswordChangeDate;
	@Column (name="\"locked\"", nullable=false, columnDefinition="BIT default 0")
	private Boolean locked;
	@Column (name="\"mail\"", length=50, nullable=false)
	private String mail;
	@Column (name="\"mailHide\"", nullable=false, columnDefinition="BIT default 0")
	private Boolean mailHide;
	@Column (name="\"mailNotification\"", nullable=false, columnDefinition="BIT default 0")
	private Boolean mailNotification;
	@Column (name="\"forumTopicSubscription\"", nullable=false, columnDefinition="BIT default 1")
	private Boolean forumTopicSubscription;
	@Column (name="\"middleName\"", length=20, nullable=false)
	private String middleName;
	@Column (name="\"organization\"", length=50, nullable=false)
	private String organization;
	@Column (name="\"password\"", length=64, nullable=false)
	private String password;
	@Column (name="\"photo\"", nullable=true)
	@Lob
	private BufferedImage photo;
	
	@Column (name="\"PORTRAIT\"", length=1, columnDefinition="TINYINT", nullable=false)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean portrait;
	
	@Column (name="\"portraitImageName\"", length=100)
	private String portraitImageName;

	@Column (name="\"registrationDate\"", nullable=false)
	private Date registrationDate;

	@Column (name="\"title\"", length=50, nullable=true)
	private String title;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade={CascadeType.MERGE } )
	private Set<UserRole> userRoles;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade={CascadeType.MERGE } )
	private Set<ActivationUser> activationsUser;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade={CascadeType.MERGE } )
	private Set<ApprovationUser> approvationsUser;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade={CascadeType.MERGE } )
	private Set<ForumTopic> forumTopics;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade={CascadeType.MERGE } )
	private Set<ForumPost> forumPosts;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade={CascadeType.MERGE } )
	private Set<ForumTopicWatch> forumTopicsWatch;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade={CascadeType.MERGE } )
	private Set<LockedUser> lockedsUser;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade={CascadeType.MERGE } )
	private Set<UserHistory> userHistory;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade={CascadeType.MERGE } )
	private Set<UserMarkedList> userMarkedList;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade={CascadeType.MERGE } )
	private Set<UserPersonalNotes> userPersonalNotes;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade={CascadeType.MERGE } )
	private Set<UserMessage> userMessage;
	
	@OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade={CascadeType.MERGE } )
	private Set<VettingHistory> vettingHistory;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}
		
		if (! (obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (getAccount() == null) {
			if (other.getAccount() != null) {
				return false;
			}
		} else if (!getAccount().equals(other.getAccount())) {
			return false;
		}
	
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		User clone=(User)super.clone();
		return clone;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[");
		stringBuilder.append("account=");
		stringBuilder.append(getAccount());
		stringBuilder.append(", firstName=");
		stringBuilder.append(getFirstName());

		stringBuilder.append(", lastName=");
		stringBuilder.append(getLastName());
		stringBuilder.append(']');

		return stringBuilder.toString();
	}

	/**
	 * 
	 * @return
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @return the activationDate
	 */
	public Date getActivationDate() {
		return activationDate;
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
	 * @return the approved
	 */
	public Boolean getApproved() {
		return approved;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the approvedBy
	 */
	public User getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @return the badLogin
	 */
	public Integer getBadLogin() {
		return badLogin;
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
	 * @return the currentLoginDate
	 */
	public Date getCurrentLoginDate() {
		return currentLoginDate;
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

	/**
	 * 
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the forumJoinedDate
	 */
	public Date getForumJoinedDate() {
		return forumJoinedDate;
	}

	/**
	 * @return the forumNumberOfPost
	 */
	public Long getForumNumberOfPost() {
		return forumNumberOfPost;
	}

	/**
	 * @return the forumPosts
	 */
	public Set<ForumPost> getForumPosts() {
		return forumPosts;
	}

	/**
	 * @return the forumTopics
	 */
	public Set<ForumTopic> getForumTopics() {
		return forumTopics;
	}

	/**
	 * @return the forumTopicsWatch
	 */
	public Set<ForumTopicWatch> getForumTopicsWatch() {
		return forumTopicsWatch;
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
	 * @return the lastActiveForumDate
	 */
	public Date getLastActiveForumDate() {
		return lastActiveForumDate;
	}

	/**
	 * @return the lastForumPostDate
	 */
	public Date getLastForumPostDate() {
		return lastForumPostDate;
	}

	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @return the surname
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the lastPasswordChangeDate
	 */
	public Date getLastPasswordChangeDate() {
		return lastPasswordChangeDate;
	}

	/**
	 * @return the locked
	 */
	public Boolean getLocked() {
		return locked;
	}

	/**
	 * @return the lockedsUser
	 */
	public Set<LockedUser> getLockedsUser() {
		return lockedsUser;
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
	 * @return the portrait
	 */
	public Boolean getPortrait() {
		return portrait;
	}

	/**
	 * @return the portraitImageName
	 */
	public String getPortraitImageName() {
		return portraitImageName;
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
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @param activationDate the activationDate to set
	 */
	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
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
	 * @param approved the approved to set
	 */
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	/**
	 * @param badLogin the badLogin to set
	 */
	public void setBadLogin(Integer badLogin) {
		this.badLogin = badLogin;
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
	 * @param currentLoginDate the currentLoginDate to set
	 */
	public void setCurrentLoginDate(Date currentLoginDate) {
		this.currentLoginDate = currentLoginDate;
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

	/**
	 * 
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param forumJoinedDate the forumJoinedDate to set
	 */
	public void setForumJoinedDate(Date forumJoinedDate) {
		this.forumJoinedDate = forumJoinedDate;
	}

	/**
	 * @param forumNumberOfPost the forumNumberOfPost to set
	 */
	public void setForumNumberOfPost(Long forumNumberOfPost) {
		this.forumNumberOfPost = forumNumberOfPost;
	}

	/**
	 * @param forumPosts the forumPosts to set
	 */
	public void setForumPosts(Set<ForumPost> forumPosts) {
		this.forumPosts = forumPosts;
	}

	/**
	 * @param forumTopics the forumTopics to set
	 */
	public void setForumTopics(Set<ForumTopic> forumTopics) {
		this.forumTopics = forumTopics;
	}

	/**
	 * @param forumTopicsWatch the forumTopicsWatch to set
	 */
	public void setForumTopicsWatch(Set<ForumTopicWatch> forumTopicsWatch) {
		this.forumTopicsWatch = forumTopicsWatch;
	}

	/**
	 * @param forumTopicSubscription the forumTopicSubscription to set
	 */
	public void setForumTopicSubscription(Boolean forumTopicSubscription) {
		this.forumTopicSubscription = forumTopicSubscription;
	}

	/**
	 * @return the forumTopicSubscription
	 */
	public Boolean getForumTopicSubscription() {
		return forumTopicSubscription;
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
	 * @param lastActiveForumDate the lastActiveForumDate to set
	 */
	public void setLastActiveForumDate(Date lastActiveForumDate) {
		this.lastActiveForumDate = lastActiveForumDate;
	}

	/**
	 * @param lastForumPostDate the lastForumPostDate to set
	 */
	public void setLastForumPostDate(Date lastForumPostDate) {
		this.lastForumPostDate = lastForumPostDate;
	}

	/**
	 * @param lastLoginDate the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * 
	 * @param lastLogoutDate
	 */
	public void setLastLogoutDate(Date lastLogoutDate) {
		this.lastLogoutDate = lastLogoutDate;
	}

	/**
	 * 
	 * @return
	 */
	public Date getLastLogoutDate() {
		return lastLogoutDate;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param lastPasswordChangeDate the lastPasswordChangeDate to set
	 */
	public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
		this.lastPasswordChangeDate = lastPasswordChangeDate;
	}

	/**
	 * @param locked the locked to set
	 */
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	/**
	 * @param lockedsUser the lockedsUser to set
	 */
	public void setLockedsUser(Set<LockedUser> lockedsUser) {
		this.lockedsUser = lockedsUser;
	}

	/**
	 * @param mail
	 *            the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setMailHide(Boolean mailHide) {
		this.mailHide = mailHide;
	}

	public Boolean getMailHide() {
		return mailHide;
	}

	public void setMailNotification(Boolean mailNotification) {
		this.mailNotification = mailNotification;
	}

	public Boolean getMailNotification() {
		return mailNotification;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
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
	 * @param portrait the portrait to set
	 */
	public void setPortrait(Boolean portrait) {
		this.portrait = portrait;
	}

	/**
	 * @param portraitImageName the portraitImageName to set
	 */
	public void setPortraitImageName(String portraitImageName) {
		this.portraitImageName = portraitImageName;
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
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	/**
	 * @return the activationsUser
	 */
	public Set<ActivationUser> getActivationsUser() {
		return activationsUser;
	}

	/**
	 * @param activationsUser the activationsUser to set
	 */
	public void setActivationsUser(Set<ActivationUser> activationsUser) {
		this.activationsUser = activationsUser;
	}

	/**
	 * @return the approvationsUser
	 */
	public Set<ApprovationUser> getApprovationsUser() {
		return approvationsUser;
	}

	/**
	 * @param approvationsUser the approvationsUser to set
	 */
	public void setApprovationsUser(Set<ApprovationUser> approvationsUser) {
		this.approvationsUser = approvationsUser;
	}

	/**
	 * @return the userMarkedList
	 */
	public Set<UserMarkedList> getUserMarkedList() {
		return userMarkedList;
	}

	/**
	 * @param userHistory the userHistory to set
	 */
	public void setUserHistory(Set<UserHistory> userHistory) {
		this.userHistory = userHistory;
	}

	/**
	 * @return the userHistory
	 */
	public Set<UserHistory> getUserHistory() {
		return userHistory;
	}

	/**
	 * @param userMarkedList the userMarkedList to set
	 */
	public void setUserMarkedList(Set<UserMarkedList> userMarkedList) {
		this.userMarkedList = userMarkedList;
	}

	/**
	 * @return the userPersonalNotes
	 */
	public Set<UserPersonalNotes> getUserPersonalNotes() {
		return userPersonalNotes;
	}

	/**
	 * @param userPersonalNotes the userPersonalNotes to set
	 */
	public void setUserPersonalNotes(Set<UserPersonalNotes> userPersonalNotes) {
		this.userPersonalNotes = userPersonalNotes;
	}

	/**
	 * @return the userMessage
	 */
	public Set<UserMessage> getUserMessage() {
		return userMessage;
	}

	/**
	 * @param userMessage the userMessage to set
	 */
	public void setUserMessage(Set<UserMessage> userMessage) {
		this.userMessage = userMessage;
	}

	/**
	 * @return the vettingHistory
	 */
	public Set<VettingHistory> getVettingHistory() {
		return vettingHistory;
	}

	/**
	 * @param vettingHistory the vettingHistory to set
	 */
	public void setVettingHistory(Set<VettingHistory> vettingHistory) {
		this.vettingHistory = vettingHistory;
	}
}
