/*
 * EuropeanaResourceContainer.java
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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.medici.bia.common.europeana.EDMConstants;

/**
 * The output Europeana container.<br/>
 * It contains all namespaces definitions.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@XmlRootElement(name = "rdf:RDF")
@XmlAccessorType(XmlAccessType.FIELD)
public class EuropeanaResourceContainer {
	
	@XmlAttribute(name = "xmlns:" + EDMConstants.DC_PREFIX)
	private static final String dc = EDMConstants.DC;
	@XmlAttribute(name = "xmlns:" + EDMConstants.DCTERMS_PREFIX)
	private static final String dcterms = EDMConstants.DCTERMS;
	@XmlAttribute(name = "xmlns:" + EDMConstants.EDM_PREFIX)
	private static final String edm = EDMConstants.EDM;
	@XmlAttribute(name = "xmlns:" + EDMConstants.FOAF_PREFIX)
	private static final String foaf = EDMConstants.FOAF;
	@XmlAttribute(name = "xmlns:" + EDMConstants.OAI_PREFIX)
	private static final String oai = EDMConstants.OAI;
	@XmlAttribute(name = "xmlns:" + EDMConstants.ORE_PREFIX)
	private static final String ore = EDMConstants.ORE;
	@XmlAttribute(name = "xmlns:" + EDMConstants.OWL_PREFIX)
	private static final String owl = EDMConstants.OWL;
	@XmlAttribute(name = "xmlns:" + EDMConstants.RDAGR2_PREFIX)
	private static final String rdaGr2 = EDMConstants.RDAGR2;
	@XmlAttribute(name = "xmlns:" + EDMConstants.RDF_PREFIX)
	private static final String rdf = EDMConstants.RDF;
	@XmlAttribute(name = "xmlns:" + EDMConstants.SKOS_PREFIX)
	private static final String skos = EDMConstants.SKOS;
	@XmlAttribute(name = "xmlns:" + EDMConstants.WGS84_POS_PREFIX)
	private static final String wgs84_pos = EDMConstants.WGS84_POS;
	
	
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":" + EDMConstants.PROVIDED_CHO)
	private List<ProvidedCHO> providedCHOs = new ArrayList<ProvidedCHO>();
	@XmlElement(name = EDMConstants.EDM_PREFIX + ":" + EDMConstants.WEBRESOURCE)
	private List<WebResource> webResources = new ArrayList<WebResource>();
	@XmlElement(name = EDMConstants.ORE_PREFIX + ":" + EDMConstants.AGGREGATION)
	private List<Aggregation> aggregations = new ArrayList<Aggregation>();
	
	
	public EuropeanaResourceContainer() {
	}

	public List<ProvidedCHO> getProvidedCHOs() {
		return providedCHOs;
	}
	
	public void addCHO(ProvidedCHO cho) {
		providedCHOs.add(cho);
	}
	
	public List<WebResource> getWebResources() {
		return webResources;
	}
	
	public void addWebResource(WebResource webResource) {
		webResources.add(webResource);
	}
	
	public List<Aggregation> getAggregations() {
		return aggregations;
	}
	
	public void addAggregation(Aggregation aggregation) {
		aggregations.add(aggregation);
	}

}
