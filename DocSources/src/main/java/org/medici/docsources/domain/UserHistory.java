/*
 * UserHistory.java
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.medici.docsources.common.search.Search;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserHistory entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblUserHistory\"" ) 
public class UserHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2001847076255349765L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="\"idUserHistory\"", length=10, nullable=false)
	private Integer idUserHistory;
	@Column (name="\"dateAndTime\"", nullable=false)
	private Date dateAndTime;
	@Column (name="\"username\"", length=25, nullable=false)
	private String username;
	@Column (name="\"action\"", length=10, nullable=true)
	@Enumerated(EnumType.STRING)
	private Action action;
	@Column (name="\"category\"", length=10, nullable=true)
	@Enumerated(EnumType.STRING)
	private Category category;
	@Column (name="\"description\"", length=100, nullable=false)
	private String description;
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
	@JoinColumn(name="\"forumId\"", nullable=true)
	private Forum forum;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"forumPostId\"", nullable=true)
	private ForumPost forumPost;

	@Column (name="\"searchData\"", nullable=true)
	@Lob
	private Search searchData;
	@Column (name="\"LOGICALDELETE\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	private Boolean logicalDelete;
	/**
	 * Default Constructor
	 */
	public UserHistory() {
		super();
	}

	/**
	 * 
	 * @param description
	 * @param action
	 * @param document
	 */
	public UserHistory(String description, Action action, Category category, Object object) {
		this.setDateAndTime(new Date());
		this.setUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		this.setDescription(description);
		this.setAction(action);
		this.setCategory(category);
		this.setLogicalDelete(Boolean.FALSE);
		
		if (category.equals(Category.DOCUMENT)) {
			setDocument((Document)object);
		} else if (category.equals(Category.PEOPLE)) {
			setPerson((People)object);
		} else if (category.equals(Category.PLACE)) {
			setPlace((Place)object);
		} else if (category.equals(Category.VOLUME)) {
			setVolume((Volume)object);
		} else {
			setSearchData((Search) object);
		}
	}

	/**
	 * @param idUserHistory the idUserHistory to set
	 */
	public void setIdUserHistory(Integer idUserHistory) {
		this.idUserHistory = idUserHistory;
	}

	/**
	 * @return the idUserHistory
	 */
	public Integer getIdUserHistory() {
		return idUserHistory;
	}

	/**
	 * @param dateAndTime the dateAndTime to set
	 */
	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	/**
	 * @return the dateAndTime
	 */
	public Date getDateAndTime() {
		return dateAndTime;
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
	 * @param action the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
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
	 * @param people the person to set
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

	/**
	 * @param forum the forum to set
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
	}

	/**
	 * @return the forum
	 */
	public Forum getForum() {
		return forum;
	}

	/**
	 * @param forumPost the forumPost to set
	 */
	public void setForumPost(ForumPost forumPost) {
		this.forumPost = forumPost;
	}

	/**
	 * @return the forumPost
	 */
	public ForumPost getForumPost() {
		return forumPost;
	}

	/**
	 * @param searchData the searchData to set
	 */
	public void setSearchData(Search searchData) {
		this.searchData = searchData;
	}

	/**
	 * @return the searchData
	 */
	public Search getSearchData() {
		return searchData;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		if (getCategory() == null) {
			return "";
		}
		
		if (getCategory().equals(Category.DOCUMENT)) {
			return getDocument().getMDPAndFolio();
		} else if (getCategory().equals(Category.PEOPLE)) {
			return getPerson().getMapNameLf();
		} else if (getCategory().equals(Category.PLACE)) {
			return getPlace().getPlaceNameFull();
		} else if (getCategory().equals(Category.VOLUME)) {
			return getVolume().getMDP();
		} else {		
			return "";
		}
	}

	/**
	 * @param logicalDelete the logicalDelete to set
	 */
	public void setLogicalDelete(Boolean logicalDelete) {
		this.logicalDelete = logicalDelete;
	}

	/**
	 * @return the logicalDelete
	 */
	public Boolean getLogicalDelete() {
		return logicalDelete;
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum Action {
		COMPARE("Compared"),
		CREATE("Created"),
		MODIFY("Modified"),
		VIEW("Viewed"),
		DELETE("Deleted");
		
		private final String action;

	    private Action(String value) {
	    	action = value;
	    }

	    @Override
	    public String toString(){
	        return action;
	    }
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum Category {
		DOCUMENT("Document"),
		PEOPLE("People"),
		PLACE("Place"),
		VOLUME("Volume"),
		FORUM("Forum"),
		FORUM_POST("ForumPost"),
		SEARCH_DOCUMENT("SearchDocument"),
		SEARCH_PEOPLE("SearchPeople"),
		SEARCH_PLACE("SearchPlace"),
		SEARCH_VOLUME("SearchVolume");
		
		private final String category;

	    private Category(String value) {
	    	category = value;
	    }

	    @Override
	    public String toString(){
	        return category;
	    }
	}
}

