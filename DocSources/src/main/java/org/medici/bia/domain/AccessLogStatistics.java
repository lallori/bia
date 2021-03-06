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
package org.medici.bia.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	@Temporal(TemporalType.DATE)
	@Column (name="\"date\"", nullable=false)
	private Date date;
	@Column (name="\"action\"", length=2000, nullable=false)
	private String action;
	@Column (name="\"httpMethod\"", length=8, nullable=false)
	private String httpMethod;
	@Column (name="\"count\"", nullable=false)
	private Integer count;
	@Column(name="\"averageTime\"", nullable=false)
	private Double averageTime;
	@Column(name="\"bestTime\"", nullable=false)
	private Long bestTime;
	@Column(name="\"worstTime\"", nullable=false)
	private Long worstTime;

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

		setDate(dateAndTime);
		setAction(action);
		setHttpMethod(httpMethod);
	}


	/**
	 * @param worstTime the worstTime to set
	 */
	public void setWorstTime(Long worstTime) {
		this.worstTime = worstTime;
	}

	/**
	 * @param accessLogStatisticsId the accessLogStatisticsId to set
	 */
	public void setAccessLogStatisticsId(Integer accessLogStatisticsId) {
		this.accessLogStatisticsId = accessLogStatisticsId;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getAccessLogStatisticsId() {
		return accessLogStatisticsId;
	}

	/**
	 * @return the dateAndTime
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
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
	 * @return the averageTime
	 */
	public Double getAverageTime() {
		return averageTime;
	}

	/**
	 * @param averageTime the averageTime to set
	 */
	public void setAverageTime(Double averageTime) {
		this.averageTime = averageTime;
	}

	/**
	 * @return the bestTime
	 */
	public Long getBestTime() {
		return bestTime;
	}

	/**
	 * @param bestTime the bestTime to set
	 */
	public void setBestTime(Long bestTime) {
		this.bestTime = bestTime;
	}

	/**
	 * @return the worstTime
	 */
	public Long getWorstTime() {
		return worstTime;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[id=");
		stringBuilder.append(getAccessLogStatisticsId());
		stringBuilder.append("date=");
		stringBuilder.append(getDate());
		stringBuilder.append(",action=");
		stringBuilder.append(getAction());
		stringBuilder.append(", average=");
		stringBuilder.append(getAverageTime());
		stringBuilder.append(", best=");
		stringBuilder.append(getBestTime());
		stringBuilder.append(", worst=");
		stringBuilder.append(getWorstTime());
		stringBuilder.append(" ]");
		return stringBuilder.toString();
	}

}
