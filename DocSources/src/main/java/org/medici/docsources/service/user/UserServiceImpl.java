/*
 * UserServiceImpl.java
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
package org.medici.docsources.service.user;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.property.ApplicationPropertyManager;
import org.medici.docsources.common.util.ApplicationError;
import org.medici.docsources.common.util.RegExUtils;
import org.medici.docsources.dao.activationuser.ActivationUserDAO;
import org.medici.docsources.dao.country.CountryDAO;
import org.medici.docsources.dao.document.DocumentDAO;
import org.medici.docsources.dao.passwordchangerequest.PasswordChangeRequestDAO;
import org.medici.docsources.dao.people.PeopleDAO;
import org.medici.docsources.dao.personalnotes.PersonalNotesDAO;
import org.medici.docsources.dao.place.PlaceDAO;
import org.medici.docsources.dao.user.UserDAO;
import org.medici.docsources.dao.userhistory.UserHistoryDAO;
import org.medici.docsources.dao.userinformation.UserInformationDAO;
import org.medici.docsources.dao.usermessage.UserMessageDAO;
import org.medici.docsources.dao.volume.VolumeDAO;
import org.medici.docsources.domain.ActivationUser;
import org.medici.docsources.domain.Country;
import org.medici.docsources.domain.PasswordChangeRequest;
import org.medici.docsources.domain.PersonalNotes;
import org.medici.docsources.domain.User;
import org.medici.docsources.domain.User.UserRole;
import org.medici.docsources.domain.UserHistory;
import org.medici.docsources.domain.UserHistory.Category;
import org.medici.docsources.domain.UserInformation;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This class is the default implementation of service responsible for every 
 * action on user :
 * - registration user
 * - unregister user
 * - requesting activation code
 * - updating user information
 * - find user in database
 * - reset user password
 *  
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Service
@Transactional(readOnly=true)
public class UserServiceImpl implements UserService {
	@Autowired
	private ActivationUserDAO activationUserDAO;
	@Autowired
	private CountryDAO countryDAO;
	@Autowired
	private DocumentDAO documentDAO;
	@Autowired
	private PasswordChangeRequestDAO passwordChangeRequestDAO;
	@Autowired
	private PeopleDAO peopleDAO;
	@Autowired
	private PersonalNotesDAO personalNotesDAO;
	@Autowired
	private PlaceDAO placeDAO;

	@Autowired(required = false)
	@Qualifier("userDaoLdapImpl")
	private UserDAO userDAO;

	@Autowired
	private UserHistoryDAO userHistoryDAO;

	@Autowired
	private UserInformationDAO userInformationDAO;

	@Autowired
	private UserMessageDAO userMessageDAO;

	@Autowired
	private VolumeDAO volumeDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void activateUser(UUID uuid) throws ApplicationThrowable {
		try {
			//Extract activationUser entity to obtains account linked the activation request
			ActivationUser activationUser = getActivationUserDAO().find(uuid.toString());
			
			//Search user by account and update active flag
			UserInformation user = getUserInformationDAO().find(activationUser.getAccount());
			user.setActive(Boolean.TRUE);
			getUserInformationDAO().merge(user);
			
			//Complete the activation by updating the activation request.
			activationUser.setActive(Boolean.TRUE);
			activationUser.setActivationDate(new Date());
			activationUser.setActivationIPAddress(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr());
			getActivationUserDAO().merge(activationUser);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void addActivationUserRequest(User user, String remoteAddress) throws ApplicationThrowable {
		try {
			ActivationUser activationUser = new ActivationUser();
			activationUser.setUuid(UUID.randomUUID().toString());
			activationUser.setAccount(user.getAccount());
			activationUser.setRequestDate(new Date());
			activationUser.setRequestIPAddress(remoteAddress);
			activationUser.setActive(Boolean.FALSE);			
			activationUser.setMailSended(Boolean.FALSE);			
			getActivationUserDAO().persist(activationUser);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void addPasswordChangeRequest(User user, String remoteAddress) throws ApplicationThrowable {
		try {
			PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest();
			passwordChangeRequest.setUuid(UUID.randomUUID().toString());
			passwordChangeRequest.setAccount(user.getAccount());
			passwordChangeRequest.setRequestDate(new Date());
			passwordChangeRequest.setRequestIPAddress(remoteAddress);
			passwordChangeRequest.setReset(Boolean.FALSE);			
			passwordChangeRequest.setMailSended(Boolean.FALSE);			
			getPasswordChangeRequestDAO().persist(passwordChangeRequest);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteMyHistory() throws ApplicationThrowable {
		try {
			getUserHistoryDAO().deleteMyHistory();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteMyHistory(Category category) throws ApplicationThrowable {
		try {
			getUserHistoryDAO().deleteMyHistory(category);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteUser(User user) throws ApplicationThrowable {
		try {
			getUserDAO().remove(user);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteUserHistory(String username) throws ApplicationThrowable {
		try {
			getUserHistoryDAO().deleteUserHistory(username);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteUserHistory(String username, Category category) throws ApplicationThrowable {
		try {
			getUserHistoryDAO().deleteUserHistory(username, category);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	} 
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public PersonalNotes editPersonalNotes(String account, PersonalNotes personalNotes) throws ApplicationThrowable {
		PersonalNotes personalNotesToUpdate = null;
		
		try {
			personalNotesToUpdate = getPersonalNotesDAO().find(account);
			
			if (personalNotesToUpdate == null) {
				personalNotesToUpdate = new PersonalNotes(personalNotes.getPersonalNotes());
				personalNotesToUpdate.setAccount(account);
				personalNotesToUpdate.setLastUpdate(new Date());
				getPersonalNotesDAO().persist(personalNotesToUpdate);
			} else {
				personalNotesToUpdate.setPersonalNotes(personalNotes.getPersonalNotes());
				personalNotesToUpdate.setLastUpdate(new Date());
				getPersonalNotesDAO().merge(personalNotesToUpdate);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		return personalNotesToUpdate;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActivationUser findActivationUser(UUID uuid) throws ApplicationThrowable {
		try {
			return getActivationUserDAO().find(uuid.toString());
		} catch (Throwable th) {
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
	public List<Country> findCountries(String description) throws ApplicationThrowable {
		try {
			return getCountryDAO().findByDescription(description);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Country findCountry(String countryCode) throws ApplicationThrowable {
		try {
			return getCountryDAO().find(countryCode);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PasswordChangeRequest findPasswordChangeRequest(UUID uuid) throws ApplicationThrowable {
		try {
			return getPasswordChangeRequestDAO().find(uuid.toString());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PasswordChangeRequest> findPasswordResetRequests() throws ApplicationThrowable {
		try {
			PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest();
			passwordChangeRequest.setReset(Boolean.FALSE);
			passwordChangeRequest.setMailSended(Boolean.FALSE);
			return getPasswordChangeRequestDAO().search(passwordChangeRequest);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	@Override
	public PersonalNotes findPersonalNotes(String account) throws ApplicationThrowable {
		try {
			return getPersonalNotesDAO().find(account);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUser(String account) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser(account);

			if (user != null) {
				return user;
			} else {
				throw new ApplicationThrowable(ApplicationError.USER_NOT_FOUND_ERROR);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUser(User userToFind) throws ApplicationThrowable {
		try {
			return getUserDAO().findUser(userToFind);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserInformation findUserInformation() throws ApplicationThrowable {
		try {
			return getUserInformationDAO().find(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserInformation findUserInformation(String account) throws ApplicationThrowable {
		try {
			return getUserInformationDAO().find(account);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findUsers(User user) throws ApplicationThrowable {
		try {
			return getUserDAO().findUsers(user);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findUsers(User user, Integer pageNumber, Integer pageSize) throws ApplicationThrowable {
		Page result;
		try {
			result = getUserDAO().findUsers(user,pageNumber, pageSize);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		return result;
	}

	/**
	 * This method will generate the userAccount. The format of the returned string is :
	 * - First Letter of field First Name;
	 * - field Last Name cleaned of not unicode letters
	 * 
	 * @param user The user to 
	 * @return A string rapresenting the account.
	 */
	private String generateAccount(User user) throws ApplicationThrowable {
		try {
			String initialLetterOfName = user.getFirstName().trim().substring(0, 1).toLowerCase();
			String surname = user.getLastName().trim().toLowerCase();
			String account = initialLetterOfName;
			
			if (!StringUtils.isAlpha(surname)) {
				surname = RegExUtils.trimNonAlphaChars(surname);
			}
			
			account+=surname;

			if (getUserDAO().findUser(account) == null)
				return account;
			
			for (int i=1; i<30; i++) {
				if (getUserDAO().findUser(account+i) == null)
					return account+i;
			}
			return null;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * This method will generate the user inital. The format of the returned string is :
	 * - First Letter of field First Name;
	 * - First or more Letters of field Last Name;
	 * 
	 * The generation code is based on calculate user initial making a previous
	 * search on LDAP tree (on field initial). If the initial are not present in LDAP,
	 * we return it, otherwise we increment the initial with another letter get from
	 * last name and so on until we find one initial that is not present on LDAP
	 *  tree.
	 * 
	 * @param user The user  
	 * @return A string rapresenting user inital.
	 */
	private String generateInitials(User user) throws ApplicationThrowable {
		try {
			// We extract name and surname from user object and we clean eventually wrong chars, it's necessary to calculate user initial 
			String name = user.getFirstName().trim().toUpperCase();;
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
				String initialChoiced = initialLetterOfName + surname.substring(0, i);
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
	 * @return the activationUserDAO
	 */
	public ActivationUserDAO getActivationUserDAO() {
		return activationUserDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HashMap<String, Long> getArchiveStatisticsFromLastLogin(UserInformation userInformation) throws ApplicationThrowable {
		HashMap<String, Long> archiveStatistics = new HashMap<String, Long>(4);
		try {
			archiveStatistics.put("Document", getDocumentDAO().countDocumentCreatedAfterDate(userInformation.getLastLoginDate()));
			archiveStatistics.put("People", getPeopleDAO().countPeopleCreatedAfterDate(userInformation.getLastLoginDate()));
			archiveStatistics.put("Place", getPlaceDAO().countPlaceCreatedAfterDate(userInformation.getLastLoginDate()));
			archiveStatistics.put("Volume", getVolumeDAO().countVolumeCreatedAfterDate(userInformation.getLastLoginDate()));
			archiveStatistics.put("Message", getUserMessageDAO().countMessageReceivedAfterDate(userInformation.getLastLoginDate()));
		} catch (Throwable th) {
			archiveStatistics.put("Document", new Long(0));
			archiveStatistics.put("People", new Long(0));
			archiveStatistics.put("Place", new Long(0));
			archiveStatistics.put("Volume", new Long(0));
			archiveStatistics.put("Message", new Long(0));
		}

		return archiveStatistics;
	}

	/**
	 * @return the countryDAO
	 */
	public CountryDAO getCountryDAO() {
		return countryDAO;
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
	public HashMap<String, List<?>> getMyHistoryReport(Integer numberOfHistory) throws ApplicationThrowable {
		HashMap<String, List<?>> historyReport = new HashMap<String, List<?>>(4);
		try {
			historyReport.put("Document", getUserHistoryDAO().findHistory(Category.DOCUMENT, 5));
			historyReport.put("People", getUserHistoryDAO().findHistory(Category.PEOPLE, 5));
			historyReport.put("Place", getUserHistoryDAO().findHistory(Category.PLACE, 5));
			historyReport.put("Volume", getUserHistoryDAO().findHistory(Category.VOLUME, 5));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		return historyReport;
	}


	/**
	 * @return the passwordChangeRequestDAO
	 */
	public PasswordChangeRequestDAO getPasswordChangeRequestDAO() {
		return passwordChangeRequestDAO;
	}
	
	/**
	 * @return the peopleDAO
	 */
	public PeopleDAO getPeopleDAO() {
		return peopleDAO;
	}

	/**
	 * @return the personalNotesDAO
	 */
	public PersonalNotesDAO getPersonalNotesDAO() {
		return personalNotesDAO;
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
	 * @return the userHistoryDAO
	 */
	public UserHistoryDAO getUserHistoryDAO() {
		return userHistoryDAO;
	}

	/**
	 * @return the userInformationDAO
	 */
	public UserInformationDAO getUserInformationDAO() {
		return userInformationDAO;
	}

	/**
	 * @return the userMessageDAO
	 */
	public UserMessageDAO getUserMessageDAO() {
		return userMessageDAO;
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
	public Boolean isAccountAvailable(String account) throws ApplicationThrowable {
		try {
			return (getUserDAO().findUser(account) == null);
		} catch (Throwable th) {
			ApplicationThrowable applicationException = new ApplicationThrowable(
					th);
			if (applicationException.getApplicationError().equals(ApplicationError.USER_NAME_NOT_FOUND_ERROR))
				return Boolean.TRUE;
			else
				throw applicationException;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void lockUser(User user) throws ApplicationThrowable {
		try {
			getUserDAO().removeAllUserRoles(user.getAccount());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer ratePassword(String password) {
		if ((password == null) || password.equals(""))
			return 0;
		
		if (password.length() <8)
			return 1;

		if (StringUtils.isAlpha(password))
			return 2;

		if (StringUtils.isAlphanumeric(password))
			return 3;

		if (StringUtils.isAsciiPrintable(password))
			return 4;

		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void registerUser(User user) throws ApplicationThrowable {
		try {
			// User account is generated by application
			user.setAccount(generateAccount(user));
			//User initial is generated by application
			user.setInitials(generateInitials(user));
			// Every user is always register as COMMUNITY_USER
			List<UserRole> userRoles = new ArrayList<UserRole>();
			userRoles.add(UserRole.COMMUNITY_USERS);
			user.setUserRoles(userRoles);

			getUserDAO().persist(user);

			UserInformation userInformation = new UserInformation(user.getAccount());
			userInformation.setRegistrationDate(new Date());
			Calendar expirationDate = Calendar.getInstance();
			expirationDate.add(Calendar.MONTH, NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("user.expiration.user.months")));
			userInformation.setExpirationDate(expirationDate.getTime());
			Calendar expirationPasswordDate = Calendar.getInstance();
			expirationPasswordDate.add(Calendar.MONTH, NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("user.expiration.password.months")));
			userInformation.setExpirationPasswordDate(expirationPasswordDate.getTime());
			userInformation.setBadLogin(0);
			userInformation.setActive(false);
			userInformation.setLocked(false);
			getUserInformationDAO().persist(userInformation);
			
			// We generate the request for sending activation mail 
			ActivationUser activationUser = new ActivationUser();
			activationUser.setUuid(UUID.randomUUID().toString());
			activationUser.setAccount(user.getAccount());
			activationUser.setActive(Boolean.FALSE);
			activationUser.setMailSended(Boolean.FALSE);
			activationUser.setRequestDate(new Date());
			activationUser.setRequestIPAddress(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr());
			getActivationUserDAO().persist(activationUser);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void restoreMyHistory() throws ApplicationThrowable {
		try {
			getUserHistoryDAO().restoreMyHistory();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void restoreMyHistory(Category category) throws ApplicationThrowable {
		try {
			getUserHistoryDAO().restoreMyHistory(category);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void restoreUserHistory(String username) throws ApplicationThrowable {
		try {
			getUserHistoryDAO().restoreUserHistory(username);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void restoreUserHistory(String username, Category category) throws ApplicationThrowable {
		try {
			getUserHistoryDAO().restoreUserHistory(username, category);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserHistory searchLastUserHistoryEntry() throws ApplicationThrowable {
		try{
			return getUserHistoryDAO().findLastEntry();
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchUserHistory(Category category, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getUserHistoryDAO().findHistory(category, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchUserHistory(Category category, PaginationFilter paginationFilter, Integer resultSize) throws ApplicationThrowable {
		try {
			return getUserHistoryDAO().findHistory(category, paginationFilter, resultSize);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchUserHistory(PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getUserHistoryDAO().findHistory(paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @param activationUserDAO the activationUserDAO to set
	 */
	public void setActivationUserDAO(ActivationUserDAO activationUserDAO) {
		this.activationUserDAO = activationUserDAO;
	}

	/**
	 * 
	 * @param countryDAO the countryDAO to set
	 */
	public void setCountryDAO(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}

	/**
	 * @param documentDAO the documentDAO to set
	 */
	public void setDocumentDAO(DocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
	}

	/**
	 * 
	 * @param passwordChangeRequestDAO the passwordChangeRequestDAO to set
	 */
	public void setPasswordChangeRequestDAO(PasswordChangeRequestDAO passwordChangeRequestDAO) {
		this.passwordChangeRequestDAO = passwordChangeRequestDAO;
	}

	/**
	 * @param peopleDAO the peopleDAO to set
	 */
	public void setPeopleDAO(PeopleDAO peopleDAO) {
		this.peopleDAO = peopleDAO;
	}

	/**
	 * @param personalNotesDAO the personalNotesDAO to set
	 */
	public void setPersonalNotesDAO(PersonalNotesDAO personalNotesDAO) {
		this.personalNotesDAO = personalNotesDAO;
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
	 * @param userHistoryDAO the userHistoryDAO to set
	 */
	public void setUserHistoryDAO(UserHistoryDAO userHistoryDAO) {
		this.userHistoryDAO = userHistoryDAO;
	}

	/**
	 * @param userInformationDAO the userInformationDAO to set
	 */
	public void setUserInformationDAO(UserInformationDAO userInformationDAO) {
		this.userInformationDAO = userInformationDAO;
	}

	/**
	 * @param userMessageDAO the userMessageDAO to set
	 */
	public void setUserMessageDAO(UserMessageDAO userMessageDAO) {
		this.userMessageDAO = userMessageDAO;
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
	public void updateUser(User user) throws ApplicationThrowable {
		try {
			getUserDAO().merge(user);

//			getUserDAO().removeAllUserRoles(user.getAccount());
//			getUserDAO().persistUserRoles(user.getAccount(), userToUpdate.getUserRoles());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String updateUserPassword(User user) throws ApplicationThrowable {
		try {
			user.setPassword(RandomStringUtils.random(12, true, true));
			getUserDAO().merge(user);
			return user.getPassword();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateUserPassword(User user, String newPassword) throws ApplicationThrowable {
		try {
			user.setPassword(newPassword);
			getUserDAO().merge(user);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateUserPassword(UUID uuid, String password) throws ApplicationThrowable {
		try {
			PasswordChangeRequest passwordChangeRequest = getPasswordChangeRequestDAO().find(uuid.toString());

			User user = getUserDAO().findUser(passwordChangeRequest.getAccount());
			user.setPassword(password);
			getUserDAO().merge(user);
			passwordChangeRequest.setReset(Boolean.TRUE);
			passwordChangeRequest.setResetDate(new Date());
			passwordChangeRequest.setResetIPAddress(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr());
			getPasswordChangeRequestDAO().merge(passwordChangeRequest);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateUserPhoto(User user, BufferedImage bufferedImage) throws ApplicationThrowable {
		try {
			user.setPhoto(bufferedImage);
			getUserDAO().merge(user);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
}
