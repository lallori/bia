/*
 * AdvancedSearchFactory.java
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
package org.medici.docsources.common.search;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.medici.docsources.command.search.AdvancedSearchCommand;
import org.medici.docsources.command.search.AdvancedSearchDocumentsCommand;
import org.medici.docsources.command.search.AdvancedSearchPeopleCommand;
import org.medici.docsources.command.search.AdvancedSearchPlacesCommand;
import org.medici.docsources.command.search.AdvancedSearchVolumesCommand;
import org.medici.docsources.command.search.SaveUserSearchFilterCommand;
import org.medici.docsources.domain.SearchFilter.SearchType;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class AdvancedSearchFactory {
	/**
	 * 
	 */
	public AdvancedSearchFactory() {
		super();
	}

	/**
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public static AdvancedSearch create(SaveUserSearchFilterCommand command) {
		if (command.getSearchType().equals(SearchType.DOCUMENT)) {
			AdvancedSearchDocumentsCommand advancedSearchDocumentsCommand = new AdvancedSearchDocumentsCommand();
			try {
				BeanUtils.copyProperties(advancedSearchDocumentsCommand, command);
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}
			AdvancedSearchDocument advancedSearchDocument = new AdvancedSearchDocument();
			advancedSearchDocument.initFromAdvancedSearchDocumentsCommand(advancedSearchDocumentsCommand);
			return advancedSearchDocument;
		} else if (command.getSearchType().equals(SearchType.PEOPLE)) {
			AdvancedSearchPeopleCommand advancedSearchPeopleCommand = new AdvancedSearchPeopleCommand();
			try {
				BeanUtils.copyProperties(advancedSearchPeopleCommand, command);
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}
			AdvancedSearchPeople advancedSearchPeople = new AdvancedSearchPeople();
			advancedSearchPeople.initFromAdvancedSearchPeopleCommand(advancedSearchPeopleCommand);
			return advancedSearchPeople;
		} else if (command.getSearchType().equals(SearchType.PLACE)) {
			AdvancedSearchPlacesCommand advancedSearchPlacesCommand = new AdvancedSearchPlacesCommand();
			try {
				BeanUtils.copyProperties(advancedSearchPlacesCommand, command);
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}
			AdvancedSearchPlace advancedSearchPlace = new AdvancedSearchPlace();
			advancedSearchPlace.initFromAdvancedSearchPlacesCommand(advancedSearchPlacesCommand);
			return advancedSearchPlace;
		} else {
			AdvancedSearchVolumesCommand advancedSearchVolumesCommand = new AdvancedSearchVolumesCommand();
			try {
				BeanUtils.copyProperties(advancedSearchVolumesCommand, command);
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}
			AdvancedSearchVolume advancedSearchVolume = new AdvancedSearchVolume();
			advancedSearchVolume.initFromAdvancedSearchVolumesCommand(advancedSearchVolumesCommand);
			return advancedSearchVolume;
		}
	}

	public static AdvancedSearch create(AdvancedSearchCommand command) {
		if (command.getSearchType().equals(SearchType.DOCUMENT)) {
			AdvancedSearchDocumentsCommand advancedSearchDocumentsCommand = new AdvancedSearchDocumentsCommand();
			try {
				BeanUtils.copyProperties(advancedSearchDocumentsCommand, command);
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}
			AdvancedSearchDocument advancedSearchDocument = new AdvancedSearchDocument();
			advancedSearchDocument.initFromAdvancedSearchDocumentsCommand(advancedSearchDocumentsCommand);
			return advancedSearchDocument;
		} else if (command.getSearchType().equals(SearchType.PEOPLE)) {
			AdvancedSearchPeopleCommand advancedSearchPeopleCommand = new AdvancedSearchPeopleCommand();
			try {
				BeanUtils.copyProperties(advancedSearchPeopleCommand, command);
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}
			AdvancedSearchPeople advancedSearchPeople = new AdvancedSearchPeople();
			advancedSearchPeople.initFromAdvancedSearchPeopleCommand(advancedSearchPeopleCommand);
			return advancedSearchPeople;
		} else if (command.getSearchType().equals(SearchType.PLACE)) {
			AdvancedSearchPlacesCommand advancedSearchPlacesCommand = new AdvancedSearchPlacesCommand();
			try {
				BeanUtils.copyProperties(advancedSearchPlacesCommand, command);
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}
			AdvancedSearchPlace advancedSearchPlace = new AdvancedSearchPlace();
			advancedSearchPlace.initFromAdvancedSearchPlacesCommand(advancedSearchPlacesCommand);
			return advancedSearchPlace;
		} else {
			AdvancedSearchVolumesCommand advancedSearchVolumesCommand = new AdvancedSearchVolumesCommand();
			try {
				BeanUtils.copyProperties(advancedSearchVolumesCommand, command);
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}
			AdvancedSearchVolume advancedSearchVolume = new AdvancedSearchVolume();
			advancedSearchVolume.initFromAdvancedSearchVolumesCommand(advancedSearchVolumesCommand);
			return advancedSearchVolume;
		}
	}

	/**
	 * This method create a new AdvancedSearch object from a specific SearchType.
	 * 
	 * @param searchType
	 * @return
	 */
	public static AdvancedSearch create(SearchType searchType) {
		if (searchType.equals(SearchType.DOCUMENT)) {
			return new AdvancedSearchDocument();
		} else if (searchType.equals(SearchType.PEOPLE)) {
			return new AdvancedSearchPeople();
		} else if (searchType.equals(SearchType.PLACE)) {
			return new AdvancedSearchPlace();
		} else {
			return new AdvancedSearchVolume();
		}
	}
}
