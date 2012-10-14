/*
 * ApplicationPropertyPlaceholderConfigurer.java
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
package org.medici.bia.common.property;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class ApplicationPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private JdbcTemplate jdbcTemplate;

	private String nameColumn;
	private String propertiesTable;
	private String valueColumn;

	/**
	 * Provide a different prefix
	 */
	public ApplicationPropertyPlaceholderConfigurer() {
		super();
		setPlaceholderPrefix("#{");
	}

	/**
	 * 
	 */
	@Override
	protected void loadProperties(final Properties props) throws IOException {
		if (null == props) {
			throw new IOException("No properties passed by Spring framework - cannot proceed");
		}
		String sql = String.format("select %s, %s from %s", nameColumn, valueColumn, propertiesTable);
		logger.info("Reading configuration properties from database");
		try {
			jdbcTemplate.query(sql, new RowCallbackHandler() {

				public void processRow(ResultSet rs) throws SQLException {
					String name = rs.getString(nameColumn);
					String value = rs.getString(valueColumn);
					if (null == name || null == value) {
						throw new SQLException("Configuration database contains empty data. Name='" + name + "' Value='" + value + "'");
					}
					props.setProperty(name, value);
				}

			});
		} catch (DataAccessException dataAccessException) {
			String message = "There is an error in either 'application.properties' or the configuration database.";
			logger.fatal(message);
			throw new IOException(message, dataAccessException);
		}
		if (props.size() == 0) {
			logger.fatal("The configuration database could not be reached or does not contain any properties in '" + propertiesTable + "'");
		}
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setNameColumn(String nameColumn) {
		this.nameColumn = nameColumn;
	}

	public void setPropertiesTable(String propertiesTable) {
		this.propertiesTable = propertiesTable;
	}

	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
	}
}
