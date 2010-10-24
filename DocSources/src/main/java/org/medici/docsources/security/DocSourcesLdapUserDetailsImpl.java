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
import java.util.Date;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.util.Assert;

/**
 * This class extends LdapUserDetailsImpl to permit storage in UserDetails
 * of custom attribute like first name, last name.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class DocSourcesLdapUserDetailsImpl extends LdapUserDetailsImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2764404863477028517L;

	private Boolean active;
	private Date expirationDate;
	private Date expirationPasswordDate;
	private String firstName;
	private Integer invalidAccess;
	private Integer invalidAccessMax;
	private String lastName;
	private String mail;

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the expirationPasswordDate
	 */
	public Date getExpirationPasswordDate() {
		return expirationPasswordDate;
	}

	/**
	 * @param expirationPasswordDate the expirationPasswordDate to set
	 */
	public void setExpirationPasswordDate(Date expirationPasswordDate) {
		this.expirationPasswordDate = expirationPasswordDate;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the invalidAccess
	 */
	public Integer getInvalidAccess() {
		return invalidAccess;
	}

	/**
	 * @param invalidAccess the invalidAccess to set
	 */
	public void setInvalidAccess(Integer invalidAccess) {
		this.invalidAccess = invalidAccess;
	}

	/**
	 * @return the invalidAccessMax
	 */
	public Integer getInvalidAccessMax() {
		return invalidAccessMax;
	}

	/**
	 * @param invalidAccessMax the invalidAccessMax to set
	 */
	public void setInvalidAccessMax(Integer invalidAccessMax) {
		this.invalidAccessMax = invalidAccessMax;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	protected DocSourcesLdapUserDetailsImpl() {
	}

	protected void populateContext(DirContextAdapter adapter) {
		/*        adapter.setAttributeValue("sn", sn);
        adapter.setAttributeValues("cn", getCn());
        adapter.setAttributeValue("description", getDescription());
        adapter.setAttributeValue("telephoneNumber", getTelephoneNumber());

        if(getPassword() != null) {
            adapter.setAttributeValue("userPassword", getPassword());
        }
        adapter.setAttributeValues("objectclass", new String[] {"top", "person"});
		 */
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

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
			setInvalidAccess(ctx.getStringAttribute("krb5MaxLife"));
			setInvalidAccessMax(ctx.getStringAttribute("krb5MaxRenew"));
			setLastName(ctx.getStringAttribute("surname"));
			setMail(ctx.getStringAttribute("mail"));
			setActive(ctx.getStringAttribute("krb5AccountDisabled"));
			setAccountNonLocked(ctx.getStringAttribute("krb5AccountLockedOut"));
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
		}

		protected LdapUserDetailsImpl createTarget() {
			return new DocSourcesLdapUserDetailsImpl();
		}

		public void setActive(String disabled) {
			((DocSourcesLdapUserDetailsImpl) instance).active = !Boolean.valueOf(disabled);
		}

		public void setAccountNonLocked(String accountNonLocked) {
			super.setAccountNonLocked(!Boolean.valueOf(accountNonLocked));        	
		}

		public void setAccountNonExpired() {
			//The user is not expired if expiration date is after or equals today
			super.setAccountNonExpired((!((DocSourcesLdapUserDetailsImpl) instance).expirationDate.before(new Date())));        	
		}

		public void setExpirationDate(Date expirationDate) {
			((DocSourcesLdapUserDetailsImpl) instance).expirationDate = expirationDate;
		}

		public void setExpirationPasswordDate(Date expirationPasswordDate) {
			((DocSourcesLdapUserDetailsImpl) instance).expirationPasswordDate = expirationPasswordDate;
		}

		public void setExpirationDate(String expirationDate) {
			try {
				((DocSourcesLdapUserDetailsImpl) instance).expirationDate = dateFormat.parse(expirationDate);
			} catch (ParseException pex) {
				pex.printStackTrace();
			}
		}

		public void setExpirationPasswordDate(String expirationPasswordDate) {
			try {
				((DocSourcesLdapUserDetailsImpl) instance).expirationPasswordDate = dateFormat.parse(expirationPasswordDate);
			} catch (ParseException pex) {
				pex.printStackTrace();
			}
		}

		public void setFirstName(String firstName) {
			((DocSourcesLdapUserDetailsImpl) instance).firstName = firstName;
		}

		public void setInvalidAccess(String invalidAccess) {

		}

		public void setInvalidAccessMax(String invalidAccessMax) {

		}

		public void setLastName(String lastName) {
			((DocSourcesLdapUserDetailsImpl) instance).lastName = lastName;
		}

		public void setMail(String mail) {
			((DocSourcesLdapUserDetailsImpl) instance).mail = mail;
		}

		public DocSourcesLdapUserDetailsImpl createUserDetails() {
			DocSourcesLdapUserDetailsImpl p = (DocSourcesLdapUserDetailsImpl) super.createUserDetails();
			Assert.notNull(p.getActive());
			Assert.notNull(p.getActive());

			// TODO: Check contents for null entries
			return p;
		}
	}
}
