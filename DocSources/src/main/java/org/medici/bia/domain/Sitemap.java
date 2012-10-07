/*
 * Sitemap.java
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
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Sitemap entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Table ( name = "\"tblSitemap\"" ) 
public class Sitemap implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8420904174841099783L;

	@Id
	@Column (name="\"location\"", length=255, nullable=false)
	private String location;
	@Column (name="\"lastModification\"", nullable=true)
	@Temporal(TemporalType.DATE)
	private Date lastModification;
	@Enumerated(EnumType.STRING) 
	@Column (name="\"changeFrequency\"",length=10, nullable=true)
	private ChangeFrequency changeFrequency;
	@Column (name="\"priority\"", nullable=true)
	private Double priority;

	/**
	 * Default constructor.
	 */
	public Sitemap() {
		super();
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the lastModification
	 */
	public Date getLastModification() {
		return lastModification;
	}

	/**
	 * @param lastModification the lastModification to set
	 */
	public void setLastModification(Date lastModification) {
		this.lastModification = lastModification;
	}

	/**
	 * @return the changeFrequency
	 */
	public ChangeFrequency getChangeFrequency() {
		return changeFrequency;
	}

	/**
	 * @param changeFrequency the changeFrequency to set
	 */
	public void setChangeFrequency(ChangeFrequency changeFrequency) {
		this.changeFrequency = changeFrequency;
	}

	/**
	 * @return the priority
	 */
	public Double getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Double priority) {
		this.priority = priority;
	}

	/**
	 * This enumeration manages Change Frequency.
	 * 
	 * From sitemaps.org :
	 * 
	 *  How frequently the page is likely to change. This value provides general 
	 *  information to search engines and may not correlate exactly to how often 
	 *  they crawl the page. Valid values are:
	 *  - always
	 *  - hourly
	 *  - daily
	 *  - weekly
	 *  - monthly
	 *  - yearly
	 *  - never
	 *  
	 *  The value "always" should be used to describe documents that change each 
	 *  time they are accessed. The value "never" should be used to describe 
	 *  archived URLs.
	 *  
	 *  Please note that the value of this tag is considered a hint and not a 
	 *  command. Even though search engine crawlers may consider this 
	 *  information when making decisions, they may crawl pages marked "hourly" 
	 *  less frequently than that, and they may crawl pages marked "yearly" more 
	 *  frequently than that. Crawlers may periodically crawl pages marked 
	 *  "never" so that they can handle unexpected changes to those pages.
	 *  
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum ChangeFrequency {
		ALWAYS("ALWAYS"), 
		HOURLY("HOURLY"),
		DAILY("DAILY"),
		WEEKLY("WEEKLY"),
		MONTHLY("MONTHLY"),
		YEARLY("YEARLY"),
		NEVER("NEVER"); 

		private final String ChangeFrequency;

	    private ChangeFrequency(String value) {
	    	ChangeFrequency = value;
	    }

	    @Override
	    public String toString(){
	        return ChangeFrequency;
	    }
	}	
}
