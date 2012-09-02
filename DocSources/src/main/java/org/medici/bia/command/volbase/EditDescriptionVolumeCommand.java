/*
 * EditDescriptionVolumeCommand.java
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
package org.medici.bia.command.volbase;

import javax.validation.constraints.NotNull;

/**
 * Command bean for action "Edit Description Volume".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.docsources.controller.volbase.EditDescriptionVolumeController
 */
public class EditDescriptionVolumeCommand {
	@NotNull
	private Integer summaryId;
	private String orgNotes;
	private String ccondition;
	private Boolean bound;
	private Boolean folsNumbrd;
	private String folioCount;
	private Boolean oldAlphaIndex;
	private Boolean printedMaterial;
	private Boolean printedDrawings;
	private Boolean italian;
	private Boolean spanish;
	private Boolean english;
	private Boolean latin;
	private Boolean german;
	private Boolean french;
	private String otherLang;
	private Boolean cipher;
	private String cipherNotes;

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
	 * @return the orgNotes
	 */
	public String getOrgNotes() {
		return orgNotes;
	}
	
	/**
	 * @param orgNotes the orgNotes to set
	 */
	public void setOrgNotes(String orgNotes) {
		this.orgNotes = orgNotes;
	}
	
	/**
	 * @return the ccondition
	 */
	public String getCcondition() {
		return ccondition;
	}
	
	/**
	 * @param ccondition the ccondition to set
	 */
	public void setCcondition(String ccondition) {
		this.ccondition = ccondition;
	}
	
	/**
	 * @return the bound
	 */
	public Boolean getBound() {
		return bound;
	}
	
	/**
	 * @param bound the bound to set
	 */
	public void setBound(Boolean bound) {
		this.bound = bound;
	}
	
	/**
	 * @return the folsNumbrd
	 */
	public Boolean getFolsNumbrd() {
		return folsNumbrd;
	}
	
	/**
	 * @param folsNumbrd the folsNumbrd to set
	 */
	public void setFolsNumbrd(Boolean folsNumbrd) {
		this.folsNumbrd = folsNumbrd;
	}
	
	/**
	 * @param folioCount the folioCount to set
	 */
	public void setFolioCount(String folioCount) {
		this.folioCount = folioCount;
	}

	/**
	 * @return the folioCount
	 */
	public String getFolioCount() {
		return folioCount;
	}
	
	/**
	 * @return the oldAlphaIndex
	 */
	public Boolean getOldAlphaIndex() {
		return oldAlphaIndex;
	}
	
	/**
	 * @param oldAlphaIndex the oldAlphaIndex to set
	 */
	public void setOldAlphaIndex(Boolean oldAlphaIndex) {
		this.oldAlphaIndex = oldAlphaIndex;
	}
	
	/**
	 * @return the italian
	 */
	public Boolean getItalian() {
		return italian;
	}
	
	/**
	 * @param italian the italian to set
	 */
	public void setItalian(Boolean italian) {
		this.italian = italian;
	}
	
	/**
	 * @return the spanish
	 */
	public Boolean getSpanish() {
		return spanish;
	}
	
	/**
	 * @param spanish the spanish to set
	 */
	public void setSpanish(Boolean spanish) {
		this.spanish = spanish;
	}
	
	/**
	 * @return the english
	 */
	public Boolean getEnglish() {
		return english;
	}
	
	/**
	 * @param english the english to set
	 */
	public void setEnglish(Boolean english) {
		this.english = english;
	}
	
	/**
	 * @return the latin
	 */
	public Boolean getLatin() {
		return latin;
	}
	
	/**
	 * @param latin the latin to set
	 */
	public void setLatin(Boolean latin) {
		this.latin = latin;
	}
	
	/**
	 * @return the german
	 */
	public Boolean getGerman() {
		return german;
	}
	
	/**
	 * @param german the german to set
	 */
	public void setGerman(Boolean german) {
		this.german = german;
	}
	
	/**
	 * @return the french
	 */
	public Boolean getFrench() {
		return french;
	}
	
	/**
	 * @param french the french to set
	 */
	public void setFrench(Boolean french) {
		this.french = french;
	}
	
	/**
	 * @return the otherLang
	 */
	public String getOtherLang() {
		return otherLang;
	}
	
	/**
	 * @param otherLang the otherLang to set
	 */
	public void setOtherLang(String otherLang) {
		this.otherLang = otherLang;
	}
	
	/**
	 * @return the cipher
	 */
	public Boolean getCipher() {
		return cipher;
	}
	
	/**
	 * @param cipher the cipher to set
	 */
	public void setCipher(Boolean cipher) {
		this.cipher = cipher;
	}
	
	/**
	 * @return the cipherNotes
	 */
	public String getCipherNotes() {
		return cipherNotes;
	}
	
	/**
	 * @param cipherNotes the cipherNotes to set
	 */
	public void setCipherNotes(String cipherNotes) {
		this.cipherNotes = cipherNotes;
	}

	/**
	 * @param printedMaterial the printedMaterial to set
	 */
	public void setPrintedMaterial(Boolean printedMaterial) {
		this.printedMaterial = printedMaterial;
	}

	/**
	 * @return the printedMaterial
	 */
	public Boolean getPrintedMaterial() {
		return printedMaterial;
	}

	/**
	 * @param printedDrawings the printedDrawings to set
	 */
	public void setPrintedDrawings(Boolean printedDrawings) {
		this.printedDrawings = printedDrawings;
	}

	/**
	 * @return the printedDrawings
	 */
	public Boolean getPrintedDrawings() {
		return printedDrawings;
	}
}
