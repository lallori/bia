/*
 * ApplicationTilesDefinitionsFactory.java
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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.tiles.Definition;
import org.apache.tiles.Initializable;
import org.apache.tiles.awareness.TilesApplicationContextAware;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.definition.DefinitionsFactory;
import org.apache.tiles.definition.NoSuchDefinitionException;
import org.apache.tiles.definition.UnresolvingLocaleDefinitionsFactory;
import org.apache.tiles.definition.dao.CachingLocaleUrlDefinitionDAO;
import org.apache.tiles.definition.dao.DefinitionDAO;
import org.apache.tiles.locale.LocaleResolver;
import org.apache.tiles.locale.impl.DefaultLocaleResolver;
import org.apache.tiles.reflect.ClassUtil;

/**
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class ApplicationTilesDefinitionsFactory extends UnresolvingLocaleDefinitionsFactory implements Initializable {

	private Map<String, Definition> tilesMap = new HashMap<String, Definition>();

	/**
	 * Initializes the DefinitionsFactory and its subcomponents.
	 * <p/>
	 * Implementations may support configuration properties to be passed in via
	 * the params Map.
	 * 
	 * @param params
	 *            The Map of configuration properties.
	 * @throws DefinitionsFactoryException
	 *             if an initialization error occurs.
	 */
	@SuppressWarnings("unchecked")
	public void init(Map<String, String> params) {
		String definitionDaoClassName = params.get(DefinitionsFactory.DEFINITION_DAO_INIT_PARAM);
		if (definitionDaoClassName != null) {
			definitionDao = (DefinitionDAO<Locale>) ClassUtil.instantiate(definitionDaoClassName);
		} else {
			definitionDao = createDefaultDefinitionDAO();
		}
		if (definitionDao instanceof TilesApplicationContextAware) {
			((TilesApplicationContextAware) definitionDao)
					.setApplicationContext(applicationContext);
		}
		if (definitionDao instanceof Initializable) {
			((Initializable) definitionDao).init(params);
		}

		String resolverClassName = params.get(DefinitionsFactory.LOCALE_RESOLVER_IMPL_PROPERTY);
		if (resolverClassName != null) {
			localeResolver = (LocaleResolver) ClassUtil.instantiate(resolverClassName);
		} else {
			localeResolver = createDefaultLocaleResolver();
		}
		localeResolver.init(params);
		
		tilesMap = getMapOfDefinitions();
	}

	/** {@inheritDoc} */
	public Map<String, Definition> getMapOfDefinitions() {
		return definitionDao.getDefinitions(null);
	}

	/**
	 * 
	 */
	public Definition getDefinition(String name, TilesRequestContext tilesContext) {
		return tilesMap.get(name);
	}
	

	/**
	 * Creates the default locale resolver, if it has not been specified
	 * outside.
	 * 
	 * @return The default locale resolver.
	 * @since 2.1.0
	 */
	protected LocaleResolver createDefaultLocaleResolver() {
		return new DefaultLocaleResolver();
	}

	/**
	 * Creates the default definition DAO, if it has not been specified outside.
	 * 
	 * @return The default definition DAO.
	 * @since 2.1.0
	 */
	protected DefinitionDAO<Locale> createDefaultDefinitionDAO() {
		return new CachingLocaleUrlDefinitionDAO();
	}
}
