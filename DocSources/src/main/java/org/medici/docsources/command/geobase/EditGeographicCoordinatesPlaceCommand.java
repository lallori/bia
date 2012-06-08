/*
 * EditGeographicCoordinatesPlaceCommand.java
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

/**
 * Command bean for action "edit geographic coordinates".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.EditDetailsPlaceController.docsources.controller.geobase.ModifyPlaceController
 */
public class EditGeographicCoordinatesPlaceCommand {
	private Integer placeAllId;
	private Integer placeGeographicCoordinatesId;
	private Integer degreeLatitude;
	private Integer minuteLatitude;
	private Integer secondLatitude;
	private String directionLatitude;
	private Integer degreeLongitude;
	private Integer minuteLongitude;
	private Integer secondLongitude;
	private String directionLongitude;

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
	 * @param degreeLatitude the degreeLatitude to set
	 */
	public void setDegreeLatitude(Integer degreeLatitude) {
		this.degreeLatitude = degreeLatitude;
	}

	/**
	 * @return the degreeLatitude
	 */
	public Integer getDegreeLatitude() {
		return degreeLatitude;
	}

	/**
	 * @param minuteLatitude the minuteLatitude to set
	 */
	public void setMinuteLatitude(Integer minuteLatitude) {
		this.minuteLatitude = minuteLatitude;
	}

	/**
	 * @return the minuteLatitude
	 */
	public Integer getMinuteLatitude() {
		return minuteLatitude;
	}

	/**
	 * @param secondLatitude the secondLatitude to set
	 */
	public void setSecondLatitude(Integer secondLatitude) {
		this.secondLatitude = secondLatitude;
	}

	/**
	 * @return the secondLatitude
	 */
	public Integer getSecondLatitude() {
		return secondLatitude;
	}

	/**
	 * @param directionLatitude the directionLatitude to set
	 */
	public void setDirectionLatitude(String directionLatitude) {
		this.directionLatitude = directionLatitude;
	}

	/**
	 * @return the directionLatitude
	 */
	public String getDirectionLatitude() {
		return directionLatitude;
	}

	/**
	 * @param degreeLongitude the degreeLongitude to set
	 */
	public void setDegreeLongitude(Integer degreeLongitude) {
		this.degreeLongitude = degreeLongitude;
	}

	/**
	 * @return the degreeLongitude
	 */
	public Integer getDegreeLongitude() {
		return degreeLongitude;
	}

	/**
	 * @param minuteLongitude the minuteLongitude to set
	 */
	public void setMinuteLongitude(Integer minuteLongitude) {
		this.minuteLongitude = minuteLongitude;
	}

	/**
	 * @return the minuteLongitude
	 */
	public Integer getMinuteLongitude() {
		return minuteLongitude;
	}

	/**
	 * @param secondLongitude the secondLongitude to set
	 */
	public void setSecondLongitude(Integer secondLongitude) {
		this.secondLongitude = secondLongitude;
	}

	/**
	 * @return the secondLongitude
	 */
	public Integer getSecondLongitude() {
		return secondLongitude;
	}

	/**
	 * @param directionLongitude the directionLongitude to set
	 */
	public void setDirectionLongitude(String directionLongitude) {
		this.directionLongitude = directionLongitude;
	}

	/**
	 * @return the directionLongitude
	 */
	public String getDirectionLongitude() {
		return directionLongitude;
	}

	/**
	 * @param placeGeographicCoordinatesId the placeGeographicCoordinatesId to set
	 */
	public void setPlaceGeographicCoordinatesId(
			Integer placeGeographicCoordinatesId) {
		this.placeGeographicCoordinatesId = placeGeographicCoordinatesId;
	}

	/**
	 * @return the placeGeographicCoordinatesId
	 */
	public Integer getPlaceGeographicCoordinatesId() {
		return placeGeographicCoordinatesId;
	}
}
