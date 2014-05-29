/*
 * WebResource.java
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
package org.medici.bia.common.europeana.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.medici.bia.common.europeana.EDMConstants;


/**
 * The Web Resource associated to a {@link ProvidedCHO}.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@XmlRootElement(name = EDMConstants.EDM_PREFIX + ":" + EDMConstants.WEBRESOURCE)
@XmlAccessorType(XmlAccessType.FIELD)
public class WebResource extends EDMResource {
	
	/* ###### DC_PREFIX ###### */
	
	/**
	 * Use for an account or description of this digital representation.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:description&gt;Performance with Buccin trombone&lt;/dc:description&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":description", type = LiteralOrReference.class)
	private List<LiteralOrReference> description = new ArrayList<LiteralOrReference>();
	
	/**
	 * Use for the format of this digital representation.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dc:rights&gt;image/jpeg&lt;/dc:rights&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":format", type = LiteralOrReference.class)
	private List<LiteralOrReference> format = new ArrayList<LiteralOrReference>();
	
	/**
	 * Use to give the name of the rights holder of this digital representation if possible or for more general rights
	 * information.<br/>
	 * Note the difference between this property and the mandatory, controlled edm:rights property below.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:rights&gt;Copyright © British Library Board&lt;/dc:rights&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":rights", type = LiteralOrReference.class)
	private List<LiteralOrReference> rights = new ArrayList<LiteralOrReference>();
	
	/**
	 * A related resource from which the web resource is derived in whole or in part.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dc:source&gt;The name of the source video tape&lt;/dc:source&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":source", type = LiteralOrReference.class)
	private List<LiteralOrReference> source = new ArrayList<LiteralOrReference>();
	
	/* ###### DCTERMS_PREFIX ###### */
	
	/**
	 * An established standard to which the Web Resource conforms.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:conformsTo&gt;W3C WCAG 2.0&lt;/dcterms:conformsTo&gt;</code> (web content accessibility
	 * guidelines)
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":conformsTo", type = LiteralOrReference.class)
	private List<LiteralOrReference> conformsTo = new ArrayList<LiteralOrReference>();
	
	/**
	 * The date of creation of the Web Resource.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:created&gt;2010&lt;/dcterms:created&gt;</code><br/>
	 * or create a reference to an instance of the TimeSpan class<br/>
	 * <code>&lt;dc:date rdf:resource="http://semium.org/time/2010"/&gt;</code> 

	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":created", type = LiteralOrReference.class)
	private List<LiteralOrReference> created = new ArrayList<LiteralOrReference>();
	
	/**
	 * The size or duration of the digital resource.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:extent&gt;1h 26 min 41 sec&lt;/dcterms:extent&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":extent", type = ValuableResource.class)
	private List<ValuableResource> extent = new ArrayList<ValuableResource>();
	
	/**
	 * A resource that is included either physically or logically in the Web Resource.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:hasPart rdf:resource="http://www.identifier/Part" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":hasPart", type = ReferenceableResource.class)
	private List<ReferenceableResource> hasPart = new ArrayList<ReferenceableResource>();
	
	/**
	 * Another resource that is substantially the same as the Web Resource but in another format.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:isFormatOf&gt;http://upload.wikimedia.org/wikipedia/en/f/f3/Europeana_logo.png&lt;/dcterms:isFormatOf&gt;</code><br/>
	 * for a png image file of the described tiff web resource.<br/>
	 * Or as a link to a resource <br/>
	 * <code>&lt;dcterms:hasFormat rdf:resource="http://upload.wikimedia.org/wikipedia/en/f/f3/Europeana_logo.png" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":isFormatOf", type = LiteralOrReference.class)
	private List<LiteralOrReference> isFormatOf = new ArrayList<LiteralOrReference>();
	
	/**
	 * A resource in which the Web Resource is physically or logically included. This property can be used for web 
	 * resources that are part of a hierarchy. Hierarchies can be represented as hierarchies of ProvidedCHOs or 
	 * hierarchies of WebResources but not both at the same time. <br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:isPartOf rdf:resource="http://data.europeana.eu/item/08701/1B0BACAA44D5A807E43D9B411C9781AAD2F96E65" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":isPartOf", type = ReferenceableResource.class)
	private List<ReferenceableResource> isPartOf = new ArrayList<ReferenceableResource>();
	
	/**
	 * Date of formal issuance or publication of the Web Resource.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:issued&gt;1999&lt;/dcterms:issued&gt;</code><br/>
	 * or create a reference to an instance of the TimeSpan class<br/>
	 * <code>&lt;dcterms:issued rdf:resource="http://semium.org/time/1999" /&gt;</code> (late 18th century)
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":issued", type = LiteralOrReference.class)
	private List<LiteralOrReference> issued = new ArrayList<LiteralOrReference>();
	
	/* ###### EDM_PREFIX ###### */
	
	/**
	 * Where one CHO has several web resources, shown by multiple instances of the edm:hasView property on the 
	 * ore:Aggregation this property can be used to show the sequence of the objects. Each web resource (apart 
	 * from the first in the sequence) should use this property to give the URI of the preceding resource in the 
	 * sequence.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:isNextInSequence rdf:resource="http://data.europeana.eu/item 2020601/9A3907CB46B651DE91621933ECC31EC1DC52B33C" /&gt;</code><br/>
	 * links to the WebResource for page 2 of a digitized diary from the WebResource for page 3.
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":isNextInSequence", type = ReferenceableResource.class)
	private List<ReferenceableResource> isNextInSequence  = new ArrayList<ReferenceableResource>();
	
	/**
	 * The value in this element will indicate the usage and access rights that apply to this digital representation. At 
	 * the time of writing only the rights associated with the ore:Aggregation class (see edm:rights in the 
	 * ore:Aggregation table) are implemented in the Europeana portal. Future implementations will be able to use 
	 * the edm:rights associated with individual WebResources so it is strongly!recommended that a value is 
	 * supplied for this property for each instance of a WebResource.  The rights statement specified at the level of 
	 * the web resource will ‘override’ the statement specified at the level of the aggregation.<br/>
	 * The value in this element is a URI taken from the set of those defined for use in Europeana. A list of these can 
	 * be found <a href="http://pro.europeana.eu/web/availableVrightsVstatements">here</a>.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:rights rdf:resource="http://creativecommons.org/publicdomain/mark/1.0/" /&gt;</code><br/>
	 * <code>&lt;europeana:rights rdf:resource="http://www.europeana.eu/rights/rrVf/" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":rights", type = ReferenceableResource.class)
	private ReferenceableResource edmRights;

	public List<LiteralOrReference> getDescriptions() {
		return description;
	}
	
	public void addDescription(String description) {
		_addLiteral(description, this.description);
	}
	
	public void addDescriptionRef(String descriptionUrl) {
		_addReference(descriptionUrl, LiteralOrReference.class, this.description);
	}

	public List<LiteralOrReference> getFormats() {
		return format;
	}
	
	public void addFormat(String format) {
		_addLiteral(format, this.format);
	}
	
	public void addFormatRef(String url) {
		_addReference(url, LiteralOrReference.class, this.format);
	}

	public List<LiteralOrReference> getRights() {
		return rights;
	}
	
	public void addRights(String rights) {
		_addLiteral(rights, this.rights);
	}
	
	public void addRightsRef(String url) {
		_addReference(url, LiteralOrReference.class, this.rights);
	}

	public List<LiteralOrReference> getSources() {
		return source;
	}
	
	public void addSource(String source) {
		_addLiteral(source, this.source);
	}
	
	public void addSourceRef(String url) {
		_addReference(url, LiteralOrReference.class, this.source);
	}

	public List<LiteralOrReference> getConformsTo() {
		return conformsTo;
	}
	
	public void addConformsTo(String conformsTo) {
		_addLiteral(conformsTo, this.conformsTo);
	}
	
	public void addConformsToRef(String url) {
		_addReference(url, LiteralOrReference.class, this.conformsTo);
	}

	public List<LiteralOrReference> getCreated() {
		return created;
	}
	
	public void addCreated(String created) {
		_addLiteral(created, this.created);
	}
	
	public void addCreatedRef(String url) {
		_addReference(url, LiteralOrReference.class, this.created);
	}

	public List<ValuableResource> getExtent() {
		return extent;
	}
	
	public void addExtent(String extent) {
		_addValue(extent, this.extent);
	}

	public List<ReferenceableResource> getHasParts() {
		return hasPart;
	}
	
	public void addHasPart(String hasPartRef) {
		_addReference(hasPartRef, ReferenceableResource.class, this.hasPart);
	}

	public List<LiteralOrReference> getIsFormatOf() {
		return isFormatOf;
	}
	
	public void addIsFormatOf(String isFormatOf) {
		_addLiteral(isFormatOf, this.isFormatOf);
	}
	
	public void addIsFormatOfRef(String url) {
		_addReference(url, LiteralOrReference.class, this.isFormatOf);
	}

	public List<ReferenceableResource> getIsPartOf() {
		return isPartOf;
	}
	
	public void addIsPartOf(String url) {
		_addReference(url, ReferenceableResource.class, this.isPartOf);
	}

	public List<LiteralOrReference> getIssued() {
		return issued;
	}
	
	public void addIssued(String issued) {
		_addLiteral(issued, this.issued);
	}
	
	public void addIssuedRef(String url) {
		_addReference(url, LiteralOrReference.class, this.issued);
	}

	public List<ReferenceableResource> getIsNextInSequence() {
		return isNextInSequence;
	}
	
	public void addIsNextInSequence(String isNextInSequenceUrl) {
		_addReference(isNextInSequenceUrl, ReferenceableResource.class, this.isNextInSequence);
	}

	public ReferenceableResource getEdmRights() {
		return edmRights;
	}
	
	public void setEdmRights(String edmRightsUrl) {
		this.edmRights = new ReferenceableResource(edmRightsUrl);
	}

}
