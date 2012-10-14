/*
 * Month.java
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

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
 * Month entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Cacheable
@Indexed
@Entity
@AnalyzerDefs({
	@AnalyzerDef(name="monthAnalyzer",
		charFilters = {
			@CharFilterDef(factory = MappingCharFilterFactory.class, params = {
				@Parameter(name = "mapping", value = "org/medici/bia/mapping-chars.properties")
			})
		},
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
		filters = {
			@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class)
	}),
	@AnalyzerDef(name = "monthNGram3Analyzer",
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
@Table ( name = "\"tblMonths\"" ) 
public class Month implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 332758709618869401L;

	@Id
	@DocumentId
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"MONTHNUM\"", length=10, nullable=false)
	private Integer monthNum;

	@Column (name="\"MONTHNAME\"", length=50)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="monthName_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String monthName;

	/**
	 * Default constructor. 
	 */
	public Month() {
		super();
	}

	/**
	 * 
	 * @param monthNum
	 */
	public Month(Integer monthNum) {
		setMonthNum(monthNum);
	}

	/**
	 * 
	 * @param monthNum
	 * @param monthName
	 */
	public Month(Integer monthNum, String monthName) {
		setMonthNum(monthNum);
		setMonthName(monthName);
	}

	/**
	 * @return the monthNum
	 */
	public Integer getMonthNum() {
		return monthNum;
	}
	
	/**
	 * @param monthNum the monthNum to set
	 */
	public void setMonthNum(Integer monthNum) {
		this.monthNum = monthNum;
	}
	
	/**
	 * @return the monthName
	 */
	public String getMonthName() {
		return monthName;
	}
	
	/**
	 * @param monthName the monthName to set
	 */
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((monthNum == null) ? 0 : monthNum.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		
		if (! (obj instanceof Month)) {
			return false;
		}

		Month other = (Month) obj;
		if (monthNum == null) {
			if (other.monthNum != null) {
				return false;
			}
		} else if (!monthNum.equals(other.monthNum)) {
			return false;
		}

		return true;
	}
	
	@Override
	public String toString() {
		return monthName;
	}
}
