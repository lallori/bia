/*
 * ProvidedCHO.java
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
 * The Provided Cultural Heritage Object.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@XmlRootElement(name = EDMConstants.EDM_PREFIX + ":" + EDMConstants.PROVIDED_CHO)
@XmlAccessorType(XmlAccessType.FIELD)
public class ProvidedCHO extends EDMResource {
	
	/* ###### DC_PREFIX ###### */
	
	/**
	 * Use for contributors to the CHO. If possible supply the identifier of the contributor from an authority source.
	 * Providers with richer role terms can elect to map a subset to dc:contributor and others to dc:creator.<br/>
	 * Repeat for multiple contributors.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:contributor&gt;Maria Callas &lt;/dc:contributor&gt;</code><br/>
	 * or create a reference to an instance of the Agent class<br/>
	 * <code>&lt;dc:contributor rdf:resource="http://www.example.com/MariaCallas"/&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":contributor", type = LiteralOrReference.class)
	private List<LiteralOrReference> contributor = new ArrayList<LiteralOrReference>();
	
	/**
	 * The spatial or temporal topic of the CHO. Use the more precise dcterms:spatial or dcterms:temporal 
	 * properties if the data will support it.<br/>
	 * One of dc:coverage or dc:subject or dc:type or dcterms:spatial <b>must</b> be provided.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:coverage&gt;1995-1996&lt;/dc:coverage&gt;</code> or <code>&lt;dc:coverage&gt;Berlin&lt;/dc:coverage&gt;</code><br/>
	 * or create a reference to an instance of a contextual class, for example, a Place class<br/>
	 * <code>&lt;dc:coverage rdf:resource="http://sws.geonames.org/2950159"/&gt;</code> 
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":coverage", type = LiteralOrReference.class)
	private List<LiteralOrReference> coverage = new ArrayList<LiteralOrReference>();
	
	/**
	 * For the creator of the CHO. If possible supply the identifier of the creator from an authority source.<br/>
	 * Repeat for multiple creators.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:creator&gt;Shakespeare, William&lt;/dc:creator&gt;</code><br/>
	 * or create a reference to an instance of the Agent class<br/>
	 * <code>&lt;dc:creator rdf:resource="http://viaf.org/viaf/96994048"/&gt;</code> 
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":creator", type = LiteralOrReference.class)
	private List<LiteralOrReference> creator = new ArrayList<LiteralOrReference>();
	
	/**
	 * Use for a significant date in the life of the CHO. Consider using the sub-properties of dcterms:created or 
	 * dcterms:issued.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:date&gt;Early 20th century&lt;/dc:date&gt;</code> or <code>&lt;dc:date&gt;1919&lt;/dc:date&gt;</code>
	 * <br/>
	 * or create a reference to an instance of the TimeSpan class<br/>
	 * <code>&lt;dc:date rdf:resource="http://semium.org/time/19xx_1_third"/&gt;</code> 
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":date", type = LiteralOrReference.class)
	private List<LiteralOrReference> date = new ArrayList<LiteralOrReference>();
	
	/**
	 * A description of the CHO. Either dc:description or dc:title <b>must</b> be provided.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:description&gt;Illustrated guide to airport markings and lighting signals, with particular reference to SMGCS
	 * (Surface Movement Guidance and Control System) for airports with low visibility conditions.&lt;/dc:description&gr;</code>
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":description", type = LanguageResourceOrReference.class)
	private List<LanguageResourceOrReference> description = new ArrayList<LanguageResourceOrReference>();
	
	/**
	 * Use for the terms generally applied to indicate the format of the cultural heritage object or the file format of 
	 * a born digital object.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dc:format&gt;paper&lt;/dc:format&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":format", type = LiteralOrReference.class)
	private List<LiteralOrReference> format = new ArrayList<LiteralOrReference>();
	
	/**
	 * An identifier of the original CHO.<br/>
	 * Example:<br/>
	 * <code>&lt;dc:identifier&gt;urn:isbn:9780387097466&lt;/dc:identifier&gt;</code>  
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":identifier", type = ValuableResource.class)
	private List<ValuableResource> identifier = new ArrayList<ValuableResource>();
	
	/**
	 * The language of text CHOs and also for other types of CHO if there is a language aspect. <br/>
	 * Mandatory for <b>TEXT</b> objects, strongly recommended for other object types with a language element. <br/>
	 * Best practice is to use ISO 639 two - or three - letter primary language tags.<br/>
	 * Repeat for multiple languages.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dc:language&gt;it&lt;/dc:language&gt;</code> 
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":language", type = ValuableResource.class)
	private List<ValuableResource> language = new ArrayList<ValuableResource>();
	
	/**
	 * The name of the publisher of the CHO. If possible supply the identifier of the publisher from an authority 
	 * source.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:publisher&gt;Oxford University Press&lt;/dc:publisher&gt;</code><br/>
	 * or create a reference to an instance of the Agent class<br/>
	 * <code>&lt;dc:publisher rdf:resource="http://www.oup.com/"/&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":publisher", type = LiteralOrReference.class)
	private List<LiteralOrReference> publisher = new ArrayList<LiteralOrReference>();
	
	/**
	 * The name or identifier of a related resource, generally used for other related CHOs. Cf edm:isRelatedTo.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:relation&gt;maps.crace.1/33&lt;/dc:relation&gt;</code> (Shelf mark)<br/>
	 * Or to provide a link to another object:<br/>
	 * <code>&lt;dc:relation rdf:resource="http://www.identifier/relatedObject"/&gt;</code> 
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":relation", type = LiteralOrReference.class)
	private List<LiteralOrReference> relation = new ArrayList<LiteralOrReference>();
	
	/**
	 * Use to give the name of the rights holder of the CHO if possible or for more general rights information.<br/>
	 * (Note that the controlled edm:rights property relates to the digital objects and applies to the edm:WebResource
	 * and/or edm:Aggregation).<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:rights&gt;Copyright © British Library Board&lt;/dc:rights&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":rights", type = LiteralOrReference.class)
	private List<LiteralOrReference> rights = new ArrayList<LiteralOrReference>();
	
	/**
	 * A related resource from which the described resource is derived in whole or in part i.e. the source of the 
	 * original CHO.  (Not the name of the content holder: for this see edm:dataProvider.)<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dc:source&gt;Security Magazine pp 3-12&lt;/dc:source&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":source", type = LiteralOrReference.class)
	private List<LiteralOrReference> source = new ArrayList<LiteralOrReference>();
	
	/**
	 * The subject of the CHO. One of dc:subject or dc:coverage or dc:type or dcterms:spatial <b>must</b> be 
	 * provided.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:subject&gt;trombone&lt;/dc:subject&gt;</code><br/>
	 * or create a reference to an instance of the Concept class<br/>
	 * <code>&lt;dc :subject rdf:resource="http://id.loc.gov/authorities/subjects/sh85137992"/&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":subject", type = LiteralOrReference.class)
	private List<LiteralOrReference> subject = new ArrayList<LiteralOrReference>();
	
	/**
	 * The title of the CHO. One of dc:subject or dc:coverage or dc:type or dcterms:spatial <b>must</b> be 
	 * provided.<br/>
	 * Each translations of the title has to be provided using appropriate language attributes.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:title xml:lang="en"&gt;Eight Weeks&lt;/dc:title&gt;</code><br/>
	 * <code>&lt;dc:title xml:lang="it"&gt;Ocho semanas&lt;/dc:title&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":title", type = LanguageResource.class)
	private List<LanguageResource> title = new ArrayList<LanguageResource>();
	
	/**
	 * The nature or genre of the CHO. Ideally the term(s) will be taken from a controlled vocabulary. One of
	 * dc:type or dc:subject or dc:coverage or dcterms:spatial <b>must</b> be provided.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dc:type&gt;Book&lt;/dc:type&gt;</code> or <code>&lt;dc:type&gt;trombone&lt;/dc:type&gt;</code><br/>
	 * or create a reference to an instance of the Concept class<br/>
	 * <code>&lt;skos:Concept rdf:about="http://www.mimo-db.eu/HornbostelAndSachs/356/"&gt;</code> 
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":type", type = LiteralOrReference.class)
	private List<LiteralOrReference> dcType = new ArrayList<LiteralOrReference>();
	
	/* ###### DCTERMS_PREFIX ###### */
	
	/**
	 * Any alternative title of the CHO including abbreviations or translations.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:alternative xml:lang="en"&gt;Eight weeks: a novel&lt;/dcterms:alternative&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":alternative", type = LanguageResource.class)
	private List<LanguageResource> alternative = new ArrayList<LanguageResource>();
	
	/**
	 * An established standard to which the CHO conforms.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:conformsTo&gt;W3C WCAG 2.0&lt;/dcterms:conformsTo&gt;</code> (conforms to web content accessibility
	 * guidelines).<br/>
	 * Or link to the resource<br/>
	 * <code>&lt;dcterms:conformsTo rdf:resource="http://www.w3.org/TR/WCAG/" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":conformsTo", type = LiteralOrReference.class)
	private List<LiteralOrReference> conformsTo = new ArrayList<LiteralOrReference>();
	
	/**
	 * The date of creation of the CHO.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:created&gt;Mid 16th century&lt;/dcterms:created&gt;</code> or 
	 * <code>&lt;dcterms:created&gt;1584&lt;/dcterms:created&gt;</code><br/>
	 * or create a reference to an instance of the TimeSpan class<br/>
	 * <code>&lt;dc:date rdf:resource="http://semium.org/time/15xx_3_third"/&gt;</code> 

	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":created", type = LiteralOrReference.class)
	private List<LiteralOrReference> created = new ArrayList<LiteralOrReference>();
	
	/**
	 * The size or duration of the CHO.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:extent&gt;13 cm&lt;/dcterms:extent&gt;</code> (the width of an original object).<br/>
	 * <code>&lt;dcterms:extent&gt;34 minutes&lt;/dcterms:extent&gt;</code> (the duration of an audio file).
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":extent", type = ValuableResource.class)
	private List<ValuableResource> extent = new ArrayList<ValuableResource>();
	
	/**
	 * A resource related to the CHO that is substantially the same as the CHO but in another format.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:hasFormat&gt;http://upload.wikimedia.org/wikipedia/en/f/f3/Europeana_logo.png&lt;/dcterms:hasFormat&gt;</code>
	 * for a png image file of the described tiff resource<br/>
	 * Or as a link to a resource<br/>
	 * <code>&lt;dcterms:hasFormat rdf:resource="http://upload.wikimedia.org/wikipedia/en/f/f3/Europeana_logo.png" /&gt;</code> 
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":hasFormat", type = LiteralOrReference.class)
	private List<LiteralOrReference> hasFormat = new ArrayList<LiteralOrReference>();
	
	/**
	 * A resource that is included either physically or logically in the CHO.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:hasPart&gt;Vol.2. Issue 1&lt;/dcterms:hasPart&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":hasPart", type = LiteralOrReference.class)
	private List<LiteralOrReference> hasPart = new ArrayList<LiteralOrReference>();
	
	/**
	 * Another, later resource that is a version, edition or adaptation of the CHO demonstrating substantive changes
	 * in content rather than format.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:hasVersion&gt;The Sorcerer's Apprentice (translation by Edwin Zeydel, 1955)&lt;/dcterms:hasVersion&gt;</code><br/>
	 * In this example the 1955 translation is a version of the described resource.
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":hasVersion", type = LiteralOrReference.class)
	private List<LiteralOrReference> hasVersion = new ArrayList<LiteralOrReference>();
	
	/**
	 * Another resource that is substantially the same as the CHO but in another format.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:isFormatOf&gt;Europeana_logo.tiff&lt;/dcterms:isFormatOf&gt;</code><br/>
	 * where the resource being described is a png image file. 
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":isFormatOf", type = LiteralOrReference.class)
	private List<LiteralOrReference> isFormatOf = new ArrayList<LiteralOrReference>();
	
	/**
	 * A resource in which the CHO is physically or logically included. This property can be used for objects that are
	 * part of a hierarchy and will be used to support an appropriate display in the portal. For that purpose it will be
	 * necessary to supply a reference as the value.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:isPartOf&gt;Crace Collection of Maps of London&lt;/dcterms:isPartOf&gt;</code><br/>
	 * Or link to parent object if part of a hierarchy of CHOs<br/>
	 * <code>&lt;dcterms:isPartOf rdf:resource="http://data.europeana.eu/item/08701/1B0BACAA44D5A807E43D9B411C9781AAD2F96E65" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":isPartOf", type = LiteralOrReference.class)
	private List<LiteralOrReference> isPartOf = new ArrayList<LiteralOrReference>();
	
	/**
	 * Another resource that references, cites or otherwise points to the CHO.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:isReferencedBy&gt;Till, Nicholas (1994) <i>Mozart and the Enlightenment: Truth, Virtue and Beauty
	 * in Mozart's Operas</i>, W. W. Norton & Company &lt;/dcterms:isReferencedBy&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":isReferencedBy", type = LiteralOrReference.class)
	private List<LiteralOrReference> isReferencedBy = new ArrayList<LiteralOrReference>();
	
	/**
	 * Another resource that supplants, displaces, or supersedes the CHO.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:isReplacedBy&gt;http://dublincore.org/about/2009/01/05/bylaws/&lt;/dcterms:isReplacedBy&gt;</code><br/>
	 * where the resource described is an older version (http://dublincore.org/about/2006/01/01/bylaws/)<br/>
	 * or link<br/>
	 * <code>&lt;dcterms:isReplacedBy rdf:resource="http://dublincore.org/about/2009/01/05/bylaws/" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":isReplacedBy", type = LiteralOrReference.class)
	private List<LiteralOrReference> isReplacedBy = new ArrayList<LiteralOrReference>();
	
	/**
	 * Another related resource that requires the CHO to support its function, delivery or coherence.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;isRequiredBy&gt;http://www.myslides.com/myslideshow.ppt&lt;/isRequiredBy&gt;</code><br/>
	 * where the image being described is required for an online slideshow. 
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":isRequiredBy", type = LiteralOrReference.class)
	private List<LiteralOrReference> isRequiredBy = new ArrayList<LiteralOrReference>();
	
	/**
	 * Date of formal issuance or publication of the CHO.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:issued&gt;1993&lt;/dcterms:issued&gt;</code><br/>
	 * or create a reference to an instance of the TimeSpan class<br/>
	 * <code>&lt;dcterms:issued rdf:resource="http://semium.org/time/17xx_3_third" /&gt;</code> (late 18th century)
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":issued", type = LiteralOrReference.class)
	private List<LiteralOrReference> issued = new ArrayList<LiteralOrReference>();
	
	/**
	 * Another, earlier resource of which the CHO is a version, edition or adaptation, demonstrating substantive
	 * changes in content rather than format.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:isVersionOf&gt;The Sorcerer's Apprentice&lt;dcterms:isVersionOf&gt;</code><br/>
	 * In this example The Sorcerer's Apprentice (translation by Edwin Zeydel, 1955) is the resource being described. 
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":isVersionOf", type = LiteralOrReference.class)
	private List<LiteralOrReference> isVersionOf = new ArrayList<LiteralOrReference>();
	
	/**
	 * The material or physical carrier of the CHO.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:medium&gt;metal&lt;/dcterms:medium&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":medium", type = LiteralOrReference.class)
	private List<LiteralOrReference> medium = new ArrayList<LiteralOrReference>();
	
	
	/**
	 * A statement of changes in ownership and custody of the CHO since its creation. Significant for authenticity, 
	 * integrity and interpretation.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:provenance&gt;Donated to The National Library in 1965&lt;/dcterms:provenance&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":provenance", type = LiteralOrReference.class)
	private List<LiteralOrReference> provenance = new ArrayList<LiteralOrReference>();
	
	/**
	 * Other resources referenced, cited or otherwise pointed to by the CHO.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:references&gt;Honderd jaar Noorse schilderkunst &lt;/dcterms:references&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":references", type = LiteralOrReference.class)
	private List<LiteralOrReference> references = new ArrayList<LiteralOrReference>();
	
	/**
	 * A related resource that is supplanted, displaced, or superseded by the CHO.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:replaces&gt;http://dublincore.org/about/2006/01/01/bylaws/&lt;/dcterms:replaces&gt;</code><br/>
	 * where the resource described is a newer version (http://dublincore.org/about/2009/01/05/bylaws/)<br/>
	 * or link to resource<br/>
	 * <code>&lt;dcterms:replaces rdf:resource="http://dublincore.org/about/2006/01/01/bylaws/" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":replaces", type = LiteralOrReference.class)
	private List<LiteralOrReference> replaces = new ArrayList<LiteralOrReference>();
	
	/**
	 * Another resource that is required by the described resource to support its function, delivery or coherence.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:requires&gt;http://ads.ahds.ac.uk/project/userinfo/css/oldbrowsers.css&lt;/dcterms:requires&gt;</code><br/>
	 * where the resource described is an HTML file at http://ads.ahds.ac.uk/project/userinfo/digitalTextArchiving.html
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":requires", type = LiteralOrReference.class)
	private List<LiteralOrReference> requires = new ArrayList<LiteralOrReference>();
	
	/**
	 * Spatial characteristics of the CHO. i.e. what the CHO represents or depicts in terms of space (e.g. a location, 
	 * co-ordinate or place). Either dcterms:spatial or dc:type or dc:subject or dc:coverage <b>must</b> be provided.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:spatial&gt;Portugal&lt;/dcterms:spatial&gt;</code><br/>
	 * or create a reference to an instance of the Place class<br/>
	 * <code>&lt;dcterms:spatial rdf:resource="http://sws.geonames.org/2264397/" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":spatial", type = LiteralOrReference.class)
	private List<LiteralOrReference> spatial = new ArrayList<LiteralOrReference>();
	
	/**
	 * A list of subVunits of the CHO.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;dcterms:tableOfContents&gt;Chapter 1. Introduction, Chapter 2. History &lt;/dcterms:tableOfContents&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":tableOfContents", type = ValuableResource.class)
	private List<ValuableResource> tableOfContents = new ArrayList<ValuableResource>();
	
	/**
	 * Temporal characteristics of the CHO. i.e. what the CHO is about or depicts in terms of time (e.g. a period, date 
	 * or date range).<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;dcterms:temporal&gt;Roman Empire&lt;/dcterms:temporal&gt;</code><br/>
	 * or create a reference to an instance of the TimeSpan class<br/>
	 * <code>&lt;dcterms:temporal rdf:resource="http://semium.org/time/roman_empire" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.DCTERMS_PREFIX + ":temporal", type = LiteralOrReference.class)
	private List<LiteralOrReference> temporal = new ArrayList<LiteralOrReference>();
	
	/* ###### EDM_PREFIX ###### */
	
	/**
	 * The geographic location whose boundaries presently include the CHO. If the name of a repository, building,
	 * site, or other entity is used then it should include an indication of its geographic location.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:currentLocation rdf:resource="http://sws.geonames.org/2950159" /&gt;</code> (Identifier for Berlin) 
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":currentLocation", type = ReferenceableResource.class)
	private ReferenceableResource currentLocation;
	
	/**
	 * The identifier of an agent, a place, a time period or any other identifiable entity that the CHO may have "met"
	 * in its life.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;edm:hasMet rdf:resource="http://viaf.org/viaf/96994048"/&gt;</code> (identifier for William Shakespeare)<br/>
	 * <code>&lt;edm:hasMet rdf:resource="http://sws.geonames.org/6620265"/&gt;</code> (location identifier for Shakespeare's Globe theatre)
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":hasMet", type = ReferenceableResource.class)
	private List<ReferenceableResource> hasMet  = new ArrayList<ReferenceableResource>();
	
	/**
	 * The identifier of a concept, or a word or phrase from a controlled vocabulary (thesaurus etc) giving the type 
	 * of the CHO. E.g. Painting from the AAT thesaurus. This property can be seen as a super-property of e.g.
	 * dc:format or dc:type to support "What" questions.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:hasType&gt;Painting&lt;/edm:hasType&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":hasType", type = LiteralOrReference.class)
	private List<LiteralOrReference> hasType  = new ArrayList<LiteralOrReference>();
	
	/**
	 * The identifier of another resource that is incorporated in the described CHO. E.g. the movie "A Clockwork 
	 * Orange" incorporates Rossini's "La Gazza Ladra" in its soundtrack.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:incorporates rdf:resource="http://www.identifier/IncorporatedResource" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":incorporates", type = ReferenceableResource.class)
	private List<ReferenceableResource> incorporates  = new ArrayList<ReferenceableResource>();
	
	/**
	 * The identifier of another resource from which the described CHO has been derived. E.g. the identifier of Moby 
	 * Dick when the Italian translation is the described CHO.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:isDerivativeOf rdf:resource="http://www.identifier/SourceResource" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":isDerivativeOf", type = ReferenceableResource.class)
	private List<ReferenceableResource> isDerivativeOf  = new ArrayList<ReferenceableResource>();
	
	/**
	 * The identifier of the preceding object where both objects are part of the same overall resource. Use this for 
	 * objects that are part of a hierarchy or sequence to ensure correct display in the portal.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:isNextInSequence rdf:resource="http://www.identifier/PrecedingResource" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":isNextInSequence", type = ReferenceableResource.class)
	private List<ReferenceableResource> isNextInSequence  = new ArrayList<ReferenceableResource>();
	
	/**
	 * The identifier or name of a concept or other resource to which the described CHO is related.  E.g. Moby Dick 
	 * is related to XIX Century literature. Cf dc:relation.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;edm:isRelatedTo&gt;Literature&lt;/edm:isRelatedTo&gt;</code><br/>
	 * Or link to resource<br/>
	 * <code>&lt;edm:isRelatedTo rdf:resource="http://www.eionet.europa.eu/gemet/concept?cp=4850"/></code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":isRelatedTo", type = LiteralOrReference.class)
	private List<LiteralOrReference> isRelatedTo  = new ArrayList<LiteralOrReference>();
	
	/**
	 * The identifier of another object of which the described CHO is a representation. E.g. the identifier of the
	 * statue when the CHO being described is a painting of that statue.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:isRepresentativeOf rdf:resource="http://www.identifier/RepresentedResource" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":isRepresentativeOf", type = ReferenceableResource.class)
	private ReferenceableResource isRepresentativeOf;
	
	/**
	 * The identifier of another resource to which the described CHO is similar.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:isSimilarTo rdf:resource="http://www.identifier/SimilarResource" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":isSimilarTo", type = ReferenceableResource.class)
	private List<ReferenceableResource> isSimilarTo = new ArrayList<ReferenceableResource>();
	
	/**
	 * The identifier of a resource to which the described CHO is a successor. E.g. "The Two Towers" is a successor
	 * of "Fellowship of the Ring".<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:isSuccessorOf rdf:resource="http://dbpedia.org/resource/The_Fellowship_of_the_Ring" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":isSuccessorOf", type = ReferenceableResource.class)
	private List<ReferenceableResource> isSuccessorOf = new ArrayList<ReferenceableResource>();
	
	/**
	 * If the CHO described is of type edm:PhysicalThing it may realize an information object. E.g. a copy of the
	 * Gutenberg publication realizes the Bible.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:realizes rdf:resource="http://www.identifier/PhysicalThing" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":realizes", type = ReferenceableResource.class)
	private List<ReferenceableResource> realizes = new ArrayList<ReferenceableResource>();
	
	/**
	 * The value must be one of the types accepted by Europeana as it will support portal functionality:
	 * TEXT, VIDEO, SOUND, IMAGE, 3D.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;edm:type&gt;IMAGE&lt;/edm:type&gt;</code> (upperVcase)<br/>
	 * <code>&lt;edm:type&gt;3D&lt;/edm:type&gt;</code> (upperVcase)
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":type", type = ValuableResource.class)
	private ValuableResource type;
	
	/**
	 * Included only for backward compatibility with ESE.  
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":unstored", type = ValuableResource.class)
	private List<ValuableResource> unstored = new ArrayList<ValuableResource>();
	
	/* ###### OWL_PREFIX ###### */
	
	/**
	 * Use to point to your own (linked data) representation of the object, if you have already minted a URI 
	 * identifier for it. It is also possible to provide URIs minted by thirdVparties for the object.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;owl:sameAs rdf:resource="http://www.identifier/SameResourceElsewhere"/&gt;</code>
	 */
	@XmlElement(name = EDMConstants.OWL_PREFIX + ":sameAs", type = ReferenceableResource.class)
	private List<ReferenceableResource> sameAs = new ArrayList<ReferenceableResource>();
	
	public ProvidedCHO() {
	}

	public List<LiteralOrReference> getContributors() {
		return contributor;
	}
	
	public void addContributor(String contributor) {
		_addLiteral(contributor, this.contributor);
	}
	
	public void addContributorRef(String url) {
		_addReference(url, LiteralOrReference.class, this.contributor);
	}

	public List<LiteralOrReference> getCoverages() {
		return coverage;
	}
	
	public void addCoverage(String coverage) {
		_addLiteral(coverage, this.coverage);
	}
	
	public void addCoverageRef(String url) {
		_addReference(url, LiteralOrReference.class, this.coverage);
	}

	public List<LiteralOrReference> getCreators() {
		return creator;
	}
	
	public void addCreator(String creator) {
		_addLiteral(creator, this.creator);
	}
	
	public void addCreatorRef(String url) {
		_addReference(url, LiteralOrReference.class, this.creator);
	}

	public List<LiteralOrReference> getDates() {
		return date;
	}
	
	public void addDate(String date) {
		_addLiteral(date, this.date);
	}
	
	public void addDateRef(String url) {
		_addReference(url, LiteralOrReference.class, this.date);
	}

	public List<LanguageResourceOrReference> getDescriptions() {
		return description;
	}
	
	public void addDescription(String lang, String description) {
		_addLanguageValue(lang, description, LanguageResourceOrReference.class, this.description);
	}
	
	public void addDescriptionRef(String url) {
		_addReference(url, LanguageResourceOrReference.class, this.description);
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
	
	public List<ValuableResource> getIdentifiers() {
		return identifier;
	}
	
	public void addIdentifier(String identifier) {
		_addValue(identifier, this.identifier);
	}
	
	public List<ValuableResource> getLanguages() {
		return language;
	}
	
	public void addLanguage(String language) {
		_addValue(language, this.language);
	}

	public List<LiteralOrReference> getPublishers() {
		return publisher;
	}
	
	public void addPublisher(String publisher) {
		_addLiteral(publisher, this.publisher);
	}
	
	public void addPublisherRef(String url) {
		_addReference(url, LiteralOrReference.class, this.publisher);
	}

	public List<LiteralOrReference> getRelations() {
		return relation;
	}
	
	public void addRelation(String relation) {
		_addLiteral(relation, this.relation);
	}
	
	public void addRelationRef(String url) {
		_addReference(url, LiteralOrReference.class, this.relation);
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

	public List<LiteralOrReference> getSubjects() {
		return subject;
	}
	
	public void addSubject(String subject) {
		_addLiteral(subject, this.subject);
	}
	
	public void addSubjectRef(String url) {
		_addReference(url, LiteralOrReference.class, this.subject);
	}

	public List<LanguageResource> getTitles() {
		return title;
	}
	
	public void addTitle(String lang, String title) {
		_addLanguageValue(lang, title, LanguageResource.class, this.title);
	}

	public List<LiteralOrReference> getDcTypes() {
		return dcType;
	}
	
	public void addDcType(String dcType) {
		_addLiteral(dcType, this.dcType);
	}
	
	public void addDcTypeRef(String url) {
		_addReference(url, LiteralOrReference.class, this.dcType);
	}

	public List<LanguageResource> getAlternatives() {
		return alternative;
	}
	
	public void addAlternative(String lang, String alternative) {
		_addLanguageValue(lang, alternative, LanguageResource.class, this.alternative);
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

	public List<LiteralOrReference> getHasFormats() {
		return hasFormat;
	}
	
	public void addHasFormat(String hasFormat) {
		_addLiteral(hasFormat, this.hasFormat);
	}
	
	public void addHasFormatRef(String url) {
		_addReference(url, LiteralOrReference.class, this.hasFormat);
	}

	public List<LiteralOrReference> getHasParts() {
		return hasPart;
	}
	
	public void addHasPart(String hasPart) {
		_addLiteral(hasPart, this.hasPart);
	}
	
	public void addHasPartRef(String url) {
		_addReference(url, LiteralOrReference.class, this.hasPart);
	}

	public List<LiteralOrReference> getHasVersions() {
		return hasVersion;
	}
	
	public void addHasVersion(String hasVersion) {
		_addLiteral(hasVersion, this.hasVersion);
	}
	
	public void addHasVersionRef(String url) {
		_addReference(url, LiteralOrReference.class, this.hasVersion);
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

	public List<LiteralOrReference> getIsPartOf() {
		return isPartOf;
	}
	
	public void addIsPartOf(String isPartOf) {
		_addLiteral(isPartOf, this.isPartOf);
	}
	
	public void addIsPartOfRef(String url) {
		_addReference(url, LiteralOrReference.class, this.isPartOf);
	}

	public List<LiteralOrReference> getIsReferencedBy() {
		return isReferencedBy;
	}
	
	public void addIsReferencedBy(String isReferencedBy) {
		_addLiteral(isReferencedBy, this.isReferencedBy);
	}
	
	public void addIsReferencedByRef(String url) {
		_addReference(url, LiteralOrReference.class, this.isReferencedBy);
	}

	public List<LiteralOrReference> getIsReplacedBy() {
		return isReplacedBy;
	}
	
	public void addIsReplacedBy(String isReplacedBy) {
		_addLiteral(isReplacedBy, this.isReplacedBy);
	}
	
	public void addIsReplacedByRef(String url) {
		_addReference(url, LiteralOrReference.class, this.isReplacedBy);
	}

	public List<LiteralOrReference> getIsRequiredBy() {
		return isRequiredBy;
	}
	
	public void addIsRequiredBy(String isRequiredBy) {
		_addLiteral(isRequiredBy, this.isRequiredBy);
	}
	
	public void addIsRequiredByRef(String url) {
		_addReference(url, LiteralOrReference.class, this.isRequiredBy);
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

	public List<LiteralOrReference> getIsVersionOf() {
		return isVersionOf;
	}
	
	public void addIsVersionOf(String isVersionOf) {
		_addLiteral(isVersionOf, this.isVersionOf);
	}
	
	public void addIsVersionOfRef(String url) {
		_addReference(url, LiteralOrReference.class, this.isVersionOf);
	}

	public List<LiteralOrReference> getMediums() {
		return medium;
	}
	
	public void addMedium(String medium) {
		_addLiteral(medium, this.medium);
	}
	
	public void addMediumRef(String url) {
		_addReference(url, LiteralOrReference.class, this.medium);
	}

	public List<LiteralOrReference> getProvenances() {
		return provenance;
	}
	
	public void addProvenance(String provenance) {
		_addLiteral(provenance, this.provenance);
	}
	
	public void addProvenanceRef(String url) {
		_addReference(url, LiteralOrReference.class, this.provenance);
	}

	public List<LiteralOrReference> getReferences() {
		return references;
	}
	
	public void addReferences(String references) {
		_addLiteral(references, this.references);
	}
	
	public void addReferencesRef(String url) {
		_addReference(url, LiteralOrReference.class, this.references);
	}

	public List<LiteralOrReference> getReplaces() {
		return replaces;
	}
	
	public void addReplaces(String replaces) {
		_addLiteral(replaces, this.replaces);
	}
	
	public void addReplacesRef(String url) {
		_addReference(url, LiteralOrReference.class, this.replaces);
	}

	public List<LiteralOrReference> getRequires() {
		return requires;
	}
	
	public void addRequires(String requires) {
		_addLiteral(requires, this.requires);
	}
	
	public void addRequiresRef(String url) {
		_addReference(url, LiteralOrReference.class, this.requires);
	}

	public List<LiteralOrReference> getSpatials() {
		return spatial;
	}
	
	public void addSpatial(String spatial) {
		_addLiteral(spatial, this.spatial);
	}
	
	public void addSpatialRef(String url) {
		_addReference(url, LiteralOrReference.class, this.spatial);
	}

	public List<ValuableResource> getTableOfContents() {
		return tableOfContents;
	}
	
	public void addTableOfContents(String tableOfContents) {
		_addValue(tableOfContents, this.tableOfContents);
	}

	public List<LiteralOrReference> getTemporals() {
		return temporal;
	}
	
	public void addTemporal(String temporal) {
		_addLiteral(temporal, this.temporal);
	}
	
	public void addTemporalRef(String url) {
		_addReference(url, LiteralOrReference.class, this.temporal);
	}

	public ReferenceableResource getCurrentLocation() {
		return currentLocation;
	}
	
	public void setCurrentLocation(String currentLocationUrl) {
		this.currentLocation = new ReferenceableResource(currentLocationUrl);
	}

	public List<ReferenceableResource> getHasMet() {
		return hasMet;
	}
	
	public void addHasMet(String hasMetUrl) {
		_addReference(hasMetUrl, ReferenceableResource.class, this.hasMet);
	}

	public List<LiteralOrReference> getHasTypes() {
		return hasType;
	}
	
	public void addHasType(String hasType) {
		_addLiteral(hasType, this.hasType);
	}
	
	public void addHasTypeRef(String url) {
		_addReference(url, LiteralOrReference.class, this.hasType);
	}

	public List<ReferenceableResource> getIncorporates() {
		return incorporates;
	}
	
	public void addIncorporates(String incorporatesUrl) {
		_addReference(incorporatesUrl, ReferenceableResource.class, this.incorporates);
	}

	public List<ReferenceableResource> getIsDerivativeOf() {
		return isDerivativeOf;
	}
	
	public void addIsDerivativeOf(String isDerivativeOfUrl) {
		_addReference(isDerivativeOfUrl, ReferenceableResource.class, this.isDerivativeOf);
	}

	public List<ReferenceableResource> getIsNextInSequence() {
		return isNextInSequence;
	}
	
	public void addIsNextInSequence(String isNextInSequenceUrl) {
		_addReference(isNextInSequenceUrl, ReferenceableResource.class, this.isNextInSequence);
	}

	public List<LiteralOrReference> getIsRelatedTo() {
		return isRelatedTo;
	}
	
	public void addIsRelatedTo(String isRelatedTo) {
		_addLiteral(isRelatedTo, this.isRelatedTo);
	}
	
	public void addIsRelatedToRef(String url) {
		_addReference(url, LiteralOrReference.class, this.isRelatedTo);
	}

	public ReferenceableResource getIsRepresentativeOf() {
		return isRepresentativeOf;
	}
	
	public void setIsRepresentativeOf(String isRepresentativeOfUrl) {
		this.isRepresentativeOf = new ReferenceableResource(isRepresentativeOfUrl);
	}

	public List<ReferenceableResource> getIsSimilarTo() {
		return isSimilarTo;
	}
	
	public void addIsSimilarTo(String isSimilarToUrl) {
		_addReference(isSimilarToUrl, ReferenceableResource.class, this.isSimilarTo);
	}

	public List<ReferenceableResource> getIsSuccessorOf() {
		return isSuccessorOf;
	}
	
	public void addIsSuccessorOf(String isSuccessorOfUrl) {
		_addReference(isSuccessorOfUrl, ReferenceableResource.class, this.isSuccessorOf);
	}

	public List<ReferenceableResource> getRealizes() {
		return realizes;
	}
	
	public void addRealizes(String realizesUrl) {
		_addReference(realizesUrl, ReferenceableResource.class, this.realizes);
	}

	public ValuableResource getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = new ValuableResource(type);
	}

	public List<ValuableResource> getUnstoreds() {
		return unstored;
	}
	
	public void addUnstored(String unstored) {
		_addValue(unstored, this.unstored);
	}

	public List<ReferenceableResource> getSameAs() {
		return sameAs;
	}
	
	public void addSameAs(String sameAsUrl) {
		_addReference(sameAsUrl, ReferenceableResource.class, this.sameAs);
	}

}
