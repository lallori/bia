/*
 * AccessLog.java
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

/**
 * AccessLog entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblAccessLog\"" ) 
public class AccessLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2001847076255349765L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="\"idAccessLog\"", length=10, nullable=false)
	private Integer idAccessLog;
	@Column (name="\"dateAndTime\"", nullable=false)
	private Date dateAndTime;
	@Column (name="\"ipAddress\"", length=50, nullable=false)
	private String ipAddress;
	@Column (name="\"account\"", length=50, nullable=false)
	private String account;
	@Column (name="\"authorities\"", length=500)
	private String authorities;
	@Column (name="\"httpMethod\"", length=8, nullable=false)
	private String httpMethod;
	@Column (name="\"action\"", length=1000, nullable=false)
	private String action;
	@Column (name="\"informations\"", columnDefinition="LONGTEXT")
	private String informations;
	@Column (name="\"errors\"", length=3000)
	private String errors;
	@Column (name="\"executionTime\"", length=15, nullable=false)
	private Long executionTime;

	/**
	 * @return the idAccessLog
	 */
	public Integer getIdAccessLog() {
		return idAccessLog;
	}
	
	/**
	 * @param idAccessLog the idAccessLog to set
	 */
	public void setIdAccessLog(Integer idAccessLog) {
		this.idAccessLog = idAccessLog;
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
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the authorities
	 */
	public String getAuthorities() {
		return authorities;
	}
	
	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	
	/**
	 * @param httpMethod the httpMethod to set
	 */
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	/**
	 * @return the httpMethod
	 */
	public String getHttpMethod() {
		return httpMethod;
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
	 * @return the informations
	 */
	public String getInformations() {
		return informations;
	}
	
	/**
	 * @param informations the informations to set
	 */
	public void setInformations(String informations) {
		this.informations = informations;
	}

	
	/**
	 * @param errors the errors to set
	 */
	public void setErrors(String errors) {
		this.errors = errors;
	}

	/**
	 * @return the errors
	 */
	public String getErrors() {
		return errors;
	}

	/**
	 * @param executionTime the executionTime to set
	 */
	public void setExecutionTime(Long executionTime) {
		this.executionTime = executionTime;
	}

	/**
	 * @return the executionTime
	 */
	public Long getExecutionTime() {
		return executionTime;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[ ");
		stringBuilder.append(getDateAndTime());
		stringBuilder.append(" - ");
		stringBuilder.append(getIpAddress());
		stringBuilder.append(" - ");
		stringBuilder.append(getAccount());
		stringBuilder.append(" - ");
		stringBuilder.append(getAuthorities());
		stringBuilder.append(" - ");
		stringBuilder.append(getHttpMethod());
		stringBuilder.append(" - ");
		stringBuilder.append(getAction());
		stringBuilder.append(" - ");
		stringBuilder.append(getInformations());
		stringBuilder.append(" - ");
		stringBuilder.append(getErrors());
		stringBuilder.append(" ]");
		return stringBuilder.toString();
	}
}
