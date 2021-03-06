/*
 * EditDetailsDocumentCommand.java
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
package org.medici.bia.command.docbase;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * Command bean for action "Edit Details Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.bia.controller.docbase.EditDetailsDocumentController
 */
public class EditDetailsDocumentCommand {
	// Document Id
	private Integer entryId;
	// researcher description
	private String researcher;
	// date created
	@DateTimeFormat(pattern="MM/dd/yyyy hh:mm:ss")
	private Date dateCreated;
	//Volume reference
	private String volume;
	//Insert
	private String insertNum;
	//Part
	private String insertLet;
	//Folio Start 
	private Integer folioNum;
	private String folioMod;
	private String folioRectoVerso;
	//Transcribe Folio
	private Integer transcribeFolioNum;
	private String transcribeFolioMod;
	private String transcribeFolioRectoVerso;
	//Unpaginated
	private Boolean unpaged;
	//Disc. Cont'd
	private Boolean contDisc;
	//Document Tipology
	private String docTypology;
	//Date : year
	private Integer docYear;
	//Date : month num
	private Integer docMonthNum;
	//Date : day
	private Integer docDay;
	//Modern Date: (year Modern???) 
	private Integer yearModern;
	// Date Uncertain or Approximate? 
	private Boolean dateUns;
	// Undated 
	private Boolean dateUndated;
	//Date notes
	private String dateNotes;

	/**
	 * @return the entryId
	 */
	public Integer getEntryId() {
		return entryId;
	}
	/**
	 * @param entryId the entryId to set
	 */
	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}
	/**
	 * @return the volume
	 */
	public String getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}
	/**
	 * @return the insertNum
	 */
	public String getInsertNum() {
		return insertNum;
	}
	/**
	 * @param insertNum the insertNum to set
	 */
	public void setInsertNum(String insertNum) {
		this.insertNum = insertNum;
	}
	/**
	 * @return the insertLet
	 */
	public String getInsertLet() {
		return insertLet;
	}
	/**
	 * @param insertLet the insertLet to set
	 */
	public void setInsertLet(String insertLet) {
		this.insertLet = insertLet;
	}
	/**
	 * @return the folioNum
	 */
	public Integer getFolioNum() {
		return folioNum;
	}
	/**
	 * @param folioNum the folioNum to set
	 */
	public void setFolioNum(Integer folioNum) {
		this.folioNum = folioNum;
	}
	/**
	 * @return the folioMod
	 */
	public String getFolioMod() {
		return folioMod;
	}
	/**
	 * @param folioMod the folioMod to set
	 */
	public void setFolioMod(String folioMod) {
		this.folioMod = folioMod;
	}
	/**
	 * @return the folioRectoVerso
	 */
	public String getFolioRectoVerso() {
		return folioRectoVerso;
	}
	/**
	 * @param folioRectoVerso the folioRectoVerso to set
	 */
	public void setFolioRectoVerso(String folioRectoVerso) {
		this.folioRectoVerso = folioRectoVerso;
	}
	/**
	 * @return the transcribeFolioNum
	 */
	public Integer getTranscribeFolioNum() {
		return transcribeFolioNum;
	}
	/**
	 * @param transcribeFolioNum the transcribeFolioNum to set
	 */
	public void setTranscribeFolioNum(Integer transcribeFolioNum) {
		this.transcribeFolioNum = transcribeFolioNum;
	}
	/**
	 * @return the transcribeFolioMod
	 */
	public String getTranscribeFolioMod() {
		return transcribeFolioMod;
	}
	/**
	 * @param transcribeFolioMod the transcribeFolioMod to set
	 */
	public void setTranscribeFolioMod(String transcribeFolioMod) {
		this.transcribeFolioMod = transcribeFolioMod;
	}
	/**
	 * @return the transcribeFolioRectoVerso
	 */
	public String getTranscribeFolioRectoVerso() {
		return transcribeFolioRectoVerso;
	}
	/**
	 * @param transcribeFolioRectoVerso the transcribeFolioRectoVerso to set
	 */
	public void setTranscribeFolioRectoVerso(String transcribeFolioRectoVerso) {
		this.transcribeFolioRectoVerso = transcribeFolioRectoVerso;
	}
	/**
	 * @return the unpaged
	 */
	public Boolean getUnpaged() {
		return unpaged;
	}
	
	/**
	 * @param unpaged the unpaged to set
	 */
	public void setUnpaged(Boolean unpaged) {
		this.unpaged = unpaged;
	}
	
	/**
	 * @return the contDisc
	 */
	public Boolean getContDisc() {
		return contDisc;
	}
	/**
	 * @param contDisc the contDisc to set
	 */
	public void setContDisc(Boolean contDisc) {
		this.contDisc = contDisc;
	}
	/**
	 * @return the docYear
	 */
	public Integer getDocYear() {
		return docYear;
	}
	/**
	 * @param docYear the docYear to set
	 */
	public void setDocYear(Integer docYear) {
		this.docYear = docYear;
	}
	/**
	 * @return the docMonthNum
	 */
	public Integer getDocMonthNum() {
		return docMonthNum;
	}
	/**
	 * @param docMonthNum the docMonthNum to set
	 */
	public void setDocMonthNum(Integer docMonthNum) {
		this.docMonthNum = docMonthNum;
	}
	/**
	 * @return the docDay
	 */
	public Integer getDocDay() {
		return docDay;
	}
	/**
	 * @param docDay the docDay to set
	 */
	public void setDocDay(Integer docDay) {
		this.docDay = docDay;
	}
	/**
	 * @return the yearModern
	 */
	public Integer getYearModern() {
		return yearModern;
	}
	/**
	 * @param yearModern the yearModern to set
	 */
	public void setYearModern(Integer yearModern) {
		this.yearModern = yearModern;
	}

	/**
	 * @return the dateUns
	 */
	public Boolean getDateUns() {
		return dateUns;
	}

	/**
	 * @param dateUns the dateUns to set
	 */
	public void setDateUns(Boolean dateUns) {
		this.dateUns = dateUns;
	}

	/**
	 * @return the dateUndated
	 */
	public Boolean getDateUndated() {
		return dateUndated;
	}

	/**
	 * @param dateUndated the dateUndated to set
	 */
	public void setDateUndated(Boolean dateUndated) {
		this.dateUndated = dateUndated;
	}

	/**
	 * @return the dateNotes
	 */
	public String getDateNotes() {
		return dateNotes;
	}

	/**
	 * @param dateNotes the dateNotes to set
	 */
	public void setDateNotes(String dateNotes) {
		this.dateNotes = dateNotes;
	}

	/**
	 * @param researcher the researcher to set
	 */
	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}

	/**
	 * @return the researcher
	 */
	public String getResearcher() {
		return researcher;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	/**
	 * @param docTypology the docTypology to set
	 */
	public void setDocTypology(String docTypology) {
		this.docTypology = docTypology;
	}
	/**
	 * @return the docTypology
	 */
	public String getDocTypology() {
		return docTypology;
	}
}
