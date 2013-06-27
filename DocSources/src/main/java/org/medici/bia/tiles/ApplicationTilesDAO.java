/*
 * ApplicationTilesDAO.java
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
package org.medici.bia.tiles;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.tiles.Attribute;
import org.apache.tiles.Definition;
import org.apache.tiles.Initializable;
import org.apache.tiles.definition.NoSuchDefinitionException;
import org.apache.tiles.definition.dao.DefinitionDAO;
import org.medici.bia.common.context.ApplicationContextProvider;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class ApplicationTilesDAO extends JdbcDaoSupport implements DefinitionDAO<Locale>, Initializable {

	private static Logger logger = Logger.getLogger(ApplicationTilesDAO.class);

	private final DefinitionRowMapper definitionRowMapper = new DefinitionRowMapper();

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void init(Map<String, String> params) {
		setJdbcTemplate(new org.springframework.jdbc.core.JdbcTemplate((DataSource)ApplicationContextProvider.getContext().getBean("dataSource")));
		logger.info(" --> " + getJdbcTemplate());
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 * 
	 */
	@SuppressWarnings("unchecked")
    public Definition getDefinition(String name, Locale locale) {
        ApplicationTemplate applicationTemplate = null;
        List<ApplicationTemplate> definitions = getJdbcTemplate().query("SELECT parentName, name, preparer, template FROM tblApplicationTemplate WHERE name = ?", new Object[] { name }, definitionRowMapper);
		if (!definitions.isEmpty()) {
			applicationTemplate = definitions.get(0);
			AttributeRowMapper attributeRowMapper = new AttributeRowMapper(applicationTemplate);
			getJdbcTemplate().query("SELECT name, type, value, cascadeAttribute FROM tblApplicationTemplateAttributes WHERE templateName = ? ", new Object[] { applicationTemplate.getName() }, attributeRowMapper);
		}

        return applicationTemplate;
    }

	/** {@inheritDoc} */
	public Map<String, Definition> getDefinitions(Locale locale) {
		Map<String, Definition> map = new HashMap<String, Definition>(0);

        // we put main definition...
        List<ApplicationTemplate> definitions = getJdbcTemplate().query("SELECT parentName, name, preparer, template FROM tblApplicationTemplate ", new Object[] { }, definitionRowMapper);
        for (ApplicationTemplate applicationTemplate:definitions) {
			map.put(applicationTemplate.getName(), applicationTemplate);
        }

        // we manage attributes...
        ApplicationTemplateAttributeRowMapper applicationTemplateAttributeRowMapper = new ApplicationTemplateAttributeRowMapper();
		List<ApplicationTemplateAttribute> applicationTemplateAttribute =getJdbcTemplate().query("SELECT templateName, name, type, value, cascadeAttribute FROM tblApplicationTemplateAttributes ORDER BY templateName ", new Object[] { }, applicationTemplateAttributeRowMapper);
		for (ApplicationTemplateAttribute singleAttribute : applicationTemplateAttribute) {
			try {
				map.get(singleAttribute.getTemplateName()).putAttribute(singleAttribute.getName(), singleAttribute,singleAttribute.getCascadeAttribute());
			} catch (NullPointerException nullPointerException) {
				logger.error("template mancante ");
			}
		}
		
		// we manage parent definition...
        for (ApplicationTemplate applicationTemplate:definitions) {

			String parentDefinitionName = applicationTemplate.getExtends();
			while (parentDefinitionName != null) {
				Definition parent = map.get(parentDefinitionName);
				if (parent == null) {
					logger.error("Cannot find definition '" + parentDefinitionName + "' ancestor of '" + applicationTemplate.getName() + "'");
					throw new NoSuchDefinitionException("Cannot find definition '" + parentDefinitionName + "' ancestor of '" + applicationTemplate.getName() + "'");
				}
				applicationTemplate.inherit(parent);
				parentDefinitionName = parent.getExtends();
			}

			map.put(applicationTemplate.getName(), applicationTemplate);
			logger.info("Added tile " + applicationTemplate.getName());
        }


		return map;
	}

	/**
	 * A definition with the new property "id".
	 */
	private static class ApplicationTemplate extends Definition {

		/**
		 * 
		 */
		private static final long serialVersionUID = -443391352346674466L;

		/**
		 * The default constructor.
		 */
		public ApplicationTemplate() {
			super();
		}

		/**
		 * 
		 * @param definition
		 */
		public ApplicationTemplate(Definition definition) {
			super(definition);
		}

		/**
		 * Constructor.
		 * 
		 * @param name
		 *            The name of the definition.
		 * @param template
		 *            The template on which the definition is based.
		 * @param attributes
		 *            The attributes of the definition.
		 */
		public ApplicationTemplate(String name, String template, Map<String, Attribute> attributes) {
			super(name, Attribute.createTemplateAttribute(template), attributes);
		}

	}

	/**
	 * A definition with the new property "templateName".
	 */
	private static class ApplicationTemplateAttribute extends Attribute {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5795617358055971925L;
		
		private String templateName;
		private Boolean cascadeAttribute;

		/**
		 * The default constructor.
		 */
		public ApplicationTemplateAttribute() {
			super();
		}

		/**
		 * 
		 * @param definition
		 */
		public ApplicationTemplateAttribute(String templateName, Attribute attribute) {
			super(attribute);

			setTemplateName(templateName);
		}

		/**
		 * 
		 * @param templateName
		 * @param name
		 * @param value
		 * @param role
		 * @param attributeType
		 */
		public ApplicationTemplateAttribute(String templateName, String name, Object value, String role, AttributeType attributeType) {
			super(value, role, attributeType);
			
			setTemplateName(templateName);
		}

		public Boolean getCascadeAttribute() {
			return cascadeAttribute;
		}

		public void setCascadeAttribute(Boolean cascadeAttribute) {
			this.cascadeAttribute = cascadeAttribute;
		}

		/**
		 * 
		 * @return
		 */
		public String getTemplateName() {
			return templateName;
		}

		/**
		 * 
		 * @param templateName
		 */
		public void setTemplateName(String templateName) {
			this.templateName = templateName;
		}

	}

	/**
	 * Maps a row of a {@link ResultSet} to a {@link Definition}.
	 */
	private static final class DefinitionRowMapper implements RowMapper {

		/** {@inheritDoc} */
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			ApplicationTemplate applicationTemplate = new ApplicationTemplate();
			applicationTemplate.setName(rs.getString("name"));
			applicationTemplate.setTemplateAttribute(Attribute.createTemplateAttribute(rs.getString("template")));
			applicationTemplate.setPreparer(rs.getString("preparer"));
			applicationTemplate.setExtends(rs.getString("parentName"));
			return applicationTemplate;
		}

	}

	/**
	 * Maps a row of a {@link ResultSet} to an {@link Attribute}. It stores the
	 * attributes directly in their definition.
	 */
	private static final class ApplicationTemplateAttributeRowMapper implements RowMapper {

		/**
		 * 
		 */
		private ApplicationTemplateAttributeRowMapper() {
		}

		/** {@inheritDoc} */
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			ApplicationTemplateAttribute applicationTemplateAttribute = new ApplicationTemplateAttribute();
			
			applicationTemplateAttribute.setTemplateName(rs.getString("templateName"));
			applicationTemplateAttribute.setName(rs.getString("name"));
			applicationTemplateAttribute.setRenderer(rs.getString("type"));
			applicationTemplateAttribute.setValue(rs.getString("value"));
			applicationTemplateAttribute.setCascadeAttribute(rs.getBoolean("cascadeAttribute"));
			return applicationTemplateAttribute;
		}

	}

	/**
	 * Maps a row of a {@link ResultSet} to an {@link Attribute}. It stores the
	 * attributes directly in their definition.
	 */
	private static final class AttributeRowMapper implements RowMapper {

		/**
		 * The definition in which the attributes will be stored.
		 */
		private Definition definition;

		/**
		 * Constructor.
		 * 
		 * @param definition
		 *            The definition in which the attributes will be stored.
		 */
		private AttributeRowMapper(Definition definition) {
			this.definition = definition;
		}

		/** {@inheritDoc} */
		public Object mapRow(ResultSet rs, int row) throws SQLException {
			Attribute attribute = new Attribute();
			attribute.setName(rs.getString("name"));
			attribute.setRenderer(rs.getString("type"));
			attribute.setValue(rs.getString("value"));
			/*definition.putAttribute(rs.getString("name"), attribute,
					rs.getBoolean("cascadeAttribute"));*/
			return attribute;
		}

	}
}
