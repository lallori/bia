/*
 * ApplicationContextVariable.java
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
package org.medici.bia.common.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.medici.bia.common.user.UserAccessDetail;
import org.medici.bia.dao.accesslog.AccessLogDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class ApplicationContextVariableManager {
	
	public enum AccessDetailType {
		DEFAULT, COMMUNITY, TEACHING;
	}
	
	private static final String ONLINEUSERSKEY = "#_@onlineUsers@_#";
	private static final String GUESTSKEY = "#_@onlineGuests@_#";
	
	protected static final ApplicationContextVariableManager INSTANCE;
	
	private final Map<String, Object> variablesMap = new HashMap<String, Object>();
	
	@Autowired
	private AccessLogDAO accessLogDAO;
	@Autowired
	private UserDAO userDAO;
	
	static {
		INSTANCE = new ApplicationContextVariableManager();
		INSTANCE.init();
	}

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

	public synchronized static void add(String key, Object value) {
		INSTANCE.variablesMap.put(key, value);
	}
	
	public synchronized static void addGuestUser(String ipAddress, AccessDetailType type) {
		UserAccessDetail anonymousDetail = INSTANCE.getPrivateGuestsMap().get(ipAddress);
		if (anonymousDetail == null) {
			anonymousDetail = UserAccessDetail.getAnonymousDetail(ipAddress);
			INSTANCE.getPrivateGuestsMap().put(ipAddress, anonymousDetail);
		}
		if (AccessDetailType.COMMUNITY.equals(type)) {
			anonymousDetail.setCommunityOnline(true);
		} else if (AccessDetailType.TEACHING.equals(type)) {
			anonymousDetail.setTeachingOnline(true);
		}
	}
	
	public synchronized static void addOnlineUser(String account) {
		User user = INSTANCE.getUserDAO().findUser(account);
		addOnlineUser(user);
	}
	
	public synchronized static void addOnlineUser(User user) {
		if (user != null && INSTANCE.getPrivateUsersMap().get(user.getAccount()) == null) {
			INSTANCE.getPrivateUsersMap().put(user.getAccount(), new UserAccessDetail(user));
		}
	}
	
	public static long countOnlineGuests() {
		return INSTANCE.getPrivateGuestsMap().entrySet().size();
	}
	
	public static long countOnlineUsers() {
		return INSTANCE.getPrivateUsersMap().entrySet().size();
	}
	
	public synchronized static Object get(String key) {
		return INSTANCE.variablesMap.get(key);
	}
	
	public synchronized static UserAccessDetail getUserAccessDetail(String account) {
		UserAccessDetail userAccessDetail = INSTANCE.getPrivateUsersMap().get(account);
		return userAccessDetail != null ? userAccessDetail : UserAccessDetail.UNKNOWN_ACCESS;
	}
	
	public synchronized static List<String> getOnlineGuests() {
		return INSTANCE.getJoined(true);
	}
	
	public synchronized static List<String> getOnlineUsers() {
		return INSTANCE.getJoined(false);
	}
	
	public synchronized static List<String> getCommunityOnlineGuests() {
		return INSTANCE.getCommunityJoined(true);
	}
	
	public synchronized static List<String> getCommunityOnlineUsers() {
		return INSTANCE.getCommunityJoined(false);
	}
	
	public synchronized static List<String> getTeachingOnlineGuests() {
		return INSTANCE.getTeachingJoined(true);
	}
	
	public synchronized static List<String> getTeachingOnlineUsers() {
		return INSTANCE.getTeachingJoined(false);
	}
	
	public synchronized static boolean isGuestOnline(String ipAddress) {
		UserAccessDetail userAccessDetail = INSTANCE.getPrivateGuestsMap().get(ipAddress);
		return userAccessDetail != null && userAccessDetail.isOnline();
	}
	
	public synchronized static boolean isGuestJoinedInCommunity(String ipAddress) {
		UserAccessDetail userAccessDetail = INSTANCE.getPrivateGuestsMap().get(ipAddress);
		return userAccessDetail != null && userAccessDetail.isOnline() && userAccessDetail.isCommunityOnline();
	}
	
	public synchronized static boolean isGuestJoinedInTeaching(String ipAddress) {
		UserAccessDetail userAccessDetail = INSTANCE.getPrivateGuestsMap().get(ipAddress);
		return userAccessDetail != null && userAccessDetail.isOnline() && userAccessDetail.isTeachingOnline();
	}

	public synchronized static boolean isUserOnline(String account) {
		UserAccessDetail userAccessDetail = INSTANCE.getPrivateUsersMap().get(account);
		return userAccessDetail != null && userAccessDetail.isOnline();
	}
	
	public synchronized static boolean isUserJoinedInCommunity(String account) {
		UserAccessDetail userAccessDetail = INSTANCE.getPrivateUsersMap().get(account);
		return userAccessDetail != null && userAccessDetail.isOnline() && userAccessDetail.isCommunityOnline();
	}
	
	public synchronized static boolean isUserJoinedInTeaching(String account) {
		UserAccessDetail userAccessDetail = INSTANCE.getPrivateUsersMap().get(account);
		return userAccessDetail != null && userAccessDetail.isOnline() && userAccessDetail.isTeachingOnline();
	}
	
	public synchronized static void refreshAllJoined() {
		refreshOnlineGuests();
		refreshOnlineUsers();
	}
	
	public synchronized static void refreshOnlineGuests() {
		Map<String, UserAccessDetail> whoIsOnline = INSTANCE.getAccessLogDAO().guestsOnline();
		INSTANCE.variablesMap.put(GUESTSKEY, whoIsOnline);
	}
	
	public synchronized static void refreshOnlineUsers() {
		Map<String, UserAccessDetail> whoIsOnline = INSTANCE.getAccessLogDAO().usersOnline();
		INSTANCE.variablesMap.put(ONLINEUSERSKEY, whoIsOnline);
	}
	
	public synchronized static Object remove(String key) {
		return INSTANCE.variablesMap.remove(key);
	}
	
	public synchronized static User removeOnlineUser(String account) {
		UserAccessDetail removed = INSTANCE.getPrivateUsersMap().remove(account);
		return removed.getUser();
	}
	
	public synchronized static User removeOnlineUser(User user) {
		return removeOnlineUser(user.getAccount());
	}
	
	public synchronized static void updateUserAccessDetail(String account, AccessDetailType type) {
		User user = INSTANCE.getUserDAO().findUser(account);
		updateUserAccessDetail(user, type);
	}
	
	public synchronized static void updateUserAccessDetail(User user, AccessDetailType type) {
		if (user != null) {
			UserAccessDetail detail = INSTANCE.getPrivateUsersMap().get(user.getAccount());
			switch (type) {
				case COMMUNITY:
					if (detail != null) {
						detail.setCommunityOnline(true);
					} else {
						INSTANCE.getPrivateUsersMap().put(user.getAccount(), new UserAccessDetail(user, true, false));
					}
					break;
				case TEACHING:
					if (detail != null) {
						detail.setTeachingOnline(true);
					} else {
						INSTANCE.getPrivateUsersMap().put(user.getAccount(), new UserAccessDetail(user, false, true));
					}
					break;
				default:
					if (detail == null) {
						INSTANCE.getPrivateUsersMap().put(user.getAccount(), new UserAccessDetail(user));
					}
					break;
			}
		}
	}

	
	/* Privates */
	
	private Collection<UserAccessDetail> getAllJoined(boolean isAnonymous) {
		return new HashMap<String, UserAccessDetail>(isAnonymous ? INSTANCE.getPrivateGuestsMap() : INSTANCE.getPrivateUsersMap()).values();
	}
	
	private List<String> getCommunityJoined(boolean isAnonymous) {
		Collection<UserAccessDetail> joinedAccessDetails = getAllJoined(isAnonymous);
		CollectionUtils.filter(joinedAccessDetails, new Predicate() {
			
			@Override
			public boolean evaluate(Object object) {
				UserAccessDetail detail = (UserAccessDetail) object;
				return detail.isCommunityOnline();
			}
		});
		
		return getJoinedIds(joinedAccessDetails, isAnonymous);
	}
	
	private List<String> getJoined(boolean isAnonymous) {
		Collection<UserAccessDetail> joinedAccessDetails = getAllJoined(isAnonymous);
		CollectionUtils.filter(joinedAccessDetails, new Predicate() {
			
			@Override
			public boolean evaluate(Object object) {
				UserAccessDetail detail = (UserAccessDetail) object;
				return detail.isOnline();
			}
		});
		
		return getJoinedIds(joinedAccessDetails, isAnonymous);
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, UserAccessDetail> getPrivateGuestsMap() {
		return (Map<String, UserAccessDetail>)variablesMap.get(GUESTSKEY);
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, UserAccessDetail> getPrivateUsersMap() {
		return (Map<String, UserAccessDetail>)variablesMap.get(ONLINEUSERSKEY);
	}
	
	private List<String> getJoinedIds(Collection<UserAccessDetail> details, boolean isAnonymous) {
		List<String> joinedIds = new ArrayList<String>();
		for (UserAccessDetail detail : details) {
			if (isAnonymous) {
				joinedIds.add(detail.getIpAddress());
			} else {
				joinedIds.add(detail.getUser().getAccount());
			}
		}
		
		return joinedIds;
	}
	
	private List<String> getTeachingJoined(boolean isAnonymous) {
		Collection<UserAccessDetail> joinedAccessDetails = getAllJoined(isAnonymous);
		CollectionUtils.filter(joinedAccessDetails, new Predicate() {
			
			@Override
			public boolean evaluate(Object object) {
				UserAccessDetail detail = (UserAccessDetail) object;
				return detail.isTeachingOnline();
			}
		});

		return getJoinedIds(joinedAccessDetails, isAnonymous);
	}
	
	private void init() {
		setUserDAO((UserDAO)ApplicationContextProvider.getContext().getBean("userDAOJpaImpl"));
		setAccessLogDAO((AccessLogDAO)ApplicationContextProvider.getContext().getBean("accessLogDAOJpaImpl"));
		refreshAllJoined();
	}
	
}
