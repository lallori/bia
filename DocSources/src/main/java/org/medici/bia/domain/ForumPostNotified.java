/*
 * ForumPostNotified.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class represents entity ForumPostNotified.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblForumPostNotified\"" ) 
public class ForumPostNotified implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5946251120972534286L;

	@Id
	@Column (name="\"postId\"", length=10, nullable=false)
	private Integer postId;

	@Column (name="\"mailSended\"", nullable=false)
	private Boolean mailSended;

	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"mailSendedDate\"")
	private Date mailSendedDate;

	/**
	 * Default constructor
	 */
	public ForumPostNotified() {
		super();
	}

	/**
	 * 
	 * @param postId
	 */
	public ForumPostNotified(Integer postId) {
		super();
		
		setPostId(postId);
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	/**
	 * @return the postId
	 */
	public Integer getPostId() {
		return postId;
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


}
