/*
 * EuropeanaService.java
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
package org.medici.bia.service.europeana;

import java.io.OutputStream;

import org.medici.bia.exception.ApplicationThrowable;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public interface EuropeanaService {
	
	static final String CURRENT_PHASE = "EUROPEANA_CURRENT_PHASE";
	static final String ERROR = "EUROPEANA_ERROR";
	static final String PHASES = "EUROPEANA_PHASES";
	static final String PROGRESS = "EUROPEANA_PROGRESS";

	long downladFile(OutputStream outputStream) throws ApplicationThrowable;
	
	String getEuropeanaFileSize() throws ApplicationThrowable;
	
	void writeEuropeanaFile() throws ApplicationThrowable;
	
	void writeEuropeanaFileAsync() throws ApplicationThrowable;

}
