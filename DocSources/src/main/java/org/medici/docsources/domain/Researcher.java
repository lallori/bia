/*
 * Researcher.java
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Researcher entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Table (name="\"tblRESEARCHERS\"")
public class Researcher implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4584036038921029845L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"RESIDNo\"", length=10, nullable=false)
	private Integer resIdNo;
	@Column (name="\"RESID\"", length=50)
	private String resId;
	@Column (name="\"RESFIRST\"", length=50)
	private String resFirst;
	@Column (name="\"RESLAST\"", length=50)
	private String resLast;
	@Column (name="\"RESSTART\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date restStart;
	@Column (name="\"RESENDPROJ\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date resEndProj;
	@Column (name="\"RESENDACTUAL\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date resEndActual;
	/**
	 * @return the resIdNo
	 */
	public Integer getResIdNo() {
		return resIdNo;
	}
	/**
	 * @param resIdNo the resIdNo to set
	 */
	public void setResIdNo(Integer resIdNo) {
		this.resIdNo = resIdNo;
	}
	/**
	 * @return the resId
	 */
	public String getResId() {
		return resId;
	}
	/**
	 * @param resId the resId to set
	 */
	public void setResId(String resId) {
		this.resId = resId;
	}
	/**
	 * @return the resFirst
	 */
	public String getResFirst() {
		return resFirst;
	}
	/**
	 * @param resFirst the resFirst to set
	 */
	public void setResFirst(String resFirst) {
		this.resFirst = resFirst;
	}
	/**
	 * @return the resLast
	 */
	public String getResLast() {
		return resLast;
	}
	/**
	 * @param resLast the resLast to set
	 */
	public void setResLast(String resLast) {
		this.resLast = resLast;
	}
	/**
	 * @return the restStart
	 */
	public Date getRestStart() {
		return restStart;
	}
	/**
	 * @param restStart the restStart to set
	 */
	public void setRestStart(Date restStart) {
		this.restStart = restStart;
	}
	/**
	 * @return the resEndProj
	 */
	public Date getResEndProj() {
		return resEndProj;
	}
	/**
	 * @param resEndProj the resEndProj to set
	 */
	public void setResEndProj(Date resEndProj) {
		this.resEndProj = resEndProj;
	}
	/**
	 * @return the resEndActual
	 */
	public Date getResEndActual() {
		return resEndActual;
	}
	/**
	 * @param resEndActual the resEndActual to set
	 */
	public void setResEndActual(Date resEndActual) {
		this.resEndActual = resEndActual;
	}
	
	
}
