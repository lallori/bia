/*
 * VolBaseServiceImpl.java
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

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.month.MonthDAO;
import org.medici.docsources.dao.serieslist.SeriesListDAO;
import org.medici.docsources.dao.volume.VolumeDAO;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.SerieList;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is the default implementation of service responsible for every 
 * action on volume :
 * - add a new volume
 * - edit an existing volume
 * - delete a volume
 * - execute simple search
 * - execute advanced search 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Service
public class VolBaseServiceImpl implements VolBaseService {
	@Autowired
	private MonthDAO monthDAO;
	@Autowired
	private SeriesListDAO seriesListDAO;
	@Autowired
	private VolumeDAO volumeDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNewVolume(Volume volume) throws ApplicationThrowable {
		try {
			// Setting primary key to null to permit persist operation, otherwise jpa will throw a Persistence Object Expcetion
			volume.setSummaryId(null);
			
			// Retrieves every object references
			if (volume.getSerieList() != null)
				volume.setSerieList(getSeriesListDAO().find(volume.getSerieList().getSeriesRefNum()));
			
			//Setting fields that are defined as nullable = false
			volume.setVolTobeVetted(true);
			volume.setVolVetted(false);
			volume.setBound(false);
			volume.setFolsNumbrd(false);
			volume.setOldAlphaIndex(false);
			volume.setItalian(false);
			volume.setSpanish(false);
			volume.setEnglish(false);
			volume.setLatin(false);
			volume.setGerman(false);
			volume.setFrench(false);
			volume.setCipher(false);

			getVolumeDAO().persist(volume);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editContextVolume(Volume volume) throws ApplicationThrowable {
		Volume volumeToUpdate = null;
		try {
			volumeToUpdate = getVolumeDAO().find(volume.getSummaryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		volumeToUpdate.setCcontext(volume.getCcontext());
		
		try {
			getVolumeDAO().merge(volumeToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editCorrespondentsVolume(Volume volume) throws ApplicationThrowable {
		Volume volumeToUpdate = null;
		try {
			volumeToUpdate = getVolumeDAO().find(volume.getSummaryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		volumeToUpdate.setRecips(volume.getRecips());
		volumeToUpdate.setSenders(volume.getSenders());
		
		try {
			getVolumeDAO().merge(volumeToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editDescriptionVolume(Volume volume) throws ApplicationThrowable {
		Volume volumeToUpdate = null;
		try {
			volumeToUpdate = getVolumeDAO().find(volume.getSummaryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		volumeToUpdate.setOrgNotes(volume.getOrgNotes());
		volumeToUpdate.setCcondition(volume.getCcondition());
		volumeToUpdate.setBound(volume.getBound());
		volumeToUpdate.setFolsNumbrd(volume.getFolsNumbrd());
		volumeToUpdate.setOldAlphaIndex(volume.getOldAlphaIndex());
		volumeToUpdate.setItalian(volume.getItalian());
		volumeToUpdate.setSpanish(volume.getSpanish());
		volumeToUpdate.setEnglish(volume.getEnglish());
		volumeToUpdate.setLatin(volume.getLatin());
		volumeToUpdate.setGerman(volume.getGerman());
		volumeToUpdate.setFrench(volume.getFrench());
		volumeToUpdate.setOtherLang(volume.getOtherLang());
		volumeToUpdate.setCipher(volume.getCipher());
		volumeToUpdate.setCipherNotes(volume.getCipherNotes());
		
		try {
			getVolumeDAO().merge(volumeToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editDetailsVolume(Volume volume) throws ApplicationThrowable {
		Volume volumeToUpdate = null;
		try {
			volumeToUpdate = getVolumeDAO().find(volume.getSummaryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		volumeToUpdate.setVolNum(volume.getVolNum());
		volumeToUpdate.setVolLeText(volume.getVolLeText());
		volumeToUpdate.setResearcher(volume.getResearcher());
		volumeToUpdate.setDateCreated(volume.getDateCreated());
		volumeToUpdate.setSerieList(volume.getSerieList());
		volumeToUpdate.setStartYear(volume.getStartYear());
		volumeToUpdate.setStartMonth(volume.getStartMonth());
		volumeToUpdate.setStartDay(volume.getStartDay());
		volumeToUpdate.setEndYear(volume.getEndYear());
		volumeToUpdate.setEndMonth(volume.getEndMonth());
		volumeToUpdate.setEndDay(volume.getEndDay());
		volumeToUpdate.setDateNotes(volume.getDateNotes());
		
		try {
			getVolumeDAO().merge(volumeToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume findLastEntryVolume() throws ApplicationThrowable {
		try {
			return getVolumeDAO().findLastEntryVolume();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SerieList> findSeries(String alias) throws ApplicationThrowable {
		try {
			return getSeriesListDAO().findSeries(alias);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume findVolume(Integer summaryId) throws ApplicationThrowable {
		try {
			return getVolumeDAO().find(summaryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the monthDAO
	 */
	public MonthDAO getMonthDAO() {
		return monthDAO;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Month> getMonths() throws ApplicationThrowable {
		try {
			return getMonthDAO().getAllMonths();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the seriesListDAO
	 */
	public SeriesListDAO getSeriesListDAO() {
		return seriesListDAO;
	}

	/**
	 * @return the volumeDAO
	 */
	public VolumeDAO getVolumeDAO() {
		return volumeDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchVolumes(String text, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getVolumeDAO().searchVolumes(text, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @param monthDAO the monthDAO to set
	 */
	public void setMonthDAO(MonthDAO monthDAO) {
		this.monthDAO = monthDAO;
	}

	/**
	 * @param seriesListDAO the seriesListDAO to set
	 */
	public void setSeriesListDAO(SeriesListDAO seriesListDAO) {
		this.seriesListDAO = seriesListDAO;
	}

	/**
	 * @param volumeDAO the volumeDAO to set
	 */
	public void setVolumeDAO(VolumeDAO volumeDAO) {
		this.volumeDAO = volumeDAO;
	}

}
