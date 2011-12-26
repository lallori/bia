/*
 * ManuscriptViewerServiceImpl.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.common.pagination.DocumentExplorer;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.PersonUtils;
import org.medici.docsources.dao.altname.AltNameDAO;
import org.medici.docsources.dao.bibliot.BiblioTDAO;
import org.medici.docsources.dao.bioreflink.BioRefLinkDAO;
import org.medici.docsources.dao.document.DocumentDAO;
import org.medici.docsources.dao.eplink.EpLinkDAO;
import org.medici.docsources.dao.image.ImageDAO;
import org.medici.docsources.dao.marriage.MarriageDAO;
import org.medici.docsources.dao.month.MonthDAO;
import org.medici.docsources.dao.parent.ParentDAO;
import org.medici.docsources.dao.people.PeopleDAO;
import org.medici.docsources.dao.place.PlaceDAO;
import org.medici.docsources.dao.polink.PoLinkDAO;
import org.medici.docsources.dao.rolecat.RoleCatDAO;
import org.medici.docsources.dao.titleoccslist.TitleOccsListDAO;
import org.medici.docsources.dao.userhistorypeople.UserHistoryPeopleDAO;
import org.medici.docsources.dao.volume.VolumeDAO;
import org.medici.docsources.domain.AltName;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.Image;
import org.medici.docsources.domain.Marriage;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.Parent;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.domain.Image.ImageType;
import org.medici.docsources.domain.People.Gender;
import org.medici.docsources.domain.PoLink;
import org.medici.docsources.domain.TitleOccsList;
import org.medici.docsources.domain.UserHistoryPeople;
import org.medici.docsources.domain.UserHistoryPeople.Action;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.security.DocSourcesLdapUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * This class is the default implementation of service responsible for every 
 * action on manuscript viewer.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Service
public class ManuscriptViewerServiceImpl implements ManuscriptViewerService {
	@Autowired
	private DocumentDAO documentDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private PeopleDAO peopleDAO;
	@Autowired
	private PlaceDAO placeDAO;
	@Autowired
	private VolumeDAO volumeDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findDocumentImage(Integer entryId, ImageType imageType, Integer imageProgTypeNum) throws ApplicationThrowable {
		try {
			Document document = getDocumentDAO().find(entryId);
			
			if (document != null) {

				List<Image> images = getImageDAO().findVolumeImages(document.getVolume().getVolNum(), document.getVolume().getVolLetExt(), imageType, imageProgTypeNum);
				if (images.size() > 0) {
					return images.get(0);
				} else 
					return null;
			} else {
				return null;
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findDocumentImage(Integer entryId, Integer imageOrder) throws ApplicationThrowable {
		try {
			Document document = getDocumentDAO().find(entryId);
			
			if (document != null) {
				return getImageDAO().findVolumeImage(document.getVolume().getVolNum(), document.getVolume().getVolLetExt(), imageOrder);
			} else {
				return null;
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findDocumentImage(Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum) throws ApplicationThrowable {
		try {
			List<Image> images = getImageDAO().findVolumeImages(volNum, volLetExt, imageType, imageProgTypeNum);
			
			if (images.size()>0) {
				return images.get(0);
			}
			
			return null;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Image> findDocumentImages(Integer entryId) throws ApplicationThrowable {
		try {
			Document document = getDocumentDAO().find(entryId);
			
			if (document != null) {
				// eilink not null is image linked to document
				/*if (document.getEiLink() != null) {
					return new ArrayList<Image>(0);
				} else {
					return getImageDAO().findDocumentImages(document.getVolume().getVolNum(), document.getVolume().getVolLetExt(), document.getFolioNum(), document.getFolioMod());
				}*/
				return getImageDAO().findDocumentImages(document.getVolume().getVolNum(), document.getVolume().getVolLetExt(), document.getFolioNum(), document.getFolioMod());
			} else {
				return new ArrayList<Image>(0);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Image> findDocumentImages(Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum) throws ApplicationThrowable {
		try {
			return getImageDAO().findVolumeImages(volNum, volLetExt, imageType, imageProgTypeNum);
			
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findDocumentImageThumbnail(Document document) throws ApplicationThrowable {
		try {
			if (document != null) {
				if ((document.getFolioNum() != null) && (document.getFolioNum() > 0)) {
					return getImageDAO().findImage(document.getVolume().getVolNum(), document.getVolume().getVolLetExt(), ImageType.C, document.getFolioNum());
				} else {
					return getImageDAO().findVolumeFirstImage(document.getVolume().getVolNum(), document.getVolume().getVolLetExt());
				}
			}
			
			return null;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 */
	@Override
	public Image findVolumeImage(Integer summaryId, Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum, Integer imageOrder) throws ApplicationThrowable {
		if (summaryId != null && volNum == null) {
			try {
				Volume volume = getVolumeDAO().find(summaryId);
				if (volume != null) {
					return getImageDAO().findVolumeImage(volume.getVolNum(), volume.getVolLetExt(), imageOrder);
				} else {
					return null;
				}
			} catch (Throwable th) {
				throw new ApplicationThrowable(th);
			}
		}
		if ((summaryId == null) && (imageOrder==null) && (imageProgTypeNum == null) && (volNum != null)) {
			return getImageDAO().findVolumeFirstImage(volNum, volLetExt);
		} else if (imageProgTypeNum != null) {
			return getImageDAO().findImage(volNum, volLetExt, imageType, imageProgTypeNum);
		} else if (imageOrder != null) {
			return getImageDAO().findVolumeImage(volNum, volLetExt, imageOrder);
		}

		return null;	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Image> findVolumeImages(Integer summaryId) throws ApplicationThrowable {
		try {
			Volume volume = getVolumeDAO().find(summaryId);
			
			return findVolumeImages(null, volume.getVolNum(), volume.getVolLetExt(), null, null, null);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Image> findVolumeImages(Integer summaryId, Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum, Integer imageOrder) throws ApplicationThrowable {
		if ((summaryId == null) && (imageOrder==null) && (imageProgTypeNum == null) && (volNum != null)) {
			return getImageDAO().findImages(volNum, volLetExt);
		} else if (imageProgTypeNum != null) {
			return getImageDAO().findVolumeImages(volNum, volLetExt, imageType, imageProgTypeNum);
		} else if (imageOrder != null) {
			return getImageDAO().findVolumeImages(volNum, volLetExt, imageOrder);
		}

		return null;
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findVolumeImages(Integer summaryId, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			// We extract volume to obtains its volNum and VolLetExt so we can use only one method.
			Volume volume = getVolumeDAO().find(summaryId);

			return getImageDAO().findImages(volume.getVolNum(), volume.getVolLetExt(), paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findVolumeImages(Integer volNum, String volLetExt, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getImageDAO().findImages(volNum, volLetExt, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findVolumeImageSpine(Integer volNum, String volLetExt) throws ApplicationThrowable {
		try {
			return getImageDAO().findVolumeSpine(volNum, volLetExt);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the documentDAO
	 */
	public DocumentDAO getDocumentDAO() {
		return documentDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DocumentExplorer getDocumentExplorer(DocumentExplorer documentExplorer) throws ApplicationThrowable {
		try {
			if (documentExplorer.getVolNum() == null) {
				Document document = getDocumentDAO().find(documentExplorer.getEntryId());
				documentExplorer.setVolNum(document.getVolume().getVolNum());
				documentExplorer.setVolLetExt(document.getVolume().getVolLetExt());
				if ((documentExplorer.getImage().getImageOrder()==null) && (documentExplorer.getImage().getImageProgTypeNum()==null) && (documentExplorer.getImage().getImageName() ==null)) {
					if (document.getFolioNum() != null) {
						if (document.getFolioNum() > 0) {
							documentExplorer.getImage().setImageProgTypeNum(document.getFolioNum());
							documentExplorer.getImage().setImageType(ImageType.C);
						}
					}
				}
			}

			return getImageDAO().findImages(documentExplorer);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the imageDAO
	 */
	public ImageDAO getImageDAO() {
		return imageDAO;
	}

	/**
	 * @return the peopleDAO
	 */
	public PeopleDAO getPeopleDAO() {
		return peopleDAO;
	}

	/**
	 * @return the placeDAO
	 */
	public PlaceDAO getPlaceDAO() {
		return placeDAO;
	}

	/**
	 * @return the volumeDAO
	 */
	public VolumeDAO getVolumeDAO() {
		return volumeDAO;
	}

	/**
	 * @param documentDAO the documentDAO to set
	 */
	public void setDocumentDAO(DocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
	}

	/**
	 * @param imageDAO the imageDAO to set
	 */
	public void setImageDAO(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}

	/**
	 * @param peopleDAO the peopleDAO to set
	 */
	public void setPeopleDAO(PeopleDAO peopleDAO) {
		this.peopleDAO = peopleDAO;
	}

	/**
	 * @param placeDAO the placeDAO to set
	 */
	public void setPlaceDAO(PlaceDAO placeDAO) {
		this.placeDAO = placeDAO;
	}

	/**
	 * @param volumeDAO the volumeDAO to set
	 */
	public void setVolumeDAO(VolumeDAO volumeDAO) {
		this.volumeDAO = volumeDAO;
	}
}
