/*
 * TopicList.java
 * 
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
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.solr.analysis.ISOLatin1AccentFilterFactory;
import org.apache.solr.analysis.MappingCharFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

/**
 * TopicList entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDef(name="topicListAnalyzer",
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
@Table ( name = "\"tblTopicsList\"" ) 
public class TopicList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5096678897160778003L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"TOPICID\"", length=10, nullable=false)
	@DocumentId
	private Integer topicId;
	
	@Column (name="\"TOPICTITLE\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String topicTitle;
	
	@Column (name="\"DESCRIPTION\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String description;

    @OneToMany(mappedBy="topic", fetch=FetchType.LAZY)
    @ContainedIn
    private Set<EplToLink> eplToLinks;
    
    /**
	 * 
	 */
	public TopicList() {
		super();
	}

	/**
	 * 
	 * @param topicId
	 */
	public TopicList(Integer topicId) {
		super();
		setTopicId(topicId);
	}

	/**
	 * @return the topicId
	 */
	public Integer getTopicId() {
		return topicId;
	}
	
	/**
	 * @param topicId the topicId to set
	 */
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	
	/**
	 * @return the topicTitle
	 */
	public String getTopicTitle() {
		return topicTitle;
	}
	
	/**
	 * @param topicTitle the topicTitle to set
	 */
	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((topicId == null) ? 0 : topicId.hashCode());
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
		TopicList other = (TopicList) obj;
		if (topicId == null) {
			if (other.topicId != null)
				return false;
		} else if (!topicId.equals(other.topicId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getTopicTitle();
	}
}
