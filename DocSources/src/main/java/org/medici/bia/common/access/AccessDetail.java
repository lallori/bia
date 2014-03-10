/*
 * AccessDetail.java
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

import org.medici.bia.domain.User;

/**
 * This class represents a user/guest access detail.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class AccessDetail {
	
	private User user;
	private boolean online;
	private boolean teachingOnline;
	private boolean communityOnline;
	private boolean guest;
	private String ipAddress;
	
	/**
	 * A guest empty access.
	 */
	public static final AccessDetail UNKNOWN_ACCESS = new AccessDetail() {};
	
	private AccessDetail() {
		guest = true;
	}
	
	/**
	 * This constructor define a simple access detail for the provided user.<br/>
	 * It should not be used for guests (use static call {@link #getAnonymousDetail(String)} instead).
	 * 
	 * @param user a user
	 */
	public AccessDetail(User user) {
		if (user == null) {
			guest = true;
		}
		this.user = user;
		// if an istance is created the provided user is online
		this.online = true;
	}
	
	/**
	 * This constructor define a complex access detail dor the provided user.<br/>
	 * It should not be used for guests (use static call {@link #getAnonymousDetail(String)} instead).
	 * 
	 * @param user a user
	 * @param communityOnline true if user is joined to the community module
	 * @param teachingOnline true if user is joined to the teaching module
	 */
	public AccessDetail(User user, boolean communityOnline, boolean teachingOnline) {
		this(user);
		this.communityOnline = communityOnline;
		this.teachingOnline = teachingOnline;
	}
	
	/**
	 * Returns the user.
	 * 
	 * @return the user or null if it is a guest
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Returns true if the user/guest is online.
	 * 
	 * @return true if user/guest is online
	 */
	public boolean isOnline() {
		return online;
	}
	
	/**
	 * Sets the online value.
	 * 
	 * @param online the online value to set
	 */
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	/**
	 * Returns true if the user/guest is joined to the community module.
	 * 
	 * @return true if user/guest is joined
	 */
	public boolean isCommunityOnline() {
		return communityOnline;
	}
	
	/**
	 * Sets the communityOnline value.
	 * 
	 * @param communityOnline the communityOnline value to set
	 */
	public void setCommunityOnline(boolean communityOnline) {
		this.communityOnline = communityOnline;
	}
	
	/**
	 * Returns true if the user/guest is joined to the teaching module.
	 * 
	 * @return true if user/guest is joined
	 */
	public boolean isTeachingOnline() {
		return teachingOnline;
	}
	
	/**
	 * Sets the teachingOnline value.
	 * 
	 * @param teachingOnline the teachingOnline value to set
	 */
	public void setTeachingOnline(boolean teachingOnline) {
		this.teachingOnline = teachingOnline;
	}
	
	/**
	 * Returns true if the current user detail is relative to a guest.
	 * 
	 * @return true if this detail is relative to a guest
	 */
	public boolean isGuest() {
		return guest;
	}

	/**
	 * Returns the ip address of this detail.
	 * 
	 * @return the ip address
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * Sets the ip address.
	 * 
	 * @param ipAddress the ip address to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	/**
	 * Returns an anonymous detail (guest) corresponding to the provided ip address.
	 * 
	 * @param ipAddress the ip address
	 * @return the anonymous detail
	 */
	public static AccessDetail getAnonymousDetail(String ipAddress) {
		AccessDetail detail = new AccessDetail();
		detail.setOnline(true);
		detail.setIpAddress(ipAddress);
		return detail;
	}

}
