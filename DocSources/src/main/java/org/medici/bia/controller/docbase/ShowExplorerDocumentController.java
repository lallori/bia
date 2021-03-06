/*
 * ShowExplorerDocumentController.java
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
package org.medici.bia.controller.docbase;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.BooleanUtils;
import org.medici.bia.command.docbase.ShowExplorerDocumentCommand;
import org.medici.bia.common.pagination.DocumentExplorer;
import org.medici.bia.common.util.StringUtils;
import org.medici.bia.domain.Image;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.manuscriptviewer.ManuscriptViewerService;
import org.medici.bia.service.volbase.VolBaseService;
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
 * Controller for action "Show Explorer Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/docbase/ShowExplorerDocument")
public class ShowExplorerDocumentController {
	@Autowired(required = false)
	@Qualifier("showExplorerDocumentValidator")
	private Validator validator;
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private VolBaseService volBaseService;
	@Autowired
	private ManuscriptViewerService manuscriptViewerService;
	
	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}

	/**
	 * @param docBaseService the docBaseService to set
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	/**
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}
	
	/**
	 * @param volBaseService the volBaseService to set
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}
	
	/**
	 * @return the volBaseService
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}

	/**
	 * @param manuscriptViewerService the manuscriptViewerService to set
	 */
	public void setManuscriptViewerService(ManuscriptViewerService manuscriptViewerService) {
		this.manuscriptViewerService = manuscriptViewerService;
	}

	/**
	 * @return the manuscriptViewerService
	 */
	public ManuscriptViewerService getManuscriptViewerService() {
		return manuscriptViewerService;
	}

	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView searchCarta(@Valid @ModelAttribute("command") ShowExplorerDocumentCommand command, BindingResult result){
		if (command.getInsertNum() != null || command.getInsertLet() != null || command.getImageProgTypeNum() != null || command.getMissedNumbering() != null) {
			// validation should be launched only when insert and folio details have a value (at least one of them)
			getValidator().validate(command, result);
		}

		if (result.hasErrors()) {
			// in case of errors we need to remove imageType and imageProgTypeNum, so we return imageOrder which is previous image.
			command.setImageType(null);
			// command.setImageProgTypeNum(null);
			return setupForm(command, result);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);
			try{
//				Document document = new Document();
//				if(command.getEntryId() != null){
//					document = getDocBaseService().findDocument(command.getEntryId());
//				}
				Boolean hasInserts = getVolBaseService().hasInserts(command.getVolNum(), command.getVolLetExt());
				model.put("hasInsert", hasInserts);
				
				DocumentExplorer documentExplorer = new DocumentExplorer(command.getEntryId(), command.getVolNum(), StringUtils.nullTrim(command.getVolLetExt()));
//				// we set informations from the document
//				documentExplorer.setFolioNum(document.getFolioNum());
//				documentExplorer.setFolioMod(document.getFolioMod());
				
				documentExplorer.setImage(new Image());
				if (!StringUtils.isNullableString(command.getInsertNum())) {
					documentExplorer.getImage().setInsertNum(StringUtils.nullTrim(command.getInsertNum()));
					documentExplorer.getImage().setInsertLet(StringUtils.nullTrim(command.getInsertLet()));
				}
				documentExplorer.getImage().setImageProgTypeNum(command.getImageProgTypeNum());
				documentExplorer.getImage().setMissedNumbering(StringUtils.nullTrim(command.getMissedNumbering()));
				documentExplorer.getImage().setImageRectoVerso(StringUtils.isNullableString(command.getImageRectoVerso()) ? null : Image.ImageRectoVerso.convertFromString(StringUtils.nullTrim(command.getImageRectoVerso())));
				documentExplorer.getImage().setImageOrder(command.getImageOrder());
				documentExplorer.getImage().setImageType(command.getImageType());
				documentExplorer.setTotal(command.getTotal());
				documentExplorer.setTotalRubricario(command.getTotalRubricario());
				documentExplorer.setTotalCarta(command.getTotalCarta());
				documentExplorer.setTotalAppendix(command.getTotalAppendix());
				documentExplorer.setTotalOther(command.getTotalOther());
				documentExplorer.setTotalGuardia(command.getTotalGuardia());
				
				documentExplorer = getManuscriptViewerService().getDocumentExplorer(documentExplorer);
		
				model.put("documentExplorer", documentExplorer);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ShowExplorerDocument", model);
			}
	
			if (BooleanUtils.isTrue(command.getModalWindow())) {
				return new ModelAndView("docbase/ShowExplorerDocumentModalWindow", model);
			} else {
				return new ModelAndView("docbase/ShowExplorerDocument", model);
			}
		}
	}

	/**
	 * 
	 * @param volumeId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)              
	public ModelAndView setupForm(@ModelAttribute("command") ShowExplorerDocumentCommand command, BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		DocumentExplorer documentExplorer = new DocumentExplorer(command.getEntryId(), command.getVolNum(), StringUtils.nullTrim(command.getVolLetExt()));
		documentExplorer.setImage(new Image());
		documentExplorer.getImage().setImageProgTypeNum(command.getImageProgTypeNum());
		documentExplorer.getImage().setMissedNumbering(StringUtils.nullTrim(command.getMissedNumbering()));
		documentExplorer.getImage().setImageRectoVerso(StringUtils.isNullableString(command.getImageRectoVerso()) ? null : Image.ImageRectoVerso.convertFromString(StringUtils.nullTrim(command.getImageRectoVerso())));
		documentExplorer.getImage().setImageOrder(command.getImageOrder());
		documentExplorer.getImage().setImageType(command.getImageType());
		documentExplorer.getImage().setInsertNum(StringUtils.nullTrim(command.getInsertNum()));
		documentExplorer.getImage().setInsertLet(StringUtils.isNullableString(command.getInsertNum()) ? null : command.getInsertLet());
		documentExplorer.setTotal(command.getTotal());
		documentExplorer.setTotalRubricario(command.getTotalRubricario());
		documentExplorer.setTotalCarta(command.getTotalCarta());
		documentExplorer.setTotalAppendix(command.getTotalAppendix());
		documentExplorer.setTotalOther(command.getTotalOther());
		documentExplorer.setTotalGuardia(command.getTotalGuardia());

		try {
			documentExplorer = getManuscriptViewerService().getDocumentExplorer(documentExplorer);
			model.put("documentExplorer", documentExplorer);
			
			Boolean hasInserts = getVolBaseService().hasInserts(command.getVolNum(), command.getVolLetExt());
			model.put("hasInsert", hasInserts);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
		}

		if (BooleanUtils.isTrue(command.getModalWindow())) {
			return new ModelAndView("docbase/ShowExplorerDocumentModalWindow", model);
		} else {
			return new ModelAndView("docbase/ShowExplorerDocument", model);
		}
	}
}