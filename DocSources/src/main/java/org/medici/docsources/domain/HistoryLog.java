/*
 * HistoryLog.java
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
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * HistoryLog entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblHistoryLog\"" ) 
public class HistoryLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2001847076255349765L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="\"idHistoryLog\"", length=10, nullable=false)
	private Integer idHistoryLog;
	@Column (name="\"dateAndTime\"", nullable=false)
	private Date dateAndTime;
	@Column (name="\"ipAddress\"", length=50, nullable=false)
	private String ipAddress;
	@Column (name="\"username\"", length=50, nullable=false)
	private String username;
	@Column (name="\"authorities\"", length=50)
	private String authorities;
	@Column (name="\"actionCategory\"", length=20, nullable=false)
	@Enumerated(EnumType.STRING)
	private ActionCategory actionCategory;
	@Column (name="\"action\"", length=100, nullable=false)
	private String action;
	@Column (name="\"actionUrl\"", length=100, nullable=false)
	private String actionUrl;
	@Column (name="\"informations\"", length=3000)
	private String informations;

	/**
	 * @return the idHistoryLog
	 */
	public Integer getIdHistoryLog() {
		return idHistoryLog;
	}
	
	/**
	 * @param idHistoryLog the idHistoryLog to set
	 */
	public void setIdHistoryLog(Integer idHistoryLog) {
		this.idHistoryLog = idHistoryLog;
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
	 * @param actionCategory the actionCategory to set
	 */
	public void setActionCategory(ActionCategory actionCategory) {
		this.actionCategory = actionCategory;
	}

	/**
	 * @return the actionCategory
	 */
	public ActionCategory getActionCategory() {
		return actionCategory;
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
	 * @param actionUrl the actionUrl to set
	 */
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	/**
	 * @return the actionURL
	 */
	public String getActionUrl() {
		return actionUrl;
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

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("[ ");
		stringBuffer.append(getDateAndTime());
		stringBuffer.append(" - ");
		stringBuffer.append(getIpAddress());
		stringBuffer.append(" - ");
		stringBuffer.append(getUsername());
		stringBuffer.append(" - ");
		stringBuffer.append(getAuthorities());
		stringBuffer.append(" - ");
		stringBuffer.append(getAction());
		stringBuffer.append(" - ");
		stringBuffer.append(getInformations());
		stringBuffer.append(" ]");
		return stringBuffer.toString();
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum ActionCategory {
		DOCUMENTS("Documents"), PEOPLE("People"), PLACE("Place"), VOLUME("Volume"), SIMPLE_SEARCH("Simple Search"), ADVANCED_SEARCH("Advanced Search"), UNKNOWN_CATEGORY("Unknown");
		
		private final String actionCategory;

	    private ActionCategory(String value) {
	    	actionCategory = value;
	    }

	    @Override
	    public String toString(){
	        return actionCategory;
	    }
	}
}
