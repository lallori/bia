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

import org.medici.docsources.domain.Place.GeoIdEncoding;

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
	private Integer tgnTermId;
	private String placeNameNoAccents;
	private String placeNameWithAccents;
	private String placeType;
	private String placeParent;
	private String placesMemo;
	private String researcher;
	private Date dateCreated;
	private GeoIdEncoding geoIdEncoding;

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
	public Integer getTgnTermId() {
		return tgnTermId;
	}

	/**
	 * @param tgnTermId the tgnTermId to set
	 */
	public void setTgnTermId(Integer tgnTermId) {
		this.tgnTermId = tgnTermId;
	}

	/**
	 * @return the placeNameNoAccents
	 */
	public String getPlaceNameNoAccents() {
		return placeNameNoAccents;
	}

	/**
	 * @param placeNameNoAccents the placeNameNoAccents to set
	 */
	public void setPlaceNameNoAccents(String placeNameNoAccents) {
		this.placeNameNoAccents = placeNameNoAccents;
	}

	/**
	 * @return the placeNameWithAccents
	 */
	public String getPlaceNameWithAccents() {
		return placeNameWithAccents;
	}

	/**
	 * @param placeNameWithAccents the placeNameWithAccents to set
	 */
	public void setPlaceNameWithAccents(String placeNameWithAccents) {
		this.placeNameWithAccents = placeNameWithAccents;
	}

	/**
	 * @return the placeType
	 */
	public String getPlaceType() {
		return placeType;
	}

	/**
	 * @param placeType the placeType to set
	 */
	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	/**
	 * @return the placeParent
	 */
	public String getPlaceParent() {
		return placeParent;
	}

	/**
	 * @param placeParent the placeParent to set
	 */
	public void setPlaceParent(String placeParent) {
		this.placeParent = placeParent;
	}

	/**
	 * @return the placeNotes
	 */
	public String getPlacesMemo() {
		return placesMemo;
	}

	/**
	 * @param placeNotes the placeNotes to set
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
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setGeoIdEncoding(GeoIdEncoding geoIdEncoding) {
		this.geoIdEncoding = geoIdEncoding;
	}

	public GeoIdEncoding getGeoIdEncoding() {
		return geoIdEncoding;
	}
}
