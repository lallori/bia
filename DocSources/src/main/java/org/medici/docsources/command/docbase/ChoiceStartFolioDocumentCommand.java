/*
 * ShowExplorerVolumeRequestCommand.java
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
package org.medici.docsources.command.docbase;

import javax.validation.constraints.Size;

import org.medici.docsources.domain.Image.ImageType;

/**
 * Command bean for action "Choice Start Folio Document Creation".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.docsources.controller.volbase.EditDetailsVolumeController
 */
public class ChoiceStartFolioDocumentCommand {
	private Integer summaryId;
	private Integer volNum;
	@Size (max=1)
	private String volLetExt;
	/** This parameter is used to manage rubricario and folio forms */
	private Integer imageOrder;
	/** This parameter is used to manage image type (Rubricario, Carta...) */
	private ImageType imageType;
	/** This parameter is used to folio identifier ) */
	private Integer imageProgTypeNum;
	private Long total;
	/** This parameter is used to count Rubricario total */ 
	private Long totalRubricario;
	/** This parameter is used to count Carta total */
	private Long totalCarta;
	/** This parameter is used to count Other total */
	private Long totalOther;
	/** This parameter is used to count Guardia total */
	private Long totalGuardia;
	/** This parameter is used to count appendix total */
	private Long totalAppendix;
	/** This parameter is used to select flash version Volume Explorer */
	private Boolean flashVersion;
	/** This parameter is used to identify imageDocumentToCreate */
	private Long imageDocumentToCreate;
	
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
	 * @return the imageOrder
	 */
	public Integer getImageOrder() {
		return imageOrder;
	}
	/**
	 * @param imageOrder the imageOrder to set
	 */
	public void setImageOrder(Integer imageOrder) {
		this.imageOrder = imageOrder;
	}
	/**
	 * @return the imageType
	 */
	public ImageType getImageType() {
		return imageType;
	}
	/**
	 * @param imageType the imageType to set
	 */
	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}
	/**
	 * @return the imageProgTypeNum
	 */
	public Integer getImageProgTypeNum() {
		return imageProgTypeNum;
	}
	/**
	 * @param imageProgTypeNum the imageProgTypeNum to set
	 */
	public void setImageProgTypeNum(Integer imageProgTypeNum) {
		this.imageProgTypeNum = imageProgTypeNum;
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
	 * @return the flashVersion
	 */
	public Boolean getFlashVersion() {
		return flashVersion;
	}
	/**
	 * @param flashVersion the flashVersion to set
	 */
	public void setFlashVersion(Boolean flashVersion) {
		this.flashVersion = flashVersion;
	}
	/**
	 * @return the imageDocumentToCreate
	 */
	public Long getImageDocumentToCreate() {
		return imageDocumentToCreate;
	}
	/**
	 * @param imageDocumentToCreate the imageDocumentToCreate to set
	 */
	public void setImageDocumentToCreate(Long imageDocumentToCreate) {
		this.imageDocumentToCreate = imageDocumentToCreate;
	}

}
