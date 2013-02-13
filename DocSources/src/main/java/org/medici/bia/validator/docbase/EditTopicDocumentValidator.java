/*
 * EditTopicDocumentValidator.java
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
package org.medici.bia.validator.docbase;

import java.util.Set;

import org.medici.bia.command.docbase.EditTopicDocumentCommand;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.EplToLink;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.geobase.GeoBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Edit Topic Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class EditTopicDocumentValidator implements Validator {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private GeoBaseService geoBaseService;

	/**
	 * x
	 * 
	 * @return
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

	/**
	 * 
	 * @param docBaseService
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	/**
	 * Indicates whether the given class is supported by this converter. This
	 * validator supports only ModifyDocumentCommand.
	 * 
	 * @param givenClass the class to test for support
	 * @return true if supported; false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class givenClass) {
		return givenClass.equals(EditTopicDocumentCommand.class);
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
		EditTopicDocumentCommand editTopicDocumentCommand = (EditTopicDocumentCommand) object;
		validateTopic(	editTopicDocumentCommand.getEntryId(), 
						editTopicDocumentCommand.getEplToId(),
						editTopicDocumentCommand.getTopicId(),
						editTopicDocumentCommand.getPlaceId(),
						editTopicDocumentCommand.getPlaceDescription(),
						errors);
	}

	private void validateTopic(Integer entryId, Integer eplToId, Integer topicId, Integer placeId, String placeDescription, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entryId", "error.entryId.null");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "eplToId", "error.epLinkId.null");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "placeDescription", "error.placeDescription.null");

		if (!errors.hasErrors()) {
			try {
				Document document = getDocBaseService().findDocument(entryId);
				if (document == null) {
					errors.reject("entryId", "error.entryId.notfound");
				} else {
					// We cannot insert a link with empty topic and placeId
					if (eplToId.equals(0) && topicId.equals(0) && placeId.equals(0)) {
						errors.reject("eplToLinkId", "error.topicandplace.notfound");
					}

					//retrieving actual linked topics
					Set<EplToLink> linkedTopics = document.getEplToLink();
					
					//Check if linkedTopics is empty and user is trying to modify an existing link
					if (linkedTopics == null && (!eplToId.equals(0))) {
						errors.reject("eplToId", "error.eplToId.invalid");
					} else {
						// if topic is specify, we check if it exists
						if (!topicId.equals(0)) {
							if (getDocBaseService().findTopic(topicId) == null) {
								errors.reject("topic", "error.topicId.invalid");
							}
						}

						// if place is specify, we check if it exists
						if (!placeId.equals(0)) {
							if (getGeoBaseService().findPlace(placeId) == null) {
								errors.reject("topic", "error.placeId.invalid");
							}
						}
						
						//if topic already exist
						for(EplToLink currentEplToLink : linkedTopics){
							if(currentEplToLink.getTopic().getTopicId() == topicId && currentEplToLink.getPlace().getPlaceAllId() == placeId){
								errors.rejectValue("topicId", "error.topic.alreadyExist");
							}
						}
					}
				}

			} catch (ApplicationThrowable ath) {
				errors.reject("entryId", "error.entryId.notfound");
			}
		}
	}

	/**
	 * 
	 * @param documentId
	 * @param errors
	 */
	public void validateDocumentId(Integer entryId, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entryId", "error.entryId.null");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "epLinkId", "error.epLinkId.null");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "personId", "error.personId.null");

		Document document = null;

		if (!errors.hasErrors()) {
			try {
				document = getDocBaseService().findDocument(entryId);
				if (document == null) {
					errors.reject("entryId", "error.entryId.notfound");
				}
			} catch (ApplicationThrowable ath) {
				errors.reject("entryId", "error.entryId.notfound");
			}
		}

		if (!errors.hasErrors()) {
			
		}
	}

	/**
	 * @param geoBaseService the geoBaseService to set
	 */
	public void setGeoBaseService(GeoBaseService geoBaseService) {
		this.geoBaseService = geoBaseService;
	}

	/**
	 * @return the geoBaseService
	 */
	public GeoBaseService getGeoBaseService() {
		return geoBaseService;
	}
}
