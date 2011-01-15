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
	@Column (name="\"imageType\"", length=45, nullable=false)
	private ImageType imageType;
	@Column (name="\"imageName\"", length=45, nullable=false)
	private String imageName;
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
		StringBuffer stringBuffer = new StringBuffer("");
		stringBuffer.append(getStoragePath());
		stringBuffer.append("/MDP");
		if (!ObjectUtils.toString(getVolNum()).equals("")) {
			stringBuffer.append(getVolNum());
		}
		if (!ObjectUtils.toString(getVolLetExt()).equals("")) {
			stringBuffer.append(getVolLetExt());
		}
		stringBuffer.append("/");
		if (!StringUtils.isEmpty(getImageName())) {
			stringBuffer.append(getImageName());
		}
		
		return stringBuffer.toString();
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
		R("R"), C("C");
		
		private final String imageType;

	    private ImageType(String value) {
	    	imageType = value;
	    }

	    @Override
	    public String toString(){
	        return imageType;
	    }
	}	
}
