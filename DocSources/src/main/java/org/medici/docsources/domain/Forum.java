/*
 * ForumCategory.java
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

import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.math.NumberUtils;

/**
 * This class represents entity Forum.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblForum\"" ) 
public class Forum {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"id\"", length=10, nullable=false)
	private Integer id;
	@Column (name="\"type\"", length=1, nullable=false)
	@Enumerated(EnumType.STRING)
	private Type type;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"forumParent\"", nullable=true)
	private Forum forumParent;
	@Column (name="\"title\"", length=2000, nullable=false)
	private String name;
	@Column (name="\"description\"", length=2000)
	private String description;
	@Column (name="\"status\"", length=2000)
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
	
	public Forum() {
		super();
	}

	/**
	 * 
	 * @param forumId
	 */
	public Forum(Integer forumId) {
		super();
		
		setId(forumId);
	}

	/**
	 * 
	 * @param forumId
	 * @param parentForum
	 */
	public Forum(Integer forumId, Integer parentForum) {
		super();
		
		setId(forumId);
		setForumParent(new Forum(parentForum));
	}

	/**
	 * 
	 * @param type
	 * @param parentForum
	 */
	public Forum(Type type, Integer parentForum) {
		super();
		
		setType(type);
		setForumParent(new Forum(parentForum));
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum Type {
		FORUM("F"),
		CATEGORY("C"),
		LINK("L");
		
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
