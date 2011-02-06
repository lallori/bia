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
package org.medici.docsources.command.volbase;

import javax.validation.constraints.Size;

/**
 * Command bean for action "Request Show Volume".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.docsources.controller.volbase.EditDetailsVolumeController
 */
public class ShowExplorerVolumeRequestCommand {
	private Integer summaryId;
	private Integer volNum;
	@Size (max=1)
	private String volLetExt;
	private String page;
	private Integer firstRecord;
	private Long total;
	private Long totalFolio;
	private Long totalRubricario;
	private Boolean flashVersion;
	private Boolean modalWindow;
	// This parameter is used to identify first request on modalWindow, so we can set style in div osx-modal-data
	private Boolean firstPage;

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
	 * @return the volLeText
	 */
	public String getVolLetExt() {
		return volLetExt;
	}
	
	/**	
	 * @param volLeText the volLetExt to set
	 */
	public void setVolLeText(String volLetExt) {
		this.volLetExt = volLetExt;
	}
	
	/**
	 * @param flashVersion the flashVersion to set
	 */
	public void setFlashVersion(Boolean flashVersion) {
		this.flashVersion = flashVersion;
	}
	
	/**
	 * @return the flashVersion
	 */
	public Boolean getFlashVersion() {
		return flashVersion;
	}
	
	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}
	
	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}
	
	/**
	 * @param firstRecord the firstRecord to set
	 */
	public void setFirstRecord(Integer firstRecord) {
		this.firstRecord = firstRecord;
	}
	
	/**
	 * @return the firstRecord
	 */
	public Integer getFirstRecord() {
		return firstRecord;
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
	 * @param totalFolio the totalFolio to set
	 */
	public void setTotalFolio(Long totalFolio) {
		this.totalFolio = totalFolio;
	}
	/**
	 * @return the totalFolio
	 */
	public Long getTotalFolio() {
		return totalFolio;
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
	 * @param modalWindow the modalWindow to set
	 */
	public void setModalWindow(Boolean modalWindow) {
		this.modalWindow = modalWindow;
	}
	
	/**
	 * @return the modalWindow
	 */
	public Boolean getModalWindow() {
		return modalWindow;
	}
	/**
	 * @param firstPage the firstPage to set
	 */
	public void setFirstPage(Boolean firstPage) {
		this.firstPage = firstPage;
	}
	/**
	 * @return the firstPage
	 */
	public Boolean getFirstPage() {
		return firstPage;
	}
}
