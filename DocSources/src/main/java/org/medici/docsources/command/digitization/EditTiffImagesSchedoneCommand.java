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

import org.medici.docsources.domain.Schedone.Formato;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class EditTiffImagesSchedoneCommand {
	private Integer schedoneId;
	private Integer numeroTotaleImmaginiTiff;
	private Long dimMediaImmaginiTiff;
	private Long dimTotaleImmaginiTiff;
	private String compressioneTiff;
	private Formato formatoMediaImmaginiTiff;
	private Formato formatoTotaleImmaginiTiff;
	
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
	 * @return the numeroTotaleImmaginiTiff
	 */
	public Integer getNumeroTotaleImmaginiTiff() {
		return numeroTotaleImmaginiTiff;
	}
	/**
	 * @param numeroTotaleImmaginiTiff the numeroTotaleImmaginiTiff to set
	 */
	public void setNumeroTotaleImmaginiTiff(Integer numeroTotaleImmaginiTiff) {
		this.numeroTotaleImmaginiTiff = numeroTotaleImmaginiTiff;
	}
	/**
	 * @return the dimMediaImmaginiTiff
	 */
	public Long getDimMediaImmaginiTiff() {
		return dimMediaImmaginiTiff;
	}
	/**
	 * @param dimMediaImmaginiTiff the dimMediaImmaginiTiff to set
	 */
	public void setDimMediaImmaginiTiff(Long dimMediaImmaginiTiff) {
		this.dimMediaImmaginiTiff = dimMediaImmaginiTiff;
	}
	/**
	 * @return the dimTotaleImmaginiTiff
	 */
	public Long getDimTotaleImmaginiTiff() {
		return dimTotaleImmaginiTiff;
	}
	/**
	 * @param dimTotaleImmaginiTiff the dimTotaleImmaginiTiff to set
	 */
	public void setDimTotaleImmaginiTiff(Long dimTotaleImmaginiTiff) {
		this.dimTotaleImmaginiTiff = dimTotaleImmaginiTiff;
	}
	/**
	 * @return the compressioneTiff
	 */
	public String getCompressioneTiff() {
		return compressioneTiff;
	}
	/**
	 * @param compressioneTiff the compressioneTiff to set
	 */
	public void setCompressioneTiff(String compressioneTiff) {
		this.compressioneTiff = compressioneTiff;
	}
	/**
	 * @return the formatoMediaImmaginiTiff
	 */
	public Formato getFormatoMediaImmaginiTiff() {
		return formatoMediaImmaginiTiff;
	}
	/**
	 * @param formatoMediaImmaginiTiff the formatoMediaImmaginiTiff to set
	 */
	public void setFormatoMediaImmaginiTiff(Formato formatoMediaImmaginiTiff) {
		this.formatoMediaImmaginiTiff = formatoMediaImmaginiTiff;
	}
	/**
	 * @return the formatoTotaleImmaginiTiff
	 */
	public Formato getFormatoTotaleImmaginiTiff() {
		return formatoTotaleImmaginiTiff;
	}
	/**
	 * @param formatoTotaleImmaginiTiff the formatoTotaleImmaginiTiff to set
	 */
	public void setFormatoTotaleImmaginiTiff(Formato formatoTotaleImmaginiTiff) {
		this.formatoTotaleImmaginiTiff = formatoTotaleImmaginiTiff;
	}
	
}
