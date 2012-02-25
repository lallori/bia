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

import java.util.List;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.Search;
import org.medici.docsources.dao.month.MonthDAO;
import org.medici.docsources.dao.schedone.SchedoneDAO;
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
 */
@Service
@Transactional(readOnly=true)
public class DigitizationServiceImpl implements DigitizationService {
	@Autowired
	private MonthDAO monthDAO;
	@Autowired
	private SchedoneDAO schedoneDAO;

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

}
