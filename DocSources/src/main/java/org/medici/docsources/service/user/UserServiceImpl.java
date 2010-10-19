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
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.medici.docsources.common.ajax.Page;
import org.medici.docsources.common.util.ApplicationError;
import org.medici.docsources.common.util.RegExUtils;
import org.medici.docsources.dao.activationuser.ActivationUserDAO;
import org.medici.docsources.dao.country.CountryDAO;
import org.medici.docsources.dao.passwordchangerequest.PasswordChangeRequestDAO;
import org.medici.docsources.dao.user.UserDAO;
import org.medici.docsources.domain.ActivationUser;
import org.medici.docsources.domain.Country;
import org.medici.docsources.domain.PasswordChangeRequest;
import org.medici.docsources.domain.User;
import org.medici.docsources.domain.User.UserRole;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class UserServiceImpl implements UserService {
	@Autowired
	private ActivationUserDAO activationUserDAO;
	@Autowired
	private CountryDAO countryDao;
	@Autowired
	private PasswordChangeRequestDAO passwordChangeRequestDAO; 
	@Autowired
	private UserDAO userDAO; 

	/**
	 * 
	 */
	public void activateUser(UUID uuid) throws ApplicationThrowable {
		try {
			//Extract activationUser entity to obtains account linked the activation request
			ActivationUser activationUser = getActivationUserDAO().find(uuid.toString());
			
			//Search user by account and update active flag
			User user = getUserDAO().findUser(activationUser.getAccount());
			user.setActive(Boolean.TRUE);
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
	 * 
	 */
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
	 * 
	 */
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
	 * 
	 */
	public void deleteUser(User user) throws ApplicationThrowable {
		try {
			getUserDAO().remove(user);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * 
	 */
	public ActivationUser findActivationUser(UUID uuid) throws ApplicationThrowable {
		try {
			return getActivationUserDAO().find(uuid.toString());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 */
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
	 */
	public List<Country> findCountries(String description) throws ApplicationThrowable {
		try {
			return getCountryDao().findByDescription(description);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 */
	public Country findCountry(String countryCode) throws ApplicationThrowable {
		try {
			return getCountryDao().find(countryCode);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 */
	public PasswordChangeRequest findPasswordChangeRequest(UUID uuid) throws ApplicationThrowable {
		try {
			return getPasswordChangeRequestDAO().find(uuid.toString());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 */
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
	 * 
	 */
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
	 * 
	 */
	public User findUser(User userToFind) throws ApplicationThrowable {
		try {
			return getUserDAO().findUser(userToFind);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 */
	public List<User> findUsers(User user) throws ApplicationThrowable {
		try {
			return getUserDAO().findUsers(user);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 */
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
	 * @return the activationUserDAO
	 */
	public ActivationUserDAO getActivationUserDAO() {
		return activationUserDAO;
	}

	/**
	 * @return the countryDao
	 */
	public CountryDAO getCountryDao() {
		return countryDao;
	}
	
	/**
	 * @return the passwordChangeRequestDAO
	 */
	public PasswordChangeRequestDAO getPasswordChangeRequestDAO() {
		return passwordChangeRequestDAO;
	}

	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * @param account
	 */
	public Boolean isAccountAvailable(String account)
	throws ApplicationThrowable {
		try {
			return (getUserDAO().findUser(account) == null);
		} catch (Throwable th) {
			ApplicationThrowable applicationException = new ApplicationThrowable(
					th);
			if (applicationException.getApplicationError().equals(
					ApplicationError.LDAP_NAME_NOT_FOUND_ERROR))
				return Boolean.TRUE;
			else
				throw applicationException;
		}
	}

	/**
	 * 
	 */
	public void lockUser(User user) throws ApplicationThrowable {
		try {
			getUserDAO().removeAllUserRoles(user.getAccount());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 */
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
	 * 
	 */
	public void registerUser(User user) throws ApplicationThrowable {
		try {
			user.setAccount(generateAccount(user));
			user.setActive(false);
			user.setLocked(false);
			user.setRegistrationDate(new Date());
			Calendar expirationDate = Calendar.getInstance();
			expirationDate.add(Calendar.YEAR, 1);
			user.setExpirationDate(expirationDate.getTime());
			Calendar expirationPasswordDate = Calendar.getInstance();
			expirationPasswordDate.add(Calendar.YEAR, 2);
			user.setExpirationPasswordDate(expirationPasswordDate.getTime());
			user.setInvalidAccess(0);
			user.setInvalidAccessMax(5);
			List<UserRole> userRoles = new ArrayList<UserRole>();
			userRoles.add(UserRole.COMMUNITY_USERS);
			user.setUserRoles(userRoles);

			getUserDAO().persist(user);
			
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
	 * @param activationUserDAO the activationUserDAO to set
	 */
	public void setActivationUserDAO(ActivationUserDAO activationUserDAO) {
		this.activationUserDAO = activationUserDAO;
	}

	/**
	 * 
	 * @param countryDao the countryDao to set
	 */
	public void setCountryDao(CountryDAO countryDao) {
		this.countryDao = countryDao;
	}

	/**
	 * 
	 * @param passwordChangeRequestDAO the passwordChangeRequestDAO to set
	 */
	public void setPasswordChangeRequestDAO(PasswordChangeRequestDAO passwordChangeRequestDAO) {
		this.passwordChangeRequestDAO = passwordChangeRequestDAO;
	}

	/**
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * 
	 * @param user
	 */
	public void updateUser(User user) throws ApplicationThrowable {
		try {
			getUserDAO().merge(user);

			getUserDAO().removeAllUserRoles(user.getAccount());
			getUserDAO().persistUserRoles(user.getAccount(), user.getUserRoles());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 * @param user
	 */
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
	 * 
	 * @param user
	 * @param newPassword
	 */
	public void updateUserPassword(User user, String newPassword) throws ApplicationThrowable {
		try {
			user.setPassword(newPassword);
			getUserDAO().merge(user);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 * @param uuid
	 * @return
	 * @throws ApplicationThrowable
	 */
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
	 * 
	 * @param user
	 * @param bufferedImage
	 */
	public void updateUserPhoto(User user, BufferedImage bufferedImage) throws ApplicationThrowable {
		try {
			user.setPhoto(bufferedImage);
			getUserDAO().merge(user);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
}
