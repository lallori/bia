/*
 * BiaLdapUserDetailsMapper.java
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
package org.medici.bia.security;

import java.util.ArrayList;
import java.util.List;

import org.medici.bia.common.util.LdapUtils;
import org.medici.bia.common.util.UserRoleUtils;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserAuthority.Authority;
import org.medici.bia.domain.UserRole;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

/**
 * This class extends LdapUserDetailsImpl to permit storage in UserDetails
 * of custom attribute like first name, last name.
 * 
 * From UserDetails Javadoc :
 * Concrete implementations must take particular care to ensure the non-null
 * contract detailed for each method is enforced. See
 * {@link org.springframework.security.core.userdetails.User} for a
 * reference implementation (which you might like to extend).
 * <p>
 * Concrete implementations should be preferably be immutable &ndash; they should
 * have value object semantics, like a String. The <code>UserDetails</code> may be
 * stored in a cache and multiple threads may use the same instance. Immutable
 * objects are more robust and are guaranteed to be thread-safe. This is not strictly
 * essential (there's nothing within Spring Security itself which absolutely requires it),
 * but if your <tt>UserDetails</tt> object <em>can</em> be modified then it's up to you to make
 * sure that you do so safely and that you manage any caches which may contain copies of
 * the object. 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class BiaLdapUserDetailsImpl extends LdapUserDetailsImpl {
	/**
	 * Variation of essence pattern. Used to create mutable intermediate object.
	 * The idea of using an essence is 
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static class Essence extends LdapUserDetailsImpl.Essence {
		public Essence() {
		}

		public Essence(DirContextOperations ctx) {
			super(ctx);

			setFirstName(ctx.getStringAttribute("givenName"));
			setInitials(ctx.getStringAttribute("initials"));
			setLastName(ctx.getStringAttribute("surname"));
			setMail(ctx.getStringAttribute("mail"));
			setSignificantRoleDescritpion(ctx.getStringAttributes("member"));
		}

		public Essence(BiaLdapUserDetailsImpl copyMe) {
			super(copyMe);
			setFirstName(copyMe.getFirstName());
			setLastName(copyMe.getLastName());
			setMail(copyMe.getMail());
			setSignificantRoleDescritpion(copyMe.getSignificantRoleDescription());
		}

		protected LdapUserDetailsImpl createTarget() {
			return new BiaLdapUserDetailsImpl();
		}

		/**
		 * This method created an UserDetails object using his superclass.
		 */
		public BiaLdapUserDetailsImpl createUserDetails() {
			BiaLdapUserDetailsImpl p = (BiaLdapUserDetailsImpl) super.createUserDetails();

			return p;
		}

		/**
		 * 
		 * @param firstName
		 */
		public void setFirstName(String firstName) {
			((BiaLdapUserDetailsImpl) instance).firstName = firstName;
		}

		/**
		 * 
		 * @param invalidAccess
		 */
		public void setInitials(String initials) {
			((BiaLdapUserDetailsImpl) instance).initials = initials;
		}
		
		/**
		 * 
		 * @param lastName
		 */
		public void setLastName(String lastName) {
			((BiaLdapUserDetailsImpl) instance).lastName = lastName;
		}

		/**
		 * 
		 * @param mail
		 */
		public void setMail(String mail) {
			((BiaLdapUserDetailsImpl) instance).mail = mail;
		}

		/**
		 * 
		 * @param significantRoleDescription
		 */
		private void setSignificantRoleDescritpion(String significantRoleDescription) {
			((BiaLdapUserDetailsImpl) instance).significantRoleDescription = significantRoleDescription;
		}

		/**
		 * 
		 * @param roles
		 */
		private void setSignificantRoleDescritpion(String[] roles) {
			List<UserRole> userRoles = new ArrayList<UserRole>();
			
			if (roles != null) {
				for(String role : roles) {
					try {
						UserRole userRole = new UserRole();
						userRole.setUserAuthority(new UserAuthority(Authority.valueOf(LdapUtils.getStringRole(role))));
						userRoles.add(userRole);
					} catch(IllegalArgumentException iaex){
					} catch (NullPointerException nex){
						// if enum name is null
					}
				}

				((BiaLdapUserDetailsImpl) instance).significantRoleDescription = UserRoleUtils.toDescriptionString(UserRoleUtils.getMostSignificantRole(userRoles));
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2764404863477028517L;
	private String firstName;
	private String initials;
	private String lastName;
	private String mail;
	// This field rapresents the most significant roleDescription
	private String significantRoleDescription;

	protected BiaLdapUserDetailsImpl() {
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return getUsername();
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the initials
	 */
	public String getInitials() {
		return initials;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @return the significantRoleDescription
	 */
	public String getSignificantRoleDescription() {
		return significantRoleDescription;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param initials the initials to set
	 */
	public void setInitials(String initials) {
		this.initials = initials;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @param significantRoleDescription the significantRoleDescription to set
	 */
	public void setSignificantRoleDescription(String significantRoleDescription) {
		this.significantRoleDescription = significantRoleDescription;
	}
}
