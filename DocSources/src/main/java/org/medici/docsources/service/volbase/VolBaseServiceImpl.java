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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.pagination.VolumeExplorer;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.VolumeUtils;
import org.medici.docsources.common.volume.FoliosInformations;
import org.medici.docsources.common.volume.VolumeSummary;
import org.medici.docsources.dao.catalog.CatalogDAO;
import org.medici.docsources.dao.image.ImageDAO;
import org.medici.docsources.dao.month.MonthDAO;
import org.medici.docsources.dao.serieslist.SeriesListDAO;
import org.medici.docsources.dao.userhistoryvolume.UserHistoryVolumeDAO;
import org.medici.docsources.dao.volume.VolumeDAO;
import org.medici.docsources.domain.Catalog;
import org.medici.docsources.domain.Image;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.SerieList;
import org.medici.docsources.domain.UserHistoryVolume;
import org.medici.docsources.domain.UserHistoryVolume.Action;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.security.DocSourcesLdapUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private CatalogDAO catalogDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private MonthDAO monthDAO;
	@Autowired
	private SeriesListDAO seriesListDAO;
	@Autowired
	private UserHistoryVolumeDAO userHistoryVolumeDAO;
	@Autowired
	private VolumeDAO volumeDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume addNewVolume(Volume volume) throws ApplicationThrowable {
		try {
			// Setting primary key to null to permit persist operation, otherwise jpa will throw a Persistence Object Expcetion
			volume.setSummaryId(null);
			
			if (StringUtils.isEmpty(volume.getVolLetExt())) {
				volume.setVolLetExt(null);
			}

			// Retrieves every object references
			if (volume.getSerieList() != null) {
				volume.setSerieList(getSeriesListDAO().find(volume.getSerieList().getSeriesRefNum()));
			}

			//Setting fields that are defined as nullable = false
			volume.setResearcher(((DocSourcesLdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			volume.setDateCreated(new Date());
			volume.setVolTobeVetted(true);
			volume.setVolTobeVettedDate(new Date());
			volume.setVolVetted(false);
			volume.setBound(false);
			volume.setFolsNumbrd(false);
			volume.setOldAlphaIndex(false);
			volume.setPrintedDrawings(false);
			volume.setPrintedMaterial(false);
			volume.setItalian(false);
			volume.setSpanish(false);
			volume.setEnglish(false);
			volume.setLatin(false);
			volume.setGerman(false);
			volume.setFrench(false);
			volume.setCipher(false);

			if (volume.getStartMonthNum() != null) {
				Month month = getMonthDAO().find(volume.getStartMonthNum().getMonthNum());
				volume.setEndMonth(month.getMonthName());
				volume.setEndMonthNum(month);
			} else {
				volume.setStartMonth(null);
				volume.setStartMonthNum(null);
			}

			if (volume.getEndMonthNum() != null) {
				Month month = getMonthDAO().find(volume.getEndMonthNum().getMonthNum());
				volume.setEndMonth(month.getMonthName());
				volume.setEndMonthNum(month);
			} else {
				volume.setEndMonth(null);
				volume.setEndMonthNum(null);
			}

			volume.setStartDate(DateUtils.getLuceneDate(volume.getStartYear(), volume.getStartMonthNum(), volume.getStartDay()));
			volume.setEndDate(DateUtils.getLuceneDate(volume.getEndYear(), volume.getEndMonthNum(), volume.getEndDay()));
			volume.setLogicalDelete(Boolean.FALSE);
			volume.setDigitized(Boolean.FALSE);

			getVolumeDAO().persist(volume);
			
			getUserHistoryVolumeDAO().persist(new UserHistoryVolume("Create volume", Action.C, volume));
			
			return volume;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean checkVolumeDigitized(Integer summaryId) throws ApplicationThrowable {
		Boolean digitized = Boolean.FALSE;
		try {
			Volume volume = getVolumeDAO().find(summaryId);
			Image firstImage = getImageDAO().findVolumeFirstImage(volume.getVolNum(), volume.getVolLetExt());
			if (firstImage != null) {
				digitized = Boolean.TRUE;
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return digitized;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean checkVolumeDigitized(Integer volNum, String volLetExt) throws ApplicationThrowable {
		Boolean digitized = Boolean.FALSE;
		try {
			Image firstImage = getImageDAO().findVolumeFirstImage(volNum, volLetExt);
			if (firstImage != null) {
				digitized = Boolean.TRUE;
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return digitized;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume deleteVolume(Integer summaryId) throws ApplicationThrowable {
		Volume volumeToDelete = null;
		try {
			volumeToDelete = getVolumeDAO().find(summaryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		volumeToDelete.setLogicalDelete(Boolean.TRUE);

		try {
			getVolumeDAO().merge(volumeToDelete);

			getUserHistoryVolumeDAO().persist(new UserHistoryVolume("Delete volume", Action.D, volumeToDelete));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return volumeToDelete;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume editContextVolume(Volume volume) throws ApplicationThrowable {
		Volume volumeToUpdate = null;
		try {
			volumeToUpdate = getVolumeDAO().find(volume.getSummaryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		volumeToUpdate.setCcontext(volume.getCcontext());
		volumeToUpdate.setInventarioSommarioDescription(volume.getInventarioSommarioDescription());
		
		try {
			getVolumeDAO().merge(volumeToUpdate);

			getUserHistoryVolumeDAO().persist(new UserHistoryVolume("Edit context", Action.M, volumeToUpdate));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		return volumeToUpdate;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume editCorrespondentsVolume(Volume volume) throws ApplicationThrowable {
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

			getUserHistoryVolumeDAO().persist(new UserHistoryVolume("Edit correspondents", Action.M, volumeToUpdate));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		return volumeToUpdate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume editDescriptionVolume(Volume volume) throws ApplicationThrowable {
		Volume volumeToUpdate = null;
		try {
			volumeToUpdate = getVolumeDAO().find(volume.getSummaryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		volumeToUpdate.setOrgNotes(volume.getOrgNotes());
		volumeToUpdate.setCcondition(volume.getCcondition());
		volumeToUpdate.setBound(volume.getBound());
		volumeToUpdate.setFolioCount(volume.getFolioCount());
		volumeToUpdate.setFolsNumbrd(volume.getFolsNumbrd());
		volumeToUpdate.setOldAlphaIndex(volume.getOldAlphaIndex());
		volumeToUpdate.setPrintedMaterial(volume.getPrintedMaterial());
		volumeToUpdate.setPrintedDrawings(volume.getPrintedDrawings());
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

			getUserHistoryVolumeDAO().persist(new UserHistoryVolume("Edit description", Action.M, volumeToUpdate));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		return volumeToUpdate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume editDetailsVolume(Volume volume) throws ApplicationThrowable {
		Volume volumeToUpdate = null;
		try {
			volumeToUpdate = getVolumeDAO().find(volume.getSummaryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		if (volume.getSerieList() != null) {
			volumeToUpdate.setSerieList(getSeriesListDAO().find(volume.getSerieList().getSeriesRefNum()));
		} else {
			volumeToUpdate.setSerieList(null);
		}

		// Start date section
		volumeToUpdate.setStartYear(volume.getStartYear());
		if (volume.getStartMonthNum() != null) {
			Month month = getMonthDAO().find(volume.getStartMonthNum().getMonthNum());
			volumeToUpdate.setStartMonth(month.getMonthName());
			volumeToUpdate.setStartMonthNum(month);
		} else {
			volumeToUpdate.setStartMonth(null);
			volumeToUpdate.setStartMonthNum(null);
		}
		volumeToUpdate.setStartDay(volume.getStartDay());
		volumeToUpdate.setStartDate(DateUtils.getLuceneDate(volumeToUpdate.getStartYear(), volumeToUpdate.getStartMonthNum(), volumeToUpdate.getStartDay()));

		// End date section
		volumeToUpdate.setEndYear(volume.getEndYear());
		if (volume.getEndMonthNum() != null) {
			Month month = getMonthDAO().find(volume.getEndMonthNum().getMonthNum());
			volumeToUpdate.setEndMonth(month.getMonthName());
			volumeToUpdate.setEndMonthNum(month);
		} else {
			volumeToUpdate.setEndMonth(null);
			volumeToUpdate.setEndMonthNum(null);
		}
		volumeToUpdate.setEndDay(volume.getEndDay());
		volumeToUpdate.setEndDate(DateUtils.getLuceneDate(volumeToUpdate.getEndYear(), volumeToUpdate.getEndMonthNum(), volumeToUpdate.getEndDay()));

		volumeToUpdate.setDateNotes(volume.getDateNotes());
		
		try {
			getVolumeDAO().merge(volumeToUpdate);

			getUserHistoryVolumeDAO().persist(new UserHistoryVolume("Edit details", Action.M, volumeToUpdate));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		return volumeToUpdate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume findLastEntryVolume() throws ApplicationThrowable {
		try {
			UserHistoryVolume userHistoryVolume = getUserHistoryVolumeDAO().findLastEntryVolume();
			
			if (userHistoryVolume != null) {
				return userHistoryVolume.getVolume();
			}
			
			// in case of no user History we extract last volume created on database.
			return getVolumeDAO().findLastEntryVolume();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Integer> findNewDigitizedVolumes() throws ApplicationThrowable {
		try {
			return getImageDAO().findNewDigitizedVolumes();
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
			Volume volume = getVolumeDAO().find(summaryId);
			
			getUserHistoryVolumeDAO().persist(new UserHistoryVolume("Show volume", Action.V, volume));

			return volume;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume findVolume(Integer volNum, String volLetExt) throws ApplicationThrowable {
		try {
			Volume volume = getVolumeDAO().findVolume(volNum, volLetExt);
			
			getUserHistoryVolumeDAO().persist(new UserHistoryVolume("Show volume", Action.V, volume));

			return volume;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VolumeSummary findVolumeSummmary(Integer volNum, String volLetExt) throws ApplicationThrowable {
		try {
			VolumeSummary volumeSummary = new VolumeSummary();
			Volume volume = getVolumeDAO().findVolume(volNum, volLetExt);
			if (volume != null) {
				volumeSummary.setSummaryId(volume.getSummaryId());
				volumeSummary.setVolNum(volume.getVolNum());
				volumeSummary.setVolLetExt(volume.getVolLetExt());
				if(volume.getSerieList() != null){
					volumeSummary.setCarteggio(volume.getSerieList().toString());
				}
				FoliosInformations foliosInformations = getImageDAO().findVolumeFoliosInformations(volume.getVolNum(), volume.getVolLetExt());
				if (foliosInformations != null) {
					volumeSummary.setTotal(foliosInformations.getTotal());
					volumeSummary.setTotalRubricario(foliosInformations.getTotalRubricario());
					volumeSummary.setTotalCarta(foliosInformations.getTotalRubricario());
					volumeSummary.setTotalGuardia(foliosInformations.getTotalGuardia());
					volumeSummary.setTotalAppendix(foliosInformations.getTotalAppendix());
					volumeSummary.setTotalOther(foliosInformations.getTotalOther());
					volumeSummary.setMissingFolios(foliosInformations.getMissingFolios());
				}
				
				Catalog catalog = getCatalogDAO().findBySummaryId(volume.getSummaryId());
				if (catalog != null) {
					volumeSummary.setCartulazione(catalog.getCartulazione());
					volumeSummary.setNoteCartulazione(catalog.getNoteCartulazione());
					//volumeSummary.setHeight(catalog.getNumeroTotaleImmagini());
					//volumeSummary.setWidth(
				}
			}
			
			return volumeSummary;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexMonth() throws ApplicationThrowable {
		try {
			getMonthDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexSerieList() throws ApplicationThrowable {
		try {
			getSeriesListDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexVolume() throws ApplicationThrowable {
		try {
			getVolumeDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}
	
	/**
	 * @return the catalogDAO
	 */
	public CatalogDAO getCatalogDAO() {
		return catalogDAO;
	}
	
	/**
	 * @return the imageDAO
	 */
	public ImageDAO getImageDAO() {
		return imageDAO;
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
			List<Month> months = getMonthDAO().getAllMonths();
			
			months.add(0, new Month(null, ""));
			
			return months;
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
	 * @return the userHistoryVolumeDAO
	 */
	public UserHistoryVolumeDAO getUserHistoryVolumeDAO() {
		return userHistoryVolumeDAO;
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
	public VolumeExplorer getVolumeExplorer(VolumeExplorer volumeExplorer) throws ApplicationThrowable {
		try {
			if (volumeExplorer.getSummaryId() != null && volumeExplorer.getVolNum() == null) {
				if (volumeExplorer.getSummaryId() >0) {
					Volume volume = getVolumeDAO().find(volumeExplorer.getSummaryId());
					volumeExplorer.setVolNum(volume.getVolNum());
					volumeExplorer.setVolLetExt(volume.getVolLetExt());
				}
			} else if (volumeExplorer.getSummaryId() == null && volumeExplorer.getVolNum() != null) {
				Volume volume = getVolumeDAO().findVolume(volumeExplorer.getVolNum(), volumeExplorer.getVolLetExt());
				if (volume != null) {
					volumeExplorer.setSummaryId(volume.getSummaryId());
				}
			}
			return getImageDAO().findImages(volumeExplorer);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Boolean> getVolumesDigitizedState(List<Integer> volNums, List<String> volLetExts) throws ApplicationThrowable {
		Map<String, Boolean> retValue = new HashMap<String, Boolean>();
		try{
			// initialize return object with all volumes setted to false
			for (int i=0; i<volNums.size(); i++) {
				retValue.put(VolumeUtils.toMDPFormat(volNums.get(i), volLetExts.get(i)), Boolean.FALSE);
			}

			// One only query...
			List<String> volumesDigitized = getImageDAO().findVolumesDigitized(volNums, volLetExts);

			// we set to true only volumes which are present in table images...
			for (String MDP : volumesDigitized) {
				//  If the map previously contained a mapping for the key, the old value is replaced by the specified value.
				retValue.put(MDP, Boolean.TRUE);
			}

			return retValue;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void optimizeIndexVolume() throws ApplicationThrowable {
		// TODO Auto-generated method stub
		
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SerieList> searchSeriesList(String alias) throws ApplicationThrowable {
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
	public Page searchVolumes(String text, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getVolumeDAO().searchVolumes(text, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}


	/**
	 * @param catalogDAO the catalogDAO to set
	 */
	public void setCatalogDAO(CatalogDAO catalogDAO) {
		this.catalogDAO = catalogDAO;
	}


	/**
	 * @param imageDAO the imageDAO to set
	 */
	public void setImageDAO(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
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
	 * @param userHistoryVolumeDAO the userHistoryVolumeDAO to set
	 */
	public void setUserHistoryVolumeDAO(UserHistoryVolumeDAO userHistoryVolumeDAO) {
		this.userHistoryVolumeDAO = userHistoryVolumeDAO;
	}


	/**
	 * @param volumeDAO the volumeDAO to set
	 */
	public void setVolumeDAO(VolumeDAO volumeDAO) {
		this.volumeDAO = volumeDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume undeleteVolume(Integer summaryId) throws ApplicationThrowable {
		Volume volumeToUnDelete = null;
		try {
			volumeToUnDelete = getVolumeDAO().find(summaryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		volumeToUnDelete.setLogicalDelete(Boolean.FALSE);

		try {
			getVolumeDAO().merge(volumeToUnDelete);

			getUserHistoryVolumeDAO().persist(new UserHistoryVolume("Recovered volume", Action.M, volumeToUnDelete));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return volumeToUnDelete;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateIndexVolume(Date fromDate) throws ApplicationThrowable {
		try {
			getVolumeDAO().updateIndex(fromDate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer updateNewDigitizedVolume(List<Integer> summaryIds) throws ApplicationThrowable {
		try {
			return getVolumeDAO().updateNewDigitizedVolume(summaryIds);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

}
