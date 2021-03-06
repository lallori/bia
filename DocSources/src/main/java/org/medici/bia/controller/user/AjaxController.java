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
package org.medici.bia.controller.user;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.SearchFromLast;
import org.medici.bia.common.search.SearchFromLast.FromLast;
import org.medici.bia.common.search.SearchFromLast.SearchPerimeter;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.DocumentUtils;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.common.util.ListBeanUtils;
import org.medici.bia.domain.Annotation;
import org.medici.bia.domain.Country;
import org.medici.bia.domain.Image.ImageType;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserHistory.Category;
import org.medici.bia.domain.UserMarkedListElement;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for User Actions.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller("UserAjaxController")
public class AjaxController {
	@Autowired
	private UserService userService;
	
	/**
	 * This method will make a check if account passed as parameter is not 
	 * already present in user store.
	 *  
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/user/ajax/IsAccountAvailable", method = RequestMethod.GET)
	public @ResponseBody String checkAccount(HttpServletRequest httpServletRequest, @RequestParam("account") String account) {
		try {
			return (getUserService().isAccountAvailable(account)).toString();
		} catch (ApplicationThrowable aex) {
			return Boolean.FALSE.toString();
		}
	}
	
	/**
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/user/GetPortraitUserInformation", method = RequestMethod.GET) 
	public ModelAndView getPortraitUserInformation(@RequestParam(value="account") String account) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			User user = getUserService().findUser(account);
			if (user != null) {
				if (user.getPortrait()) {
					BufferedImage bufferedImage = getUserService().getPortraitUser(user.getPortraitImageName());

					model.put("portraitWidth", bufferedImage.getWidth());
					model.put("portraitHeight", bufferedImage.getHeight());
				} else {
					model.put("portraitWidth", new Integer(0));
					model.put("portraitHeight", new Integer(0));
				}
			} else {
				model.put("portraitWidth", new Integer(0));
				model.put("portraitHeight", new Integer(0));
			}
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}
	
	@RequestMapping(value = "/user/LastEntryUser.json", method = RequestMethod.GET)
	public ModelAndView lastEntryUser(){
		Map<String, Object> model = new HashMap<String, Object>(0);
		UserHistory lastEntry = null;
		try{
			lastEntry = getUserService().searchLastUserHistoryBaseEntry();
			
			// Check is necessary for new user which has not history... 
			if (lastEntry != null) {
				model.put("category", lastEntry.getCategory().toString());
			} else {
				model.put("category", "");
			}
			
			return new ModelAndView("responseOK", model);
		}catch(ApplicationThrowable aex){
			return new ModelAndView("responseKO", model);
		}
		
	}

	/** This method returns 4 elements for History Category View Preview.
	 * 
	 * @param searchType
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/MyHistoryFirstFourElementsByCategoryPagination.json", method = RequestMethod.GET)
	public ModelAndView myHistoryFirstFourElementsByCategoryPagination(@RequestParam(value="category") Category category,
	   		 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
	   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
	   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
		     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Page page = null;

		try {
			page = getUserService().searchUserHistory(category, paginationFilter, 4);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		
		 List<Object> resultList = new ArrayList<Object>();
		  for (UserHistory currentUserHistory : (List<UserHistory>)page.getList()) {
		   List<String> singleRow = new ArrayList<String>();
		   if (currentUserHistory.getCategory().equals(Category.DOCUMENT)) {
				singleRow.add(simpleDateFormat.format(currentUserHistory.getDateAndTime()));
			    singleRow.add(currentUserHistory.getAction().toString());
			    singleRow.add(DocumentUtils.toMDPInsertFolioFormat(currentUserHistory.getDocument()));
			    resultList.add(HtmlUtils.showDocument(singleRow, currentUserHistory.getDocument().getEntryId()));
		   } else if (currentUserHistory.getCategory().equals(Category.PEOPLE)) {
			   	singleRow.add(simpleDateFormat.format(currentUserHistory.getDateAndTime()));
			   	singleRow.add(currentUserHistory.getAction().toString());
			   	//MD: This code is for prevent a string too long.
			   	if(currentUserHistory.getPerson().getMapNameLf().length() < 38) {
			   		singleRow.add(currentUserHistory.getPerson().getMapNameLf());	
			   	} else {
			   		singleRow.add(currentUserHistory.getPerson().getMapNameLf().substring(0, 35) + "...");
			   	}
			    resultList.add(HtmlUtils.showPeople(singleRow, currentUserHistory.getPerson().getPersonId()));
		   } else if (currentUserHistory.getCategory().equals(Category.PLACE)) {
			   singleRow.add(simpleDateFormat.format(currentUserHistory.getDateAndTime()));
			   singleRow.add(currentUserHistory.getAction().toString());
			   //MD: This code is for prevent a string too long
			   String placeNameAndParent = currentUserHistory.getPlace().getPlaceName() + " / " + currentUserHistory.getPlace().getPlParent();
			   if(placeNameAndParent.length() < 40) {
				   singleRow.add(placeNameAndParent);
			   } else {
				   singleRow.add(placeNameAndParent.substring(0, 37) + "...");
			   }
			   resultList.add(HtmlUtils.showPlaceMarkedList(singleRow, currentUserHistory.getPlace().getPlaceAllId()));
		   } else if (currentUserHistory.getCategory().equals(Category.VOLUME)) {
			   singleRow.add(simpleDateFormat.format(currentUserHistory.getDateAndTime()));
			   singleRow.add(currentUserHistory.getAction().toString());
			   singleRow.add(currentUserHistory.getVolume().getMDP());
			   resultList.add(HtmlUtils.showVolume(singleRow, currentUserHistory.getVolume().getSummaryId()));
		   }
		  }

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		return new ModelAndView("responseOK", model);
	}

	/**
	 * 
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/MyHistoryPagination.json", method = RequestMethod.GET)
	public ModelAndView myHistoryPagination(@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Page page = null;


		try {
			page = getUserService().searchUserHistory(paginationFilter);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List<Object> resultList = new ArrayList<Object>();
		for (UserHistory currentUserHistory : (List<UserHistory>)page.getList()) {
			List<String> singleRow = new ArrayList<String>();
			singleRow.add(simpleDateFormat.format(currentUserHistory.getDateAndTime()));
			singleRow.add(currentUserHistory.getCategory().toString());
			singleRow.add(currentUserHistory.getAction().toString());
			if (currentUserHistory.getCategory().equals(Category.DOCUMENT)) {
				singleRow.add(DocumentUtils.toMDPInsertFolioFormat(currentUserHistory.getDocument()));
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				resultList.add(HtmlUtils.showDocument(singleRow, currentUserHistory.getDocument().getEntryId()));
			}  else if (currentUserHistory.getCategory().equals(Category.VOLUME)) {
				singleRow.add("");
				singleRow.add(currentUserHistory.getVolume().getMDP());
				singleRow.add("");
				singleRow.add("");
				resultList.add(HtmlUtils.showVolume(singleRow, currentUserHistory.getVolume().getSummaryId()));
			} else if (currentUserHistory.getCategory().equals(Category.PLACE)) {
				singleRow.add("");
				singleRow.add("");
				singleRow.add(currentUserHistory.getPlace().getPlaceNameFull());
				singleRow.add("");
				resultList.add(HtmlUtils.showPlace(singleRow, currentUserHistory.getPlace().getPlaceAllId()));
			} else if (currentUserHistory.getCategory().equals(Category.PEOPLE)) {
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				singleRow.add(currentUserHistory.getPerson().getMapNameLf());
				resultList.add(HtmlUtils.showPeople(singleRow, currentUserHistory.getPerson().getPersonId()));
			}else if (currentUserHistory.getCategory().equals(Category.SEARCH_DOCUMENT)){
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				resultList.add(HtmlUtils.showSearch(singleRow, currentUserHistory.getIdUserHistory()));
			}else if (currentUserHistory.getCategory().equals(Category.SEARCH_PEOPLE)){
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				resultList.add(HtmlUtils.showSearch(singleRow, currentUserHistory.getIdUserHistory()));
			}else if (currentUserHistory.getCategory().equals(Category.SEARCH_PLACE)){
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				resultList.add(HtmlUtils.showSearch(singleRow, currentUserHistory.getIdUserHistory()));
			}else if (currentUserHistory.getCategory().equals(Category.SEARCH_VOLUME)){
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				resultList.add(HtmlUtils.showSearch(singleRow, currentUserHistory.getIdUserHistory()));
			}
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK", model);
	}

	/**
	 * 
	 * @param searchType
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/MyHistoryByCategoryPagination.json", method = RequestMethod.GET)
	public ModelAndView myHistoryReportByCategoryPagination(@RequestParam(value="category") Category category,
	   		 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
	   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
	   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
		     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Page page = null;

		try {
			page = getUserService().searchUserHistory(category, paginationFilter);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List<Object> resultList = new ArrayList<Object>();
		for (UserHistory currentUserHistory : (List<UserHistory>)page.getList()) {
			List<String> singleRow = new ArrayList<String>();
			singleRow.add(simpleDateFormat.format(currentUserHistory.getDateAndTime()));
			singleRow.add(currentUserHistory.getAction().toString());
			if (currentUserHistory.getCategory().equals(Category.DOCUMENT)) {
				singleRow.add(DocumentUtils.toMDPInsertFolioFormat(currentUserHistory.getDocument()));

				if (currentUserHistory.getDocument().getSenderPeople() != null) {
					singleRow.add(currentUserHistory.getDocument().getSenderPeople().toString());
				} else {
					singleRow.add("");
				}

				if (currentUserHistory.getDocument().getRecipientPeople() != null) {
					singleRow.add(currentUserHistory.getDocument().getRecipientPeople().toString());
				}else {
					singleRow.add("");
				}
				
				resultList.add(HtmlUtils.showDocument(singleRow, currentUserHistory.getDocument().getEntryId()));
			}  else if (currentUserHistory.getCategory().equals(Category.VOLUME)) {
				singleRow.add(currentUserHistory.getVolume().getMDP());
				singleRow.add((currentUserHistory.getVolume().getSerieList() == null) ? "" : currentUserHistory.getVolume().getSerieList().toString());
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentUserHistory.getVolume().getStartYear(), currentUserHistory.getVolume().getStartMonthNum(), currentUserHistory.getVolume().getStartDay()));
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentUserHistory.getVolume().getEndYear(), currentUserHistory.getVolume().getEndMonthNum(), currentUserHistory.getVolume().getEndDay()));
				if(currentUserHistory.getVolume().getDigitized().equals(Boolean.TRUE)) {
					singleRow.add("YES");
				} else {
					singleRow.add("NO");
				}
				resultList.add(HtmlUtils.showVolume(singleRow, currentUserHistory.getVolume().getSummaryId()));
			} else if (currentUserHistory.getCategory().equals(Category.PLACE)) {
				singleRow.add(currentUserHistory.getPlace().getPlaceNameFull());
				singleRow.add(currentUserHistory.getPlace().getPlType());
				resultList.add(HtmlUtils.showPlace(singleRow, currentUserHistory.getPlace().getPlaceAllId()));
			} else if (currentUserHistory.getCategory().equals(Category.PEOPLE)) {
				singleRow.add(currentUserHistory.getPerson().getMapNameLf());
				singleRow.add(DateUtils.getStringDate(currentUserHistory.getPerson().getBornYear(), currentUserHistory.getPerson().getBornMonth(), currentUserHistory.getPerson().getBornDay()));
				singleRow.add(DateUtils.getStringDate(currentUserHistory.getPerson().getDeathYear(), currentUserHistory.getPerson().getDeathMonth(), currentUserHistory.getPerson().getDeathDay()));
				resultList.add(HtmlUtils.showPeople(singleRow, currentUserHistory.getPerson().getPersonId()));
			}
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		return new ModelAndView("responseOK", model);
	}

	/**
	 * 
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/MyMarkedListPagination.json", method = RequestMethod.GET)
	public ModelAndView myMarkedListPagination(@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Page page = null;


		try {
			page = getUserService().searchUserMarkedList(paginationFilter);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List<Object> resultList = new ArrayList<Object>();
		for (UserMarkedListElement currentElement : (List<UserMarkedListElement>)page.getList()) {
			List<String> singleRow = new ArrayList<String>();
			singleRow.add(simpleDateFormat.format(currentElement.getDateCreated()));
			if (currentElement.getDocument() != null) {
				singleRow.add("Document");
				singleRow.add(DocumentUtils.toMDPInsertFolioFormat(currentElement.getDocument()));
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				singleRow.add("<input type=\"checkbox\" name=\"unpaginated\" idElement=\"" + currentElement.getId() + "\" >");
				resultList.add(HtmlUtils.showDocument(singleRow, currentElement.getDocument().getEntryId()));
			}  else if (currentElement.getVolume() != null) {
				singleRow.add("Volume");
				singleRow.add("");
				singleRow.add(currentElement.getVolume().getMDP());
				singleRow.add("");
				singleRow.add("");
				singleRow.add("<input type=\"checkbox\" name=\"unpaginated\" idElement=\"" + currentElement.getId() + "\" >");
				resultList.add(HtmlUtils.showVolume(singleRow, currentElement.getVolume().getSummaryId()));
			} else if (currentElement.getPlace() != null) {
				singleRow.add("Place");
				singleRow.add("");
				singleRow.add("");
				singleRow.add(currentElement.getPlace().getPlaceNameFull());
				singleRow.add("");
				singleRow.add("<input type=\"checkbox\" name=\"unpaginated\" idElement=\"" + currentElement.getId() + "\" >");
				resultList.add(HtmlUtils.showPlaceMarkedList(singleRow, currentElement.getPlace().getPlaceAllId()));
			} else if (currentElement.getPerson() != null) {
				singleRow.add("Person");
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				singleRow.add(currentElement.getPerson().getMapNameLf());
				singleRow.add("<input type=\"checkbox\" name=\"unpaginated\" idElement=\"" + currentElement.getId() + "\" >");
				resultList.add(HtmlUtils.showPeople(singleRow, currentElement.getPerson().getPersonId()));
			}
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * 
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/PersonalAnnotationsPagination.json", method = RequestMethod.GET)
	public ModelAndView personalAnnotationsPagination(@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Page page = null;


		try {
			page = getUserService().searchUserPersonalAnnotations(paginationFilter);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List<Object> resultList = new ArrayList<Object>();
		for (Annotation currentAnnotation : (List<Annotation>)page.getList()) {
			List<String> singleRow = new ArrayList<String>();
			singleRow.add(simpleDateFormat.format(currentAnnotation.getLastUpdate()));
			singleRow.add(currentAnnotation.getTitle());
			singleRow.add(currentAnnotation.getText());
			if(currentAnnotation.getImage().getVolLetExt() != null){
				singleRow.add(currentAnnotation.getImage().getVolNum() + currentAnnotation.getImage().getVolLetExt());
			}else{
				singleRow.add(currentAnnotation.getImage().getVolNum().toString());
			}
			if(currentAnnotation.getImage().getImageType().equals(ImageType.C)){
				singleRow.add("Folio");
			}else if(currentAnnotation.getImage().getImageType().equals(ImageType.R)){
				singleRow.add("Index of names");
			}else if(currentAnnotation.getImage().getImageType().equals(ImageType.A)){
				singleRow.add("Allegato");
			}else if(currentAnnotation.getImage().getImageType().equals(ImageType.G)){
				singleRow.add("Guardia");
			}else if(currentAnnotation.getImage().getImageType().equals(ImageType.O)){
				singleRow.add("Other Type");
			}else{
				singleRow.add("");
			}
			singleRow.add(currentAnnotation.getImage().getImageProgTypeNum().toString());
			
			resultList.add(HtmlUtils.showPersonalAnnotationsExplorer(singleRow, currentAnnotation.getImage()));
			
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK", model);
	}
	
	
	/**
	 * This method calculate password rating. 
	 *  
	 * @param password Password to rate.
	 * @return Integer Rating level
	 */
	@RequestMapping(value = "/user/ajax/RatePassword", method = RequestMethod.GET)
	public @ResponseBody Integer ratePassword(@RequestParam("quavadis") String password) {
		return getUserService().ratePassword(password);
	}
	
	/**
	 * This method will search Country entity by name field. 
	 * @param name Description field of the country
	 * @return ModelAndView containing country result list.
	 */
	@RequestMapping(value = "/user/FindCountries", method = RequestMethod.GET)
	public ModelAndView searchCountries(@RequestParam("query") String name) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			List<Country> countries = getUserService().findCountries(name);
			model.put("query", name);
			model.put("suggestions", ListBeanUtils.transformList(countries, "name"));
			model.put("data", ListBeanUtils.transformList(countries, "code"));
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	
	/**
	 * This method will search Country entity by name field. 
	 * @param name Description field of the country
	 * @return ModelAndView containing country result list.
	 */
	@RequestMapping(value = "/user/ajax/FindCountries", method = RequestMethod.GET)
	public ModelAndView searchCountries(@RequestParam("query") String name, HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		httpServletRequest.setAttribute("persistentAccessLogDisabled", Boolean.TRUE);

		try {
			List<Country> countries = getUserService().findCountries(name);
			model.put("query", name);
			model.put("suggestions", ListBeanUtils.transformList(countries, "name"));
			model.put("data", ListBeanUtils.transformList(countries, "code"));
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * 
	 * @param alias
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/user/ajax/PaginationUser.do", method = RequestMethod.GET)
	public String searchPagination(@RequestParam("sSearch") String alias, Model model) {
		List<User> searchResults = null;

		User user = new User();
		user.setAccount(alias);
		user.setFirstName(alias);
		user.setLastName(alias);
		user.setOrganization(alias);
		user.setMail(alias);

		try {
			searchResults = userService.findUsers(user);
		} catch (ApplicationThrowable aex) {
		}

		// Ordering results...
		PropertyComparator.sort(searchResults, new MutableSortDefinition("firstName", true, true));
		searchResults = Collections.unmodifiableList(searchResults);
		// Paging results...
		PagedListHolder<User> pagedListHolder = new PagedListHolder<User>(searchResults);

		Integer page = Integer.valueOf(1);
		pagedListHolder.setPage(page);
		int pageSize = 10;
		pagedListHolder.setPageSize(pageSize);
		//SearchResult userSearchPagination = new SearchResult(searchResults, 1, 10);
		//model.addAttribute(userSearchPagination);
		List test = new ArrayList(0);
		for (int i=0; i<10; i++) {
			List singleRow = new ArrayList(0);
			singleRow.add("Lorenzo");
			singleRow.add("Pasquinelli");
			singleRow.add("pasquinelli");
			singleRow.add("pasquinelli@gmail.com");
			test.add(singleRow);
		}

		model.addAttribute("iEcho", 1);
		model.addAttribute("iTotalDisplayRecords", 10);
		model.addAttribute("iTotalRecords", 24);
		model.addAttribute("aaData", test);
		return "OK";
	}

	@RequestMapping(value= "/user/SearchUsers", method = RequestMethod.GET)
	public ModelAndView searchUsers(@RequestParam("query") String query){
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try{
			List<User> users = getUserService().searchUsers(query);
			model.put("query", query);
			model.put("count", users.size());
			model.put("data", ListBeanUtils.transformList(users, "account"));
			List<String> names = new ArrayList<String>();
			for(User currentUser : users){
				names.add(currentUser.getFirstName() + " " + currentUser.getLastName() + " - " + currentUser.getAccount());
			}
			model.put("suggestions", names);
		}catch(ApplicationThrowable aex){
			return new ModelAndView("responseKO", model);
		}
		return new ModelAndView("responseOK", model);
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 
	 * @param fromLast
	 * @param searchPerimeter
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@RequestMapping(value = "/user/ArchiveStatisticsFromLast.json", method = RequestMethod.GET)
	public ModelAndView statisticsFromLast( @RequestParam(value="fromLast", required=false) FromLast fromLast,
											@RequestParam(value="searchPerimeter", required=false) SearchPerimeter searchPerimeter,
											@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
								   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		Map<SearchPerimeter, Long> archiveStatistics = new HashMap<SearchPerimeter, Long>(0);

		try {
			archiveStatistics = getUserService().getArchiveStatisticsFromLast(new SearchFromLast(fromLast, searchPerimeter));
		} catch (ApplicationThrowable aex) {
		}

		List<Object> resultList = new ArrayList<Object>();
		List<Object> singleRow = new ArrayList<Object>();
		
		singleRow.add("Document");
		singleRow.add(archiveStatistics.get(SearchPerimeter.DOCUMENT));
		resultList.add(singleRow);
		singleRow.clear();
				
		singleRow.add("People");
		singleRow.add(archiveStatistics.get(SearchPerimeter.PEOPLE));
		resultList.add(singleRow);
		singleRow.clear();

		singleRow.add("Places");
		singleRow.add(archiveStatistics.get(SearchPerimeter.PLACE));
		resultList.add(singleRow);
		singleRow.clear();

		singleRow.add("Volumes");
		singleRow.add(archiveStatistics.get(SearchPerimeter.VOLUME));
		resultList.add(singleRow);
		singleRow.clear();

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", "4");
		model.put("iTotalRecords", "4");
		model.put("aaData", resultList);

		return new ModelAndView("responseOK", model);
	}

}