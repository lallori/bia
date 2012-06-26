/*
 * ForumPost.java
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class represents entity Forum.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblForumPost\"" ) 
public class ForumPost implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7835012793497758978L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"postId\"", length=10, nullable=false)
	private Integer postId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="\"forum\"", nullable=true)
	private Forum forum;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="\"topic\"", nullable=true)
	private ForumTopic topic;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"parentPost\"", nullable=true)
	private ForumPost parentPost;
	@Column (name="\"username\"", length=64, nullable=false)
	private String username;
	@Column (name="\"dateCreated\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@Column (name="\"lastUpdate\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	@Column (name="\"ipAddress\"", length=50, nullable=false)
	private String ipAddress;
	@Column (name="\"subject\"", length=64, nullable=false)
	private String subject;
	@Column (name="\"text\"", length=75000)
	private String text;
	@Column (name="\"replyNumber\"")
	private Integer replyNumber;

	/**
	 * Default constructor
	 */
	public ForumPost() {
		super();
	}

	/**
	 * 
	 * @param id2
	 */
	public ForumPost(Integer postId) {
		super();
		
		setPostId(postId);
	}

	/**
	 * @return the postId
	 */
	public Integer getPostId() {
		return postId;
	}
	/**
	 * @param postId the postId to set
	 */
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	/**
	 * @param topic the topic to set
	 */
	public void setTopic(ForumTopic topic) {
		this.topic = topic;
	}

	/**
	 * @return the topic
	 */
	public ForumTopic getTopic() {
		return topic;
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
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}
	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	/**
	 * @return the forum
	 */
	public Forum getForum() {
		return forum;
	}
	/**
	 * @param forum the forum to set
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
	}
	/**
	 * @param parentPost the parentPost to set
	 */
	public void setParentPost(ForumPost parentPost) {
		this.parentPost = parentPost;
	}

	/**
	 * @return the parentPost
	 */
	public ForumPost getParentPost() {
		return parentPost;
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
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param replyNumber the replyNumber to set
	 */
	public void setReplyNumber(Integer replyNumber) {
		this.replyNumber = replyNumber;
	}

	/**
	 * @return the replyNumber
	 */
	public Integer getReplyNumber() {
		return replyNumber;
	}

}
