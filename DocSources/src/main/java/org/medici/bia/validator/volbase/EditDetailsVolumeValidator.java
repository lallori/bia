/*
 * EditDetailsVolumeValidator.java
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
package org.medici.bia.validator.volbase;

import org.medici.docsources.command.volbase.EditDetailsVolumeCommand;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Edit Details Volume".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class EditDetailsVolumeValidator implements Validator {
	@Autowired
	private VolBaseService volBaseService;

	/**
	 * 
	 * @return
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}

	/**
	 * 
	 * @param volBaseService
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}

	/**
	 * Indicates whether the given class is supported by this converter. This
	 * validator supports only ModifyVolumeCommand.
	 * 
	 * @param givenClass the class to test for support
	 * @return true if supported; false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class givenClass) {
		return givenClass.equals(EditDetailsVolumeCommand.class);
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
		EditDetailsVolumeCommand editDetailsVolumeCommand = (EditDetailsVolumeCommand) object;
		validateVolume(editDetailsVolumeCommand.getSummaryId(), editDetailsVolumeCommand.getVolNum(), editDetailsVolumeCommand.getVolLetExt(), errors);
		validateDates(editDetailsVolumeCommand.getStartYear(), editDetailsVolumeCommand.getStartMonthNum(), editDetailsVolumeCommand.getStartDay(), editDetailsVolumeCommand.getEndYear(), editDetailsVolumeCommand.getEndMonthNum(), editDetailsVolumeCommand.getEndDay(), errors);
	}

	private void validateDates(Integer startYear, Integer startMonthNum, Integer startDay, Integer endYear, Integer endMonthNum, Integer endDay, Errors errors) {
		if (!errors.hasErrors()) {
			if (startYear != null) {
				if ((startYear < 1300) || (startYear > 1750)) {
					errors.reject("startYear", "error.startYear.invalid");
				}
			}
			if (startMonthNum != null) {
				if ((startMonthNum <1) || (startMonthNum >13)) {
					errors.reject("startMonthNum", "error.startMonthNum.invalid");
				}
			}
			if (startDay != null) {
				if ((startDay < 0) || (startDay > 31)) {
					errors.reject("startDay", "error.startDay.invalid");
				}
			}

			if (endYear != null) {
				if ((endYear < 1200) || (endYear > 1700)) {
					errors.reject("endYear", "error.endYear.invalid");
				}
			}
			if (endMonthNum != null) {
				if ((endMonthNum <1) || (endMonthNum >13)) {
					errors.reject("endMonthNum", "error.endMonthNum.invalid");
				}
			}
			if (endDay != null) {
				if ((endDay < 0) || (endDay > 31)) {
					errors.reject("endDay", "error.endDay.invalid");
				}
			}
		}
	}

	/**
	 * 
	 * @param summaryId
	 * @param volNum
	 * @param volLetExt
	 * @param errors
	 */
	public void validateVolume(Integer summaryId, Integer volNum, String volLetExt, Errors errors) {
		if (!errors.hasErrors()) {
			// summary id equals zero is 'New Document', it shouldn't be validated  
			if (summaryId > 0) {
				try {
					if (getVolBaseService().findVolume(summaryId) == null) {
						errors.reject("volNum", "error.volume.notfound");
					}
				} catch (ApplicationThrowable ath) {
					errors.reject("volNum", "error.volume.notfound");
				}
			} else {
				// In this case we are creating a new Volume
				try {
					if (getVolBaseService().findVolume(volNum, volLetExt) != null) {
						errors.rejectValue("volNum", "error.volumeId.alreadypresent");
					}
				} catch (ApplicationThrowable ath) {
				}
			}
		}
	}
}
