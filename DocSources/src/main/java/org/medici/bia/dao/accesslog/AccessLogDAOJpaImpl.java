/*
 * AccessLogDAOJpaImpl.java
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
package org.medici.bia.dao.accesslog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.joda.time.DateTime;
import org.medici.bia.common.user.UserAccessDetail;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.AccessLog;
import org.medici.bia.domain.User;
import org.springframework.stereotype.Repository;

/**
 * <b>AccessLogDAOJpaImpl</b> is a default implementation of
 * <b>AccessLogDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.AccessLog
 */
@Repository
public class AccessLogDAOJpaImpl extends JpaDao<Integer, AccessLog> implements AccessLogDAO {

	/**
	 * 
	 *  If a serializable class does not explicitly declare a serialVersionUID, 
	 *  then the serialization runtime will calculate a default serialVersionUID 
	 *  value for that class based on various aspects of the class, as described
	 *  in the Java(TM) Object Serialization Specification. However, it is 
	 *  strongly recommended that all serializable classes explicitly declare 
	 *  serialVersionUID values, since the default serialVersionUID computation 
	 *  is highly sensitive to class details that may vary depending on compiler
	 *  implementations, and can thus result in unexpected 
	 *  InvalidClassExceptions during deserialization. Therefore, to guarantee a
	 *   consistent serialVersionUID value across different java compiler 
	 *   implementations, a serializable class must declare an explicit 
	 *  serialVersionUID value. It is also strongly advised that explicit 
	 *  serialVersionUID declarations use the private modifier where possible, 
	 *  since such declarations apply only to the immediately declaring 
	 *  class--serialVersionUID fields are not useful as inherited members. 
	 */
	private static final long serialVersionUID = -8769762056162920397L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countGuestsForum() {
		DateTime dateTime = (new DateTime(System.currentTimeMillis())).minusMinutes(5);
		Query query = getEntityManager().createQuery("SELECT COUNT(DISTINCT ipAddress) FROM AccessLog WHERE account = 'anonymousUser' AND action LIKE '%community%' AND (dateAndTime > '"+ DateUtils.getMYSQLDateTime(dateTime) + "')");
		query.setMaxResults(1);
		return (Long) query.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException {
		String jpql = "UPDATE AccessLog SET account=:newAccount WHERE account=:originalAccount";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("newAccount", newAccount);
		query.setParameter("originalAccount", originalAccount);

		return query.executeUpdate();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, UserAccessDetail> guestsOnline() {
		DateTime dateTime = (new DateTime(System.currentTimeMillis())).minusMinutes(30);
		
		StringBuilder sb = new StringBuilder("SELECT a.ipAddress,")
			.append(" CASE WHEN SUM(CASE WHEN a.action LIKE '%community%' THEN 1 ELSE 0 END) > 0 THEN true ELSE false END AS onlineCommunity,")
			.append(" CASE WHEN SUM(CASE WHEN a.action LIKE '%teaching%' THEN 1 ELSE 0 END) > 0 THEN true ELSE false END AS onlineTeaching")
			.append(" FROM")
			.append(" AccessLog AS a")
			.append(" WHERE")
			.append(" (a.idAccessLog, a.account) IN")
			.append(" ( SELECT")
			.append(" al.idAccessLog,")
			.append(" al.account")
			.append(" FROM")
			.append(" AccessLog AS al")
			.append(" WHERE")
			.append(" al.dateAndTime > '").append(DateUtils.getMYSQLDateTime(dateTime)).append("' AND")
			.append(" al.account = 'anonymousUser'")
			.append(" )")
			.append(" GROUP BY a.ipAddress");
		
		Query query = getEntityManager().createQuery(sb.toString());
		List<Object[]> results = query.getResultList();
		
		Map<String, UserAccessDetail> whoIsOnlineMap = new HashMap<String, UserAccessDetail>();
		for(Object[] detail : results) {
			String ipAddress = (String) detail[0];
			Boolean onlineCommunity = (Boolean) detail[1];
			Boolean onlineTeaching = (Boolean) detail[2];
			UserAccessDetail userDetail = UserAccessDetail.getAnonymousDetail(ipAddress);
			userDetail.setCommunityOnline(onlineCommunity);
			userDetail.setTeachingOnline(onlineTeaching);
			whoIsOnlineMap.put(ipAddress, userDetail);
		}
		
		return whoIsOnlineMap;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, UserAccessDetail> usersOnline() {
		DateTime dateTime = (new DateTime(System.currentTimeMillis())).minusMinutes(30);
		
		StringBuilder sb = new StringBuilder("SELECT u,")
			.append(" CASE WHEN SUM(CASE WHEN a.action LIKE '%community%' THEN 1 ELSE 0 END) > 0 THEN true ELSE false END AS onlineCommunity,")
			.append(" CASE WHEN SUM(CASE WHEN a.action LIKE '%teaching%' THEN 1 ELSE 0 END) > 0 THEN true ELSE false END AS onlineTeaching")
			.append(" FROM")
			.append(" User AS u,")
			.append(" AccessLog AS a")
			.append(" WHERE")
			.append(" u.account = a.account")
			.append(" AND (a.idAccessLog, a.account) IN")
			.append(" ( SELECT")
			.append(" al.idAccessLog,")
			.append(" al.account")
			.append(" FROM")
			.append(" AccessLog AS al")
			.append(" WHERE")
			.append(" al.dateAndTime > '").append(DateUtils.getMYSQLDateTime(dateTime)).append("'")
			.append(" )")
			.append(" GROUP BY a.account")
			.append(" ORDER BY u.account ASC");
		
		Query query = getEntityManager().createQuery(sb.toString());
		List<Object[]> results = query.getResultList();
		
		Map<String, UserAccessDetail> whoIsOnlineMap = new HashMap<String, UserAccessDetail>();
		for(Object[] detail : results) {
			User user = (User) detail[0];
			Boolean onlineCommunity = (Boolean) detail[1];
			Boolean onlineTeaching = (Boolean) detail[2];
			UserAccessDetail userDetail = new UserAccessDetail(user, onlineCommunity, onlineTeaching);
			whoIsOnlineMap.put(user.getAccount(), userDetail);
		}
		
		return whoIsOnlineMap;
	}
}
