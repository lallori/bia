/*
 * Document.java
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
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.solr.analysis.ISOLatin1AccentFilterFactory;
import org.apache.solr.analysis.MappingCharFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.bridge.builtin.BooleanBridge;

/**
 * Document entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDef(name="documentAnalyzer",
		  charFilters = {
		    @CharFilterDef(factory = MappingCharFilterFactory.class, params = {
		      @Parameter(name = "mapping", value = "org/medici/docsources/mapping-chars.properties")
		    })
		  },
		  tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
		  filters = {
		    @TokenFilterDef(factory = ISOLatin1AccentFilterFactory.class)
		    })
@Audited
@Table ( name = "\"tblDocuments\"" ) 
public class Document implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 135030362618173811L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"ENTRYID\"", length=10, nullable=false)
	@DocumentId
	private Integer entryId;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="\"SUMMARYID\"")
	private Volume volume;
	
	@Column (name="\"SUBVOL\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String subVol;
	
	@Column (name="\"RESID\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String researcher;
	
	@Column (name="\"DATECREATED\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateCreated;

	@Column (name="\"LASTUPDATE\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date lastUpdate;
	
	@Column (name="\"DOCTOBEVETTED\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean docTobeVetted;
	
	@Column (name="\"DOCTOBEVETTEDDATE\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date docToBeVettedDate;
	
	@Column (name="\"DOCVETID\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String docVetId;
	
	@Column (name="\"DOCVETBEGINS\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date docVetBegins;
	
	@Column (name="\"DOCVETTED\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Boolean docVetted;
	
	@Column (name="\"DOCVETTEDDATE\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date docVettedDate;
	
	@Column (name="\"DOCSTATBOX\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String docStatBox;
	
	@Column (name="\"NEWENTRY\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean newEntry;
	
	@Column (name="\"INSERTNUM\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String insertNum;
	
	@Column (name="\"INSERTLET\"", length=15)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String insertLet;
	
	@Column (name="\"FOLIONUM\"", length=10)
	private Integer folioNum;
	
	@Column (name="\"FOLIOMOD\"", length=15)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String folioMod;
	
	@Column (name="\"CONTDISC\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean contDisc;
	
	@Column (name="\"UNPAGED\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean unpaged;
	
	@Column (name="\"DOCDAY\"", length=10)
	private Integer docDay;
	
	@Column (name="\"DOCMONTHNUM\"", length=10)
	private Integer docMonthNum;
	
	@Column (name="\"DOCYEAR\"", length=10)
	private Integer docYear;
	
	@Column (name="\"SORTABLEDATE\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String sortableDate;
	
	@Column (name="\"YEARMODERN\"", length=15, precision=5)
	private Integer yearModern;
	
	@Column (name="\"RECKONING\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean reckoning;
	
	@Column (name="\"UNDATED\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean undated;
	
	@Column (name="\"DATEUNS\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean dateUns;
	
	@Column (name="\"DATEAPPROX\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String dateApprox;
	
	@Column (name="\"DATENOTES\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String dateNotes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SENDID")
	private People senderPeople;
	
	@Column (name="\"SENDUNS\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean senderPeopleUnsure;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"SENDLOCPLALL\"")
	private Place senderPlace;
	
	@Column (name="\"SENDLOCUNS\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean senderPlaceUnsure;
	
	@Column (name="\"SENDNOTES\"", length=250)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String sendNotes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"RECIPID\"")
	private People recipientPeople;
	
	@Column (name="\"RECIPUNS\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean recipientPeopleUnsure;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"RECIPLOCPLALL\"")
	private Place recipientPlace;
	
	@Column (name="\"RECIPLOCUNS\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean recipientPlaceUnsure;
	
	@Column (name="\"RECIPNOTES\"", length=250)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String recipNotes;
	
	@Column (name="\"GRAPHIC\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean graphic;

	//Linked fact Check
	@OneToOne(fetch=FetchType.LAZY,mappedBy="document")
	@JoinColumn(name="ENTRYID", referencedColumnName = "ENTRYID")
	//@IndexedEmbedded
	private FactChecks factChecks;

	//Association topic-place 
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")
	//@IndexedEmbedded
	private Set<EplToLink> eplToLink;

	//Association people
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "document")
	//@IndexedEmbedded
	private Set<EpLink> epLink;
	
	//Association people
	@OneToOne(fetch=FetchType.LAZY,mappedBy="document")
	@JoinColumn(name="ENTRYID", referencedColumnName = "ENTRYID")
	//@IndexedEmbedded
	private SynExtract synExtract;

	/**
	 * Default constructor.
	 */
	public Document() {
		super();
	}

	/**
	 * 
	 * @param entryId
	 */
	public Document(Integer entryId) {
		setEntryId(entryId);
	}

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
	public Volume getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(Volume volume) {
		this.volume = volume;
	}
	/**
	 * @return the subVol
	 */
	public String getSubVol() {
		return subVol;
	}
	/**
	 * @param subVol the subVol to set
	 */
	public void setSubVol(String subVol) {
		this.subVol = subVol;
	}
	/**
	 * @return the researcher
	 */
	public String getResearcher() {
		return researcher;
	}
	/**
	 * @param researcher the researcher to set
	 */
	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}
	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}
	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	/**
	 * @return the docTobeVetted
	 */
	public Boolean getDocTobeVetted() {
		return docTobeVetted;
	}
	/**
	 * @param docTobeVetted the docTobeVetted to set
	 */
	public void setDocTobeVetted(Boolean docTobeVetted) {
		this.docTobeVetted = docTobeVetted;
	}
	/**
	 * @return the docToBeVettedDate
	 */
	public Date getDocToBeVettedDate() {
		return docToBeVettedDate;
	}
	/**
	 * @param docToBeVettedDate the docToBeVettedDate to set
	 */
	public void setDocToBeVettedDate(Date docToBeVettedDate) {
		this.docToBeVettedDate = docToBeVettedDate;
	}
	/**
	 * @return the docVetId
	 */
	public String getDocVetId() {
		return docVetId;
	}
	/**
	 * @param docVetId the docVetId to set
	 */
	public void setDocVetId(String docVetId) {
		this.docVetId = docVetId;
	}
	/**
	 * @return the docVetBegins
	 */
	public Date getDocVetBegins() {
		return docVetBegins;
	}
	/**
	 * @param docVetBegins the docVetBegins to set
	 */
	public void setDocVetBegins(Date docVetBegins) {
		this.docVetBegins = docVetBegins;
	}
	/**
	 * @return the docVetted
	 */
	public Boolean getDocVetted() {
		return docVetted;
	}
	/**
	 * @param docVetted the docVetted to set
	 */
	public void setDocVetted(Boolean docVetted) {
		this.docVetted = docVetted;
	}
	
	/**
	 * @return the docVettedDate
	 */
	public Date getDocVettedDate() {
		return docVettedDate;
	}
	
	/**
	 * @param docVettedDate the docVettedDate to set
	 */
	public void setDocVettedDate(Date docVettedDate) {
		this.docVettedDate = docVettedDate;
	}
	
	/**
	 * @return the docStatBox
	 */
	public String getDocStatBox() {
		return docStatBox;
	}
	
	/**
	 * @param docStatBox the docStatBox to set
	 */
	public void setDocStatBox(String docStatBox) {
		this.docStatBox = docStatBox;
	}
	
	/**
	 * @return the newEntry
	 */
	public Boolean getNewEntry() {
		return newEntry;
	}
	
	/**
	 * @param newEntry the newEntry to set
	 */
	public void setNewEntry(Boolean newEntry) {
		this.newEntry = newEntry;
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
	 * @return the sortableDate
	 */
	public String getSortableDate() {
		return sortableDate;
	}
	
	/**
	 * @param sortableDate the sortableDate to set
	 */
	public void setSortableDate(String sortableDate) {
		this.sortableDate = sortableDate;
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
	 * @return the reckoning
	 */
	public Boolean getReckoning() {
		return reckoning;
	}
	
	/**
	 * @param reckoning the reckoning to set
	 */
	public void setReckoning(Boolean reckoning) {
		this.reckoning = reckoning;
	}
	
	/**
	 * @return the undated
	 */
	public Boolean getUndated() {
		return undated;
	}
	
	/**
	 * @param undated the undated to set
	 */
	public void setUndated(Boolean undated) {
		this.undated = undated;
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
	 * @return the dateApprox
	 */
	public String getDateApprox() {
		return dateApprox;
	}
	
	/**
	 * @param dateApprox the dateApprox to set
	 */
	public void setDateApprox(String dateApprox) {
		this.dateApprox = dateApprox;
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
	 * @return the senderPeople
	 */
	public People getSenderPeople() {
		return senderPeople;
	}
	
	/**
	 * @param senderPeople the senderPeople to set
	 */
	public void setSenderPeople(People senderPeople) {
		this.senderPeople = senderPeople;
	}
	/**
	 * @return the senderPeopleUnsure
	 */
	public Boolean getSenderPeopleUnsure() {
		return senderPeopleUnsure;
	}
	/**
	 * @param senderPeopleUnsure the senderPeopleUnsure to set
	 */
	public void setSenderPeopleUnsure(Boolean senderPeopleUnsure) {
		this.senderPeopleUnsure = senderPeopleUnsure;
	}
	/**
	 * @return the senderPlace
	 */
	public Place getSenderPlace() {
		return senderPlace;
	}
	
	/**
	 * @param senderPlace the senderPlace to set
	 */
	public void setSenderPlace(Place senderPlace) {
		this.senderPlace = senderPlace;
	}

	/**
	 * @return the senderPlaceUnsure
	 */
	public Boolean getSenderPlaceUnsure() {
		return senderPlaceUnsure;
	}
	
	/**
	 * @param senderPlaceUnsure the senderPlaceUnsure to set
	 */
	public void setSenderPlaceUnsure(Boolean senderPlaceUnsure) {
		this.senderPlaceUnsure = senderPlaceUnsure;
	}
	
	/**
	 * @return the sendNotes
	 */
	public String getSendNotes() {
		return sendNotes;
	}
	
	/**
	 * @param sendNotes the sendNotes to set
	 */
	public void setSendNotes(String sendNotes) {
		this.sendNotes = sendNotes;
	}
	
	/**
	 * @return the recipientPeople
	 */
	public People getRecipientPeople() {
		return recipientPeople;
	}
	
	/**
	 * @param recipientPeople the recipientPeople to set
	 */
	public void setRecipientPeople(People recipientPeople) {
		this.recipientPeople = recipientPeople;
	}
	
	/**
	 * @return the recipientPeopleUnsure
	 */
	public Boolean getRecipientPeopleUnsure() {
		return recipientPeopleUnsure;
	}
	
	/**
	 * @param recipientPeopleUnsure the recipientPeopleUnsure to set
	 */
	public void setRecipientPeopleUnsure(Boolean recipientPeopleUnsure) {
		this.recipientPeopleUnsure = recipientPeopleUnsure;
	}

	/**
	 * @return the recipientPlace
	 */
	public Place getRecipientPlace() {
		return recipientPlace;
	}
	
	/**
	 * @param recipientPlace the recipientPlace to set
	 */
	public void setRecipientPlace(Place recipientPlace) {
		this.recipientPlace = recipientPlace;
	}
	
	/**
	 * @return the recipientPlaceUnsure
	 */
	public Boolean getRecipientPlaceUnsure() {
		return recipientPlaceUnsure;
	}
	
	/**
	 * @param recipientPlaceUnsure the recipientPlaceUnsure to set
	 */
	public void setRecipientPlaceUnsure(Boolean recipientPlaceUnsure) {
		this.recipientPlaceUnsure = recipientPlaceUnsure;
	}
	
	/**
	 * @return the recipNotes
	 */
	public String getRecipNotes() {
		return recipNotes;
	}
	
	/**
	 * @param recipNotes the recipNotes to set
	 */
	public void setRecipNotes(String recipNotes) {
		this.recipNotes = recipNotes;
	}
	
	/**
	 * @return the graphic
	 */
	public Boolean getGraphic() {
		return graphic;
	}
	
	/**
	 * @param graphic the graphic to set
	 */
	public void setGraphic(Boolean graphic) {
		this.graphic = graphic;
	}

	/**
	 * @param factChecks the factChecks to set
	 */
	public void setFactChecks(FactChecks factChecks) {
		this.factChecks = factChecks;
	}

	/**
	 * @return the factChecks
	 */
	public FactChecks getFactChecks() {
		return factChecks;
	}

	/**
	 * @param eplToLink the eplToLink to set
	 */
	public void setEplToLink(Set<EplToLink> eplToLink) {
		this.eplToLink = eplToLink;
	}

	/**
	 * @return the eplToLink
	 */
	public Set<EplToLink> getEplToLink() {
		return eplToLink;
	}

	@Override
	public String toString() {
		if (getEntryId() == null)
			return "";

		return getEntryId().toString();
	}

	/**
	 * @param epLink the epLink to set
	 */
	public void setEpLink(Set<EpLink> epLink) {
		this.epLink = epLink;
	}

	/**
	 * @return the eplLink
	 */
	public Set<EpLink> getEpLink() {
		return epLink;
	}

	/**
	 * @param synExtract the synExtract to set
	 */
	public void setSynExtract(SynExtract synExtract) {
		this.synExtract = synExtract;
	}

	/**
	 * @return the synExtract
	 */
	public SynExtract getSynExtract() {
		return synExtract;
	}
}
