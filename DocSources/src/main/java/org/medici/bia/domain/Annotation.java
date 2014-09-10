/*
 * Annontation.java
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

import org.hibernate.search.annotations.IndexedEmbedded;


/**
 * This class represents entity Annotation.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblAnnotations\"" ) 
public class Annotation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2032010411783359973L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"annotationId\"", length=10, nullable=false)
	private Integer annotationId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"imageId\"")
	private Image image;
	
	@Column (name="\"type\"", length=11, nullable=false)
	@Enumerated(EnumType.STRING)
	private Type type;
	@Column (name="\"dateCreated\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@Column (name="\"lastUpdate\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"account\"")
	private User user;
	
	@Column (name="\"x\"", nullable=false)
	private Double x;
	@Column (name="\"y\"", nullable=false)
	private Double y;
	@Column (name="\"w\"", nullable=false)
	private Double width;
	@Column (name="\"h\"", nullable=false)
	private Double height;
	@Column (name="\"title\"", nullable=true) 
	private String title;
	@Column (name="\"text\"", length=75000, nullable=true)
	private String text;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"topicId\"")
	@IndexedEmbedded
	private ForumTopic forumTopic;
	
	@Column (name="\"visible\"", length=1, columnDefinition="tinyint default 1", nullable=true)
	private Boolean visible;
	
	@Column (name="\"logicalDelete\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean logicalDelete;
	
	/**
	 * 
	 */
	public Annotation() {
		super();
	}

	/**
	 * 
	 * @param annotationId
	 */
	public Annotation(Integer annotationId) {
		super();
		
		setAnnotationId(annotationId);
	}

	/**
	 * 
	 * @param annotationId
	 * @param parentForum
	 */
	public Annotation(Integer annotationId, Type type) {
		super();
		
		setAnnotationId(annotationId);
		setType(type);
	}

	/**
	 * @return the annotationId
	 */
	public Integer getAnnotationId() {
		return annotationId;
	}

	/**
	 * @return the w
	 */
	public Double getWidth() {
		return width;
	}

	/**
	 * @param w the w to set
	 */
	public void setWidth(Double width) {
		this.width = width;
	}

	/**
	 * @return the h
	 */
	public Double getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Double height) {
		this.height = height;
	}

	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param annotationId the annotationId to set
	 */
	public void setAnnotationId(Integer annotationId) {
		this.annotationId = annotationId;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Image getImage() {
		return image;
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
	 * @return the x
	 */
	public Double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(Double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public Double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(Double y) {
		this.y = y;
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

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	/**
	 * @return the forumTopic
	 */
	public ForumTopic getForumTopic() {
		return forumTopic;
	}

	/**
	 * @param forumTopic the forumTopic to set
	 */
	public void setForumTopic(ForumTopic forumTopic) {
		this.forumTopic = forumTopic;
	}
	
	/**
	 * @return the visible
	 */
	public Boolean getVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the logicalDelete
	 */
	public Boolean getLogicalDelete() {
		return logicalDelete;
	}
	
	/**
	 * @param logicalDelete the logicalDelete to set
	 */
	public void setLogicalDelete(Boolean logicalDelete) {
		this.logicalDelete = logicalDelete;
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum Type {
		GENERAL("GENERAL"),
		PALEOGRAPHY("PALEOGRAPHY"), 
		PERSONAL("PERSONAL"),
		TEACHING("TEACHING");
		
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
