/*
 * ShowManageImagesForLessons.java
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
package org.medici.bia.controller.teaching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.medici.bia.command.teaching.ShowManageImagesForLessonsCommand;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.service.teaching.TeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Controller
@RequestMapping("/teaching/ShowManageImagesForLessons")
public class ShowManageImagesForLessons {
	
	private static final Integer ELEMENTS_FOR_PAGE = 10;
	
	@Autowired
	private TeachingService teachingService;
	
	public TeachingService getTeachingService() {
		return teachingService;
	}

	public void setTeachingService(TeachingService teachingService) {
		this.teachingService = teachingService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setup(@ModelAttribute("command") ShowManageImagesForLessonsCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		PaginationFilter paginationFilter = new PaginationFilter(
				command.getFirstRecord() != null ? command.getFirstRecord() : 0, 
				command.getElementsForPage() != null ? command.getElementsForPage() : ELEMENTS_FOR_PAGE, 
				null);
		
		if (paginationFilter.getElementsForPage() == null) {
			paginationFilter.setElementsForPage(ELEMENTS_FOR_PAGE);
		}
		paginationFilter.setThisPage(command.getPageNumber() != null ? command.getPageNumber() : 1);
		
		List<String> tableOrderFields = convertTableField(
				command.getOrderByTableField() != null && command.getAscendingOrder() != null ? command.getOrderByTableField() : 0);
		for (String sortableField : tableOrderFields) {
			paginationFilter.addSortingCriteria(sortableField, command.getAscendingOrder() == null || command.getAscendingOrder() ? "ASC" : "DESC");
		}
		
		model.put("images", getTeachingService().getImagesForLessons(paginationFilter, StringUtils.split(command.getFilter(), " ")));
		
		return new ModelAndView("teaching/ShowManageImagesForLessons", model);
	}
	
	/* Privates */
	
	private List<String> convertTableField(Integer tableFieldIndex) {
		List<String> sortableFields = new ArrayList<String>();
		switch (tableFieldIndex) {
			case 0:
				sortableFields.add("imageId");
				break;
			case 1:
				sortableFields.add("imageTitle");
				break;
			case 2:
				sortableFields.add("dateCreated");
				break;
			default:
				sortableFields.add("imageId");
				break;
		}
		return sortableFields;
	}

}
