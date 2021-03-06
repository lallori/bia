/*
 * AdminServiceImpl.java
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
package org.medici.bia.service.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.search.AccessLogSearch;
import org.medici.bia.common.search.Search;
import org.medici.bia.common.util.RegExUtils;
import org.medici.bia.dao.accesslog.AccessLogDAO;
import org.medici.bia.dao.accesslogstatistics.AccessLogStatisticsDAO;
import org.medici.bia.dao.activationuser.ActivationUserDAO;
import org.medici.bia.dao.annotation.AnnotationDAO;
import org.medici.bia.dao.applicationproperty.ApplicationPropertyDAO;
import org.medici.bia.dao.approvationuser.ApprovationUserDAO;
import org.medici.bia.dao.coursepeople.CoursePeopleDAO;
import org.medici.bia.dao.emailmessageuser.EmailMessageUserDAO;
import org.medici.bia.dao.forumpost.ForumPostDAO;
import org.medici.bia.dao.forumpostnotified.ForumPostNotifiedDAO;
import org.medici.bia.dao.forumtopic.ForumTopicDAO;
import org.medici.bia.dao.forumtopicwatch.ForumTopicWatchDAO;
import org.medici.bia.dao.lockeduser.LockedUserDAO;
import org.medici.bia.dao.month.MonthDAO;
import org.medici.bia.dao.passwordchangerequest.PasswordChangeRequestDAO;
import org.medici.bia.dao.persistentLogin.PersistentLoginDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userauthority.UserAuthorityDAO;
import org.medici.bia.dao.userhistory.UserHistoryDAO;
import org.medici.bia.dao.usermarkedlist.UserMarkedListDAO;
import org.medici.bia.dao.usermessage.UserMessageDAO;
import org.medici.bia.dao.userpersonalnotes.UserPersonalNotesDAO;
import org.medici.bia.dao.userrole.UserRoleDAO;
import org.medici.bia.dao.vettinghistory.VettingHistoryDAO;
import org.medici.bia.domain.AccessLog;
import org.medici.bia.domain.ActivationUser;
import org.medici.bia.domain.ApplicationProperty;
import org.medici.bia.domain.ApprovationUser;
import org.medici.bia.domain.EmailMessageUser;
import org.medici.bia.domain.ForumPostNotified;
import org.medici.bia.domain.LockedUser;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserAuthority.Authority;
import org.medici.bia.domain.UserMessage;
import org.medici.bia.domain.UserMessage.RecipientStatus;
import org.medici.bia.domain.UserRole;
import org.medici.bia.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Service
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AccessLogDAO accessLogDAO;
	@Autowired
	private AccessLogStatisticsDAO accessLogStatisticsDAO;
	@Autowired
	private ActivationUserDAO activationUserDAO;
	@Autowired
	private AnnotationDAO annotationDAO;
	@Autowired
	private ApplicationPropertyDAO applicationPropertyDAO;
	@Autowired
	private ApprovationUserDAO approvationUserDAO;
	@Autowired
	private CoursePeopleDAO coursePeopleDAO;
	@Autowired
	private EmailMessageUserDAO emailMessageUserDAO;
	@Autowired
	private ForumTopicDAO forumTopicDAO;
	@Autowired
	private ForumTopicWatchDAO forumTopicWatchDAO;
	@Autowired
	private ForumPostDAO forumPostDAO;
	@Autowired
	private ForumPostNotifiedDAO forumPostNotifiedDAO;
	@Autowired
	private LockedUserDAO lockedUserDAO;
	@Autowired
	private MonthDAO monthDAO;
	@Autowired
	private PasswordChangeRequestDAO passwordChangeRequestDAO;
	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;
	@Autowired
	private PersistentLoginDAO persistentLoginDAO;
	@Autowired
	private UserAuthorityDAO userAuthorityDAO;
	@Autowired(required = false)
	@Qualifier("userDAOJpaImpl")
	private UserDAO userDAO;
	@Autowired
	private UserHistoryDAO userHistoryDAO;
	@Autowired
	private UserMarkedListDAO userMarkedListDAO;
	@Autowired
	private UserMessageDAO userMessageDAO;
	@Autowired
	private UserPersonalNotesDAO userPersonalNotesDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;
	@Autowired
	private VettingHistoryDAO vettingHistoryDAO;
	
	public AccessLogDAO getAccessLogDAO() {
		return accessLogDAO;
	}

	public void setAccessLogDAO(AccessLogDAO accessLogDAO) {
		this.accessLogDAO = accessLogDAO;
	}

	public AccessLogStatisticsDAO getAccessLogStatisticsDAO() {
		return accessLogStatisticsDAO;
	}

	public void setAccessLogStatisticsDAO(
			AccessLogStatisticsDAO accessLogStatisticsDAO) {
		this.accessLogStatisticsDAO = accessLogStatisticsDAO;
	}

	public ActivationUserDAO getActivationUserDAO() {
		return activationUserDAO;
	}

	public void setActivationUserDAO(ActivationUserDAO activationUserDAO) {
		this.activationUserDAO = activationUserDAO;
	}

	public AnnotationDAO getAnnotationDAO() {
		return annotationDAO;
	}

	public void setAnnotationDAO(AnnotationDAO annotationDAO) {
		this.annotationDAO = annotationDAO;
	}

	public ApplicationPropertyDAO getApplicationPropertyDAO() {
		return applicationPropertyDAO;
	}

	public void setApplicationPropertyDAO(ApplicationPropertyDAO applicationPropertyDAO) {
		this.applicationPropertyDAO = applicationPropertyDAO;
	}

	public ApprovationUserDAO getApprovationUserDAO() {
		return approvationUserDAO;
	}

	public void setApprovationUserDAO(ApprovationUserDAO approvationUserDAO) {
		this.approvationUserDAO = approvationUserDAO;
	}
	
	public CoursePeopleDAO getCoursePeopleDAO() {
		return coursePeopleDAO;
	}

	public void setCoursePeopleDAO(CoursePeopleDAO coursePeopleDAO) {
		this.coursePeopleDAO = coursePeopleDAO;
	}

	public EmailMessageUserDAO getEmailMessageUserDAO() {
		return emailMessageUserDAO;
	}

	public void setEmailMessageUserDAO(EmailMessageUserDAO emailMessageUserDAO) {
		this.emailMessageUserDAO = emailMessageUserDAO;
	}

	public ForumTopicDAO getForumTopicDAO() {
		return forumTopicDAO;
	}

	public void setForumTopicDAO(ForumTopicDAO forumTopicDAO) {
		this.forumTopicDAO = forumTopicDAO;
	}

	public ForumTopicWatchDAO getForumTopicWatchDAO() {
		return forumTopicWatchDAO;
	}

	public void setForumTopicWatchDAO(ForumTopicWatchDAO forumTopicWatchDAO) {
		this.forumTopicWatchDAO = forumTopicWatchDAO;
	}

	public ForumPostDAO getForumPostDAO() {
		return forumPostDAO;
	}

	public void setForumPostDAO(ForumPostDAO forumPostDAO) {
		this.forumPostDAO = forumPostDAO;
	}

	public ForumPostNotifiedDAO getForumPostNotifiedDAO() {
		return forumPostNotifiedDAO;
	}

	public void setForumPostNotifiedDAO(ForumPostNotifiedDAO forumPostNotifiedDAO) {
		this.forumPostNotifiedDAO = forumPostNotifiedDAO;
	}

	public LockedUserDAO getLockedUserDAO() {
		return lockedUserDAO;
	}

	public void setLockedUserDAO(LockedUserDAO lockedUserDAO) {
		this.lockedUserDAO = lockedUserDAO;
	}

	public MonthDAO getMonthDAO() {
		return monthDAO;
	}

	public void setMonthDAO(MonthDAO monthDAO) {
		this.monthDAO = monthDAO;
	}

	public PasswordChangeRequestDAO getPasswordChangeRequestDAO() {
		return passwordChangeRequestDAO;
	}

	public void setPasswordChangeRequestDAO(PasswordChangeRequestDAO passwordChangeRequestDAO) {
		this.passwordChangeRequestDAO = passwordChangeRequestDAO;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public PersistentLoginDAO getPersistentLoginDAO() {
		return persistentLoginDAO;
	}

	public void setPersistentLoginDAO(PersistentLoginDAO persistentLoginDAO) {
		this.persistentLoginDAO = persistentLoginDAO;
	}

	public UserAuthorityDAO getUserAuthorityDAO() {
		return userAuthorityDAO;
	}

	public void setUserAuthorityDAO(UserAuthorityDAO userAuthorityDAO) {
		this.userAuthorityDAO = userAuthorityDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserHistoryDAO getUserHistoryDAO() {
		return userHistoryDAO;
	}

	public void setUserHistoryDAO(UserHistoryDAO userHistoryDAO) {
		this.userHistoryDAO = userHistoryDAO;
	}

	public UserMarkedListDAO getUserMarkedListDAO() {
		return userMarkedListDAO;
	}

	public void setUserMarkedListDAO(UserMarkedListDAO userMarkedListDAO) {
		this.userMarkedListDAO = userMarkedListDAO;
	}

	public UserMessageDAO getUserMessageDAO() {
		return userMessageDAO;
	}

	public void setUserMessageDAO(UserMessageDAO userMessageDAO) {
		this.userMessageDAO = userMessageDAO;
	}

	public UserPersonalNotesDAO getUserPersonalNotesDAO() {
		return userPersonalNotesDAO;
	}

	public void setUserPersonalNotesDAO(UserPersonalNotesDAO userPersonalNotesDAO) {
		this.userPersonalNotesDAO = userPersonalNotesDAO;
	}

	public UserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	public VettingHistoryDAO getVettingHistoryDAO() {
		return vettingHistoryDAO;
	}

	public void setVettingHistoryDAO(VettingHistoryDAO vettingHistoryDAO) {
		this.vettingHistoryDAO = vettingHistoryDAO;
	}

	@Override
	public LockedUser addLockedUser(User user) throws ApplicationThrowable {
		try{
			User userToLock = getUserDAO().findUser(user.getAccount());
			LockedUser lockedUser = new LockedUser(userToLock);
			lockedUser.setId(null);
			lockedUser.setMailSended(false);
			lockedUser.setMailUnlockSended(false);
			lockedUser.setUnlocked(false);
			getLockedUserDAO().persist(lockedUser);
			return lockedUser;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public User addNewUser(User user) throws ApplicationThrowable {
		try{
			User userToCreate = user;
			userToCreate.setInitials(generateInitials(userToCreate));
			if (userToCreate.getAddress() == null) {
				userToCreate.setAddress("");
			}
			if (userToCreate.getCity() == null) {
				userToCreate.setCity("");
			}
			if (userToCreate.getCountry() == null) {
				userToCreate.setCountry("");
			}
			if (userToCreate.getMail() == null) {
				userToCreate.setMail("");
			}
			if (userToCreate.getOrganization() == null) {
				userToCreate.setOrganization("");
			}
			userToCreate.setPassword(getPasswordEncoder().encodePassword(user.getPassword(), null));
			userToCreate.setBadLogin(new Integer(0));
			userToCreate.setForumNumberOfPost(new Long(0));
			userToCreate.setMailHide(Boolean.FALSE);
			userToCreate.setMailNotification(Boolean.FALSE);
			userToCreate.setForumTopicSubscription(Boolean.TRUE);
			userToCreate.setPortrait(Boolean.FALSE);
			userToCreate.setPortraitImageName(null);
			if (userToCreate.getApproved()) {
				User approvedBy = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
				userToCreate.setApprovedBy(approvedBy);
			}

			userToCreate.setRegistrationDate(new Date());
			if (user.getActive()){ 
				userToCreate.setActivationDate(new Date());
			}
			getUserDAO().persist(userToCreate);

			getUserRoleDAO().removeAllUserRoles(userToCreate.getAccount());
			if (user.getUserRoles() != null) {
				//We need before to attach jpa session..
				for (UserRole userRole : user.getUserRoles()) {
					userRole.setUser(userToCreate);
					userRole.setUserAuthority(getUserAuthorityDAO().find(userRole.getUserAuthority().getAuthority()));
				}
				getUserRoleDAO().addAllUserRoles(user.getUserRoles());
			}

			userToCreate.setUserRoles(user.getUserRoles());
			
			ApprovationUser approvationUser = new ApprovationUser(user);
			if (user.getActive()) {
				approvationUser.setMessageSended(Boolean.TRUE);
				approvationUser.setMessageSendedDate(new Date());
				approvationUser.setApproved(Boolean.TRUE);
				approvationUser.setApprovedDate(new Date());
			} else {
				approvationUser.setMessageSended(Boolean.FALSE);
				approvationUser.setMessageSendedDate(null);
				approvationUser.setApproved(Boolean.FALSE);
			}
			approvationUser.setMailSended(Boolean.FALSE);
			approvationUser.setMailSended(Boolean.FALSE);
			getApprovationUserDAO().persist(approvationUser);

			return userToCreate;
		} catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User approveNewUser(User user) throws ApplicationThrowable {
		try{
			User userToUpdate = getUserDAO().findUser(user.getAccount());
			userToUpdate.setApproved(user.getApproved());
			User admin = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
			userToUpdate.setApprovedBy(admin);
			getUserDAO().merge(userToUpdate);
			
			//Delete All new user's messages for other admin
			getUserMessageDAO().removeApprovationMessages(userToUpdate);			

			// We need to set approved on ApprovationUser entity to send automatic user mail 
			ApprovationUser approvationUser = getApprovationUserDAO().findByAccount(userToUpdate.getAccount());
			if (approvationUser != null) {
				approvationUser.setApproved(Boolean.TRUE);
				approvationUser.setApprovedDate(new Date());
				
				getApprovationUserDAO().merge(approvationUser);
			}

			return userToUpdate;
		} catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User approveUser(User user) throws ApplicationThrowable {
		try{
			User approvedBy = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			User userToUpdate = getUserDAO().findUser(user.getAccount());
			userToUpdate.setApproved(user.getApproved());
			userToUpdate.setApprovedBy(approvedBy);
			getUserDAO().merge(userToUpdate);
			
			// We need to set approved on ApprovationUser entity to send automatic user mail 
			ApprovationUser approvationUser = getApprovationUserDAO().findByAccount(userToUpdate.getAccount());
			
			if (approvationUser != null) {
				approvationUser.setApproved(Boolean.TRUE);
				approvationUser.setApprovedDate(new Date());
				
				getApprovationUserDAO().merge(approvationUser);
			}

			return userToUpdate;
		} catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void createAccessLogDailyStatistics(Date dateSelected) throws ApplicationThrowable {

		try {
			Integer statisticsDeleted = getAccessLogStatisticsDAO().deleteStatisticsOnDay(dateSelected);
			
			logger.info("Removed " + statisticsDeleted + " on date " + dateSelected);
			
			Boolean status = getAccessLogStatisticsDAO().generateStatisticsOnDay(dateSelected);
			
			if (status.equals(Boolean.TRUE)) {
				logger.info(" Statistics on date " + dateSelected + " successfully created.");
			} else {
				logger.error(" Statistics on date " + dateSelected + " not created.");
			}
		} catch(Throwable th){
			logger.error(" Statistics on date " + dateSelected + " not created.");
			throw new ApplicationThrowable(th);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public User editUser(User user, String originalAccount) throws ApplicationThrowable {
		try{
			User userToUpdate = getUserDAO().findUser(originalAccount);
			
			if (user.getPassword() != null) {
				if (!user.getPassword().equals(userToUpdate.getPassword())){
					userToUpdate.setPassword(getPasswordEncoder().encodePassword(user.getPassword(), null));
					userToUpdate.setLastPasswordChangeDate(new Date());
				}
			}

			userToUpdate.setFirstName(user.getFirstName());
			userToUpdate.setMiddleName(user.getMiddleName());
			//In this case if the first and the last names are the same, we don't generate another initials
			if(!userToUpdate.getFirstName().equalsIgnoreCase(user.getFirstName()) || !userToUpdate.getLastName().equalsIgnoreCase(user.getLastName())){
				userToUpdate.setInitials(generateInitials(user));
			}			
			userToUpdate.setInterests(user.getInterests());
			userToUpdate.setLastName(user.getLastName());
			
			userToUpdate.setExpirationDate(user.getExpirationDate());
			userToUpdate.setExpirationPasswordDate(user.getExpirationPasswordDate());
			userToUpdate.setActive(user.getActive());
			if (!userToUpdate.getApproved().equals(user.getApproved())) {
				userToUpdate.setApproved(user.getApproved());

				if (userToUpdate.getApproved()) {
					User approvedBy = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
					userToUpdate.setApprovedBy(approvedBy);
					
					ApprovationUser approvationUser = getApprovationUserDAO().findByAccount(userToUpdate.getAccount());
					
					if (approvationUser != null) {
						approvationUser.setApproved(Boolean.TRUE);
						approvationUser.setApprovedDate(new Date());
						
						getApprovationUserDAO().merge(approvationUser);
					}
				}
			}
			if(userToUpdate.getLocked() && !user.getLocked()){
				userToUpdate.setBadLogin(0);
			}
			userToUpdate.setLocked(user.getLocked());
			
			// user role adjustments
			
			// make a copy of the roles from the view
			Set<UserRole> fromViewRoles = new HashSet<UserRole>();
			fromViewRoles.addAll(user.getUserRoles());
			
			Set<UserRole> invariantRoles = new HashSet<UserRole>();
			
			if (userToUpdate.getUserRoles().size() > 0) {
				Map<Authority, UserRole> roleMap = new HashMap<Authority, UserRole>();
				for(UserRole role : userToUpdate.getUserRoles()) {
					roleMap.put(role.getUserAuthority().getAuthority(), role);
				}
				
				for(UserRole fromViewRole : user.getUserRoles()) {
					Authority auth = fromViewRole.getUserAuthority().getAuthority();
					if (roleMap.containsKey(auth)) {
						invariantRoles.add(roleMap.remove(auth));
						fromViewRoles.remove(fromViewRole);
					}
				}
				
				if (roleMap.values().size() > 0) {
					// RR: in the roleMap there are the roles to remove
					if (roleMap.containsKey(Authority.TEACHERS) || roleMap.containsKey(Authority.STUDENTS)) {
						// we first remove CoursePeople entries (if exist)
						if (roleMap.containsKey(Authority.TEACHERS)) {
							getCoursePeopleDAO().removeCoursePersonByUserRole(roleMap.get(Authority.TEACHERS));
						}
						if (roleMap.containsKey(Authority.STUDENTS)) {
							getCoursePeopleDAO().removeCoursePersonByUserRole(roleMap.get(Authority.STUDENTS));
						}
					}
					
					// now we remove the not associated user roles
					for(UserRole toRemoveRole : roleMap.values()) {
						getUserRoleDAO().remove(toRemoveRole);
					}
				}
			}
			
			if (fromViewRoles != null) {
				//We need before to attach jpa session..
				for (UserRole userRole : fromViewRoles) {
					userRole.setUser(userToUpdate);
					userRole.setUserAuthority(getUserAuthorityDAO().find(userRole.getUserAuthority().getAuthority()));
				}
				getUserRoleDAO().addAllUserRoles(fromViewRoles);
				invariantRoles.addAll(fromViewRoles);
			}
			
			userToUpdate.setUserRoles(invariantRoles);

			getUserDAO().merge(userToUpdate);
			
			// in case of "account rename", we need to update every entity containing account field. 
			if (!userToUpdate.getAccount().equals(user.getAccount())) {
				getUserDAO().renameAccount(originalAccount, user.getAccount());

				userToUpdate=getUserDAO().findUser(user.getAccount());
				userToUpdate.setInitials(generateInitials(user));
				getUserDAO().merge(userToUpdate);

				getAccessLogDAO().renameAccount(originalAccount, user.getAccount());
				getUserMessageDAO().renameAccount(originalAccount, user.getAccount());
				getUserMessageDAO().renameRecipient(originalAccount, user.getAccount());
				getUserMessageDAO().renameSender(originalAccount, user.getAccount());
				getPasswordChangeRequestDAO().renameAccount(originalAccount, user.getAccount());
			}

			return userToUpdate;
		} catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User editUserMail(User user) throws ApplicationThrowable {
		try{
			User userToUpdate = getUserDAO().findUser(user.getAccount());
			
			if (userToUpdate != null) {
				userToUpdate.setMail(user.getMail());
				userToUpdate.setMailHide(user.getMailHide());
				userToUpdate.setMailNotification(user.getMailNotification());
				userToUpdate.setForumTopicSubscription(user.getForumTopicSubscription());
				
				getUserDAO().merge(userToUpdate);
			}
			
			return userToUpdate;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AccessLog findAccessLog(Integer idAccessLog) throws ApplicationThrowable {
		try {
			return getAccessLogDAO().find(idAccessLog);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EmailMessageUser> findEmailMessageUserToSend() throws ApplicationThrowable {
		try{
			EmailMessageUser emailMessageUser = new EmailMessageUser();
			emailMessageUser.setMailSended(false);
			return getEmailMessageUserDAO().searchEmailMessageUserToSend(emailMessageUser);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ActivationUser> findActivationUsers() throws ApplicationThrowable {
		try {
			ActivationUser activationUser = new ActivationUser();
			activationUser.setActive(Boolean.FALSE);
			activationUser.setMailSended(Boolean.FALSE);
			return getActivationUserDAO().searchUsersToActivate(activationUser);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public List<ForumPostNotified> findForumPostRepliedNotNotified() throws ApplicationThrowable {
		try{
			return getForumPostNotifiedDAO().findForumPostRepliedNotNotified();
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LockedUser> findLockedUsers() throws ApplicationThrowable {
		try{
			LockedUser lockedUser = new LockedUser();
			lockedUser.setUnlocked(Boolean.FALSE);
			lockedUser.setMailSended(Boolean.FALSE);
			lockedUser.setMailUnlockSended(Boolean.FALSE);
			return getLockedUserDAO().searchLockedUsers(lockedUser);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Date> findMissingStatisticsDate(Integer numberMaxOfDay) throws ApplicationThrowable {
		try{
			return getAccessLogStatisticsDAO().findMissingStatisticsDate(numberMaxOfDay);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LockedUser> findUnlockedUsers() throws ApplicationThrowable {
		try{
			LockedUser lockedUser = new LockedUser();
			lockedUser.setUnlocked(Boolean.TRUE);
			lockedUser.setMailSended(Boolean.TRUE);
			lockedUser.setMailUnlockSended(Boolean.FALSE);
			return getLockedUserDAO().searchLockedUsers(lockedUser);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUser(String account) throws ApplicationThrowable {
		try{
			return getUserDAO().findUser(account);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findUsers(User user) throws ApplicationThrowable {
		try{
			return getUserDAO().findUsers(user);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findUsers(User user, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getUserDAO().findUsers(user, paginationFilter);
		} catch(Throwable th){
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ApprovationUser> findUsersApprovedNotMailed() throws ApplicationThrowable {
		try {
			return getApprovationUserDAO().searchUsersApprovedNotMailed();
		} catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ApprovationUser> findUsersToApprove() throws ApplicationThrowable {
		try {
			return getApprovationUserDAO().searchUsersToApprove();
		} catch(Throwable th){
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * This method will generate the user inital. The format of the returned string is :
	 * - First Letter of field First Name;
	 * - First or more Letters of field Last Name;
	 * 
	 * The generation code is based on calculate user initial making a previous
	 * search. 
	 * If the initial are not present, we return it, otherwise we increment the initial 
	 * with another letter get from last name and so on until we find one initial that 
	 * is not present on database.
	 * 
	 * @param user The user  
	 * @return A string rapresenting user inital.
	 */
	private String generateInitials(User user) throws ApplicationThrowable {
		try {
			// We extract name and surname from user object and we clean eventually wrong chars, it's necessary to calculate user initial 
			String name = user.getFirstName().trim().toUpperCase();
			String surname = user.getLastName().trim().toUpperCase();
			if (!StringUtils.isAlpha(name)) {
				name = RegExUtils.trimNonAlphaChars(name);
			}
			if (!StringUtils.isAlpha(surname)) {
				surname = RegExUtils.trimNonAlphaChars(surname);
			}
			
			String initialLetterOfName = name.substring(0, 1).toUpperCase();
			
			//In this case we edit a user
			if(getUserDAO().findUser(user.getAccount()) != null)
				return getUserDAO().findUser(user.getAccount()).getInitials();

			// We try to search user with these inital, if we find one, we increment letters in initial  
			for (int i=1; i<surname.length(); i++) {
				String initialChoiced = initialLetterOfName + surname.substring(i-1, i);
				User userToSearch = new User();
				userToSearch.setInitials(initialChoiced);
				if (getUserDAO().findUser(userToSearch) == null)
					return initialChoiced;
			}
			
			// unreachable statement, we always find unused initial, but we need to insert this code beacuse the method must always return a value.
			return null;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserAuthority> getAuthorities() throws ApplicationThrowable {
		try {
			return getUserAuthorityDAO().getAuthorities();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Integer removeBadForumPostNotified() throws ApplicationThrowable {
		try {
			return getForumPostNotifiedDAO().removeBadElements();
		} catch(Throwable th) {
			logger.error("");
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchAccessLog(AccessLogSearch accessLogSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getAccessLogDAO().searchMYSQL(accessLogSearch, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchUser(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getUserDAO().searchMYSQL(searchContainer, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchWhoIsOnline(PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getUserDAO().searchWhoIsOnline(paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendApprovationMessage(List<ApprovationUser> approvationUsers) throws ApplicationThrowable {
		try {
			List<User> administratorUsers = getUserDAO().findUsers(getUserAuthorityDAO().find(Authority.ADMINISTRATORS));

			for (ApprovationUser currentApprovationUser : approvationUsers) {
				for (User currentAdministator : administratorUsers) {
					try {
						UserMessage userMessage = new UserMessage();
						userMessage.setSender(currentApprovationUser.getUser().getAccount());
						userMessage.setRecipient(currentAdministator.getAccount());
						userMessage.setUser(currentAdministator);
						userMessage.setParentMessage(null);
						userMessage.setReadedDate(null);
						userMessage.setRecipientStatus(RecipientStatus.NOT_READ);
						userMessage.setSendedDate(new Date());
						userMessage.setSubject(ApplicationPropertyManager.getApplicationProperty("message.approvationUser.subject",
												new String[]{
												currentApprovationUser.getUser().getAccount()},"{", "}"));
						userMessage.setBody(ApplicationPropertyManager.getApplicationProperty("message.approvationUser.text",
											new String[]{
												currentApprovationUser.getUser().getAccount(), 
												currentApprovationUser.getUser().getFirstName(),
												currentApprovationUser.getUser().getMiddleName(),
												currentApprovationUser.getUser().getLastName(), 
												currentApprovationUser.getUser().getOrganization(),
												currentApprovationUser.getUser().getMail(), 
												ApplicationPropertyManager.getApplicationProperty("website.protocol"),
												ApplicationPropertyManager.getApplicationProperty("website.domain"),
												ApplicationPropertyManager.getApplicationProperty("website.contextPath")
											},
											"{", "}"));
	
						getUserMessageDAO().persist(userMessage);
					} catch (PersistenceException persistenceException) {
						logger.error(persistenceException);
					}
				}					

				currentApprovationUser.setMessageSended(Boolean.TRUE);
				currentApprovationUser.setMessageSendedDate(new Date());
				getApprovationUserDAO().merge(currentApprovationUser);
			}
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendLockedMessage(User user) throws ApplicationThrowable {
		try{			
			List<User> administratorUsers = getUserDAO().findUsers(getUserAuthorityDAO().find(Authority.ADMINISTRATORS));

			for (User currentAdministator : administratorUsers) {
				try {
					UserMessage userMessage = new UserMessage();
					userMessage.setSender(user.getAccount());
					userMessage.setRecipient(currentAdministator.getAccount());
					userMessage.setUser(currentAdministator);
					userMessage.setParentMessage(null);
					userMessage.setReadedDate(null);
					userMessage.setRecipientStatus(RecipientStatus.NOT_READ);
					userMessage.setSendedDate(new Date());
					userMessage.setSubject(ApplicationPropertyManager.getApplicationProperty("message.lockedUser.subject",
											new String[]{
											user.getAccount()},"{", "}"));
					userMessage.setBody(ApplicationPropertyManager.getApplicationProperty("message.lockedUser.text",
										new String[]{
											user.getAccount(),
											ApplicationPropertyManager.getApplicationProperty("website.protocol"),
											ApplicationPropertyManager.getApplicationProperty("website.domain"),
											ApplicationPropertyManager.getApplicationProperty("website.contextPath")
										},
										"{", "}"));
					getUserMessageDAO().persist(userMessage);
				} catch (PersistenceException persistenceException) {
					logger.error(persistenceException);
				}
			}
			
		}catch(Throwable throwable){
			throw new ApplicationThrowable(throwable);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User unlockUser(User user) throws ApplicationThrowable {
		try{
			User userToUpdate = getUserDAO().findUser(user.getAccount());
			userToUpdate.setLocked(user.getLocked());
			userToUpdate.setBadLogin(0);
			getUserDAO().merge(userToUpdate);
			
			//Delete All user's locked messages for other admin
			getUserMessageDAO().removeUnlockedMessages(userToUpdate);
			
			LockedUser lockedUser = getLockedUserDAO().findByAccount(userToUpdate.getAccount());
			if(lockedUser != null){
				lockedUser.setUnlocked(true);
				lockedUser.setUnlockedBy((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
				getLockedUserDAO().merge(lockedUser);
			}

			return userToUpdate;
		} catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void updateApplicationProperties(Map<String, String> hashMap) throws ApplicationThrowable {
		try {
			Iterator<Entry<String,String>> iterator = hashMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) iterator.next();
				ApplicationProperty applicationProperty = getApplicationPropertyDAO().find(pairs.getKey());
				applicationProperty.setValue(pairs.getValue());
				getApplicationPropertyDAO().merge(applicationProperty);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
}
