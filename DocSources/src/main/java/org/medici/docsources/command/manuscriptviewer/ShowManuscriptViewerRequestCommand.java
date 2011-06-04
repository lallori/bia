/*
 * ShowManuscriptViewerRequestCommand.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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
package org.medici.docsources.command.manuscriptviewer;

import javax.validation.constraints.Size;

/**
 * Command bean for action "Show Manuscript Viewer".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.docsources.controller.volbase.EditDetailsVolumeController
 */
public class ShowManuscriptViewerRequestCommand {
	private Integer entryId;
	private Integer summaryId;
	private Integer volNum;
	@Size (max=1)
	private String volLeText;
	private Integer imageId;
	private String imageName;
	private String imageProgTypeNum;
	private String imageRectoVerso;
	private Boolean showHelp;
	private Boolean showThumbnail;
	private Boolean flashVersion;

	/**
	 * @param entryId the entryId to set
	 */
	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}
	
	/**
	 * @return the entryId
	 */
	public Integer getEntryId() {
		return entryId;
	}
	
	/**
	 * @return the summaryId
	 */
	public Integer getSummaryId() {
		return summaryId;
	}
	
	/**
	 * @param summaryId the summaryId to set
	 */
	public void setSummaryId(Integer summaryId) {
		this.summaryId = summaryId;
	}

	/**
	 * @return the volNum
	 */
	public Integer getVolNum() {
		return volNum;
	}

	/**
	 * @param volNum the volNum to set
	 */
	public void setVolNum(Integer volNum) {
		this.volNum = volNum;
	}
	
	/**
	 * @return the volLeText
	 */
	public String getVolLeText() {
		return volLeText;
	}
	
	/**
	 * @param volLeText the volLeText to set
	 */
	public void setVolLeText(String volLeText) {
		this.volLeText = volLeText;
	}
	
	/**
	 * @param flashVersion the flashVersion to set
	 */
	public void setFlashVersion(Boolean flashVersion) {
		this.flashVersion = flashVersion;
	}
	
	/**
	 * @return the flashVersion
	 */
	public Boolean getFlashVersion() {
		return flashVersion;
	}
	
	/**
	 * @param imageId the imageId to set
	 */
	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}
	
	/**
	 * @return the imageId
	 */
	public Integer getImageId() {
		return imageId;
	}
	
	/**
	 * @param imageProgTypeNum the imageProgTypeNum to set
	 */
	public void setImageProgTypeNum(String imageProgTypeNum) {
		this.imageProgTypeNum = imageProgTypeNum;
	}
	
	/**
	 * @return the imageProgTypeNum
	 */
	public String getImageProgTypeNum() {
		return imageProgTypeNum;
	}
	
	/**
	 * @param imageName the imageName to set
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	/**
	 * @return the imageName
	 */
	public String getImageName() {
		return imageName;
	}
	
	/**
	 * @param imageRectoVerso the imageRectoVerso to set
	 */
	public void setImageRectoVerso(String imageRectoVerso) {
		this.imageRectoVerso = imageRectoVerso;
	}
	
	/**
	 * @return the imageRectoVerso
	 */
	public String getImageRectoVerso() {
		return imageRectoVerso;
	}

	/**
	 * @param showHelp the showHelp to set
	 */
	public void setShowHelp(Boolean showHelp) {
		this.showHelp = showHelp;
	}

	/**
	 * @return the showHelp
	 */
	public Boolean getShowHelp() {
		return showHelp;
	}

	/**
	 * @param showThumbnail the showThumbnail to set
	 */
	public void setShowThumbnail(Boolean showThumbnail) {
		this.showThumbnail = showThumbnail;
	}
	
	/**
	 * @return the showThumbnail
	 */
	public Boolean getShowThumbnail() {
		return showThumbnail;
	}
}
