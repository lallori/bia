/*
 * Course.java
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * Course entity.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblCourse\"" ) 
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "courseId")
	private Integer courseId;
	@Column(name = "active", length=1, columnDefinition="tinyint default 1", nullable=false)
	private Boolean active;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forumId", nullable=false)
	private Forum forum;
	
	/**
	 * 
	 */
	public Course() {
		super();
	}
	
	/**
	 * @param courseId the course identifier
	 */
	public Course(Integer courseId) {
		super();
		setCourseId(courseId);
	}

	/**
	 * @return the course identifier
	 */
	public Integer getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId the course identifier to set
	 */
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	/**
	 * @return if the course is active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
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
	
	
}