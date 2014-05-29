/*
 * Aggregation.java
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
 * The Aggregation between a {@link ProvidedCHO} and a {@link WebResource}.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@XmlRootElement(name = EDMConstants.ORE_PREFIX + ":" + EDMConstants.AGGREGATION)
@XmlAccessorType(XmlAccessType.FIELD)
public class Aggregation extends EDMResource {
	
	/* ###### DC_PREFIX ###### */
	
	/**
	 * Ideally this should be applied to the edm:WebResource or the edm:ProvidedCHO. It is included here for 
	 * the conversion of data from ESE where it is not known which object the rights apply to.
	 */
	@XmlElement(name = EDMConstants.DC_PREFIX + ":rights", type = ValuableResource.class)
	private List<ValuableResource> rights = new ArrayList<ValuableResource>();
	
	/* ###### EDM_PREFIX ###### */
	
	/**
	 * The identifier of the source object e.g. the Mona Lisa itself. This could be a full linked open data URI or an 
	 * internal identifier.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:aggregatedCHO rdf resource="#UEDIN:214" /&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":aggregatedCHO", type = ReferenceableResource.class)
	private ReferenceableResource aggregatedCHO;
	
	/**
	 * The name or identifier of the data provider of the object (i.e. the organization providing data to an 
	 * aggregator). Identifiers will not be available until Europeana has implemented its Organization profile.<br/><br/>
	 * Examples:<br/> 
	 * <code>&lt;edm:dataProvider&gt;Palais des Beaux Arts de Lille&lt;/edm:dataProvider&gt;</code><br/>
	 * Or as a link to a resource<br/>
	 * <code>&lt;edm:dataProvider rdf:resource="http://www.pba-lille.fr/"/&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":dataProvider", type = LiteralOrReference.class)
	private LiteralOrReference dataProvider;
	
	/**
	 * The URL of a web resource which is a digital representation of the CHO. This may be the source object 
	 * itself in the case of a born digital cultural heritage object.<br/><br/>
	 * edm:hasView should only be used where there are several views of the CHO and one (or both) of the 
	 * mandatory edm:isShownAt or edm:isShownBy properties have already been used. It is for cases where 
	 * one CHO has several views of the same object. (e.g. a shoe and a detail of the label of the shoe).<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;edm:hasView rdf:resource="http://www.mimoVdb.eu/media/UEDIN/VIDEO/0032195v.mpg"/&gt;</code><br/>
	 * <code>&lt;edm:hasView rdf:resource="http://www.mimoVdb.eu/media/UEDIN/AUDIO/0032195s.mp3"/&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":hasView", type = ReferenceableResource.class)
	private List<ReferenceableResource> hasView = new ArrayList<ReferenceableResource>();
	
	/**
	 * The URL of a web view of the object in full information context. Either edm:isShownAt or edm:isShownBy 
	 * is mandatory.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:isShownAt rdf:resource="http://www.mimoVdb.eu/UEDIN/214"/&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":isShownAt", type = ReferenceableResource.class)
	private ReferenceableResource isShownAt;
	
	/**
	 * The URL of a web view of the object. Either edm:isShownAt or edm:isShownBy is mandatory.!!<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:isShownBy rdf:resource="http://www.mimoVdb.eu/media/UEDIN/IMAGE/0032195c.jpg"/&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":isShownBy", type = ReferenceableResource.class)
	private ReferenceableResource isShownBy;
	
	/**
	 * The URL of a representation of the CHO which will be used for generating previews for use in the 
	 * Europeana portal.  This may be the same URL as edm:isShownBy. See Europeana -> Portal -> Image -> Guidelines 
	 * (<a href="http://pro.europeana.eu/technical-requirements">here</a>) for information regarding the specifications
	 * of previews.<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:object rdf:resource="http://www.mimo-db.eu/media/UEDIN/IMAGE/0032195c.jpg"/&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":object", type = ReferenceableResource.class)
	private ReferenceableResource object;
	
	/**
	 * The name or identifier of the provider of the object (i.e. the organization providing data directly to 
	 * Europeana). Identifiers will not be available until Europeana has implemented its Organization profile.<br/><br/>
	 * Examples:<br/>
	 * <code>&lt;edm:provider&gt;Geheugen van Nederland&lt;/edm:provider&gt;</code><br/>
	 * or link to a resource<br/>
	 * <code>&lt;edm:provider rdf:resource="http://www.geheugenvannederland.nl/"/&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":provider", type = LiteralOrReference.class)
	private LiteralOrReference provider;
	
	/**
	 * This is a mandatory property and the value given here should be the rights statement that applies to the 
	 * digital representation as given (for example) in edm:object or edm:isShownAt/By, when these resources 
	 * are not provided with their own edm:rights (see edm:rights documentation). The value for the rights 
	 * statement in this element is a URI taken from the set of those defined for use in Europeana 
	 * <a href="http://pro.europeana.eu/availableVrightsVstatements">here</a>.<br/><br/>
	 * The rights statement given in this property will also by default apply to the previews used in the portal 
	 * and will support portal search and display functionality.<br/><br/> 
	 * Where there are several web resources attached to one edm:ProvidedCHO the rights statement given 
	 * here will be regarded as the "reference" value for all the web resources. Therefore a suitable value 
	 * should be chosen with care if the rights statements vary between different resources. In fact in such 
	 * cases Europeana encourages the provision of separate rights statements for each individual web 
	 * resource. For example, a lowVresolution of a JPEG file could be CCVBY, while the high resolution version or 
	 * a video showing the object would be CCVBYVNC. In such cases the rights statements given for the 
	 * individual web resources would 'override' the one specified at the ore:Aggregation level. Any other 
	 * associated web resources would still be governed by the edm:rights of the ore:Aggregation.<br/><br/><br/>
	 * Examples:<br/>
	 * <code>&lt;edm:rights rdf:resource="http://creativecommons.org/publicdomain/mark/1.0/"/&gt;</code><br/>
	 * <code>&lt;edm:rights rdf:resource="http://www.europeana.eu/rights/rrVf/"/&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":rights", type = ReferenceableResource.class)
	private ReferenceableResource edmRights;
	
	/**
	 * This is a mandatory property for objects that are user generated or user created that have been collected 
	 * by crowdsourcing or project activity.  The property is used to identify such content and can only take the 
	 * value "true" (lower case).<br/><br/>
	 * Example:<br/>
	 * <code>&lt;edm:ugc&gt;true&lt;edm:ugc&gt;</code>
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":ugc", type = ValuableResource.class)
	private ValuableResource ugc;
	
	/**
	 * Included only for backward compatibility with ESE.  
	 */
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":unstored", type = ValuableResource.class)
	private List<ValuableResource> unstored = new ArrayList<ValuableResource>();

	
	
	public List<ValuableResource> getRights() {
		return rights;
	}
	
	public void addRights(String rights) {
		_addValue(rights, this.rights);
	}
	
	public ReferenceableResource getAggregatedCHO() {
		return aggregatedCHO;
	}

	public void setAggregatedCHO(String aggregatedCHO) {
		this.aggregatedCHO = new ReferenceableResource(aggregatedCHO);
	}

	public LiteralOrReference getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(String dataProvider) {
		LiteralOrReference lit = new LiteralOrReference();
		lit.setValue(dataProvider);
		this.dataProvider = lit;
	}
	
	public void setDataProviderRef(String dataProviderRef) {
		LiteralOrReference ref = new LiteralOrReference();
		ref.setUrl(dataProviderRef);
		this.dataProvider = ref;
	}

	public List<ReferenceableResource> getHasView() {
		return hasView;
	}
	
	public void addHasView(String hasViewRef) {
		_addReference(hasViewRef, ReferenceableResource.class, this.hasView);
	}

	public ReferenceableResource getIsShownAt() {
		return isShownAt;
	}

	public void setIsShownAt(String isShownAtRef) {
		this.isShownAt = new ReferenceableResource(isShownAtRef);
	}

	public ReferenceableResource getIsShownBy() {
		return isShownBy;
	}

	public void setIsShownBy(String isShownByRef) {
		this.isShownBy = new ReferenceableResource(isShownByRef);
	}

	public ReferenceableResource getObject() {
		return object;
	}

	public void setObject(String objectRef) {
		this.object = new ReferenceableResource(objectRef);
	}

	public LiteralOrReference getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		LiteralOrReference lit = new LiteralOrReference();
		lit.setValue(provider);
		this.provider = lit;
	}
	
	public void setProviderRef(String providerRef) {
		LiteralOrReference ref = new LiteralOrReference();
		ref.setUrl(providerRef);
		this.provider = ref;
	}

	public ReferenceableResource getEdmRights() {
		return edmRights;
	}

	public void setEdmRights(String edmRightsRef) {
		this.edmRights = new ReferenceableResource(edmRightsRef);
	}

	public ValuableResource getUgc() {
		return ugc;
	}

	public void setUgc(String ugc) {
		this.ugc = new ValuableResource(ugc);
	}

	public List<ValuableResource> getUnstored() {
		return unstored;
	}
	
	public void addUnstored(String unstored) {
		_addValue(unstored, this.unstored);
	}

}
