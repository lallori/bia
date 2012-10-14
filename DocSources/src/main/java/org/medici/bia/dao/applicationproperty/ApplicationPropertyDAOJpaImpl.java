/*
 * ApplicationPropertyDAOJpaImpl.java
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
package org.medici.bia.dao.applicationproperty;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.ApplicationProperty;
import org.springframework.stereotype.Repository;

/**
 * <b>ApplicationPropertyDAOJpaImpl</b> is a default implementation of <b>ApplicationPropertyDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.AltName
 */
@Repository
public class ApplicationPropertyDAOJpaImpl extends JpaDao<String, ApplicationProperty> implements ApplicationPropertyDAO {
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
	private static final long serialVersionUID = 2108912098591204306L;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getApplicationProperty(String id) {
		try {
	        Query query = getEntityManager().createQuery("FROM ApplicationProperty where id=:id");
	        query.setParameter("id", id);
	
	        List<ApplicationProperty> list = query.getResultList();
	        
	        if (list.size() ==1) {
	        	return ((ApplicationProperty)list.get(0)).getValue();
	        } else if (list.size() ==0) {
				logger.fatal("Property " + id + " not found. Check database table.");
				return "";
	        } else {
	        	return "";
	        }
		} catch(PersistenceException persistenceException) {
			logger.debug("Exception during reading application property " + id, persistenceException);
			return "";
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getApplicationPropertiesNames() {
		try {
	        Query query = getEntityManager().createQuery("SELECT id FROM ApplicationProperty ORDER by id ASC ");

	         return query.getResultList();
		} catch(PersistenceException persistenceException) {
			logger.error("Exception during reading application properties names ", persistenceException);
			return new ArrayList<String>(0);
		}
	}
}
