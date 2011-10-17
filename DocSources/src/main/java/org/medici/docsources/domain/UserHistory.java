/*
 * UserHistory.java
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

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserHistory entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblUserHistory\"" ) 
public class UserHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2001847076255349765L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="\"idUserHistory\"", length=10, nullable=false)
	private Integer idUserHistory;
	@Column (name="\"dateAndTime\"", nullable=false)
	private Date dateAndTime;
	@Column (name="\"username\"", length=25, nullable=false)
	private String username;
	@Column (name="\"baseCategory\"", length=20, nullable=false)
	@Enumerated(EnumType.STRING)
	private BaseCategory baseCategory;
	@Column (name="\"action\"", length=100, nullable=false)
	private String action;
	@Column (name="\"entityId\"", length=10, nullable=false)
	private Integer entityId;

	/**
	 * Default Constructor
	 */
	public UserHistory() {
		super();
	}

	/**
	 * 
	 * @param baseCategory
	 * @param action
	 * @param entityId
	 */
	public UserHistory(BaseCategory baseCategory, String action, Integer entityId) {
		this.setDateAndTime(new Date());
		this.username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		this.baseCategory = baseCategory;
		this.action = action;
		this.entityId = entityId;
	}

	/**
	 * @return the idHistoryLog
	 */
	public Integer getIdUserHistory() {
		return idUserHistory;
	}
	
	/**
	 * @param idUserHistory the idUserHistory to set
	 */
	public void setIdUserHistory(Integer idUserHistory) {
		this.idUserHistory = idUserHistory;
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
	 * @param baseCategory the baseCategory to set
	 */
	public void setBaseCategory(BaseCategory baseCategory) {
		this.baseCategory = baseCategory;
	}

	/**
	 * @return the baseCategory
	 */
	public BaseCategory getBaseCategory() {
		return baseCategory;
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
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the entityId
	 */
	public Integer getEntityId() {
		return entityId;
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("[ ");
		stringBuffer.append(getDateAndTime());
		stringBuffer.append(" - ");
		stringBuffer.append(getUsername());
		stringBuffer.append(" - ");
		stringBuffer.append(getBaseCategory());
		stringBuffer.append(" - ");
		stringBuffer.append(getAction());
		stringBuffer.append(" - ");
		stringBuffer.append(getEntityId());
		stringBuffer.append(" ]");
		return stringBuffer.toString();
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum BaseCategory {
		DOCUMENT("Document"), PEOPLE("People"), PLACE("Place"), VOLUME("Volume");
		
		private final String baseCategory;

	    private BaseCategory(String value) {
			baseCategory = value;
	    }

	    @Override
	    public String toString(){
	        return baseCategory;
	    }
	}
}

