/*
 * VolumeExplorer.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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
package org.medici.bia.common.pagination;

import org.medici.bia.domain.Image;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class VolumeExplorer {
	protected Integer summaryId;
	protected Integer volNum;
	protected String volLetExt;
	protected Image image;
	protected Long total;
	protected Long totalRubricario;
	protected Long totalCarta;
	protected Long totalOther;
	protected Long totalGuardia;
	protected Long totalAppendix;

	/**
	 * 
	 */
	public VolumeExplorer() {
		super();
	}

	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 */
	public VolumeExplorer(Integer volNum, String volLetExt) {
		this.volNum = volNum;
		this.volLetExt = volLetExt;
		summaryId = new Integer(0);
		total  = new Long(0);
		totalRubricario  = new Long(0);
		totalCarta = new Long(0);
		totalAppendix = new Long(0);
		totalGuardia = new Long(0);
		totalOther = new Long(0);
	}

	/**
	 * 
	 * @param summaryId
	 */
	public VolumeExplorer(Integer summaryId) {
		this.summaryId = summaryId;
		volNum = new Integer(0);
		volLetExt = "";
		total  = new Long(0);
		totalRubricario  = new Long(0);
		totalCarta = new Long(0);
		totalAppendix = new Long(0);
		totalGuardia = new Long(0);
		totalOther = new Long(0);
	}
	
	public VolumeExplorer(Integer summaryId, Integer volNum, String volLetExt) {
		this.summaryId = summaryId;
		this.volNum = volNum;
		this.volLetExt = volLetExt;
		total  = new Long(0);
		totalRubricario  = new Long(0);
		totalCarta = new Long(0);
		totalAppendix = new Long(0);
		totalGuardia = new Long(0);
		totalOther = new Long(0);
	}

	/**
	 * @return the summaryId
	 */
	public Integer getSummaryId() {
		return summaryId;
	}

	/**
	 * @param summaryId the summaryId to set
	 */
	public void setSummaryId(Integer summaryId) {
		this.summaryId = summaryId;
	}
	
	/**
	 * @param volNum the volNum to set
	 */
	public void setVolNum(Integer volNum) {
		this.volNum = volNum;
	}

	/**
	 * @return the volNum
	 */
	public Integer getVolNum() {
		return volNum;
	}

	/**
	 * @param volLetExt the volLetExt to set
	 */
	public void setVolLetExt(String volLetExt) {
		this.volLetExt = volLetExt;
	}

	/**
	 * @return the volLetExt
	 */
	public String getVolLetExt() {
		return volLetExt;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}
	
	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}
	
	/**
	 * @return the totalRubricario
	 */
	public Long getTotalRubricario() {
		return totalRubricario;
	}
	
	/**
	 * @param totalRubricario the totalRubricario to set
	 */
	public void setTotalRubricario(Long totalRubricario) {
		this.totalRubricario = totalRubricario;
	}
	
	/**
	 * @return the totalCarta
	 */
	public Long getTotalCarta() {
		return totalCarta;
	}
	
	/**
	 * @param totalFolio the totalCarta to set
	 */
	public void setTotalCarta(Long totalCarta) {
		this.totalCarta = totalCarta;
	}

	/**
	 * @param totalOther the totalOther to set
	 */
	public void setTotalOther(Long totalOther) {
		this.totalOther = totalOther;
	}

	/**
	 * @return the totalOther
	 */
	public Long getTotalOther() {
		return totalOther;
	}

	/**
	 * @param totalGuardia the totalGuardia to set
	 */
	public void setTotalGuardia(Long totalGuardia) {
		this.totalGuardia = totalGuardia;
	}

	/**
	 * @return the totalGuardia
	 */
	public Long getTotalGuardia() {
		return totalGuardia;
	}

	/**
	 * @param totalAppendix the totalAppendix to set
	 */
	public void setTotalAppendix(Long totalAppendix) {
		this.totalAppendix = totalAppendix;
	}

	/**
	 * @return the totalAppendix
	 */
	public Long getTotalAppendix() {
		return totalAppendix;
	}
}
