/*
 * ShowUserPhotoCommand.java
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
package org.medici.bia.command.user;

/**
 * Command bean for action "show user".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.merdici.docsources.controller.user.ShowUserPhotoController
 */
public class ShowUserPhotoCommand {
	private Integer height;
	private Boolean thumbnail;
	private String user;
	private Integer width;

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * This method returns thumbnail property.
	 * 
	 * @return the thumbnail
	 */
	public Boolean getThumbnail() {
		return thumbnail;
	}

	/**
	 * This method returns user property.
	 * 
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * This method returns image's width property.
	 * 
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * This method sets image's heigth property.
	 * 
	 * @param height
	 *            the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * This method sets thumbnail property.
	 * 
	 * @param thumbnail
	 *            the thumbnail to set
	 */
	public void setThumbnail(Boolean thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * This method sets user property.
	 * 
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * This method sets image's width property.
	 * 
	 * @param width
	 *            the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}
}
