/*
 * EditPdfImagesSchedoneCommand.java
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
package org.medici.bia.command.digitization;

import org.medici.bia.domain.Schedone.Formato;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class EditPdfImagesSchedoneCommand {
	private Integer schedoneId;
	private Integer numeroTotaleImmaginiPdf;
	private Long dimMediaImmaginiPdf;
	private Long dimTotaleImmaginiPdf;
	private String compressionePdf;
	private Formato formatoMediaImmaginiPdf;
	private Formato formatoTotaleImmaginiPdf;
	
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
	 * @return the numeroTotaleImmaginiPdf
	 */
	public Integer getNumeroTotaleImmaginiPdf() {
		return numeroTotaleImmaginiPdf;
	}
	/**
	 * @param numeroTotaleImmaginiPdf the numeroTotaleImmaginiPdf to set
	 */
	public void setNumeroTotaleImmaginiPdf(Integer numeroTotaleImmaginiPdf) {
		this.numeroTotaleImmaginiPdf = numeroTotaleImmaginiPdf;
	}
	/**
	 * @return the dimMediaImmaginiPdf
	 */
	public Long getDimMediaImmaginiPdf() {
		return dimMediaImmaginiPdf;
	}
	/**
	 * @param dimMediaImmaginiPdf the dimMediaImmaginiPdf to set
	 */
	public void setDimMediaImmaginiPdf(Long dimMediaImmaginiPdf) {
		this.dimMediaImmaginiPdf = dimMediaImmaginiPdf;
	}
	/**
	 * @return the dimTotaleImmaginiPdf
	 */
	public Long getDimTotaleImmaginiPdf() {
		return dimTotaleImmaginiPdf;
	}
	/**
	 * @param dimTotaleImmaginiPdf the dimTotaleImmaginiPdf to set
	 */
	public void setDimTotaleImmaginiPdf(Long dimTotaleImmaginiPdf) {
		this.dimTotaleImmaginiPdf = dimTotaleImmaginiPdf;
	}
	/**
	 * @return the compressionePdf
	 */
	public String getCompressionePdf() {
		return compressionePdf;
	}
	/**
	 * @param compressionePdf the compressionePdf to set
	 */
	public void setCompressionePdf(String compressionePdf) {
		this.compressionePdf = compressionePdf;
	}
	/**
	 * @return the formatoMediaImmaginiPdf
	 */
	public Formato getFormatoMediaImmaginiPdf() {
		return formatoMediaImmaginiPdf;
	}
	/**
	 * @param formatoMediaImmaginiPdf the formatoMediaImmaginiPdf to set
	 */
	public void setFormatoMediaImmaginiPdf(Formato formatoMediaImmaginiPdf) {
		this.formatoMediaImmaginiPdf = formatoMediaImmaginiPdf;
	}
	/**
	 * @return the formatoTotaleImmaginiPdf
	 */
	public Formato getFormatoTotaleImmaginiPdf() {
		return formatoTotaleImmaginiPdf;
	}
	/**
	 * @param formatoTotaleImmaginiPdf the formatoTotaleImmaginiPdf to set
	 */
	public void setFormatoTotaleImmaginiPdf(Formato formatoTotaleImmaginiPdf) {
		this.formatoTotaleImmaginiPdf = formatoTotaleImmaginiPdf;
	}

}
