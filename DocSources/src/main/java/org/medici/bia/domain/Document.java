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
package org.medici.bia.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.persistence.Transient;

import org.apache.solr.analysis.ASCIIFoldingFilterFactory;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.MappingCharFilterFactory;
import org.apache.solr.analysis.NGramFilterFactory;
import org.apache.solr.analysis.StandardFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.NumericFields;
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
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDefs({
	@AnalyzerDef(name="documentAnalyzer",
		charFilters = {
			@CharFilterDef(factory = MappingCharFilterFactory.class, params = {
				@Parameter(name = "mapping", value = "org/medici/bia/mapping-chars.properties")
			})
		},
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
		filters = {
			@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class)
	}),
	@AnalyzerDef(name = "documentNGram3Analyzer",
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class ),
		filters = {
			@TokenFilterDef(factory = StandardFilterFactory.class),
			@TokenFilterDef(factory = LowerCaseFilterFactory.class),
			@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
			@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
			@TokenFilterDef(factory = NGramFilterFactory.class,
				params = { 
					@Parameter(name = "minGramSize", value = "3"),
					@Parameter(name = "maxGramSize", value = "3")
				})
		}
	)
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

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"SUMMARYID\"")
	@IndexedEmbedded
	private Volume volume;
	
	@Column (name="\"SUBVOL\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String subVol;
	
	@Column (name="\"RESID\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String researcher;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"createdBy\"", nullable=true)
	private User createdBy;

	@Column (name="\"DATECREATED\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateCreated;

	@Column (name="\"LASTUPDATE\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date lastUpdate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"lastUpdateBy\"", nullable=true)
	private User lastUpdateBy;
	
	@Column (name="\"DOCSTATBOX\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String docStatBox;
	
	@Column (name="\"NEWENTRY\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean newEntry;
	
	@Column (name="\"INSERTNUM\"", length=50)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="insertNum_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String insertNum;
	
	@Column (name="\"INSERTLET\"", length=15)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="insertLet_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String insertLet;
	
	@Column (name="\"FOLIONUM\"", length=10)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="folioNum_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="folioNum"),
		@NumericField(forField="folioNum_Sort")
	})
	private Integer folioNum;
	
	@Enumerated(EnumType.STRING)
	@Column (name="\"FOLIORV\"", length=1, nullable=true)
	/*
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="folioNumRectoVerso_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="folioNumRectoVerso"),
		@NumericField(forField="folioNumRectoVerso_Sort")
	})*/
	private RectoVerso folioRectoVerso;
	
	@Column (name="\"FOLIOMOD\"", length=15)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="folioMod_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String folioMod;
	
	@Column (name="\"TRANSCRIBEFOLIONUM\"", length=10)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="transcribeFolioNum_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private Integer transcribeFolioNum;

	@Column (name="\"TRANSCRIBEFOLIOMOD\"", length=15)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="transcribeFolioMod_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String transcribeFolioMod;
	
	@Column (name="\"CONTDISC\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean contDisc;
	
	@Column (name="\"UNPAGED\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean unpaged;
	
	@Column (name="\"DOCYEAR\"", length=10)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="docYear_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="docYear"),
		@NumericField(forField="docYear_Sort")
	})
	private Integer docYear;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"DOCMONTHNUM\"", nullable=true)
	@IndexedEmbedded
	private Month docMonthNum;
	
	@Column (name="\"DOCDAY\"", length=10)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="docDay_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="docDay"),
		@NumericField(forField="docDay_Sort")
	})
	private Integer docDay;

	@Column (name="\"DOCDATE\"", length=10)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="docDate_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="docDate"),
		@NumericField(forField="docDate_Sort")
	})
	private Integer docDate;

	@Column (name="\"YEARMODERN\"", length=4)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="yearModern_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="yearModern"),
		@NumericField(forField="yearModern_Sort")
	})
	private Integer yearModern;
	
	@Column (name="\"SORTABLEDATE\"", length=10)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String sortableDate;
	
	@Column (name="\"SORTABLEDATEINT\"", length=10)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer sortableDateInt;
	
	@Column (name="\"RECKONING\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean reckoning;
	
	@Column (name="\"UNDATED\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean undated;
	
	@Column (name="\"DATEUNS\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean dateUns;
	
	@Column (name="\"DATEAPPROX\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String dateApprox;
	
	@Column (name="\"DATENOTES\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String dateNotes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SENDID")
	@IndexedEmbedded
	private People senderPeople;
	
	@Column (name="\"SENDUNS\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean senderPeopleUnsure;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"SENDLOCPLALL\"")
	@IndexedEmbedded
	private Place senderPlace;
	
	@Column (name="\"SENDLOCUNS\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean senderPlaceUnsure;
	
	@Column (name="\"SENDNOTES\"", length=250)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String sendNotes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"RECIPID\"")
	@IndexedEmbedded
	private People recipientPeople;
	
	@Column (name="\"RECIPUNS\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean recipientPeopleUnsure;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"RECIPLOCPLALL\"")
	@IndexedEmbedded
	private Place recipientPlace;
	
	@Column (name="\"RECIPLOCUNS\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean recipientPlaceUnsure;
	
	@Column (name="\"RECIPNOTES\"", length=250)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String recipNotes;
	
	@Column (name="\"GRAPHIC\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean graphic;

	@Column (name="\"DOCTYPOLOGY\"", length=250)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String docTypology;

	@Column (name="\"LOGICALDELETE\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean logicalDelete;

	//Linked fact Check
	@OneToOne(fetch=FetchType.LAZY,mappedBy="document", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name="ENTRYID", referencedColumnName = "ENTRYID")
	@IndexedEmbedded
	private FactChecks factChecks;

	//Association topic-place 
	@OneToMany(fetch=FetchType.LAZY, mappedBy="document", cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@IndexedEmbedded
	private Set<EplToLink> eplToLink;

	//Association people (attention recipient and sender are not here, they are defined up!)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "document", cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@IndexedEmbedded
	private Set<EpLink> epLink;

	//Association Synopsys and Extract
	@OneToOne(fetch=FetchType.LAZY,mappedBy="document")
	@JoinColumn(name="ENTRYID", referencedColumnName = "ENTRYID")
	@IndexedEmbedded
	private SynExtract synExtract;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "documentFrom", cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@IndexedEmbedded
	private Set<DocReference> docReference;

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
	 * @return the createdBy
	 */
	public User getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastUpdateBy
	 */
	public User getLastUpdateBy() {
		return lastUpdateBy;
	}

	/**
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(User lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
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
	 * @return the folioRectoVerso
	 */
	public RectoVerso getFolioRectoVerso() {
		return folioRectoVerso;
	}

	/**
	 * @param folioRectoVerso the folioRectoVerso to set
	 */
	public void setFolioRectoVerso(RectoVerso folioRectoVerso) {
		this.folioRectoVerso = folioRectoVerso;
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
	 * This method return volume MDP information and Folio information.
	 * It's a concatenation of volume number, volume letter extension, folio number and folio mod.
	 * It's a transient property(not stored on database ndr).
	 * 
	 * @return String rappresentation of volume and folio identifiers.
	 */
	@Transient
	public String getMDPAndFolio(){
		String returnValue = "";
		
		if(getVolume().getMDP() != null){
			returnValue += getVolume().getMDP();
		}
		if(getFolioNum() != null){
			returnValue += " / " + getFolioNum().toString();
			if(getFolioMod() != null){
				returnValue += " " + getFolioMod();
			}
		}
		else{
			returnValue += " / NNF";
		}
		
		return returnValue;
	}
	
	/**
	 * @param transcribeFolioNum the transcribeFolioNum to set
	 */
	public void setTranscribeFolioNum(Integer transcribeFolioNum) {
		this.transcribeFolioNum = transcribeFolioNum;
	}

	/**
	 * @return the transcribeFolioNum
	 */
	public Integer getTranscribeFolioNum() {
		return transcribeFolioNum;
	}

	/**
	 * @param transcribeFolioMod the transcribeFolioMod to set
	 */
	public void setTranscribeFolioMod(String transcribeFolioMod) {
		this.transcribeFolioMod = transcribeFolioMod;
	}

	/**
	 * @return the transcribeFolioMod
	 */
	public String getTranscribeFolioMod() {
		return transcribeFolioMod;
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
	public Month getDocMonthNum() {
		return docMonthNum;
	}
	
	/**
	 * @param docMonthNum the docMonthNum to set
	 */
	public void setDocMonthNum(Month docMonthNum) {
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
	 * @param docDate the docDate to set
	 */
	public void setDocDate(Integer docDate) {
		this.docDate = docDate;
	}

	/**
	 * @return the docDate
	 */
	public Integer getDocDate() {
		return docDate;
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
	 * @return the sortableDateInt
	 */
	public Integer getSortableDateInt() {
		return sortableDateInt;
	}

	/**
	 * @param sortableDateInt the sortableDateInt to set
	 */
	public void setSortableDateInt(Integer sortableDateInt) {
		this.sortableDateInt = sortableDateInt;
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
		if (getEntryId() == null) {
			return "";
		}

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

	/**
	 * @return the docReference
	 */
	public Set<DocReference> getDocReference() {
		return docReference;
	}

	/**
	 * @param docReference the docReference to set
	 */
	public void setDocReference(Set<DocReference> docReference) {
		this.docReference = docReference;
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

	/**
	 * @param logicalDelete the logicalDelete to set
	 */
	public void setLogicalDelete(Boolean logicalDelete) {
		this.logicalDelete = logicalDelete;
	}

	/**
	 * @return the logicalDelete
	 */
	public Boolean getLogicalDelete() {
		return logicalDelete;
	}

	/**
	 * This enumeration manages recto verso information on folio.
	 *  
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum RectoVerso {
		R(0), // Recto 
		V(1); // Verso
		
		private final int imageRectoVerso;

	    private RectoVerso(int value) {
	    	imageRectoVerso = value;
	    }

	    @Override
	    public String toString(){
	        switch (imageRectoVerso) {
		        case 0 :
		        	return "R";
		        case 1 :
		        	return "V";
		        default :
		        	return "R";
	        }
	    }
	}	
}
