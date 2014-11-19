/*
 * ShowCoursePeopleController.java
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

import javax.servlet.http.HttpServletRequest;

import org.medici.bia.command.teaching.ShowCoursePeopleCommand;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.teaching.CoursePerson;
import org.medici.bia.domain.CoursePeople;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.teaching.TeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
public class ShowCoursePeopleController {
	
	private static final int PEOPLE_FOR_PAGE = 10;
	
	@Autowired
	private TeachingService teachingService;
	
	/**
	 * @return the teachingService
	 */
	public TeachingService getTeachingService() {
		return teachingService;
	}

	/**
	 * @param teachingService the teachingService to set
	 */
	public void setTeachingService(TeachingService teachingService) {
		this.teachingService = teachingService;
	}

	@RequestMapping(value = {"/teaching/ShowCourseStudents","/teaching/ShowOtherStudents"}, method = RequestMethod.GET)
	public ModelAndView setup(@ModelAttribute("command") ShowCoursePeopleCommand command, BindingResult result, HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			PaginationFilter paginationFilter = new PaginationFilter(
					command.getFirstRecord() != null ? command.getFirstRecord() : 0, 
					command.getPeopleForPage() != null ? command.getPeopleForPage() : PEOPLE_FOR_PAGE, 
					command.getPageTotal());
			
			if (paginationFilter.getElementsForPage() == null) {
				paginationFilter.setElementsForPage(PEOPLE_FOR_PAGE);
			}
			paginationFilter.setThisPage(command.getPageNumber() != null ? command.getPageNumber() : 1);
			
			boolean isCourseStudentServlet = httpServletRequest.getServletPath().equals("/teaching/ShowCourseStudents.do");
			
			List<String> tableOrderFields = convertTableField(
					command.getOrderByTableField() != null && command.getAscendingOrder() != null ? command.getOrderByTableField() : 0,
					isCourseStudentServlet);
			for (String sortableField : tableOrderFields) {
				paginationFilter.addSortingCriteria(sortableField, command.getAscendingOrder() == null || command.getAscendingOrder() ? "ASC" : "DESC");
			}
			
			if (isCourseStudentServlet) {
				Page courseStudentsPage = getTeachingService().getCourseStudents(command.getCourseId(), paginationFilter);
				model.put("courseStudentsPage", courseStudentsPage);
				List<CoursePerson> courseStudents = getCourseStudentsForView(courseStudentsPage.getList());
				model.put("courseStudents", courseStudents);
				return new ModelAndView("teaching/ShowCourseStudents", model);
			} else {
				Page otherStudentsPage = getTeachingService().getOtherStudents(command.getCourseId(), paginationFilter);
				model.put("studentsPage", otherStudentsPage);
				return new ModelAndView("teaching/ShowOtherStudents", model);
			}
		} catch (ApplicationThrowable th) {
			model.put("error", th.toString());
			return new ModelAndView("error/ShowCourseStudents", model);
		}
		
	}
	
	/* Privates */
	
	private List<String> convertTableField(Integer tableFieldIndex, boolean courseStudents) {
		List<String> sortableFields = new ArrayList<String>();
		switch (tableFieldIndex) {
			case 0:
				if (courseStudents) {
					sortableFields.add("userRole.user.lastName");
				}
				sortableFields.add("userRole.user.firstName");
				break;
			case 1:
				if (courseStudents) {
					sortableFields.add("userRole.user.account");
				} else {
					sortableFields.add("userRole.user.lastName");
				}
				break;
			case 2:
				if (courseStudents) {
					// NOT POSSIBLE
				} else {
					sortableFields.add("userRole.user.account");
				}
				break;
			default:
				if (courseStudents) {
					sortableFields.add("userRole.user.lastName");
				}
				sortableFields.add("userRole.user.firstName");
				break;
		}
		return sortableFields;
	}
	
	private List<CoursePerson> getCourseStudentsForView(List<?> students) {
		List<CoursePerson> studentsForView = new ArrayList<CoursePerson>();
		for(Object student : students) {
			studentsForView.add(CoursePerson.convert((CoursePeople) student));
		}
		return studentsForView;
	}

}
