/*
 * UserAccessDetail.java
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
package org.medici.bia.common.user;

import org.medici.bia.domain.User;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class UserAccessDetail {
	
	private User user;
	private boolean online;
	private boolean teachingOnline;
	private boolean communityOnline;
	private boolean guest;
	private String ipAddress;
	
	public static final UserAccessDetail UNKNOWN_ACCESS = new UserAccessDetail() {};
	
	private UserAccessDetail() {
		guest = true;
	}
	
	public UserAccessDetail(User user) {
		if (user == null) {
			guest = true;
		}
		this.user = user;
		// if an istance is created the provided user is online
		this.online = true;
	}
	
	public UserAccessDetail(User user, boolean communityOnline, boolean teachingOnline) {
		this(user);
		this.communityOnline = communityOnline;
		this.teachingOnline = teachingOnline;
	}
	
	public User getUser() {
		return user;
	}
	
	public boolean isOnline() {
		return online;
	}
	
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public boolean isTeachingOnline() {
		return teachingOnline;
	}
	
	public void setTeachingOnline(boolean teachingOnline) {
		this.teachingOnline = teachingOnline;
	}
	
	public boolean isCommunityOnline() {
		return communityOnline;
	}
	
	public void setCommunityOnline(boolean communityOnline) {
		this.communityOnline = communityOnline;
	}
	
	public boolean isGuest() {
		return guest;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public static UserAccessDetail getAnonymousDetail(String ipAddress) {
		UserAccessDetail detail = new UserAccessDetail();
		detail.setOnline(true);
		detail.setIpAddress(ipAddress);
		return detail;
	}

}
