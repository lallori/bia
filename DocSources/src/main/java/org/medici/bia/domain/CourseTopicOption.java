/*
 * CourseTopicOption.java
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblCourseTopicOption\"" ) 
public class CourseTopicOption implements Serializable {

	private static final long serialVersionUID = -2039016482089963712L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "optionId")
	private Integer optionId;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topicId", nullable = false)
	private ForumTopic courseTopic;
	@Enumerated(EnumType.STRING)
	@Column (name = "mode", length = 1, nullable = true)
	private CourseTopicMode mode;
	
	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public ForumTopic getCourseTopic() {
		return courseTopic;
	}

	public void setCourseTopic(ForumTopic courseTopic) {
		this.courseTopic = courseTopic;
	}

	public CourseTopicMode getMode() {
		return mode;
	}

	public void setMode(CourseTopicMode mode) {
		this.mode = mode;
	}

	/**
	 * This enumeration manages the course topic mode.
	 * 
	 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
	 *
	 */
	public static enum CourseTopicMode {
		C("CHECKPOINT"),
		D("DISCUSSION"),
		I("INCREMENTAL"),
		Q("QUESTION"),
		R("ROUNDROBIN");
		
		private final String name;
		
		private CourseTopicMode(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public static CourseTopicMode findByName(String name) {
			if (name == null || "".equals(name.trim())) {
				return null;
			}
			try {
				return valueOf(name.trim());
			} catch (Exception e) {
				return null;
			}
		}
		
		public static boolean isCourseTranscriptionMode(CourseTopicMode mode) {
			return C.equals(mode) || R.equals(mode) || I.equals(mode);
		}
	}

}
