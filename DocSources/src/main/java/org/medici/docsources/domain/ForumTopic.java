/*
 * ForumTopic.java
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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class represents entity ForumTopic.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblForumTopic\"" ) 
public class ForumTopic implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 543596794864499842L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "topicId")
	private Integer topicId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forumId")
	private Forum forum;
	@Column(name = "totalViews")
	private Integer totalViews;
	@Column(name = "totalReplies")
	private Integer totalReplies;
	@Column(name = "status")
	private Integer status;
	@Column(name = "topicType")
	private Type type;
	@Column(name = "hasAttachment")
	private Boolean hasAttachment;
	@Column (name="\"username\"", length=64, nullable=false)
	private String username;
	@Column (name="\"ipAddress\"", length=50, nullable=false)
	private String ipAddress;
	@Column(name = "subject")
	private String subject;
	@Column (name="\"dateCreated\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@Column(name = "lastUpdate")
	private Date lastUpdate;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "firstPost", nullable=true)
	private ForumPost firstPost;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lastPost", nullable=true)
	private ForumPost lastPost;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="topic", nullable=true)
	private List<ForumPost> posts;

	/**
	 * 
	 */
	public ForumTopic() {
		super();
	}

	/**
	 * 
	 * @param topicId
	 */
	public ForumTopic(Integer topicId) {
		super();
		
		setTopicId(topicId);
	}

	/**
	 * @return the topicId
	 */
	public Integer getTopicId() {
		return topicId;
	}

	/**
	 * @param topicId the topicId to set
	 */
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
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
	 * @return the totalViews
	 */
	public int getTotalViews() {
		return totalViews;
	}

	/**
	 * @param totalViews the totalViews to set
	 */
	public void setTotalViews(int totalViews) {
		this.totalViews = totalViews;
	}

	/**
	 * @return the totalReplies
	 */
	public int getTotalReplies() {
		return totalReplies;
	}

	/**
	 * @param totalReplies the totalReplies to set
	 */
	public void setTotalReplies(int totalReplies) {
		this.totalReplies = totalReplies;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the hasAttachment
	 */
	public boolean isHasAttachment() {
		return hasAttachment;
	}

	/**
	 * @param hasAttachment the hasAttachment to set
	 */
	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
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
	 * @return the firstPost
	 */
	public ForumPost getFirstPost() {
		return firstPost;
	}

	/**
	 * @param firstPost the firstPost to set
	 */
	public void setFirstPost(ForumPost firstPost) {
		this.firstPost = firstPost;
	}

	/**
	 * @return the lastPost
	 */
	public ForumPost getLastPost() {
		return lastPost;
	}

	/**
	 * @param lastPost the lastPost to set
	 */
	public void setLastPost(ForumPost lastPost) {
		this.lastPost = lastPost;
	}

	/**
	 * @return the posts
	 */
	public List<ForumPost> getPosts() {
		return posts;
	}

	/**
	 * @param posts the posts to set
	 */
	public void setPosts(List<ForumPost> posts) {
		this.posts = posts;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("[");
		stringBuffer.append("id=");
		stringBuffer.append(getTopicId());
		stringBuffer.append(", subject=");
		stringBuffer.append(getSubject());
		if (getForum() == null) {
			stringBuffer.append(", forum=null,");
		} else {
			stringBuffer.append(", forum=");
			stringBuffer.append(getForum().getForumId());
		}
		stringBuffer.append("]");

		return stringBuffer.toString();
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum Type {
		NORMAL("NORMAL"),STICKY("CATEGORY"),ANNOUNCE("LINK");
		
		private final String type;

	    private Type(String value) {
	    	type = value;
	    }

	    @Override
	    public String toString(){
	        return type;
	    }
	}
}
