/*
 * AccessLogStatisticsDAOJpaImpl.java
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
package org.medici.bia.dao.accesslogstatistics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.AccessLogStatistics;
import org.springframework.stereotype.Repository;

/**
 * <b>AccessLogStatisticsDAOJpaImpl</b> is a default implementation of
 * <b>AccessLogStatisticsDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.AccessLogStatistics
 */
@Repository
public class AccessLogStatisticsDAOJpaImpl extends JpaDao<Integer, AccessLogStatistics> implements AccessLogStatisticsDAO {
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
	private static final long serialVersionUID = -3579212220653601715L;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer deleteStatisticsOnDay(Date date) throws PersistenceException {
		String jpql = "DELETE FROM AccessLogStatistics WHERE date=:date";
    	
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("date", date);
        
        return query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean generateStatisticsOnDay(Date date) throws PersistenceException {
		// select date_format(dateAndTime, '%Y/%m/%d'), action, httpMethod, count(idAccessLog), avg(executionTime), min(executionTime), max(executionTime) from tblAccessLog where date_format(dateAndTime, '%Y/%m/%d') = date_format('2012/08/03', '%Y/%m/%d') 
		// group by action, httpMethod

		String jpql = "SELECT action, httpMethod, COUNT(idAccessLog), AVG(executionTime), MIN(executionTime), MAX(executionTime) " +
		"FROM AccessLog WHERE DATE_FORMAT(dateAndTime, '%Y-%m-%d')=:dateAndTime GROUP BY action, httpMethod";

		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("dateAndTime", DateUtils.getMYSQLDate(date));
		List<Object[]> result = query.getResultList();
		
		if (result.size() > 0) {
		
			// We set new partial-total values 
			for (int i=0; i<result.size(); i++) {
				// This is an array defined as [ImageType, Count by ImageType]
				Object[] singleGroup = result.get(i);
				AccessLogStatistics accessLogStatistics = new AccessLogStatistics();
				accessLogStatistics.setDate(date);
				accessLogStatistics.setAction(singleGroup[0].toString());
				accessLogStatistics.setHttpMethod(singleGroup[1].toString());
				accessLogStatistics.setCount(new Integer(singleGroup[2].toString()));
				accessLogStatistics.setAverageTime(new Double(singleGroup[3].toString()));
				accessLogStatistics.setBestTime(new Long(singleGroup[4].toString()));
				accessLogStatistics.setWorstTime(new Long(singleGroup[5].toString()));
				
				getEntityManager().persist(accessLogStatistics);
			}
		}		
		
		return Boolean.TRUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Date> findMissingStatisticsDate(Integer numberMaxOfDay) throws PersistenceException {
		List<Date> returnValue = new ArrayList<Date>(0); 
		// SELECT distinct(DATE_FORMAT(dateAndTime, '%Y-%m-%d')) from AccessLog where DATE_FORMAT(dateAndTime, '%Y-%m-%d') not in (select distinct(date) from AccessLogStatistics)
		String jpql = "SELECT distinct(DATE_FORMAT(dateAndTime, '%Y-%m-%d')) from AccessLog where DATE_FORMAT(dateAndTime, '%Y-%m-%d') not in (select distinct(date) from AccessLogStatistics)";
		Query query = getEntityManager().createQuery(jpql);
		query.setFirstResult(0);
		query.setMaxResults(numberMaxOfDay);

		List<String> result = query.getResultList();
		
		if (result.size() > 0) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			for (int i=0; i<result.size(); i++) {
				try {
					returnValue.add(dateFormat.parse(result.get(i)));
				} catch (ParseException parseException) {
					logger.error(parseException);
				}
			}
		}
		
		return returnValue;
	}

}
