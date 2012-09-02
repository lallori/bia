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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.medici.bia.common.pagination.DocumentExplorer;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.common.util.ImageUtils;
import org.medici.docsources.domain.Annotation;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.Image;
import org.medici.docsources.domain.Image.ImageType;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.manuscriptviewer.ManuscriptViewerService;
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
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller("ManuscriptViewerAjaxController")
public class AjaxController {
	@Autowired
	private ManuscriptViewerService manuscriptViewerService;

	@RequestMapping(value = {"/src/mview/CreateAnnotation.json", "/de/mview/CreateAnnotation.json"}, method = RequestMethod.GET)
	public ModelAndView createAnnotation(	@RequestParam(value="volNum", required=true) Integer volNum,
											@RequestParam(value="volLetExt", required=true) String volLetExt,
											@RequestParam(value="imageType", required=true) String imageType,
											@RequestParam(value="imageProgTypeNum", required=true) Integer imageProgTypeNum,
											@RequestParam(value="imageOrder", required=true) Integer imageOrder,
											@RequestParam(value="imageName", required=true) String imageName,
											@RequestParam(value="id", required=false) String id,
											@RequestParam(value="x", required=true) Double x,
											@RequestParam(value="y", required=true) Double y,
											@RequestParam(value="w", required=true) Double w,
											@RequestParam(value="h", required=true) Double h,
											@RequestParam(value="title", required=true) String title,
											@RequestParam(value="category", required=true) String category,
											@RequestParam(value="text", required=false) String text,
											HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Annotation annotation = new Annotation();
			annotation.setX(x);
			annotation.setY(y);
			annotation.setWidth(w);
			annotation.setHeight(h);
			annotation.setType(Annotation.Type.valueOf(category));
			annotation.setSubject(title);
			annotation.setText(text);
			Image image = new Image();
			image.setImageType(Image.ImageType.valueOf(imageType));
			image.setImageProgTypeNum(imageProgTypeNum);
			image.setImageOrder(imageOrder);
			image.setImageName(imageName);
			annotation = getManuscriptViewerService().createAnnotation(annotation, image, httpServletRequest.getRemoteAddr());			
			model.put("annotationId", annotation.getAnnotationId());
		} catch (ApplicationThrowable ath) {
		}

		return new ModelAndView("responseOK", model);
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
	@RequestMapping(value = {"/src/mview/GetImageAnnotation.json", "/de/mview/GetImageAnnotation.json"}, method = RequestMethod.GET)
	public ModelAndView getImageAnnotation(@RequestParam(value="imageName", required=false) String imageName) {
		Map<String, Object> model = new HashMap<String, Object>();

	/*	try {
			List<String> = getDocBaseService().getImageAnnotation(imageName);			
			model.put("annotations", null);
		} catch (ApplicationThrowable ath) {
		}
		*/	
		return new ModelAndView("responseOK", model);
	}

	@RequestMapping(value = {"/src/mview/GetLinkedDocument.json", "/de/mview/GetLinkedDocument.json"}, method = RequestMethod.GET)
	public ModelAndView findLinkedDocument(@RequestParam(value="entryId", required=false) Integer entryId,
			@RequestParam(value="volNum", required=false) Integer volNum,
			@RequestParam(value="volLetExt", required=false) String volLetExt,
			@RequestParam(value="imageType", required=false) String imageType,
			@RequestParam(value="imageProgTypeNum", required=false) Integer imageProgTypeNum,
			@RequestParam(value="imageOrder", required=false) Integer imageOrder,
			@RequestParam(value="imageName", required=false) String imageName,
			@RequestParam(value="total", required=false) Long total,
			@RequestParam(value="totalRubricario", required=false) Long totalRubricario,
			@RequestParam(value="totalCarta", required=false) Long totalCarta,
			@RequestParam(value="totalAppendix", required=false) Long totalAppendix,
			@RequestParam(value="totalOther", required=false) Long totalOther,
			@RequestParam(value="totalGuardia", required=false) Long totalGuardia,
			@RequestParam(value="modeEdit", required=false) Boolean modeEdit, 
			HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Integer documentId = null;
			List<Document> documents = null;
			Boolean isExtract = false;
			Image image = new Image();
//			if (entryId != null) {
//				documentId = entryId;
//			} else {
				if ((!ObjectUtils.toString(imageType).equals(""))  && (!imageType.equals("C"))){
					model.put("error", "wrongType");
				} else {
					// We extract image
					image = getManuscriptViewerService().findVolumeImage(null, volNum, volLetExt, (imageType!=null) ? ImageType.valueOf(imageType) : null, imageProgTypeNum, imageOrder);
					
					if (image != null) {
						if ((!ObjectUtils.toString(image.getImageType()).equals(""))  && (!image.getImageType().equals(ImageType.C))){
							model.put("error", "wrongType");
						} else {
							// We check if this image has a document linked...
							documents = getManuscriptViewerService().findLinkedDocument(volNum, volLetExt, image);
							if(documents != null && documents.size() == 1){
								documentId = documents.get(0).getEntryId();
							}else if(documents != null && documents.size() > 1){
								isExtract = Boolean.TRUE;
							}
						}
						model.put("imageName", image.getImageName());
						model.put("imageId", image.getImageId());
						model.put("imageType", image.getImageType());
						model.put("imageOrder", image.getImageOrder());
					}
				}
//			}			
			if(documentId != null){
				isExtract = getManuscriptViewerService().isDocumentExtract(documentId);
			}
			
			model.put("linkedDocument", (documents != null) ? "true" : "false");
			model.put("countAlreadyEntered", (documents != null) ? documents.size() : 0);
			model.put("entryId", documentId );
			if(documents != null && documents.size() == 1){
				model.put("showLinkedDocument",  HtmlUtils.showDocument(documentId));
			}else if(documents != null && documents.size() > 1){
				Integer folioNum = ImageUtils.extractFolioNumber(image.getImageName());
				String folioMod = ImageUtils.extractFolioExtension(image.getImageName());
				if(folioMod != null)
					model.put("showLinkedDocument", HtmlUtils.showSameFolioDocuments(volNum, volLetExt, folioNum, folioMod));
				else
					model.put("showLinkedDocument", HtmlUtils.showSameFolioDocuments(volNum, volLetExt, folioNum, ""));
			}
			model.put("isExtract", (isExtract == true) ? "true" : "false");
		}catch (ApplicationThrowable applicationThrowable) {
			model.put("entryId", null);
			model.put("linkedDocument", "false");
			model.put("showLinkedDocument", "");
			model.put("imageName", "");
			model.put("imageId", "");
			model.put("imageOrder", "");
			model.put("isExtract", "false");
		}
		
		return new ModelAndView("responseOK", model);
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
			documentExplorer = getManuscriptViewerService().getDocumentExplorer(documentExplorer);
			
			if (documentExplorer.getImage().getImageName() != null) {
				model.put("entryId", documentExplorer.getEntryId());
				model.put("volNum", documentExplorer.getVolNum());
				model.put("volLetExt", documentExplorer.getVolLetExt());
				model.put("imageType", documentExplorer.getImage().getImageType());
				model.put("imageName", documentExplorer.getImage().getImageName());
				if (documentExplorer.getImage().getMissedNumbering() != null) { 
					model.put("missedNumbering", documentExplorer.getImage().getMissedNumbering());
				}
				model.put("imageCompleteName", documentExplorer.getImage().toString());
				model.put("imageProgTypeNum", documentExplorer.getImage().getImageProgTypeNum());
				model.put("imageRectoVerso", documentExplorer.getImage().getImageRectoVerso());
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