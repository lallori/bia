/*
 * ApplicationAccessContainer.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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
package org.medici.bia.common.access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.medici.bia.dao.accesslog.AccessLogDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.domain.User;
import org.medici.bia.scheduler.WhoIsOnlineJob;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class stores {@link UserAccessDetail} of application users and guests.<br/>
 * NOTE: this is an 'application scope' bean that is updated by {@link WhoIsOnlineJob}.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class ApplicationAccessContainer {
	
	private Map<String, AccessDetail> guestsMap = new HashMap<String, AccessDetail>();
	private Map<String, AccessDetail> usersMap = new HashMap<String, AccessDetail>();
	
	@Autowired
	private AccessLogDAO accessLogDAO;
	@Autowired
	private UserDAO userDAO;
	
	public AccessLogDAO getAccessLogDAO() {
		return accessLogDAO;
	}

	public void setAccessLogDAO(AccessLogDAO accessLogDAO) {
		this.accessLogDAO = accessLogDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	
	/**
	 * Adds/Updates a guest access detail (identified by his <code>ipAddress</code>) to guests stored.
	 * 
	 * @param ipAddress the ipAddress of the guest 
	 * @param type the access type
	 */
	public void addGuestUser(String ipAddress, AccessDetailType type) {
		AccessDetail anonymousDetail = guestsMap.get(ipAddress);
		if (anonymousDetail == null) {
			anonymousDetail = AccessDetail.getAnonymousDetail(ipAddress);
			_addGuestUser(anonymousDetail);
		}
		if (AccessDetailType.COMMUNITY.equals(type)) {
			anonymousDetail.setCommunityOnline(true);
		} else if (AccessDetailType.TEACHING.equals(type)) {
			anonymousDetail.setTeachingOnline(true);
		}
	}
	
	/**
	 * Associates a user to online users stored.
	 * 
	 * @param account the user account identifier
	 */
	public void addOnlineUser(String account) {
		User user = getUserDAO().findUser(account);
		addOnlineUser(user);
	}
	
	/**
	 * Associates a user to online users stored.
	 * 
	 * @param user the user
	 */
	public void addOnlineUser(User user) {
		if (user != null && usersMap.get(user.getAccount()) == null) {
			_addNotAnonymousUser(new AccessDetail(user));
		}
	}
	
	/**
	 * Returns the number of online guest.
	 * 
	 * @return the number of online guests
	 */
	public long countOnlineGuests() {
		return guestsMap.entrySet().size();
	}
	
	/**
	 * Returns the number of online users.
	 * 
	 * @return the number of online users
	 */
	public long countOnlineUsers() {
		return usersMap.entrySet().size();
	}
	
	/**
	 * Returns the user access detail corresponding to the provided account.
	 * 
	 * @param account the user's account
	 * @return the user's {@link AccessDetail}
	 */
	public AccessDetail getUserAccessDetail(String account) {
		AccessDetail userAccessDetail = usersMap.get(account);
		return userAccessDetail != null ? userAccessDetail : AccessDetail.UNKNOWN_ACCESS;
	}
	
	/**
	 * Returns the list of ip addresses corresponding to the joined guests.
	 *  
	 * @return the list of ip addresses
	 */
	public List<String> getOnlineGuests() {
		return getJoined(true);
	}
	
	/**
	 * Returns the list of user's accounts corresponding to the joined users.
	 *  
	 * @return the list of user's accounts
	 */
	public List<String> getOnlineUsers() {
		return getJoined(false);
	}
	
	/**
	 * Returns the list of ip addresses corresponding to the guests joined to the community module.
	 *  
	 * @return the list of ip addresses
	 */
	public List<String> getCommunityOnlineGuests() {
		return getCommunityJoined(true);
	}
	
	/**
	 * Returns the list of user's accounts corresponding to the users joined to the community module.
	 * 
	 * @return the list of user's accounts
	 */
	public List<String> getCommunityOnlineUsers() {
		return getCommunityJoined(false);
	}
	
	/**
	 * Returns the list of ip addresses corresponding to the guests joined to the teaching module.
	 *  
	 * @return the list of ip addresses
	 */
	public List<String> getTeachingOnlineGuests() {
		return getTeachingJoined(true);
	}
	
	/**
	 * Returns the list of user's accounts corresponding to the users joined to the teaching module.
	 * 
	 * @return the list of user's accounts
	 */
	public List<String> getTeachingOnlineUsers() {
		return getTeachingJoined(false);
	}
	
	/**
	 * Returns if the guest corresponding to the provided ip address is online.
	 * 
	 * @param ipAddress the guest's ip address
	 * @return true if guest is online, otherwise false 
	 */
	public boolean isGuestOnline(String ipAddress) {
		AccessDetail userAccessDetail = guestsMap.get(ipAddress);
		return userAccessDetail != null && userAccessDetail.isOnline();
	}
	
	/**
	 * Returns if the guest corresponding to the provided ip address is joined to the community module.
	 * 
	 * @param ipAddress the guest's ip address
	 * @return true if guest is joined, otherwise false 
	 */
	public boolean isGuestJoinedInCommunity(String ipAddress) {
		AccessDetail userAccessDetail = guestsMap.get(ipAddress);
		return userAccessDetail != null && userAccessDetail.isOnline() && userAccessDetail.isCommunityOnline();
	}
	
	/**
	 * Returns if the guest corresponding to the provided ip address is joined to the teaching module.
	 * 
	 * @param ipAddress the guest's ip address
	 * @return true if guest is joined, otherwise false 
	 */
	public boolean isGuestJoinedInTeaching(String ipAddress) {
		AccessDetail userAccessDetail = guestsMap.get(ipAddress);
		return userAccessDetail != null && userAccessDetail.isOnline() && userAccessDetail.isTeachingOnline();
	}

	/**
	 * Returns if the user is online.
	 * 
	 * @param account the user account
	 * @return true if user is online, otherwise false 
	 */
	public boolean isUserOnline(String account) {
		AccessDetail userAccessDetail = usersMap.get(account);
		return userAccessDetail != null && userAccessDetail.isOnline();
	}
	
	/**
	 * Returns if the user is joined to the community module.
	 * 
	 * @param account the user account
	 * @return true if user is joined, otherwise false 
	 */
	public boolean isUserJoinedInCommunity(String account) {
		AccessDetail userAccessDetail = usersMap.get(account);
		return userAccessDetail != null && userAccessDetail.isOnline() && userAccessDetail.isCommunityOnline();
	}
	
	/**
	 * Returns if the user is joined to the teaching module.
	 * 
	 * @param account the user account
	 * @return true if user is joined, otherwise false 
	 */
	public boolean isUserJoinedInTeaching(String account) {
		AccessDetail userAccessDetail = usersMap.get(account);
		return userAccessDetail != null && userAccessDetail.isOnline() && userAccessDetail.isTeachingOnline();
	}
	
	
	/**
	 * Reloads all the joined guests and users.
	 */
	public void refreshAllJoined() {
		refreshOnlineGuests();
		refreshOnlineUsers();
	}
	
	/**
	 * Reloads all the joined guests.
	 */
	public void refreshOnlineGuests() {
		Map<String, AccessDetail> whoIsOnline = getAccessLogDAO().guestsOnline();
		_replaceGuests(whoIsOnline);
	}

	/**
	 * Reloads all the joined users.
	 */
	public void refreshOnlineUsers() {
		Map<String, AccessDetail> whoIsOnline = getAccessLogDAO().usersOnline();
		_replaceNotAnonymousUsers(whoIsOnline);
	}
	
	/**
	 * Removes the user corresponding to the provided account from the stored data.
	 * 
	 * @param account the user account
	 * @return the user removed from the stored data, null if none
	 */
	public User removeOnlineUser(String account) {
		return _removeOnlineUser(account);
	}
	
	/**
	 * Removes the user from the stored data.
	 * 
	 * @param user the user to remove
	 * @return the user removed from the stored data, null if none
	 */
	public User removeOnlineUser(User user) {
		return removeOnlineUser(user.getAccount());
	}
	
	/**
	 * Stores a user active access detail.
	 * 
	 * @param account the user account
	 * @param type the active access type (one of DEFAULT, COMMUNITY or TEACHING)
	 */
	public void setActiveUserAccessDetail(String account, AccessDetailType type) {
		User user = getUserDAO().findUser(account);
		setActiveUserAccessDetail(user, type);
	}
	
	/**
	 * Stores a user active access detail.
	 * 
	 * @param user the user
	 * @param type the active access type (one of DEFAULT, COMMUNITY or TEACHING)
	 */
	public void setActiveUserAccessDetail(User user, AccessDetailType type) {
		if (user != null) {
			AccessDetail detail = usersMap.get(user.getAccount());
			switch (type) {
				case COMMUNITY:
					if (detail != null) {
						detail.setCommunityOnline(true);
					} else {
						_addNotAnonymousUser(new AccessDetail(user, true, false));
					}
					break;
				case TEACHING:
					if (detail != null) {
						detail.setTeachingOnline(true);
					} else {
						_addNotAnonymousUser(new AccessDetail(user, false, true));
					}
					break;
				default:
					if (detail == null) {
						_addNotAnonymousUser(new AccessDetail(user));
					}
					break;
			}
		}
	}
	
	/* Privates */
	
	private synchronized void _addGuestUser(AccessDetail detail) {
		guestsMap.put(detail.getIpAddress(), detail);
	}
	
	private synchronized void _addNotAnonymousUser(AccessDetail detail) {
		usersMap.put(detail.getUser().getAccount(), detail);
	}
	
	private synchronized User _removeOnlineUser(String account) {
		return usersMap.remove(account).getUser();
	}
	
	private synchronized void _replaceGuests(Map<String, AccessDetail> whoIsOnline) {
		guestsMap = whoIsOnline;
	}
	
	private synchronized void _replaceNotAnonymousUsers(Map<String, AccessDetail> whoIsOnline) {
		usersMap = whoIsOnline;
	}
	
	private Collection<AccessDetail> getAllJoined(boolean isAnonymous) {
		return new HashMap<String, AccessDetail>(isAnonymous ? guestsMap : usersMap).values();
	}
	
	private List<String> getCommunityJoined(boolean isAnonymous) {
		Collection<AccessDetail> joinedAccessDetails = getAllJoined(isAnonymous);
		CollectionUtils.filter(joinedAccessDetails, new Predicate() {
			
			@Override
			public boolean evaluate(Object object) {
				AccessDetail detail = (AccessDetail) object;
				return detail.isCommunityOnline();
			}
		});
		
		return getJoinedIds(joinedAccessDetails, isAnonymous);
	}
	
	private List<String> getJoined(boolean isAnonymous) {
		Collection<AccessDetail> joinedAccessDetails = getAllJoined(isAnonymous);
		CollectionUtils.filter(joinedAccessDetails, new Predicate() {
			
			@Override
			public boolean evaluate(Object object) {
				AccessDetail detail = (AccessDetail) object;
				return detail.isOnline();
			}
		});
		
		return getJoinedIds(joinedAccessDetails, isAnonymous);
	}
	
	private List<String> getJoinedIds(Collection<AccessDetail> details, boolean isAnonymous) {
		List<String> joinedIds = new ArrayList<String>();
		for (AccessDetail detail : details) {
			if (isAnonymous) {
				joinedIds.add(detail.getIpAddress());
			} else {
				joinedIds.add(detail.getUser().getAccount());
			}
		}
		
		return joinedIds;
	}
	
	private List<String> getTeachingJoined(boolean isAnonymous) {
		Collection<AccessDetail> joinedAccessDetails = getAllJoined(isAnonymous);
		CollectionUtils.filter(joinedAccessDetails, new Predicate() {
			
			@Override
			public boolean evaluate(Object object) {
				AccessDetail detail = (AccessDetail) object;
				return detail.isTeachingOnline();
			}
		});

		return getJoinedIds(joinedAccessDetails, isAnonymous);
	}

}
