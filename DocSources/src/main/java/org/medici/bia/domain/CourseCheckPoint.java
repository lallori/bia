/*
 * CourseCheckPoint.java
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The Course Check Point entity.<br/>
 * It should be used when it is necessary to define an important transcription status stored in
 * a {@link CoursePostExt}.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblCourseCheckPoint\"" ) 
public class CourseCheckPoint implements Serializable {

	private static final long serialVersionUID = 6522075565420971742L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "checkPointId")
	private Integer checkPointId;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "optionId", nullable = false)
	private CourseTopicOption courseOption;
	@Column(name = "checkPointTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkPointTime;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postExtId", nullable = false)
	private CoursePostExt checkPointPost;
	
	public Integer getCheckPointId() {
		return checkPointId;
	}

	public void setCheckPointId(Integer checkPointId) {
		this.checkPointId = checkPointId;
	}

	public CourseTopicOption getCourseOption() {
		return courseOption;
	}

	public void setCourseOption(CourseTopicOption courseOption) {
		this.courseOption = courseOption;
	}

	public Date getCheckPointTime() {
		return checkPointTime;
	}

	public void setCheckPointTime(Date checkPointTime) {
		this.checkPointTime = checkPointTime;
	}

	public CoursePostExt getCheckPointPost() {
		return checkPointPost;
	}

	public void setCheckPointPost(CoursePostExt checkPointPost) {
		this.checkPointPost = checkPointPost;
	}

	public CourseCheckPoint() {
		super();
	}
	
	public CourseCheckPoint(CourseTopicOption courseOption, CoursePostExt checkPointPost) {
		super();
		setCourseOption(courseOption);
		setCheckPointPost(checkPointPost);
	}

}
