/*
 * EuropeanaConverter.java
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
package org.medici.bia.common.util;

import java.text.SimpleDateFormat;

import org.medici.bia.common.europeana.EuropeanaItem;
import org.medici.bia.common.europeana.model.Aggregation;
import org.medici.bia.common.europeana.model.ProvidedCHO;
import org.medici.bia.common.europeana.model.WebResource;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.People;

/**
 * This class is used to convert {@link Document} or {@link Image} to {@link EuropeanaItem}.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class EuropeanaConverter {
	
	/* AGGREGATION CONSTANTS */
	private static String AG_DATAPROVIDER = "http://www.medici.org/";
	private static String AG_PROVIDER = "Archivio di Stato di Firenze";
	private static String AG_RIGHTS = "TO BE DEFINED";
	
	/* PROVIDED CHO CONSTANTS */
	private static String CURRENT_LOCATION = "https://www.google.it/maps/place/Archivio+di+Stato/@43.769118,11.270559,17z/data=!3m1!4b1!4m2!3m1!1s0x0:0xf9fa78efe45dd564";
	private static String DOC_DCTYPE = "Manuscript";
	private static String DOC_EDMTYPE = "TEXT";
	private static String DOC_LANG = "it";
	private static String DOC_RIGHTS = "TO BE DEFINED";
	private static String IMG_EDMTYPE = "IMAGE";
	private static String SYN_LANG = "en";
	private static String EXTRACT_LANG = "it";
	
	/* WEB RESOURCE CONSTANTS */
	private static String WR_DOCFORMAT = "text/html";
	private static String WR_DOCRIGHTS = "TO BE DEFINED";
	private static String WR_IMGFORMAT = "image/tif";
	private static String WR_IMGRIGHTS = "TO BE DEFINED";
	private static String WR_EDMRIGHTS = "TO BE DEFINED";
	
	/* OTHER CONSTANTS */
	private static String CONTEXT = ApplicationPropertyManager.getApplicationProperty("website.contextPath");
	private static String DOMAIN = ApplicationPropertyManager.getApplicationProperty("website.domain");
	private static String FONDO = ApplicationPropertyManager.getApplicationProperty("schedone.fondo");
	private static String PROTOCOL = ApplicationPropertyManager.getApplicationProperty("website.protocol");
	private static String SHAREDDOC_SERVLET = "src/docbase/ShareDocument.do";
	private static String SHAREDIMAGE_SERVLET = "src/docbase/ShareImage.do";
	
	/**
	 * Converts a {@link Document} to an {@link EuropeanaItem}.
	 * 
	 * @param document the {@link Document} to convert
	 * @param relatedImageId the image identifier relative to the {@link Image} associated to the document (null if none)
	 * @return the {@link EuropeanaItem} created
	 */
	public EuropeanaItem toEuropeanaItem(Document document, Integer relatedImageId) {
		EuropeanaItem item = new EuropeanaItem(EuropeanaItem.ItemType.DOCUMENT);
		
		item.setCho(createDocumentCHO(document, relatedImageId));
		item.setWebResource(createDocumentWebResource(document));
		item.setAggregation(createAggregation(item.getCho(), item.getWebResource()));
		
		item.setItemComment("Serialization of document#" + document.getEntryId());
		
		return item;
	}
	
	/**
	 * Converts an {@link Image} to an {@link EuropeanaItem}.
	 * 
	 * @param image the {@link Image} to convert
	 * @return the {@link EuropeanaItem} created
	 */
	public EuropeanaItem toEuropeanaItem(Image image) {
		EuropeanaItem item = new EuropeanaItem(EuropeanaItem.ItemType.IMAGE);
		
		item.setCho(createImageCHO(image));
		item.setWebResource(createImageWebResource(image));
		item.setAggregation(createAggregation(item.getCho(), item.getWebResource()));
		
		item.setItemComment("Serialization of image#" + image.getImageId());
		
		return item;
	}
	
	/* Privates */
	
	/**
	 * Returns true if the provided person is not important (Not Important Person), i.e. it is null, not entered (code 198),
	 * not relevant (code 3905) or unidentifiable (code 9285).
	 * 
	 * @param person the person to check
	 * @return true if the person is a NIP, false otherwise
	 */
	private boolean checkNIP(People person) {
		return person != null && person.getPersonId() != 198 && person.getPersonId() != 3905 && person.getPersonId() != 9285;
	}
	
	/**
	 * Creates the {@link Aggregation} element of the {@link EuropeanaItem}
	 * 
	 * @param cho the {@link ProvidedCHO} to link to this element
	 * @param res the {@link WebResource} to link to this element
	 * @return the {@link Aggregation} created
	 */
	private Aggregation createAggregation(ProvidedCHO cho, WebResource res) {
		Aggregation agg = new Aggregation();
		
		agg.setReference("agg:" +  cho.getReference());
		
		agg.setAggregatedCHO(cho.getReference());
		
		agg.setDataProviderRef(AG_DATAPROVIDER);
		
		agg.setIsShownBy(res.getReference());
		
		agg.setProvider(AG_PROVIDER);
		
		agg.setEdmRights(AG_RIGHTS);
		
		return agg;
	}
	
	/**
	 * Creates the {@link ProvidedCHO} element of the {@link EuropeanaItem} for a {@link Document}.
	 * 
	 * @param document the {@link Document} to convert
	 * @param relatedImageId the image identifier if the document has an associated {@link Image}
	 * @return the {@link ProvidedCHO} created
	 */
	private ProvidedCHO createDocumentCHO(Document document, Integer relatedImageId) {
		ProvidedCHO cho = new ProvidedCHO();
		cho.setReference("doc#" + document.getEntryId());
		
		// title of CHO
		cho.addTitle("en", getTitle(document));
		
		// creator of CHO
		if (checkNIP(document.getSenderPeople())) {
			cho.addCreator(document.getSenderPeople().getMapNameLf());
		}
		
		// CHO date
		String documentDate = getDocumentDate(document);
		if (documentDate != null) {
			cho.addDate(documentDate);
		}
		
		// CHO descriptions
		String synopsis = getSynopsisOrExtract(document, true);
		if (synopsis != null) {
			cho.addDescription(SYN_LANG, "{Synopsis}: " + synopsis);
		}
		
		String extract = getSynopsisOrExtract(document, false);
		if (extract != null) {
			cho.addDescription(EXTRACT_LANG, "{Extract}: " + extract);
		}
		
		// CHO current location
		cho.setCurrentLocation(CURRENT_LOCATION);
		
		// CHO rights
		cho.addRights(DOC_RIGHTS);
		
		// CHO 'TEXT' edm:type and 'Manuscript' dc:type
		cho.setType(DOC_EDMTYPE);
		cho.addDcType(DOC_DCTYPE);
		
		// CHO language
		cho.addLanguage(DOC_LANG);
		
		// CHO isRelatedTO
		if (relatedImageId != null) {
			cho.addIsRelatedToRef("img#" + relatedImageId);
		}
		
		return cho;
	}
	
	/**
	 * Creates the {@link WebResource} element of the {@link EuropeanaItem} for a {@link Document}.
	 * 
	 * @param document the {@link Document} to convert
	 * @return the {@link WebResource} created
	 */
	private WebResource createDocumentWebResource(Document document) {
		WebResource res = new WebResource();
		res.setReference(getSharedUrl(document.getEntryId(), "entryId", SHAREDDOC_SERVLET));
		
		res.addDescription(getTitle(document));
		
		res.addFormat(WR_DOCFORMAT);
		
		res.addRights(WR_DOCRIGHTS);
		
		res.addCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(document.getDateCreated()));
		
		res.setEdmRights(WR_EDMRIGHTS);
		
		return res;
	}
	
	/**
	 * Creates the {@link ProvidedCHO} element of the {@link EuropeanaItem} for an {@link Image}.
	 * 
	 * @param image the {@link Image} to convert
	 * @return the {@link ProvidedCHO} created
	 */
	private ProvidedCHO createImageCHO(Image image) {
		ProvidedCHO cho = new ProvidedCHO();
		cho.setReference("img#" + image.getImageId());
		
		// image title
		cho.addTitle("en", getTitle(image));
		
		// image type
		cho.setType(IMG_EDMTYPE);
		
		// current location
		cho.setCurrentLocation(CURRENT_LOCATION);
		
		return cho;
	}
	
	/**
	 * Creates the {@link WebResource} element of the {@link EuropeanaItem} for an {@link Image}.
	 * 
	 * @param image the {@link Image} to convert
	 * @return the {@link WebResource} created
	 */
	private WebResource createImageWebResource(Image image) {
		WebResource res = new WebResource();
		res.setReference(getSharedUrl(image.getImageId(), "imageId", SHAREDIMAGE_SERVLET));
		
		res.addDescription(getTitle(image));
		
		res.addFormat(WR_IMGFORMAT);
		
		res.addRights(WR_IMGRIGHTS);
		
		res.addCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(image.getDateCreated()));
		
		res.setEdmRights(WR_EDMRIGHTS);
		
		return res;
	}
	
	private String getDocumentDate(Document document) {
		String docDate = null;
		
		if (document.getDateApprox() != null) {
			docDate = "Approx " + document.getDateApprox();
		} else if (document.getDocDate() > new Integer(1000000)) {
			if (document.getDocMonthNum() != null) {
				docDate = document.getDocMonthNum().getMonthName();
				if (document.getDocDay() != null) {
					docDate += " " + document.getDocDay();
					docDate += appendOrdinal(document.getDocDay());
				}
			}
			if (docDate != null) {
				docDate += ", ";
			} else {
				docDate = "";
			}
			docDate += document.getDocYear();
		}
		return docDate;
	}
	
	private String getSynopsisOrExtract(Document document, boolean synopsis) {
		String result = null;
		
		if (document.getSynExtract() != null) {
			String res = synopsis ? document.getSynExtract().getSynopsis() : document.getSynExtract().getDocExtract();
			if (res != null && !"".equals(res.trim())) {
				result = res.trim().length() > 200 ? res.trim().substring(0, 199) : res.trim(); 
			}
		}
		return result;
	}
	
	private String getTitle(Document document) {
		String title = getTitle(
				document.getVolume().getVolNum(),
				document.getVolume().getVolLetExt(),
				document.getInsertNum(),
				document.getInsertLet(),
				document.getFolioNum(),
				document.getFolioMod(),
				document.getFolioRectoVerso() != null ? document.getFolioRectoVerso().toString() : null);
		
		title += ", doc#" +  document.getEntryId();
		if (checkNIP(document.getSenderPeople())) {
			title += ", FROM " + document.getSenderPeople().getMapNameLf();
		}
		
		if (checkNIP(document.getRecipientPeople())) {
			title += ", TO " + document.getRecipientPeople().getMapNameLf();
		}
		
		return title;
	}
	
	private String getTitle(Image image) {
		return getTitle(
				image.getVolNum(),
				image.getVolLetExt(),
				image.getInsertNum(),
				image.getInsertLet(),
				image.getImageProgTypeNum(),
				image.getMissedNumbering(), 
				image.getImageRectoVerso() != null ? image.getImageRectoVerso().toString() : null); 
	}
	
	private String getTitle(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso) {
		String title = FONDO + ", Volume " + volNum + (volLetExt != null ? " " + volLetExt : "");
		if (insertNum != null) {
			title += ", Insert " + insertNum + (insertNum != null ? " " + insertNum : "");
		}
		title += ", Folio ";
		if (folioNum != null) {
			title += folioNum + (folioMod != null ? " " + folioMod : "") + (rectoVerso != null ? " " + rectoVerso : "");
		} else {
			title += "NN";
		}
		return title;
	}
	
	private String appendOrdinal(Integer docDay) {
		if (new Integer(1).equals(docDay) || new Integer(21).equals(docDay) || new Integer(31).equals(docDay)) {
			return "st";
		}
		if (new Integer(2).equals(docDay) || new Integer(22).equals(docDay) || new Integer(32).equals(docDay)) {
			return "nd";
		}
		if (new Integer(3).equals(docDay) || new Integer(23).equals(docDay) || new Integer(33).equals(docDay)) {
			return "rd";
		}
		return "th";
	}
	
	private String getSharedUrl(Integer resourceId, String resourceField, String servletUrl) {
		return PROTOCOL + "://" + DOMAIN + CONTEXT + servletUrl + "?" + resourceField + "=" + resourceId;
	}

}
