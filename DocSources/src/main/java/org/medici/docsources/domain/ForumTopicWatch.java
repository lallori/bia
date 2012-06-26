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
package org.medici.docsources.domain;

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
	@Column(name = "topicWwatchId")
	private Integer topicWwatchId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topicId")
	private ForumTopic topic;

	@Column(name = "\"username\"", length = 64, nullable = false)
	private String username;

	@Column(name = "is_read")
	private Boolean read;


	/**
	 * @return the topicWwatchId
	 */
	public Integer getTopicWwatchId() {
		return topicWwatchId;
	}

	/**
	 * @param topicWwatchId the topicWwatchId to set
	 */
	public void setTopicWwatchId(Integer topicWwatchId) {
		this.topicWwatchId = topicWwatchId;
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
	 * @return the read
	 */
	public Boolean isRead() {
		return read;
	}

	/**
	 * @param read the read to set
	 */
	public void setRead(Boolean read) {
		this.read = read;
	}
}