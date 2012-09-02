/*
 * EditNamesPersonCommand.java
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

import java.util.Set;

import org.medici.bia.domain.AltName;

/**
 * Command bean for action "Edit Names Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.bia.controller.peoplebase.EditNamesPersonController
 */
public class EditNamesPersonCommand {
	private Integer personId;
	private Set<AltName> altNames;

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
	 * @param altNames the altNames to set
	 */
	public void setAltNames(Set<AltName> altNames) {
		this.altNames = altNames;
	}

	/**
	 * @return the altNames
	 */
	public Set<AltName> getAltNames() {
		return altNames;
	}
}
