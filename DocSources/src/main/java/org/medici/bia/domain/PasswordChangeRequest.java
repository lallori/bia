/*
 * PasswordChangeRequest.java
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
 * Entity for store request of change password.
 * 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Table ( name = "\"tblPasswordChangeRequest\"" ) 
public class PasswordChangeRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4069049433392567124L;
	@Id
	@Column (name="\"UUID\"", length=50, nullable=false)
	private String uuid;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"account\"", nullable=true)
	private User user;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"REQUESTDATE\"", nullable=false)
	private Date requestDate;
	@Column (name="\"REQUESTIPADDRESS\"", length=50, nullable=false)
	private String requestIPAddress;
	@Column (name="\"MAILSENDED\"", nullable=false)
	private Boolean mailSended;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"MAILSENDEDDATE\"")
	private Date mailSendedDate;
	@Column (name="\"RESET\"", length=30, nullable=false)
	private Boolean reset;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"RESETDATE\"")
	private Date resetDate;
	@Column (name="\"RESETIPADDRESS\"", length=50)
	private String resetIPAddress;

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
	 * @param account the user to set
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
	 * @return the reset
	 */
	public Boolean getReset() {
		return reset;
	}
	
	/**
	 * @param reset the reset to set
	 */
	public void setReset(Boolean reset) {
		this.reset = reset;
	}
	
	/**
	 * @return the resetDate
	 */
	public Date getResetDate() {
		return resetDate;
	}
	
	/**
	 * @param resetDate the resetDate to set
	 */
	public void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}
	
	/**
	 * @return the resetIPAddress
	 */
	public String getResetIPAddress() {
		return resetIPAddress;
	}
	
	/**
	 * @param resetIPAddress the resetIPAddress to set
	 */
	public void setResetIPAddress(String resetIPAddress) {
		this.resetIPAddress = resetIPAddress;
	}
}
