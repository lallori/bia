/*
 * EditDetailsPlaceCommand.java
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
package org.medici.docsources.command.geobase;

import java.util.Date;

/**
 * Command bean for action "Edit Details Place".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.EditDetailsPlaceController.docsources.controller.geobase.EditDetailsPlaceController
 */
public class EditDetailsPlaceCommand {
	private Integer placeAllId;
	private Integer geogKey;
	private Integer placeNameId;
	private String termAccent;
	private String plType;
	private String plParent;
	private String placesMemo;
	private String researcher;
	private Date dateEntered;
	private String plSource;
	private String placeName;
	private Integer parentPlaceAllId;

	/**
	 * This method returns placeId property.
	 * 
	 * @return the placeId
	 */
	public Integer getPlaceAllId() {
		return placeAllId;
	}

	/**
	 * This method sets placeId property.
	 * 
	 * @param placeId
	 *            the placeId to set.
	 */
	public void setPlaceAllId(Integer placeAllId) {
		this.placeAllId = placeAllId;
	}

	/**
	 * @return the geogKey
	 */
	public Integer getGeogKey() {
		return geogKey;
	}

	/**
	 * @param geogKey the geogKey to set
	 */
	public void setGeogKey(Integer geogKey) {
		this.geogKey = geogKey;
	}

	/**
	 * @return the tgnTermId
	 */
	public Integer getPlaceNameId() {
		return placeNameId;
	}

	/**
	 * @param tgnTermId the tgnTermId to set
	 */
	public void setPlaceNameId(Integer placeNameId) {
		this.placeNameId = placeNameId;
	}

	/**
	 * @return the termAccent
	 */
	public String getTermAccent() {
		return termAccent;
	}

	/**
	 * @param termAccent the termAccent to set
	 */
	public void setTermAccent(String termAccent) {
		this.termAccent = termAccent;
	}

	/**
	 * @return the plType
	 */
	public String getPlType() {
		return plType;
	}

	/**
	 * @param plType the plType to set
	 */
	public void setPlType(String plType) {
		this.plType = plType;
	}

	/**
	 * @return the plaParent
	 */
	public String getPlParent() {
		return plParent;
	}

	/**
	 * @param plParent the plParent to set
	 */
	public void setPlParent(String plParent) {
		this.plParent = plParent;
	}

	/**
	 * @return the placeMemo
	 */
	public String getPlacesMemo() {
		return placesMemo;
	}

	/**
	 * @param placeNMemo the placeMemo to set
	 */
	public void setPlacesMemo(String placesMemo) {
		this.placesMemo = placesMemo;
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

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateEntered() {
		return dateEntered;
	}

	/**
	 * 
	 * @param plSource the plSource to set
	 */
	public void setPlSource(String plSource) {
		this.plSource = plSource;
	}

	/**
	 * 
	 * @return the plSource
	 */
	public String getPlSource() {
		return plSource;
	}

	/**
	 * 
	 * @param placeName the placeName to set
	 */
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	/**
	 * 
	 * @return the placeName
	 */
	public String getPlaceName() {
		return placeName;
	}

	public void setParentPlaceAllId(Integer parentPlaceAllId) {
		this.parentPlaceAllId = parentPlaceAllId;
	}

	public Integer getParentPlaceAllId() {
		return parentPlaceAllId;
	}
}
