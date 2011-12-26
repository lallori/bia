/*
 * ManuscriptViewerService.java
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
package org.medici.docsources.service.manuscriptviewer;

import java.util.List;

import org.medici.docsources.common.pagination.DocumentExplorer;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.Image;
import org.medici.docsources.domain.Image.ImageType;
import org.medici.docsources.exception.ApplicationThrowable;

/**
 * This interface is designed to work on {@link org.medici.docsources.domain.People} 
 * object.<br>
 * It defines every business methods needed to work on people.
 * With this service, you can :<br>
 * - add a new person<br>
 * - modify an existing person<br> 
 * - search a person by his unique id<br>
 * - execute complex search on people<br>
 * ...<br>
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface ManuscriptViewerService {

	/**
	 * 
	 * @param entryId
	 * @param imageType
	 * @param imageProgTypeNum
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Image findDocumentImage(Integer entryId, ImageType imageType, Integer imageProgTypeNum) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @param imageOrder
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Image findDocumentImage(Integer entryId, Integer imageOrder) throws ApplicationThrowable;

	/**
	 * This method will search every {@link org.medici.docsources.domain.Image} 
	 * with specific image type and folio number linked to a 
	 * {@link org.medici.docsources.domain.Volume} identified by his volume
	 * number and his letter extension.
	 * 
	 * @param volNum Volume number identifier
	 * @param volLetExt Volume letter extension identifier.
	 * @param imageType {@link org.medici.docsources.domain.Image$ImageType} identifier
	 * @param imageProgTypeNum Folio number
	 * @return {@link java.util.List} of {@link org.medici.docsources.domain.Image}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Image findDocumentImage(Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum) throws ApplicationThrowable;

	/**
	 * This method searches document's images linked to a specific document.
	 * 
	 * @param entryId Document
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<Image> findDocumentImages(Integer entryId) throws ApplicationThrowable;

	/**
	 * This method will search every {@link org.medici.docsources.domain.Image} 
	 * with specific image type and folio number linked to a 
	 * {@link org.medici.docsources.domain.Volume} identified by his volume
	 * number and his letter extension.
	 * 
	 * @param volNum Volume number identifier
	 * @param volLetExt Volume letter extension identifier.
	 * @param imageType {@link org.medici.docsources.domain.Image$ImageType} identifier
	 * @param imageProgTypeNum Folio number
	 * @return {@link java.util.List} of {@link org.medici.docsources.domain.Image}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<Image> findDocumentImages(Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum) throws ApplicationThrowable;

	/**
	 * 
	 * @param document
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Image findDocumentImageThumbnail(Document document) throws ApplicationThrowable;

	/**
	 * 
	 * @param summaryId
	 * @param volNum
	 * @param volLetExt
	 * @param imageType
	 * @param imageProgTypeNum
	 * @param imageOrder
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Image findVolumeImage(Integer summaryId, Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum, Integer imageOrder) throws ApplicationThrowable;

	/**
	 * This method will search every {@link org.medici.docsources.domain.Image} 
	 * linked to a {@link org.medici.docsources.domain.Volume} identified by his
	 * unique identifier
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return {@link java.util.List} of {@link org.medici.docsources.domain.Image}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<Image> findVolumeImages(Integer summaryId) throws ApplicationThrowable;
	
	/**
	 * This method searches for existing {@link org.medici.docsources.domain.Image}
	 * using a {@link org.medici.docsources.common.pagination.Page} object result
	 * based on {@link org.medici.docsources.common.pagination.PaginationFilter} input object.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @param paginationFilter {@link org.medici.docsources.common.pagination.PaginationFilter} 
	 *        with search parameters
	 * @return {@link org.medici.docsources.common.pagination.Page}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Page findVolumeImages(Integer summaryId, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * This method will search every {@link org.medici.docsources.domain.Image} 
	 * with specific image type and folio number linked to a 
	 * {@link org.medici.docsources.domain.Volume} identified by his volume
	 * number and his letter extension.
	 * 
	 * @param volNum Volume number identifier
	 * @param volLetExt Volume letter extension identifier.
	 * @param imageType {@link org.medici.docsources.domain.Image$ImageType} identifier
	 * @param imageProgTypeNum Folio number
	 * @return {@link java.util.List} of {@link org.medici.docsources.domain.Image}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<Image> findVolumeImages(Integer summaryId, Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum, Integer imageOrder) throws ApplicationThrowable;

	/**
	 * This method will search every {@link org.medici.docsources.domain.Image} 
	 * linked to a {@link org.medici.docsources.domain.Volume} identified by his
	 * volume number and his letter extension.
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Page findVolumeImages(Integer volNum, String volLetExt, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Image findVolumeImageSpine(Integer volNum, String volLetExt) throws ApplicationThrowable;


	/**
	 * 
	 * @param pageTurner
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public DocumentExplorer getDocumentExplorer(DocumentExplorer pageTurner) throws ApplicationThrowable;
}
