/**
*  Ajax Volume Explorer for jQuery, version 1.0
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
 * 
 * Last Review: 11/26/2010
*/
(function($) {
	
	var methods = {
		buildRemoteCall : function(remoteUrl, parametersName, parametersValue) {
			//console.log("constructing remote call");
			var retValue = remoteUrl + "&";
			for (i=0; i<parametersName.length; i++) {
				retValue+=parametersName[i];
				retValue+="=";
				if (i<parametersValue.length)
					retValue+=parametersValue[i];
				else
					retValue+="";
				retValue+="&";
			}

			//console.log("remote call : " + retValue);
			return retValue;
		},

		loadRemoteCall : function(target, urlToLoad) {
			//console.log("invoking " + urlToLoad);
			$(target).load(urlToLoad, "",
				function(responseText, textStatus, XMLHttpRequest) {
					//console.log("response on remoteCall" + urlToLoad + ": " + textStatus);
					$(target).html(responseText);
		        }
			);
			return true;
		}
	};

	$.fn.volumeExplorer = function( options ) {  
		var defaults = {
			volNum      : "",
			volLetExt   : "",
			checkVolumeURL : "",
			target : null, 
			remoteUrl : null,
			zIndex: 9999
		};
		var options = $.extend(defaults, options);
		
		return this.each(function() {
			var volNum  = "";
			var volLetExt = "";
			var target=$(options.target);
			if (typeof options.volNum == "string")
				volNum = options.volNum;
			else if (typeof options.volNum == "object")
				; //Qui bisogna gestire il prelevamento del valore tramite il reference al campo
			if (typeof options.volLetExt == "string") {
				if (options.volLetExt == "") {
					volLeText = "";
				} else {
					volLeText = options.volLetExt;
				}
				console.log("volLetExt : " + volLetExt);
			} else if (typeof options.volLetExt == "object")
				; //Qui bisogna gestire il prelevamento del valore tramite il reference al campo

			methods['loadRemoteCall']($(target), (methods['buildRemoteCall'](options.remoteUrl, ['volNum', 'volLetExt'], [volNum, volLetExt])));
			return true;
		});

	};
})( jQuery );
