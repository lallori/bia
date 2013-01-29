/*
 * LockedUser.java
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity for store locked users.
 * 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Entity
@Table ( name = "\"tblLockedUser\"" ) 
public class LockedUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6676232150579728494L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"id\"", length=10, nullable=false)
	private Integer id;	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"account\"", nullable=false)
	private User user;
	@Column (name="\"mailSended\"", nullable=false)
	private Boolean mailSended;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"mailSendedDate\"")
	private Date mailSendedDate;
	@Column (name="\"mailUnlockSended\"", nullable=false)
	private Boolean mailUnlockSended;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"mailUnlockSendedDate\"")
	private Date mailUnlockSendedDate;
	@Column (name="\"unlocked\"", nullable=false)
	private Boolean unlocked;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"unlockedDate\"")
	private Date unlockedDate;
	@Column(name="\"unlockedBy\"")
	private String unlockedBy;
	
	/**
	 * 
	 * @param string
	 */
	public LockedUser(User user) {
		super();
		
		setUser(user);
	}

	/**
	 * default constructor.
	 */
	public LockedUser() {
		super();
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the mailUnlockSended
	 */
	public Boolean getMailUnlockSended() {
		return mailUnlockSended;
	}

	/**
	 * @param mailUnlockSended the mailUnlockSended to set
	 */
	public void setMailUnlockSended(Boolean mailUnlockSended) {
		this.mailUnlockSended = mailUnlockSended;
	}

	/**
	 * @return the mailUnlockSendedDate
	 */
	public Date getMailUnlockSendedDate() {
		return mailUnlockSendedDate;
	}

	/**
	 * @param mailUnlockSendedDate the mailUnlockSendedDate to set
	 */
	public void setMailUnlockSendedDate(Date mailUnlockSendedDate) {
		this.mailUnlockSendedDate = mailUnlockSendedDate;
	}

	/**
	 * @return the unlocked
	 */
	public Boolean getUnlocked() {
		return unlocked;
	}

	/**
	 * @param unlocked the unlocked to set
	 */
	public void setUnlocked(Boolean unlocked) {
		this.unlocked = unlocked;
	}

	/**
	 * @return the unlockedDate
	 */
	public Date getUnlockedDate() {
		return unlockedDate;
	}

	/**
	 * @param unlockedDate the unlockedDate to set
	 */
	public void setUnlockedDate(Date unlockedDate) {
		this.unlockedDate = unlockedDate;
	}

	/**
	 * @return the unlockedBy
	 */
	public String getUnlockedBy() {
		return unlockedBy;
	}

	/**
	 * @param unlockedBy the unlockedBy to set
	 */
	public void setUnlockedBy(String unlockedBy) {
		this.unlockedBy = unlockedBy;
	}
}
