/*
 * FoliosInformations.java
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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class FoliosInformations {
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
	private Integer totalMissingFolios;
	// List of missed Numbering Folios
	private List<Integer> missingNumberingFolios;
	// List of missed Numbering Folios
	private List<String> misnumberedFolios;

	public FoliosInformations() {
		setMissingNumberingFolios(new ArrayList<Integer>(0));
		setMisnumberedFolios(new ArrayList<String>(0));
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @param totalRubricario the totalRubricario to set
	 */
	public void setTotalRubricario(Long totalRubricario) {
		this.totalRubricario = totalRubricario;
	}

	/**
	 * @return the totalRubricario
	 */
	public Long getTotalRubricario() {
		return totalRubricario;
	}

	/**
	 * @param totalCarta the totalCarta to set
	 */
	public void setTotalCarta(Long totalCarta) {
		this.totalCarta = totalCarta;
	}

	/**
	 * @return the totalCarta
	 */
	public Long getTotalCarta() {
		return totalCarta;
	}

	/**
	 * @param totalOther the totalOther to set
	 */
	public void setTotalOther(Long totalOther) {
		this.totalOther = totalOther;
	}

	/**
	 * @return the totalOther
	 */
	public Long getTotalOther() {
		return totalOther;
	}

	/**
	 * @param totalGuardia the totalGuardia to set
	 */
	public void setTotalGuardia(Long totalGuardia) {
		this.totalGuardia = totalGuardia;
	}

	/**
	 * @return the totalGuardia
	 */
	public Long getTotalGuardia() {
		return totalGuardia;
	}

	/**
	 * @param totalAppendix the totalAppendix to set
	 */
	public void setTotalAppendix(Long totalAppendix) {
		this.totalAppendix = totalAppendix;
	}

	/**
	 * @return the totalAppendix
	 */
	public Long getTotalAppendix() {
		return totalAppendix;
	}

	/**
	 * @param totalMissingFolios the totalMissingFolios to set
	 */
	public void setTotalMissingFolios(Integer totalMissingFolios) {
		this.totalMissingFolios = totalMissingFolios;
	}

	/**
	 * @return the totalMissingFolios
	 */
	public Integer getTotalMissingFolios() {
		return totalMissingFolios;
	}

	/**
	 * @param misnumberedFolios the misnumberedFolios to set
	 */
	public void setMisnumberedFolios(List<String> misnumberedFolios) {
		this.misnumberedFolios = misnumberedFolios;
	}

	/**
	 * @return the misnumberedFolios
	 */
	public List<String> getMisnumberedFolios() {
		return misnumberedFolios;
	}

	/**
	 * @param missingNumberingFolios the missingNumberingFolios to set
	 */
	public void setMissingNumberingFolios(List<Integer> missingNumberingFolios) {
		this.missingNumberingFolios = missingNumberingFolios;
	}

	/**
	 * @return the missingNumberingFolios
	 */
	public List<Integer> getMissingNumberingFolios() {
		return missingNumberingFolios;
	}

}
