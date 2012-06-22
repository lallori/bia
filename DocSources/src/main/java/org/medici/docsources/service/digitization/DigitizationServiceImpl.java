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
package org.medici.docsources.service.digitization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.Search;
import org.medici.docsources.common.util.VolumeUtils;
import org.medici.docsources.dao.image.ImageDAO;
import org.medici.docsources.dao.month.MonthDAO;
import org.medici.docsources.dao.schedone.SchedoneDAO;
import org.medici.docsources.dao.volume.VolumeDAO;
import org.medici.docsources.domain.Schedone;
import org.medici.docsources.domain.Month;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
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
	private ImageDAO imageDAO;
	@Autowired
	private MonthDAO monthDAO;
	@Autowired
	private SchedoneDAO schedoneDAO;
	@Autowired
	private VolumeDAO volumeDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Schedone addNewSchedone(Schedone schedone) throws ApplicationThrowable {
		try {
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
			schedoneToUpdate.setSerie(schedone.getSerie());
			schedoneToUpdate.setNumeroUnita(schedone.getNumeroUnita());
			schedoneToUpdate.setDataInizioAnno(schedone.getDataInizioAnno());
			schedoneToUpdate.setDataInizioMese((schedone.getDataInizioMese() != null) ? schedone.getDataInizioMese() : null);
			schedoneToUpdate.setDataInizioGiorno(schedone.getDataInizioGiorno());
			schedoneToUpdate.setDataFineAnno(schedone.getDataFineAnno());
			schedoneToUpdate.setDataFineMese((schedone.getDataFineMese() != null) ? schedone.getDataFineMese() : null);
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
			schedoneToUpdate.setDataRipresaMese((schedone.getDataRipresaMese() != null) ? schedone.getDataRipresaMese() : null);
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
			// we update only jpeg images fields
			
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
	public Map<Integer, Boolean> findSchedoniMapByVolume(Integer volNum, Integer volNumBetween) throws ApplicationThrowable {
		try{
			Map<Integer, Boolean> result = new HashMap<Integer, Boolean>();
			for(int i = volNum; i <= volNumBetween; i++){
				result.put(i, Boolean.FALSE);
			}
			List<Schedone> resultList = getSchedoneDAO().findByVolumesNumber(volNum, volNumBetween);
			if(resultList != null){
				for(Schedone currentSchedone : resultList){
					result.put(currentSchedone.getVolNum(), Boolean.TRUE);
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
	 * @return the volumeDAO
	 */
	public VolumeDAO getVolumeDAO() {
		return volumeDAO;
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
	public Page searchVolumes(Integer volNum, Integer volNumBetween, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getVolumeDAO().searchVolumesByDigitization(volNum, volNumBetween, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
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
	 * @param volumeDAO the volumeDAO to set
	 */
	public void setVolumeDAO(VolumeDAO volumeDAO) {
		this.volumeDAO = volumeDAO;
	}

}
