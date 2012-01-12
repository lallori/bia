/*
 * DeleteSpousePersonCommand.java
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
package org.medici.docsources.command.peoplebase;

/**
 * Command bean for action "Delete Spouse Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.controller.peoplebase.DeleteSpousePersonController
 */
public class DeleteSpousePersonCommand {
	private Integer personId;
	private Integer marriageId;
	private Integer wifeId;
	private Integer husbandId;

	/**
	 * This method returns personId property.
	 * 
	 * @return the personId
	 */
	public Integer getPersonId() {
		return personId;
	}

	/**
	 * This method sets personId property.
	 * 
	 * @param personId
	 *            the personId to set
	 */
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	/**
	 * @param marriageId the marriageId to set
	 */
	public void setMarriageId(Integer marriageId) {
		this.marriageId = marriageId;
	}

	/**
	 * @return the marriageId
	 */
	public Integer getMarriageId() {
		return marriageId;
	}

	/**
	 * @param wifeId the wifeId to set
	 */
	public void setWifeId(Integer wifeId) {
		this.wifeId = wifeId;
	}

	/**
	 * @return the wifeId
	 */
	public Integer getWifeId() {
		return wifeId;
	}

	/**
	 * @param husbandId the husbandId to set
	 */
	public void setHusbandId(Integer husbandId) {
		this.husbandId = husbandId;
	}

	/**
	 * @return the husbandId
	 */
	public Integer getHusbandId() {
		return husbandId;
	}
}
