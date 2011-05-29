/*
 * VolumeSummary.java
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
package org.medici.docsources.common.volume;

import java.util.List;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class VolumeSummary {
	//Volume Id
	private Integer summaryId;
	//Vol Number
	private Integer volNum;
	//Vol Letter Extension
	private String volLetExt;
	// Description of carteggio
	private String carteggio;
	// Total volume pages
	private Long total;
	// Total rubricario
	private Long totalRubricario;
	// Total carta
	private Long totalCarta;
	// Total other
	private Long totalOther;
	// Total guardia
	private Long totalGuardia;
	// Total guardia
	private Long totalAppendix;	
	// Total number of missing Folios
	private Integer missingFolios;
	// List of missed Numbering Folios
	private List<Integer> missedNumberingFolios;
	// Description of cartulazione
	private String cartulazione;
	// Note to cartulazione
	private String noteCartulazione;
	// Volume size (
	private Integer volumeSize;
	// Width expressed in millimeters
	private Integer width;
	// Height expressed in millimeters
	private Integer height;
	
	/**
	 * @return the summaryId
	 */
	public Integer getSummaryId() {
		return summaryId;
	}
	
	/**
	 * @param summaryId the summaryId to set
	 */
	public void setSummaryId(Integer summaryId) {
		this.summaryId = summaryId;
	}
	
	/**
	 * @return the volNum
	 */
	public Integer getVolNum() {
		return volNum;
	}
	
	/**
	 * @param volNum the volNum to set
	 */
	public void setVolNum(Integer volNum) {
		this.volNum = volNum;
	}
	
	/**
	 * @return the volLetExt
	 */
	public String getVolLetExt() {
		return volLetExt;
	}
	
	/**
	 * @param volLetExt the volLetExt to set
	 */
	public void setVolLetExt(String volLetExt) {
		this.volLetExt = volLetExt;
	}
	
	/**
	 * @return the carteggio
	 */
	public String getCarteggio() {
		return carteggio;
	}
	
	/**
	 * @param carteggio the carteggio to set
	 */
	public void setCarteggio(String carteggio) {
		this.carteggio = carteggio;
	}
	
	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * @return the totalRubricario
	 */
	public Long getTotalRubricario() {
		return totalRubricario;
	}

	/**
	 * @param totalRubricario the totalRubricario to set
	 */
	public void setTotalRubricario(Long totalRubricario) {
		this.totalRubricario = totalRubricario;
	}

	/**
	 * @return the totalCarta
	 */
	public Long getTotalCarta() {
		return totalCarta;
	}

	/**
	 * @param totalCarta the totalCarta to set
	 */
	public void setTotalCarta(Long totalCarta) {
		this.totalCarta = totalCarta;
	}

	/**
	 * @return the totalOther
	 */
	public Long getTotalOther() {
		return totalOther;
	}

	/**
	 * @param totalOther the totalOther to set
	 */
	public void setTotalOther(Long totalOther) {
		this.totalOther = totalOther;
	}

	/**
	 * @return the totalGuardia
	 */
	public Long getTotalGuardia() {
		return totalGuardia;
	}

	/**
	 * @param totalGuardia the totalGuardia to set
	 */
	public void setTotalGuardia(Long totalGuardia) {
		this.totalGuardia = totalGuardia;
	}

	/**
	 * @return the totalAppendix
	 */
	public Long getTotalAppendix() {
		return totalAppendix;
	}

	/**
	 * @param totalAppendix the totalAppendix to set
	 */
	public void setTotalAppendix(Long totalAppendix) {
		this.totalAppendix = totalAppendix;
	}

	/**
	 * @return the missingFolios
	 */
	public Integer getMissingFolios() {
		return missingFolios;
	}
	
	/**
	 * @param missingFolios the missingFolios to set
	 */
	public void setMissingFolios(Integer missingFolios) {
		this.missingFolios = missingFolios;
	}
	
	/**
	 * @return the missedNumberingFolios
	 */
	public List<Integer> getMissedNumberingFolios() {
		return missedNumberingFolios;
	}
	
	/**
	 * @param missedNumberingFolios the missedNumberingFolios to set
	 */
	public void setMissedNumberingFolios(List<Integer> missedNumberingFolios) {
		this.missedNumberingFolios = missedNumberingFolios;
	}
	
	/**
	 * @return the cartulazione
	 */
	public String getCartulazione() {
		return cartulazione;
	}
	
	/**
	 * @param cartulazione the cartulazione to set
	 */
	public void setCartulazione(String cartulazione) {
		this.cartulazione = cartulazione;
	}
	
	/**
	 * @return the noteCartulazione
	 */
	public String getNoteCartulazione() {
		return noteCartulazione;
	}
	
	/**
	 * @param noteCartulazione the noteCartulazione to set
	 */
	public void setNoteCartulazione(String noteCartulazione) {
		this.noteCartulazione = noteCartulazione;
	}
	
	/**
	 * @return the volumeSize
	 */
	public Integer getVolumeSize() {
		return volumeSize;
	}
	
	/**
	 * @param volumeSize the volumeSize to set
	 */
	public void setVolumeSize(Integer volumeSize) {
		this.volumeSize = volumeSize;
	}
	
	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}
	
	/**
	 * @param height the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
	}
}