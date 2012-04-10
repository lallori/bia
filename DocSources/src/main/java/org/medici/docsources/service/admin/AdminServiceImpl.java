/*
 * AdminServiceImpl.java
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
package org.medici.docsources.service.admin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.medici.docsources.dao.applicationproperty.ApplicationPropertyDAO;
import org.medici.docsources.domain.ApplicationProperty;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Service
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {
	@Autowired
	private ApplicationPropertyDAO applicationPropertyDAO;

	/**
	 * @return the applicationPropertyDAO
	 */
	public ApplicationPropertyDAO getApplicationPropertyDAO() {
		return applicationPropertyDAO;
	}

	/**
	 * @param applicationPropertyDAO
	 *            the applicationPropertyDAO to set
	 */
	public void setApplicationPropertyDAO(ApplicationPropertyDAO applicationPropertyDAO) {
		this.applicationPropertyDAO = applicationPropertyDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void updateApplicationProperties(HashMap<String, String> hashMap) throws ApplicationThrowable {
		try {
			Iterator<Entry<String,String>> iterator = hashMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) iterator.next();
				ApplicationProperty applicationProperty = getApplicationPropertyDAO().find(pairs.getKey());
				applicationProperty.setValue(pairs.getValue());
				getApplicationPropertyDAO().merge(applicationProperty);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		// TODO Auto-generated method stub

	}
}
