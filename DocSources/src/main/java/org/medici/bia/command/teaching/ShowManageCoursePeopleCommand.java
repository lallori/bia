/*
 * ShowManageCoursePeopleCommand.java
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
package org.medici.bia.command.teaching;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class ShowManageCoursePeopleCommand {
	
	private Integer courseId;
	
	private Integer courseStudentsFirstRecord;
	
	private Integer courseStudentsPageNumber;
	
	private Long courseStudentsPageTotal;
	
	private Integer courseStudentsForPage;
	
	private Integer courseStudentsOrderByTableField;
	
	private Boolean courseStudentsAscendingOrder;
	
	private Integer otherStudentsFirstRecord;
	
	private Integer otherStudentsPageNumber;
	
	private Long otherStudentsPageTotal;
	
	private Integer otherStudentsForPage;
	
	private Integer otherStudentsOrderByTableField;
	
	private Boolean otherStudentsAscendingOrder;
	
	/**
	 * @return the courseId
	 */
	public Integer getCourseId() {
		return courseId;
	}

	/**
	 * @param courseId the courseId to set
	 */
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	/**
	 * @return the courseStudentsFirstRecord
	 */
	public Integer getCourseStudentsFirstRecord() {
		return courseStudentsFirstRecord;
	}

	/**
	 * @param courseStudentsFirstRecord the courseStudentsFirstRecord to set
	 */
	public void setCourseStudentsFirstRecord(Integer courseStudentsFirstRecord) {
		this.courseStudentsFirstRecord = courseStudentsFirstRecord;
	}

	/**
	 * @return the courseStudentsPageNumber
	 */
	public Integer getCourseStudentsPageNumber() {
		return courseStudentsPageNumber;
	}

	/**
	 * @param courseStudentsPageNumber the courseStudentsPageNumber to set
	 */
	public void setCourseStudentsPageNumber(Integer courseStudentsPageNumber) {
		this.courseStudentsPageNumber = courseStudentsPageNumber;
	}

	/**
	 * @return the courseStudentsPageTotal
	 */
	public Long getCourseStudentsPageTotal() {
		return courseStudentsPageTotal;
	}

	/**
	 * @param courseStudentsPageTotal the courseStudentsPageTotal to set
	 */
	public void setCourseStudentsPageTotal(Long courseStudentsPageTotal) {
		this.courseStudentsPageTotal = courseStudentsPageTotal;
	}

	/**
	 * @return the courseStudentsForPage
	 */
	public Integer getCourseStudentsForPage() {
		return courseStudentsForPage;
	}

	/**
	 * @param courseStudentsForPage the courseStudentsForPage to set
	 */
	public void setCourseStudentsForPage(Integer courseStudentsForPage) {
		this.courseStudentsForPage = courseStudentsForPage;
	}

	/**
	 * @return the courseStudentsOrderByTableField
	 */
	public Integer getCourseStudentsOrderByTableField() {
		return courseStudentsOrderByTableField;
	}

	/**
	 * @param courseStudentsOrderByTableField the courseStudentsOrderByTableField to set
	 */
	public void setCourseStudentsOrderByTableField(
			Integer courseStudentsOrderByTableField) {
		this.courseStudentsOrderByTableField = courseStudentsOrderByTableField;
	}

	/**
	 * @return the courseStudentsAscendingOrder
	 */
	public Boolean getCourseStudentsAscendingOrder() {
		return courseStudentsAscendingOrder;
	}

	/**
	 * @param courseStudentsAscendingOrder the courseStudentsAscendingOrder to set
	 */
	public void setCourseStudentsAscendingOrder(Boolean courseStudentsAscendingOrder) {
		this.courseStudentsAscendingOrder = courseStudentsAscendingOrder;
	}

	/**
	 * @return the otherStudentsFirstRecord
	 */
	public Integer getOtherStudentsFirstRecord() {
		return otherStudentsFirstRecord;
	}

	/**
	 * @param otherStudentsFirstRecord the otherStudentsFirstRecord to set
	 */
	public void setOtherStudentsFirstRecord(Integer otherStudentsFirstRecord) {
		this.otherStudentsFirstRecord = otherStudentsFirstRecord;
	}

	/**
	 * @return the otherStudentsPageNumber
	 */
	public Integer getOtherStudentsPageNumber() {
		return otherStudentsPageNumber;
	}

	/**
	 * @param otherStudentsPageNumber the otherStudentsPageNumber to set
	 */
	public void setOtherStudentsPageNumber(Integer otherStudentsPageNumber) {
		this.otherStudentsPageNumber = otherStudentsPageNumber;
	}

	/**
	 * @return the otherStudentsPageTotal
	 */
	public Long getOtherStudentsPageTotal() {
		return otherStudentsPageTotal;
	}

	/**
	 * @param otherStudentsPageTotal the otherStudentsPageTotal to set
	 */
	public void setOtherStudentsPageTotal(Long otherStudentsPageTotal) {
		this.otherStudentsPageTotal = otherStudentsPageTotal;
	}

	/**
	 * @return the otherStudentsForPage
	 */
	public Integer getOtherStudentsForPage() {
		return otherStudentsForPage;
	}

	/**
	 * @param otherStudentsForPage the otherStudentsForPage to set
	 */
	public void setOtherStudentsForPage(Integer otherStudentsForPage) {
		this.otherStudentsForPage = otherStudentsForPage;
	}

	/**
	 * @return the otherStudentsOrderByTableField
	 */
	public Integer getOtherStudentsOrderByTableField() {
		return otherStudentsOrderByTableField;
	}

	/**
	 * @param otherStudentsOrderByTableField the otherStudentsOrderByTableField to set
	 */
	public void setOtherStudentsOrderByTableField(
			Integer otherStudentsOrderByTableField) {
		this.otherStudentsOrderByTableField = otherStudentsOrderByTableField;
	}

	/**
	 * @return the otherStudentsAscendingOrder
	 */
	public Boolean getOtherStudentsAscendingOrder() {
		return otherStudentsAscendingOrder;
	}

	/**
	 * @param otherStudentsAscendingOrder the otherStudentsAscendingOrder to set
	 */
	public void setOtherStudentsAscendingOrder(Boolean otherStudentsAscendingOrder) {
		this.otherStudentsAscendingOrder = otherStudentsAscendingOrder;
	}

}
