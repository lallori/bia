/*
 * EditCatalogController.java
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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.medici.docsources.command.digitization.EditCatalogCommand;
import org.medici.docsources.command.digitization.EditCatalogRequestCommand;
import org.medici.docsources.domain.Catalog;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.digitization.DigitizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Edit Catalog".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/digitization/EditCatalog")
public class EditCatalogController {
	@Autowired
	private DigitizationService catalogService;
	@Autowired(required = false)
	@Qualifier("editCatalogValidator")
	private Validator validator;
	@Qualifier("editCatalogRequestValidator")
	private Validator requestValidator;

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
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditCatalogCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			Map<String, Object> model = new HashMap<String, Object>();

			return new ModelAndView("catalog/EditCatalog", model);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			
			Catalog catalog = new Catalog();
				
			try {
				BeanUtils.copyProperties(catalog, command);
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}
			
			try {
				getCatalogService().save(catalog);
			} catch (ApplicationThrowable ath){
				
			} 

			return new ModelAndView("catalog/EditCatalogResult", model);
		}
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@Valid @ModelAttribute("requestCommand") EditCatalogRequestCommand command, BindingResult result) {
		getRequestValidator().validate(command, result);

		if (result.hasErrors()) {
			return new ModelAndView("error/EditCatalog");
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			EditCatalogCommand editCatalogCommand = new EditCatalogCommand();
			if ((command.getId() != null) && (command.getId()>0)) {
				Catalog catalog = getCatalogService().findCatalog(command.getId());
				
				try {
					BeanUtils.copyProperties(editCatalogCommand, catalog);
				} catch (IllegalAccessException iaex) {
				} catch (InvocationTargetException itex) {
				}
			}
			
			model.put("command", editCatalogCommand);

			return new ModelAndView("catalog/EditCatalog", model);
		}
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * @param catalogService the catalogService to set
	 */
	public void setCatalogService(DigitizationService catalogService) {
		this.catalogService = catalogService;
	}

	/**
	 * @return the catalogService
	 */
	public DigitizationService getCatalogService() {
		return catalogService;
	}

	/**
	 * @param requestValidator the requestValidator to set
	 */
	public void setRequestValidator(Validator requestValidator) {
		this.requestValidator = requestValidator;
	}

	/**
	 * @return the requestValidator
	 */
	public Validator getRequestValidator() {
		return requestValidator;
	}
}