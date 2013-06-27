/*
 * Place.java
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
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.bridge.builtin.BooleanBridge;

/**
 * Place entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDefs({
	@AnalyzerDef(name="placeAnalyzer",
		charFilters = {
			@CharFilterDef(factory = MappingCharFilterFactory.class, params = {
				@Parameter(name = "mapping", value = "org/medici/bia/mapping-chars.properties")
			})
		},
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
		filters = {
			@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class)
	}),
	@AnalyzerDef(name = "placeNGram3Analyzer",
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class ),
		filters = {
			@TokenFilterDef(factory = StandardFilterFactory.class),
			@TokenFilterDef(factory = LowerCaseFilterFactory.class),
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
@Table ( name = "\"tblPlaces\"" ) 
public class Place implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1161692568465916123L;

	@Id
	@DocumentId
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"PLACEALLID\"", length=10, nullable=false)
	private Integer placeAllId;
	
	@Column (name="\"PLACENAMEID\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer placeNameId;
	
	@Column (name="\"GEOGKEY\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer geogKey;
	
	@Column (name="\"PLACENAME\"", length=255)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="placeName_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String placeName;
	
	@Column (name="\"PLACENAMEFULL\"", length=255)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="placeNameFull_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String placeNameFull;
	
	@Column (name="\"PLNAMEFULL_PLTYPE\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String plNameFullPlType;
	
	@Column (name="\"PLTYPE\"", length=255)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="plType_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String plType;
	
	@Column (name="\"PREFFLAG\"", length=5)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String prefFlag;
	
	@Column (name="\"PLSOURCE\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String plSource;
	
	@Column (name="\"PLPARENT\"", length=255)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="plParent_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String plParent;
	
	@Column (name="\"PARENTTYPE\"", length=255)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="parentType_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String parentType;
	
	@Column (name="\"PLPARENT_TERM_ID\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer plParentTermId;
	
	@Column (name="\"PLPARENT_SUBJECT_ID\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer plParentSubjectId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"PLPARENT_PLACEALLID\"")
	@IndexedEmbedded(depth=1)
	private Place parentPlace;
	
	@Column (name="\"GPARENT\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String gParent;
	
	@Column (name="\"GPTYPE\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String gpType;
	
	@Column (name="\"GGP\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String ggp;
	
	@Column (name="\"GGPTYPE\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String ggpType;
	
	@Column (name="\"GP2\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String gp2;
	
	@Column (name="\"GP2TYPE\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String gp2Ttype;
	
	@Column (name="\"RESID\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String researcher;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"createdBy\"", nullable=true)
	private User createdBy;

	@Column (name="\"DATEENTERED\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateEntered;
	
	@Column (name="\"PLACESMEMO\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String placesMemo;
	
	@Column (name="\"ADDLRES\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean addlRes;
	
	@Column (name="\"TERM_ACCENT\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String termAccent;
	
	@Column (name="\"LANGUAGE\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer language;
	
	@Column (name="\"OTHER_FLAGS\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String otherFlags;
	
	@Column (name="\"GEOGKEY_CHILDREN\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String geogkeyChildren;

	@Column (name="\"LOGICALDELETE\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean logicalDelete;

	@Column (name="\"DATECREATED\"")
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateCreated;
	
	@Column (name="\"LASTUPDATE\"")
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date lastUpdate;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"lastUpdateBy\"", nullable=true)
	private User lastUpdateBy;
	
	@OneToMany(mappedBy="senderPlace", fetch=FetchType.LAZY)
	//@ContainedIn
    private Set<Document> senderDocuments;
    
    @OneToMany(mappedBy="recipientPlace", fetch=FetchType.LAZY)
	//@ContainedIn
    private Set<Document> recipientDocuments;

    @OneToMany(mappedBy="bornPlace", fetch=FetchType.LAZY)
	//@ContainedIn
    private Set<People> bornedPeople;
    
    @OneToMany(mappedBy="deathPlace", fetch=FetchType.LAZY)
	//@ContainedIn
    private Set<People> deathPeople;

    @OneToMany(mappedBy="place", fetch=FetchType.LAZY)
	//@ContainedIn
    private Set<EplToLink> eplToLinks;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="place", cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="PLACEALLID", referencedColumnName="PALCEALLID")
    @IndexedEmbedded
    private PlaceGeographicCoordinates placeGeographicCoordinates;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="place", cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @IndexedEmbedded
    private Set<PlaceExternalLinks> placeExternalLinks;
    
	/**
	 * Default constructor.
	 * 
	 */
	public Place(){
		super();
	}

	/**
	 * 
	 * @param placeAllId
	 */
	public Place(Integer placeAllId) {
		super();
		
		setPlaceAllId(placeAllId);
	}
	
	/**
	 * @return the addlRes
	 */
	public Boolean getAddlRes() {
		return addlRes;
	}
	
	/**
	 * @return the bornedPeople
	 */
	public Set<People> getBornedPeople() {
		return bornedPeople;
	}
	
	/**
	 * @return the createdBy
	 */
	public User getCreatedBy() {
		return createdBy;
	}
	
	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	
	/**
	 * @return the dateEntered
	 */
	public Date getDateEntered() {
		return dateEntered;
	}
	
	/**
	 * @return the deathPeople
	 */
	public Set<People> getDeathPeople() {
		return deathPeople;
	}
	
	/**
	 * @return the eplToLinks
	 */
	public Set<EplToLink> getEplToLinks() {
		return eplToLinks;
	}
	
	/**
	 * @return the geogKey
	 */
	public Integer getGeogKey() {
		return geogKey;
	}
	
	/**
	 * @return the geogkeyChildren
	 */
	public String getGeogkeyChildren() {
		return geogkeyChildren;
	}
	
	/**
	 * @return the ggp
	 */
	public String getGgp() {
		return ggp;
	}
	
	/**
	 * @return the ggpType
	 */
	public String getGgpType() {
		return ggpType;
	}
	
	/**
	 * @return the gp2
	 */
	public String getGp2() {
		return gp2;
	}
	
	/**
	 * @return the gp2Ttype
	 */
	public String getGp2Ttype() {
		return gp2Ttype;
	}
	
	/**
	 * @return the gParent
	 */
	public String getgParent() {
		return gParent;
	}
	
	/**
	 * @return the gpType
	 */
	public String getGpType() {
		return gpType;
	}
	
	/**
	 * @return the language
	 */
	public Integer getLanguage() {
		return language;
	}
	
	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	/**
	 * @return the lastUpdateBy
	 */
	public User getLastUpdateBy() {
		return lastUpdateBy;
	}
	
	/**
	 * @return the logicalDelete
	 */
	public Boolean getLogicalDelete() {
		return logicalDelete;
	}
	
	/**
	 * @return the otherFlags
	 */
	public String getOtherFlags() {
		return otherFlags;
	}
	
	/**
	 * @return the parentPlace
	 */
	public Place getParentPlace() {
		return parentPlace;
	}
	
	/**
	 * @return the parentType
	 */
	public String getParentType() {
		return parentType;
	}
	
	/**
	 * @return the placeAllId
	 */
	public Integer getPlaceAllId() {
		return placeAllId;
	}
	
	/**
	 * 
	 * @return the placeExternalLinks
	 */
	public Set<PlaceExternalLinks> getPlaceExternalLinks() {
		return placeExternalLinks;
	}
	
	/**
	 * 
	 * @return the placeGeographicCoordinates
	 */
	public PlaceGeographicCoordinates getPlaceGeographicCoordinates() {
		return placeGeographicCoordinates;
	}
	
	/**
	 * @return the placeName
	 */
	public String getPlaceName() {
		return placeName;
	}
	
	/**
	 * @return the placeNameFull
	 */
	public String getPlaceNameFull() {
		return placeNameFull;
	}
	
	/**
	 * @return the placeNameId
	 */
	public Integer getPlaceNameId() {
		return placeNameId;
	}
	
	/**
	 * @return the placesMemo
	 */
	public String getPlacesMemo() {
		return placesMemo;
	}
	
	/**
	 * @return the plNameFullPlType
	 */
	public String getPlNameFullPlType() {
		return plNameFullPlType;
	}
	
	/**
	 * @return the plParent
	 */
	public String getPlParent() {
		return plParent;
	}
	
	/**
	 * @return the plParentSubjectId
	 */
	public Integer getPlParentSubjectId() {
		return plParentSubjectId;
	}
	
	/**
	 * @return the plParentTermId
	 */
	public Integer getPlParentTermId() {
		return plParentTermId;
	}
	
	/**
	 * @return the plSource
	 */
	public String getPlSource() {
		return plSource;
	}
	
	/**
	 * @return the plType
	 */
	public String getPlType() {
		return plType;
	}
	
	/**
	 * @return the prefFlag
	 */
	public String getPrefFlag() {
		return prefFlag;
	}
	
	/**
	 * @return the recipientDocuments
	 */
	public Set<Document> getRecipientDocuments() {
		return recipientDocuments;
	}
	
	/**
	 * @return the researcher
	 */
	public String getResearcher() {
		return researcher;
	}
	
	/**
	 * @return the senderDocuments
	 */
	public Set<Document> getSenderDocuments() {
		return senderDocuments;
	}
	
	/**
	 * @return the termAccent
	 */
	public String getTermAccent() {
		return termAccent;
	}
	
	/**
	 * @param addlRes the addlRes to set
	 */
	public void setAddlRes(Boolean addlRes) {
		this.addlRes = addlRes;
	}
	
	/**
	 * @param bornedPeople the bornedPeople to set
	 */
	public void setBornedPeople(Set<People> bornedPeople) {
		this.bornedPeople = bornedPeople;
	}
	
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	/**
	 * @param dateEntered the dateEntered to set
	 */
	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}
	
	/**
	 * @param deathPeople the deathPeople to set
	 */
	public void setDeathPeople(Set<People> deathPeople) {
		this.deathPeople = deathPeople;
	}

	/**
	 * @param eplToLinks the eplToLinks to set
	 */
	public void setEplToLinks(Set<EplToLink> eplToLinks) {
		this.eplToLinks = eplToLinks;
	}
	
	/**
	 * @param geogKey the geogKey to set
	 */
	public void setGeogKey(Integer geogKey) {
		this.geogKey = geogKey;
	}
	
	/**
	 * @param geogkeyChildren the geogkeyChildren to set
	 */
	public void setGeogkeyChildren(String geogkeyChildren) {
		this.geogkeyChildren = geogkeyChildren;
	}
	
	/**
	 * @param ggp the ggp to set
	 */
	public void setGgp(String ggp) {
		this.ggp = ggp;
	}
	
	/**
	 * @param ggpType the ggpType to set
	 */
	public void setGgpType(String ggpType) {
		this.ggpType = ggpType;
	}
	
	/**
	 * @param gp2 the gp2 to set
	 */
	public void setGp2(String gp2) {
		this.gp2 = gp2;
	}
	
	/**
	 * @param gp2Ttype the gp2Ttype to set
	 */
	public void setGp2Ttype(String gp2Ttype) {
		this.gp2Ttype = gp2Ttype;
	}
	
	/**
	 * @param gParent the gParent to set
	 */
	public void setgParent(String gParent) {
		this.gParent = gParent;
	}
	
	/**
	 * @param gpType the gpType to set
	 */
	public void setGpType(String gpType) {
		this.gpType = gpType;
	}
	
	/**
	 * @param language the language to set
	 */
	public void setLanguage(Integer language) {
		this.language = language;
	}

	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(User lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	/**
	 * @param logicalDelete the logicalDelete to set
	 */
	public void setLogicalDelete(Boolean logicalDelete) {
		this.logicalDelete = logicalDelete;
	}

	/**
	 * @param otherFlags the otherFlags to set
	 */
	public void setOtherFlags(String otherFlags) {
		this.otherFlags = otherFlags;
	}

	/**
	 * @param parentPlace the parentPlace to set
	 */
	public void setParentPlace(Place parentPlace) {
		this.parentPlace = parentPlace;
	}

	/**
	 * @param parentType the parentType to set
	 */
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	/**
	 * @param placeAllId the placeAllId to set
	 */
	public void setPlaceAllId(Integer placeAllId) {
		this.placeAllId = placeAllId;
	}

	/**
	 * 
	 * @param placeExternalLinks the placeExternalLinks to set
	 */
	public void setPlaceExternalLinks(Set<PlaceExternalLinks> placeExternalLinks) {
		this.placeExternalLinks = placeExternalLinks;
	}

	/**
	 * 
	 * @param placeGeographicCoordinates the placeGeographicCoordinates to set
	 */
	public void setPlaceGeographicCoordinates(PlaceGeographicCoordinates placeGeographicCoordinates) {
		this.placeGeographicCoordinates = placeGeographicCoordinates;
	}

	/**
	 * @param placeName the placeName to set
	 */
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	/**
	 * @param placeNameFull the placeNameFull to set
	 */
	public void setPlaceNameFull(String placeNameFull) {
		this.placeNameFull = placeNameFull;
	}

	/**
	 * @param placeNameId the placeNameId to set
	 */
	public void setPlaceNameId(Integer placeNameId) {
		this.placeNameId = placeNameId;
	}

	/**
	 * @param placesMemo the placesMemo to set
	 */
	public void setPlacesMemo(String placesMemo) {
		this.placesMemo = placesMemo;
	}

	/**
	 * @param plNameFullPlType the plNameFullPlType to set
	 */
	public void setPlNameFullPlType(String plNameFullPlType) {
		this.plNameFullPlType = plNameFullPlType;
	}

	/**
	 * @param plParent the plParent to set
	 */
	public void setPlParent(String plParent) {
		this.plParent = plParent;
	}

	/**
	 * @param plParentSubjectId the plParentSubjectId to set
	 */
	public void setPlParentSubjectId(Integer plParentSubjectId) {
		this.plParentSubjectId = plParentSubjectId;
	}

	/**
	 * @param plParentTermId the plParentTermId to set
	 */
	public void setPlParentTermId(Integer plParentTermId) {
		this.plParentTermId = plParentTermId;
	}

	/**
	 * @param plSource the plSource to set
	 */
	public void setPlSource(String plSource) {
		this.plSource = plSource;
	}

	/**
	 * @param plType the plType to set
	 */
	public void setPlType(String plType) {
		this.plType = plType;
	}

	/**
	 * @param prefFlag the prefFlag to set
	 */
	public void setPrefFlag(String prefFlag) {
		this.prefFlag = prefFlag;
	}

	/**
	 * @param recipientDocuments the recipientDocuments to set
	 */
	public void setRecipientDocuments(Set<Document> recipientDocuments) {
		this.recipientDocuments = recipientDocuments;
	}

	/**
	 * @param researcher the researcher to set
	 */
	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}

	/**
	 * @param senderDocuments the senderDocuments to set
	 */
	public void setSenderDocuments(Set<Document> senderDocuments) {
		this.senderDocuments = senderDocuments;
	}

	/**
	 * @param termAccent the termAccent to set
	 */
	public void setTermAccent(String termAccent) {
		this.termAccent = termAccent;
	}

}
