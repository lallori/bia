/*
 * MySQLDialect5fixedDialect.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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
package org.medici.docsources.common.hibernate;

import java.sql.Types;

import org.hibernate.dialect.MySQL5Dialect;

/**
 * An SQL dialect for MySQL 5.x specific features.
 * This class contains a fix on manage of type VARBINARY.
 * 
 * This fix was taked from :
 * 
 * http://lists.jboss.org/pipermail/hibernate-issues/2008-May/010407.html
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public class MySQLDialect5fixedDialect extends MySQL5Dialect {
	public MySQLDialect5fixedDialect() {
		super();
		registerColumnType(Types.VARBINARY, 255, "varbinary($l)");
	}
}