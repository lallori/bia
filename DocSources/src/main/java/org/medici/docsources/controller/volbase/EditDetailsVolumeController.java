/*
 * EditDetailsVolumeController.java
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
package org.medici.docsources.controller.volbase;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.command.volbase.EditDetailsVolumeCommand;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.SerieList;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.security.DocSourcesLdapUserDetailsImpl;
import org.medici.docsources.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Edit Details Volume".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/volbase/EditDetailsVolume")
public class EditDetailsVolumeController {
	@Autowired(required = false)
	@Qualifier("editDetailsVolumeValidator")
	private Validator validator;
	@Autowired
	private VolBaseService volBaseService;

	/**
	 * This method returns the Validator class used by Controller to make
	 * business validation.
	 * 
	 * @return
	 */
	public Validator getValidator() {
		return validator;
	}

	/**
	 * @return the volBaseService
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}

	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditDetailsVolumeCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			Volume volume = new Volume(command.getSummaryId());
			volume.setVolNum(command.getVolNum());
			volume.setVolLetExt(command.getVolLetExt());
			volume.setResearcher(command.getResearcher());

			// We consider null series if the user clean description field
			if ((!ObjectUtils.toString(command.getSeriesRefNum()).equals("")) && (!ObjectUtils.toString(command.getSeriesRefDescription()).equals(""))) {
				volume.setSerieList(new SerieList(command.getSeriesRefNum()));
			}

			volume.setStartYear(command.getStartYear());
			volume.setStartMonthNum((command.getStartMonthNum() != null) ? new Month(command.getStartMonthNum()) : null);
			volume.setStartDay(command.getStartDay());
			volume.setEndYear(command.getEndYear());
			volume.setEndMonthNum((command.getEndMonthNum() != null) ? new Month(command.getEndMonthNum()) : null);
			volume.setEndDay(command.getEndDay());
			volume.setDateNotes(command.getDateNotes());

			try {
				if (command.getSummaryId().equals(0)) {
					volume = getVolBaseService().addNewVolume(volume);
					model.put("volume", volume);
					return new ModelAndView("volbase/ShowVolume", model);
				} else {
					volume = getVolBaseService().editDetailsVolume(volume);
					model.put("volume", volume);
					model.put("volDocsRelated", getVolBaseService().findVolumeDocumentsRelated(volume.getSummaryId()));

					return new ModelAndView("volbase/ShowVolume", model);
				}
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/EditDetailsVolume", model);
			}
		}
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EditDetailsVolumeCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Month> months = null;
		try {
			months = getVolBaseService().getMonths();
			model.put("months", months);
		} catch (ApplicationThrowable ath) {
			return new ModelAndView("error/ShowVolume", model);
		}

		if ((command != null) && (command.getSummaryId() > 0)) {
			Volume volume = new Volume();

			try {
				volume = getVolBaseService().findVolume(command.getSummaryId());
				model.put("volume", volume);
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/EditDetailsVolume", model);
			}

			try {
				BeanUtils.copyProperties(command, volume);
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}

			if (volume.getSerieList() != null) {
				command.setSeriesRefNum(volume.getSerieList().getSeriesRefNum());
				command.setSeriesRefDescription(volume.getSerieList().toString());
			}

			if (volume.getStartMonthNum() != null) {
				command.setStartMonthNum(volume.getStartMonthNum().getMonthNum());
			}
			if (volume.getEndMonthNum() != null) {
				command.setEndMonthNum(volume.getEndMonthNum().getMonthNum());
			}

			if (command.getStartYear() == 0)
				command.setStartYear(null);

			if (command.getStartDay() == 0)
				command.setStartDay(null);

			if (command.getEndYear() == 0)
				command.setEndYear(null);

			if (command.getEndDay() == 0)
				command.setEndDay(null);
		} else {
			// On Volume creation, the research is always the current user.
			command.setResearcher(((DocSourcesLdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			// We need to expose dateCreated field because it must be rendered on view
			command.setDateCreated(new Date());
			command.setVolNum(null);
			command.setVolLetExt(null);
			command.setStartYear(null);
			// Empty month is in last positizion
			command.setStartMonthNum(null);
			command.setStartDay(null);
			command.setEndYear(null);
			// Empty month is in last positizion
			command.setEndMonthNum(null);
			command.setEndDay(null);
			command.setSeriesRefDescription(null);
			command.setSeriesRefNum(null);
		}

		return new ModelAndView("volbase/EditDetailsVolume", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * @param volBaseService
	 *            the volBaseService to set
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}
}