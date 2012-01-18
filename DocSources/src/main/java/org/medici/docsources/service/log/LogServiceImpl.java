/*
 * HistoryLogServiceImpl.java
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
package org.medici.docsources.service.log;

import org.medici.docsources.dao.historylog.HistoryLogDAO;
import org.medici.docsources.dao.accesslog.AccessLogDAO;
import org.medici.docsources.domain.AccessLog;
import org.medici.docsources.domain.HistoryLog;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Service
@Transactional(readOnly=true)
public class LogServiceImpl implements LogService {
	@Autowired
	private HistoryLogDAO historyLogDAO;
	@Autowired
	private AccessLogDAO accessLogDAO;

	/**
	 * @return the historyLogDAO
	 */
	public HistoryLogDAO getHistoryLogDAO() {
		return historyLogDAO;
	}

	/**
	 * @param historyLogDAO
	 *            the historyLogDAO to set
	 */
	public void setHistoryLogDAO(HistoryLogDAO historyLogDAO) {
		this.historyLogDAO = historyLogDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void traceHistoryLog(HistoryLog historyLog) throws ApplicationThrowable {
		try {
			getHistoryLogDAO().persist(historyLog);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void traceAccessLog(AccessLog accessLog) throws ApplicationThrowable {
		try {
			getAccessLogDAO().persist(accessLog);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @param accessLogDAO the accessLogDAO to set
	 */
	public void setAccessLogDAO(AccessLogDAO accessLogDAO) {
		this.accessLogDAO = accessLogDAO;
	}

	/**
	 * @return the accessLogDAO
	 */
	public AccessLogDAO getAccessLogDAO() {
		return accessLogDAO;
	}
}
