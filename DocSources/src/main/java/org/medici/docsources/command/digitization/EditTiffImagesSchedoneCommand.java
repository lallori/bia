/*
 * EditTiffImagesSchedoneCommand.java
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
package org.medici.docsources.command.digitization;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class EditTiffImagesSchedoneCommand {
	private Integer schedoneId;
	private Integer numeroTotaleImmagini;
	private Long dimMediaImmagini;
	private Long dimTotaleImmagini;
	
	/**
	 * @return the schedoneId
	 */
	public Integer getSchedoneId() {
		return schedoneId;
	}
	
	/**
	 * @param schedoneId the schedoneId to set
	 */
	public void setSchedoneId(Integer schedoneId) {
		this.schedoneId = schedoneId;
	}
	
	/**
	 * @return the numeroTotaleImmagini
	 */
	public Integer getNumeroTotaleImmagini() {
		return numeroTotaleImmagini;
	}
	
	/**
	 * @param numeroTotaleImmagini the numeroTotaleImmagini to set
	 */
	public void setNumeroTotaleImmagini(Integer numeroTotaleImmagini) {
		this.numeroTotaleImmagini = numeroTotaleImmagini;
	}
	
	/**
	 * @return the dimMediaImmagini
	 */
	public Long getDimMediaImmagini() {
		return dimMediaImmagini;
	}
	
	/**
	 * @param dimMediaImmagini the dimMediaImmagini to set
	 */
	public void setDimMediaImmagini(Long dimMediaImmagini) {
		this.dimMediaImmagini = dimMediaImmagini;
	}
	
	/**
	 * @return the dimTotaleImmagini
	 */
	public Long getDimTotaleImmagini() {
		return dimTotaleImmagini;
	}
	
	/**
	 * @param dimTotaleImmagini the dimTotaleImmagini to set
	 */
	public void setDimTotaleImmagini(Long dimTotaleImmagini) {
		this.dimTotaleImmagini = dimTotaleImmagini;
	}
}
