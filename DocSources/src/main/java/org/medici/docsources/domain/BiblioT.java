/*
 * BiblioT.java
 * 
 * Developed by Medici Archive Project (2010-2012).
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
package org.medici.docsources.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

/**
 * BiblioT entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Audited
@Table ( name = "\"tblBiblioT\"" ) 
public class BiblioT implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -984709582916010527L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"BiblioID\"", length=10, nullable=false)
	private Integer biblioId;
	@Column (name="\"AuthorEditor\"", length=255)
	private String authorEditor;
	@Column (name="\"Title\"", length=255)
	private String title;
	@Column (name="\"Type\"", length=255)
	private String type;
	@Column (name="\"Periodical\"", length=255)
	private String periodical;
	@Column (name="\"Publisher\"", length=255)
	private String publisher;
	@Column (name="\"Dates\"", length=255)
	private String dates;
	@Column (name="\"Notes\"", length=255)
	private String notes;
	@Column (name="\"ShelfNum\"", length=255)
	private String shelfNum;	

	/**
	 * @return the biblioId
	 */
	public Integer getBiblioId() {
		return biblioId;
	}
	/**
	 * @param biblioID the biblioId to set
	 */
	public void setBiblioId(Integer biblioId) {
		this.biblioId = biblioId;
	}
	/**
	 * @return the authorEditor
	 */
	public String getAuthorEditor() {
		return authorEditor;
	}
	/**
	 * @param authorEditor the authorEditor to set
	 */
	public void setAuthorEditor(String authorEditor) {
		this.authorEditor = authorEditor;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the periodical
	 */
	public String getPeriodical() {
		return periodical;
	}
	/**
	 * @param periodical the periodical to set
	 */
	public void setPeriodical(String periodical) {
		this.periodical = periodical;
	}
	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}
	/**
	 * @param publisher the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	/**
	 * @return the dates
	 */
	public String getDates() {
		return dates;
	}
	/**
	 * @param dates the dates to set
	 */
	public void setDates(String dates) {
		this.dates = dates;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * @return the shelfNum
	 */
	public String getShelfNum() {
		return shelfNum;
	}
	/**
	 * @param shelfNum the shelfNum to set
	 */
	public void setShelfNum(String shelfNum) {
		this.shelfNum = shelfNum;
	}
}
