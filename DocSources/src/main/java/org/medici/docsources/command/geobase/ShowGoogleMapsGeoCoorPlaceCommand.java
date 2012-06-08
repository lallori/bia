/*
 * ShowGoogleMapsGeoCoorPlaceCommand.java
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

import javax.validation.constraints.NotNull;

/**
 * Command bean for action "Show Google Maps Geo Coor Place".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.docsources.controller.peoplebase.ShowPersonController
 */
public class ShowGoogleMapsGeoCoorPlaceCommand {
	@NotNull
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
	 * @return the degreeLatitude
	 */
	public Integer getDegreeLatitude() {
		return degreeLatitude;
	}

	/**
	 * @param degreeLatitude the degreeLatitude to set
	 */
	public void setDegreeLatitude(Integer degreeLatitude) {
		this.degreeLatitude = degreeLatitude;
	}

	/**
	 * @return the minuteLatitude
	 */
	public Integer getMinuteLatitude() {
		return minuteLatitude;
	}

	/**
	 * @param minuteLatitude the minuteLatitude to set
	 */
	public void setMinuteLatitude(Integer minuteLatitude) {
		this.minuteLatitude = minuteLatitude;
	}

	/**
	 * @return the secondLatitude
	 */
	public Integer getSecondLatitude() {
		return secondLatitude;
	}

	/**
	 * @param secondLatitude the secondLatitude to set
	 */
	public void setSecondLatitude(Integer secondLatitude) {
		this.secondLatitude = secondLatitude;
	}

	/**
	 * @return the directionLatitude
	 */
	public String getDirectionLatitude() {
		return directionLatitude;
	}

	/**
	 * @param directionLatitude the directionLatitude to set
	 */
	public void setDirectionLatitude(String directionLatitude) {
		this.directionLatitude = directionLatitude;
	}

	/**
	 * @return the degreeLongitude
	 */
	public Integer getDegreeLongitude() {
		return degreeLongitude;
	}

	/**
	 * @param degreeLongitude the degreeLongitude to set
	 */
	public void setDegreeLongitude(Integer degreeLongitude) {
		this.degreeLongitude = degreeLongitude;
	}

	/**
	 * @return the minuteLongitude
	 */
	public Integer getMinuteLongitude() {
		return minuteLongitude;
	}

	/**
	 * @param minuteLongitude the minuteLongitude to set
	 */
	public void setMinuteLongitude(Integer minuteLongitude) {
		this.minuteLongitude = minuteLongitude;
	}

	/**
	 * @return the secondLongitude
	 */
	public Integer getSecondLongitude() {
		return secondLongitude;
	}

	/**
	 * @param secondLongitude the secondLongitude to set
	 */
	public void setSecondLongitude(Integer secondLongitude) {
		this.secondLongitude = secondLongitude;
	}

	/**
	 * @return the directionLongitude
	 */
	public String getDirectionLongitude() {
		return directionLongitude;
	}

	/**
	 * @param directionLongitude the directionLongitude to set
	 */
	public void setDirectionLongitude(String directionLongitude) {
		this.directionLongitude = directionLongitude;
	}

	/**
	 * @param placeId the placeId to set
	 */
	public void setPlaceAllId(Integer placeAllId) {
		this.placeAllId = placeAllId;
	}

	/**
	 * @return the placeId
	 */
	public Integer getPlaceAllId() {
		return placeAllId;
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
