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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.search.AccessLogSearch;
import org.medici.bia.common.util.RegExUtils;
import org.medici.bia.dao.accesslog.AccessLogDAO;
import org.medici.bia.dao.accesslogstatistics.AccessLogStatisticsDAO;
import org.medici.bia.dao.activationuser.ActivationUserDAO;
import org.medici.bia.dao.applicationproperty.ApplicationPropertyDAO;
import org.medici.bia.dao.approvationuser.ApprovationUserDAO;
import org.medici.bia.dao.month.MonthDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userauthority.UserAuthorityDAO;
import org.medici.bia.dao.usermessage.UserMessageDAO;
import org.medici.bia.dao.userrole.UserRoleDAO;
import org.medici.bia.domain.AccessLog;
import org.medici.bia.domain.ActivationUser;
import org.medici.bia.domain.ApplicationProperty;
import org.medici.bia.domain.ApprovationUser;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserMessage;
import org.medici.bia.domain.UserRole;
import org.medici.bia.domain.UserAuthority.Authority;
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
	@Autowired
	private AccessLogDAO accessLogDAO;

	@Autowired
	private AccessLogStatisticsDAO accessLogStatisticsDAO;

	@Autowired
	private ActivationUserDAO activationUserDAO;

	@Autowired
	private ApplicationPropertyDAO applicationPropertyDAO;

	@Autowired
	private ApprovationUserDAO approvationUserDAO;

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private MonthDAO monthDAO;

	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserAuthorityDAO userAuthorityDAO;

	@Autowired(required = false)
	@Qualifier("userDAOJpaImpl")
	private UserDAO userDAO;
	
	@Autowired
	private UserMessageDAO userMessageDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;

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
			userToCreate.setMailHide(false);
			userToCreate.setMailNotification(false);
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
	public User approveUser(User user) throws ApplicationThrowable {
		try{
			User userToUpdate = getUserDAO().findUser(user.getAccount());
			userToUpdate.setApproved(user.getApproved());
			getUserDAO().merge(userToUpdate);
			
			// TODO; Here we insert code to send mail to user...
			
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
	public User editUser(User user) throws ApplicationThrowable {
		try{
			User userToUpdate = getUserDAO().findUser(user.getAccount()); 

			if (user.getPassword() != null) {
				if (!user.getPassword().equals(userToUpdate.getPassword())){
					userToUpdate.setPassword(getPasswordEncoder().encodePassword(user.getPassword(), null));
					userToUpdate.setLastPasswordChangeDate(new Date());
				}
			}

			userToUpdate.setFirstName(user.getFirstName());
			userToUpdate.setMiddleName(user.getMiddleName());
			userToUpdate.setInitials(generateInitials(user));
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
					}
				}
			}
			userToUpdate.setLocked(user.getLocked());
						
			getUserRoleDAO().removeAllUserRoles(userToUpdate.getAccount());
			if (user.getUserRoles() != null) {
				//We need before to attach jpa session..
				for (UserRole userRole : user.getUserRoles()) {
					userRole.setUser(userToUpdate);
					userRole.setUserAuthority(getUserAuthorityDAO().find(userRole.getUserAuthority().getAuthority()));
				}
				getUserRoleDAO().addAllUserRoles(user.getUserRoles());
			}
			
			
			userToUpdate.setUserRoles(user.getUserRoles());
			getUserDAO().merge(userToUpdate);
			
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
	 * @return the accessLogDAO
	 */
	public AccessLogDAO getAccessLogDAO() {
		return accessLogDAO;
	}

	/**
	 * @return the accessLogStatisticsDAO
	 */
	public AccessLogStatisticsDAO getAccessLogStatisticsDAO() {
		return accessLogStatisticsDAO;
	}

	/**
	 * @return the applicationPropertyDAO
	 */
	public ApplicationPropertyDAO getApplicationPropertyDAO() {
		return applicationPropertyDAO;
	}

	/**
	 * 
	 * @return
	 */
	public ApprovationUserDAO getApprovationUserDAO() {
		return approvationUserDAO;
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
	 * @return the monthDAO
	 */
	public MonthDAO getMonthDAO() {
		return monthDAO;
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
	 * 
	 * @return
	 */
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	/**
	 * @return the userAuthorityDAO
	 */
	public UserAuthorityDAO getUserAuthorityDAO() {
		return userAuthorityDAO;
	}

	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * @return the userMessageDAO
	 */
	public UserMessageDAO getUserMessageDAO() {
		return userMessageDAO;
	}

	/**
	 * 
	 * @return
	 */
	public UserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
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
	public void sendApprovationMessage(List<ApprovationUser> approvationUsers) throws ApplicationThrowable {
		try {
			List<User> administratorUsers = getUserRoleDAO().findUsers(getUserAuthorityDAO().find(Authority.ADMINISTRATORS));

			for (ApprovationUser currentApprovationUser : approvationUsers) {
				for (User currentAdministator : administratorUsers) {
					try {
						UserMessage userMessage = new UserMessage();
						userMessage.setSender(currentApprovationUser.getUser().getAccount());
						userMessage.setRecipient(currentAdministator.getAccount());
						userMessage.setUser(currentAdministator);
						userMessage.setParentMessage(null);
						userMessage.setReadedDate(null);
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
												ApplicationPropertyManager.getApplicationProperty("website.domain")
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
	 * @param accessLogDAO the accessLogDAO to set
	 */
	public void setAccessLogDAO(AccessLogDAO accessLogDAO) {
		this.accessLogDAO = accessLogDAO;
	}

	/**
	 * @param accessLogStatisticsDAO the accessLogStatisticsDAO to set
	 */
	public void setAccessLogStatisticsDAO(AccessLogStatisticsDAO accessLogStatisticsDAO) {
		this.accessLogStatisticsDAO = accessLogStatisticsDAO;
	}

	/**
	 * @param activationUserDAO the activationUserDAO to set
	 */
	public void setActivationUserDAO(ActivationUserDAO activationUserDAO) {
		this.activationUserDAO = activationUserDAO;
	}

	/**
	 * @return the activationUserDAO
	 */
	public ActivationUserDAO getActivationUserDAO() {
		return activationUserDAO;
	}

	/**
	 * @param applicationPropertyDAO
	 *            the applicationPropertyDAO to set
	 */
	public void setApplicationPropertyDAO(ApplicationPropertyDAO applicationPropertyDAO) {
		this.applicationPropertyDAO = applicationPropertyDAO;
	}

	/**
	 * 
	 * @param approvationUserDAO
	 */
	public void setApprovationUserDAO(ApprovationUserDAO approvationUserDAO) {
		this.approvationUserDAO = approvationUserDAO;
	}

	/**
	 * @param monthDAO the monthDAO to set
	 */
	public void setMonthDAO(MonthDAO monthDAO) {
		this.monthDAO = monthDAO;
	}

	/**
	 * 
	 * @param passwordEncoder
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * @param userAuthorityDAO the userAuthorityDAO to set
	 */
	public void setUserAuthorityDAO(UserAuthorityDAO userAuthorityDAO) {
		this.userAuthorityDAO = userAuthorityDAO;
	}
	/**
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	/**
	 * @param userMessageDAO the userMessageDAO to set
	 */
	public void setUserMessageDAO(UserMessageDAO userMessageDAO) {
		this.userMessageDAO = userMessageDAO;
	}
	
	public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
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
