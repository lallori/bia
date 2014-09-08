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
package org.medici.bia.controller.manuscriptviewer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.medici.bia.common.pagination.DocumentExplorer;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.domain.Annotation;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Image.ImageType;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.community.CommunityService;
import org.medici.bia.service.manuscriptviewer.ManuscriptViewerService;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for ManuscriptViewer.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * @author Ronny Rianldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 */
@Controller("ManuscriptViewerAjaxController")
public class AjaxController {
	@Autowired
	private CommunityService communityService;
	@Autowired
	private ManuscriptViewerService manuscriptViewerService;
	@Autowired
	private UserService userService;
	
	/**
	 * @return the communityService
	 */
	public CommunityService getCommunityService() {
		return communityService;
	}

	/**
	 * @param communityService the communityService to set
	 */
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}

	/**
	 * @return the manuscriptViewerService
	 */
	public ManuscriptViewerService getManuscriptViewerService() {
		return manuscriptViewerService;
	}
	
	/**
	 * @param manuscriptViewerService the manuscriptViewerService to set
	 */
	public void setManuscriptViewerService(ManuscriptViewerService manuscriptViewerService) {
		this.manuscriptViewerService = manuscriptViewerService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * This method is called from the client when the user try to jump to a folio.
	 * It checks if the folio details are correct.
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param folioNum the folio number
	 * @param folioMod the folio extension
	 * @return the data of the checking process
	 */
	@RequestMapping(value = {"/src/mview/CheckFolio.json"}, method = RequestMethod.GET)
	public ModelAndView checkFolio(
			@RequestParam(value="volNum", required=false) Integer volNum,
			@RequestParam(value="volLetExt", required=false) String volLetExt,
			@RequestParam(value="insertNum", required=false) String insertNum,
			@RequestParam(value="insertLet", required=false) String insertLet,
			@RequestParam(value="folioNum", required=false) Integer folioNum,
			@RequestParam(value="folioMod", required=false) String folioMod) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			if (folioNum != null) {
				Image image = getManuscriptViewerService().findImage(
						volNum,
						volLetExt,
						ImageType.C, 
						org.medici.bia.common.util.StringUtils.nullTrim(insertNum),
						org.medici.bia.common.util.StringUtils.nullTrim(insertLet),
						folioNum,
						org.medici.bia.common.util.StringUtils.nullTrim(folioMod));
				model.put("folioOK", image != null);
			} else {
				model.put("error", "error.manuscriptviewer.incorrectfolio");
			}
		} catch (ApplicationThrowable e) {
			model.put("error", e.getApplicationError().toString());
		}
		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method is called from the client when the user try to jump to a folio.
	 * It checks if the insert details are correct.
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @return the data of the checking process
	 */
	@RequestMapping(value = {"/src/mview/CheckInsert.json"}, method = RequestMethod.GET)
	public ModelAndView checkInsert(
			@RequestParam(value="volNum", required=false) Integer volNum,
			@RequestParam(value="volLetExt", required=false) String volLetExt,
			@RequestParam(value="insertNum", required=false) String insertNum,
			@RequestParam(value="insertLet", required=false) String insertLet) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			if (volNum != null) {
				Boolean insertOK = getManuscriptViewerService().checkInsert(volNum, volLetExt, org.medici.bia.common.util.StringUtils.nullTrim(insertNum), org.medici.bia.common.util.StringUtils.nullTrim(insertLet));
				model.put("insertOK", insertOK);
			} else {
				model.put("error", "error.manuscriptviewer.incorrectvolume");
			}
		} catch (ApplicationThrowable e) {
			model.put("error", e.getApplicationError().toString());
		}
		return new ModelAndView("responseOK", model);
	}

	/**
	 * @param entryId
	 * @param volNum
	 * @param volLetExt
	 * @param imageType
	 * @param imageProgTypeNum
	 * @param imageOrder
	 * @param imageName
	 * @param total
	 * @param totalRubricario
	 * @param totalCarta
	 * @param totalAppendix
	 * @param totalOther
	 * @param totalGuardia
	 * @param modeEdit
	 * @param request
	 * @return
	 */
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
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			Integer documentId = null;
			List<Document> linkedDocumentOnStartFolio = null;
			List<Document> linkedDocumentOnTranscribeFolio = null;
			boolean isExtract = false;
			Image image = new Image();
			
			int intersectionSize = 0;
			int unionSize = 0;
			
//			if (entryId != null) {
//				documentId = entryId;
//			} else {
				if (org.medici.bia.common.util.StringUtils.safeTrim(imageType) != null && !"C".equals(imageType.trim())) {
					model.put("error", "wrongType");
				} else {
					// We extract image
					image = getManuscriptViewerService().findVolumeImage(null, volNum, volLetExt, (imageType != null) ? ImageType.valueOf(imageType) : null, imageProgTypeNum, imageOrder);
					
					if (image != null) {
						model.put("imageName", image.getImageName());
						model.put("imageId", image.getImageId());
						model.put("imageType", image.getImageType());
						model.put("imageOrder", image.getImageOrder());
						
						if (!ImageType.C.equals(image.getImageType())) {
							model.put("error", "wrongType");
						} else {
							// We check if this image has linked document on start folio...
							linkedDocumentOnStartFolio = getManuscriptViewerService().findLinkedDocumentOnStartFolioWithOrWithoutRectoVerso(
									volNum,
									volLetExt,
									image.getInsertNum(),
									image.getInsertLet(),
									image.getImageProgTypeNum(),
									image.getMissedNumbering(),
									image.getImageRectoVerso() != null ? image.getImageRectoVerso().toString() : null);

							// ..and on transcribe folio
							linkedDocumentOnTranscribeFolio = getManuscriptViewerService().findLinkedDocumentOnTranscriptionWithOrWithoutRectoVerso(
									volNum,
									volLetExt,
									image.getInsertNum(),
									image.getInsertLet(),
									image.getImageProgTypeNum(),
									image.getMissedNumbering(),
									image.getImageRectoVerso() != null ? image.getImageRectoVerso().toString() : null);
								
							if (!linkedDocumentOnStartFolio.isEmpty() && !linkedDocumentOnTranscribeFolio.isEmpty()) {
								intersectionSize = CollectionUtils.intersection(linkedDocumentOnStartFolio, linkedDocumentOnTranscribeFolio).size();
								unionSize = CollectionUtils.union(linkedDocumentOnStartFolio, linkedDocumentOnTranscribeFolio).size();
							} else {
								unionSize = Math.max(linkedDocumentOnStartFolio.size(), linkedDocumentOnTranscribeFolio.size());
							}
							
							if (unionSize == 1) {
									documentId = linkedDocumentOnStartFolio.isEmpty() ? linkedDocumentOnTranscribeFolio.iterator().next().getEntryId() : linkedDocumentOnStartFolio.iterator().next().getEntryId();
							} else if (unionSize > 1) {
								isExtract = true;
							}
						}
					}
				}
//			}			
			if (documentId != null) {
				isExtract = getManuscriptViewerService().isDocumentExtract(documentId);
			}
			
			// old one
			//model.put("linkedDocument", (linkedDocumentOnStartFolio != null && linkedDocumentOnStartFolio.size() > 0) ? true : false);
			// new ones
			model.put("linkedDocumentOnStartFolio", (linkedDocumentOnStartFolio != null && linkedDocumentOnStartFolio.size() > 0) ? true : false);
			model.put("linkedDocumentOnTranscribeFolio", (linkedDocumentOnTranscribeFolio != null && linkedDocumentOnTranscribeFolio.size() > 0) ? true : false);
			model.put("countAlreadyEntered", unionSize);
			model.put("countStartAndTranscribeHere", intersectionSize);
			model.put("entryId", documentId);
			if (unionSize == 1) {
				model.put("showLinkedDocument",  HtmlUtils.showDocument(documentId));
			} else if (unionSize > 1) {
				model.put("showLinkedDocument", HtmlUtils.showSameFolioDocuments(volNum, volLetExt, image.getInsertNum(), image.getInsertLet(), image.getImageProgTypeNum(), image.getMissedNumbering(), image.getImageRectoVerso().toString()));
			}
			model.put("isExtract", isExtract);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("entryId", null);
			//model.put("linkedDocument", false);
			model.put("linkedDocumentOnStartFolio", false);
			model.put("linkedDocumentOnTranscribeFolio", false);
			model.put("showLinkedDocument", "");
			model.put("imageName", "");
			model.put("imageId", "");
			model.put("imageOrder", "");
			model.put("isExtract", false);
		}
		
		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * 
	 * @param imageName the image name
	 * @param annotationId the annotation identifier to show (if none all image annotations are retrieved)
	 * @return
	 */
	@RequestMapping(value = {"/src/mview/GetImageAnnotation.json"}, method = RequestMethod.GET)
	public ModelAndView getImageAnnotation(
			@RequestParam(value="imageName", required=false) String imageName,
			@RequestParam(value="annotationId", required=false) Integer annotationId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			List<Annotation> annotations;
			if (annotationId != null) {
				annotations = new ArrayList<Annotation>();
				Annotation annotation = getManuscriptViewerService().getImageAnnotation(imageName, annotationId);
				if (annotation != null) {
					annotations.add(annotation);
				}
			} else {
				annotations = getManuscriptViewerService().getImageAnnotations(imageName, null);	
			}
			List<Object> resultList = getAnnotationsForView(annotationId, annotations); 
			model.put("annotations", resultList);
		} catch (ApplicationThrowable ath) {
			return new ModelAndView("responseKO", model);
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
	public ModelAndView searchCarta(
			@RequestParam(value="entryId", required=false) Integer entryId,
			@RequestParam(value="volNum", required=false) Integer volNum,
			@RequestParam(value="volLetExt", required=false) String volLetExt,
			@RequestParam(value="insertNum", required=false) String insertNum,
			@RequestParam(value="insertLet", required=false) String insertLet,
			@RequestParam(value="imageType", required=false) String imageType,
			@RequestParam(value="imageProgTypeNum", required=false) Integer imageProgTypeNum,
			@RequestParam(value="missedNumbering", required=false) String missedNumbering,
			@RequestParam(value="imageOrder", required=false) Integer imageOrder,
			@RequestParam(value="total", required=false) Long total,
			@RequestParam(value="totalRubricario", required=false) Long totalRubricario,
			@RequestParam(value="totalCarta", required=false) Long totalCarta,
			@RequestParam(value="totalAppendix", required=false) Long totalAppendix,
			@RequestParam(value="totalOther", required=false) Long totalOther,
			@RequestParam(value="totalGuardia", required=false) Long totalGuardia,
			@RequestParam(value="formSubmitting", required=false) Boolean formSubmitting,
			@RequestParam(value="modeEdit", required=false) Boolean modeEdit, 
			HttpServletRequest request){

		Map<String, Object> model = new HashMap<String, Object>(0);
		
		DocumentExplorer documentExplorer = new DocumentExplorer(entryId, volNum, org.medici.bia.common.util.StringUtils.nullTrim(volLetExt));
		documentExplorer.setImage(new Image());
		if (Boolean.TRUE.equals(formSubmitting)) {
			documentExplorer.getImage().setInsertNum(org.medici.bia.common.util.StringUtils.nullTrim(insertNum));
			documentExplorer.getImage().setInsertLet(org.medici.bia.common.util.StringUtils.nullTrim(insertLet));
			documentExplorer.getImage().setImageProgTypeNum(imageProgTypeNum);
			documentExplorer.getImage().setMissedNumbering(org.medici.bia.common.util.StringUtils.nullTrim(missedNumbering));
			// In this case recto/verso detail is not specified
			documentExplorer.getImage().setImageRectoVerso(null);
			if (!StringUtils.isEmpty(imageType)) {
				documentExplorer.getImage().setImageType(ImageType.valueOf(imageType));
			}
		} else {
			documentExplorer.getImage().setImageOrder(imageOrder != null && imageOrder < 1 ? 1 : imageOrder);
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
				model.put("insertNum", documentExplorer.getImage().getInsertNum());
				model.put("insertExt", documentExplorer.getImage().getInsertLet());
				model.put("imageId", documentExplorer.getImage().getImageId());
				model.put("imageType", documentExplorer.getImage().getImageType());
				model.put("imageName", documentExplorer.getImage().getImageName());
				model.put("missedNumbering", documentExplorer.getImage().getMissedNumbering());
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
	 * This method update the annotations of the folio presented in the manuscript viewer.
	 * 
	 * @param httpServletRequest the request
	 * @return
	 */
	@RequestMapping(value = {"/src/mview/UpdateAnnotations.json", "/de/mview/UpdateAnnotations.json"}, method = RequestMethod.POST)
	public ModelAndView updateAnnotations(HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			// In this controller we get input parameter at low level because 
			// there is a bug in spring which construct a wrong list of 
			// annotations in case of client send 1 single annotation 
			//String imageName = httpServletRequest.getParameter("imageName");
			Integer imageId = NumberUtils.toInt(httpServletRequest.getParameter("imageId"));
			String[] annotationsFormView = httpServletRequest.getParameterValues("annotations");
			List<Annotation> annotationsList = new ArrayList<Annotation>(0);
			List<Object> resultList = new ArrayList<Object>();

			if (annotationsFormView != null) {
				for (String string : annotationsFormView) {
					//Next code is instructed on code of javascript IIPMooViewer.annotationsAsQueryParameterString
					String[] splitted = StringUtils.splitPreserveAllTokens(string, ",");
					Annotation annotation = new Annotation();
					annotation.setAnnotationId(NumberUtils.toInt(splitted[0]));
					annotation.setX(NumberUtils.toDouble(splitted[2]));
					annotation.setY(NumberUtils.toDouble(splitted[3]));
					annotation.setWidth(NumberUtils.toDouble(splitted[4]));
					annotation.setHeight(NumberUtils.toDouble(splitted[5]));
					annotation.setType(Annotation.Type.valueOf(splitted[6].toUpperCase()));
					annotation.setTitle(splitted[7]);
					annotation.setText(splitted[8]);
					annotationsList.add(annotation);
				}
			}
			Map<Annotation, Integer> imageAnnotationsMap = getManuscriptViewerService().updateAnnotations(imageId, annotationsList, httpServletRequest.getRemoteAddr());
			for (Annotation currentAnnotation : imageAnnotationsMap.keySet()) {
				Map<String, Object> singleRow = new HashMap<String, Object>(0);
				if (imageAnnotationsMap.get(currentAnnotation) > -1) {
					singleRow.put("forum", ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath() + "/community/EditForumPostAnnotation.do?topicId=" + imageAnnotationsMap.get(currentAnnotation));
					resultList.add(singleRow);
				}
			}
			// links -> only new annotations associated to a forum 
			model.put("links", resultList);
			// annotation -> all of the annotations associated to the current image
			model.put("annotations", getAnnotationsForView(null, imageAnnotationsMap.keySet()));
		} catch (ApplicationThrowable applicationThrowable) {
			return new ModelAndView("responseKO", model);
		}
		
		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method generates a view of annotations to be sent to the view level.
	 * 
	 * @param annotationId the identifier of the annotation to show (if null all annotations of the list are showed)
	 * @param annotations a list of annotations
	 * @return a view of annotations
	 * @throws ApplicationThrowable
	 */
	private List<Object> getAnnotationsForView(Integer annotationId, Collection<Annotation> annotations) throws ApplicationThrowable {
		String account = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		Boolean administrator = getUserService().isAccountAdministrator(account);
		
		List<Object> resultList = new ArrayList<Object>();
		for (Annotation currentAnnotation : annotations) {
			Map<String, Object> singleRow = new HashMap<String, Object>(0);
			if (annotationId == null || (annotationId != null && annotationId.equals(currentAnnotation.getAnnotationId()))) {
				singleRow.put("annotationId", currentAnnotation.getAnnotationId());
				singleRow.put("x", currentAnnotation.getX());
				singleRow.put("y", currentAnnotation.getY());
				singleRow.put("w", currentAnnotation.getWidth());
				singleRow.put("h", currentAnnotation.getHeight());
				singleRow.put("type", currentAnnotation.getType());
				singleRow.put("title", currentAnnotation.getTitle());
				singleRow.put("text", currentAnnotation.getText());
				singleRow.put("deletable", annotationId == null && (administrator || getManuscriptViewerService().isDeletableAnnotation(currentAnnotation)) ? true : false);
				singleRow.put("updatable", annotationId == null && (account.equals(currentAnnotation.getUser().getAccount()) || administrator) ? true : false);
				if (currentAnnotation.getForumTopic() != null) {
					singleRow.put("forumTopicURL", HtmlUtils.getShowTopicForumHrefUrl(currentAnnotation.getForumTopic()) + "&completeDOM=true");
				}
				resultList.add(singleRow);
			}
		}
		return resultList;
	}
	
}