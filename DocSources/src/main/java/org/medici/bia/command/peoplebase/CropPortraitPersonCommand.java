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
	private Double x;
	private Double y;
	private Double x2;
	private Double y2;
	private Double w;
	private Double h;
	
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
	public Double getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(Double x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public Double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(Double y) {
		this.y = y;
	}
	/**
	 * @return the x2
	 */
	public Double getX2() {
		return x2;
	}
	/**
	 * @param x2 the x2 to set
	 */
	public void setX2(Double x2) {
		this.x2 = x2;
	}
	/**
	 * @return the y2
	 */
	public Double getY2() {
		return y2;
	}
	/**
	 * @param y2 the y2 to set
	 */
	public void setY2(Double y2) {
		this.y2 = y2;
	}
	/**
	 * @return the w
	 */
	public Double getW() {
		return w;
	}
	/**
	 * @param w the w to set
	 */
	public void setW(Double w) {
		this.w = w;
	}
	/**
	 * @return the h
	 */
	public Double getH() {
		return h;
	}
	/**
	 * @param h the h to set
	 */
	public void setH(Double h) {
		this.h = h;
	}
}
