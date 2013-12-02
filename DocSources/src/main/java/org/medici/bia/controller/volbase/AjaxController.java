/*
 * AjaxController.java
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
package org.medici.bia.controller.volbase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.DocumentUtils;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.common.util.ListBeanUtils;
import org.medici.bia.common.util.VolumeUtils;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.medici.bia.domain.SerieList;
import org.medici.bia.domain.VettingHistory;
import org.medici.bia.domain.Volume;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.user.UserService;
import org.medici.bia.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for VolBase.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller("VolBaseAjaxController")
public class AjaxController {
	@Autowired
	private UserService userService;
	@Autowired
	private VolBaseService volBaseService;
	
	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}
	
	/**
	 * @return the volBaseService
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}
	
	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @param volBaseService the volBaseService to set
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/src/volbase/CheckVolumeDigitized", method = RequestMethod.GET)
	public ModelAndView checkVolumeDigitized(	
			@RequestParam(value="summaryId", required=false) Integer summaryId,
			@RequestParam(value="volNum", required=false) Integer volNum, 
			@RequestParam(value="volLetExt", required=false) String volLetExt) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		if (summaryId != null) {
			try {
				Boolean digitized = getVolBaseService().checkVolumeDigitized(summaryId);
				
				model.put("summaryId", summaryId.toString());
				model.put("digitized", digitized.toString());
			} catch (ApplicationThrowable aex) {
				model.put("summaryId", summaryId.toString());
				model.put("digitized", "false");
				model.put("error", aex.getApplicationError().toString());
			}
		} else if (volNum != null){
			try {
				Boolean digitized = getVolBaseService().checkVolumeDigitized(volNum, volLetExt);
				
				model.put("volNum", volNum.toString());
				model.put("volLetExt", volLetExt);
				model.put("digitized", digitized.toString());
			} catch (ApplicationThrowable aex) {
				model.put("volNum", volNum.toString());
				model.put("volLetExt", volLetExt);
				model.put("digitized", "false");
				model.put("error", aex.getApplicationError().toString());
			}
		} else {
			model.put("digitized", "false");
			model.put("error", "incorrect call");
		}
		
		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method checks if a folio (identified by volume, insert and folio details provided)
	 * is stored in the system or not. It also checks if the volume provided exists or not.
	 * 
	 * @param inputVolume Volume (number and extension letter)
	 * @param insertNum Insert number
	 * @param insertLet Insert extension
	 * @param folioNum Folio number
	 * @param folioMod Folio extension
	 * @param folioRectoVerso Folio recto/verso
	 * @return ModelAndView containing the above informations.
	 */
	@RequestMapping(value = "/de/volbase/CheckFolio.json", method = RequestMethod.GET)
	public ModelAndView checkDigitization(@RequestParam(value="volume", required=true) String inputVolume,
			@RequestParam(value="insertNum", required=false) String insertNum,
			@RequestParam(value="insertLet", required=false) String insertLet,
			@RequestParam(value="folioNum", required=true) Integer folioNum,
			@RequestParam(value="folioMod", required=false) String folioMod,
			@RequestParam(value="folioRectoVerso", required=false) String folioRectoVerso) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			Integer volNum = VolumeUtils.extractVolNum(inputVolume);
			String volLetExt = VolumeUtils.extractVolLetExt(inputVolume);
			Volume volume = getVolBaseService().findVolume(volNum, volLetExt);
			if (volume != null) {
				model.put("volumeDigitized", volume.getDigitized());
				if (volume.getDigitized()) {
					Set<String> existenceConfig = getVolBaseService().checkChartaExistence(volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, folioRectoVerso);
					for (String key : existenceConfig) {
						model.put(key, Boolean.TRUE);
					}
				}
			} else {
				model.put("volumeNotExist", Boolean.TRUE);
			}
			
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}
		
		return new ModelAndView("responseOK", model);
	}

	/**
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/volbase/CheckVolumeIsDeletable", method = RequestMethod.GET)
	public ModelAndView checkVolumeIsDeletable(@RequestParam(value="summaryId") Integer summaryId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			Volume volume = getVolBaseService().findVolume(summaryId);
			if (volume != null) {
				Integer numberOfDocumentsVolume = getVolBaseService().findVolumeDocumentsRelated(summaryId);
	
				if (numberOfDocumentsVolume >0) {
					model.put("isDeletable", Boolean.FALSE.toString());
					
					model.put("documentsVolume", numberOfDocumentsVolume);
					model.put("documentsVolumeURL", HtmlUtils.showDocumentsVolume(summaryId, "Documents"));
				} else {
					model.put("isDeletable", Boolean.TRUE.toString());
				}
				model.put("summaryId", summaryId.toString());
				if(volume.getVolNum() != null)
					model.put("volNum", volume.getVolNum().toString());
				else
					model.put("volNum", "");
				model.put("volLetExt", ObjectUtils.toString(volume.getVolLetExt()));
			} else {
				model.put("isDeletable", Boolean.FALSE.toString());
			}
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @return
	 */
	private ModelAndView findVolume(Integer volNum, String volLetExt) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			Volume volume = getVolBaseService().findVolume(volNum, volLetExt);
			model.put("volNum", volNum.toString());
			model.put("volLetExt", volLetExt);
			model.put("summaryId", (volume == null) ? "" : volume.getSummaryId().toString());
			model.put("volumeDigitized", (volume != null) ? volume.getDigitized() : "false");
		} catch (ApplicationThrowable aex) {
			model.put("volNum", (volNum != null) ? volNum.toString() : "");
			model.put("volLetExt", (volLetExt != null) ? volLetExt : "");
			model.put("summaryId", "");
			model.put("folioCount", "");
			model.put("volumeDigitized", "false");
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns the summaryId of the volume searched by is MDP. 
	 *  
	 * @param inputVolume String containing volNum and VolLetExt
	 * @return ModelAndView containing input params and summaryId.
	 */
	private ModelAndView findVolume(String inputVolume) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			Volume volume = getVolBaseService().findVolume(VolumeUtils.extractVolNum(inputVolume), VolumeUtils.extractVolLetExt(inputVolume));
			if(volume != null){
				model.put("volNum", volume.getVolNum().toString());
				model.put("volLetExt", (volume.getVolLetExt()!= null) ? volume.getVolLetExt() : "");
				model.put("summaryId", (volume == null) ? "" : volume.getSummaryId().toString());
				if(volume.getFolioCount() != null) {
					model.put("folioCount", volume.getFolioCount());
				} else {
					model.put("folioCount", "");
				}
				model.put("volumeDigitized", volume.getDigitized());
			} else {
				model.put("volume", (inputVolume != null) ? inputVolume : "");
				model.put("summaryId", "");
				model.put("volumeDigitized", "false");
			}
		} catch (ApplicationThrowable aex) {
			model.put("volume", (inputVolume != null) ? inputVolume : "");
			model.put("summaryId", "");
			model.put("volumeDigitized", "false");
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method can act  
	 *  
	 * @param volNum Volume Id
	 * @param volLeText Volume Filza
	 * @return ModelAndView containing input params and summaryId.
	 */
	@RequestMapping(value = "/de/volbase/FindVolume", method = RequestMethod.GET)
	public ModelAndView findVolume(
			@RequestParam(value="volume", required=false) String volume,
			@RequestParam(value="volNum", required=false) Integer volNum, 
			@RequestParam(value="volLetExt", required=false) String volLetExt) {
		
 		if (volNum != null) {
			return findVolume(volNum, volLetExt);
		}
		
		return findVolume(volume);
	}

	/**
	 * 
	 * @param summaryId
	 * @return
	 */
	@RequestMapping(value = "/src/volbase/GetLinkedForum", method = RequestMethod.GET)
	public ModelAndView getLinkedForum(@RequestParam(value="summaryId") Integer summaryId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			Forum forum = getVolBaseService().getVolumeForum(summaryId);
			
			if (forum != null && !forum.getLogicalDelete()) {
				model.put("isPresent", Boolean.TRUE.toString());
				model.put("forumId", forum.getForumId().toString());
				model.put("forumUrl", HtmlUtils.getShowForumUrl(forum));
				model.put("discussions", forum.getTopicsNumber());
				model.put("forumUrlCompleteDOM", HtmlUtils.getShowForumCompleteDOMUrl(forum));
			} else {
				model.put("isPresent", Boolean.FALSE.toString());
			}

		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * This method returns a list of seriesList. 
	 *  
	 * @param text Text to search in title, subTitle1 and subTitle2
	 * @return ModelAndView containing seriesList.
	 */
	@RequestMapping(value = "/de/volbase/SearchSeriesList", method = RequestMethod.GET)
	public ModelAndView searchSeriesList(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			List<SerieList> series = getVolBaseService().searchSeriesList(query);
			model.put("query", query);
			model.put("data", ListBeanUtils.transformList(series, "seriesRefNum"));
			model.put("suggestions", ListBeanUtils.toStringListWithConcatenationFields(series, "title/subTitle1/subTitle2", "/", "/", Boolean.TRUE));

		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/src/volbase/ShowDocumentsRelatedVolume.json", method = RequestMethod.GET)
	public ModelAndView showDocumentsRelatedPerson(
			@RequestParam(value="sSearch") String alias,
			@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			@RequestParam(value="iDisplayStart") Integer firstRecord,
			@RequestParam(value="iDisplayLength") Integer length) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.DOCUMENT);
		
		try {
			page = getVolBaseService().searchDocumentsRelated(alias, paginationFilter);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}
		
		List resultList = new ArrayList(0);
		for (Document currentDocument : (List<Document>)page.getList()) {
			List singleRow = new ArrayList(0);
			if (currentDocument.getSenderPeople() != null){
				if(!currentDocument.getSenderPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable")) {
					singleRow.add(currentDocument.getSenderPeople().getMapNameLf());
				} else {
					singleRow.add("Person Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			if (currentDocument.getRecipientPeople() != null) {
				if(!currentDocument.getRecipientPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable")) {
					singleRow.add(currentDocument.getRecipientPeople().getMapNameLf());
				} else {
					singleRow.add("Person Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			if(currentDocument.getYearModern() != null) {
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getYearModern(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			} else {
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getDocYear(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			}
			
			if (currentDocument.getSenderPlace() != null) {
				if (!currentDocument.getSenderPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable")) {
					singleRow.add(currentDocument.getSenderPlace().getPlaceName());
				} else {
					singleRow.add("Place Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			if (currentDocument.getRecipientPlace() != null) {
				if (!currentDocument.getRecipientPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable")) {
					singleRow.add(currentDocument.getRecipientPlace().getPlaceName());
				} else {
					singleRow.add("Place Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			StringBuilder titleLastColumn = new StringBuilder();
			StringBuilder lastColumn = new StringBuilder();
			lastColumn.append("<b>" + currentDocument.getVolume().getMDP());
			lastColumn.append("</b><br />");
			titleLastColumn.append("Volume " + currentDocument.getVolume().getMDP() + ", ");
			lastColumn.append("(");
			if (currentDocument.getInsertNum() != null && !currentDocument.getInsertNum().equals("")) {
				lastColumn.append(currentDocument.getInsertNum() + "/");
				titleLastColumn.append("Insert " + currentDocument.getInsertNum() + ", ");
				if (currentDocument.getInsertLet() != null) {
					lastColumn.append(currentDocument.getInsertLet());
					titleLastColumn.append("Part " + currentDocument.getInsertLet() + ", ");
				} else {
					lastColumn.append("-");
				}
			} else {
				lastColumn.append("-/-");
			}
			lastColumn.append(")<br />");
			lastColumn.append("<b>");
			if (currentDocument.getFolioNum() != null) {
				lastColumn.append(currentDocument.getFolioNum());
				titleLastColumn.append("Folio " + currentDocument.getFolioNum());
				if (currentDocument.getFolioMod() != null) {
					lastColumn.append(currentDocument.getFolioMod());
					titleLastColumn.append(currentDocument.getFolioMod());
				}
				if (currentDocument.getFolioRectoVerso() != null) {
					lastColumn.append(" ").append(currentDocument.getFolioRectoVerso().toString());
					titleLastColumn.append(" ").append(currentDocument.getFolioRectoVerso().toString());
				}
			} else {
				lastColumn.append("NNF");
				titleLastColumn.append("Folio NNF");
			}
			lastColumn.append("</b>");
			if (currentDocument.getVolume().getDigitized()) {
				lastColumn.append("&nbsp;" + HtmlUtils.getImageDigitized());
			}
			singleRow.add(lastColumn.toString());

			resultList.add(HtmlUtils.showDocumentRelated(singleRow, currentDocument.getEntryId(), DocumentUtils.toMDPInsertFolioFormat(currentDocument), titleLastColumn.toString()));
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		
		return new ModelAndView("responseOK", model);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/src/volbase/ShowVettingHistoryVolume.json", method = RequestMethod.GET)
	public ModelAndView ShowVettingHistoryVolume(
			@RequestParam(value="summaryId") Integer summaryId,
			@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			@RequestParam(value="iDisplayStart") Integer firstRecord,
			@RequestParam(value="iDisplayLength") Integer length) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		try {
			page = getVolBaseService().searchVettingHistoryVolume(summaryId,
					paginationFilter);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList();
		for (VettingHistory currentVettingHistory : (List<VettingHistory>) page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(simpleDateFormat.format(currentVettingHistory.getDateAndTime()));
			singleRow.add(currentVettingHistory.getDescription());
			singleRow.add(currentVettingHistory.getUser().getAccount());

			resultList.add(singleRow);
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK", model);
	}

}