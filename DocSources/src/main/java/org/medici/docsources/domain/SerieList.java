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
package org.medici.docsources.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

/**
 * SerieList entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Cacheable
@Audited
@Table ( name = "\"tblSeriesList\"" ) 
public class SerieList implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 135030362618173811L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"SeriesRefNum\"", length=10, nullable=false)
	private Integer seriesRefNum;
	@Column (name="\"Title\"", length=100)
	private String title;
	@Column (name="\"SubTitle1\"", length=100)
	private String subTitle1;
	@Column (name="\"SubTitle2\"", length=100,nullable=true)
	private String subTitle2;

	/**
	 * 
	 * @param seriesRefNum
	 */
	public void setSeriesRefNum(Integer seriesRefNum) {
		this.seriesRefNum = seriesRefNum;
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
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
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
	 * @return
	 */
	public String getSubTitle1() {
		return subTitle1;
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
	 * @return
	 */
	public String getSubTitle2() {
		return subTitle2;
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
		result = prime * result + ((seriesRefNum == null) ? 0 : seriesRefNum.hashCode());
		return result;
	}

	/**
	 * Implementing this method is mandatory for caching object.
	 * 
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
		SerieList other = (SerieList) obj;
		if (seriesRefNum == null) {
			if (other.seriesRefNum != null)
				return false;
		} else if (!seriesRefNum.equals(other.seriesRefNum))
			return false;
		return true;
	}
}