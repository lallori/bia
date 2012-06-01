/*
 * UserInformationDAOJpaImpl.java
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
package org.medici.docsources.dao.userinformation;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.UserInformation;
import org.springframework.stereotype.Repository;

/**
 * <b>UserInformationDAOJpaImpl</b> is a default implementation of
 * <b>UserInformationDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.domain.UserInformation
 */
@Repository
public class UserInformationDAOJpaImpl extends JpaDao<String, UserInformation> implements UserInformationDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1193605850422464008L;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserInformation getNewestMember() throws PersistenceException {
        String queryString = "FROM UserInformation WHERE lastLoginDate is not null ORDER BY activationDate DESC";
    	
        Query query = getEntityManager().createQuery(queryString);
    	
		List<UserInformation> result = (List<UserInformation>) query.getResultList();
			
		if (result.size() > 0) {
			return (UserInformation) result.get(0);
		}
        
		return new UserInformation();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countMembersForum() throws PersistenceException {
        String queryString = "select count(account) FROM UserInformation WHERE lastLoginDate is not null";
    	
        Query query = getEntityManager().createQuery(queryString);
    	
        return (Long) query.getSingleResult();
	}

}
