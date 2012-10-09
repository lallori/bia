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
package org.medici.bia.service.user;

import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.search.SearchFromLast;
import org.medici.bia.common.search.SearchFromLast.FromLast;
import org.medici.bia.common.search.SearchFromLast.SearchPerimeter;
import org.medici.bia.common.util.ApplicationError;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.RegExUtils;
import org.medici.bia.dao.activationuser.ActivationUserDAO;
import org.medici.bia.dao.country.CountryDAO;
import org.medici.bia.dao.document.DocumentDAO;
import org.medici.bia.dao.passwordchangerequest.PasswordChangeRequestDAO;
import org.medici.bia.dao.people.PeopleDAO;
import org.medici.bia.dao.place.PlaceDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userauthority.UserAuthorityDAO;
import org.medici.bia.dao.userhistory.UserHistoryDAO;
import org.medici.bia.dao.usermarkedlistelement.UserMarkedListElementDAO;
import org.medici.bia.dao.usermessage.UserMessageDAO;
import org.medici.bia.dao.userpersonalnotes.UserPersonalNotesDAO;
import org.medici.bia.dao.userrole.UserRoleDAO;
import org.medici.bia.dao.volume.VolumeDAO;
import org.medici.bia.domain.ActivationUser;
import org.medici.bia.domain.Country;
import org.medici.bia.domain.PasswordChangeRequest;
import org.medici.bia.domain.UserPersonalNotes;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserRole;
import org.medici.bia.domain.UserAuthority.Authority;
import org.medici.bia.domain.UserHistory.Category;
import org.medici.bia.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
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
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;
	@Autowired
	private PeopleDAO peopleDAO;
	@Autowired
	private PlaceDAO placeDAO;
	@Autowired
	private UserAuthorityDAO userAuthorityDAO;

	@Autowired(required=false)
	@Qualifier(value="userDAOJpaImpl")
	private UserDAO userDAO;
	
	@Autowired
	private UserHistoryDAO userHistoryDAO;

	@Autowired
	private UserMarkedListElementDAO userMarkedListElementDAO;
	
	@Autowired
	private UserMessageDAO userMessageDAO;

	@Autowired
	private UserPersonalNotesDAO userPersonalNotesDAO;

	@Autowired
	private UserRoleDAO userRoleDAO;

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
			User user = activationUser.getUser();
			user.setActive(Boolean.TRUE);
			user.setActivationDate(new Date());
			getUserDAO().merge(user);
			
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
			User userToSearch = getUserDAO().findUser(user);

			ActivationUser activationUser = new ActivationUser();
			activationUser.setUuid(UUID.randomUUID().toString());
			activationUser.setUser(userToSearch);
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
			User userToSearch = getUserDAO().findUser(user);
			PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest();
			passwordChangeRequest.setUuid(UUID.randomUUID().toString());
			passwordChangeRequest.setUser(userToSearch);
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
	@Override
	public Boolean checkUserPassword(String inputPassword) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
			
			if (user != null) {
				if (getPasswordEncoder().isPasswordValid(user.getPassword(), inputPassword, null)) {
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteMyHistory() throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().deleteMyHistory(user);
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
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().deleteMyHistory(user, category);
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
			User user = getUserDAO().findUser(username);

			getUserHistoryDAO().deleteUserHistory(user);
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
			User user = getUserDAO().findUser(username);

			getUserHistoryDAO().deleteUserHistory(user, category);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserPersonalNotes editPersonalNotes(String account, UserPersonalNotes personalNotes) throws ApplicationThrowable {
		UserPersonalNotes userPersonalNotesToUpdate = null;
		
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
			userPersonalNotesToUpdate = getUserPersonalNotesDAO().getPersonalNotes(user);
			
			if (userPersonalNotesToUpdate == null) {
				userPersonalNotesToUpdate = new UserPersonalNotes(personalNotes.getPersonalNotes());
				userPersonalNotesToUpdate.setIdPersonalNotes(null);
				userPersonalNotesToUpdate.setUser(user);
				userPersonalNotesToUpdate.setDateCreated(new Date());
				userPersonalNotesToUpdate.setLastUpdate(new Date());
				getUserPersonalNotesDAO().persist(userPersonalNotesToUpdate);
			} else {
				userPersonalNotesToUpdate.setPersonalNotes(personalNotes.getPersonalNotes());
				userPersonalNotesToUpdate.setLastUpdate(new Date());
				getUserPersonalNotesDAO().merge(userPersonalNotesToUpdate);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		return userPersonalNotesToUpdate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserPersonalNotes editPersonalNotes(UserPersonalNotes userPersonalNotes) throws ApplicationThrowable {
		UserPersonalNotes userPersonalNotesToUpdate = null;
		
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
			userPersonalNotesToUpdate = getUserPersonalNotesDAO().getPersonalNotes(user);
			
			if (userPersonalNotesToUpdate == null) {
				userPersonalNotesToUpdate = new UserPersonalNotes(userPersonalNotes.getPersonalNotes());
				userPersonalNotesToUpdate.setIdPersonalNotes(null);
				userPersonalNotesToUpdate.setUser(user);
				userPersonalNotesToUpdate.setDateCreated(new Date());
				userPersonalNotesToUpdate.setLastUpdate(new Date());
				getUserPersonalNotesDAO().persist(userPersonalNotesToUpdate);
			} else {
				userPersonalNotesToUpdate.setPersonalNotes(userPersonalNotes.getPersonalNotes());
				userPersonalNotesToUpdate.setLastUpdate(new Date());
				getUserPersonalNotesDAO().merge(userPersonalNotesToUpdate);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		return userPersonalNotesToUpdate;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserPersonalNotes findPersonalNotes() throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			return getUserPersonalNotesDAO().getPersonalNotes(user);
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
	public UserPersonalNotes findUserPersonalNotes() throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			return getUserPersonalNotesDAO().getPersonalNotes(user);
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
	 * {@inheritDoc}
	 */
	@Override
	public String generateUserPassword(User user) throws ApplicationThrowable {
		try {
			User usetToUpdate = getUserDAO().findUser(user.getAccount());
			String password = RandomStringUtils.random(12, true, true);
			usetToUpdate.setPassword(getPasswordEncoder().encodePassword(password, null));
			getUserDAO().merge(usetToUpdate);
			return user.getPassword();
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
	public HashMap<SearchPerimeter, Long> getArchiveStatisticsFromLast(SearchFromLast searchFromLast) throws ApplicationThrowable {
		HashMap<SearchPerimeter, Long> archiveStatistics = new HashMap<SearchPerimeter, Long>(4);
		try {
			if (searchFromLast.getFromLast().equals(FromLast.LOGON)) {
				User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
				Date dateFrom = user.getLastLoginDate();
				
				archiveStatistics.put(SearchPerimeter.DOCUMENT, getDocumentDAO().countDocumentCreatedAfterDate(dateFrom));
				archiveStatistics.put(SearchPerimeter.PEOPLE, getPeopleDAO().countPeopleCreatedAfterDate(dateFrom ));
				archiveStatistics.put(SearchPerimeter.PLACE, getPlaceDAO().countPlaceCreatedAfterDate(dateFrom ));
				archiveStatistics.put(SearchPerimeter.VOLUME, getVolumeDAO().countVolumeCreatedAfterDate(dateFrom));
			} else if (searchFromLast.getFromLast().equals(FromLast.LASTWEEK)) {
				Date dateFrom = DateUtils.getFirstDayOfCurrentWeek();
				
				archiveStatistics.put(SearchPerimeter.DOCUMENT, getDocumentDAO().countDocumentCreatedAfterDate(dateFrom));
				archiveStatistics.put(SearchPerimeter.PEOPLE, getPeopleDAO().countPeopleCreatedAfterDate(dateFrom ));
				archiveStatistics.put(SearchPerimeter.PLACE, getPlaceDAO().countPlaceCreatedAfterDate(dateFrom ));
				archiveStatistics.put(SearchPerimeter.VOLUME, getVolumeDAO().countVolumeCreatedAfterDate(dateFrom));
			} else if (searchFromLast.getFromLast().equals(FromLast.LASTMONTH)) {
				Date dateFrom = DateUtils.getFirstDayOfCurrentMonth();

				archiveStatistics.put(SearchPerimeter.DOCUMENT, getDocumentDAO().countDocumentCreatedAfterDate(dateFrom));
				archiveStatistics.put(SearchPerimeter.PEOPLE, getPeopleDAO().countPeopleCreatedAfterDate(dateFrom ));
				archiveStatistics.put(SearchPerimeter.PLACE, getPlaceDAO().countPlaceCreatedAfterDate(dateFrom ));
				archiveStatistics.put(SearchPerimeter.VOLUME, getVolumeDAO().countVolumeCreatedAfterDate(dateFrom));
			}
		} catch (Throwable th) {
			archiveStatistics.put(SearchPerimeter.DOCUMENT, new Long(0));
			archiveStatistics.put(SearchPerimeter.PEOPLE, new Long(0));
			archiveStatistics.put(SearchPerimeter.PLACE, new Long(0));
			archiveStatistics.put(SearchPerimeter.VOLUME, new Long(0));
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
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			historyReport.put("Document", getUserHistoryDAO().findHistory(user, Category.DOCUMENT, 5));
			historyReport.put("People", getUserHistoryDAO().findHistory(user, Category.PEOPLE, 5));
			historyReport.put("Place", getUserHistoryDAO().findHistory(user, Category.PLACE, 5));
			historyReport.put("Volume", getUserHistoryDAO().findHistory(user, Category.VOLUME, 5));
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

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
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
	 * @return the userHistoryDAO
	 */
	public UserHistoryDAO getUserHistoryDAO() {
		return userHistoryDAO;
	}

	/**
	 * @return the userMarkedListElementDAO
	 */
	public UserMarkedListElementDAO getUserMarkedListElementDAO() {
		return userMarkedListElementDAO;
	}

	/**
	 * @return the userMessageDAO
	 */
	public UserMessageDAO getUserMessageDAO() {
		return userMessageDAO;
	}

	/**
	 * @return the personalNotesDAO
	 */
	public UserPersonalNotesDAO getUserPersonalNotesDAO() {
		return userPersonalNotesDAO;
	}

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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void lockUser(User user) throws ApplicationThrowable {
		try {
			User userToLock = getUserDAO().findUser(user.getAccount());
			userToLock.setLocked(Boolean.TRUE);

			getUserDAO().removeAllUserRoles(user.getAccount());
			
			getUserDAO().merge(userToLock);
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
			user.setPassword(getPasswordEncoder().encodePassword(user.getPassword(), null));
			//User initial is generated by application
			user.setInitials(generateInitials(user));

			user.setRegistrationDate(new Date());
			Calendar expirationDate = Calendar.getInstance();
			expirationDate.add(Calendar.MONTH, NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("user.expiration.user.months")));
			user.setExpirationDate(expirationDate.getTime());
			Calendar expirationPasswordDate = Calendar.getInstance();
			expirationPasswordDate.add(Calendar.MONTH, NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("user.expiration.password.months")));
			user.setExpirationPasswordDate(expirationPasswordDate.getTime());
			user.setBadLogin(0);
			user.setActive(false);
			user.setApproved(false);
			user.setLocked(false);
			user.setForumNumberOfPost(new Long(0));

			getUserDAO().persist(user);

			// Every user is always register as COMMUNITY_USER
			UserAuthority userAuthority = getUserAuthorityDAO().find(Authority.COMMUNITY_USERS);
			
			UserRole userRole = new UserRole(user, userAuthority);
			getUserRoleDAO().persist(userRole);

			if (getUserPersonalNotesDAO().getPersonalNotes(user) == null) {
				UserPersonalNotes userPersonalNotes = new UserPersonalNotes();
				userPersonalNotes.setUser(user);
				userPersonalNotes.setPersonalNotes("");
			}

			// We generate the request for sending activation mail 
			ActivationUser activationUser = new ActivationUser();
			activationUser.setUuid(UUID.randomUUID().toString());
			activationUser.setUser(user);
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void restoreMyHistory() throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().restoreMyHistory(user);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void restoreMyHistory(Category category) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().restoreMyHistory(user, category);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void restoreUserHistory(String username) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser(username);

			getUserHistoryDAO().restoreUserHistory(user);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void restoreUserHistory(String username, Category category) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser(username);

			getUserHistoryDAO().restoreUserHistory(user, category);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserHistory searchLastUserHistoryBaseEntry() throws ApplicationThrowable {
		try{
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			return getUserHistoryDAO().findLastEntryBase(user);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserHistory searchLastUserHistoryEntry() throws ApplicationThrowable {
		try{
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			return getUserHistoryDAO().findLastEntry(user);
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
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			return getUserHistoryDAO().findHistory(user, category, paginationFilter);
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
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			return getUserHistoryDAO().findHistory(user, category, paginationFilter, resultSize);
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
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			return getUserHistoryDAO().findHistory(user, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchUserMarkedList(PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			return getUserMarkedListElementDAO().findMarkedList(user, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> searchUsers(String query) throws ApplicationThrowable {
		try{
			return getUserDAO().findUsers(query);
		}catch(Throwable th){
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
	 * 
	 * @param passwordEncoder
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
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
	 * @param userHistoryDAO the userHistoryDAO to set
	 */
	public void setUserHistoryDAO(UserHistoryDAO userHistoryDAO) {
		this.userHistoryDAO = userHistoryDAO;
	}

	/**
	 * @param userMarkedListElementDAO the userMarkedListElementDAO to set
	 */
	public void setUserMarkedListElementDAO(
			UserMarkedListElementDAO userMarkedListElementDAO) {
		this.userMarkedListElementDAO = userMarkedListElementDAO;
	}

	/**
	 * @param userMessageDAO the userMessageDAO to set
	 */
	public void setUserMessageDAO(UserMessageDAO userMessageDAO) {
		this.userMessageDAO = userMessageDAO;
	}

	/**
	 * @param userPersonalNotesDAO the userPersonalNotesDAO to set
	 */
	public void setUserPersonalNotesDAO(UserPersonalNotesDAO userPersonalNotesDAO) {
		this.userPersonalNotesDAO = userPersonalNotesDAO;
	}

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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void updateUser(User user) throws ApplicationThrowable {
		try {
			User userToUpdate = getUserDAO().findUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

			userToUpdate.setMail(user.getMail());
			userToUpdate.setAddress(user.getAddress());
			userToUpdate.setCountry(user.getCountry());
			userToUpdate.setInterests(user.getInterests());
			userToUpdate.setOrganization(user.getOrganization());
			userToUpdate.setTitle(user.getTitle());
			//MD: I don't know if the location is the city
			userToUpdate.setCity(user.getCity());
			
			if (user.getPassword() != null) {
				userToUpdate.setPassword(getPasswordEncoder().encodePassword(user.getPassword(), null));
			}
			
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void updateUserPassword(String newPassword) throws ApplicationThrowable {
		try {
			User userToUpdate = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
			userToUpdate.setPassword(getPasswordEncoder().encodePassword(newPassword, null));
			getUserDAO().merge(userToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void updateUserPassword(UUID uuid, String password) throws ApplicationThrowable {
		try {
			PasswordChangeRequest passwordChangeRequest = getPasswordChangeRequestDAO().find(uuid.toString());

			User user = passwordChangeRequest.getUser();
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
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
