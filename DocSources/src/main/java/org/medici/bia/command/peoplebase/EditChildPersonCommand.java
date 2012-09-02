/*
 * EditChildPersonCommand.java
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
package org.medici.bia.command.peoplebase;

/**
 * Command bean for action "Edit Child Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.bia.controller.peoplebase.EditChildPersonController
 */
public class EditChildPersonCommand {
	private Integer id;
	private Integer parentId;
	private Integer childId;
	private String childDescription;
	private Integer bornYear;
	private Integer deathYear;
	private Integer ageAtDeath;
	
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}
	
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * @return the childId
	 */
	public Integer getChildId() {
		return childId;
	}
	
	/**
	 * @param childId the childId to set
	 */
	public void setChildId(Integer childId) {
		this.childId = childId;
	}
	
	/**
	 * @return the childDescription
	 */
	public String getChildDescription() {
		return childDescription;
	}
	
	/**
	 * @param childDescription the childDescription to set
	 */
	public void setChildDescription(String childDescription) {
		this.childDescription = childDescription;
	}
	
	/**
	 * @return the bornYear
	 */
	public Integer getBornYear() {
		return bornYear;
	}
	
	/**
	 * @param bornYear the bornYear to set
	 */
	public void setBornYear(Integer bornYear) {
		this.bornYear = bornYear;
	}
	
	/**
	 * @return the deathYear
	 */
	public Integer getDeathYear() {
		return deathYear;
	}
	
	/**
	 * @param deathYear the deathYear to set
	 */
	public void setDeathYear(Integer deathYear) {
		this.deathYear = deathYear;
	}
	
	/**
	 * @return the ageAtDeath
	 */
	public Integer getAgeAtDeath() {
		return ageAtDeath;
	}

	/**
	 * @param ageAtDeath the ageAtDeath to set
	 */
	public void setAgeAtDeath(Integer ageAtDeath) {
		this.ageAtDeath = ageAtDeath;
	}
}
