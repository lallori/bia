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
import org.medici.bia.common.util.ManuscriptViewerUtils.ManuscriptMode;
import org.medici.bia.common.volume.VolumeSummary;
import org.medici.bia.domain.Annotation;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Image.ImageType;
import org.medici.bia.domain.User;
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
	//public Annotation addNewAnnotation(Annotation annotation, Image image, String ipAddress) throws ApplicationThrowable;
	
	/**
	 * This method checks if an insert is a part of a volume.<br/>
	 * <b>NOTE :</b>this check is possible only for digitized volumes.
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @return true if the insert exists, false otherwise
	 * @throws ApplicationThrowable
	 */
	public Boolean checkInsert(Integer volNum, String volLetExt, String insertNum, String insertLet) throws ApplicationThrowable;

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
	 * Returns the image associated to the configuration provided.
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param imageType the image type
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param imageProgTypeNum the folio number
	 * @param missedNumbering the folio extension
	 * @return an {@link org.medici.bia.domain.Image}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Image findImage(Integer volNum, String volLetExt, ImageType imageType, String insertNum, String insertLet, Integer imageProgTypeNum, String missedNumbering) throws ApplicationThrowable;
	
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
	 * This method returns the documents that have the given start folio.<br/>
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param folioNum the folio number
	 * @param folioMod the folio extension
	 * @param rectoVerso the folio recto/verso
	 * @return a list of {@link Document} that have the given start folio, an empty list if no document is found.
	 * @throws ApplicationThrowable
	 */
	public List<Document> findLinkedDocumentOnStartFolio(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso) throws ApplicationThrowable;
	
	/**
	 * This method returns the documents that have the given start folio.<br/>
	 * The folio recto/verso is considered twice: with the value provided (only if it is not empty)
	 * and then with null value.
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param folioNum the folio number
	 * @param folioMod the folio extension
	 * @param rectoVerso the folio recto/verso
	 * @return a list of {@link Document} that have the given start folio, an empty list if no document is found.
	 * @throws ApplicationThrowable
	 */
	public List<Document> findLinkedDocumentOnStartFolioWithOrWithoutRectoVerso(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso) throws ApplicationThrowable;
	
	/**
	 * This method returns the documents that have the given transcribe folio.<br/>
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param transcribeFolioNum the transcribe folio number
	 * @param transcribeFolioMod the transcribe folio extension
	 * @param rectoVerso the transcribe folio recto/verso
	 * @return a list of {@link Document} that have the given transcribe folio, an empty list if no document is found.
	 * @throws ApplicationThrowable
	 */
	public List<Document> findLinkedDocumentOnTranscription(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer transcribeFolioNum, String transcribeFolioMod, String rectoVerso) throws ApplicationThrowable;
	
	/**
	 * This method returns the documents that have the given transcribe folio.<br/>
	 * The transcribe folio recto/verso is considered twice: with the value provided (only if it is not empty)
	 * and then with null value.
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param transcribeFolioNum the transcribe folio number
	 * @param transcribeFolioMod the transcribe folio extension
	 * @param rectoVerso the transcribe folio recto/verso
	 * @return a list of {@link Document} that have the given transcribe folio, an empty list if no document is found.
	 * @throws ApplicationThrowable
	 */
	public List<Document> findLinkedDocumentOnTranscriptionWithOrWithoutRectoVerso(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioTranscribeNum, String folioTranscribeMod, String rectoVerso) throws ApplicationThrowable;
	
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
	 * This method returns a {@link DocumentExplorer} associated to the document with the <code>ntryId</code> provided.<br/>
	 * If <code> forTranscribeFolio</code> is true the generated {@link DocumentExplorer} is associated to the transcription folio,
	 * otherwise it is associated to the document start folio.
	 * 
	 * @param entryId the document identifier
	 * @param forTranscribeFolio true if we search for the transcription folio
	 * @return a {@link DocumentExplorer}
	 * @throws ApplicationThrowable
	 */
	public DocumentExplorer getDocumentExplorer(Integer entryId, boolean forTranscribeFolio) throws ApplicationThrowable;
	
	/**
	 * This method returns the annotation by its identifier in the provided image.
	 * 
	 * @param imageName the image name
	 * @param annotationId the annotation identifier
	 * @return the annotation found
	 * @throws ApplicationThrowable
	 */
	public Annotation getImageAnnotation(String imageName, Integer annotationId) throws ApplicationThrowable;
	
	/**
	 * This method returns the list of annotations linked to the provided image.
	 * The annotations returned correspond to the provided manuscript mode ({@link ManuscriptMode}).
	 * If null mode is provided then 'COMMUNITY' mode is considered. 
	 * 
	 * @param imageId the image identifier
	 * @param mode the manuscript mode
	 * @return the list of annotation found
	 * @throws ApplicationThrowable
	 */
	public List<Annotation> getImageAnnotations(Integer imageId, ManuscriptMode mode)throws ApplicationThrowable;
	
	/**
	 * This method determines which image annotations are editable from the provided user.
	 * The annotations are filtered by the provided manuscript mode ({@link ManuscriptMode}).
	 * If null mode is provided then 'COMMUNITY' mode is considered.
	 * 
	 * @param imageId the image identifier
	 * @param user the user
	 * @param mode the manuscript mode
	 * @return a map with annotation editable details
	 * @throws ApplicationThrowable
	 */
	public Map<Annotation, Boolean> getImageAnnotationsToEdit(Integer imageId, User user, ManuscriptMode mode)throws ApplicationThrowable;

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
	 * 
	 * @param pageTurner
	 * @return
	 * @throws ApplicationThrowable
	 */
	public VolumeExplorer getVolumeExplorer(VolumeExplorer pageTurner) throws ApplicationThrowable;

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
	 * @param adminMode if true the update process consider that operation is performed by admin users
	 * @return a map with all the annotations (key) of the current image associated to a forum identifier (value greather than -1 only for new annotations)
	 * @throws ApplicationThrowable
	 */
	public Map<Annotation, Integer> updateAnnotations(Integer imageId, List<Annotation> fromViewAnnotations, String ipAddress, boolean adminMode) throws ApplicationThrowable;
	
}
