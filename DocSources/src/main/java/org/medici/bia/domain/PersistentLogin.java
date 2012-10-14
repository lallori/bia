/*
 * PersistentLogin.java
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
package org.medici.bia.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */

@Entity
@Table(name = "persistent_logins")
public class PersistentLogin implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4774266458727881446L;

	@Id
    @Column(name="series", nullable=false)
    private String series;

    @Column(name = "last_used", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsed;

    @Column(name="token", nullable=false)
    private String token;

    @Column(name = "username")
    private String userName;

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof PersistentLogin)) {
            return false;
        }
        
        final PersistentLogin castOther = (PersistentLogin) other;
        return new EqualsBuilder().append(userName, castOther.userName)
                                  .append(token, castOther.token)
                                  .append(lastUsed, castOther.lastUsed)
                                  .isEquals();
    }

    /**
     * 
     * @return
     */
    public Date getLastUsed() {
        return lastUsed;
    }

    /**
     * 
     * @return
     */
    public String getSeries() {
        return series;
    }

    /**
     * 
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * 
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(userName).append(token).append(lastUsed).toHashCode();
    }

    /**
     * 
     * @param lastUsed
     */
    public void setLastUsed(final Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    /**
     * 
     * @param series
     */
    public void setSeries(final String series) {
        this.series = series;
    }

    /**
     * 
     * @param token
     */
    public void setToken(final String token) {
        this.token = token;
    }

    /**
     * 
     * @param userName
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        	.append("userName",userName)
        	.append("series", series)
        	.append("token", token)
        	.append("lastUsed",lastUsed).toString();
    }
}
