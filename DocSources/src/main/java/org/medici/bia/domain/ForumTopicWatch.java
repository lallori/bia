/*
 * ForumTopicWatch.java
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
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
@Entity
@Table(name = "\"tblForumTopicWatch\"")
public class ForumTopicWatch implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -192731279584289026L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "topicWatchId")
	private Integer topicWatchId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topicId")
	private ForumTopic topic;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"account\"", nullable=true)
	private User user;

	/**
	 * 
	 */
	public ForumTopicWatch() {
		super();
	}

	/**
	 * 
	 * @param forumTopic
	 * @param user2
	 */
	public ForumTopicWatch(ForumTopic topic, User user2) {
		super();
		
		setTopic(topic);
		setUser(user2);
	}

	/**
	 * @return the topicWatchId
	 */
	public Integer getTopicWatchId() {
		return topicWatchId;
	}

	/**
	 * @param topicWatchId the topicWatchId to set
	 */
	public void setTopicWatchId(Integer topicWatchId) {
		this.topicWatchId = topicWatchId;
	}

	/**
	 * @return the topic
	 */
	public ForumTopic getTopic() {
		return topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(ForumTopic topic) {
		this.topic = topic;
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

}