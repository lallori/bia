/*
 * ApplicationThrowable.java
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
package org.medici.docsources.exception;

import org.apache.log4j.Logger;
import org.medici.docsources.common.util.ApplicationError;

/**
 * Classe di gestione degli errori applicativi.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public class ApplicationThrowable extends Throwable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6787899919869231739L;
	private ApplicationError applicationError;
	private final Logger logger = Logger.getLogger(getClass());

	public ApplicationThrowable(ApplicationError applicationError) {
		initCause(null);
		setApplicationError(applicationError);
	}

	public ApplicationThrowable(Throwable th) {
		initCause(th);
		setApplicationError(throawbleToApplicationError());
	}

	/**
	 * @return the applicationError
	 */
	public ApplicationError getApplicationError() {
		return applicationError;
	}

	/**
	 * @param applicationError
	 *            the applicationError to set
	 */
	public void setApplicationError(ApplicationError applicationError) {
		this.applicationError = applicationError;
	}

	private ApplicationError throawbleToApplicationError() {
		if (getCause() != null) {

			if (this.getCause().getClass().getName()
					.endsWith("RollbackException")) {
				if (this.getCause().getCause() != null) {
					if (this.getCause().getCause().toString()
							.contains("ConstraintViolationException")) {
						return ApplicationError.UNIQUE_CONSTRAINT_VIOLATED_ERROR;
					}

					return ApplicationError.UNKNOWN_DB_ERROR;
				} else {
					return ApplicationError.UNKNOWN_DB_ERROR;
				}
			} else if (getCause().getClass().getName()
					.endsWith("PersistenceException")) {
				return ApplicationError.UNKNOWN_DB_ERROR;
			} else if (getCause().getClass().getName()
					.endsWith("EntityNotFoundException")) {
				return ApplicationError.RECORD_NOT_FOUND_ERROR;
			} else if (getCause().getClass().getName()
					.endsWith("NoResultException")) {
				return ApplicationError.RECORD_NOT_FOUND_ERROR;
			} else if (getCause().getClass().getName()
					.endsWith("DataAccessException")) {
				if (getCause().getMessage().indexOf("Connection") != -1)
					return ApplicationError.DB_CONNECTION_LOST_ERROR;

				return ApplicationError.GENERIC_ERROR;
			} else if (getCause().getClass().getName()
					.endsWith("IllegalArgumentException")) {
				if (getCause().getMessage() != null) {
					if (getCause().getMessage().contains("no enum")) {
						return ApplicationError.RECORD_NOT_FOUND_ERROR;
					}
				}
			} else if (getCause().getClass().getName()
					.endsWith("ldap.CommunicationException")) {
				return ApplicationError.LDAP_SERVER_NOT_RESPONDING_ERROR;
			} else if (getCause().getClass().getName()
					.endsWith("ldap.NameNotFoundException")) {
				return ApplicationError.LDAP_NAME_NOT_FOUND_ERROR;
			} else if (getCause().getClass().getName()
					.endsWith("NullPointerException")) {
				if (getCause().getMessage() != null) {
					if (getCause().getMessage().contains("no enum")) {
						return ApplicationError.RECORD_NOT_FOUND_ERROR;
					}
				}

				return ApplicationError.UNKNOWN_DB_ERROR;
			} else if (getCause().getClass().getName().endsWith("Error")) {
				logger.error("An error is occured.", getCause());
			} else {
				logger.error("EXCEPTION NOT MANAGED.", getCause());
			}

			return ApplicationError.UNKNOWN_ERROR;
		} else {
			return ApplicationError.UNKNOWN_ERROR;
		}
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("Error Code : ");
		stringBuffer.append(getApplicationError());
		stringBuffer.append(", Cause Message : ");
		if (getCause() != null)
			stringBuffer.append(getCause().getMessage());
		else
			stringBuffer.append("not available");

		return stringBuffer.toString();
	}
}
