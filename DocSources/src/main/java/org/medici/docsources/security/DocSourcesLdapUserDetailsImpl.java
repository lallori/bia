/*
 * DocSourcesLdapUserDetailsMapper.java
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
package org.medici.docsources.security;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.medici.docsources.common.util.LdapUtils;
import org.medici.docsources.common.util.UserRoleUtils;
import org.medici.docsources.domain.User.UserRole;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.util.Assert;

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
 * 
 */
public class DocSourcesLdapUserDetailsImpl extends LdapUserDetailsImpl {
	/**
	 * Variation of essence pattern. Used to create mutable intermediate object.
	 * The idea of using an essence is 
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static class Essence extends LdapUserDetailsImpl.Essence {
		// 20101030214433+0100
		private DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssZZZZ");

		public Essence() {
		}

		public Essence(DirContextOperations ctx) {
			super(ctx);

			setExpirationDate(ctx.getStringAttribute("krb5AccountExpirationTime"));
			setExpirationPasswordDate(ctx.getStringAttribute("krb5PasswordEnd"));
			setFirstName(ctx.getStringAttribute("givenName"));
			setInitials(ctx.getStringAttribute("initials"));
			setInvalidAccess(ctx.getStringAttribute("krb5MaxLife"));
			setInvalidAccessMax(ctx.getStringAttribute("krb5MaxRenew"));
			setLastName(ctx.getStringAttribute("surname"));
			setMail(ctx.getStringAttribute("mail"));
			setActive(ctx.getStringAttribute("krb5AccountDisabled"));
			setAccountNonLocked(ctx.getStringAttribute("krb5AccountLockedOut"));
			setSignificantRoleDescritpion(ctx.getStringAttributes("member"));
			setAccountNonExpired();
		}

		public Essence(DocSourcesLdapUserDetailsImpl copyMe) {
			super(copyMe);
			setExpirationDate(copyMe.getExpirationDate());
			setExpirationPasswordDate(copyMe.getExpirationPasswordDate());
			setFirstName(copyMe.getFirstName());
			setInvalidAccess(copyMe.getInvalidAccess().toString());
			setInvalidAccessMax(copyMe.getInvalidAccessMax().toString());
			setLastName(copyMe.getLastName());
			setMail(copyMe.getMail());
			setActive(copyMe.getActive().toString());
			setAccountNonLocked(((LdapUserDetailsImpl)copyMe).isAccountNonLocked());
			setAccountNonExpired();
			setSignificantRoleDescritpion(copyMe.getSignificantRoleDescription());
		}

		protected LdapUserDetailsImpl createTarget() {
			return new DocSourcesLdapUserDetailsImpl();
		}

		/**
		 * This method created an UserDetails object using his superclass.
		 */
		public DocSourcesLdapUserDetailsImpl createUserDetails() {
			DocSourcesLdapUserDetailsImpl p = (DocSourcesLdapUserDetailsImpl) super.createUserDetails();
			Assert.notNull(p.getActive());
			Assert.notNull(p.getActive());

			return p;
		}

		/**
		 * 
		 */
		public void setAccountNonExpired() {
			//The user is not expired if expiration date is after or equals today
			super.setAccountNonExpired((!((DocSourcesLdapUserDetailsImpl) instance).expirationDate.before(new Date())));        	
		}

		/**
		 * 
		 * @param accountNonLocked
		 */
		public void setAccountNonLocked(String accountNonLocked) {
			super.setAccountNonLocked(!Boolean.valueOf(accountNonLocked));        	
		}

		/**
		 * 
		 * @param disabled
		 */
		public void setActive(String disabled) {
			((DocSourcesLdapUserDetailsImpl) instance).active = !Boolean.valueOf(disabled);
		}

		/**
		 * 
		 * @param expirationDate
		 */
		public void setExpirationDate(Date expirationDate) {
			((DocSourcesLdapUserDetailsImpl) instance).expirationDate = expirationDate;
		}

		/**
		 * 
		 * @param expirationDate
		 */
		public void setExpirationDate(String expirationDate) {
			try {
				((DocSourcesLdapUserDetailsImpl) instance).expirationDate = dateFormat.parse(expirationDate);
			} catch (ParseException pex) {
				pex.printStackTrace();
			}
		}

		/**
		 * 
		 * @param expirationPasswordDate
		 */
		public void setExpirationPasswordDate(Date expirationPasswordDate) {
			((DocSourcesLdapUserDetailsImpl) instance).expirationPasswordDate = expirationPasswordDate;
		}

		/**
		 * 
		 * @param expirationPasswordDate
		 */
		public void setExpirationPasswordDate(String expirationPasswordDate) {
			try {
				((DocSourcesLdapUserDetailsImpl) instance).expirationPasswordDate = dateFormat.parse(expirationPasswordDate);
			} catch (ParseException pex) {
				pex.printStackTrace();
			}
		}

		/**
		 * 
		 * @param firstName
		 */
		public void setFirstName(String firstName) {
			((DocSourcesLdapUserDetailsImpl) instance).firstName = firstName;
		}

		/**
		 * 
		 * @param invalidAccess
		 */
		public void setInitials(String initials) {
			((DocSourcesLdapUserDetailsImpl) instance).initials = initials;
		}
		
		/**
		 * 
		 * @param invalidAccess
		 */
		public void setInvalidAccess(String invalidAccess) {
			try {
				((DocSourcesLdapUserDetailsImpl) instance).invalidAccess = Integer.valueOf(invalidAccess);
			} catch(NumberFormatException nfex) {
			}
		}

		/**
		 * 
		 * @param invalidAccessMax
		 */
		public void setInvalidAccessMax(String invalidAccessMax) {
			try {
				((DocSourcesLdapUserDetailsImpl) instance).invalidAccessMax = Integer.valueOf(invalidAccessMax);
			} catch(NumberFormatException nfex) {
			}
		}

		/**
		 * 
		 * @param lastName
		 */
		public void setLastName(String lastName) {
			((DocSourcesLdapUserDetailsImpl) instance).lastName = lastName;
		}

		/**
		 * 
		 * @param mail
		 */
		public void setMail(String mail) {
			((DocSourcesLdapUserDetailsImpl) instance).mail = mail;
		}

		/**
		 * 
		 * @param significantRoleDescription
		 */
		private void setSignificantRoleDescritpion(String significantRoleDescription) {
			((DocSourcesLdapUserDetailsImpl) instance).significantRoleDescription = significantRoleDescription;
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
						userRoles.add(UserRole.valueOf(LdapUtils.getStringRole(role)));
					} catch(IllegalArgumentException iaex){
					} catch (NullPointerException nex){
						// if enum name is null
					}
				}

				((DocSourcesLdapUserDetailsImpl) instance).significantRoleDescription = UserRoleUtils.toDescriptionString(UserRoleUtils.getMostSignificantRole(userRoles));
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2764404863477028517L;
	private Boolean active;
	private Date expirationDate;
	private Date expirationPasswordDate;
	private String firstName;
	private String initials;
	private Integer invalidAccess;
	private Integer invalidAccessMax;
	private String lastName;
	private String mail;
	// This field rapresents the most significant roleDescription
	private String significantRoleDescription;

	protected DocSourcesLdapUserDetailsImpl() {
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return getUsername();
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @return the expirationPasswordDate
	 */
	public Date getExpirationPasswordDate() {
		return expirationPasswordDate;
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
	 * @return the invalidAccess
	 */
	public Integer getInvalidAccess() {
		return invalidAccess;
	}

	/**
	 * @return the invalidAccessMax
	 */
	public Integer getInvalidAccessMax() {
		return invalidAccessMax;
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
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @param expirationPasswordDate the expirationPasswordDate to set
	 */
	public void setExpirationPasswordDate(Date expirationPasswordDate) {
		this.expirationPasswordDate = expirationPasswordDate;
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
	 * @param invalidAccess the invalidAccess to set
	 */
	public void setInvalidAccess(Integer invalidAccess) {
		this.invalidAccess = invalidAccess;
	}

	/**
	 * @param invalidAccessMax the invalidAccessMax to set
	 */
	public void setInvalidAccessMax(Integer invalidAccessMax) {
		this.invalidAccessMax = invalidAccessMax;
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
