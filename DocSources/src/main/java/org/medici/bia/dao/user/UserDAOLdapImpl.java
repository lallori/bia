/*
 * UserDAOLdapImpl.java
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
package org.medici.bia.dao.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.naming.Name;

import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.util.LdapUtils;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserRole;
import org.medici.bia.exception.TooManyUsersException;
import org.medici.bia.security.LdapConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;
import org.springframework.security.authentication.encoding.PasswordEncoder;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class UserDAOLdapImpl implements UserDAO {
	/**
	 * This class is DocSource's implementation to map LDAP Attribute in User
	 * Model Object.
	 * 
	 * @author Lorenzo Pasquinelli (<a
	 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 * 
	 */
	private static class UserContextMapper extends AbstractContextMapper {
		private static Logger logger = Logger.getLogger(UserContextMapper.class);

		@Override
		public Object doMapFromContext(DirContextOperations context) {
			
			User user = new User();
			user.setAccount(context.getStringAttribute("cn"));
			try {
				user.setPassword(new String((byte[]) context.getObjectAttribute("userPassword"), "US-ASCII"));
			} catch (UnsupportedEncodingException unsupportedEncodingException) {
				logger.error(unsupportedEncodingException);
			}

			user.setFirstName(context.getStringAttribute("givenName"));
			user.setLastName(context.getStringAttribute("surname"));
			user.setOrganization(context.getStringAttribute("ou"));
			user.setAddress(context.getStringAttribute("street"));
			user.setCity(context.getStringAttribute("l"));
			user.setCountry(context.getStringAttribute("c"));
			user.setInitials(context.getStringAttribute("initials"));
			user.setMail(context.getStringAttribute("mail"));
			user.setTitle(context.getStringAttribute("personalTitle"));
			user.setInterests(context.getStringAttribute("info"));
			
			InputStream inputStream = null;
			try {
				if (context.getObjectAttribute("photo") != null) {
					inputStream = new ByteArrayInputStream((byte[]) context.getObjectAttribute("photo"));
	
					user.setPhoto(ImageIO.read(inputStream));
				}
			} catch (IOException ioException) {
				logger.debug(ioException);
				user.setPhoto(null);
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch(IOException ioException2) {
						logger.debug(ioException2);
					}
				}
			}

			Set<UserRole> userRoles = getUserRoles(context, "member");
			user.setUserRoles(userRoles);
			return user;
		}

		/**
		 * @param context
		 * @param attributeName
		 * @return
		 */
		private Set<UserRole> getUserRoles(DirContextOperations context, String attributeName) {
			String[] userRolesAttribute = context.getStringAttributes(attributeName);
			Set<UserRole> userRoles = new HashSet<UserRole>();
			
			if (userRolesAttribute != null) {
				for(String role : userRolesAttribute) {
					try {
						UserRole userRole = new UserRole();
						userRole.setUserAuthority(new UserAuthority(UserAuthority.Authority.valueOf(LdapUtils.getStringRole(role))));
						userRoles.add(userRole);
					} catch(IllegalArgumentException illegalArgumentException){
						logger.error(illegalArgumentException);
						// if the specified enum type has no constant with the 
						// specified name, or the specified class object does not 
						// represent an enum type. 
					} catch (NullPointerException nullPointerException){
						logger.error(nullPointerException);
						// if enum name is null
					}
				}
			}
			return userRoles;
		}
	}
	/**
	 * This class makes a mapping between context attribute cn and Authority
	 * enum. It's used to istantiate UserRoles generated from the subTree
	 * containing application group policy.
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 * 
	 */
	private static class UserRoleContextMapper extends AbstractContextMapper {
		@Override
		public Object doMapFromContext(DirContextOperations context) {
			return UserAuthority.Authority.valueOf(context.getStringAttribute("cn"));
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 135533278179686049L;

	@Autowired
	private LdapConfiguration ldapConfiguration;

	@Autowired
	private LdapTemplate ldapTemplate;

	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	@Override
	public Long countMembersForum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page findForumMembers(String letter, PaginationFilter paginationFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method find an unique user searching on ldap server.
	 * 
	 * @param account
	 */
	@Override
	public User findUser(String account) {
		try {
			Name dn = LdapUtils.userDistinguishedName(getLdapConfiguration(), account);
	
			return (User) getLdapTemplate().lookup(dn, getUserContextMapper());
		} catch (NameNotFoundException nameNotFoundException) {
			return null;
		}
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public User findUser(User user) throws TooManyUsersException {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "person"));
		OrFilter inputfilter = new OrFilter();
		if (user.getAccount() != null) {
			inputfilter.or(new LikeFilter("cn", user.getAccount()));
		}
		if (user.getInitials() != null) {
			inputfilter.or(new LikeFilter("initials", user.getLastName()));
		}
		if (user.getFirstName() != null) {
			inputfilter.or(new LikeFilter("givenName", user.getFirstName()));
		}
		if (user.getLastName() != null) {
			inputfilter.or(new LikeFilter("surname", user.getLastName()));
		}
		if (user.getOrganization() != null) {
			inputfilter.or(new LikeFilter("ou", user.getOrganization()));
		}
		if (user.getMail() != null) {
			inputfilter.or(new LikeFilter("mail", user.getMail()));
		}

		if (inputfilter.encode().length() > 0) {
			filter.and(inputfilter);
		}
		
		List users = getLdapTemplate().search(DistinguishedName.EMPTY_PATH,filter.encode(), getUserContextMapper());

		if (users.size() > 1) {
			throw new TooManyUsersException();
		} else if (users.size() == 0) {
			return null;
		}

		return (User) users.get(0);
	}

	/**
	 * 
	 * @param userRole
	 * @return
	 */
	public UserRole findUserRole(UserRole userRole) {
		Name dn = LdapUtils.userRoleDistinguishedName(getLdapConfiguration(), userRole.getUserAuthority());

		try {
			return (UserRole) getLdapTemplate().lookup(dn, getUserRoleContextMapper());
		} catch (NameNotFoundException nameNotFoundException) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findUsers(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Search for all users matching the supplied filter on following fields : -
	 * account - first name - last name - organization - mail address
	 * 
	 * The Attributes in each SearchResult is supplied to the specified
	 * AttributesMapper.
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> findUsers(User user) {
		AndFilter filter = getSearchFilter(user);
		List users = null;
		try {
			users = getLdapTemplate().search(DistinguishedName.EMPTY_PATH,filter.encode(), getUserContextMapper());
		} catch (Throwable th) {
			logger.debug(th);
		}
		return users;
	}

	/**
	 * 
	 */
	@Override
	public Page findUsers(User user, Integer pageNumber, Integer pageSize) {
		AndFilter filter = getSearchFilter(user);
		
		return new Page(getLdapTemplate().search(DistinguishedName.EMPTY_PATH,  filter.encode(), getUserContextMapper()), pageNumber, pageSize);
	}

	@Override
	public Page findUsers(User user, PaginationFilter paginationFilter) {
		AndFilter filter = getSearchFilter(user);
		List<User> users = null;
		try {
			users = getLdapTemplate().search(DistinguishedName.EMPTY_PATH, filter.encode(), getUserContextMapper());
		} catch (Throwable th) {
			logger.error(th);
			users = new ArrayList<User>(0);
		}

		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		// We set size of result.
		if (paginationFilter.getTotal() == null) {
			page.setTotal(new Long(Integer.valueOf(users.size())));
		}

		if (page.getTotal() >0) {
			if(paginationFilter.getLength() < users.size()){
				page.setList(users.subList(paginationFilter.getFirstRecord(), paginationFilter.getLength()));
			}else{
				page.setList(users.subList(paginationFilter.getFirstRecord(), users.size()));
			}
		} else{
			page.setList(new ArrayList<User>(0));
		}
		
		return page;
	}

	/**
	 * @return the ldapConfiguration
	 */
	public LdapConfiguration getLdapConfiguration() {
		return ldapConfiguration;
	}

	/**
	 * 
	 * @return
	 */
	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	@Override
	public User getNewestMember() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the passwordEncoder
	 */
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	private AndFilter getSearchFilter(User user) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "person"));
		OrFilter orFilter = new OrFilter();
		if (user.getAccount() != null && !user.getAccount().equals("")) {
			orFilter.or(new WhitespaceWildcardsFilter("cn", user.getAccount()));
		}
		if (user.getFirstName() != null && !user.getFirstName().equals("")) {
			orFilter.or(new WhitespaceWildcardsFilter("givenName", user.getFirstName()));
		}
		if (user.getLastName() != null && !user.getLastName().equals("")) {
			orFilter.or(new WhitespaceWildcardsFilter("surname", user.getLastName()));
		}
		if (user.getOrganization() != null && !user.getOrganization().equals("")) {
			orFilter.or(new WhitespaceWildcardsFilter("ou", user.getOrganization()));
		}
		if (user.getMail() != null && !user.getMail().equals("")) {
			orFilter.or(new WhitespaceWildcardsFilter("mail", user.getMail()));
		}
		filter.and(orFilter);
		return filter;
	}

	/**
	 * 
	 * @return
	 */
	protected ContextMapper getUserContextMapper() {
		return new UserContextMapper();
	}

	/**
	 * 
	 * @return
	 */
	protected ContextMapper getUserRoleContextMapper() {
		return new UserRoleContextMapper();
	}

	/**
	 * {@inheritDoc}
	 */
	protected void mapUserRoleToContext(UserRole userRole, DirContextOperations context) {
		context.setAttributeValues("objectclass", new String[] { "top", "groupOfNames" });
	}

	/**
	 * This method will map user bean property to corresponding corresponding
	 * entry on LDAP only if user bean property is not null.
	 * 
	 * @param user Input user to map on context
	 * @param context Context that receives the data from user
	 */
	protected void mapUserToContext(User user, DirContextOperations context) {
		if (user.getPassword() != null) {
			if (!user.getPassword().startsWith("{SHA}")) {
				context.setAttributeValue("userPassword", passwordEncoder.encodePassword(user.getPassword(), null));
			}
		}
		if (user.getFirstName() != null) {
			if (!user.getFirstName().equals("")){ 
				context.setAttributeValue("givenName", user.getFirstName());
			}
		}
		if (user.getLastName() != null) {
			if (!user.getLastName().equals("")){ 
				context.setAttributeValue("surname", user.getLastName());
			}
		}
		if (user.getOrganization() != null) {
			if (!user.getOrganization().equals("")) { 
				context.setAttributeValue("ou", user.getOrganization());
			}
		}
		if (user.getAddress() != null) {
			if (!user.getAddress().equals("")) {
				context.setAttributeValue("street", user.getAddress());
			}
		}
		if (user.getCity() != null) {
			if (!user.getCity().equals("")) {
				context.setAttributeValue("l", user.getCity());
			}
		}
		if (user.getCountry() != null) {
			if (!user.getCountry().equals("")) {
				context.setAttributeValue("c", user.getCountry());
			}
		}
		if (user.getMail() != null){
			if (!user.getMail().equals("")) {
				context.setAttributeValue("mail", user.getMail());
			}
		}
		if (user.getTitle() != null) {
			if (!user.getTitle().equals("")) {
				context.setAttributeValue("personalTitle", user.getTitle());
			}
		}
		
		if (user.getInterests() != null) {
			if (!user.getInterests().equals("")) {
				context.setAttributeValue("info", user.getInterests());
			}
		}

		if (context.getObjectAttribute("photo") != null)  {
			context.removeAttributeValue("photo;binary", context.getObjectAttribute("photo"));
		}
		
		if (user.getPhoto() != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream( 1000 );
			
			try {
				
				ImageIO.write( user.getPhoto(), "jpeg", baos );
				baos.flush();
				context.setAttributeValue("photo;binary", baos.toByteArray());
			} catch (IOException ioException) {
				logger.error(ioException);
			} finally {
				if (baos != null) {
					try {
						baos.close();
					}catch (IOException ioException2) {
						logger.error(ioException2);
					}
				}
			}
		}
		
		if (user.getUserRoles() != null) {
			for (UserRole singleRole : user.getUserRoles()) {
				context.addAttributeValue("member", "cn=" + singleRole);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User merge(User user) {
		DirContextOperations context = getLdapTemplate().lookupContext(LdapUtils.userDistinguishedName(getLdapConfiguration(), user.getAccount()));
		mapUserToContext(user, context);
		getLdapTemplate().modifyAttributes(context);
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void persist(User user) {
		DirContextAdapter context = new DirContextAdapter();
		context.setAttributeValues("objectclass", new String[] { "top", "extensibleObject", "krb5Principal", "person", "organizationalPerson" });
		context.setAttributeValue("cn", user.getAccount());
		context.setAttributeValue("krb5PrincipalName", user.getAccount());
		mapUserToContext(user, context);
		getLdapTemplate().bind(LdapUtils.userDistinguishedName(getLdapConfiguration(), user.getAccount()), context, null);
	}

	/**
	 * 
	 * @param account
	 * @param userRole
	 */
	public void persistUserRoles(String account, List<UserRole> userRoles) {
		if ((account == null) || (userRoles == null)) {
			return;
		}
		
		DirContextOperations context = getLdapTemplate().lookupContext(LdapUtils.userDistinguishedName(getLdapConfiguration(), account));

		for (UserRole singleRole : userRoles) {
			context.setAttributeValue("member", LdapUtils.fullUserRoleDistinguishedName(getLdapConfiguration(), singleRole.toString()));
		}
	}

	/**
	 * @param user
	 */
	public void remove(User user) {
		DirContextAdapter context = new DirContextAdapter();
		mapUserToContext(user, context);
		getLdapTemplate().unbind(LdapUtils.userDistinguishedName(getLdapConfiguration(), user.getAccount()));
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeAllUserRoles(String account) {
		if (account == null) {
			return;
		}

		DirContextOperations context = getLdapTemplate().lookupContext(LdapUtils.userDistinguishedName(getLdapConfiguration(), account));
		
		User user = findUser(account);
		mapUserToContext(user, context);
		getLdapTemplate().modifyAttributes(context);
		
		
		for (UserRole singleRole : user.getUserRoles()) {
			context.removeAttributeValue("member", "cn=" + singleRole);
		}
		getLdapTemplate().modifyAttributes(context);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeUserRoles(String account, List<UserRole> userRoles) {
		if ((account == null) || (userRoles == null)) {
			return;
		}

		DirContextOperations context = getLdapTemplate().lookupContext(LdapUtils.userDistinguishedName(getLdapConfiguration(), account));
		
		for (UserRole singleRole : userRoles) {
			context.removeAttributeValue("member", LdapUtils.fullUserRoleDistinguishedName(getLdapConfiguration(), singleRole.toString()));
		}
	}

	/**
	 * @param ldapConfiguration
	 *            the ldapConfiguration to set
	 */
	public void setLdapConfiguration(LdapConfiguration ldapConfiguration) {
		this.ldapConfiguration = ldapConfiguration;
	}

	/**
	 * 
	 * @param ldapTemplate
	 */
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	/**
	 * @param passwordEncoder the passwordEncoder to set
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
