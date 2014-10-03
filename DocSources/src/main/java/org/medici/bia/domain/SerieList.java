/*
 * SerieList.java
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
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
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
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

/**
 * SerieList entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDefs({
	@AnalyzerDef(name="serieListAnalyzer",
		charFilters = {
			@CharFilterDef(factory = MappingCharFilterFactory.class, params = {
				@Parameter(name = "mapping", value = "org/medici/bia/mapping-chars.properties")
			})
		},
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
		filters = {
			@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class)
	}),
	@AnalyzerDef(name = "serieListNGram3Analyzer",
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
@Cacheable
@Audited
@Table ( name = "\"tblSeriesList\"" ) 
public class SerieList implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 135030362618173811L;
	
	@Id
	@DocumentId
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"SERIESREFNUM\"", length=10, nullable=false)
	private Integer seriesRefNum;
	
	@Column (name="\"SUBTITLE1\"", length=100)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="subTitle1_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String subTitle1;
	
	@Column (name="\"SUBTITLE2\"", length=100,nullable=true)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="subTitle2_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String subTitle2;
	
	@Column (name="\"TITLE\"", length=100)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="title_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String title;

    @ContainedIn
    @OneToMany(mappedBy="serieList", fetch=FetchType.LAZY)
    private Set<Volume> volumes;

    /**
	 * Default constructor
	 */
	public SerieList() {
		super();
	}

	/**
	 * 
	 * @param seriesRefNum
	 */
	public SerieList(Integer seriesRefNum) {
		this.seriesRefNum = seriesRefNum;
	}
	
	/**
	 * Implementing this method is mandatory for caching object.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}
		
		if (! (obj instanceof SerieList)) {
			return false;
		}

		SerieList other = (SerieList) obj;
		if (getSeriesRefNum() == null) {
			if (other.getSeriesRefNum() != null) {
				return false;
			}
		} else if (!getSeriesRefNum().equals(other.getSeriesRefNum())) {
			return false;
		}

		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer getSeriesRefNum() {
		return seriesRefNum;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSubTitle1() {
		return subTitle1;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSubTitle2() {
		return subTitle2;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	public Set<Volume> getVolumes() {
		return volumes;
	}

	/**
	 * Implementing this method is mandatory for caching object.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getSeriesRefNum() == null) ? 0 : getSeriesRefNum().hashCode());
		return result;
	}

	/**
	 * 
	 * @param seriesRefNum
	 */
	public void setSeriesRefNum(Integer seriesRefNum) {
		this.seriesRefNum = seriesRefNum;
	}

	/**
	 * 
	 * @param subTitle1
	 */
	public void setSubTitle1(String subTitle1) {
		this.subTitle1 = subTitle1;
	}
	
	
	/**
	 * 
	 * @param subTitle2
	 */
	public void setSubTitle2(String subTitle2) {
		this.subTitle2 = subTitle2;
	}

	
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public void setVolumes(Set<Volume> volumes) {
		this.volumes = volumes;
	}

	/**
	 * toString method.
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(0);
		if (!StringUtils.isEmpty(getTitle())) {
			stringBuilder.append(getTitle());
		}
		
		if (!StringUtils.isEmpty(getSubTitle1())) {
			if (stringBuilder.length() > 0) {
				stringBuilder.append(" / ");
			}
			stringBuilder.append(getSubTitle1());
		}

		if (!StringUtils.isEmpty(getSubTitle2())) {
			if (stringBuilder.length() > 0) {
				stringBuilder.append(" / ");
			}

			stringBuilder.append(getSubTitle2());
		}

		return stringBuilder.toString();
	}
}