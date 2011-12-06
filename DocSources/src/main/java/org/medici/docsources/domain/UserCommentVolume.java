/*
 * UserCommentVolume.java
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserCommentVolume entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblUserCommentVolume\"" ) 
public class UserCommentVolume implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7891586455762869714L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="\"idCommentVolume\"", length=10, nullable=false)
	private Integer idCommentVolume;
	@Column (name="\"parentComment\"", length=10, nullable=false)
	private UserCommentVolume parentComment;
	@Column (name="\"dateAndTime\"", nullable=false)
	private Date dateAndTime;
	@Column (name="\"username\"", length=25, nullable=false)
	private String username;
	@Column (name="\"ipAddress\"", length=50, nullable=false)
	private String ipAddress;
	@Column (name="\"title\"", length=10, nullable=true)
	private String title;
	@Column (name="\"ccontext\"", length=100, nullable=false)
	private String ccontext;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"summaryId\"")
	private Volume volume;
	
	/**
	 * @return the idCommentVolume
	 */
	public Integer getIdCommentVolume() {
		return idCommentVolume;
	}
	
	/**
	 * @param idCommentVolume the idCommentVolume to set
	 */
	public void setIdCommentVolume(Integer idCommentVolume) {
		this.idCommentVolume = idCommentVolume;
	}
	
	/**
	 * @return the parentComment
	 */
	public UserCommentVolume getParentComment() {
		return parentComment;
	}
	
	/**
	 * @param parentComment the parentComment to set
	 */
	public void setParentComment(UserCommentVolume parentComment) {
		this.parentComment = parentComment;
	}
	
	/**
	 * @return the dateAndTime
	 */
	public Date getDateAndTime() {
		return dateAndTime;
	}
	
	/**
	 * @param dateAndTime the dateAndTime to set
	 */
	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the ccontext
	 */
	public String getCcontext() {
		return ccontext;
	}
	
	/**
	 * @param ccontext the ccontext to set
	 */
	public void setCcontext(String ccontext) {
		this.ccontext = ccontext;
	}
	
	/**
	 * @return the volume
	 */
	public Volume getVolume() {
		return volume;
	}
	
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(Volume volume) {
		this.volume = volume;
	}
}