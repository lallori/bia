/*
 * Image.java
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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.envers.Audited;

/**
 * Entity implementation class for Entity: Image
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Audited
@Table ( name = "\"tblImages\"" ) 
public class Image implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8616764317326453173L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"imageId\"", length=10, nullable=false)
	private Integer imageId;
	@Column (name="\"volNum\"", length=10, nullable=false)
	private Integer volNum;
	@Column (name="\"volLetExt\"", length=1)
	private String volLetExt;
	@Column (name="\"imageName\"", length=45, nullable=false)
	private String imageName;
	@Enumerated(EnumType.STRING) 
	@Column (name="\"imageType\"", length=1, nullable=false)
	private ImageType imageType;
	@Enumerated(EnumType.STRING)
	@Column (name="\"imageRectoVerso\"", length=1, nullable=true)
	private ImageRectoVerso imageRectoVerso;
	@Column (name="\"missedNumbering\"", length=45, nullable=false)
	private String missedNumbering;
	@Column (name="\"imageOrder\"", length=5, nullable=false)
	private Integer imageOrder;
	@Column (name="\"imageProgTypeNum\"", length=5, nullable=false)
	private Integer imageProgTypeNum;
	@Column (name="\"storagePath\"", length=1, nullable=false)
	private Integer storagePath;
	@Column (name="\"dateCreated\"", length=1, nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	/**
	 * @return the imageId
	 */
	public Integer getImageId() {
		return imageId;
	}

	/**
	 * @param imageId the imageId to set
	 */
	public void setImageId(Integer imageId) {
		this.imageId = imageId;
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
	 * @param imageName the imageName to set
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}
	
	/**
	 * @param imageType the imageType to set
	 */
	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}

	/**
	 * @return the imageType
	 */
	public ImageType getImageType() {
		return imageType;
	}

	/**
	 * 
	 * @param imageRectoVerso
	 */
	public void setImageRectoVerso(ImageRectoVerso imageRectoVerso) {
		this.imageRectoVerso = imageRectoVerso;
	}

	/**
	 * 
	 * @return
	 */
	public ImageRectoVerso getImageRectoVerso() {
		return imageRectoVerso;
	}

	/**
	 * @param missedNumbering the missedNumbering to set
	 */
	public void setMissedNumbering(String missedNumbering) {
		this.missedNumbering = missedNumbering;
	}

	/**
	 * @return the missedNumbering
	 */
	public String getMissedNumbering() {
		return missedNumbering;
	}

	/**
	 * @param imageOrder the imageOrder to set
	 */
	public void setImageOrder(Integer imageOrder) {
		this.imageOrder = imageOrder;
	}

	/**
	 * @return the imageOrder
	 */
	public Integer getImageOrder() {
		return imageOrder;
	}

	/**
	 * @param imageProgTypeNum the imageProgTypeNum to set
	 */
	public void setImageProgTypeNum(Integer imageProgTypeNum) {
		this.imageProgTypeNum = imageProgTypeNum;
	}

	/**
	 * @return the imageProgTypeNum
	 */
	public Integer getImageProgTypeNum() {
		return imageProgTypeNum;
	}

	/**
	 * @param storageDisk the storageDisk to set
	 */
	public void setStoragePath(Integer storagePath) {
		this.storagePath = storagePath;
	}

	/**
	 * @return the storageDisk
	 */
	public Integer getStoragePath() {
		return storagePath;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append(getStoragePath());
		stringBuilder.append("/MDP");
		if (!ObjectUtils.toString(getVolNum()).equals("")) {
			stringBuilder.append(getVolNum());
		}
		if (!ObjectUtils.toString(getVolLetExt()).equals("")) {
			stringBuilder.append(getVolLetExt());
		}
		stringBuilder.append('/');
		if (!StringUtils.isEmpty(getImageName())) {
			stringBuilder.append(getImageName());
		}
		
		return stringBuilder.toString();
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum ImageType {
		R("R"), C("C"), G("G"), A("A"), O("O");
		
		private final String imageType;

	    private ImageType(String value) {
	    	imageType = value;
	    }

	    @Override
	    public String toString(){
	        return imageType;
	    }
	}	

	/**
	 * This enumeration manages recto verso information on folio.
	 *  
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum ImageRectoVerso {
		R("R"), // Recto 
		V("V"), // Verso
		N("N"); // For digitized items without recto or verso.
		
		private final String imageRectoVerso;

	    private ImageRectoVerso(String value) {
	    	imageRectoVerso = value;
	    }

	    @Override
	    public String toString(){
	        return imageRectoVerso;
	    }
	}	
}
