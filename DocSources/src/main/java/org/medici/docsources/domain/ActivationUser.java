/*
 * ActivationUser.java
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

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity for store user's activation.
 * 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Table ( name = "\"tblActivationUser\"" ) 
public class ActivationUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2520604342480766749L;

	@Id
	@Column (name="\"UUID\"", length=50, nullable=false)
	private String uuid;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"account\"", nullable=true)
	private User user;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"RequestDate\"", nullable=false)
	private Date requestDate;
	@Column (name="\"RequestIpAddress\"", length=50, nullable=false)
	private String requestIPAddress;
	@Column (name="\"MailSended\"", nullable=false)
	private Boolean mailSended;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"MailSendedDate\"")
	private Date mailSendedDate;
	@Column (name="\"active\"", length=30, nullable=false)
	private Boolean active;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"ActivationDate\"")
	private Date activationDate;
	@Column (name="\"ActivationIpAddress\"", length=50)
	private String activationIPAddress;

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the requestDate
	 */
	public Date getRequestDate() {
		return requestDate;
	}
	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	/**
	 * @return the requestIPAddress
	 */
	public String getRequestIPAddress() {
		return requestIPAddress;
	}
	/**
	 * @param requestIPAddress the requestIPAddress to set
	 */
	public void setRequestIPAddress(String requestIPAddress) {
		this.requestIPAddress = requestIPAddress;
	}
	/**
	 * @return the mailSended
	 */
	public Boolean getMailSended() {
		return mailSended;
	}
	/**
	 * @param mailSended the mailSended to set
	 */
	public void setMailSended(Boolean mailSended) {
		this.mailSended = mailSended;
	}
	/**
	 * @return the mailSendedDate
	 */
	public Date getMailSendedDate() {
		return mailSendedDate;
	}
	/**
	 * @param mailSendedDate the mailSendedDate to set
	 */
	public void setMailSendedDate(Date mailSendedDate) {
		this.mailSendedDate = mailSendedDate;
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
	 * @param activationDate the activationDate to set
	 */
	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}
	/**
	 * @return the activationDate
	 */
	public Date getActivationDate() {
		return activationDate;
	}
	/**
	 * @param activationIPAddress the activationIPAddress to set
	 */
	public void setActivationIPAddress(String activationIPAddress) {
		this.activationIPAddress = activationIPAddress;
	}
	/**
	 * @return the activationIPAddress
	 */
	public String getActivationIPAddress() {
		return activationIPAddress;
	}
}
