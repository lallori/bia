/*
 * PeopleBaseServiceImpl.java
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
package org.medici.docsources.service.peoplebase;

import java.util.List;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.altname.AltNameDAO;
import org.medici.docsources.dao.bibliot.BiblioTDAO;
import org.medici.docsources.dao.bioreflink.BioRefLinkDAO;
import org.medici.docsources.dao.eplink.EpLinkDAO;
import org.medici.docsources.dao.marriage.MarriageDAO;
import org.medici.docsources.dao.month.MonthDAO;
import org.medici.docsources.dao.people.PeopleDAO;
import org.medici.docsources.dao.polink.PoLinkDAO;
import org.medici.docsources.dao.rolecat.RoleCatDAO;
import org.medici.docsources.dao.titleoccslist.TitleOccsListDAO;
import org.medici.docsources.domain.AltName;
import org.medici.docsources.domain.Marriage;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.People.Gender;
import org.medici.docsources.domain.PoLink;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is the default implementation of service responsible for every 
 * action on people.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Service
public class PeopleBaseServiceImpl implements PeopleBaseService {
	@Autowired
	private AltNameDAO altNameDAO;
	
	@Autowired
	private BiblioTDAO biblioTDAO;
	
	@Autowired
	private BioRefLinkDAO bioRefLinkDAO;
	
	@Autowired 
	private EpLinkDAO epLinkDAO;
	
	@Autowired
	private MarriageDAO marriageDAO;

	@Autowired
	private MonthDAO monthDAO;

	@Autowired
	private PeopleDAO peopleDAO;
	
	@Autowired
	private PoLinkDAO poLinkDAO;
	
	@Autowired
	private RoleCatDAO roleCatDAO;
	
	@Autowired 
	private TitleOccsListDAO titleOccsListDAO;

	@Override
	public void addNewMarriagePerson(Marriage marriage) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFatherFromChildPerson(People child) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMotherFromChildPerson(People child) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editChildPerson(People child, Integer parentId) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editMarriagePerson(Marriage marriage) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AltName findAltNamePerson(Integer personId, Integer nameId) throws ApplicationThrowable {
		try {
			return getAltNameDAO().findAltNamePerson(personId, nameId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	@Override
	public People findChildPerson(Integer parentId, Integer childId) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<People> findChildrenPerson(Integer personId) throws ApplicationThrowable {
		try {
			People parent = getPeopleDAO().find(personId);
			
			return getPeopleDAO().findChildren(parent.getPersonId(), parent.getGender());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<People> findChildrenPerson(Integer personId, Gender gender) throws ApplicationThrowable {
		try {
			return getPeopleDAO().findChildren(personId, gender);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public People findLastEntryPerson() throws ApplicationThrowable {
		try {
			return getPeopleDAO().findLastEntryPerson();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	@Override
	public Marriage findMarriagePerson(Integer marriageId) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Marriage findMarriagePerson(Integer marriageId, Integer personId) throws ApplicationThrowable {
		try {
			People person = getPeopleDAO().find(personId);
			
			return getMarriageDAO().findMarriagePerson(marriageId, person.getPersonId(), person.getGender());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Marriage> findMarriagesPerson(Integer personId) throws ApplicationThrowable {
		try {
			People person = getPeopleDAO().find(personId);
			
			return getMarriageDAO().findMarriagesPerson(person.getPersonId(), person.getGender());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Marriage> findMarriagesPerson(Integer personId, Gender gender) throws ApplicationThrowable {
		try {
			return getMarriageDAO().findMarriagesPerson(personId, gender);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public People findPerson(Integer personId)  throws ApplicationThrowable {
		try {
			return getPeopleDAO().find(personId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	@Override
	public PoLink findTitleOrOccupationPerson(Integer personId, Integer prLinkId) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexAltName() throws ApplicationThrowable {
		try {
			getAltNameDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexBiblioT() throws ApplicationThrowable {
		try {
			getBiblioTDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexBioRefLink() throws ApplicationThrowable {
		try {
			getBioRefLinkDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexEpLink() throws ApplicationThrowable {
		try {
			getEpLinkDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexPeople() throws ApplicationThrowable {
		try {
			getPeopleDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexPoLink() throws ApplicationThrowable {
		try {
			getPoLinkDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexRoleCat() throws ApplicationThrowable {
		try {
			getRoleCatDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexTitleOccsList() throws ApplicationThrowable {
		try {
			getTitleOccsListDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * @return the altNameDAO
	 */
	public AltNameDAO getAltNameDAO() {
		return altNameDAO;
	}

	/**
	 * @return the biblioTDAO
	 */
	public BiblioTDAO getBiblioTDAO() {
		return biblioTDAO;
	}

	/**
	 * @return the bioRefLinkDAO
	 */
	public BioRefLinkDAO getBioRefLinkDAO() {
		return bioRefLinkDAO;
	}

	/**
	 * @return the epLinkDAO
	 */
	public EpLinkDAO getEpLinkDAO() {
		return epLinkDAO;
	}

	/**
	 * @return the marriageDAO
	 */
	public MarriageDAO getMarriageDAO() {
		return marriageDAO;
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
	 * @return the peopleDAO
	 */
	public PeopleDAO getPeopleDAO() {
		return peopleDAO;
	}

	/**
	 * @return the poLinkDAO
	 */
	public PoLinkDAO getPoLinkDAO() {
		return poLinkDAO;
	}

	/**
	 * @return the roleCatsDAO
	 */
	public RoleCatDAO getRoleCatDAO() {
		return roleCatDAO;
	}

	/**
	 * @return the titleOccsListDAO
	 */
	public TitleOccsListDAO getTitleOccsListDAO() {
		return titleOccsListDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchPeople(String text, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getPeopleDAO().searchPeople(text, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<People> searchRecipientsPeople(String query) throws ApplicationThrowable {
		try {
			return getPeopleDAO().searchRecipientsPeople(query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<People> searchSendersPeople(String query) throws ApplicationThrowable {
		try {
			return getPeopleDAO().searchSendersPeople(query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @param altNameDAO the altNameDAO to set
	 */
	public void setAltNameDAO(AltNameDAO altNameDAO) {
		this.altNameDAO = altNameDAO;
	}

	/**
	 * @param biblioTDAO the biblioTDAO to set
	 */
	public void setBiblioTDAO(BiblioTDAO biblioTDAO) {
		this.biblioTDAO = biblioTDAO;
	}

	/**
	 * @param bioRefLinkDAO the bioRefLinkDAO to set
	 */
	public void setBioRefLinkDAO(BioRefLinkDAO bioRefLinkDAO) {
		this.bioRefLinkDAO = bioRefLinkDAO;
	}

	/**
	 * @param epLinkDAO the epLinkDAO to set
	 */
	public void setEpLinkDAO(EpLinkDAO epLinkDAO) {
		this.epLinkDAO = epLinkDAO;
	}

	public void setMarriageDAO(MarriageDAO marriageDAO) {
		this.marriageDAO = marriageDAO;
	}

	/**
	 * @param monthDAO the monthDAO to set
	 */
	public void setMonthDAO(MonthDAO monthDAO) {
		this.monthDAO = monthDAO;
	}

	/**
	 * @param peopleDAO the peopleDAO to set
	 */
	public void setPeopleDAO(PeopleDAO peopleDAO) throws ApplicationThrowable {
		this.peopleDAO = peopleDAO;
	}

	/**
	 * @param poLinkDAO the poLinkDAO to set
	 */
	public void setPoLinkDAO(PoLinkDAO poLinkDAO) {
		this.poLinkDAO = poLinkDAO;
	}

	/**
	 * @param roleCatDAO the roleCatDAO to set
	 */
	public void setRoleCatDAO(RoleCatDAO roleCatDAO) {
		this.roleCatDAO = roleCatDAO;
	}

	/**
	 * @param titleOccsListDAO the titleOccsListDAO to set
	 */
	public void setTitleOccsListDAO(TitleOccsListDAO titleOccsListDAO) {
		this.titleOccsListDAO = titleOccsListDAO;
	}

}
