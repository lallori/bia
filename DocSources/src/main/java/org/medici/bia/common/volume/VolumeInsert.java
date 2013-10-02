/*
 * VolumeInsert.java
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
package org.medici.bia.common.volume;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class VolumeInsert {
	
	private Integer volNum;
	private String volLetExt;
	private String insertNum;
	private String insertLet;
	
	public Integer getVolNum() {
		return volNum;
	}

	public void setVolNum(Integer volNum) {
		this.volNum = volNum;
	}

	public String getVolLetExt() {
		return volLetExt;
	}

	public void setVolLetExt(String volLetExt) {
		this.volLetExt = volLetExt;
	}

	public String getInsertNum() {
		return insertNum;
	}

	public void setInsertNum(String insertNum) {
		this.insertNum = insertNum;
	}

	public String getInsertLet() {
		return insertLet;
	}

	public void setInsertLet(String insertLet) {
		this.insertLet = insertLet;
	}

	public VolumeInsert(Integer volNum, String volLetExt, String insertNum, String insertLet) {
		this.volNum = volNum;
		this.volLetExt = volLetExt;
		this.insertNum = insertNum;
		this.insertLet = insertLet;
	}
	
	public VolumeInsert(Integer volNum, String volLetExt, String insertNum) {
		this(volNum, volLetExt, insertNum, null);
	}
	
	public VolumeInsert(Integer volNum, String insertNum) {
		this(volNum, null, insertNum, null);
	}
}
