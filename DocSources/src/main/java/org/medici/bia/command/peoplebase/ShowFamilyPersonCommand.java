/*
 * ShowFamilyPersonCommand.java
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

import javax.validation.constraints.NotNull;

/**
 * Command bean for action "Show Family Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.docsources.controller.peoplebase.ShowPersonController
 */
public class ShowFamilyPersonCommand {
	@NotNull
	private String altName;
	@NotNull
	private Integer nameId;
	
	/**
	 * @param altName the altName to set
	 */
	public void setAltName(String altName) {
		this.altName = altName;
	}

	/**
	 * @return the altName
	 */
	public String getAltName() {
		return altName;
	}

	/**
	 * @param nameId the nameId to set
	 */
	public void setNameId(Integer nameId) {
		this.nameId = nameId;
	}

	/**
	 * @return the nameId
	 */
	public Integer getNameId() {
		return nameId;
	}

		
}
