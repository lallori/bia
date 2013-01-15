/*
 * EmailMessageUser.java
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
 * Entity for store user's email messages.
 * 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Entity
@Table ( name = "\"tblEmailMessageUser\"" ) 
public class EmailMessageUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2084201619215125604L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"emailMessageId\"", length=10, nullable=false)
	private Integer emailMessageId;	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"account\"", nullable=true)
	private User user;
	@Column (name="\"messageSubject\"", length=100)
	private String subject;
	@Column (name="\"messageBody\"", length=4000)
	private String body;
	@Column (name="\"mailSended\"", nullable=false)
	private Boolean mailSended;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"mailSendedDate\"")
	private Date mailSendedDate;
	
	/**
	 * 
	 * @param string
	 */
	public EmailMessageUser(User user) {
		super();
		
		setUser(user);
	}

	/**
	 * default constructor.
	 */
	public EmailMessageUser() {
		super();
	}

	/**
	 * @return the emailMessageId
	 */
	public Integer getEmailMessageId() {
		return emailMessageId;
	}

	/**
	 * @param emailMessageId the emailMessageId to set
	 */
	public void setEmailMessageId(Integer emailMessageId) {
		this.emailMessageId = emailMessageId;
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
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}
}
