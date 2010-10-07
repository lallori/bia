/*
 * DocSourcesLdapConfiguration.java
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
package org.medici.docsources.security;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class DocSourcesLdapConfiguration implements LdapConfiguration {
	private String baseDN;
	private String groupsDN;
	private String roleAttribute;
	private String roleAttributeValue;
	private String roleFilter;
	private String userAttribute;
	private String usersDN;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.medici.docsources.security.LdapConfiguration#getBaseDN()
	 */
	@Override
	public String getBaseDN() {
		return baseDN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.medici.docsources.security.LdapConfiguration#getGroupsDN()
	 */
	@Override
	public String getGroupsDN() {
		return groupsDN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.medici.docsources.security.LdapConfiguration#getRoleAttribute()
	 */
	@Override
	public String getRoleAttribute() {
		return roleAttribute;
	}

	/**
	 * @return the roleAttributeValue
	 */
	public String getRoleAttributeValue() {
		return roleAttributeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.medici.docsources.security.LdapConfiguration#getRoleFilter()
	 */
	@Override
	public String getRoleFilter() {
		return roleFilter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.medici.docsources.security.LdapConfiguration#getUserAttribute()
	 */
	@Override
	public String getUserAttribute() {
		return userAttribute;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.medici.docsources.security.LdapConfiguration#getUsersDN()
	 */
	@Override
	public String getUsersDN() {
		return usersDN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.medici.docsources.security.LdapConfiguration#setBaseDN(java.lang.
	 * String)
	 */
	@Override
	public void setBaseDN(String baseDN) {
		this.baseDN = baseDN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.medici.docsources.security.LdapConfiguration#setGroupsDN(java.lang
	 * .String)
	 */
	@Override
	public void setGroupsDN(String groupsDN) {
		this.groupsDN = groupsDN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.medici.docsources.security.LdapConfiguration#setRoleAttribute(java
	 * .lang.String)
	 */
	@Override
	public void setRoleAttribute(String roleAttribute) {
		this.roleAttribute = roleAttribute;
	}

	/**
	 * @param roleAttributeValue the roleAttributeValue to set
	 */
	public void setRoleAttributeValue(String roleAttributeValue) {
		this.roleAttributeValue = roleAttributeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.medici.docsources.security.LdapConfiguration#setRoleFilter(java.lang
	 * .String)
	 */
	@Override
	public void setRoleFilter(String roleFilter) {
		this.roleFilter = roleFilter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.medici.docsources.security.LdapConfiguration#setUserAttribute(java
	 * .lang.String)
	 */
	@Override
	public void setUserAttribute(String userAttribute) {
		this.userAttribute = userAttribute;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.medici.docsources.security.LdapConfiguration#setUsersDN(java.lang
	 * .String)
	 */
	@Override
	public void setUsersDN(String usersDN) {
		this.usersDN = usersDN;
	}
}
