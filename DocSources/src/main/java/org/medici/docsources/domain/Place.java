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
package org.medici.docsources.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.solr.analysis.ISOLatin1AccentFilterFactory;
import org.apache.solr.analysis.MappingCharFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
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
@AnalyzerDef(name="placeAnalyzer",
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
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String placeName;
	
	@Column (name="\"PLACENAMEFULL\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String placeNameFull;
	
	@Column (name="\"PLNAMEFULL_PLTYPE\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String plNameFullPlType;
	
	@Column (name="\"PLTYPE\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String plType;
	
	@Column (name="\"PREFFLAG\"", length=5)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String prefFlag;
	
	@Column (name="\"PLSOURCE\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String plSource;
	
	@Column (name="\"PLPARENT\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String plParent;
	
	@Column (name="\"PARENTTYPE\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String parentType;
	
	@Column (name="\"PLPARENT_TERM_ID\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer plParentTermId;
	
	@Column (name="\"PLPARENT_SUBJECT_ID\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
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
	
	@Column (name="\"DATEENTERED\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateEntered;
	
	@Column (name="\"PLACESMEMO\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String placesMemo;
	
	@Column (name="\"ADDLRES\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
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

    @OneToMany(mappedBy="senderPlace", fetch=FetchType.LAZY)
	@ContainedIn
    private Set<Document> senderDocuments;
    
    @OneToMany(mappedBy="recipientPlace", fetch=FetchType.LAZY)
	@ContainedIn
    private Set<Document> recipientDocuments;

    @OneToMany(mappedBy="bornPlace", fetch=FetchType.LAZY)
	@ContainedIn
    private Set<People> bornedPeople;
    
    @OneToMany(mappedBy="deathPlace", fetch=FetchType.LAZY)
	@ContainedIn
    private Set<People> deathPeople;

    @OneToMany(mappedBy="place", fetch=FetchType.LAZY)
	@ContainedIn
    private Set<EplToLink> eplToLinks;
    
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
	 * @return the placeAllId
	 */
	public Integer getPlaceAllId() {
		return placeAllId;
	}
	
	/**
	 * @param placeAllId the placeAllId to set
	 */
	public void setPlaceAllId(Integer placeAllId) {
		this.placeAllId = placeAllId;
	}
	
	/**
	 * @return the placeNameId
	 */
	public Integer getPlaceNameId() {
		return placeNameId;
	}
	
	/**
	 * @param placeNameId the placeNameId to set
	 */
	public void setPlaceNameId(Integer placeNameId) {
		this.placeNameId = placeNameId;
	}
	
	/**
	 * @return the geogKey
	 */
	public Integer getGeogKey() {
		return geogKey;
	}
	
	/**
	 * @param geogKey the geogKey to set
	 */
	public void setGeogKey(Integer geogKey) {
		this.geogKey = geogKey;
	}
	
	/**
	 * @return the placeName
	 */
	public String getPlaceName() {
		return placeName;
	}
	
	/**
	 * @param placeName the placeName to set
	 */
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	
	/**
	 * @return the placeNameFull
	 */
	public String getPlaceNameFull() {
		return placeNameFull;
	}
	
	/**
	 * @param placeNameFull the placeNameFull to set
	 */
	public void setPlaceNameFull(String placeNameFull) {
		this.placeNameFull = placeNameFull;
	}
	
	/**
	 * @return the plNameFullPlType
	 */
	public String getPlNameFullPlType() {
		return plNameFullPlType;
	}
	
	/**
	 * @param plNameFullPlType the plNameFullPlType to set
	 */
	public void setPlNameFullPlType(String plNameFullPlType) {
		this.plNameFullPlType = plNameFullPlType;
	}
	
	/**
	 * @return the plType
	 */
	public String getPlType() {
		return plType;
	}
	
	/**
	 * @param plType the plType to set
	 */
	public void setPlType(String plType) {
		this.plType = plType;
	}
	
	/**
	 * @return the prefFlag
	 */
	public String getPrefFlag() {
		return prefFlag;
	}
	
	/**
	 * @param prefFlag the prefFlag to set
	 */
	public void setPrefFlag(String prefFlag) {
		this.prefFlag = prefFlag;
	}
	
	/**
	 * @return the plSource
	 */
	public String getPlSource() {
		return plSource;
	}
	
	/**
	 * @param plSource the plSource to set
	 */
	public void setPlSource(String plSource) {
		this.plSource = plSource;
	}
	
	/**
	 * @return the plParent
	 */
	public String getPlParent() {
		return plParent;
	}
	
	/**
	 * @param plParent the plParent to set
	 */
	public void setPlParent(String plParent) {
		this.plParent = plParent;
	}
	
	/**
	 * @return the parentType
	 */
	public String getParentType() {
		return parentType;
	}
	
	/**
	 * @param parentType the parentType to set
	 */
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
	
	/**
	 * @return the plParentTermId
	 */
	public Integer getPlParentTermId() {
		return plParentTermId;
	}
	
	/**
	 * @param plParentTermId the plParentTermId to set
	 */
	public void setPlParentTermId(Integer plParentTermId) {
		this.plParentTermId = plParentTermId;
	}
	
	/**
	 * @return the plParentSubjectId
	 */
	public Integer getPlParentSubjectId() {
		return plParentSubjectId;
	}
	
	/**
	 * @param plParentSubjectId the plParentSubjectId to set
	 */
	public void setPlParentSubjectId(Integer plParentSubjectId) {
		this.plParentSubjectId = plParentSubjectId;
	}
	
	/**
	 * @return the parentPlace
	 */
	public Place getParentPlace() {
		return parentPlace;
	}
	
	/**
	 * @param parentPlace the parentPlace to set
	 */
	public void setParentPlace(Place parentPlace) {
		this.parentPlace = parentPlace;
	}
	
	/**
	 * @return the gParent
	 */
	public String getgParent() {
		return gParent;
	}
	
	/**
	 * @param gParent the gParent to set
	 */
	public void setgParent(String gParent) {
		this.gParent = gParent;
	}
	
	/**
	 * @return the gpType
	 */
	public String getGpType() {
		return gpType;
	}
	
	/**
	 * @param gpType the gpType to set
	 */
	public void setGpType(String gpType) {
		this.gpType = gpType;
	}
	
	/**
	 * @return the ggp
	 */
	public String getGgp() {
		return ggp;
	}
	
	/**
	 * @param ggp the ggp to set
	 */
	public void setGgp(String ggp) {
		this.ggp = ggp;
	}
	
	/**
	 * @return the ggpType
	 */
	public String getGgpType() {
		return ggpType;
	}
	
	/**
	 * @param ggpType the ggpType to set
	 */
	public void setGgpType(String ggpType) {
		this.ggpType = ggpType;
	}
	
	/**
	 * @return the gp2
	 */
	public String getGp2() {
		return gp2;
	}
	
	/**
	 * @param gp2 the gp2 to set
	 */
	public void setGp2(String gp2) {
		this.gp2 = gp2;
	}
	
	/**
	 * @return the gp2Ttype
	 */
	public String getGp2Ttype() {
		return gp2Ttype;
	}
	
	/**
	 * @param gp2Ttype the gp2Ttype to set
	 */
	public void setGp2Ttype(String gp2Ttype) {
		this.gp2Ttype = gp2Ttype;
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
	 * @return the dateEntered
	 */
	public Date getDateEntered() {
		return dateEntered;
	}
	
	/**
	 * @param dateEntered the dateEntered to set
	 */
	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}
	
	/**
	 * @return the placesMemo
	 */
	public String getPlacesMemo() {
		return placesMemo;
	}
	
	/**
	 * @param placesMemo the placesMemo to set
	 */
	public void setPlacesMemo(String placesMemo) {
		this.placesMemo = placesMemo;
	}

	/**
	 * @return the addlRes
	 */
	public Boolean getAddlRes() {
		return addlRes;
	}
	
	/**
	 * @param addlRes the addlRes to set
	 */
	public void setAddlRes(Boolean addlRes) {
		this.addlRes = addlRes;
	}
	
	/**
	 * @return the termAccent
	 */
	public String getTermAccent() {
		return termAccent;
	}
	
	/**
	 * @param termAccent the termAccent to set
	 */
	public void setTermAccent(String termAccent) {
		this.termAccent = termAccent;
	}
	
	/**
	 * @return the language
	 */
	public Integer getLanguage() {
		return language;
	}
	
	/**
	 * @param language the language to set
	 */
	public void setLanguage(Integer language) {
		this.language = language;
	}
	
	/**
	 * @return the otherFlags
	 */
	public String getOtherFlags() {
		return otherFlags;
	}
	
	/**
	 * @param otherFlags the otherFlags to set
	 */
	public void setOtherFlags(String otherFlags) {
		this.otherFlags = otherFlags;
	}
	
	/**
	 * @return the geogkeyChildren
	 */
	public String getGeogkeyChildren() {
		return geogkeyChildren;
	}
	
	/**
	 * @param geogkeyChildren the geogkeyChildren to set
	 */
	public void setGeogkeyChildren(String geogkeyChildren) {
		this.geogkeyChildren = geogkeyChildren;
	}

	/**
	 * @param senderDocuments the senderDocuments to set
	 */
	public void setSenderDocuments(Set<Document> senderDocuments) {
		this.senderDocuments = senderDocuments;
	}

	/**
	 * @return the senderDocuments
	 */
	public Set<Document> getSenderDocuments() {
		return senderDocuments;
	}

	/**
	 * @param recipientDocuments the recipientDocuments to set
	 */
	public void setRecipientDocuments(Set<Document> recipientDocuments) {
		this.recipientDocuments = recipientDocuments;
	}

	/**
	 * @return the recipientDocuments
	 */
	public Set<Document> getRecipientDocuments() {
		return recipientDocuments;
	}

	/**
	 * @param bornedPeople the bornedPeople to set
	 */
	public void setBornedPeople(Set<People> bornedPeople) {
		this.bornedPeople = bornedPeople;
	}

	/**
	 * @return the bornedPeople
	 */
	public Set<People> getBornedPeople() {
		return bornedPeople;
	}

	/**
	 * @param deathPeople the deathPeople to set
	 */
	public void setDeathPeople(Set<People> deathPeople) {
		this.deathPeople = deathPeople;
	}

	/**
	 * @return the deathPeople
	 */
	public Set<People> getDeathPeople() {
		return deathPeople;
	}

	/**
	 * @param eplToLinks the eplToLinks to set
	 */
	public void setEplToLinks(Set<EplToLink> eplToLinks) {
		this.eplToLinks = eplToLinks;
	}

	/**
	 * @return the eplToLinks
	 */
	public Set<EplToLink> getEplToLinks() {
		return eplToLinks;
	}
}
