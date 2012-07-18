/*
 * Forum.java
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * This class represents entity Forum.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblForum\"" ) 
public class Forum implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5735698070115951563L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"forumId\"", length=10, nullable=false)
	private Integer forumId;
	@Column (name="\"type\"", length=10, nullable=false)
	@Enumerated(EnumType.STRING)
	private Type type;
	@Column (name="\"subType\"", length=10, nullable=false)
	@Enumerated(EnumType.STRING)
	private SubType subType;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"forumParent\"", nullable=true)
	private Forum forumParent;
	@Column (name="\"title\"", length=2000, nullable=false)
	private String title;
	@Column (name="\"description\"", length=2000)
	private String description;
	@Column (name="\"status\"")
	private Status status;
	@Column (name="\"postsNumber\"")
	private Integer postsNumber;
	@Column (name="\"topicsNumber\"")
	private Integer topicsNumber;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"lastPost\"", nullable=true)
	private ForumPost lastPost;
	@Column (name="\"dateCreated\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@Column (name="\"lastUpdate\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	@Column (name="\"dispositionOrder\"", length=10, columnDefinition="TINYINT default '0'")
	private Integer dispositionOrder;

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

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"forumId\"", nullable=true)
	private ForumOption option;

	@OneToMany(mappedBy = "forum")
	private List<ForumTopic> forumTopics;

	public Forum() {
		super();
	}

	/**
	 * 
	 * @param forumId
	 */
	public Forum(Integer forumId) {
		super();
		
		setForumId(forumId);
	}

	/**
	 * 
	 * @param id
	 * @param type
	 */
	public Forum(Integer forumId, Type type) {
		super();
		
		setForumId(forumId);
		setType(type);
	}

	/**
	 * 
	 * @param forumId
	 * @param parentForum
	 */
	public Forum(Integer forumId, Type type, Integer parentForumId) {
		super();
		
		setForumId(forumId);
		setType(type);
		setForumParent(new Forum(parentForumId));
	}

	/**
	 * @return the forumId
	 */
	public Integer getForumId() {
		return forumId;
	}

	/**
	 * @param id the id to set
	 */
	public void setForumId(Integer forumId) {
		this.forumId= forumId;
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
	 * @return the forumParent
	 */
	public Forum getForumParent() {
		return forumParent;
	}

	/**
	 * @param forumParent the forumParent to set
	 */
	public void setForumParent(Forum forumParent) {
		this.forumParent = forumParent;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the name to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the postsNumber
	 */
	public Integer getPostsNumber() {
		return postsNumber;
	}

	/**
	 * @param postsNumber the postsNumber to set
	 */
	public void setPostsNumber(Integer postsNumber) {
		this.postsNumber = postsNumber;
	}

	/**
	 * @return the topicsNumber
	 */
	public Integer getTopicsNumber() {
		return topicsNumber;
	}

	/**
	 * @param topicsNumber the topicsNumber to set
	 */
	public void setTopicsNumber(Integer topicsNumber) {
		this.topicsNumber = topicsNumber;
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
	 * @param dispositionOrder the dispositionOrder to set
	 */
	public void setDispositionOrder(Integer dispositionOrder) {
		this.dispositionOrder = dispositionOrder;
	}

	/**
	 * @return the dispositionOrder
	 */
	public Integer getDispositionOrder() {
		return dispositionOrder;
	}

	/**
	 * @param subType the subType to set
	 */
	public void setSubType(SubType subType) {
		this.subType = subType;
	}

	/**
	 * @return the subType
	 */
	public SubType getSubType() {
		return subType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[");
		stringBuilder.append("id=");
		stringBuilder.append(getForumId());
		stringBuilder.append(", name=");
		stringBuilder.append(getTitle());
		if (getForumParent() == null) {
			stringBuilder.append(", forumId=null,");
		} else {
			stringBuilder.append(", parentForumId=");
			stringBuilder.append(getForumParent().getForumId());
		}
		stringBuilder.append(", description=");
		stringBuilder.append(getDescription());
		stringBuilder.append("]");

		return stringBuilder.toString();
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}
	/**
	 * @param person the person to set
	 */
	public void setPerson(People person) {
		this.person = person;
	}

	/**
	 * @return the person
	 */
	public People getPerson() {
		return person;
	}
	/**
	 * @param place the place to set
	 */
	public void setPlace(Place place) {
		this.place = place;
	}

	/**
	 * @return the place
	 */
	public Place getPlace() {
		return place;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	/**
	 * @return the volume
	 */
	public Volume getVolume() {
		return volume;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
	}

	/**
	 * @param forumOption the forumOption to set
	 */
	public void setOption(ForumOption forumOption) {
		this.option = forumOption;
	}

	/**
	 * @return the forumOption
	 */
	public ForumOption getOption() {
		return option;
	}

	/**
	 * @param forumTopics the forumTopics to set
	 */
	public void setForumTopics(List<ForumTopic> forumTopics) {
		this.forumTopics = forumTopics;
	}

	/**
	 * @return the forumTopics
	 */
	public List<ForumTopic> getForumTopics() {
		return forumTopics;
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum SubType {
		GENERIC("GENERIC"),
		DOCUMENT("DOCUMENT"),
		FOLIO("FOLIO"),
		PEOPLE("PEOPLE"),
		PLACE("PLACE"),
		VOLUME("VOLUME");
		
		private final String subType;

	    private SubType(String value) {
	    	subType = value;
	    }

	    @Override
	    public String toString(){
	        return subType;
	    }
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum Type {
		FORUM("FORUM"),
		CATEGORY("CATEGORY"),
		LINK("LINK");
		
		private final String type;

	    private Type(String value) {
	    	type = value;
	    }

	    @Override
	    public String toString(){
	        return type;
	    }
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum Status {
		OFFLINE(0), ONLINE(1);
		
		private final Integer status;

	    private Status(Integer value) {
	    	status = value;
	    }

	    @Override
	    public String toString(){
	    	if (status == null)
	    		return null;

	    	return status.toString();
	    }
	}
}
