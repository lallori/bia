/*
 * EditJpegImagesSchedoneCommand.java
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
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class EditJpegImagesSchedoneCommand {
	private Integer schedoneId;
	private Integer numeroTotaleImmaginiJpeg;
	private Long dimMediaImmaginiJpeg;
	private Long dimTotaleImmaginiJpeg;
	private String compressioneJpeg;
	private Formato formatoMediaImmaginiJpeg;
	private Formato formatoTotaleImmaginiJpeg;
	
	
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
	 * @return the numeroTotaleImmaginiJpeg
	 */
	public Integer getNumeroTotaleImmaginiJpeg() {
		return numeroTotaleImmaginiJpeg;
	}
	/**
	 * @param numeroTotaleImmaginiJpeg the numeroTotaleImmaginiJpeg to set
	 */
	public void setNumeroTotaleImmaginiJpeg(Integer numeroTotaleImmaginiJpeg) {
		this.numeroTotaleImmaginiJpeg = numeroTotaleImmaginiJpeg;
	}
	/**
	 * @return the dimMediaImmaginiJpeg
	 */
	public Long getDimMediaImmaginiJpeg() {
		return dimMediaImmaginiJpeg;
	}
	/**
	 * @param dimMediaImmaginiJpeg the dimMediaImmaginiJpeg to set
	 */
	public void setDimMediaImmaginiJpeg(Long dimMediaImmaginiJpeg) {
		this.dimMediaImmaginiJpeg = dimMediaImmaginiJpeg;
	}
	/**
	 * @return the dimTotaleImmaginiJpeg
	 */
	public Long getDimTotaleImmaginiJpeg() {
		return dimTotaleImmaginiJpeg;
	}
	/**
	 * @param dimTotaleImmaginiJpeg the dimTotaleImmaginiJpeg to set
	 */
	public void setDimTotaleImmaginiJpeg(Long dimTotaleImmaginiJpeg) {
		this.dimTotaleImmaginiJpeg = dimTotaleImmaginiJpeg;
	}
	/**
	 * @return the compressioneJpeg
	 */
	public String getCompressioneJpeg() {
		return compressioneJpeg;
	}
	/**
	 * @param compressioneJpeg the compressioneJpeg to set
	 */
	public void setCompressioneJpeg(String compressioneJpeg) {
		this.compressioneJpeg = compressioneJpeg;
	}
	/**
	 * @return the formatoMediaImmaginiJpeg
	 */
	public Formato getFormatoMediaImmaginiJpeg() {
		return formatoMediaImmaginiJpeg;
	}
	/**
	 * @param formatoMediaImmaginiJpeg the formatoMediaImmaginiJpeg to set
	 */
	public void setFormatoMediaImmaginiJpeg(Formato formatoMediaImmaginiJpeg) {
		this.formatoMediaImmaginiJpeg = formatoMediaImmaginiJpeg;
	}
	/**
	 * @return the formatoTotaleImmaginiJpeg
	 */
	public Formato getFormatoTotaleImmaginiJpeg() {
		return formatoTotaleImmaginiJpeg;
	}
	/**
	 * @param formatoTotaleImmaginiJpeg the formatoTotaleImmaginiJpeg to set
	 */
	public void setFormatoTotaleImmaginiJpeg(Formato formatoTotaleImmaginiJpeg) {
		this.formatoTotaleImmaginiJpeg = formatoTotaleImmaginiJpeg;
	}
	
}
