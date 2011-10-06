/*
 * EditExternalLinkPlaceCommand.java
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
 * Command bean for action "edit external link place".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.EditDetailsPlaceController.docsources.controller.geobase.EditExternalLinkPlaceController
 */
public class EditExternalLinkPlaceCommand {
	private Integer placeAllId;
	private Integer placeExternalLinksId;
	private String externalLink;
	private String description;

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
	 * @param placeExternalLinksId the placeExternalLinksId to set
	 */
	public void setPlaceExternalLinksId(Integer placeExternalLinksId) {
		this.placeExternalLinksId = placeExternalLinksId;
	}

	/**
	 * @return the placeExternalLinksId
	 */
	public Integer getPlaceExternalLinksId() {
		return placeExternalLinksId;
	}

	/**
	 * @param externalLink the externalLink to set
	 */
	public void setExternalLink(String externalLink) {
		this.externalLink = externalLink;
	}

	/**
	 * @return the externalLink
	 */
	public String getExternalLink() {
		return externalLink;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
}
