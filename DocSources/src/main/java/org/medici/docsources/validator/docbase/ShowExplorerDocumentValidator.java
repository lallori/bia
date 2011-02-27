/*
 * ShowExplorerDocumentValidator.java
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
package org.medici.docsources.validator.docbase;

import org.medici.docsources.command.docbase.ShowExplorerDocumentCommand;
import org.medici.docsources.service.docbase.DocBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Show Document Explorer".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class ShowExplorerDocumentValidator implements Validator {
	@Autowired
	private DocBaseService docBaseService;

	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	public DocBaseService getDocBaseService() {
		return docBaseService;
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
		return givenClass.equals(ShowExplorerDocumentCommand.class);
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
		ShowExplorerDocumentCommand showExplorerDocumentCommand = (ShowExplorerDocumentCommand) object;
		
		if (showExplorerDocumentCommand.getImageProgTypeNum() != null) {
			/*try {
				if (getDocBaseService().findVolumeImage(showExplorerVolumeRequestCommand.getVolNum(), showExplorerVolumeRequestCommand.getVolLetExt(), showExplorerVolumeRequestCommand.getImageType(), showExplorerVolumeRequestCommand.getImageProgTypeNum()) == null) {
					errors.rejectValue("imageProgTypeNum", "error.folio.notfound", new Object[]{showExplorerVolumeRequestCommand.getImageProgTypeNum()}, null);
				}
			} catch (ApplicationThrowable applicationThrowable) {
				
			}*/
		}
	}
}
