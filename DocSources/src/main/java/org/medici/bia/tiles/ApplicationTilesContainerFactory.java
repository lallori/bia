/*
 * ApplicationTiles.java
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

import javax.sql.DataSource;

import org.apache.tiles.TilesApplicationContext;
import org.apache.tiles.context.TilesRequestContextFactory;
import org.apache.tiles.definition.DefinitionsFactory;
import org.apache.tiles.definition.dao.DefinitionDAO;
import org.apache.tiles.factory.BasicTilesContainerFactory;
import org.apache.tiles.locale.LocaleResolver;
import org.medici.bia.common.context.ApplicationContextProvider;

public class ApplicationTilesContainerFactory extends BasicTilesContainerFactory {


    /** {@inheritDoc} */
    @Override
    protected DefinitionDAO<Locale> createLocaleDefinitionDao(TilesApplicationContext applicationContext, TilesRequestContextFactory contextFactory, LocaleResolver resolver) {
        ApplicationTilesDAO applicationTilesDAO = new ApplicationTilesDAO();
        applicationTilesDAO.setDataSource((DataSource) ApplicationContextProvider.getContext().getBean("dataSource"));
        return applicationTilesDAO;
    }

    /** {@inheritDoc} */
    @Override
    protected ApplicationTilesDefinitionsFactory instantiateDefinitionsFactory(TilesApplicationContext applicationContext, TilesRequestContextFactory contextFactory, LocaleResolver resolver) {
    	ApplicationTilesDefinitionsFactory applicationTilesDefinitionsFactory = new ApplicationTilesDefinitionsFactory();
    	HashMap<String, String> tilesPropertyMap = new HashMap<String, String>(0);
    	tilesPropertyMap.put(DefinitionsFactory.DEFINITION_DAO_INIT_PARAM, "org.medici.bia.tiles.ApplicationTilesDAO");	
		applicationTilesDefinitionsFactory.init(tilesPropertyMap);
    	
		return applicationTilesDefinitionsFactory;
   }
}