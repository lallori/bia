/*
 * UserDaoLdapImpl.java
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
package org.medici.docsources.dao.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.naming.Name;

import org.medici.docsources.common.ajax.Page;
import org.medici.docsources.common.util.LdapUtils;
import org.medici.docsources.domain.User;
import org.medici.docsources.domain.User.UserRole;
import org.medici.docsources.exception.TooManyUsersException;
import org.medici.docsources.security.LdapConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Repository
public class UserDaoLdapImpl implements UserDAO {
	/**
	 * This class is DocSource's implementation to map LDAP Attribute in User
	 * Model Object.
	 * 
	 * @author Lorenzo Pasquinelli (<a
	 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 * 
	 */
	private static class UserContextMapper extends AbstractContextMapper {
		@Override
		public Object doMapFromContext(DirContextOperations context) {
			
			User user = new User();
			user.setAccount(context.getStringAttribute("cn"));
			try {
				user.setPassword(new String((byte[]) context.getObjectAttribute("userPassword"), "US-ASCII"));
			} catch (UnsupportedEncodingException ueex) {
				
			}
			user.setFirstName(context.getStringAttribute("givenName"));
			user.setLastName(context.getStringAttribute("surname"));
			user.setOrganization(context.getStringAttribute("ou"));
			user.setAddress(context.getStringAttribute("street"));
			user.setCity(context.getStringAttribute("l"));
			user.setCountry(context.getStringAttribute("c"));
			user.setMail(context.getStringAttribute("mail"));
			user.setTitle(context.getStringAttribute("personalTitle"));
			user.setInterests(context.getStringAttribute("info"));

			try {
				if (context.getObjectAttribute("photo") != null) {
					InputStream in = new ByteArrayInputStream((byte[]) context.getObjectAttribute("photo"));
	
					user.setPhoto(ImageIO.read(in));
				}
			} catch (IOException ioex) {
				user.setPhoto(null);
				ioex.printStackTrace();
			}

			List<UserRole> userRoles = getUserRoles(context, "member");
			user.setUserRoles(userRoles);
			return user;
		}

		/**
		 * @param context
		 * @param attributeName
		 * @return
		 */
		private List<UserRole> getUserRoles(DirContextOperations context, String attributeName) {
			String[] userRolesAttribute = context.getStringAttributes(attributeName);
			List<UserRole> userRoles = new ArrayList<UserRole>();
			
			if (userRolesAttribute != null) {
				for(String role : userRolesAttribute) {
					try {
						userRoles.add(UserRole.valueOf(role));
					} catch(IllegalArgumentException iaex){
						// if the specified enum type has no constant with the 
						// specified name, or the specified class object does not 
						// represent an enum type. 
					} catch (NullPointerException nex){
						// if enum name is null
					}
				}
			}
			return userRoles;
		}
	}

	/**
	 * This class makes a mapping between context attribute cn and UserRole
	 * enum. It's used to istantiate UserRoles generated from the subTree
	 * containing application group policy.
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 * 
	 */
	private static class UserRoleContextMapper extends AbstractContextMapper {
		@Override
		public Object doMapFromContext(DirContextOperations context) {
			return UserRole.valueOf(context.getStringAttribute("cn"));
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * This method find an unique user searching on ldap server.
	 * 
	 * @param account
	 */
	@Override
	public User findUser(String account) {
		Name dn = LdapUtils.userDistinguishedName(getLdapConfiguration(), account);

		return (User) getLdapTemplate().lookup(dn, getUserContextMapper());
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
			throw new TooManyUsersException();
		}

		return (User) users.get(0);
	}

	/**
	 * 
	 * @param userRole
	 * @return
	 */
	public UserRole findUserRole(UserRole userRole) {
		Name dn = LdapUtils.userRoleDistinguishedName(getLdapConfiguration(),
				userRole.name());

		try {
			return (UserRole) getLdapTemplate().lookup(dn,
					getUserRoleContextMapper());
		} catch (NameNotFoundException nameNotFoundException) {
			return null;
		}
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
			users = getLdapTemplate().search(DistinguishedName.EMPTY_PATH,
				filter.encode(), getUserContextMapper());
		} catch (Throwable th) {
			th.printStackTrace();
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
		if (!user.getAccount().equals("")) {
			orFilter.or(new WhitespaceWildcardsFilter("cn", user.getAccount()));
		}
		if (!user.getFirstName().equals("")) {
			orFilter.or(new WhitespaceWildcardsFilter("givenName", user.getFirstName()));
		}
		if (!user.getLastName().equals("")) {
			orFilter.or(new WhitespaceWildcardsFilter("surname", user.getLastName()));
		}
		if (!user.getOrganization().equals("")) {
			orFilter.or(new WhitespaceWildcardsFilter("ou", user.getOrganization()));
		}
		if (!user.getMail().equals("")) {
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
	 * 
	 * @param userRole
	 * @param context
	 */
	protected void mapUserRoleToContext(UserRole userRole,
			DirContextOperations context) {
		context.setAttributeValues("objectclass", new String[] { "top",
		"groupOfNames" });
	}

	/**
	 * This method will map user bean property to corresponding corresponding
	 * entry on LDAP only if user bean property is not null.
	 * 
	 * @param user Input user to map on context
	 * @param context Context that receives the data from user
	 */
	protected void mapUserToContext(User user, DirContextOperations context) {
		if (user.getPassword() != null)
			context.setAttributeValue("userPassword", passwordEncoder.encodePassword(user.getPassword(), null));
		if (user.getFirstName() != null)
			context.setAttributeValue("givenName", user.getFirstName());
		if (user.getLastName() != null)
			context.setAttributeValue("surname", user.getLastName());
		if (user.getOrganization() != null)
			context.setAttributeValue("ou", user.getOrganization());
		if (user.getAddress() != null)
			context.setAttributeValue("street", user.getAddress());
		if (user.getCity() != null)
			context.setAttributeValue("l", user.getCity());
		if (user.getCountry() != null)
			context.setAttributeValue("c", user.getCountry());
		if (user.getMail() != null)
			context.setAttributeValue("mail", user.getMail());
		if (user.getTitle() != null)
			context.setAttributeValue("personalTitle", user.getTitle());
		if (user.getInterests() != null)
			context.setAttributeValue("info", user.getInterests());
		
		if (context.getObjectAttribute("photo") != null)  {
			context.removeAttributeValue("photo;binary", context.getObjectAttribute("photo"));
		}
		
		if (user.getPhoto() != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream( 1000 );
			
			try {
				
				ImageIO.write( user.getPhoto(), "jpeg", baos );
				baos.flush();
				context.setAttributeValue("photo;binary", baos.toByteArray());
				baos.close();
			} catch (IOException ioex) {
				
			} finally {
			}
		}
		
		if (user.getUserRoles() != null) {
			//List<UserRole> userRoles = getUserRoles(context, "member");
			//user.setUserRoles(userRoles);
		}
	}

	@Override
	public void merge(User user) {
		DirContextOperations context = getLdapTemplate().lookupContext(LdapUtils.userDistinguishedName(getLdapConfiguration(), user.getAccount()));
		mapUserToContext(user, context);
		getLdapTemplate().modifyAttributes(context);
	}

	@Override
	public void persist(User user) {
		DirContextAdapter context = new DirContextAdapter();
		context.setAttributeValues("objectclass", new String[] { "top", "extensibleObject", "person", "organizationalPerson" });
		context.setAttributeValue("cn", user.getAccount());
		mapUserToContext(user, context);
		getLdapTemplate().bind(
				LdapUtils.userDistinguishedName(getLdapConfiguration(),
						user.getAccount()), context, null);
	}

	/**
	 * 
	 * @param account
	 * @param userRole
	 */
	public void persistUserRoles(String account, List<User.UserRole> userRoles) {
		if ((account == null) || (userRoles == null))
			return;

		for (User.UserRole singleRole : userRoles) {
			DirContextOperations context = ldapTemplate.lookupContext(LdapUtils.userRoleDistinguishedName(getLdapConfiguration(),singleRole.name()));
			context.addAttributeValue("member", LdapUtils.fullUserDistinguishedName(getLdapConfiguration(), account));
		}
	}

	/**
	 * @param user
	 */
	public void remove(User user) {
		DirContextAdapter context = new DirContextAdapter();
		mapUserToContext(user, context);
		getLdapTemplate().unbind(
				LdapUtils.userDistinguishedName(getLdapConfiguration(),
						user.getAccount()));
	}

	/**
	 * 
	 */
	public void removeAllUserRoles(String account) {
		if (account == null)
			return;

		DirContextOperations context = getLdapTemplate().lookupContext(LdapUtils.userDistinguishedName(getLdapConfiguration(), account));
		
		User user = new User();
		mapUserToContext(user, context);
		getLdapTemplate().modifyAttributes(context);
		
		for (User.UserRole singleRole : user.getUserRoles()) {
			context.removeAttributeValue("member", LdapUtils
					.fullUserRoleDistinguishedName(getLdapConfiguration(), singleRole.toString()));
		}
	}

	/**
	 * @param account
	 * @param userRoles
	 */
	public void removeUserRoles(String account, List<User.UserRole> userRoles) {
		if ((account == null) || (userRoles == null))
			return;

		DirContextOperations context = getLdapTemplate().lookupContext(
				LdapUtils.userDistinguishedName(getLdapConfiguration(),
						account));
		
		for (User.UserRole singleRole : userRoles) {
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
