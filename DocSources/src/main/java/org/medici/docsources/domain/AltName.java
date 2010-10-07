/*
 * AltName.java
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AltName entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Table ( name = "\"tblAltNames\"" ) 
public class AltName implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5441152442133272419L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"NameID\"", length=10, nullable=false)
	private Integer nameID;
	@ManyToOne
	@JoinColumn(name="\"PersonID\"")
	private People personId;
	@Column (name="\"NamePrefix\"", length=50)
	private String namePrefix;
	@Column (name="\"AltName\"", length=255)
	private String altName;
	@Column (name="\"NameType\"", length=50)
	private String nameType;
	@Column (name="\"Notes\"", columnDefinition="LONGTEXT")
	private String notes;
	
	/**
	 * @return the nameID
	 */
	public Integer getNameID() {
		return nameID;
	}
	/**
	 * @param nameID the nameID to set
	 */
	public void setNameID(Integer nameID) {
		this.nameID = nameID;
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
	 * @return the namePrefix
	 */
	public String getNamePrefix() {
		return namePrefix;
	}
	/**
	 * @param namePrefix the namePrefix to set
	 */
	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}
	/**
	 * @return the altName
	 */
	public String getAltName() {
		return altName;
	}
	/**
	 * @param altName the altName to set
	 */
	public void setAltName(String altName) {
		this.altName = altName;
	}
	/**
	 * @return the nameType
	 */
	public String getNameType() {
		return nameType;
	}
	/**
	 * @param nameType the nameType to set
	 */
	public void setNameType(String nameType) {
		this.nameType = nameType;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
