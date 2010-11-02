/*
 * PrcLink.java
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

/**
 * PrcLink entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Audited
@Table ( name = "\"tblPrcLink\"" )
public class PrcLink implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3357784442938115944L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"PRCLinkID\"", nullable=false)
	private Integer prcLinkId;
	@ManyToOne
	@JoinColumn(name="\"PersonID\"")
	private People personId;
	@ManyToOne
	@JoinColumn (name="\"RoleCatID\"")
	private RoleCat roleCatId;
	@Column (name="\"DateCreated\"")
	@Temporal (TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	/**
	 * @return the prcLinkId
	 */
	public Integer getPrcLinkId() {
		return prcLinkId;
	}
	
	/**
	 * @param prcLinkId the prcLinkId to set
	 */
	public void setPrcLinkId(Integer prcLinkId) {
		this.prcLinkId = prcLinkId;
	}
	
	/**
	 * @return the personId
	 */
	public People getPersonId() {
		return personId;
	}
	
	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(People personId) {
		this.personId = personId;
	}
	
	/**
	 * @return the roleCatId
	 */
	public RoleCat getRoleCatId() {
		return roleCatId;
	}
	
	/**
	 * @param roleCatId the roleCatId to set
	 */
	public void setRoleCatId(RoleCat roleCatId) {
		this.roleCatId = roleCatId;
	}
	
	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	
	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
