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
package org.medici.bia.service.manuscriptviewer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.medici.bia.common.pagination.DocumentExplorer;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.VolumeExplorer;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.util.ImageUtils;
import org.medici.bia.common.util.UserRoleUtils;
import org.medici.bia.common.volume.FoliosInformations;
import org.medici.bia.common.volume.VolumeSummary;
import org.medici.bia.dao.annotation.AnnotationDAO;
import org.medici.bia.dao.document.DocumentDAO;
import org.medici.bia.dao.forum.ForumDAO;
import org.medici.bia.dao.forumoption.ForumOptionDAO;
import org.medici.bia.dao.forumpost.ForumPostDAO;
import org.medici.bia.dao.forumtopic.ForumTopicDAO;
import org.medici.bia.dao.forumtopicwatch.ForumTopicWatchDAO;
import org.medici.bia.dao.image.ImageDAO;
import org.medici.bia.dao.people.PeopleDAO;
import org.medici.bia.dao.place.PlaceDAO;
import org.medici.bia.dao.schedone.SchedoneDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userrole.UserRoleDAO;
import org.medici.bia.dao.volume.VolumeDAO;
import org.medici.bia.domain.Annotation;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Document.RectoVerso;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.Forum.Status;
import org.medici.bia.domain.Forum.SubType;
import org.medici.bia.domain.Forum.Type;
import org.medici.bia.domain.ForumOption;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Image.ImageType;
import org.medici.bia.domain.ForumTopicWatch;
import org.medici.bia.domain.Schedone;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserRole;
import org.medici.bia.domain.Volume;
import org.medici.bia.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is the default implementation of service responsible for every 
 * action on manuscript viewer.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Service
@Transactional(readOnly=true)
public class ManuscriptViewerServiceImpl implements ManuscriptViewerService {
	@Autowired
	private AnnotationDAO annotationDAO;
	@Autowired
	private SchedoneDAO catalogDAO;
	@Autowired
	private DocumentDAO documentDAO;
	@Autowired
	private ForumDAO forumDAO;
	@Autowired
	private ForumOptionDAO forumOptionDAO;
	@Autowired
	private ForumPostDAO forumPostDAO;
	@Autowired
	private ForumTopicDAO forumTopicDAO;
	@Autowired
	private ForumTopicWatchDAO forumTopicWatchDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private PeopleDAO peopleDAO;
	@Autowired
	private PlaceDAO placeDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;
	@Autowired
	private VolumeDAO volumeDAO;

	/**
	 * 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Annotation addNewAnnotation(Annotation annotation, Image image, String ipAddress) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
			annotation.setUser(user);

			// first of all, persisting annotation 
			getAnnotationDAO().persist(annotation);
			
			if (annotation.getType().equals(Annotation.Type.GENERAL)) {
				Forum generalQuestionsForum = getForumDAO().find(NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("forum.identifier.general")));
				image = getImageDAO().find(image.getImageId());
				Volume volume = getVolumeDAO().findVolume(image.getVolNum(), image.getVolLetExt()); 

				if (volume != null) {
					Forum volumeForum = getForumDAO().findVolumeForumFromParent(generalQuestionsForum, volume.getSummaryId());
					if (volumeForum == null) {
						volume = getVolumeDAO().find(volume.getSummaryId());
						Schedone schedone = getCatalogDAO().findByVolume(volume.getVolNum(),volume.getVolLetExt());
						volumeForum = getForumDAO().addNewVolumeForum(generalQuestionsForum, volume, schedone);
						
						ForumOption forumOption = new ForumOption(volumeForum);
						forumOption.setGroupBySubForum(Boolean.TRUE);
						forumOption.setCanHaveTopics(Boolean.TRUE);
						forumOption.setCanDeletePosts(Boolean.TRUE);
						forumOption.setCanDeleteTopics(Boolean.TRUE);
						forumOption.setCanEditPosts(Boolean.TRUE);
						forumOption.setCanEditPosts(Boolean.TRUE);
						forumOption.setCanPostReplys(Boolean.TRUE);
						getForumOptionDAO().persist(forumOption);
					}
					
					Forum folioForum  = getForumDAO().findFolioForumFromParent(volumeForum, image.getImageId());
					if (folioForum == null) {
						folioForum  = new Forum();
						folioForum.setDateCreated(new Date());
						folioForum.setDescription("Folio n. " + image.getImageOrder() + " " + image.getImageRectoVerso());
						folioForum.setTitle("Folio n. " + image.getImageOrder() + " " + image.getImageRectoVerso());
						folioForum.setForumParent(volumeForum);
						folioForum.setImage(image);
						folioForum.setDispositionOrder(new Integer(0));
						folioForum.setPostsNumber(new Integer(0));
						folioForum.setStatus(Status.ONLINE);
						folioForum.setTopicsNumber(new Integer(0));
						folioForum.setType(Type.FORUM);
						folioForum.setSubType(SubType.FOLIO);
						folioForum = getForumDAO().addNewForum(folioForum);
					}
	
					// create specific topic on annotation...
					ForumTopic forumTopic = new ForumTopic(null);
					forumTopic.setForum(folioForum);
					forumTopic.setDateCreated(new Date());
					forumTopic.setLastUpdate(forumTopic.getDateCreated());
					forumTopic.setIpAddress(ipAddress);
					forumTopic.setAnnotation(annotation);
					
					forumTopic.setUser(user);
					forumTopic.setSubject(annotation.getTitle());
					forumTopic.setTotalReplies(new Integer(0));
					forumTopic.setTotalViews(new Integer(0));
					forumTopic.setLastPost(null);
					forumTopic.setFirstPost(null);
					
					getForumTopicDAO().persist(forumTopic);
					
					ForumPost forumPost = new ForumPost();
					forumPost.setSubject(annotation.getTitle());
					forumPost.setText(annotation.getText());
					forumPost.setTopic(forumTopic);
					forumPost.setDateCreated(new Date());
					forumPost.setLastUpdate(new Date());
					forumPost.setUser(getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())));
					forumPost.setAnnotation(annotation);
					getForumPostDAO().persist(forumPost);

					getForumDAO().recursiveIncreasePostsNumber(folioForum);

					folioForum.setLastPost(forumPost);
					getForumDAO().merge(folioForum);
				} else {
					return null;
				}
			} else if (annotation.getType().equals(Annotation.Type.PALEOGRAPHY)) {
				Forum paleographyForum = getForumDAO().find(NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("forum.identifier.paleography")));
				image = getImageDAO().find(image.getImageId());

				// create specific topic on annotation...
				ForumTopic forumTopic = new ForumTopic(null);
				forumTopic.setForum(paleographyForum);
				forumTopic.setDateCreated(new Date());
				forumTopic.setLastUpdate(forumTopic.getDateCreated());
				forumTopic.setIpAddress(ipAddress);
				forumTopic.setAnnotation(annotation);
				
				forumTopic.setUser(user);
				forumTopic.setSubject(annotation.getTitle());
				forumTopic.setTotalReplies(new Integer(0));
				forumTopic.setTotalViews(new Integer(0));
				forumTopic.setLastPost(null);
				forumTopic.setFirstPost(null);
				
				getForumTopicDAO().persist(forumTopic);
				
				ForumPost forumPost = new ForumPost();
				forumPost.setSubject(annotation.getTitle());
				forumPost.setText(annotation.getText());
				forumPost.setTopic(forumTopic);
				forumPost.setDateCreated(new Date());
				forumPost.setLastUpdate(new Date());
				forumPost.setUser(getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())));
				forumPost.setAnnotation(annotation);
				getForumPostDAO().persist(forumPost);

				getForumDAO().recursiveIncreasePostsNumber(paleographyForum);

				paleographyForum.setLastPost(forumPost);
				getForumDAO().merge(paleographyForum);
			} else if (annotation.getType().equals(Annotation.Type.PERSONAL)) {
				// we do nothing on forums...
			}

			return annotation;
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findDocumentImage(Integer entryId, Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum, Integer imageOrder) throws ApplicationThrowable {
		try {
			if ((entryId != null) && (imageProgTypeNum != null)) {
				Document document = getDocumentDAO().find(entryId);
				
				if (document != null) {
	
					List<Image> images = getImageDAO().findVolumeImages(document.getVolume().getVolNum(), document.getVolume().getVolLetExt(), imageType, imageProgTypeNum);
					if (images != null) {
						return images.get(0);
					} else 
						return null;
				} else {
					return null;
				}
			} else if ((entryId != null) && (imageOrder != null)) {
				Document document = getDocumentDAO().find(entryId);
				
				if (document != null && imageOrder != null) {
					return getImageDAO().findVolumeImage(document.getVolume().getVolNum(), document.getVolume().getVolLetExt(), imageOrder);
				} else {
					return null;
				}
			} else {
				List<Image> images = getImageDAO().findVolumeImages(volNum, volLetExt, imageType, imageProgTypeNum);
				
				if (images != null) {
					return images.get(0);
				}
				
				return null;
			}
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
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
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Image> findDocumentImages(Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum) throws ApplicationThrowable {
		try {
			return getImageDAO().findVolumeImages(volNum, volLetExt, imageType, imageProgTypeNum);
			
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findDocumentImageThumbnail(Document document) throws ApplicationThrowable {
		try {
			if (document != null) {
				// We extract only image based on folioNum 
				if ((document.getFolioNum() != null) && (document.getFolioNum() > 0) && (document.getFolioMod() == null)) {
					return getImageDAO().findImage(document.getVolume().getVolNum(), document.getVolume().getVolLetExt(), ImageType.C, document.getFolioNum());
				}else if((document.getFolioNum() != null) && (document.getFolioNum() > 0) && (document.getFolioMod() != null)){
					return getImageDAO().findDocumentImage(document.getVolume().getVolNum(), document.getVolume().getVolLetExt(), document.getFolioNum(), document.getFolioMod());
				}
			}
			
			return null;
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findImage(Integer imageId) throws ApplicationThrowable {
		try{
			Image image = getImageDAO().find(imageId);
			return image;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Document> findLinkedDocument(Integer volNum, String volLetExt, Image image) throws ApplicationThrowable {
		try {
			RectoVerso rectoVerso = RectoVerso.convertFromString(image.getImageRectoVerso().toString());
			List<Document> documents = getDocumentDAO().findDocument(volNum, volLetExt, image.getInsertNum(), image.getInsertLet(), image.getImageProgTypeNum(), image.getMissedNumbering(), rectoVerso);
			if (documents != null) {
				return documents;
			} else {
				return null;
			}

		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findVolumeImage(Integer summaryId, Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum, Integer imageOrder) throws ApplicationThrowable {
		if (summaryId != null) {
			try {
				Volume volume = getVolumeDAO().find(summaryId);
				if (volume != null) {
					return getImageDAO().findVolumeImage(volume.getVolNum(), volume.getVolLetExt(), imageOrder);
				} else {
					return null;
				}
			} catch (Throwable throwable) {
				throw new ApplicationThrowable(throwable);
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
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
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
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findVolumeImages(Integer volNum, String volLetExt, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getImageDAO().findImages(volNum, volLetExt, paginationFilter);
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findVolumeImageSpine(Integer volNum, String volLetExt) throws ApplicationThrowable {
		try {
			return getImageDAO().findVolumeSpine(volNum, volLetExt);
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}

	@Override
	public VolumeSummary findVolumeSummmary(Integer volNum, String volLetExt) throws ApplicationThrowable {
		try {
			VolumeSummary volumeSummary = new VolumeSummary();
			Volume volume = getVolumeDAO().findVolume(volNum, volLetExt);
			if (volume != null) {
				volumeSummary.setSummaryId(volume.getSummaryId());
				volumeSummary.setVolNum(volume.getVolNum());
				volumeSummary.setVolLetExt(volume.getVolLetExt());
				if(volume.getSerieList() != null){
					volumeSummary.setCarteggio(volume.getSerieList().toString());
				}
				volumeSummary.setCcontext(volume.getCcontext());
				volumeSummary.setInventarioSommarioDescription(volume.getInventarioSommarioDescription());
				
				FoliosInformations foliosInformations = getImageDAO().findVolumeFoliosInformations(volume.getVolNum(), volume.getVolLetExt());
				if (foliosInformations != null) {
					volumeSummary.setTotal(foliosInformations.getTotal());
					volumeSummary.setTotalRubricario(foliosInformations.getTotalRubricario());
					volumeSummary.setTotalCarta(foliosInformations.getTotalCarta());
					volumeSummary.setTotalGuardia(foliosInformations.getTotalGuardia());
					volumeSummary.setTotalAppendix(foliosInformations.getTotalAppendix());
					volumeSummary.setTotalOther(foliosInformations.getTotalOther());
					volumeSummary.setTotalMissingFolios(foliosInformations.getTotalMissingFolios());
					volumeSummary.setMissingFolios(foliosInformations.getMissingNumberingFolios());
					volumeSummary.setMisnumberedFolios(foliosInformations.getMisnumberedFolios());
				}
				
				Schedone catalog = getCatalogDAO().findByVolume(volume.getVolNum(), volume.getVolLetExt());
				if (catalog != null) {
					volumeSummary.setCartulazione(catalog.getCartulazione());
					volumeSummary.setNoteCartulazione(catalog.getNoteCartulazione());
					volumeSummary.setNoteCartulazioneEng(catalog.getNoteCartulazioneEng());
					//volumeSummary.setHeight(catalog.getNumeroTotaleImmagini());
					//volumeSummary.setWidth(catalog.getNumeroTotaleImmagini());
				}
			}
			
			return volumeSummary;
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}	
	}

	/**
	 * @return the annotationDAO
	 */
	public AnnotationDAO getAnnotationDAO() {
		return annotationDAO;
	}

	/**
	 * @return the catalogDAO
	 */
	public SchedoneDAO getCatalogDAO() {
		return catalogDAO;
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
							documentExplorer.getImage().setMissedNumbering(document.getFolioMod());
						}
					}
				}
			}

			return getImageDAO().findImages(documentExplorer);
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public VolumeExplorer getVolumeExplorer(VolumeExplorer volumeExplorer) throws ApplicationThrowable {
		try {
			if (volumeExplorer.getSummaryId() != null && volumeExplorer.getVolNum() == null) {
				if (volumeExplorer.getSummaryId() >0) {
					Volume volume = getVolumeDAO().find(volumeExplorer.getSummaryId());
					volumeExplorer.setVolNum(volume.getVolNum());
					volumeExplorer.setVolLetExt(volume.getVolLetExt());
				}
			} else if (volumeExplorer.getSummaryId() == null && volumeExplorer.getVolNum() != null) {
				Volume volume = getVolumeDAO().findVolume(volumeExplorer.getVolNum(), volumeExplorer.getVolLetExt());
				if (volume != null) {
					volumeExplorer.setSummaryId(volume.getSummaryId());
				}
			}
			return getImageDAO().findImages(volumeExplorer);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	public ForumDAO getForumDAO() {
		return forumDAO;
	}

	public ForumOptionDAO getForumOptionDAO() {
		return forumOptionDAO;
	}

	public ForumPostDAO getForumPostDAO() {
		return forumPostDAO;
	}

	public ForumTopicDAO getForumTopicDAO() {
		return forumTopicDAO;
	}

	/**
	 * @return the forumTopicWatchDAO
	 */
	public ForumTopicWatchDAO getForumTopicWatchDAO() {
		return forumTopicWatchDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Annotation> getImageAnnotations(String imageName) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
			return getAnnotationDAO().findAnnotationByImageAndUser(imageName, user);
		} catch(Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Annotation, Boolean> getImageAnnotationsToEdit(String imageName) throws ApplicationThrowable {
		try {
			Map<Annotation, Boolean> resultMap = new HashMap<Annotation, Boolean>();
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
			List<Annotation> result = getAnnotationDAO().findAnnotationByImageAndUser(imageName, user);
			List<UserRole> userRoles = getUserRoleDAO().findUserRoles(user.getAccount());
			UserRole mostSignificantRole = UserRoleUtils.getMostSignificantRole(userRoles);
			for(Annotation currentAnnotation : result){
				if(currentAnnotation.getUser().getAccount().equals(user.getAccount()) || mostSignificantRole.getUserAuthority().getAuthority().equals(UserAuthority.Authority.ADMINISTRATORS)){
					resultMap.put(currentAnnotation, Boolean.TRUE);
				}else{
					resultMap.put(currentAnnotation, Boolean.FALSE);
				}
			}
			return resultMap;
		} catch(Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}

	/**
	 * @return the imageDAO
	 */
	public ImageDAO getImageDAO() {
		return imageDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageType getImageType(Integer volNum, String volLetExt, Integer imageOrder) throws ApplicationThrowable {
		try {
			Image image = getImageDAO().findVolumeImage(volNum, volLetExt, imageOrder);
			if (image != null) {
				return image.getImageType();
			}
			
			return null;
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}	
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
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	/**
	 * @return the userRoleDAO
	 */
	public UserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	/**
	 * @return the volumeDAO
	 */
	public VolumeDAO getVolumeDAO() {
		return volumeDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isDocumentExtract(Integer entryId) throws ApplicationThrowable {
		try{
			Document document = getDocumentDAO().find(entryId);
			if(document.getSynExtract() != null && (!document.getSynExtract().getDocExtract().isEmpty())){
				return Boolean.TRUE;
			}
			
			return Boolean.FALSE;
		}catch(Throwable throwable){
			throw new ApplicationThrowable(throwable);
		}
	}

	/**
	 * @param annotationDAO the annotationDAO to set
	 */
	public void setAnnotationDAO(AnnotationDAO annotationDAO) {
		this.annotationDAO = annotationDAO;
	}

	/**
	 * @param catalogDAO the catalogDAO to set
	 */
	public void setCatalogDAO(SchedoneDAO catalogDAO) {
		this.catalogDAO = catalogDAO;
	}

	/**
	 * @param documentDAO the documentDAO to set
	 */
	public void setDocumentDAO(DocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
	}

	public void setForumDAO(ForumDAO forumDAO) {
		this.forumDAO = forumDAO;
	}

	public void setForumOptionDAO(ForumOptionDAO forumOptionDAO) {
		this.forumOptionDAO = forumOptionDAO;
	}

	public void setForumPostDAO(ForumPostDAO forumPostDAO) {
		this.forumPostDAO = forumPostDAO;
	}

	public void setForumTopicDAO(ForumTopicDAO forumTopicDAO) {
		this.forumTopicDAO = forumTopicDAO;
	}

	/**
	 * @param forumTopicWatchDAO the forumTopicWatchDAO to set
	 */
	public void setForumTopicWatchDAO(ForumTopicWatchDAO forumTopicWatchDAO) {
		this.forumTopicWatchDAO = forumTopicWatchDAO;
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
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * @param userRoleDAO the userRoleDAO to set
	 */
	public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	/**
	 * @param volumeDAO the volumeDAO to set
	 */
	public void setVolumeDAO(VolumeDAO volumeDAO) {
		this.volumeDAO = volumeDAO;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Annotation updateAnnotation(Integer imageId, Annotation annotation) throws ApplicationThrowable {
		try {
			Image image = getImageDAO().findImageByImageId(imageId);
			if (image == null) {
				return null;
			}

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
			annotation.setUser(user);
			annotation.setImage(image);
			if(annotation.getAnnotationId() == 0){
				annotation.setAnnotationId(null);
				annotation.setDateCreated(new Date());
				annotation.setLastUpdate(new Date());
				if (annotation.getType() == null) {
					annotation.setType(Annotation.Type.GENERAL);
				}
				getAnnotationDAO().persist(annotation);
			}
			return annotation;
		} catch (Throwable throwable){
			throw new ApplicationThrowable(throwable);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Map<Annotation, Integer> updateAnnotations(Integer imageId, List<Annotation> annotationsList, String ipAddress) throws ApplicationThrowable {
		try {
			Map<Annotation, Integer> returnMap = new HashMap<Annotation, Integer>();
			Image image = getImageDAO().findImageByImageId(imageId);
			if (image == null) {
				return new HashMap<Annotation, Integer>(0);
			}

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
			
			List<Annotation> annotationSaved = getAnnotationDAO().findAnnotationByImageAndUser(image.getImageName(), user);

			for (Annotation annotation : annotationsList) {
				annotation.setUser(user);
				annotation.setImage(image);

				Annotation persistedAnnotation = getAnnotationDAO().findByAnnotationId(annotation.getAnnotationId());
				if (persistedAnnotation == null) {
					annotation.setAnnotationId(null);
					annotation.setDateCreated(new Date());
					annotation.setLastUpdate(new Date());
					if (annotation.getType() == null) {
						annotation.setType(Annotation.Type.GENERAL);
					}
					getAnnotationDAO().persist(annotation);
					
					if (annotation.getType().equals(Annotation.Type.GENERAL)) {
						Forum generalQuestionsForum = getForumDAO().find(NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("forum.identifier.general")));
						image = getImageDAO().find(image.getImageId());
						
						ForumTopic topicAnnotation = new ForumTopic(null);
						topicAnnotation.setForum(generalQuestionsForum);
						topicAnnotation.setDateCreated(new Date());
						topicAnnotation.setLastUpdate(topicAnnotation.getDateCreated());
						topicAnnotation.setIpAddress(ipAddress);
						topicAnnotation.setUser(user);
						topicAnnotation.setSubject(annotation.getTitle() + " (Annotation)");
						topicAnnotation.setTotalReplies(new Integer(0));
						topicAnnotation.setTotalViews(new Integer(0));
						topicAnnotation.setLastPost(null);
						topicAnnotation.setFirstPost(null);
						topicAnnotation.setLogicalDelete(Boolean.FALSE);
						
						topicAnnotation.setAnnotation(annotation);
						getForumTopicDAO().persist(topicAnnotation);
						
						getForumDAO().recursiveIncreaseTopicsNumber(generalQuestionsForum);
						if (user.getForumTopicSubscription().equals(Boolean.TRUE)) {
							ForumTopicWatch forumTopicWatch = new ForumTopicWatch(topicAnnotation, user);
							getForumTopicWatchDAO().persist(forumTopicWatch);
						}
						annotation.setForumTopic(topicAnnotation);
						getAnnotationDAO().merge(annotation);
						returnMap.put(annotation, topicAnnotation.getTopicId());
//						annotation.setText(topicAnnotation.getSubject());
//						getAnnotationDAO().merge(annotation);
					}else if(annotation.getType().equals(Annotation.Type.PALEOGRAPHY)){
						Forum paleographyForum = getForumDAO().find(NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("forum.identifier.paleography")));
						image = getImageDAO().find(imageId);
						
						ForumTopic topicAnnotation = new ForumTopic(null);
						topicAnnotation.setForum(paleographyForum);
						topicAnnotation.setDateCreated(new Date());
						topicAnnotation.setLastUpdate(topicAnnotation.getDateCreated());
						topicAnnotation.setIpAddress(ipAddress);
						topicAnnotation.setUser(user);
						topicAnnotation.setSubject(annotation.getTitle() + " (Annotation)");
						topicAnnotation.setTotalReplies(new Integer(0));
						topicAnnotation.setTotalViews(new Integer(0));
						topicAnnotation.setLastPost(null);
						topicAnnotation.setFirstPost(null);
						topicAnnotation.setLogicalDelete(Boolean.FALSE);
						
						topicAnnotation.setAnnotation(annotation);
						getForumTopicDAO().persist(topicAnnotation);
						
						getForumDAO().recursiveIncreaseTopicsNumber(paleographyForum);
						if (user.getForumTopicSubscription().equals(Boolean.TRUE)) {
							ForumTopicWatch forumTopicWatch = new ForumTopicWatch(topicAnnotation, user);
							getForumTopicWatchDAO().persist(forumTopicWatch);
						}
						annotation.setForumTopic(topicAnnotation);
						getAnnotationDAO().merge(annotation);
						returnMap.put(annotation, topicAnnotation.getTopicId());
					}
				} else {
					// we override id value beacause wen you edit an existing annotation, client set id to numeric.
					annotation.setAnnotationId(persistedAnnotation.getAnnotationId());
					
					int i = 0;
					Boolean finded = Boolean.FALSE;
					while(i < annotationSaved.size() && !finded){
						if(annotationSaved.get(i).getAnnotationId() == persistedAnnotation.getAnnotationId()){
							annotationSaved.remove(i);
							finded = Boolean.TRUE;
						}
						i++;
					}
					
					annotation.setLastUpdate(new Date());
					getAnnotationDAO().merge(annotation);
					returnMap.put(annotation, 0);
				}
			}
			
			if(!annotationSaved.isEmpty()){
				for(Annotation annotation : annotationSaved){
					ForumTopic topicAnnotation = getForumTopicDAO().find(annotation.getForumTopic().getTopicId());
					topicAnnotation.setLogicalDelete(Boolean.TRUE);
					topicAnnotation.setAnnotation(null);
					getForumTopicDAO().merge(topicAnnotation);
					getForumPostDAO().deleteForumPostsFromForumTopic(topicAnnotation.getTopicId());
					Forum forum = topicAnnotation.getForum();
					recursiveSetLastPost(forum);
					getForumDAO().recursiveDecreasePostsNumber(forum, topicAnnotation.getTotalReplies());
					getForumDAO().recursiveDecreaseTopicsNumber(forum);
//					forum.setPostsNumber(forum.getPostsNumber() - forumTopic.getTotalReplies());
					getForumDAO().merge(forum);				
					annotation.setForumTopic(null);
					getAnnotationDAO().remove(annotation);
				}
			}
			
			return returnMap;
		} catch (Throwable throwable){
			throw new ApplicationThrowable(throwable);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	private void recursiveSetLastPost(Forum forum) throws ApplicationThrowable {
		if(forum.getType().equals(Type.CATEGORY)){
			return;
		}

		ForumPost lastPost = getForumPostDAO().findLastPostFromForum(forum);
		forum.setLastPost(lastPost);
		//last update must be updated to obtain a correct indexing of forum
		forum.setLastUpdate(new Date());
		getForumDAO().merge(forum);

		recursiveSetLastPost(forum.getForumParent(), lastPost);
	}
	
	/**
	 * {@inheritDoc}
	 */
	private void recursiveSetLastPost(Forum forum, ForumPost forumPost) throws ApplicationThrowable {
		if(forum.getType().equals(Type.CATEGORY)){
			return;
		}
		
		forum.setLastPost(forumPost);
		//last update must be updated to obtain a correct indexing of forum
		forum.setLastUpdate(new Date());
		getForumDAO().merge(forum);
		
		recursiveSetLastPost(forum.getForumParent(), forumPost);
	}
}

