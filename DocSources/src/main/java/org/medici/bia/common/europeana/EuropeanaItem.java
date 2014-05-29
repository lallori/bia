/*
 * EuropeanaItem.java
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
package org.medici.bia.common.europeana;

import org.medici.bia.common.europeana.model.Aggregation;
import org.medici.bia.common.europeana.model.ProvidedCHO;
import org.medici.bia.common.europeana.model.WebResource;

/**
 * This class represents all elements that has to be serialized for Europeana.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class EuropeanaItem {
	
	/**
	 * The Provided Cultural Heritage Object
	 */
	private ProvidedCHO cho;
	/**
	 * The Web Resource linked to the CHO
	 */
	private WebResource webResource;
	/**
	 * The Aggregation element that links CHOs to WebResources
	 */
	private Aggregation aggregation;
	/**
	 * The Type of the item
	 */
	private ItemType type;
	/**
	 * The item comment
	 */
	private String itemComment;
	
	public static enum ItemType {
		DOCUMENT,
		IMAGE;
	}
	
	public EuropeanaItem(ItemType type) {
		this.type = type;
	}
	
	public ProvidedCHO getCho() {
		return cho;
	}
	
	public void setCho(ProvidedCHO cho) {
		this.cho = cho;
	}
	
	public WebResource getWebResource() {
		return webResource;
	}
	
	public void setWebResource(WebResource webResource) {
		this.webResource = webResource;
	}
	
	public Aggregation getAggregation() {
		return aggregation;
	}
	
	public void setAggregation(Aggregation aggregation) {
		this.aggregation = aggregation;
	}

	public ItemType getType() {
		return type;
	}

	public String getItemComment() {
		return itemComment;
	}

	public void setItemComment(String itemComment) {
		this.itemComment = itemComment;
	}

}
