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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"account\"", nullable=true)
	private User user;
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

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"entryId\"", nullable=true)
	private Document document;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"peopleId\"", nullable=true)
	private People person;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"placeAllId\"", nullable=true)
	private Place place;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"summaryId\"", nullable=true)
	private Volume volume;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"imageId\"", nullable=true)
	private Image image;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"annotationId\"", nullable=true)
	private Annotation annotation;
	
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

	public Annotation getAnnotation() {
		return annotation;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @return the firstPost
	 */
	public ForumPost getFirstPost() {
		return firstPost;
	}

	/**
	 * @return the forum
	 */
	public Forum getForum() {
		return forum;
	}

	/**
	 * @return the hasAttachment
	 */
	public Boolean getHasAttachment() {
		return hasAttachment;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @return the lastPost
	 */
	public ForumPost getLastPost() {
		return lastPost;
	}

	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @return the person
	 */
	public People getPerson() {
		return person;
	}

	/**
	 * @return the place
	 */
	public Place getPlace() {
		return place;
	}

	/**
	 * @return the posts
	 */
	public List<ForumPost> getPosts() {
		return posts;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return the topicId
	 */
	public Integer getTopicId() {
		return topicId;
	}

	/**
	 * @return the totalReplies
	 */
	public int getTotalReplies() {
		return totalReplies;
	}

	/**
	 * @return the totalViews
	 */
	public int getTotalViews() {
		return totalViews;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the volume
	 */
	public Volume getVolume() {
		return volume;
	}

	/**
	 * @return the hasAttachment
	 */
	public boolean isHasAttachment() {
		return hasAttachment;
	}

	public void setAnnotation(Annotation annotation) {
		this.annotation = annotation;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @param firstPost the firstPost to set
	 */
	public void setFirstPost(ForumPost firstPost) {
		this.firstPost = firstPost;
	}

	/**
	 * @param forum the forum to set
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
	}

	/**
	 * @param hasAttachment the hasAttachment to set
	 */
	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	/**
	 * @param hasAttachment the hasAttachment to set
	 */
	public void setHasAttachment(Boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @param lastPost the lastPost to set
	 */
	public void setLastPost(ForumPost lastPost) {
		this.lastPost = lastPost;
	}

	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(People person) {
		this.person = person;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(Place place) {
		this.place = place;
	}

	/**
	 * @param posts the posts to set
	 */
	public void setPosts(List<ForumPost> posts) {
		this.posts = posts;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @param topicId the topicId to set
	 */
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	/**
	 * @param totalReplies the totalReplies to set
	 */
	public void setTotalReplies(int totalReplies) {
		this.totalReplies = totalReplies;
	}

	/**
	 * @param totalReplies the totalReplies to set
	 */
	public void setTotalReplies(Integer totalReplies) {
		this.totalReplies = totalReplies;
	}

	/**
	 * @param totalViews the totalViews to set
	 */
	public void setTotalViews(int totalViews) {
		this.totalViews = totalViews;
	}

	/**
	 * @param totalViews the totalViews to set
	 */
	public void setTotalViews(Integer totalViews) {
		this.totalViews = totalViews;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @param userInformation the userInformation to set
	 */
	public void setUser(User user) {
		this.user= user;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[");
		stringBuilder.append("id=");
		stringBuilder.append(getTopicId());
		stringBuilder.append(", subject=");
		stringBuilder.append(getSubject());
		if (getForum() == null) {
			stringBuilder.append(", forum=null,");
		} else {
			stringBuilder.append(", forum=");
			stringBuilder.append(getForum().getForumId());
		}
		stringBuilder.append("]");

		return stringBuilder.toString();
	}


	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum Type {
		ANNOUNCE("LINK"),NORMAL("NORMAL"),STICKY("CATEGORY");
		
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
