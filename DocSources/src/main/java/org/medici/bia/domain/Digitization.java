/*
 * Digitization.java
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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.bridge.builtin.BooleanBridge;

/**
 * Digitization entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Audited
@Table ( name = "\"tblDigitization\"" ) 
public class Digitization implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3167509255731787849L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"ID\"",length=10, nullable=false)
	@DocumentId
	private Integer id;
	
	@Column (name="\"volNum\"", length=10, nullable=false)
	private Integer volNum;
	
	@Column (name="\"volLetExt\"", length=1)
	private String volLetExt;
	
	@Column (name="\"active\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean active;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the volNum
	 */
	public Integer getVolNum() {
		return volNum;
	}

	/**
	 * @param volNum the volNum to set
	 */
	public void setVolNum(Integer volNum) {
		this.volNum = volNum;
	}

	/**
	 * @return the volLetExt
	 */
	public String getVolLetExt() {
		return volLetExt;
	}

	/**
	 * @param volLetExt the volLetExt to set
	 */
	public void setVolLetExt(String volLetExt) {
		this.volLetExt = volLetExt;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
}
