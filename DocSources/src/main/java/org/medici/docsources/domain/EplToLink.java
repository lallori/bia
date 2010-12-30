/*
 * EplToLink.java
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.ObjectUtils;
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
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

/**
 * EplToLink entity.
 * This Entity contains information on a linked topic and his place.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Indexed
@AnalyzerDef(name="eplToLinkAnalyzer",
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
@Table ( name = "\"tblEPLTOLink\"" ) 
public class EplToLink implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8996706080679578610L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"EPLTOID\"",length=10, nullable=false)
	@DocumentId
	private Integer eplToId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"ENTRYID\"", nullable=false)
	@ContainedIn
	private Document document;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"TOPICID\"", nullable=false)
	@IndexedEmbedded
	private TopicList topic; 
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"PLACESALLID\"", nullable=false)
	@IndexedEmbedded
	private Place place;
	
	@Column (name="\"DATECREATED\"", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateCreated;

	/**
	 * 
	 */
	public EplToLink() {
		super();
	}
	/**
	 * 
	 * @param eplToId
	 */
	public EplToLink(Integer eplToId) {
		super();
		
		setEplToId(eplToId);
	}

	/**
	 * @return the eplToId
	 */
	public Integer getEplToId() {
		return eplToId;
	}
	
	/**
	 * @param eplToId the eplToId to set
	 */
	public void setEplToId(Integer eplToId) {
		this.eplToId = eplToId;
	}
	
	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}
	
	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @return the topic
	 */
	public TopicList getTopic() {
		return topic;
	}
	
	/**
	 * @param topic the topic to set
	 */
	public void setTopic(TopicList topic) {
		this.topic = topic;
	}
	
	/**
	 * @return the place
	 */
	public Place getPlace() {
		return place;
	}
	
	/**
	 * @param place the place to set
	 */
	public void setPlace(Place place) {
		this.place = place;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eplToId == null) ? 0 : eplToId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EplToLink other = (EplToLink) obj;
		if (eplToId == null) {
			if (other.eplToId != null)
				return false;
		} else if (!eplToId.equals(other.eplToId))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("");

		if (!ObjectUtils.toString(getTopic()).equals("")) {
			if (!ObjectUtils.toString(getTopic().getTopicTitle()).equals("")) {
				stringBuffer.append(getTopic().getTopicTitle());
			}
		}
		
		if (!ObjectUtils.toString(getPlace()).equals("")) {
			if (!ObjectUtils.toString(getPlace().getPlaceNameFull()).equals("")) {
				if (stringBuffer.length() > 0) {
					stringBuffer.append(" - ");
				}
				stringBuffer.append(getPlace().getPlaceNameFull());
			}
		}
		
		return stringBuffer.toString();
	}
}
