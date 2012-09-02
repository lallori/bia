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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.dao.accesslogstatistics.AccessLogStatisticsDAO;
import org.medici.bia.dao.applicationproperty.ApplicationPropertyDAO;
import org.medici.bia.dao.month.MonthDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userauthority.UserAuthorityDAO;
import org.medici.bia.dao.userrole.UserRoleDAO;
import org.medici.bia.domain.ApplicationProperty;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserRole;
import org.medici.bia.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.PasswordEncoder;
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
	private AccessLogStatisticsDAO accessLogStatisticsDAO;

	@Autowired
	private ApplicationPropertyDAO applicationPropertyDAO;
	
	@Autowired
	private MonthDAO monthDAO;
	
	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;
	
	@Autowired(required = false)
	@Qualifier("userDAOJpaImpl")
	private UserDAO userDAO;

	@Autowired
	private UserAuthorityDAO userAuthorityDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void createAccessLogDailyStatistics() throws ApplicationThrowable {
		Date dateSelected = new DateTime().minusDays(1).toDate();

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
	public void editUser(User user) throws ApplicationThrowable {
		try{
			User userToUpdate = getUserDAO().findUser(user.getAccount()); 
			//userToUpdate.setAddress(user.getAddress());
			//userToUpdate.setCity(user.getCity());
			//userToUpdate.setCountry(user.getCountry());

			if (!user.getPassword().equals(userToUpdate.getPassword())){
				userToUpdate.setPassword(getPasswordEncoder().encodePassword(user.getPassword(), null));
				userToUpdate.setLastPasswordChangeDate(new Date());
			}

			userToUpdate.setFirstName(user.getFirstName());
			userToUpdate.setInitials(user.getInitials());
			userToUpdate.setInterests(user.getInterests());
			userToUpdate.setLastName(user.getLastName());
			
			userToUpdate.setExpirationDate(user.getExpirationDate());
			userToUpdate.setExpirationPasswordDate(user.getExpirationPasswordDate());
			userToUpdate.setActive(user.getActive());
			if (!userToUpdate.getApproved().equals(user.getApproved())) {
				userToUpdate.setApproved(user.getApproved());
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
		} catch(Throwable th){
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
		try{
			return getUserDAO().findUsers(user, paginationFilter);
			
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}	
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
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * @param accessLogStatisticsDAO the accessLogStatisticsDAO to set
	 */
	public void setAccessLogStatisticsDAO(AccessLogStatisticsDAO accessLogStatisticsDAO) {
		this.accessLogStatisticsDAO = accessLogStatisticsDAO;
	}

	/**
	 * @param applicationPropertyDAO
	 *            the applicationPropertyDAO to set
	 */
	public void setApplicationPropertyDAO(ApplicationPropertyDAO applicationPropertyDAO) {
		this.applicationPropertyDAO = applicationPropertyDAO;
	}

	/**
	 * @param monthDAO the monthDAO to set
	 */
	public void setMonthDAO(MonthDAO monthDAO) {
		this.monthDAO = monthDAO;
	}

	/**
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void updateApplicationProperties(HashMap<String, String> hashMap) throws ApplicationThrowable {
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
		// TODO Auto-generated method stub

	}

	/**
	 * @return the userAuthorityDAO
	 */
	public UserAuthorityDAO getUserAuthorityDAO() {
		return userAuthorityDAO;
	}

	/**
	 * @param userAuthorityDAO the userAuthorityDAO to set
	 */
	public void setUserAuthorityDAO(UserAuthorityDAO userAuthorityDAO) {
		this.userAuthorityDAO = userAuthorityDAO;
	}

	public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	public UserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
}