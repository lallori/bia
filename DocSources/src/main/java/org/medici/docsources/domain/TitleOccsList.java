/*
 * TitleOccList.java
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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

/**
 * TitleOccList entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Audited
@Table ( name = "\"tblTitleOccsList\"" ) 
public class TitleOccsList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8339800362047066324L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"TITLEOCCID\"", length=10, nullable=false)
	private Integer titleOccId;
	@Column (name="\"TITLEOCC\"", length=255)
	private String titleOcc;
	@ManyToOne
	@JoinColumn(name="\"ROLECATMINORID\"")
	private RoleCat roleCatMinorId;
	@Column (name="\"TITLEVARIANTS\"", columnDefinition="LONGTEXT")
	private String titleVariants;

	/**
	 * @return the titleOccId
	 */
	public Integer getTitleOccId() {
		return titleOccId;
	}
	
	/**
	 * @param titleOccId the titleOccId to set
	 */
	public void setTitleOccId(Integer titleOccId) {
		this.titleOccId = titleOccId;
	}
	
	/**
	 * @return the titleOcc
	 */
	public String getTitleOcc() {
		return titleOcc;
	}
	
	/**
	 * @param titleOcc the titleOcc to set
	 */
	public void setTitleOcc(String titleOcc) {
		this.titleOcc = titleOcc;
	}
	
	/**
	 * @return the roleCatMinorId
	 */
	public RoleCat getRoleCatMinorId() {
		return roleCatMinorId;
	}
	
	/**
	 * @param roleCatMinorId the roleCatMinorId to set
	 */
	public void setRoleCatMinorId(RoleCat roleCatMinorId) {
		this.roleCatMinorId = roleCatMinorId;
	}
	
	/**
	 * @return the titleVariants
	 */
	public String getTitleVariants() {
		return titleVariants;
	}
	
	/**
	 * @param titleVariants the titleVariants to set
	 */
	public void setTitleVariants(String titleVariants) {
		this.titleVariants = titleVariants;
	}
}
