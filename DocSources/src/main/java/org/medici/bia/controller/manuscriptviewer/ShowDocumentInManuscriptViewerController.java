/*
 * ShowDocumentInManuscriptViewerController.java
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
 * 
 */
package org.medici.bia.controller.manuscriptviewer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.medici.bia.command.manuscriptviewer.ShowDocumentInManuscriptViewerCommand;
import org.medici.bia.common.pagination.DocumentExplorer;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Image;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.manuscriptviewer.ManuscriptViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show Document In Manuscript Viewer".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/mview/ShowDocumentInManuscriptViewer")
public class ShowDocumentInManuscriptViewerController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private ManuscriptViewerService manuscriptViewerService;

	/**
	 * 
	 * @param volumeId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupPage(@ModelAttribute("requestCommand") ShowDocumentInManuscriptViewerCommand command, BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>(0);

		DocumentExplorer documentExplorer = null;
		if (!Boolean.TRUE.equals(command.getShowTranscription())) {
			documentExplorer = new DocumentExplorer(command.getEntryId(), command.getVolNum(), StringUtils.isEmpty(command.getVolLetExt()) ? null : command.getVolLetExt());
			documentExplorer.setImage(new Image());
			documentExplorer.getImage().setImageProgTypeNum(command.getImageProgTypeNum());
			documentExplorer.getImage().setImageOrder(command.getImageOrder());
			if (command.getImageOrder() == null) {
				// FIXME do extend command
				documentExplorer.getImage().setInsertNum("");
				documentExplorer.getImage().setInsertLet("");
				documentExplorer.getImage().setMissedNumbering("");
				documentExplorer.getImage().setImageRectoVerso(null);
			}
			documentExplorer.getImage().setImageType(command.getImageType());
			documentExplorer.setTotal(command.getTotal());
			documentExplorer.setTotalRubricario(command.getTotalRubricario());
			documentExplorer.setTotalCarta(command.getTotalCarta());
			documentExplorer.setTotalAppendix(command.getTotalAppendix());
			documentExplorer.setTotalOther(command.getTotalOther());
			documentExplorer.setTotalGuardia(command.getTotalGuardia());
		}

		try {
			if (!Boolean.TRUE.equals(command.getShowTranscription())) {
				documentExplorer = getManuscriptViewerService().getDocumentExplorer(documentExplorer);
			} else {
				documentExplorer = getManuscriptViewerService().getDocumentExplorer(command.getEntryId(), true);
			}
			
			if (documentExplorer.getEntryId() == null) {
				Image image = documentExplorer.getImage();
				List<Document> documents = getManuscriptViewerService().findLinkedDocument(
						command.getVolNum(),
						command.getVolLetExt(),
						image.getInsertNum(),
						image.getInsertLet(),
						image.getImageProgTypeNum(),
						image.getMissedNumbering(),image.getImageRectoVerso().toString());
				
				if (documents.size() > 0) {
					documentExplorer.setEntryId(documents.get(0).getEntryId());
				}
			}

			model.put("documentExplorer", documentExplorer);
			model.put("showTranscription", !Boolean.TRUE.equals(command.getShowTranscription()) ? false : true);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
		}
		
		return new ModelAndView("mview/ShowDocumentInManuscriptViewerHtml", model);
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

}