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
package org.medici.docsources.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Month entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Table ( name = "\"tblMonths\"" ) 
public class Month implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 332758709618869401L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"MONTHNUM\"", length=10, nullable=false)
	private Integer monthNum;
	@Column (name="\"MONTHNAME\"", length=50)
	private String monthName;
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
}
