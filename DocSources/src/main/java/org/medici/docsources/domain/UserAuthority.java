/*
 * UserAuthority.java
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
package org.medici.docsources.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table(name = "tblUserAuthority")
public class UserAuthority implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4435922248711308962L;

	public enum Authority {
		ADMINISTRATORS("ADMINISTRATORS"), 
		COMMUNITY_COORDINATORS("COMMUNITY_COORDINATORS"),
		COMMUNITY_USERS("COMMUNITY_USERS"), 
		DIGITIZATION_COORDINATORS("DIGITIZATION_COORDINATORS"), 
		DIGITIZATION_TECHNICIANS("DIGITIZATION_TECHNICIANS"), 
		DISTANCE_FELLOWS("DISTANCE_FELLOWS"),
		DISTANCE_FELLOWS_COORDINATORS("DISTANCE_FELLOWS_COORDINATORS"),
		FORMER_FELLOWS("FORMER_FELLOWS"),
		GUESTS("GUESTS"), 
		ONSITE_FELLOWS("ONSITE_FELLOWS"), 
		SENIOR_DISTANCE_FELLOWS("SENIOR_DISTANCE_FELLOWS");

        private String value;

        Authority(String value) { this.value = value; }    

        public String getValue() { return value; }

        public static Authority getRight(String id) {
        	Authority authority = null; // Default
            for (Authority item : Authority.values()) {
                if (item.getValue().equals(id)) {
                	authority = item;
                    break;
                }
            }
            return authority;
        }

        @Override
        public String toString() {
        	return value;
        }
    };

    @Id
    @Column(name = "authority")
	@Enumerated(EnumType.STRING)
    private Authority authority;

    @Column(name = "priority", nullable=false)
    private Integer priority;
    
    /**
     * Default Constructor.
     */
    public UserAuthority() {
		super();
	}

    /**
     * 
     * @param authority
     */
    public UserAuthority(Authority authority) {
		setAuthority(authority);
	}

    /**
     * 
     * @return
     */
    public Authority getAuthority() {
        return this.authority;
    }

    /**
     * 
     * @param authority
     */
    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getPriority() {
		return priority;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		if (getAuthority() != null) {
			return getAuthority().toString();
		} else {
			return "";
		}
	}
}
