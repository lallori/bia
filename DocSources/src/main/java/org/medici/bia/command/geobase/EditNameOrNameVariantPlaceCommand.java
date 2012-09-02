/*
 * EditNameOrNameVariantPlaceCommand.java
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
package org.medici.bia.command.geobase;

/**
 * Command bean for action "modify place".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.EditDetailsPlaceController.docsources.controller.geobase.ModifyPlaceController
 */
public class EditNameOrNameVariantPlaceCommand {
	private Integer placeAllId;
	private Integer currentPlaceAllId;
	private Integer geogKey;
	private String plType;
	private String plName;
	private String researcher;

	/**
	 * This method returns placeId property.
	 * 
	 * @return the placeAllId
	 */
	public Integer getPlaceAllId() {
		return placeAllId;
	}

	/**
	 * This method sets placeId property.
	 * 
	 * @param placeAllId the placeAllId to set.
	 */
	public void setPlaceAllId(Integer placeAllId) {
		this.placeAllId = placeAllId;
	}

	/**
	 * @param geogKey the geogKey to set
	 */
	public void setGeogKey(Integer geogKey) {
		this.geogKey = geogKey;
	}

	/**
	 * @return the geogKey
	 */
	public Integer getGeogKey() {
		return geogKey;
	}

	/**
	 * @param plType the plType to set
	 */
	public void setPlType(String plType) {
		this.plType = plType;
	}

	/**
	 * @return the plType
	 */
	public String getPlType() {
		return plType;
	}

	/**
	 * @param plName the plName to set
	 */
	public void setPlName(String plName) {
		this.plName = plName;
	}

	/**
	 * @return the plName
	 */
	public String getPlName() {
		return plName;
	}

	/**
	 * @param currentPlaceAllId the currentPlaceAllId to set
	 */
	public void setCurrentPlaceAllId(Integer currentPlaceAllId) {
		this.currentPlaceAllId = currentPlaceAllId;
	}

	/**
	 * @return the currentPlaceAllId
	 */
	public Integer getCurrentPlaceAllId() {
		return currentPlaceAllId;
	}

	/**
	 * @param researcher the researcher to set
	 */
	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}

	/**
	 * @return the researcher
	 */
	public String getResearcher() {
		return researcher;
	}
}
