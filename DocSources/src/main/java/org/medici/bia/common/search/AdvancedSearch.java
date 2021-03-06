/*
 * AdvancedSearch.java
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
package org.medici.bia.common.search;

import org.medici.bia.command.search.AdvancedSearchCommand;
import org.medici.bia.command.search.SimpleSearchCommand;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public interface AdvancedSearch extends Search {

	/**
	 * This method initialize object from an advanced search command bea.
	 * 
	 * @param command Advanced Search command bean,
	 */
	void initFromAdvancedSearchCommand(AdvancedSearchCommand command);

	/**
	 * This method initialize object from a simple search command bea.
	 * It's used in refining simple search action.
	 * 
	 * @param command Simple Search command bean,
	 */
	void initFromSimpleSearchCommand(SimpleSearchCommand command);
}
