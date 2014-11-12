/*
 * CoursePeople.java
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

/**
 * Course People entity.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
//@Entity
//@Table(name = "tblCoursePeople")
public class CoursePeople {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "coursePeopleId")
	private Integer coursePeopleId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="courseId", nullable=false)
	private Course course;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userRoleId", nullable=false)
	private UserRole userRole;
	
	@Column(name="subscription", length=1, columnDefinition="tinyint default 1", nullable=false)
	private Boolean subscription;
	
	@Column(name = "lastUpdate")
	private Date lastUpdate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="lastUpdateBy", nullable=true)
	private User lastUpdateBy;

	/**
	 * @return the coursePeopleId
	 */
	public Integer getCoursePeopleId() {
		return coursePeopleId;
	}

	/**
	 * @param coursePeopleId the coursePeopleId to set
	 */
	public void setCoursePeopleId(Integer coursePeopleId) {
		this.coursePeopleId = coursePeopleId;
	}

	/**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * @return the userRole
	 */
	public UserRole getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return the subscription
	 */
	public Boolean getSubscription() {
		return subscription;
	}

	/**
	 * @param subscription the subscription to set
	 */
	public void setSubscription(Boolean subscription) {
		this.subscription = subscription;
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
	 * @return the lastUpdateBy
	 */
	public User getLastUpdateBy() {
		return lastUpdateBy;
	}

	/**
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(User lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	
}
