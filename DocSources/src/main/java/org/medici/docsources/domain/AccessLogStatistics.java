/*
 * AccessLogStatistics.java
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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AccessLogStatistics entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblAccessLogStatistics\"" ) 
public class AccessLogStatistics implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4395448593539708664L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="\"accessLogStatisticsId\"", length=10, nullable=false)
	private Integer accessLogStatisticsId;
	@Column (name="\"dateAndTime\"", nullable=false)
	private Date dateAndTime;
	@Column (name="\"action\"", length=2000, nullable=false)
	private String action;
	@Column (name="\"httpMethod\"", length=8, nullable=false)
	private String httpMethod;
	@Column (name="\"count\"", nullable=false)
	private Integer count;
	@Column(name="\"bestAccessTime\"", nullable=false)
	private Long bestAccessTime;
	@Column(name="\"worstAccessLog\"", nullable=false)
	private Long worstAccessTime;

	/**
	 * Default constructor
	 */
	public AccessLogStatistics() {
		super();
	}

	/**
	 * 
	 * @param dateAndTime
	 * @param action
	 * @param httpMethod
	 */
	public AccessLogStatistics(Date dateAndTime, String action, String httpMethod) {
		super();

		setDateAndTime(dateAndTime);
		setAction(action);
		setHttpMethod(httpMethod);
	}

	/**
	 * @return the accessLogStatisticsId
	 */
	public Integer getAccessLogStatisticsId() {
		return accessLogStatisticsId;
	}

	/**
	 * @param accessLogStatisticsId the accessLogStatisticsId to set
	 */
	public void setIdAccessLogStatisticsId(Integer accessLogStatisticsId) {
		this.accessLogStatisticsId = accessLogStatisticsId;
	}

	/**
	 * @return the dateAndTime
	 */
	public Date getDateAndTime() {
		return dateAndTime;
	}

	/**
	 * @param dateAndTime the dateAndTime to set
	 */
	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the httpMethod
	 */
	public String getHttpMethod() {
		return httpMethod;
	}

	/**
	 * @param httpMethod the httpMethod to set
	 */
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	/**
	 * 
	 * @param count
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getCount() {
		return count;
	}



	/**
	 * @return the bestAccessTime
	 */
	public Long getBestAccessTime() {
		return bestAccessTime;
	}



	/**
	 * @param bestAccessTime the bestAccessTime to set
	 */
	public void setBestAccessTime(Long bestAccessTime) {
		this.bestAccessTime = bestAccessTime;
	}



	/**
	 * @return the worstAccessTime
	 */
	public Long getWorstAccessTime() {
		return worstAccessTime;
	}



	/**
	 * @param worstAccessTime the worstAccessTime to set
	 */
	public void setWorstAccessTime(Long worstAccessTime) {
		this.worstAccessTime = worstAccessTime;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[id=");
		stringBuilder.append(getAccessLogStatisticsId());
		stringBuilder.append("dateAndTime=");
		stringBuilder.append(getDateAndTime());
		stringBuilder.append(",action=");
		stringBuilder.append(getAction());
		stringBuilder.append(", best=");
		stringBuilder.append(getBestAccessTime());
		stringBuilder.append(", worst=");
		stringBuilder.append(getWorstAccessTime());
		stringBuilder.append(" ]");
		return stringBuilder.toString();
	}

}
