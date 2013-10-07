/*
 * ApprovationUser.java
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
@Table ( name = "\"tblApprovationUser\"" ) 
public class ApprovationUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8840387704967604225L;

	@Id
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"account\"", nullable=true)
	private User user;
	@Column (name="\"messageSended\"", nullable=false)
	private Boolean messageSended;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"messageSendedDate\"")
	private Date messageSendedDate;
	@Column (name="\"approved\"", nullable=false)
	private Boolean approved;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"approvedDate\"")
	private Date approvedDate;
	@Column (name="\"mailSended\"", nullable=false)
	private Boolean mailSended;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"mailSendedDate\"")
	private Date mailSendedDate;
	
	/**
	 * 
	 * @param string
	 */
	public ApprovationUser(User user) {
		super();
		
		setUser(user);
	}

	/**
	 * default constructor.
	 */
	public ApprovationUser() {
		super();
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
	 * @return the messageSended
	 */
	public Boolean getMessageSended() {
		return messageSended;
	}

	/**
	 * @param messageSended the messageSended to set
	 */
	public void setMessageSended(Boolean messageSended) {
		this.messageSended = messageSended;
	}

	/**
	 * @return the messageSendedDate
	 */
	public Date getMessageSendedDate() {
		return messageSendedDate;
	}

	/**
	 * @param messageSendedDate the messageSendedDate to set
	 */
	public void setMessageSendedDate(Date messageSendedDate) {
		this.messageSendedDate = messageSendedDate;
	}

	/**
	 * @param approved the approved to set
	 */
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	/**
	 * @return the approved
	 */
	public Boolean getApproved() {
		return approved;
	}

	/**
	 * @param mailSended the mailSended to set
	 */
	public void setMailSended(Boolean mailSended) {
		this.mailSended = mailSended;
	}

	/**
	 * @return the mailSended
	 */
	public Boolean getMailSended() {
		return mailSended;
	}

	/**
	 * @param mailSendedDate the mailSendedDate to set
	 */
	public void setMailSendedDate(Date mailSendedDate) {
		this.mailSendedDate = mailSendedDate;
	}

	/**
	 * @return the mailSendedDate
	 */
	public Date getMailSendedDate() {
		return mailSendedDate;
	}

	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	/**
	 * @return the approvedDate
	 */
	public Date getApprovedDate() {
		return approvedDate;
	}
}
