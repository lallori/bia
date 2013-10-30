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
package org.medici.bia.service.manuscriptviewer;

import java.util.List;
import java.util.Map;

import org.medici.bia.common.pagination.DocumentExplorer;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.VolumeExplorer;
import org.medici.bia.common.volume.VolumeSummary;
import org.medici.bia.domain.Annotation;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Image.ImageType;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * This interface is designed to work on {@link org.medici.bia.domain.People} 
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
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface ManuscriptViewerService {

	/**
	 * 
	 * @param annotation
	 * @param image
	 * @param ipAddress
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Annotation addNewAnnotation(Annotation annotation, Image image, String ipAddress) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @param volNum
	 * @param volLetExt
	 * @param imageType
	 * @param imageProgTypeNum
	 * @param imageOrder
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Image findDocumentImage(Integer entryId, Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum, Integer imageOrder) throws ApplicationThrowable;

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
	 * This method will search every {@link org.medici.bia.domain.Image} 
	 * with specific image type and folio number linked to a 
	 * {@link org.medici.bia.domain.Volume} identified by his volume
	 * number and his letter extension.
	 * 
	 * @param volNum Volume number identifier
	 * @param volLetExt Volume letter extension identifier.
	 * @param imageType {@link org.medici.bia.domain.Image$ImageType} identifier
	 * @param imageProgTypeNum Folio number
	 * @return {@link java.util.List} of {@link org.medici.bia.domain.Image}
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
	 * @param imageId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Image findImage(Integer imageId) throws ApplicationThrowable;
	
	/**
	 * This method returns the documents linked to an image in a volume.<br/>
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param imageProgTypeNum the folio number
	 * @param missedNumbering the folio extension
	 * @param rectoVerso the recto/verso detail
	 * @return a list of documents related to the image details provided within the volume provided
	 * @throws ApplicationThrowable
	 */
	public List<Document> findLinkedDocument(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer imageProgTypeNum, String missedNumbering, String rectoVerso) throws ApplicationThrowable;
	
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
	 * This method will search every {@link org.medici.bia.domain.Image} 
	 * linked to a {@link org.medici.bia.domain.Volume} identified by his
	 * unique identifier
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return {@link java.util.List} of {@link org.medici.bia.domain.Image}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<Image> findVolumeImages(Integer summaryId) throws ApplicationThrowable;

	/**
	 * This method will search every {@link org.medici.bia.domain.Image} 
	 * with specific image type and folio number linked to a 
	 * {@link org.medici.bia.domain.Volume} identified by his volume
	 * number and his letter extension.
	 * 
	 * @param volNum Volume number identifier
	 * @param volLetExt Volume letter extension identifier.
	 * @param imageType {@link org.medici.bia.domain.Image$ImageType} identifier
	 * @param imageProgTypeNum Folio number
	 * @return {@link java.util.List} of {@link org.medici.bia.domain.Image}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<Image> findVolumeImages(Integer summaryId, Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum, Integer imageOrder) throws ApplicationThrowable;

	/**
	 * This method searches for existing {@link org.medici.bia.domain.Image}
	 * using a {@link org.medici.bia.common.pagination.Page} object result
	 * based on {@link org.medici.bia.common.pagination.PaginationFilter} input object.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @param paginationFilter {@link org.medici.bia.common.pagination.PaginationFilter} 
	 *        with search parameters
	 * @return {@link org.medici.bia.common.pagination.Page}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Page findVolumeImages(Integer summaryId, PaginationFilter paginationFilter) throws ApplicationThrowable;


	/**
	 * This method will search every {@link org.medici.bia.domain.Image} 
	 * linked to a {@link org.medici.bia.domain.Volume} identified by his
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
	 * @param volNum
	 * @param volLetExt
	 * @return
	 * @throws ApplicationThrowable
	 */
	public VolumeSummary findVolumeSummmary(Integer volNum, String volLetExt) throws ApplicationThrowable;

	/**
	 * 
	 * @param pageTurner
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public DocumentExplorer getDocumentExplorer(DocumentExplorer pageTurner) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param pageTurner
	 * @return
	 * @throws ApplicationThrowable
	 */
	public VolumeExplorer getVolumeExplorer(VolumeExplorer pageTurner) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param imageName
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Annotation> getImageAnnotations(String imageName)throws ApplicationThrowable;
	
	/**
	 * 
	 * @param imageName
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Map<Annotation, Boolean> getImageAnnotationsToEdit(String imageName)throws ApplicationThrowable;

	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @param imageOrder
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ImageType getImageType(Integer volNum, String volLetExt, Integer imageOrder) throws ApplicationThrowable;

	/**
	 * This method determines if an annotation is deletable or not.<br/>
	 * An annotation is deletable if it has no forum topic associated or if the associated forum topic has no active post.
	 * 
	 * @param annotation the annotation
	 * @return true if the annotation provided is deletable
	 */
	public Boolean isDeletableAnnotation(Annotation annotation);
	
	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Boolean isDocumentExtract(Integer entryId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param imageId
	 * @param annotation
	 * @throws ApplicationThrowable
	 */
	public Annotation updateAnnotation(Integer imageId, Annotation annotation) throws ApplicationThrowable;
	
	/**
	 * This method updates the annotations associated to an image.
	 * 
	 * @param imageId the image identifier
	 * @param fromViewAnnotations the list of annotations retrieved from the view level
	 * @param ipAddress the ip address
	 * @return a map with all the annotations (key) of the current image associated to a forum identifier (value greather than -1 only for new annotations)
	 * @throws ApplicationThrowable
	 */
	public Map<Annotation, Integer> updateAnnotations(Integer imageId, List<Annotation> fromViewAnnotations, String ipAddress) throws ApplicationThrowable;
}
