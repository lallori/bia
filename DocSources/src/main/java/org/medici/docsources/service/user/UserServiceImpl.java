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
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.medici.docsources.common.ajax.Page;
import org.medici.docsources.common.util.ApplicationError;
import org.medici.docsources.dao.country.CountryDAO;
import org.medici.docsources.dao.passwordchangerequest.PasswordChangeRequestDAO;
import org.medici.docsources.dao.user.UserDAO;
import org.medici.docsources.domain.Country;
import org.medici.docsources.domain.PasswordChangeRequest;
import org.medici.docsources.domain.User;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private CountryDAO countryDao;
	@Autowired
	private PasswordChangeRequestDAO passwordChangeRequestDAO;
	@Autowired
	private UserDAO userDAO; 

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
	 * @param user
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Country> findCountries(String description)
	throws ApplicationThrowable {
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
	 * @param account
	 * @return
	 * @throws ApplicationThrowable
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
	 * @param user
	 * @return
	 * @throws ApplicationThrowable
	 */
	public User findUser(User userToFind) throws ApplicationThrowable {
		User user = new User();
		try {
			user = getUserDAO().findUser(userToFind);

			if (user != null) {
				return user;
			} else {
				throw new ApplicationThrowable(
						ApplicationError.USER_NOT_FOUND_ERROR);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 * @param user
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<User> findUsers(User user) throws ApplicationThrowable {
		try {
			return getUserDAO().findUsers(user);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

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
	 * @param password
	 * @return Integer 0, is not a valid password; 1, password is too short;
	 *         2, password contains only alphabetic chars, not number or symbol;
	 *         3, password contains alphabetic chars and letters;
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
	 * @param
	 */
	public void registerNewUser(User user) throws ApplicationThrowable {
		try {
			getUserDAO().persist(user);
			getUserDAO().persistUserRoles(user.getAccount(),
					user.getUserRoles());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
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
			getPasswordChangeRequestDAO().remove(passwordChangeRequest);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
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
