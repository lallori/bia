/*
 * CropPortraitPersonCommand.java
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

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class CropPortraitPersonCommand {
	private Integer personId;
	/**
	 * @return the personId
	 */
	public Integer getPersonId() {
		return personId;
	}
	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	/**
	 * @return the x
	 */
	public Integer getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(Integer x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public Integer getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(Integer y) {
		this.y = y;
	}
	/**
	 * @return the x2
	 */
	public Integer getX2() {
		return x2;
	}
	/**
	 * @param x2 the x2 to set
	 */
	public void setX2(Integer x2) {
		this.x2 = x2;
	}
	/**
	 * @return the y2
	 */
	public Integer getY2() {
		return y2;
	}
	/**
	 * @param y2 the y2 to set
	 */
	public void setY2(Integer y2) {
		this.y2 = y2;
	}
	/**
	 * @return the w
	 */
	public Integer getW() {
		return w;
	}
	/**
	 * @param w the w to set
	 */
	public void setW(Integer w) {
		this.w = w;
	}
	/**
	 * @return the h
	 */
	public Integer getH() {
		return h;
	}
	/**
	 * @param h the h to set
	 */
	public void setH(Integer h) {
		this.h = h;
	}
	private Integer x;
	private Integer y;
	private Integer x2;
	private Integer y2;
	private Integer w;
	private Integer h;
}
