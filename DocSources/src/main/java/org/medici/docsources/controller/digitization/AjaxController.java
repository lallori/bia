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
package org.medici.docsources.controller.digitization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.SchedoneSearch;
import org.medici.docsources.common.util.HtmlUtils;
import org.medici.docsources.common.util.ListBeanUtils;
import org.medici.docsources.common.util.VolumeUtils;
import org.medici.docsources.domain.Digitization;
import org.medici.docsources.domain.Schedone;
import org.medici.docsources.domain.SerieList;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.digitization.DigitizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for Digitization.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller("DigitizationAjaxController")
public class AjaxController {
	@Autowired
	private DigitizationService digitizationService;

	/**
	 * @param digitizationService the digitizationService to set
	 */
	public void setDigitizationService(DigitizationService digitizationService) {
		this.digitizationService = digitizationService;
	}

	/**
	 * @return the digitizationService
	 */
	public DigitizationService getDigitizationService() {
		return digitizationService;
	}


	/**
	 * 
	 * @param schedoneId
	 * @return
	 */
	@RequestMapping(value = "/digitization/FindSchedone", method = RequestMethod.GET)
	public ModelAndView FindSchedone(@RequestParam(value="schedoneId", required=true) Integer schedoneId) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try{
			Schedone schedone = getDigitizationService().findSchedone(schedoneId);
			if(schedone!= null){
				model.put("schedoneId", schedone.getSchedoneId());
			}else{
				model.put("schedoneId", "");
			}			
		}catch(ApplicationThrowable th){
			model.put("schedoneId", "");
		}

		return new ModelAndView("responseOK", model);		
	}
	
	/**
	 * 
	 * @param searchType
	 * @param volNum
	 * @param volNumBetween
	 * @param active
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	@RequestMapping(value = "/digitization/BrowseActivatedVolumes.json", method = RequestMethod.GET)
	public ModelAndView browseActivatedVolumes(@RequestParam(value="searchType", required=false) String searchType,
											@RequestParam(value="volNum", required=false) Integer volNum,
											@RequestParam(value="volNumBetween", required=false) Integer volNumBetween,
											@RequestParam(value="active", required=false) String active,
								   		 	@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
								   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Schedone> ifSchedone = new HashMap<String, Schedone>();

		Boolean activated;
		if(active.equals("Yes"))
			activated = Boolean.TRUE;
		else
			activated = Boolean.FALSE;
		
		List<Integer> volNums = new ArrayList<Integer>();
		List<String> volLetExts = new ArrayList<String>();

		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length, sortingColumnNumber, sortingDirection);

		try {
			if(searchType.equals("Exactly")){
				page = getDigitizationService().searchActiveVolumes(volNum, volNum, activated, paginationFilter);
				
				ifSchedone = getDigitizationService().findSchedoniMapByVolume(volNum, volNum);
			}else if(searchType.equals("Between")){
				page = getDigitizationService().searchActiveVolumes(volNum, volNumBetween, activated, paginationFilter);
				
				ifSchedone = getDigitizationService().findSchedoniMapByVolume(volNum, volNumBetween);
			}else if(searchType.equals("All")){
				page = getDigitizationService().searchAllActiveVolumes(activated, paginationFilter);
				
				for (Digitization currentDigitization : (List<Digitization>)page.getList()) {
					volNums.add(currentDigitization.getVolNum());
					volLetExts.add(currentDigitization.getVolLetExt());
				}
				
				if(volNums != null && volNums.size() > 0){
					ifSchedone = getDigitizationService().findSchedoniMapByVolume(volNums.get(0), volNums.get(volNums.size() - 1));
				}
			}
			
//			page = getDigitizationService().searchSchedones(new SchedoneSearch(searchType, volNum, volNumBetween), paginationFilter);
			
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList();
		for(Digitization currentDigitization : (List<Digitization>)page.getList()){
			List singleRow = new ArrayList();
			//MDP
			if(currentDigitization.getVolLetExt() != null){
				singleRow.add(currentDigitization.getVolNum().toString() + currentDigitization.getVolLetExt().toString());
			}else{
				singleRow.add(currentDigitization.getVolNum().toString());
			}
			
			//Schedone
			Schedone currentSchedone = ifSchedone.get(currentDigitization.getVolNum() + currentDigitization.getVolLetExt());
			if(currentSchedone != null && currentSchedone.getSchedoneId() != 0){
				singleRow.add("YES");
			}else{
				singleRow.add("NO");
			}
			//Active
			if(currentDigitization.getActive()){
				singleRow.add("YES");
			}else{
				singleRow.add("NO");
			}
			
			if(currentDigitization.getActive()){
				resultList.add(HtmlUtils.showDigitizedVolumeDeactiveIt(singleRow, currentDigitization));
			}else{				
				resultList.add(HtmlUtils.showDigitizedVolumeActiveIt(singleRow, currentDigitization));
			}
		}
//		for (Schedone currentSchedone : (List<Schedone>)page.getList()) {
//			List singleRow = new ArrayList();
//			// MDP
//			singleRow.add(HtmlUtils.showSchedoneMDP(currentSchedone));         
//			// Catalog Description
//			singleRow.add(HtmlUtils.showSchedoneDescription(currentSchedone));
//			// Active
//			singleRow.add(HtmlUtils.showSchedoneActive(currentSchedone));      
//			// Edit it
//			singleRow.add(HtmlUtils.showSchedoneEditIt(currentSchedone));      
//			// Deactive it
//			singleRow.add(HtmlUtils.showSchedoneDeactivateIt(currentSchedone));     
//
//			resultList.add(singleRow);
//		}
		
		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		
		return new ModelAndView("responseOK", model);		
	}

	/**
	 * 
	 * @param searchType
	 * @param volNum
	 * @param volNumBetween
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	@RequestMapping(value = "/digitization/BrowseDigitizedVolumes.json", method = RequestMethod.GET)
	public ModelAndView browseDigitizedVolumes(@RequestParam(value="searchType", required=false) String searchType,
											@RequestParam(value="volNum", required=false) Integer volNum,
											@RequestParam(value="volLetExt", required=false) String volLetExt,
											@RequestParam(value="volNumBetween", required=false) Integer volNumBetween,
								   		 	@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
								   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Schedone> ifSchedone = new HashMap<String, Schedone>();
		Map<String, Boolean> ifDigitized = new HashMap<String, Boolean>();
		List<Integer> volNums = new ArrayList<Integer>();
		List<String> volLetExts = new ArrayList<String>();
		Volume volumeExactly = null;

		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length, sortingColumnNumber, sortingDirection);

		try {
			if(searchType.equals("Exactly")){
				if(volLetExt == null || volLetExt.equals("")){
					volumeExactly = getDigitizationService().searchVolume(volNum, null);
				}else{
					volumeExactly = getDigitizationService().searchVolume(volNum, volLetExt);
				}
				List<Volume> listForPage = new ArrayList<Volume>();
				listForPage.add(volumeExactly);
				page = new Page(paginationFilter);
				page.setList(listForPage);
				page.setTotal(new Long(listForPage.size()));
				
				ifSchedone = getDigitizationService().findSchedoniMapByVolume(volNum, volNum);
			}else if(searchType.equals("Between")){
				page = getDigitizationService().searchVolumes(volNum, volNumBetween, paginationFilter);
				
				ifSchedone = getDigitizationService().findSchedoniMapByVolume(volNum, volNumBetween);
			}else if(searchType.equals("All")){
				page = getDigitizationService().searchSchedones(new SchedoneSearch(), paginationFilter);
				
				for (Schedone currentSchedone : (List<Schedone>)page.getList()) {
					volNums.add(currentSchedone.getVolNum());
					volLetExts.add(currentSchedone.getVolLetExt());
				}
				
				ifDigitized = getDigitizationService().findVolumesDigitizedBySchedoni(volNums, volLetExts);
			}
			
//			page = getDigitizationService().searchSchedones(new SchedoneSearch(searchType, volNum, volNumBetween), paginationFilter);
			
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList();
		if(searchType.equals("Exactly") || searchType.equals("Between")){
			if(page.getList().size() > 0){
				for(Volume currentVolume : (List<Volume>)page.getList()){
					List singleRow = new ArrayList();
					//MDP
					singleRow.add(currentVolume.getMDP());
					//Schedone
					Schedone currentSchedone;
					if(currentVolume.getVolLetExt() != null)
						currentSchedone = ifSchedone.get(currentVolume.getVolNum() + currentVolume.getVolLetExt());
					else
						currentSchedone = ifSchedone.get(currentVolume.getVolNum().toString());
					if(currentSchedone != null && currentSchedone.getSchedoneId() != 0){
						singleRow.add("YES");
					}else{
						singleRow.add("NO");
					}
					//Digitized
					if(currentVolume.getDigitized()){
						singleRow.add("YES");
					}else{
						singleRow.add("NO");
					}
				
					if(currentSchedone != null && currentSchedone.getSchedoneId() != 0){
						resultList.add(HtmlUtils.showSchedone(singleRow, currentSchedone));
					}else{				
						resultList.add(singleRow);
					}
				}
			}else{
				
			}
		}else if(searchType.equals("All")){
			for (Schedone currentSchedone : (List<Schedone>)page.getList()) {
				List singleRow = new ArrayList();
				//MDP
				singleRow.add(HtmlUtils.showSchedoneMDP(currentSchedone));
				//Schedone
				singleRow.add("YES");
				//Digitized
				if(ifDigitized.get(VolumeUtils.toMDPFormat(currentSchedone.getVolNum(), currentSchedone.getVolLetExt()))){
					singleRow.add("YES");
				}else{
					singleRow.add("NO");
				}
				resultList.add(HtmlUtils.showSchedone(singleRow, currentSchedone));
			}
		}
//		for (Schedone currentSchedone : (List<Schedone>)page.getList()) {
//			List singleRow = new ArrayList();
//			// MDP
//			singleRow.add(HtmlUtils.showSchedoneMDP(currentSchedone));         
//			// Catalog Description
//			singleRow.add(HtmlUtils.showSchedoneDescription(currentSchedone));
//			// Active
//			singleRow.add(HtmlUtils.showSchedoneActive(currentSchedone));      
//			// Edit it
//			singleRow.add(HtmlUtils.showSchedoneEditIt(currentSchedone));      
//			// Deactive it
//			singleRow.add(HtmlUtils.showSchedoneDeactivateIt(currentSchedone));     
//
//			resultList.add(singleRow);
//		}
		
		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		
		return new ModelAndView("responseOK", model);		
	}
	
	/**
	 * This method returns a list of seriesList. 
	 *  
	 * @param text Text to search in title, subTitle1 and subTitle2
	 * @return ModelAndView containing seriesList.
	 */
	@RequestMapping(value = "/digitization/SearchSeriesList.json", method = RequestMethod.GET)
	public ModelAndView searchSeriesList(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			List<SerieList> series = getDigitizationService().searchSeriesList(query);
			model.put("query", query);
			model.put("data", ListBeanUtils.transformList(series, "seriesRefNum"));
			model.put("suggestions", ListBeanUtils.toStringListWithConcatenationFields(series, "title/subTitle1/subTitle2", "/", "/", Boolean.TRUE));

		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
}