/*
 * HtmlUtils.java
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
package org.medici.docsources.common.util;

import java.util.ArrayList;
import java.util.List;

import org.medici.docsources.common.pagination.DocumentExplorer;
import org.medici.docsources.domain.Digitization;
import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.ForumTopic;
import org.medici.docsources.domain.PlaceGeographicCoordinates;
import org.medici.docsources.domain.Schedone;
import org.medici.docsources.domain.SearchFilter.SearchType;
import org.medici.docsources.domain.UserHistory;
import org.medici.docsources.domain.UserHistory.Category;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This class is an utility to obtains specific application Url.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class HtmlUtils {

	/**
	 * 
	 * @param placeGeographicCoordinates
	 * @return
	 */
	public static String generateLinkGoogleMaps(PlaceGeographicCoordinates placeGeographicCoordinates){
		String link = "http://maps.google.com/maps?q=";
		Double latitude = placeGeographicCoordinates.getMinuteLatitude().doubleValue() * 60 + placeGeographicCoordinates.getSecondLatitude().doubleValue();
		latitude = latitude / 3600;
		latitude = placeGeographicCoordinates.getDegreeLatitude().doubleValue() + latitude;
		if(placeGeographicCoordinates.getDirectionLatitude().toUpperCase().equals("N")){
			link += "+" + latitude.toString();
		}else if(placeGeographicCoordinates.getDirectionLatitude().toUpperCase().equals("S")){
			link += "-" + latitude.toString();
		}
		link += ",";
		Double longitude = placeGeographicCoordinates.getMinuteLongitude().doubleValue() * 60 + placeGeographicCoordinates.getSecondLongitude().doubleValue();
		longitude = longitude / 3600;
		longitude = placeGeographicCoordinates.getDegreeLongitude().doubleValue() + longitude;
		if(placeGeographicCoordinates.getDirectionLongitude().toUpperCase().equals("E")){
			link += "+" + longitude.toString();
		}else if(placeGeographicCoordinates.getDirectionLongitude().toUpperCase().equals("W")){
			link += "-" + longitude.toString();
		}
		return link;
	}
	
	/**
	 * 
	 * @param documentExplorer
	 * @return
	 */
	public static String getDocumentExplorerNextPageUrl(DocumentExplorer documentExplorer) {
		if (documentExplorer == null)
			return "";
		
		if (documentExplorer.getImage().getImageOrder() == documentExplorer.getTotal().intValue())
			return "";

		StringBuilder stringBuilder = new StringBuilder(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI());
		stringBuilder.append("?entryId=");
		stringBuilder.append((documentExplorer.getEntryId()!= null) ? documentExplorer.getEntryId() : "");
		stringBuilder.append("&volNum=");
		stringBuilder.append(documentExplorer.getVolNum());
		stringBuilder.append("&volLetExt=");
		stringBuilder.append(documentExplorer.getVolLetExt());
		stringBuilder.append("&imageOrder=");
		stringBuilder.append(documentExplorer.getImage().getImageOrder()+1);
		stringBuilder.append("&total=");
		stringBuilder.append(documentExplorer.getTotal());
		stringBuilder.append("&totalRubricario=");
		stringBuilder.append(documentExplorer.getTotalRubricario());
		stringBuilder.append("&totalCarta=");
		stringBuilder.append(documentExplorer.getTotalCarta());
		stringBuilder.append("&totalAppendix=");
		stringBuilder.append(documentExplorer.getTotalAppendix());
		stringBuilder.append("&totalOther=");
		stringBuilder.append(documentExplorer.getTotalOther());
		stringBuilder.append("&totalGuardia=");
		stringBuilder.append(documentExplorer.getTotalGuardia());
		
		return stringBuilder.toString();
	}

	/**
	 * 
	 * @param documentExplorer
	 * @return
	 */
	public static String getDocumentExplorerPreviousPageUrl(DocumentExplorer documentExplorer) {
		if (documentExplorer == null)
			return "";
		
		if (documentExplorer.getImage().getImageOrder() == 1)
			return "";

		StringBuilder stringBuilder = new StringBuilder(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI());
		stringBuilder.append("?entryId=");
		stringBuilder.append((documentExplorer.getEntryId()!= null) ? documentExplorer.getEntryId() : "");
		stringBuilder.append("&volNum=");
		stringBuilder.append(documentExplorer.getVolNum());
		stringBuilder.append("&volLetExt=");
		stringBuilder.append(documentExplorer.getVolLetExt());
		stringBuilder.append("&imageOrder=");
		stringBuilder.append(documentExplorer.getImage().getImageOrder()-1);
		stringBuilder.append("&total=");
		stringBuilder.append(documentExplorer.getTotal());
		stringBuilder.append("&totalRubricario=");
		stringBuilder.append(documentExplorer.getTotalRubricario());
		stringBuilder.append("&totalCarta=");
		stringBuilder.append(documentExplorer.getTotalCarta());
		stringBuilder.append("&totalAppendix=");
		stringBuilder.append(documentExplorer.getTotalAppendix());
		stringBuilder.append("&totalOther=");
		stringBuilder.append(documentExplorer.getTotalOther());
		stringBuilder.append("&totalGuardia=");
		stringBuilder.append(documentExplorer.getTotalGuardia());
		
		return stringBuilder.toString();
	}

	/**
	 * 
	 * @param inputList
	 * @param summaryId
	 * @return
	 */
	public static String getHistoryNavigatorNextPageUrl(UserHistory userHistory) {
		if (userHistory == null)
			return null;

		return getHistoryNavigatorUrl(userHistory.getCategory(), userHistory.getIdUserHistory());
	}

	/**
	 * 
	 * @param inputList
	 * @param summaryId
	 * @return
	 */
	public static String getHistoryNavigatorPreviousPageUrl(UserHistory userHistory) {
		if (userHistory == null)
			return null;

		return getHistoryNavigatorUrl(userHistory.getCategory(), userHistory.getIdUserHistory());
	}

	/**
	 * 
	 * @param category
	 * @param idUserHistory
	 * @return
	 */
	private static String getHistoryNavigatorUrl(Category category, Integer idUserHistory) {
		if ((category == null) || (idUserHistory ==null))
			return null;

		String url = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath();
		switch (category) {
			case DOCUMENT :
				url += "/src/docbase/ShowDocumentFromHistory.do?idUserHistory=";
				url += idUserHistory;
				break;
			case PEOPLE :
				url += "/src/peoplebase/ShowPersonFromHistory.do?idUserHistory=";
				url += idUserHistory;
				break;
			case PLACE :
				url += "/src/geobase/ShowPlaceFromHistory.do?idUserHistory=";
				url += idUserHistory;
				break;
			case VOLUME :
				url += "/src/volbase/ShowVolumeFromHistory.do?idUserHistory=";
				url += idUserHistory;
				break;
			default :
				break;
		}

		return url;
	}

	/**
	 * 
	 * @param forum
	 * @return
	 */
	public static Object getShowForumCompleteDOMUrl(Forum forum) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (forum != null) {
			stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
			stringBuilder.append("/community/ShowForum.do?forumId=");
			stringBuilder.append(forum.getForumId());
			stringBuilder.append("&completeDOM=true");
		}

		return stringBuilder.toString();
	}

	/**
	 * 
	 * @return
	 */
	public static String getShowForumHrefUrl(Forum forum) {
		StringBuilder stringBuilder = new StringBuilder("<a href=\"");
		if (forum != null) {
			stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
			stringBuilder.append("/community/ShowForum.do?forumId=");
			stringBuilder.append(forum.getForumId());
			stringBuilder.append("&\" class=\"forum\">");
			stringBuilder.append(forum.getTitle());
			stringBuilder.append("</a>");
		} else {
			stringBuilder.append("&\" class=\"forum\" />");
			stringBuilder.append("</a>");
		}

		return stringBuilder.toString();
	}
	/**
	 * 
	 * @return
	 */
	public static String getShowForumIndexUrl(Forum forum) {
		if (forum == null) {
			return "";
		}

		StringBuilder stringBuilder = new StringBuilder("<img src=\"");
		stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		stringBuilder.append("/images/forum/img_chronology.png\" alt=\"Chronology\" />\n");
		stringBuilder.append("<a href=\"");
		stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		stringBuilder.append("/community/ShowForum.do?forumId=");
		stringBuilder.append(forum.getForumId());
		stringBuilder.append("\" class=\"boardIndex\">");
		stringBuilder.append(forum.getTitle());
		stringBuilder.append("</a>");

		return stringBuilder.toString();
	}

	/**
	 * 
	 * @param forumTopic
	 * @return
	 */
	public static String getShowTopicForumHrefUrl(ForumTopic forumTopic) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (forumTopic != null) {
			stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
			stringBuilder.append("/community/ShowTopicForum.do?topicId=");
			stringBuilder.append(forumTopic.getTopicId());
		}

		return stringBuilder.toString();
	}
	
	/**
	 * 
	 * @param forum
	 * @return
	 */
	public static String getShowForumUrl(Forum forum) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (forum != null) {
			stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
			stringBuilder.append("/community/ShowForum.do?forumId=");
			stringBuilder.append(forum.getForumId());
		}

		return stringBuilder.toString();
	}
	
	/**
	 * 
	 * @param inputList
	 * @param digitization
	 * @return
	 */
	public static List<String> showDigitizedVolumeActiveIt(List<String> inputList, Digitization digitization){
		StringBuilder anchorBegin = new StringBuilder("<a class=\"showActivateModal\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/digitization/ShowActivateVolumeModal.do?id=");
		anchorBegin.append(digitization.getId());
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		for (int i=0; i<inputList.size(); i++) {
			StringBuilder stringBuilder = new StringBuilder(anchorBegin.toString());
			stringBuilder.append(inputList.get(i));
			stringBuilder.append(hrefEnd);
			retValue.add(stringBuilder.toString());
		}
		
		return retValue;
	}
	
	/**
	 * 
	 * @param inputList
	 * @param digitization
	 * @return
	 */
	public static List<String> showDigitizedVolumeDeactiveIt(List<String> inputList, Digitization digitization){
		StringBuilder anchorBegin = new StringBuilder("<a class=\"showDeactivateModal\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/digitization/ShowDeactivateVolumeModal.do?id=");
		anchorBegin.append(digitization.getId());
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		for (int i=0; i<inputList.size(); i++) {
			StringBuilder stringBuilder = new StringBuilder(anchorBegin.toString());
			stringBuilder.append(inputList.get(i));
			stringBuilder.append(hrefEnd);
			retValue.add(stringBuilder.toString());
		}
		
		return retValue;
	}

	/**
	 * 
	 * @param inputList
	 * @param entryId
	 * @return
	 */
	public static String showDocument(Integer entryId) {
		if (entryId == null)
			return "";

		String url = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath();
		url += "/src/docbase/ShowDocument.do?entryId=";
		url += entryId;

		return url;
	}
	
	/**
	 * 
	 * @param inputList
	 * @param entryId
	 * @return
	 */
	public static List<String> showDocument(List<String> inputList, Integer entryId) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		String anchorBegin = "<a class=\"searchResult\" href=\"";
		anchorBegin += ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath();
		anchorBegin += "/src/docbase/ShowDocument.do?entryId=";
		anchorBegin += entryId;
		anchorBegin += "\">";
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			retValue.add(anchorBegin + inputList.get(i) + hrefEnd);
		}
		
		return retValue;
	}

	/**
	 * 
	 * @param inputList
	 * @param entryId
	 * @return
	 */
	public static List<String> showDocumentRelated(List<String> inputList, Integer entryId) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuilder anchorBegin = new StringBuilder("<a title=\"");
		anchorBegin.append(inputList.get(inputList.size() - 1).toString().substring(3, inputList.get(inputList.size() - 1).toString().indexOf("</b>")));
		anchorBegin.append("\" class=\"showResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/docbase/CompareDocument.do?entryId=");
		anchorBegin.append(entryId);
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			retValue.add(anchorBegin + inputList.get(i) + hrefEnd);
		}
		
		return retValue;
	}
	
	/**
	 * 
	 * @param senderUser
	 * @return
	 */
	public static String showMessage(Integer messageId, String text) {
		StringBuilder anchor = new StringBuilder("<a class=\"searchResult\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/community/ShowMessage.do?messageId=");
		anchor.append(messageId);
		anchor.append("\">text</a>");

		return anchor.toString();	}

	/**
	 * 
	 * @param inputList
	 * @param entryId
	 * @return
	 */
	public static List<String> showPeople(List<String> inputList, Integer personId) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuilder anchorBegin = new StringBuilder("<a class=\"searchResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/peoplebase/ShowPerson.do?personId=");
		anchorBegin.append(personId);
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			StringBuilder stringBuilder = new StringBuilder(anchorBegin.toString());
			stringBuilder.append(inputList.get(i));
			stringBuilder.append(hrefEnd);
			retValue.add(stringBuilder.toString());
		}
		
		return retValue;
	}

	public static List<String> showPeopleRelated(List<String> inputList, Integer personId){
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuilder anchorBegin = new StringBuilder("<a title=\"");
		anchorBegin.append(inputList.get(0));
		anchorBegin.append("\" class=\"showResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/peoplebase/ComparePerson.do?personId=");
		anchorBegin.append(personId);
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			StringBuilder stringBuilder = new StringBuilder(anchorBegin.toString());
			stringBuilder.append(inputList.get(i));
			stringBuilder.append(hrefEnd);
			retValue.add(stringBuilder.toString());
		}
		
		return retValue;
	}
	
	/**
	 * 
	 * @param singleRow
	 * @param placeAllId
	 * @return
	 */
	public static List<String> showPlace(List<String> inputList, Integer placeAllId) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuilder anchorBegin = new StringBuilder("<a class=\"searchResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/geobase/ShowPlace.do?placeAllId=");
		anchorBegin.append(placeAllId);
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			if(i != inputList.size() - 1){
				StringBuilder stringBuilder = new StringBuilder(anchorBegin.toString());
				stringBuilder.append(inputList.get(i));
				stringBuilder.append(hrefEnd);
				retValue.add(stringBuilder.toString());
			}else{
				StringBuilder topicsRelated = new StringBuilder("<a class=\"tabResult\" href=\"");
				topicsRelated.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
				topicsRelated.append("/de/geobase/ShowTopicsPlace.do?placeAllId=");
				topicsRelated.append(placeAllId);
				topicsRelated.append("\">");
				StringBuilder stringBuilder = new StringBuilder(topicsRelated.toString());
				stringBuilder.append(inputList.get(i));
				stringBuilder.append(hrefEnd);
				retValue.add(stringBuilder.toString());				
			}
		}
		
		return retValue;
	}
	
	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @param folioNum
	 * @param folioMod
	 * @return
	 */
	public static String showSameFolioDocuments(Integer volNum, String volLetExt, Integer folioNum, String folioMod) {
		StringBuilder url = new StringBuilder(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath()); 
		url.append("/src/docbase/ShowSameFolioDocuments.do?volNum=");
		url.append(volNum);
		url.append("&volLetExt=");
		url.append(volLetExt);
		url.append("&folioNum=");
		url.append(folioNum);
		url.append("&folioMod=");
		url.append(folioMod);

		return url.toString();
	}
	
	/**
	 * 
	 * @param inputList
	 * @param entryId
	 * @return
	 */
	public static List<String> showSchedone(List<String> inputList, Schedone schedone) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuilder anchorBegin = new StringBuilder("<a class=\"searchResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/digitization/ShowSchedone.do?schedoneId=");
		anchorBegin.append(schedone.getSchedoneId());
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			StringBuilder stringBuilder = new StringBuilder(anchorBegin.toString());
			stringBuilder.append(inputList.get(i));
			stringBuilder.append(hrefEnd);
			retValue.add(stringBuilder.toString());
		}
		
		return retValue;
	}

	/**
	 * 
	 * @param currentSchedone
	 * @return
	 */
	public static String showSchedoneActive(Schedone currentSchedone) {
		StringBuilder anchor = new StringBuilder("<a class=\"searchResult\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/digitization/ActivateSchedone.json?schedoneId=");
		anchor.append(currentSchedone.getSchedoneId());
		anchor.append("\">Active It</a>");

		return anchor.toString();
	}

	/**
	 * 
	 * @param currentSchedone
	 * @return
	 */
	public static String showSchedoneDeactivateIt(Schedone currentSchedone) {
		StringBuilder anchor = new StringBuilder("<a class=\"searchResult\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/digitization/DeactivateSchedone.json?schedoneId=");
		anchor.append(currentSchedone.getSchedoneId());
		anchor.append("\">Deactivate It</a>");

		return anchor.toString();
	}

	/**
	 * 
	 * @param currentSchedone
	 * @return
	 */
	public static String showSchedoneDescription(Schedone currentSchedone) {
		StringBuilder anchor = new StringBuilder("<a class=\"searchResult\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/digitization/ShowSchedone.do?schedoneId=");
		anchor.append(currentSchedone.getSchedoneId());
		anchor.append("\">" + currentSchedone.getDescrizioneContenuto() + "</a>");

		return anchor.toString();
	}

	/**
	 * 
	 * @param currentSchedone
	 * @return
	 */
	public static String showSchedoneEditIt(Schedone currentSchedone) {
		StringBuilder anchor = new StringBuilder("<a class=\"searchResult\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/digitization/ShowSchedone.do?schedoneId=");
		anchor.append(currentSchedone.getSchedoneId());
		anchor.append("\">Edit It</a>");

		return anchor.toString();
	}
	
	/**
	 * 
	 * @param currentSchedone
	 * @return
	 */
	public static String showSchedoneMDP(Schedone currentSchedone) {
		StringBuilder anchor = new StringBuilder("<a class=\"searchResult\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/digitization/ShowSchedone.do?schedoneId=");
		anchor.append(currentSchedone.getSchedoneId());
		if(currentSchedone.getVolLetExt() == null){
			anchor.append("\">" + currentSchedone.getVolNum() + "</a>");
		}else{
			anchor.append("\">" + currentSchedone.getVolNum() + currentSchedone.getVolLetExt() + "</a>");
		}

		return anchor.toString();
	}

	/**
	 * 
	 * @param inputList
	 * @param entryId
	 * @return
	 */
	public static List<String> showTopicsDocumentRelated(List<String> inputList, Integer entryId) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuilder anchorBegin = new StringBuilder("<a title=\"");
		anchorBegin.append(inputList.get(0));
		anchorBegin.append("\" class=\"showResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/docbase/CompareDocument.do?entryId=");
		anchorBegin.append(entryId);
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			retValue.add(anchorBegin + inputList.get(i) + hrefEnd);
		}
		
		return retValue;
	}

	/**
	 * 
	 * @param inputList
	 * @param entryId
	 * @return
	 */
	public static List<String> showTopicsDocumentRelated(List<String> inputList, String place) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuilder anchorBegin = new StringBuilder("<a title=\"");
		anchorBegin.append(inputList.get(0));
		anchorBegin.append("\" class=\"showResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/de/docbase/LinkedDocumentsTopic.do?placeAllId=");
		anchorBegin.append(place + "&topicTitle=");
		anchorBegin.append(inputList.get(0));
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			retValue.add(anchorBegin + inputList.get(i) + hrefEnd);
		}
		
		return retValue;
	}
	
	/**
	 * 
	 * @param inputList
	 * @param entryId
	 * @return
	 */
	public static List<String> showUser(List<String> inputList, String account) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		String anchorBegin = "<a class=\"searchResult\" href=\"";
		anchorBegin += ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath();
		anchorBegin += "/admin/ShowUser.do?account=";
		anchorBegin += account;
		anchorBegin += "\">";
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			retValue.add(anchorBegin + inputList.get(i) + hrefEnd);
		}
		
		return retValue;
	}

	/**
	 * 
	 * @param inputList
	 * @param idFilter
	 * @return
	 */
	public static List<String> showUserSearchFilter(List<Object> inputList, Integer idSearchFilter, SearchType searchType) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuilder anchorBegin = new StringBuilder("<a class=\"searchResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/AdvancedSearch.do?idSearchFilter=");
		anchorBegin.append(idSearchFilter);
		anchorBegin.append("&searchType=");
		anchorBegin.append(searchType);
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			retValue.add(anchorBegin + inputList.get(i).toString() + hrefEnd);
		}
		
		return retValue;
	}

	/**
	 * 
	 * @param inputList
	 * @param summaryId
	 * @return
	 */
	public static List<String> showVolume(List<String> inputList, Integer summaryId) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuilder anchorBegin = new StringBuilder("<a class=\"searchResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/volbase/ShowVolume.do?summaryId=");
		anchorBegin.append(summaryId);
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			StringBuilder stringBuilder = new StringBuilder(anchorBegin.toString());
			stringBuilder.append(inputList.get(i));
			stringBuilder.append(hrefEnd);
			retValue.add(stringBuilder.toString());
		}
		
		return retValue;
	}
}
