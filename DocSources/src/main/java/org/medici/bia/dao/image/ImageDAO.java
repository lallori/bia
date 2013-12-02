/*
 * ImageDAO.java
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
package org.medici.bia.dao.image;

import java.util.List;
import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.VolumeExplorer;
import org.medici.bia.common.volume.FoliosInformations;
import org.medici.bia.common.volume.VolumeInsert;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Image.ImageRectoVerso;
import org.medici.bia.domain.Image.ImageType;

/**
 * Image Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface ImageDAO extends Dao<Integer, Image> {
	
	/**
	 * This method counts the images that correspond to the filters.<br/>
	 * Note that null value for a filter is considered as null value in the database, while
	 * if a filter is empty (only for string fields) it is not considered.
	 * 
	 * @param volNum the volume number (mandatory)
	 * @param volLetExt the volume letter extension (mandatory or null)
	 * @param imageType the image type
	 * @param insertNum the insert number
	 * @param insertLet the insert letter
	 * @param folioNum the folio number (mandatory)
	 * @param folioMod the folio extension
	 * @param rectoVerso the folio recto/verso
	 * @return number of images that correspond to the filters
	 * @throws PersistenceException
	 */
	long countImages(Integer volNum, String volLetExt, String imageType, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso) throws PersistenceException;

	/**
	 * 
	 * @param imageId
	 * @return
	 * @throws PersistenceException
	 */
	Image findImageByImageId(Integer imageId) throws PersistenceException;

	/**
	 * This method searches a document image identified by volume identifiers, insert identifiers, number of folio
	 * and folio format, folio recto/verso.
	 *  
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extenxion letter
	 * @param folioNum Number of folio
	 * @param folioMod Folio format
	 * @param rectoVerso the recto/verso folio detail
	 * @return a document image
	 * @throws PersistenceException
	 */
	Image findDocumentImage(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso) throws PersistenceException;
	
	/**
	 * This method searches a document image identified by volume identifiers, insert identifiers, folio number, folio 
	 * format and image type.
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extenxion letter
	 * @param folioNum Number of folio
	 * @param folioMod folio format
	 * @param type the image type
	 * @return a document image
	 * @throws PersistenceException
	 */
	Image findDocumentImage(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, ImageType type) throws PersistenceException;
	
	/**
	 * This method searches a document image identified by volume identifiers, insert identifiers, folio number, folio 
	 * format, folio recto/verso and image type.
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extenxion letter
	 * @param folioNum Number of folio
	 * @param folioMod folio format
	 * @param rectoVerso folio recto/verso
	 * @param type the image type
	 * @return a document image
	 * @throws PersistenceException
	 */
	Image findDocumentImage(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, ImageRectoVerso rectoVerso, ImageType type) throws PersistenceException;

	/**
	 * This method searches every document images identified by volume identifiers, number of folio
	 * and folio format. 
	 * 
	 * @param volNum MDP Volume identifier 
	 * @param volLetExt MDP Volume extension
	 * @param folioNum Number of folio
	 * @param folioMod Folio format
	 * @return A List<Image> of documents
	 * @throws PersistenceException
	 */
	List<Image> findDocumentImages(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException;

	/**
	 * This method returns a list of string representation of digitized documents.<br/>
	 * NOTE: use {@link #findDigitizedDocumentsFromImages(List)} instead.
	 * 
	 * @param volNums
	 * @param volLetExts
	 * @param folioNums
	 * @param folioMods
	 * @return
	 */
	@Deprecated
	List<String> findDocumentsDigitized(List<Integer> volNums, List<String> volLetExts, List<Integer> folioNums, List<String> folioMods);
	
	/**
	 * This method return a list of string representation of digitized documents.
	 * 
	 * @param documents list of document provided
	 * @return list of string representation of digitized documents
	 */
	List<String> findDigitizedDocumentsFromImages(List<Document> documents);

	/**
	 * This method searches an image identified by volume identifiers, number of folio
	 * and image type. 
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @param imageType Type of image
	 * @param folioNum Number of folio
	 * @return A {@link org.medici.bia.domain.Image}
	 * @throws PersistenceException
	 */
	Image findImage(Integer volNum, String volLetExt, ImageType imageType, Integer folioNum) throws PersistenceException;
	
	/**
	 * This method searches an image identified by volume identifiers, insert identifier, folio identifiers and image type.<br/>
	 * Note that this method does not consider the recto/verso detail.
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @param imageType Type of image
	 * @param insertNum Number of insert
	 * @param insertLet extension of insert
	 * @param folioNum Number of folio
	 * @param folioMod extension of folio
	 * @return A {@link org.medici.bia.domain.Image}
	 */
	Image findImage(Integer volNum, String volLetExt, ImageType imageType, String insertNum, String insertLet, Integer folioNum, String folioMod);
	
	/**
	 * This method searches an image identified by volume identifiers, insert identifier, folio identifiers and image type.
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @param imageType Type of image
	 * @param insertNum Number of insert
	 * @param insertLet extension of insert
	 * @param folioNum Number of folio
	 * @param folioMod extension of folio
	 * @param rectoVerso recto or verso of the folio
	 * @return A {@link org.medici.bia.domain.Image}
	 */
	Image findImage(Integer volNum, String volLetExt, ImageType imageType, String insertNum, String insertLet, Integer folioNum, String folioMod, Image.ImageRectoVerso rectoVerso);

	/**
	 * This method populates the documentExplorer provided with the first image that satisfies the searching 
	 * criteria.<br />
	 * <b>NOTE :</b>
	 * <ul>
	 * <li>a null field implies a null value in the database</li>
	 * <li>a blank field (empty string) means that field is not considered during the search process</li> 
	 * <li>for the recto/verso detail the 'N' value stands for 'not specified' (null, recto or verso)</li>
	 * <li>one of <code>imageOrder</code>, <code>imageName</code>, <code>imageProgrTypeNum</code> (aka
	 * <code>folioNum</code>) should be specified, otherwise <i>1</i> is assigned to <code>imageOrder</code> 
	 * field</li>
	 * </ul>
	 * 
	 * @param explorer the explorer that contains the searching criteria
	 * @return the image found
	 * @throws PersistenceException
	 */
	<T extends VolumeExplorer> Image findImage(T explorer) throws PersistenceException;

	/**
	 * This method returns a list of Images linked to a specific volume.
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @return A List<Image> linked to a volume
	 * @throws PersistenceException
	 */
	List<Image> findImages(Integer volNum, String volLetExt) throws PersistenceException;

	/**
	 * This method returns a Paged list of Images linked to a specific volume .
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @param paginationFilter 
	 * @return A result page of Images
	 * @throws PersistenceException
	 */
	Page findImages(Integer volNum, String volLetExt, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * This methods returns a list of images linked to a specific insert.
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @param insertNum insert identifier
	 * @param insertLet insert extension
	 * @return A List<Image> linked to an insert
	 */
	List<Image> findImages(Integer volNum, String volLetExt, String insertNum, String insertLet);

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	List<Integer> findNewDigitizedVolumes() throws PersistenceException;

	/**
	 * This method searches the first volume image identified by volume identifiers. 
	 * 
	 * @param volNum MDP Volume identifiers
	 * @param volLetExt MDP Volume extension
	 * @return The first Image of a Volume
	 * @throws PersistenceException
	 */
	Image findVolumeFirstImage(Integer volNum, String volLetExt) throws PersistenceException;
	
	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @return
	 * @throws PersistenceException
	 */
	FoliosInformations findVolumeFoliosInformations(Integer volNum, String volLetExt) throws PersistenceException;

	/**
	 * This method searches a volume image identified by volume identifiers and image order.
	 * It returns null value if no image has been found.
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @param imageOrder 
	 * @return A Volume Image
	 * @throws PersistenceException
	 */
	Image findVolumeImage(Integer volNum, String volLetExt, Integer imageOrder) throws PersistenceException;

	/**
	 * This method searches every volume images identified by volume identifiers. 
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @return A List<Image> of a Volume
	 * @throws PersistenceException
	 */
	List<Image> findVolumeImages(Integer volNum, String volLetExt) throws PersistenceException;

	/**
	 * This method searches every volume images identified by volume identifiers 
	 * and type of image. 
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @param imageType Type of image
	 * @param imageProgTypeNum
	 * @return A List<Image> of a Volume
	 * @throws PersistenceException
	 */
	List<Image> findVolumeImages(Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum) throws PersistenceException;

	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @param imageOrder
	 * @return
	 */
	List<Image> findVolumeImages(Integer volNum, String volLetExt, Integer imageOrder);
	
	/**
	 * This method searches the inserts of a volume.
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @return A List of {@link VolumeInsert}
	 */
	List<VolumeInsert> findVolumeInserts(Integer volNum, String volLetExt);

	/**
	 * This method returns a list of all volume which are digitized. 
	 * 
	 * @param volNums Input volNums
	 * @param volLetExts
	 * @return List of MDP Volumes (MDP is volNum concatenated with volLetExt)
	 */
	List<String> findVolumesDigitized(List<Integer> volNums, List<String> volLetExts) throws PersistenceException;

	/**
	 * This method searches the volume spine identified by volume identifiers. 
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @return An Image of volume spine
	 * @throws PersistenceException
	 */
	Image findVolumeSpine(Integer volNum, String volLetExt) throws PersistenceException;
	
	
	/**
	 * This methods checks if a volume contains inserts or not.
	 * 
	 * @param volNum MDP Volume identifier
	 * @param volLetExt MDP Volume extension
	 * @return true if the volume contains inserts.
	 */
	Boolean hasInserts(Integer volNum, String volLetExt);
}
