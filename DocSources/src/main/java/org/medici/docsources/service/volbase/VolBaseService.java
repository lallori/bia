/*
 * VolBaseService.java
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
package org.medici.docsources.service.volbase;

import java.util.List;

import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.SerieList;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;

/**
 * This interface is designed to work on {@link org.medici.docsources.domain.Volume} 
 * object.<br>
 * It defines every business methods needed to work on volumes.
 * With this service, you can :<br>
 * - add a new volume<br>
 * - modify an existing volume<br> 
 * - search a volume by is summaryId, volNum and volLeText (this last is optional ndr)<br>
 * - execute complex search on volumes<br>
 * ...<br>
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public interface VolBaseService {
	/**
	 * This method add a new Volume.
	 * 
	 * @param volume
	 * @throws ApplicationThrowable
	 */
	public void addNewVolume(Volume volume) throws ApplicationThrowable;

	/**
	 * This method modify an existing Volume.
	 * 
	 * @param volume
	 * @throws ApplicationThrowable
	 */
	public void editVolume(Volume volume) throws ApplicationThrowable;

	/**
	 * This method will search an existing volume by his unique identifiers.
	 * 
	 * @param summaryId
	 * @param volNum
	 * @param volLeText
	 * @return
	 */
	public Volume findVolume(Integer summaryId, Integer volNum, String volLeText) throws ApplicationThrowable;

	/**
	 * This method searches for existing seriesList object.
	 * 
	 * @param alias
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<SerieList> findSeries(String alias) throws ApplicationThrowable;

	/**
	 * This method searches for existing volumes that contains input text.
	 * 
	 * @param text
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Volume> searchVolumes(String text) throws ApplicationThrowable;

	/**
	 * This method extracts all months available.
	 *  
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Month> getMonths() throws ApplicationThrowable;
}
