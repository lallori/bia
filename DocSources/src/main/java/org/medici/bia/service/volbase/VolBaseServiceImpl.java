/*
 * VolBaseServiceImpl.java
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
package org.medici.bia.service.volbase;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.HistoryNavigator;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.VolumeExplorer;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.DocumentUtils;
import org.medici.bia.common.util.ForumUtils;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.common.util.VolumeUtils;
import org.medici.bia.common.volume.FoliosInformations;
import org.medici.bia.common.volume.VolumeInsert;
import org.medici.bia.common.volume.VolumeSummary;
import org.medici.bia.dao.document.DocumentDAO;
import org.medici.bia.dao.forum.ForumDAO;
import org.medici.bia.dao.forumoption.ForumOptionDAO;
import org.medici.bia.dao.image.ImageDAO;
import org.medici.bia.dao.month.MonthDAO;
import org.medici.bia.dao.schedone.SchedoneDAO;
import org.medici.bia.dao.serieslist.SeriesListDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userhistory.UserHistoryDAO;
import org.medici.bia.dao.usermarkedlist.UserMarkedListDAO;
import org.medici.bia.dao.usermarkedlistelement.UserMarkedListElementDAO;
import org.medici.bia.dao.vettinghistory.VettingHistoryDAO;
import org.medici.bia.dao.volume.VolumeDAO;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumOption;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Image.ImageType;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.Schedone;
import org.medici.bia.domain.SerieList;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserHistory.Action;
import org.medici.bia.domain.UserHistory.Category;
import org.medici.bia.domain.UserMarkedList;
import org.medici.bia.domain.UserMarkedListElement;
import org.medici.bia.domain.VettingHistory;
import org.medici.bia.domain.Volume;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.security.BiaUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is the default implementation of service responsible for every 
 * action on volume :
 * - add a new volume
 * - edit an existing volume
 * - delete a volume
 * - execute simple search
 * - execute advanced search 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Service
@Transactional(readOnly=true)
public class VolBaseServiceImpl implements VolBaseService {
	@Autowired
	private DocumentDAO documetDAO;
	@Autowired
	private ForumDAO forumDAO;
	@Autowired
	private ForumOptionDAO forumOptionDAO;
	@Autowired
	private ImageDAO imageDAO;
	private final Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private MonthDAO monthDAO;
	@Autowired
	private SchedoneDAO schedoneDAO;
	@Autowired
	private SeriesListDAO seriesListDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserHistoryDAO userHistoryDAO;
	@Autowired
	private UserMarkedListDAO userMarkedListDAO;
	@Autowired
	private UserMarkedListElementDAO userMarkedListElementDAO;
	@Autowired
	private VolumeDAO volumeDAO;
	@Autowired
	private VettingHistoryDAO vettingHistoryDAO;
	
	/**
	 * @return the documetDAO
	 */
	public DocumentDAO getDocumetDAO() {
		return documetDAO;
	}
	
	/**
	 * @return the forumDAO
	 */
	public ForumDAO getForumDAO() {
		return forumDAO;
	}

	/**
	 * @return the forumOptionDAO
	 */
	public ForumOptionDAO getForumOptionDAO() {
		return forumOptionDAO;
	}
	
	/**
	 * @return the imageDAO
	 */
	public ImageDAO getImageDAO() {
		return imageDAO;
	}

	/**
	 * @return the monthDAO
	 */
	public MonthDAO getMonthDAO() {
		return monthDAO;
	}
	
	/**
	 * @return the catalogDAO
	 */
	public SchedoneDAO getSchedoneDAO() {
		return schedoneDAO;
	}

	/**
	 * @return the seriesListDAO
	 */
	public SeriesListDAO getSeriesListDAO() {
		return seriesListDAO;
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * @return the UserMessageDAO
	 */
	public UserHistoryDAO getUserHistoryDAO() {
		return userHistoryDAO;
	}

	/**
	 * @return the userMarkedListDAO
	 */
	public UserMarkedListDAO getUserMarkedListDAO() {
		return userMarkedListDAO;
	}

	/**
	 * @return the userMarkedListElementDAO
	 */
	public UserMarkedListElementDAO getUserMarkedListElementDAO() {
		return userMarkedListElementDAO;
	}

	/**
	 * @return the vettingHistoryDAO
	 */
	public VettingHistoryDAO getVettingHistoryDAO() {
		return vettingHistoryDAO;
	}
	
	/**
	 * @return the volumeDAO
	 */
	public VolumeDAO getVolumeDAO() {
		return volumeDAO;
	}
	
	/**
	 * @param documetDAO the documetDAO to set
	 */
	public void setDocumetDAO(DocumentDAO documetDAO) {
		this.documetDAO = documetDAO;
	}

	/**
	 * @param forumDAO the forumDAO to set
	 */
	public void setForumDAO(ForumDAO forumDAO) {
		this.forumDAO = forumDAO;
	}

	/**
	 * @param forumOptionDAO the forumOptionDAO to set
	 */
	public void setForumOptionDAO(ForumOptionDAO forumOptionDAO) {
		this.forumOptionDAO = forumOptionDAO;
	}

	/**
	 * @param imageDAO the imageDAO to set
	 */
	public void setImageDAO(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}

	/**
	 * @param monthDAO the monthDAO to set
	 */
	public void setMonthDAO(MonthDAO monthDAO) {
		this.monthDAO = monthDAO;
	}

	/**
	 * @param schedoneDAO the schedoneDAO to set
	 */
	public void setSchedoneDAO(SchedoneDAO schedoneDAO) {
		this.schedoneDAO = schedoneDAO;
	}

	/**
	 * @param seriesListDAO the seriesListDAO to set
	 */
	public void setSeriesListDAO(SeriesListDAO seriesListDAO) {
		this.seriesListDAO = seriesListDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * @param userHistoryDAO the userHistoryDAO to set
	 */
	public void setUserHistoryDAO(UserHistoryDAO userHistoryDAO) {
		this.userHistoryDAO = userHistoryDAO;
	}

	/**
	 * @param userMarkedListDAO the userMarkedListDAO to set
	 */
	public void setUserMarkedListDAO(UserMarkedListDAO userMarkedListDAO) {
		this.userMarkedListDAO = userMarkedListDAO;
	}


	/**
	 * @param userMarkedListElementDAO the userMarkedListElementDAO to set
	 */
	public void setUserMarkedListElementDAO(
			UserMarkedListElementDAO userMarkedListElementDAO) {
		this.userMarkedListElementDAO = userMarkedListElementDAO;
	}

	/**
	 * @param volumeDAO the volumeDAO to set
	 */
	public void setVolumeDAO(VolumeDAO volumeDAO) {
		this.volumeDAO = volumeDAO;
	}
	
	/**
	 * @param vettingHistoryDAO the vettingHistoryDAO to set
	 */
	public void setVettingHistoryDAO(VettingHistoryDAO vettingHistoryDAO) {
		this.vettingHistoryDAO = vettingHistoryDAO;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Volume addNewVolume(Volume volume) throws ApplicationThrowable {
		try {
			// Setting primary key to null to permit persist operation, otherwise jpa will throw a Persistence Object Expcetion
			volume.setSummaryId(null);
			
			if (StringUtils.isEmpty(volume.getVolLetExt())) {
				volume.setVolLetExt(null);
			}

			// Retrieves every object references
			if (volume.getSerieList() != null) {
				volume.setSerieList(getSeriesListDAO().find(volume.getSerieList().getSeriesRefNum()));
			}

			//Setting fields that are defined as nullable = false
			volume.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			User user = getUserDAO().findUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()); 
			volume.setCreatedBy(user);
			volume.setLastUpdateBy(user);
			volume.setDateCreated(new Date());
			volume.setLastUpdate(new Date());
			volume.setVolTobeVetted(true);
			volume.setVolTobeVettedDate(new Date());
			volume.setVolVetted(false);
			volume.setBound(false);
			volume.setFolsNumbrd(false);
			volume.setOldAlphaIndex(false);
			volume.setPrintedDrawings(false);
			volume.setPrintedMaterial(false);
			volume.setItalian(false);
			volume.setSpanish(false);
			volume.setEnglish(false);
			volume.setLatin(false);
			volume.setGerman(false);
			volume.setFrench(false);
			volume.setCipher(false);

			if (volume.getStartMonthNum() != null) {
				Month month = getMonthDAO().find(volume.getStartMonthNum().getMonthNum());
				volume.setStartMonth(month.getMonthName());
				volume.setStartMonthNum(month);
			} else {
				volume.setStartMonth(null);
				volume.setStartMonthNum(null);
			}

			if (volume.getEndMonthNum() != null) {
				Month month = getMonthDAO().find(volume.getEndMonthNum().getMonthNum());
				volume.setEndMonth(month.getMonthName());
				volume.setEndMonthNum(month);
			} else {
				volume.setEndMonth(null);
				volume.setEndMonthNum(null);
			}

			volume.setStartDate(DateUtils.getLuceneDate(volume.getStartYear(), volume.getStartMonthNum(), volume.getStartDay()));
			volume.setEndDate(DateUtils.getLuceneDate(volume.getEndYear(), volume.getEndMonthNum(), volume.getEndDay()));
			volume.setLogicalDelete(Boolean.FALSE);
			volume.setDigitized(Boolean.FALSE);

			getVolumeDAO().persist(volume);
			
			getUserHistoryDAO().persist(new UserHistory(user, "Create volume", Action.CREATE, Category.VOLUME, volume));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Create volume", org.medici.bia.domain.VettingHistory.Action.CREATE, org.medici.bia.domain.VettingHistory.Category.VOLUME, volume));
			
			return volume;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Forum addNewVolumeForum(Volume volume) throws ApplicationThrowable {
		try {
			Forum forum = getForumDAO().getForumVolume(volume.getSummaryId());

			//this control is mandatory to prevent duplication records on forum
			if (forum == null) {
				volume = getVolumeDAO().find(volume.getSummaryId());
				Schedone schedone = getSchedoneDAO().findByVolume(volume.getVolNum(),volume.getVolLetExt());
				Forum parentForum = getForumDAO().find(NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("forum.identifier.volume")));
				forum = getForumDAO().addNewVolumeForum(parentForum, volume, schedone);
				
				ForumOption forumOption = ForumUtils.getForumOptionForForumTopicContainer(forum);
				getForumOptionDAO().persist(forumOption);

				// this method call is mandatory to increment topic number on parent forum
				getForumDAO().recursiveIncreaseTopicsNumber(parentForum);

				// we need to set new FullPath for recursive functions...
				forum.setFullPath(parentForum.getFullPath() + forum.getForumId() + ".");
				getForumDAO().merge(forum);
				
				// Increment the number of subforums
				getForumDAO().recursiveIncreaseSubForumsNumber(parentForum);

				User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

				getUserHistoryDAO().persist(new UserHistory(user, "Create new forum", Action.CREATE, Category.FORUM, forum));
				getVettingHistoryDAO().persist(new VettingHistory(user, "Create new forum", org.medici.bia.domain.VettingHistory.Action.CREATE, org.medici.bia.domain.VettingHistory.Category.FORUM, forum));
			}else if(forum.getLogicalDelete()){
				Forum parentForum = getForumDAO().find(NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("forum.identifier.volume")));
				
				forum.setLogicalDelete(Boolean.FALSE);
				forum.setTotalViews(0);
				forum.setLastUpdate(new Date());
				getForumDAO().merge(forum);
				
				// this method call is mandatory to increment topic number on parent forum
				getForumDAO().recursiveIncreaseTopicsNumber(parentForum);
				
				// Increment the number of subforums
				getForumDAO().recursiveIncreaseSubForumsNumber(parentForum);

				User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

				getUserHistoryDAO().persist(new UserHistory(user, "Create new forum", Action.CREATE, Category.FORUM, forum));
				getVettingHistoryDAO().persist(new VettingHistory(user, "Create new forum", org.medici.bia.domain.VettingHistory.Action.CREATE, org.medici.bia.domain.VettingHistory.Category.FORUM, forum));
			}

			return forum;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean checkVolumeDigitized(Integer summaryId) throws ApplicationThrowable {
		Boolean digitized = Boolean.FALSE;
		try {
			Volume volume = getVolumeDAO().find(summaryId);
			Image firstImage = getImageDAO().findVolumeFirstImage(volume.getVolNum(), volume.getVolLetExt());
			if (firstImage != null) {
				digitized = Boolean.TRUE;
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return digitized;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> checkChartaExistence(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String folioRV) throws ApplicationThrowable {
		Set<String> output = new HashSet<String>();
		String insNum = org.medici.bia.common.util.StringUtils.nullTrim(insertNum);
		String insLet = org.medici.bia.common.util.StringUtils.nullTrim(insertLet);
		
		String folMod = org.medici.bia.common.util.StringUtils.safeTrim(folioMod);
		String folRV = org.medici.bia.common.util.StringUtils.safeTrim(folioRV);
		
		if (getImageDAO().countImages(volNum, volLetExt, ImageType.C.toString(), insNum, insLet, folioNum, "", "") <= 0) {
			// There are no configuration with this folio number
			return output;
		}
		output.add("folioNumOk");
		
		if (!"".equals(folMod)) {
			if (getImageDAO().countImages(volNum, volLetExt, ImageType.C.toString(), insNum, insLet, folioNum, folMod, "") <= 0) {
				// There are no configuration with this folio number and extension
				return output;
			}
			output.add("folioModOk");
		}
		
		if (!"".equals(folRV)) {
			if (getImageDAO().countImages(volNum, volLetExt, ImageType.C.toString(), insNum, insLet, folioNum, folMod, folRV) <= 0) {
				return output;
			}
			output.add("folioRVOk");
		}
		
		return output;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean checkFolio(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String type) throws ApplicationThrowable {
		try{
			Image image = getImageDAO().findImage(volNum, volLetExt, ImageType.convertFromString(type), insertNum, insertLet, folioNum, folioMod);
			return image != null;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean checkVolumeDigitized(Integer volNum, String volLetExt) throws ApplicationThrowable {
		Boolean digitized = Boolean.FALSE;
		try {
			Image firstImage = getImageDAO().findVolumeFirstImage(volNum, volLetExt);
			if (firstImage != null) {
				digitized = Boolean.TRUE;
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return digitized;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkVolumeHasLinkedDocuments(Integer summaryId) throws ApplicationThrowable {
		Boolean linkedDocuments = Boolean.FALSE;
		try {
			Long count = getDocumetDAO().countDocumentsLinkedToAVolume(summaryId);
			if (count>0 ) {
				linkedDocuments = Boolean.TRUE;
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return linkedDocuments;
	}
	
	@Override
	public Volume compareVolume(Integer summaryId) throws ApplicationThrowable {
		try {
			Volume volume = getVolumeDAO().find(summaryId);
			
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Compare volume", Action.COMPARE, Category.VOLUME, volume));

			return volume;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Volume deleteVolume(Integer summaryId) throws ApplicationThrowable {
		Volume volumeToDelete = null;
		try {
			volumeToDelete = getVolumeDAO().find(summaryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		volumeToDelete.setLastUpdate(new Date());
		volumeToDelete.setLogicalDelete(Boolean.TRUE);

		try {
			getVolumeDAO().merge(volumeToDelete);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Delete volume", Action.DELETE, Category.VOLUME, volumeToDelete));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Delete volume", org.medici.bia.domain.VettingHistory.Action.DELETE, org.medici.bia.domain.VettingHistory.Category.VOLUME, volumeToDelete));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return volumeToDelete;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Volume editContextVolume(Volume volume) throws ApplicationThrowable {
		Volume volumeToUpdate = null;
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			volumeToUpdate = getVolumeDAO().find(volume.getSummaryId());
			volumeToUpdate.setCcontext(volume.getCcontext());
			volumeToUpdate.setInventarioSommarioDescription(volume.getInventarioSommarioDescription());
			volumeToUpdate.setLastUpdate(new Date());
			volumeToUpdate.setLastUpdateBy(user);
			getVolumeDAO().merge(volumeToUpdate);


			getUserHistoryDAO().persist(new UserHistory(user, "Edit context", Action.MODIFY, Category.VOLUME, volumeToUpdate));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit context", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.VOLUME, volumeToUpdate));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		return volumeToUpdate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Volume editCorrespondentsVolume(Volume volume) throws ApplicationThrowable {
		Volume volumeToUpdate = null;
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			volumeToUpdate = getVolumeDAO().find(volume.getSummaryId());
			volumeToUpdate.setRecips(volume.getRecips());
			volumeToUpdate.setSenders(volume.getSenders());
			volumeToUpdate.setLastUpdate(new Date());
			volumeToUpdate.setLastUpdateBy(user);
			getVolumeDAO().merge(volumeToUpdate);

			getUserHistoryDAO().persist(new UserHistory(user, "Edit correspondents", Action.MODIFY, Category.VOLUME, volumeToUpdate));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit correspondents", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.VOLUME, volumeToUpdate));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		return volumeToUpdate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Volume editDescriptionVolume(Volume volume) throws ApplicationThrowable {
		Volume volumeToUpdate = null;
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			volumeToUpdate = getVolumeDAO().find(volume.getSummaryId());

			volumeToUpdate.setOrgNotes(volume.getOrgNotes());
			volumeToUpdate.setCcondition(volume.getCcondition());
			volumeToUpdate.setBound(volume.getBound());
			volumeToUpdate.setFolioCount(volume.getFolioCount());
			volumeToUpdate.setFolsNumbrd(volume.getFolsNumbrd());
			volumeToUpdate.setOldAlphaIndex(volume.getOldAlphaIndex());
			volumeToUpdate.setPrintedMaterial(volume.getPrintedMaterial());
			volumeToUpdate.setPrintedDrawings(volume.getPrintedDrawings());
			volumeToUpdate.setItalian(volume.getItalian());
			volumeToUpdate.setSpanish(volume.getSpanish());
			volumeToUpdate.setEnglish(volume.getEnglish());
			volumeToUpdate.setLatin(volume.getLatin());
			volumeToUpdate.setGerman(volume.getGerman());
			volumeToUpdate.setFrench(volume.getFrench());
			volumeToUpdate.setOtherLang(volume.getOtherLang());
			volumeToUpdate.setCipher(volume.getCipher());
			volumeToUpdate.setCipherNotes(volume.getCipherNotes());
			volumeToUpdate.setLastUpdate(new Date());
			volumeToUpdate.setLastUpdateBy(user);

			getVolumeDAO().merge(volumeToUpdate);

			getUserHistoryDAO().persist(new UserHistory(user, "Edit description", Action.MODIFY, Category.VOLUME, volumeToUpdate));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit description", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.VOLUME, volumeToUpdate));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		return volumeToUpdate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Volume editDetailsVolume(Volume volume) throws ApplicationThrowable {
		Volume volumeToUpdate = null;
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			volumeToUpdate = getVolumeDAO().find(volume.getSummaryId());
		
			volumeToUpdate.setVolNum(volume.getVolNum());
			volumeToUpdate.setVolLetExt(volume.getVolLetExt());
	
			if (volume.getSerieList() != null) {
				volumeToUpdate.setSerieList(getSeriesListDAO().find(volume.getSerieList().getSeriesRefNum()));
			} else {
				volumeToUpdate.setSerieList(null);
			}
	
			// Start date section
			volumeToUpdate.setStartYear(volume.getStartYear());
			if (volume.getStartMonthNum() != null) {
				Month month = getMonthDAO().find(volume.getStartMonthNum().getMonthNum());
				volumeToUpdate.setStartMonth(month.getMonthName());
				volumeToUpdate.setStartMonthNum(month);
			} else {
				volumeToUpdate.setStartMonth(null);
				volumeToUpdate.setStartMonthNum(null);
			}
			volumeToUpdate.setStartDay(volume.getStartDay());
			volumeToUpdate.setStartDate(DateUtils.getLuceneDate(volumeToUpdate.getStartYear(), volumeToUpdate.getStartMonthNum(), volumeToUpdate.getStartDay()));
	
			// End date section
			volumeToUpdate.setEndYear(volume.getEndYear());
			if (volume.getEndMonthNum() != null) {
				Month month = getMonthDAO().find(volume.getEndMonthNum().getMonthNum());
				volumeToUpdate.setEndMonth(month.getMonthName());
				volumeToUpdate.setEndMonthNum(month);
			} else {
				volumeToUpdate.setEndMonth(null);
				volumeToUpdate.setEndMonthNum(null);
			}
			volumeToUpdate.setEndDay(volume.getEndDay());
			volumeToUpdate.setEndDate(DateUtils.getLuceneDate(volumeToUpdate.getEndYear(), volumeToUpdate.getEndMonthNum(), volumeToUpdate.getEndDay()));
	
			volumeToUpdate.setDateNotes(volume.getDateNotes());
			volumeToUpdate.setLastUpdate(new Date());
			volumeToUpdate.setLastUpdateBy(user);
			getVolumeDAO().merge(volumeToUpdate);

			getUserHistoryDAO().persist(new UserHistory(user, "Edit details", Action.MODIFY, Category.VOLUME, volumeToUpdate));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit details", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.VOLUME, volumeToUpdate));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		return volumeToUpdate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume findLastEntryVolume() throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserHistory userHistory = getUserHistoryDAO().findLastEntry(user, Category.VOLUME);
			
			if (userHistory != null) {
				return userHistory.getVolume();
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
	public List<Integer> findNewDigitizedVolumes() throws ApplicationThrowable {
		try {
			return getImageDAO().findNewDigitizedVolumes();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Schedone findSchedone(Integer volNum, String volLetExt) throws ApplicationThrowable {
		try{
			return getSchedoneDAO().findByVolume(volNum, volLetExt);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume findVolume(Integer summaryId) throws ApplicationThrowable {
		try {
			Volume volume = getVolumeDAO().find(summaryId);
			
			User user;
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
				user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

				getUserHistoryDAO().persist(new UserHistory(user, "Show volume", Action.VIEW, Category.VOLUME, volume));
			}

			return volume;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume findVolume(Integer volNum, String volLetExt) throws ApplicationThrowable {
		try {
			Volume volume = getVolumeDAO().findVolume(volNum, volLetExt);
			
			if(volume != null){
				User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

				getUserHistoryDAO().persist(new UserHistory(user, "Show volume", Action.VIEW, Category.VOLUME, volume));
			}

			return volume;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findVolumeDocumentsRelated(Integer summaryId) throws ApplicationThrowable {
		try{
			return getDocumetDAO().findNumberOfDocumentsRelatedVolume(summaryId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume findVolumeFromHistory(Integer idUserHistory) throws ApplicationThrowable {
		try{
			UserHistory userHistory = getUserHistoryDAO().find(idUserHistory);
			
			return userHistory.getVolume();
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public VolumeSummary findVolumeSummary(Volume volume) throws ApplicationThrowable {
		try{
			VolumeSummary volumeSummary = new VolumeSummary();
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
			}
			
			return volumeSummary;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void generateIndexMonth() throws ApplicationThrowable {
		try {
			getMonthDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void generateIndexSerieList() throws ApplicationThrowable {
		try {
			getSeriesListDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void generateIndexVolume() throws ApplicationThrowable {
		try {
			getVolumeDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryNavigator getCategoryHistoryNavigator(Volume volume) throws ApplicationThrowable {
		HistoryNavigator historyNavigator = new HistoryNavigator();
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserHistory userHistory = getUserHistoryDAO().findHistoryFromEntity(user, Category.VOLUME, volume.getSummaryId());
			
			UserHistory previousUserHistory = getUserHistoryDAO().findPreviousCategoryHistoryCursor(user, userHistory.getCategory(), userHistory.getIdUserHistory());
			UserHistory nextUserHistory = getUserHistoryDAO().findNextCategoryHistoryCursor(user, userHistory.getCategory(), userHistory.getIdUserHistory());
			
			historyNavigator.setPreviousHistoryUrl(HtmlUtils.getHistoryNavigatorPreviousPageUrl(previousUserHistory));
			historyNavigator.setNextHistoryUrl(HtmlUtils.getHistoryNavigatorNextPageUrl(nextUserHistory));

			return historyNavigator;
		}catch(Throwable th){
			logger.error(th);
		}

		return historyNavigator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Boolean> getDocumentsDigitizedState(List<Document> documents) throws ApplicationThrowable {
		Map<String, Boolean> retValue = new HashMap<String, Boolean>();
		try {
			for(Document document : documents)
				retValue.put(DocumentUtils.toMDPInsertFolioFormat(document), Boolean.FALSE);
			
			List<String> documentsDigitized = getImageDAO().findDigitizedDocumentsFromImages(documents);
			
			for(String documentString : documentsDigitized)
				retValue.put(documentString, Boolean.TRUE);
			
			return retValue;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryNavigator getHistoryNavigator(Integer historyId, Volume volume) throws ApplicationThrowable {
		HistoryNavigator historyNavigator = new HistoryNavigator();
		try {
			UserHistory userHistory = getUserHistoryDAO().find(historyId);
			
			UserHistory previousUserHistory = getUserHistoryDAO().findPreviousHistoryCursorFromVolume(userHistory.getUser(), userHistory.getIdUserHistory(), volume.getSummaryId());
			UserHistory nextUserHistory = getUserHistoryDAO().findNextHistoryCursorFromVolume(userHistory.getUser(), userHistory.getIdUserHistory(), volume.getSummaryId());
			
			historyNavigator.setPreviousHistoryUrl(HtmlUtils.getHistoryNavigatorPreviousPageUrl(previousUserHistory));
			historyNavigator.setNextHistoryUrl(HtmlUtils.getHistoryNavigatorNextPageUrl(nextUserHistory));

			return historyNavigator;
		}catch(Throwable th){
			logger.error(th);
		}

		return historyNavigator;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryNavigator getHistoryNavigator(Volume volume) throws ApplicationThrowable {
		HistoryNavigator historyNavigator = new HistoryNavigator();
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserHistory userHistory = getUserHistoryDAO().findHistoryFromEntity(user, Category.VOLUME, volume.getSummaryId());
			
			UserHistory previousUserHistory = getUserHistoryDAO().findPreviousHistoryCursorFromVolume(user, userHistory.getIdUserHistory(), volume.getSummaryId());
			UserHistory nextUserHistory = getUserHistoryDAO().findNextHistoryCursorFromVolume(user, userHistory.getIdUserHistory(), volume.getSummaryId());
			
			historyNavigator.setPreviousHistoryUrl(HtmlUtils.getHistoryNavigatorPreviousPageUrl(previousUserHistory));
			historyNavigator.setNextHistoryUrl(HtmlUtils.getHistoryNavigatorNextPageUrl(nextUserHistory));

			return historyNavigator;
		}catch(Throwable th){
			logger.error(th);
		}

		return historyNavigator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Month> getMonths() throws ApplicationThrowable {
		try {
			List<Month> months = getMonthDAO().getAllMonths();
			
			months.add(0, new Month(null, ""));
			
			return months;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
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
			Image image = getImageDAO().findImage(volumeExplorer);
			if (image != null) {
				volumeExplorer.setImage(image);
			}
			return volumeExplorer;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Forum getVolumeForum(Integer summaryId) throws ApplicationThrowable {
		try{
			return getForumDAO().getForumVolume(summaryId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Boolean> getVolumesDigitizedState(List<Integer> volNums, List<String> volLetExts) throws ApplicationThrowable {
		Map<String, Boolean> retValue = new HashMap<String, Boolean>();
		try{
			// initialize return object with all volumes setted to false
			for (int i=0; i<volNums.size(); i++) {
				retValue.put(VolumeUtils.toMDPFormat(volNums.get(i), volLetExts.get(i)), Boolean.FALSE);
			}

			// One only query...
			List<String> volumesDigitized = getImageDAO().findVolumesDigitized(volNums, volLetExts);

			// we set to true only volumes which are present in table images...
			for (String MDP : volumesDigitized) {
				//  If the map previously contained a mapping for the key, the old value is replaced by the specified value.
				retValue.put(MDP, Boolean.TRUE);
			}

			return retValue;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasCandidateInsert(Integer volNum, String volLetExt, String insNum) throws ApplicationThrowable {
		try {
			List<VolumeInsert> inserts = getImageDAO().findVolumeInserts(volNum, volLetExt);
			for (VolumeInsert insert : inserts) {
				if (insNum.equalsIgnoreCase(insert.getInsertNum())) {
						return Boolean.TRUE;
				}
			}
			return Boolean.FALSE;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean hasInsert(Integer volNum, String volLetExt, String insertNum, String insertLet) throws ApplicationThrowable {
		try {
			List<VolumeInsert> inserts = getImageDAO().findVolumeInserts(volNum, volLetExt);
			for (VolumeInsert insert : inserts) {
				if (insertNum.equalsIgnoreCase(insert.getInsertNum())) {
					if (insertLet == null) {
						if (insert.getInsertLet() == null)
							return Boolean.TRUE;
					} else { 
						if (insertLet.equalsIgnoreCase(insert.getInsertLet()))
							return Boolean.TRUE;
					}
				}
			}
			return Boolean.FALSE;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean hasInserts(Integer volNum, String volLetExt) throws ApplicationThrowable {
		try {
			return getImageDAO().hasInserts(volNum, volLetExt);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean ifVolumeAlreadyPresentInMarkedList(Integer summaryId) throws ApplicationThrowable {
		try{
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserMarkedList userMarkedList = getUserMarkedListDAO().getMyMarkedList(user);
			if (userMarkedList ==null) {
				return Boolean.FALSE;
			}
			UserMarkedListElement userMarkedListElement = getUserMarkedListElementDAO().findVolumeInMarkedList(userMarkedList.getIdMarkedList(), summaryId);
			if(userMarkedListElement != null){
				return Boolean.TRUE;
			}else{
				return Boolean.FALSE;
			}
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void optimizeIndexVolume() throws ApplicationThrowable {
		// TODO Auto-generated method stub
		
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchDocumentsRelated(String volumeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getDocumetDAO().searchDocumentsRelatedVolume(volumeToSearch, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SerieList> searchSeriesList(String alias) throws ApplicationThrowable {
		try {
			return getSeriesListDAO().findSeries(alias);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	@Override
	public Page searchVettingHistoryVolume(Integer summaryId, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			Volume volume = getVolumeDAO().find(summaryId);
			return getVettingHistoryDAO().getVettingHistoryVolume(volume, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchVolumes(String text, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getVolumeDAO().searchVolumes(text, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Volume undeleteVolume(Integer summaryId) throws ApplicationThrowable {
		Volume volumeToUnDelete = null;
		try {
			volumeToUnDelete = getVolumeDAO().find(summaryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		volumeToUnDelete.setLogicalDelete(Boolean.FALSE);

		try {
			getVolumeDAO().merge(volumeToUnDelete);

			User user = getUserDAO().findUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

			getUserHistoryDAO().persist(new UserHistory(user, "Recovered volume", Action.UNDELETE, Category.VOLUME, volumeToUnDelete));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return volumeToUnDelete;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void updateIndexVolume(Date fromDate) throws ApplicationThrowable {
		try {
			getVolumeDAO().updateIndex(fromDate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Integer updateNewDigitizedVolume(List<Integer> summaryIds) throws ApplicationThrowable {
		try {
			//TODO: Insert record in tblDigitization to activate/deactivate a volume
			return getVolumeDAO().updateNewDigitizedVolume(summaryIds);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
}
