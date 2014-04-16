/*
 * DigitizationServiceImpl.java
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
package org.medici.bia.service.digitization;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.VolumeUtils;
import org.medici.bia.dao.digitization.DigitizationDAO;
import org.medici.bia.dao.image.ImageDAO;
import org.medici.bia.dao.month.MonthDAO;
import org.medici.bia.dao.schedone.SchedoneDAO;
import org.medici.bia.dao.serieslist.SeriesListDAO;
import org.medici.bia.dao.volume.VolumeDAO;
import org.medici.bia.domain.Digitization;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.Schedone;
import org.medici.bia.domain.SerieList;
import org.medici.bia.domain.Volume;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.security.BiaUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Service
@Transactional(readOnly=true)
public class DigitizationServiceImpl implements DigitizationService {
	@Autowired
	private DigitizationDAO digitizationDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private MonthDAO monthDAO;
	@Autowired
	private SchedoneDAO schedoneDAO;
	@Autowired
	private SeriesListDAO seriesListDAO;
	@Autowired
	private VolumeDAO volumeDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Digitization activationOrDeactivationVolume(Digitization digitization) throws ApplicationThrowable {
		try{
			getDigitizationDAO().merge(digitization);
			return digitization;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Schedone addNewSchedone(Schedone schedone) throws ApplicationThrowable {
		try {
			schedone.setSchedoneId(null);
			
			if(schedone.getSerie() != null){
				schedone.setSerie(getSeriesListDAO().find(schedone.getSerie().getSeriesRefNum()));
			}
			
			//TODO: Create volume record if not entered (BETA)
			
			Volume volume = getVolumeDAO().findVolume(schedone.getVolNum(), schedone.getVolLetExt());
			if(volume == null){
				volume = new Volume();
				volume.setSummaryId(null);
				volume.setVolNum(schedone.getVolNum());
				volume.setVolLetExt(schedone.getVolLetExt());
				if(schedone.getSerie() != null){
					volume.setSerieList(getSeriesListDAO().find(schedone.getSerie().getSeriesRefNum()));
				}
				
				volume.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
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
				
				volume.setStartYear(schedone.getDataInizioAnno());
				if(schedone.getDataInizioMese() != null){
					Month month = getMonthDAO().find(schedone.getDataInizioMese().getMonthNum());
					volume.setStartMonth(month.getMonthName());
					volume.setStartMonthNum(month);
				}else{
					volume.setStartMonth(null);
					volume.setStartMonthNum(null);
				}
				volume.setStartDay(schedone.getDataInizioGiorno());
				volume.setEndYear(schedone.getDataFineAnno());
				if(schedone.getDataFineMese() != null){
					Month month = getMonthDAO().find(schedone.getDataFineMese().getMonthNum());
					volume.setEndMonth(month.getMonthName());
					volume.setEndMonthNum(month);
				}else{
					volume.setEndMonth(null);
					volume.setEndMonthNum(null);
				}
				volume.setEndDay(schedone.getDataFineGiorno());
				volume.setStartDate(DateUtils.getLuceneDate(volume.getStartYear(), volume.getStartMonthNum(), volume.getStartDay()));
				volume.setEndDate(DateUtils.getLuceneDate(volume.getEndYear(), volume.getEndMonthNum(), volume.getEndDay()));
				volume.setLogicalDelete(Boolean.FALSE);
				volume.setDigitized(Boolean.FALSE);
				
				getVolumeDAO().persist(volume);
				
			}
			
			getSchedoneDAO().persist(schedone);
			
			return schedone;
		}catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Schedone editDetailsSchedone(Schedone schedone) throws ApplicationThrowable {
		try {
			Schedone schedoneToUpdate = getSchedoneDAO().find(schedone.getSchedoneId());
			// we update only schedone fields
			
			schedoneToUpdate.setIstituto(schedone.getIstituto());
			schedoneToUpdate.setFondo(schedone.getFondo());
			
			if(schedone.getSerie() != null){
				schedoneToUpdate.setSerie(getSeriesListDAO().find(schedone.getSerie().getSeriesRefNum()));
			}else{
				schedoneToUpdate.setSerie(null);
			}
			
			
			schedoneToUpdate.setNumeroUnita(schedone.getNumeroUnita());
			schedoneToUpdate.setVolNum(schedone.getVolNum());
			schedoneToUpdate.setVolLetExt(schedone.getVolLetExt());
			
			schedoneToUpdate.setDataInizioAnno(schedone.getDataInizioAnno());

			if(schedone.getDataInizioMese() != null){
				Month month = getMonthDAO().find(schedone.getDataInizioMese().getMonthNum());
				schedoneToUpdate.setDataInizioMese(month);
			}else{
				schedoneToUpdate.setDataInizioMese(null);
			}
			schedoneToUpdate.setDataInizioGiorno(schedone.getDataInizioGiorno());
			schedoneToUpdate.setDataFineAnno(schedone.getDataFineAnno());

			if(schedone.getDataFineMese() != null){
				Month month = getMonthDAO().find(schedone.getDataFineMese().getMonthNum());
				schedoneToUpdate.setDataFineMese(month);
			}else{
				schedoneToUpdate.setDataFineMese(null);
			}
			
			schedoneToUpdate.setDataFineGiorno(schedone.getDataFineGiorno());
			schedoneToUpdate.setDescrizioneContenuto(schedone.getDescrizioneContenuto());
			schedoneToUpdate.setDescrizioneContenutoEng(schedone.getDescrizioneContenutoEng());
			schedoneToUpdate.setLegatura(schedone.getLegatura());
			schedoneToUpdate.setSupporto(schedone.getSupporto());
			schedoneToUpdate.setCartulazione(schedone.getCartulazione());
			schedoneToUpdate.setNoteCartulazione(schedone.getNoteCartulazione());
			schedoneToUpdate.setNoteCartulazioneEng(schedone.getNoteCartulazioneEng());
			schedoneToUpdate.setCarteBianche(schedone.getCarteBianche());
			schedoneToUpdate.setCarteMancanti(schedone.getCarteMancanti());
			schedoneToUpdate.setDimensioniBase(schedone.getDimensioniBase());
			schedoneToUpdate.setDimensioniAltezza(schedone.getDimensioniAltezza());
			schedoneToUpdate.setTipoRipresa(schedone.getTipoRipresa());
			schedoneToUpdate.setColoreImmagine(schedone.getColoreImmagine());
			schedoneToUpdate.setRisoluzione(schedone.getRisoluzione());
			schedoneToUpdate.setNomeFiles(schedone.getNomeFiles());
			schedoneToUpdate.setResponsabileFotoRiproduzione(schedone.getResponsabileFotoRiproduzione());
			schedoneToUpdate.setDataRipresaAnno(schedone.getDataRipresaAnno());

			if(schedone.getDataRipresaMese() != null){
				Month month = getMonthDAO().find(schedone.getDataRipresaMese().getMonthNum());
				schedoneToUpdate.setDataRipresaMese(month);
			}else{
				schedoneToUpdate.setDataRipresaMese(null);
			}
			schedoneToUpdate.setDataRipresaGiorno(schedone.getDataRipresaGiorno());
			schedoneToUpdate.setOperatore(schedone.getOperatore());
			
			getSchedoneDAO().merge(schedoneToUpdate);
			
			return schedoneToUpdate;
		}catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Schedone editJpegImagesSchedone(Schedone schedone) throws ApplicationThrowable {
		try {
			Schedone schedoneToUpdate = getSchedoneDAO().find(schedone.getSchedoneId());
			// we update only jpeg images fields
			schedoneToUpdate.setDimMediaImmaginiJpeg(schedone.getDimMediaImmaginiJpeg());
			schedoneToUpdate.setDimTotaleImmaginiJpeg(schedone.getDimTotaleImmaginiJpeg());
			schedoneToUpdate.setNumeroTotaleImmaginiJpeg(schedone.getNumeroTotaleImmaginiJpeg());
			schedoneToUpdate.setCompressioneJpeg(schedone.getCompressioneJpeg());
			schedoneToUpdate.setFormatoMediaImmaginiJpeg(schedone.getFormatoMediaImmaginiJpeg());
			schedoneToUpdate.setFormatoTotaleImmaginiJpeg(schedone.getFormatoTotaleImmaginiJpeg());
			
			getSchedoneDAO().merge(schedoneToUpdate);
			
			return schedoneToUpdate;
		}catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Schedone editPdfImagesSchedone(Schedone schedone) throws ApplicationThrowable {
		try {
			Schedone schedoneToUpdate = getSchedoneDAO().find(schedone.getSchedoneId());
			// we update only pdf images fields
			schedoneToUpdate.setDimMediaImmaginiPdf(schedone.getDimMediaImmaginiPdf());
			schedoneToUpdate.setDimTotaleImmaginiPdf(schedone.getDimTotaleImmaginiPdf());
			schedoneToUpdate.setNumeroTotaleImmaginiPdf(schedone.getNumeroTotaleImmaginiPdf());
			schedoneToUpdate.setCompressionePdf(schedone.getCompressionePdf());
			schedoneToUpdate.setFormatoMediaImmaginiPdf(schedone.getFormatoMediaImmaginiPdf());
			schedoneToUpdate.setFormatoTotaleImmaginiPdf(schedone.getFormatoTotaleImmaginiPdf());
			
			getSchedoneDAO().merge(schedoneToUpdate);
			
			return schedoneToUpdate;
		}catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Schedone editTiffImagesSchedone(Schedone schedone) throws ApplicationThrowable {
		try {
			Schedone schedoneToUpdate = getSchedoneDAO().find(schedone.getSchedoneId());
			// we update only tiff images fields
			schedoneToUpdate.setDimMediaImmaginiTiff(schedone.getDimMediaImmaginiTiff());
			schedoneToUpdate.setDimTotaleImmaginiTiff(schedone.getDimTotaleImmaginiTiff());
			schedoneToUpdate.setNumeroTotaleImmaginiTiff(schedone.getNumeroTotaleImmaginiTiff());
			schedoneToUpdate.setCompressioneTiff(schedone.getCompressioneTiff());
			schedoneToUpdate.setFormatoMediaImmaginiTiff(schedone.getFormatoMediaImmaginiTiff());
			schedoneToUpdate.setFormatoTotaleImmaginiTiff(schedone.getFormatoTotaleImmaginiTiff());
			
			getSchedoneDAO().merge(schedoneToUpdate);
			
			return schedoneToUpdate;
		}catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Digitization findActivateVolumeDigitized(Integer id) throws ApplicationThrowable {
		try{
			Digitization digitization = getDigitizationDAO().find(id);
			return digitization;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Schedone findSchedone(Integer schedoneId) throws ApplicationThrowable {
		try {
			Schedone schedone = getSchedoneDAO().find(schedoneId);
			
			return schedone;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Schedone findSchedone(Integer volNum, String volLetExt) throws ApplicationThrowable {
		try {
			Schedone schedone = getSchedoneDAO().findByVolume(volNum, volLetExt);
			
			return schedone;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Schedone> findSchedoniMapByVolume(Integer volNum, Integer volNumBetween) throws ApplicationThrowable {
		try{
			Map<String, Schedone> result = new HashMap<String, Schedone>();
//			for(int i = volNum; i <= volNumBetween; i++){
//				result.put(i, new Schedone(0));
//			}
			List<Schedone> resultList = getSchedoneDAO().findByVolumesNumber(volNum, volNumBetween);
			if(resultList != null){
				for(Schedone currentSchedone : resultList){
					if(currentSchedone.getVolLetExt() != null)
						result.put(currentSchedone.getVolNum() + currentSchedone.getVolLetExt(), currentSchedone);
					else
						result.put(currentSchedone.getVolNum().toString(), currentSchedone);
				}
			}
			return result;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Boolean> findVolumesDigitizedBySchedoni(List<Integer> volNums, List<String> volLetExts) throws ApplicationThrowable {
		try{
			Map<String, Boolean> result = new HashMap<String, Boolean>();
			for(int i = 0; i < volNums.size(); i++){
				result.put(VolumeUtils.toMDPFormat(volNums.get(i), volLetExts.get(i)), Boolean.FALSE);
			}
			List<String> resultList = getImageDAO().findVolumesDigitized(volNums, volLetExts);
			
			for(String MDP : resultList){
				result.put(MDP, Boolean.TRUE);
			}
			return result;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the digitizationDAO
	 */
	public DigitizationDAO getDigitizationDAO() {
		return digitizationDAO;
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
	 * 
	 */
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
	 * @return the schedoneDAO
	 */
	public SchedoneDAO getSchedoneDAO() {
		return schedoneDAO;
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
	public Page searchActiveVolumes(Integer volNum, Integer volNumBetween, Boolean activated, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getDigitizationDAO().searchActiveVolumes(volNum, volNumBetween, activated, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchAllActiveVolumes(Boolean activated, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getDigitizationDAO().searchAllActiveVolumes(activated, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchSchedones(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getSchedoneDAO().searchMYSQL(searchContainer, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SerieList> searchSeriesList(String alias) throws ApplicationThrowable {
		try{
			return getSeriesListDAO().findSeries(alias);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume searchVolume(Integer volNum, String volLetExt) throws ApplicationThrowable {
		try{
			return getVolumeDAO().findVolume(volNum, volLetExt);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchVolumes(Integer volNum, Integer volNumBetween, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getVolumeDAO().searchVolumesByDigitization(volNum, volNumBetween, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @param digitizationDAO the digitizationDAO to set
	 */
	public void setDigitizationDAO(DigitizationDAO digitizationDAO) {
		this.digitizationDAO = digitizationDAO;
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
	 * @param schedoneDAO the schedoneDAO to set
	 */
	public void setSchedoneDAO(SchedoneDAO schedoneDAO) {
		this.schedoneDAO = schedoneDAO;
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
