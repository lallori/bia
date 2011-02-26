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
package org.medici.docsources.controller.manuscriptviewer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.medici.docsources.common.html.HtmlUtils;
import org.medici.docsources.common.pagination.DocumentExplorer;
import org.medici.docsources.domain.Image;
import org.medici.docsources.domain.Image.ImageType;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for ManuscriptViewer.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller("ManuscriptViewerAjaxController")
public class AjaxController {
	@Autowired
	private DocBaseService docBaseService;

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
	 * 
	 * @param entryId Document identifier
	 * @param volNum Volume Number
	 * @param volLetExt Volume Letter Extension
	 * @param imageType 
	 * @param imageProgTypeNum
	 * @param firstRecord This is input parameter for Carta Form
	 * @param secondRecord This is input parameter for Rubricario Form
	 * @param imageOrder Unique id identifier inside volume.
	 * @param total Global total of volume.
	 * @param totalRubricario Total count page in rubricario section.
	 * @param totalCarta Total count page in carta section.
	 * @param totalAppendix Total count page in appendix section.
	 * @param totalOther
	 * @param totalGuardia
	 * @param modeEdit
	 * @return
	 */
	@RequestMapping(value = {"/src/mview/SearchCarta.json", "/de/mview/SearchCarta.json"}, method = RequestMethod.GET)
	public ModelAndView searchCarta(@RequestParam(value="entryId", required=false) Integer entryId,
			@RequestParam(value="volNum", required=false) Integer volNum,
			@RequestParam(value="volLetExt", required=false) String volLetExt,
			@RequestParam(value="imageType", required=false) String imageType,
			@RequestParam(value="imageProgTypeNum", required=false) Integer imageProgTypeNum,
			@RequestParam(value="imageOrder", required=false) Integer imageOrder,
			@RequestParam(value="total", required=false) Long total,
			@RequestParam(value="totalRubricario", required=false) Long totalRubricario,
			@RequestParam(value="totalCarta", required=false) Long totalCarta,
			@RequestParam(value="totalAppendix", required=false) Long totalAppendix,
			@RequestParam(value="totalOther", required=false) Long totalOther,
			@RequestParam(value="totalGuardia", required=false) Long totalGuardia,
			@RequestParam(value="modeEdit", required=false) Boolean modeEdit, 
			HttpServletRequest request){

		Map<String, Object> model = new HashMap<String, Object>();

		DocumentExplorer documentExplorer = new DocumentExplorer(entryId, volNum, volLetExt);
		documentExplorer.setImage(new Image());
		documentExplorer.getImage().setImageOrder(imageOrder);
        if (!StringUtils.isEmpty(imageType)) {
			documentExplorer.getImage().setImageType(ImageType.valueOf(imageType));
			documentExplorer.getImage().setImageProgTypeNum(imageProgTypeNum);
		}
		documentExplorer.setTotal(total);
		documentExplorer.setTotalRubricario(totalRubricario);
		documentExplorer.setTotalCarta(totalCarta);
		documentExplorer.setTotalAppendix(totalAppendix);
		documentExplorer.setTotalOther(totalOther);
		documentExplorer.setTotalGuardia(totalGuardia);

		try {
			documentExplorer = getDocBaseService().getDocumentExplorer(documentExplorer);
			
			if (documentExplorer.getImage().getImageName() != null) {
				model.put("entryId", documentExplorer.getEntryId());
				model.put("volNum", documentExplorer.getVolNum());
				model.put("volLetExt", documentExplorer.getVolLetExt());
				model.put("imageType", documentExplorer.getImage().getImageType());
				model.put("imageName", documentExplorer.getImage().getImageName());
				model.put("imageCompleteName", documentExplorer.getImage().toString());
				model.put("imageProgTypeNum", documentExplorer.getImage().getImageProgTypeNum());
				model.put("imageOrder", documentExplorer.getImage().getImageOrder());
				model.put("total", documentExplorer.getTotal());
				model.put("totalRubricario", documentExplorer.getTotalRubricario());
				model.put("totalCarta", documentExplorer.getTotalCarta());
				model.put("totalAppendix", documentExplorer.getTotalAppendix());
				model.put("totalOther", documentExplorer.getTotalOther());
				model.put("totalGuardia", documentExplorer.getTotalGuardia());
				model.put("previousPage", HtmlUtils.getDocumentExplorerPreviousPageUrl(documentExplorer));
				model.put("nextPage", HtmlUtils.getDocumentExplorerNextPageUrl(documentExplorer));
				if (modeEdit != null && modeEdit.equals(Boolean.TRUE)) {
					model.put("redirectUri", "/de/mview/EditDocumentInManuscriptViewer.do");
				} else {
					model.put("redirectUri", "/src/mview/ShowDocumentInManuscriptViewer.do");
				}
			} else {
				model.put("error", "image not found");
			}
		} catch (ApplicationThrowable ath) {
		}
			
		return new ModelAndView("responseOK", model);
	}
}