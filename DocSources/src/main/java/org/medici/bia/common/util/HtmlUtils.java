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
package org.medici.bia.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.medici.bia.common.pagination.DocumentExplorer;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.search.AdvancedSearchAbstract.DateType;
import org.medici.bia.domain.Digitization;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.PlaceGeographicCoordinates;
import org.medici.bia.domain.Schedone;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.medici.bia.domain.TitleOccsList;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserHistory.Category;
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
	 * @param lastLogonDate
	 * @return
	 */
	public static Map<String, String> generateAdvancedSearchLinks(Date inputDate) {
		Map<String, String> retValue = new HashMap<String, String>();
		retValue.put("DOCUMENT", "");
		retValue.put("PLACE", "");
		retValue.put("PEOPLE", "");
		retValue.put("VOLUME", "");
		
		if (inputDate == null) {
			return retValue;
		}

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		stringBuilder.append("/src/AdvancedSearch.do?searchType=");
		stringBuilder.append(SearchType.DOCUMENT);
		stringBuilder.append("&dateLastUpdate=");
		stringBuilder.append(DateType.Between.toString());
		stringBuilder.append('|');
		stringBuilder.append(DateUtils.getStringDate(inputDate));
		stringBuilder.append('|');
		stringBuilder.append(DateUtils.getStringDate(Calendar.getInstance().getTime()));
		stringBuilder.append("&searchUUID=");
		stringBuilder.append(UUID.randomUUID());
		retValue.put("DOCUMENT", stringBuilder.toString());
		
		stringBuilder = new StringBuilder();
		stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		stringBuilder.append("/src/AdvancedSearch.do?searchType=");
		stringBuilder.append(SearchType.PEOPLE);
		stringBuilder.append("&dateLastUpdate=");
		stringBuilder.append(DateType.Between.toString());
		stringBuilder.append('|');
		stringBuilder.append(DateUtils.getStringDate(inputDate));
		stringBuilder.append('|');
		stringBuilder.append(DateUtils.getStringDate(Calendar.getInstance().getTime()));
		stringBuilder.append("&searchUUID=");
		stringBuilder.append(UUID.randomUUID());
		retValue.put("PEOPLE", stringBuilder.toString());

		stringBuilder = new StringBuilder();
		stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		stringBuilder.append("/src/AdvancedSearch.do?searchType=");
		stringBuilder.append(SearchType.PLACE);
		stringBuilder.append("&dateLastUpdate=");
		stringBuilder.append(DateType.Between.toString());
		stringBuilder.append('|');
		stringBuilder.append(DateUtils.getStringDate(inputDate));
		stringBuilder.append('|');
		stringBuilder.append(DateUtils.getStringDate(Calendar.getInstance().getTime()));
		stringBuilder.append("&searchUUID=");
		stringBuilder.append(UUID.randomUUID());
		retValue.put("PLACE", stringBuilder.toString());

		stringBuilder = new StringBuilder();
		stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		stringBuilder.append("/src/AdvancedSearch.do?searchType=");
		stringBuilder.append(SearchType.VOLUME);
		stringBuilder.append("&dateLastUpdate=");
		stringBuilder.append(DateType.Between.toString());
		stringBuilder.append('|');
		stringBuilder.append(DateUtils.getStringDate(inputDate));
		stringBuilder.append('|');
		stringBuilder.append(DateUtils.getStringDate(Calendar.getInstance().getTime()));
		stringBuilder.append("&searchUUID=");
		stringBuilder.append(UUID.randomUUID());
		retValue.put("VOLUME", stringBuilder.toString());

		return retValue;
	}
	
	/**
	 * 
	 * @param placeGeographicCoordinates
	 * @return
	 */
	public static String generateLinkGoogleMaps(PlaceGeographicCoordinates placeGeographicCoordinates){
		StringBuilder stringBuilder = new StringBuilder("http://maps.google.com/maps?q=");
		Double latitude = placeGeographicCoordinates.getMinuteLatitude().doubleValue() * 60 + placeGeographicCoordinates.getSecondLatitude().doubleValue();
		latitude = latitude / 3600;
		latitude = placeGeographicCoordinates.getDegreeLatitude().doubleValue() + latitude;
		if(placeGeographicCoordinates.getDirectionLatitude().toUpperCase().equals("N")) {
			stringBuilder.append("+");
			stringBuilder.append(latitude.toString());
		} else if(placeGeographicCoordinates.getDirectionLatitude().toUpperCase().equals("S")) {
			stringBuilder.append("-");
			stringBuilder.append(latitude.toString());
		}
		stringBuilder.append(",");
		Double longitude = placeGeographicCoordinates.getMinuteLongitude().doubleValue() * 60 + placeGeographicCoordinates.getSecondLongitude().doubleValue();
		longitude = longitude / 3600;
		longitude = placeGeographicCoordinates.getDegreeLongitude().doubleValue() + longitude;
		if(placeGeographicCoordinates.getDirectionLongitude().toUpperCase().equals("E")){
			stringBuilder.append("+");
			stringBuilder.append(longitude.toString());
		}else if(placeGeographicCoordinates.getDirectionLongitude().toUpperCase().equals("W")){
			stringBuilder.append("-");
			stringBuilder.append(longitude.toString());
		}
		return stringBuilder.toString();
	}

	/**
	 * 
	 * @param documentExplorer
	 * @return
	 */
	public static String getDocumentExplorerNextPageUrl(DocumentExplorer documentExplorer) {
		if (documentExplorer == null) {
			return "";
		}
		
		if (documentExplorer.getImage().getImageOrder() == documentExplorer.getTotal().intValue()) {
			return "";
		}

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
		if (documentExplorer == null) {
			return "";
		}
		
		if (documentExplorer.getImage().getImageOrder() == 1){
			return "";
		}

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
		if (userHistory == null) {
			return null;
		}

		return getHistoryNavigatorUrl(userHistory.getCategory(), userHistory.getIdUserHistory());
	}

	/**
	 * 
	 * @param inputList
	 * @param summaryId
	 * @return
	 */
	public static String getHistoryNavigatorPreviousPageUrl(UserHistory userHistory) {
		if (userHistory == null) {
			return null;
		}

		return getHistoryNavigatorUrl(userHistory.getCategory(), userHistory.getIdUserHistory());
	}

	/**
	 * 
	 * @param category
	 * @param idUserHistory
	 * @return
	 */
	private static String getHistoryNavigatorUrl(Category category, Integer idUserHistory) {
		if ((category == null) || (idUserHistory ==null)) {
			return null;
		}

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
	 * @return
	 */
	public static String getImageDigitized() {
		StringBuilder stringBuilder = new StringBuilder("<img src=\"");
		stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		stringBuilder.append("/images/1024/img_digitized_small_document.png\">");
		return stringBuilder.toString();
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
		stringBuilder.append("/images/forum/img_chronology.png\" alt=\"Chronology\" />");
		stringBuilder.append(System.getProperty("line.separator"));
		stringBuilder.append("<a href=\"");
		stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		stringBuilder.append("/community/ShowForum.do?forumId=");
		stringBuilder.append(forum.getForumId());
		stringBuilder.append("&completeDOM=true\" class=\"boardIndex\">");
		stringBuilder.append(forum.getTitle());
		stringBuilder.append("</a>");

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
	 * @param forumTopic
	 * @return
	 */
	public static String getShowTopicForumHrefUrl(ForumTopic forumTopic) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (forumTopic != null) {
			stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
			stringBuilder.append("/community/ShowTopicForum.do?topicId=");
			stringBuilder.append(forumTopic.getTopicId());
			//For generate a link to the last page of topic
			stringBuilder.append("&postForPage=10&postPageNumber=");
			stringBuilder.append((forumTopic.getTotalReplies() / 10) + 1);
			stringBuilder.append("&postPageTotal=");
			stringBuilder.append((forumTopic.getTotalReplies() / 10) + 1);
			
		}

		return stringBuilder.toString();
	}

	/**
	 * Urls must be encoded.
	 * 
	 * @param topicId
	 * @param postPageNumber
	 * @param postPageTotal
	 * @param postsForPage
	 * @return
	 */
	public static String getSitemapForumTopicUrl(Integer topicId, Integer postPageNumber, Integer postPageTotal, Integer postsForPage, Boolean completeDOM) {
		StringBuilder stringBuilder = new StringBuilder(0);
		if (topicId != null) {
			stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("website.protocol"));
			stringBuilder.append("://");
			stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("website.domain"));
			stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("website.contextPath"));
			stringBuilder.append("community/ShowTopicForum.do?forumId=");
			stringBuilder.append("&amp;topicId=");
			stringBuilder.append(topicId);
			stringBuilder.append("&amp;postPageNumber");
			stringBuilder.append(postPageNumber);
			stringBuilder.append("&amp;postPageTotal=");
			stringBuilder.append(postPageTotal);
			stringBuilder.append("&amp;postsForPage=");
			stringBuilder.append(postsForPage);
			stringBuilder.append("&amp;completeDOM=");
			stringBuilder.append(completeDOM);
		}

		return stringBuilder.toString();
	}
	
	/**
	 * 
	 * @param integer
	 * @return
	 */
	public static String getSitemapForumUrl(Integer forumId, Boolean completeDOM) {
		StringBuilder stringBuilder = new StringBuilder(0);
		if (forumId != null) {
			stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("website.protocol"));
			stringBuilder.append("://");
			stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("website.domain"));
			stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("website.contextPath"));
			stringBuilder.append("community/ShowForum.do?forumId=");
			stringBuilder.append(forumId);
			stringBuilder.append("&amp;completeDOM=");
			stringBuilder.append(completeDOM);
		}
		
		return stringBuilder.toString();
	}

	/**
	 * 
	 * @param pageNumber
	 * @return
	 */
	public static String getSitemapUrl(Integer pageNumber) {
		StringBuilder stringBuilder = new StringBuilder(0);
		if (pageNumber != null) {
			stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("website.protocol"));
			stringBuilder.append("://");
			stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("website.domain"));
			stringBuilder.append(ApplicationPropertyManager.getApplicationProperty("website.contextPath"));
			stringBuilder.append("sitemap/Sitemap.do?id=");
			stringBuilder.append(pageNumber);
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * 
	 * @param text
	 * @param searchWord
	 * @return
	 */
	public static String highlightText(String text, String searchWord){
		StringBuffer returnText = new StringBuffer(text);
		Integer indexWord = new Integer(0);
		List<String> exactWords = new ArrayList<String>();
		String toSearch = searchWord;
		//MD: This code is to identify the words between double quotes
		while(toSearch.contains("\"")){
			//First double quote
			int from = toSearch.indexOf('\"');
			//Second double quote
			int to = toSearch.indexOf('\"', from + 1);
			//If there is the second double quote or not
			if(to != -1){
				//Add the exact words to the list and remove them from the string
				exactWords.add(toSearch.substring(from + 1, to).toLowerCase());
				toSearch = toSearch.substring(0, from) + toSearch.substring(to + 1, toSearch.length());
			}else{
				toSearch = toSearch.replace("\"", " ");
			}
		}
		String [] words = StringUtils.split(searchWord.toLowerCase(), " ");
		if(exactWords.size() == 0){
			for(String currentWord : words){
				indexWord = returnText.toString().toLowerCase().indexOf(currentWord);
				while(indexWord != -1){
					while(indexWord >= 0 && returnText.charAt(indexWord) != ' '){
						indexWord--;
					}
					Integer beginWord = indexWord + 1; 
					returnText.insert(beginWord, "<span class='highlighted'>");
					Integer endWord = beginWord + 26;
					while(endWord < returnText.length() && returnText.charAt(endWord) != ' '){
						endWord++;
					}
					returnText.insert(endWord, "</span>");
					Integer nextWord = returnText.substring(endWord).toLowerCase().indexOf(currentWord);
					if(nextWord != -1){
						indexWord = endWord + nextWord;
					}else{
						indexWord = nextWord;
					}
				}
			}
		}else{
			for(String currentWord : exactWords){
				indexWord = returnText.toString().toLowerCase().indexOf(currentWord);
				while(indexWord != -1){
					Integer beginWord = indexWord; 
					returnText.insert(beginWord, "<span class='highlighted'>");
					Integer endWord = beginWord + 26 + currentWord.length();					
					returnText.insert(endWord, "</span>");
					Integer nextWord = returnText.substring(endWord).toLowerCase().indexOf(currentWord);
					if(nextWord != -1){
						indexWord = endWord + nextWord;
					}else{
						indexWord = nextWord;
					}
				}
			}
		}
		return returnText.toString();
	}

	/**
	 * 
	 * @param singleRow
	 * @param idAccessLog
	 * @return
	 */
	public static Object showAccessLog(List inputList, Integer idAccessLog) {
		if (inputList == null) {
			return null;
		}

		List<String> retValue = new ArrayList<String>(inputList.size());
		
		String anchorBegin = "<a class=\"searchResult\" href=\"";
		anchorBegin += ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath();
		anchorBegin += "/admin/ShowAccessLog.do?idAccessLog=";
		anchorBegin += idAccessLog;
		anchorBegin += "\">";
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			retValue.add(anchorBegin + inputList.get(i) + hrefEnd);
		}
		
		return retValue;
	}
	
	/**
	 * 
	 * @param placeAllId
	 * @param numberOfActiveEndInPlace
	 * @param description
	 * @return
	 */
	public static String showActiveEndPeoplePlace(Integer placeAllId, Integer numberOfActiveEndInPlace, String description) {
		StringBuilder anchor = new StringBuilder("<a class=\"activeEnd\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/src/geobase/ShowActiveEndPeoplePlace.do?placeAllId=");
		anchor.append(placeAllId);
		anchor.append("\">");
		anchor.append(numberOfActiveEndInPlace);
		anchor.append(' ');
		anchor.append(description);
		anchor.append("</a>");

		return anchor.toString();	
	}

	/**
	 * 
	 * @param placeAllId
	 * @param numberOfActiveStartInPlace
	 * @param description
	 * @return
	 */
	public static String showActiveStartPeoplePlace(Integer placeAllId, Integer numberOfActiveStartInPlace, String description) {
		StringBuilder anchor = new StringBuilder("<a class=\"activeStart\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/src/geobase/ShowActiveStartPeoplePlace.do?placeAllId=");
		anchor.append(placeAllId);
		anchor.append("\">");
		anchor.append(numberOfActiveStartInPlace);
		anchor.append(' ');
		anchor.append(description);
		anchor.append("</a>");
		
		return anchor.toString();
	}

	/**
	 * 
	 * @param placeAllId
	 * @param numberOfBirthInPlace
	 * @param description
	 * @return
	 */
	public static String showBirthPeoplePlace(Integer placeAllId, Integer numberOfBirthInPlace, String description) {
		StringBuilder anchor = new StringBuilder("<a class=\"birth\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/src/geobase/ShowBirthPeoplePlace.do?placeAllId=");
		anchor.append(placeAllId);
		anchor.append("\">");
		anchor.append(numberOfBirthInPlace);
		anchor.append(' ');
		anchor.append(description);
		anchor.append("</a>");
		
		return anchor.toString();
	}
	
	/**
	 * 
	 * @param placeAllId
	 * @param numberOfDeathInPlace
	 * @param description
	 * @return
	 */
	public static String showDeathPeoplePlace(Integer placeAllId, Integer numberOfDeathInPlace, String description) {
		StringBuilder anchor = new StringBuilder("<a class=\"death\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/src/geobase/ShowDeathPeoplePlace.do?placeAllId=");
		anchor.append(placeAllId);
		anchor.append("\">");
		anchor.append(numberOfDeathInPlace);
		anchor.append(' ');
		anchor.append(description);
		anchor.append("</a>");
		
		return anchor.toString();
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
		
		List<String> retValue = new ArrayList<String>(inputList.size());
		
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
		
		List<String> retValue = new ArrayList<String>(inputList.size());
		
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
		if (entryId == null) {
			return "";
		}

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
		if (inputList == null) {
			return null;
		}

		List<String> retValue = new ArrayList<String>(inputList.size());
		
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
	 * @param titleLastRow
	 * @return
	 */
	public static List<String> showDocument(List<String> inputList, Integer entryId, String titleLastRow) {
		if (inputList == null) {
			return null;
		}

		List<String> retValue = new ArrayList<String>(inputList.size());
		
		String anchorBegin = "<a class=\"searchResult\" href=\"";
		anchorBegin += ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath();
		anchorBegin += "/src/docbase/ShowDocument.do?entryId=";
		anchorBegin += entryId;
		anchorBegin += "\"";
		String anchorBeginEnd = ">";
		String title = "title=\"";
		title += titleLastRow;
		title += "\"";
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			if(i < inputList.size() - 1)
				retValue.add(anchorBegin  + anchorBeginEnd + inputList.get(i) + hrefEnd);
			else
				retValue.add(anchorBegin + title + anchorBeginEnd + inputList.get(i) + hrefEnd);
		}
		
		return retValue;
	}
	
	/**
	 * 
	 * @param inputList
	 * @param entryId
	 * @return
	 */
	public static List<String> showDocumentExpand(List<String> inputList, Integer entryId) {
		if (inputList == null) {
			return null;
		}

		List<String> retValue = new ArrayList<String>(inputList.size());
		
		String anchorBegin = "<a class=\"searchResult\" href=\"";
		String anchorBeginText = "<a class=\"searchResult textDoc\" href=\"";
		String anchor = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath();
		anchor += "/src/docbase/ShowDocument.do?entryId=";
		anchor += entryId;
		anchor += "\">";
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			if(i < inputList.size() - 1)
				retValue.add(anchorBegin + anchor + inputList.get(i) + hrefEnd);
			else
				retValue.add(anchorBeginText + anchor + inputList.get(i) + hrefEnd);
		}
		
		return retValue;
	}

	/**
	 * 
	 * @param entryId
	 * @return
	 */
	public static String showDocumentRelated(Integer entryId) {
		StringBuilder stringBuilder = new StringBuilder("<a class=\"showResult\" href=\"");
		stringBuilder.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		stringBuilder.append("/src/docbase/CompareDocument.do?entryId=");
		stringBuilder.append(entryId);
		stringBuilder.append("\">Show Documents Related</a>");
				
		return stringBuilder.toString();
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
		if(!inputList.get(inputList.size() - 1).contains("NNF"))
			anchorBegin.append(inputList.get(inputList.size() - 1).toString().substring(3, inputList.get(inputList.size() - 1).toString().indexOf("</b>")));
		else
			anchorBegin.append("DocId#" + entryId + " - " + inputList.get(inputList.size() - 1).toString().substring(3, inputList.get(inputList.size() - 1).toString().indexOf("</b>")));
		anchorBegin.append("\" class=\"showResult tabTitle\" id=\"docId" + entryId + "\" href=\"");
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
	 * @param titleLastRow
	 * @return
	 */
	public static List<String> showDocumentRelated(List<String> inputList, Integer entryId, String MDPAndFolio, String titleLastRow) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuilder anchorBegin = new StringBuilder("<a title=\"");
		if(!MDPAndFolio.contains("NNF"))
			anchorBegin.append(MDPAndFolio);
		else
			anchorBegin.append("DocId#" + entryId + " - " + MDPAndFolio);
		anchorBegin.append("\" class=\"showResult tabTitle\" id=\"docId" + entryId + "\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/docbase/CompareDocument.do?entryId=");
		anchorBegin.append(entryId);
		anchorBegin.append("\">");
		StringBuilder anchorBeginLastColumn = new StringBuilder("<a title=\"");
		anchorBeginLastColumn.append(titleLastRow);
		anchorBeginLastColumn.append("\" class=\"showResult\" id=\"docId" + entryId + "\" href=\"");
		anchorBeginLastColumn.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBeginLastColumn.append("/src/docbase/CompareDocument.do?entryId=");
		anchorBeginLastColumn.append(entryId);
		anchorBeginLastColumn.append("\">");
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			if(i < inputList.size() - 1)
				retValue.add(anchorBegin + inputList.get(i) + hrefEnd);
			else
				retValue.add(anchorBeginLastColumn + inputList.get(i) + hrefEnd);
		}
		
		return retValue;
	}

	/**
	 * 
	 * @param placeAllId
	 * @param numberOfDocumentsInTopicsPlace
	 * @param numberOfTopicsPlace
	 * @param description
	 * @return
	 */
	public static String showDocumentsInTopicsPlace(Integer placeAllId, Integer numberOfDocumentsInTopicsPlace, Integer numberOfTopicsPlace) {
		StringBuilder anchor = new StringBuilder("<a class=\"topics\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/src/geobase/ShowTopicsPlace.do?placeAllId=");
		anchor.append(placeAllId);
		anchor.append("\">");
		anchor.append(numberOfDocumentsInTopicsPlace);
		anchor.append(" Documents on ");
		anchor.append(numberOfTopicsPlace);
		anchor.append(" Topics");
		anchor.append("</a>");

		return anchor.toString();	
	}
	
	/**
	 * 
	 * @param summaryId
	 * @param description
	 * @return
	 */
	public static String showDocumentsVolume(Integer summaryId, String description) {
		StringBuilder anchor = new StringBuilder("<a id=\"showDocumentsRelated\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/src/volbase/ShowDocumentsVolume.do?summaryId=");
		anchor.append(summaryId);
		anchor.append("\">");
		anchor.append(description);
		anchor.append("</a>");
		
		return anchor.toString();
	}

	/**
	 * 
	 * @param messageId
	 * @param text
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
		anchorBegin.append("\" class=\"showResult\" id=\"peopleId" + personId + "\" href=\"");
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
	 * @param singleRow
	 * @param placeAllId
	 * @return
	 */
	public static List<String> showPlaceMarkedList(List<String> inputList, Integer placeAllId) {
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
			StringBuilder stringBuilder = new StringBuilder(anchorBegin.toString());
			stringBuilder.append(inputList.get(i));
			stringBuilder.append(hrefEnd);
			retValue.add(stringBuilder.toString());
		}
		
		return retValue;
	}

	/**
	 * 
	 * @param placeAllId
	 * @param numberOfRecipientDocumentsPlace
	 * @param description
	 * @return
	 */
	public static String showRecipientDocumentsPlace(Integer placeAllId, Integer numberOfRecipientDocumentsPlace, String description) {
		StringBuilder anchor = new StringBuilder("<a class=\"recipient\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/src/geobase/ShowRecipientDocumentsPlace.do?placeAllId=");
		anchor.append(placeAllId);
		anchor.append("\">");
		anchor.append(numberOfRecipientDocumentsPlace);
		anchor.append(' ');
		anchor.append(description);
		anchor.append("</a>");

		return anchor.toString();	
	}

	/**
	 * 
	 * @param personId
	 */
	public static String showRecipientDocumentsRelated(Integer personId, String description) {
		StringBuilder anchor = new StringBuilder("<a class=\"recipient_docs\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath()); 
		anchor.append("/src/peoplebase/ShowRecipientDocumentsPerson.do?personId=");
		anchor.append(personId);
		anchor.append("\">");
		anchor.append(description);
		anchor.append("</a>");

		return anchor.toString();
	}

	/**
	 * 
	 * @param personId
	 */
	public static String showReferringToDocumentsRelated(Integer personId, String description) {
		StringBuilder anchor = new StringBuilder("<a class=\"referred_docs\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath()); 
		anchor.append("/src/peoplebase/ShowReferringToDocumentsPerson.do?personId=");
		anchor.append(personId);
		anchor.append("\">");
		anchor.append(description);
		anchor.append("</a>");

		return anchor.toString();
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
	 * @param placeAllId
	 * @param numberOfSenderDocumentsPlace
	 * @param description
	 * @return
	 */
	public static String showSenderDocumentsPlace(Integer placeAllId, Integer numberOfSenderDocumentsPlace, String description) {
		StringBuilder anchor = new StringBuilder("<a class=\"sender\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/src/geobase/ShowSenderDocumentsPlace.do?placeAllId=");
		anchor.append(placeAllId);
		anchor.append("\">");
		anchor.append(numberOfSenderDocumentsPlace);
		anchor.append(' ');
		anchor.append(description);
		anchor.append("</a>");

		return anchor.toString();	
	}

	/**
	 * 
	 * @param personId
	 */
	public static String showSenderDocumentsRelated(Integer personId, String description) {
		StringBuilder anchor = new StringBuilder("<a class=\"sender_docs\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath()); 
		anchor.append("/src/peoplebase/ShowSenderDocumentsPerson.do?personId=");
		anchor.append(personId);
		anchor.append("\">");
		anchor.append(description);
		anchor.append("</a>");

		return anchor.toString();
	}

	/**
	 * 
	 * @param titleOccsList
	 * @return
	 */
	public static String showTitleOrOccupation(TitleOccsList titleOccsList) {
		StringBuilder anchor = new StringBuilder("<a class=\"searchResult\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/src/peoplebase/ShowTitleOrOccupation.do?titleOccId=");
		anchor.append(titleOccsList.getTitleOccId());
		anchor.append("\">" + titleOccsList.getTitleOcc() + "</a>");

		return anchor.toString();
	}

	/**
	 * 
	 * @param assignedPeople
	 * @return
	 */
	public static String showTitleOrOccupationAssignedPeopleSearch(TitleOccsList titleOccsList, Long assignedPeople) {
		StringBuilder anchor = new StringBuilder("<a class=\"searchResult\" href=\"");
		anchor.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchor.append("/src/peoplebase/ShowTitleOrOccupation.do?titleOccId=");
		anchor.append(titleOccsList.getTitleOccId());
		anchor.append("\">" + assignedPeople + "</a>");

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
		if(!inputList.get(inputList.size() - 1).contains("NNF"))
			anchorBegin.append(inputList.get(inputList.size() - 1).toString().substring(3, inputList.get(inputList.size() - 1).toString().indexOf("</b>")));
		else
			anchorBegin.append("DocId#" + entryId + " - " + inputList.get(inputList.size() - 1).toString().substring(3, inputList.get(inputList.size() - 1).toString().indexOf("</b>")));
		anchorBegin.append("\" class=\"showResult\" id=\"docId" + entryId + "\" href=\"");
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
		anchorBegin.append("\" class=\"showResultTopicsDoc\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/docbase/LinkedDocumentsTopic.do?placeAllId=");
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
	
	/**
	 * 
	 * @param inputList
	 * @param entryId
	 * @return
	 */
	public static List<String> showSearch(List<String> inputList, Integer idUserHistory) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuilder anchorBegin = new StringBuilder("<a class=\"searchResultUserSearch\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/ShowSearchUserHistory.do?idUserHistory=");
		anchorBegin.append(idUserHistory);
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
