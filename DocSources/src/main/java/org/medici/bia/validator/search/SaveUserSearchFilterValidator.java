/*
 * SaveUserSearchFilterValidator.java
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
package org.medici.bia.validator.search;

import java.util.List;

import org.medici.bia.command.search.SaveUserSearchFilterCommand;
import org.medici.bia.command.search.SaveUserSearchFilterCommand.SaveType;
import org.medici.bia.domain.SearchFilter;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Save User Search Filter".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class SaveUserSearchFilterValidator implements Validator {
	@Autowired
	private SearchService searchService;

	/**
	 * @param searchService the searchService to set
	 */
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	/**
	 * @return the searchService
	 */
	public SearchService getSearchService() {
		return searchService;
	}

	/**
	 * Indicates whether the given class is supported by this converter. This
	 * validator supports only SaveUserSearchFilterCommand.
	 * 
	 * @param givenClass the class to test for support
	 * @return true if supported; false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class givenClass) {
		return givenClass.equals(SaveUserSearchFilterCommand.class);
	}

	/**
	 * Validate the supplied target object, which must be of a Class for which
	 * the supports(Class) method typically has (or would) return true. The
	 * supplied errors instance can be used to report any resulting validation
	 * errors.
	 * 
	 * @param object the object that is to be validated (can be null)
	 * @param errors contextual state about the validation process (never null)
	 */
	public void validate(Object object, Errors errors) {
		SaveUserSearchFilterCommand saveUserSearchFilterCommand = (SaveUserSearchFilterCommand) object;
		validateSaveAction(saveUserSearchFilterCommand.getSaveType(), saveUserSearchFilterCommand.getSaveAs(), saveUserSearchFilterCommand.getIdSearchFilterToReplace(), errors);
	}

	/**
	 * 
	 * @param saveType
	 * @param saveAs
	 * @param searchFilter
	 * @param errors
	 */
	private void validateSaveAction(SaveType saveType, String saveAs, Integer searchFilter, Errors errors) {
		if(saveAs != null && saveType.equals(SaveType.newSearch)){
			try{
				if(saveAs.equals("")){
					errors.reject("saveAs", "error.saveAs.invalid");
				}
				List<SearchFilter> userSearchFilters = getSearchService().getUserSearchFilters();
				for(SearchFilter currentSearchFilter : userSearchFilters){
					if(currentSearchFilter.getFilterName().toLowerCase().equals(saveAs.toLowerCase())){
						errors.reject("saveAs", "error.saveAs.invalid");
					}
				}
			}catch(ApplicationThrowable ath){
				errors.reject("searchFilter", "error.searchFilter.notFound");
			}
		}
	}


}
