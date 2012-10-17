/*
 * UserService.java
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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.SearchFromLast;
import org.medici.bia.common.search.SearchFromLast.SearchPerimeter;
import org.medici.bia.domain.ActivationUser;
import org.medici.bia.domain.Country;
import org.medici.bia.domain.PasswordChangeRequest;
import org.medici.bia.domain.UserPersonalNotes;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserHistory.Category;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * This interface is designed to work on {@link org.medici.bia.domain.User} 
 * User object.<br>
 * It defines every business methods needed to work on authentication and
 * administrator module.<br>
 * With this service, you can :<br>
 * - create new user<br> 
 * - search an user by a property field, or unique identify field (acocunt ndr)<br>
 * - obtaining user's roles<br>
 * - request user activation<br>
 * - lock a user<br>
 * ...<br>
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface UserService {

	/**
	 * This method activates a new user.<br>
	 * Using the unique identify of activation request (uuid ndr), this method
	 * will search the user on persistence layer and provides to update
	 * all user's state informations (lock flag, activate flag). 
	 * 
	 * @param uuid  The {@link java.util.UUID} object which is the unique identify of activation request.
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public void activateUser(UUID uuid) throws ApplicationThrowable;

	/**
	 * This method add an "Activation User Request" in persistent layer.<br>
	 * The request is created the {@link org.medici.bia.domain.User} user, and
	 * Internet Address of client requester.
	 * 
	 * @param user The {@link org.medici.bia.domain.User} object to be activated.
	 * @param remoteAddress The {@link java.util.String} Internet Address of client requester.
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 * @see org.medici.bia.domain.ActivationUser
	 */
	public void addActivationUserRequest(User user, String remoteAddress) throws ApplicationThrowable;

	/**
	 * 
	 * @param user The {@link org.medici.bia.domain.User} object changes password.
	 * @param remoteAddress The {@link java.util.String} Internet Address of requester.
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public void addPasswordChangeRequest(User user, String remoteAddress) throws ApplicationThrowable;
	
	/**
	 * Check if inputPassword is equals to user password
	 * @param user
	 * @param newPassword
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public Boolean checkUserPassword(String inputPassword) throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void deleteMyHistory() throws ApplicationThrowable;

	/**
	 * 
	 * @param category
	 * @throws ApplicationThrowable
	 */
	public void deleteMyHistory(Category category) throws ApplicationThrowable;
	
	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void deletePersonalNotes() throws ApplicationThrowable;

	/**
	 * Removes specified user from persistent layer.
	 * 
	 * @param user the {@link org.medici.bia.domain.User} object to be removed.
	 * @throws org.medici.bia.exception.ApplicationThrowable application throwable throwed if an error is occured.
	 */
	public void deleteUser(User user) throws ApplicationThrowable;

	/**
	 * This is an admin function. If you want to delete your history log please
	 * see deleteMyHistory methods.
	 * 
	 * @param username
	 * @throws ApplicationThrowable
	 */
	public void deleteUserHistory(String username) throws ApplicationThrowable;

	/**
	 * This is an admin function. If you want to delete your history log please
	 * see deleteMyHistory methods.
	 * 
	 * @param username
	 * @param category
	 * @throws ApplicationThrowable
	 */
	public void deleteUserHistory(String username, Category category) throws ApplicationThrowable;

	/**
	 * 
	 * @param account
	 * @param personalNotes
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserPersonalNotes editPersonalNotes(String account, UserPersonalNotes personalNotes) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userPersonalNotes
	 * @throws ApplicationThrowable
	 */
	public UserPersonalNotes editPersonalNotes(UserPersonalNotes userPersonalNotes) throws ApplicationThrowable;

	/**
	 * This method will find an activation user entity.
	 * 
	 * @param uuid the unique identify of activation process
	 * @return the {@link org.medici.bia.domain.ActivationUser} linked to the primary key. 
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public ActivationUser findActivationUser(UUID uuid) throws ApplicationThrowable;

	/**
	 * This method searchs for user to be activated. The condition is composed
	 * of "active" flag equals false and "mail sended" flag equals false.
	 *
	 * @return The {@link java.util.List} of users that needs to be activated 
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public List<ActivationUser> findActivationUsers() throws ApplicationThrowable;

	/**
	 * This method returns a list of country object which match description
	 * field with inpurt parameter description. 
	 * 
	 * @param description the {@link java.util.String} country's description. 
	 * @return
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public List<Country> findCountries(String description) throws ApplicationThrowable;

	/**
	 * This method finds country with the specified countryCode given in input
	 * @param countryCode The {@link java.util.String} country's code. 
	 * @return The {@link org.medici.bia.domain.Country} country object or null if it's not available 
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public Country findCountry(String countryCode) throws ApplicationThrowable;

	/**
	 * This method finds a "password change request user information to permit data recovery by mail.
	 * 
	 * @param uuid {@link java.lang.String} unique identify of password change request
	 * @return {@link org.medici.bia.domain.PasswordChangeRequest} istance of request or null
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured. 
	 */
	public PasswordChangeRequest findPasswordChangeRequest(UUID uuid) throws ApplicationThrowable;

	/**
	 * This method searchs for password reset requests.<br>
	 * Condition search is composed of "active flag" equals false and "mail sended flag" equals false.
	 * 
	 * @return {@link java.util.List} PasswordChangeRequest founds.
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public List<PasswordChangeRequest> findPasswordResetRequests() throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserPersonalNotes findPersonalNotes() throws ApplicationThrowable;

	/**
	 * Given in input user account, this method returns the user object.
	 * 
	 * @param account the {@link java.lang.String} user account that we are searching 
	 * @return
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public User findUser(String account) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public User findUser(User user) throws ApplicationThrowable;


	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserPersonalNotes findUserPersonalNotes() throws ApplicationThrowable;

	/**
	 * Given in input an user containing search fields conditions, this method
	 * will returns {@link java.util.List} of User object that match with the search
	 *   
	 * @param user {@link org.medici.bia.domain.User} containing search fields
	 * @return {@link java.util.List} User search result.
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public List<User> findUsers(User user) throws ApplicationThrowable;
	
	/**
	 * This method will make a search paginated.
	 * 
	 * @param user {@link org.medici.bia.domain.User} containing search fields
	 * @param pageNumber {@link java.lang.Integer} Number of page result that we want to obtain
	 * @param pageSize {@link java.lang.Integer} Number of records that compose a single page result
	 * @return {@link org.medici.bia.common.pagination.Page} containing the result page.
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 * {@inheritDoc}
	 */
	public Page findUsers(User user, Integer pageNumber, Integer pageSize) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public String generateUserPassword(User user) throws ApplicationThrowable;

	/**
	 * 
	 * @param searchFromLast
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HashMap<SearchPerimeter, Long> getArchiveStatisticsFromLast(SearchFromLast searchFromLast) throws ApplicationThrowable;

	/**
	 * 
	 * @param numberOfHistory
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HashMap<String, List<?>> getMyHistoryReport(Integer numberOfHistory) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param account
	 * @return
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public Boolean isAccountAvailable(String account) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public void lockUser(User user) throws ApplicationThrowable;

	/**
	 * This method implements business logic for password rating.
	 * 
	 * @param password The password object to be rated.
	 * @return An integer rappresenting the rate. Values are between 1 and 4.
	 *          - 0, is not a valid password; 
	 *          - 1, password is too short;
	 *          - 2, password contains only alphabetic chars, not number or symbol;
	 *          - 3, password contains alphabetic chars and letters;
	 */
	public Integer ratePassword(String password);

	/**
	 * This method implements business logic for register a new user.<br>
	 * The input user must be completed, with following informations :<br>
	 * - active flag equals false<br>
	 * - lock flag equals false<br>
	 * - user expiration Date equals "current date + 1 year"<br>
	 * - password expiration Date equals "current date + 3 month"<br>
	 * - invalid access must be filled with 0<br>
	 * - max invalid access must be filled with 5<br>
	 * - default user's roles must be defined as COMMUNITY<p>
	 * 
	 * After the pesistation process, the method must fill an activation request
	 * entity to permit user activation.
	 *    
	 * @param user The user to be registered.
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public void registerUser(User user) throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void restoreMyHistory() throws ApplicationThrowable;

	/**
	 * 
	 * @param category
	 * @throws ApplicationThrowable
	 */
	public void restoreMyHistory(Category category) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param username
	 * @throws ApplicationThrowable
	 */
	public void restoreUserHistory(String username) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param username
	 * @param category
	 * @throws ApplicationThrowable
	 */
	public void restoreUserHistory(String username, Category category) throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserHistory searchLastUserHistoryBaseEntry() throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserHistory searchLastUserHistoryEntry() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param category
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchUserHistory(Category category, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param category
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchUserHistory(Category category, PaginationFilter paginationFilter, Integer resultSize) throws ApplicationThrowable;

	/**
	 * 
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchUserHistory(PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchUserMarkedList(PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<User> searchUsers(String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public void updateUser(User user) throws ApplicationThrowable;

	/**
	 * 
	 * @param newPassword
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public void updateUserPassword(String newPassword) throws ApplicationThrowable;

	/**
	 * This method update user password on the user account linked to the request
	 * identify by the uuid.
	 * 
	 * @param uuid
	 * @param password
	 * @return
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public void updateUserPassword(UUID uuid, String password) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @param bufferedImage
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public void updateUserPhoto(User user, BufferedImage bufferedImage) throws ApplicationThrowable;

}
