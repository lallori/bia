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
 * Last Review: 02/18/2011
*/
(function ($) {

    $.volumeExplorer = {};

    $.volumeExplorer.defaults = {
    	"summaryId" : 0,
		"volNum" : 0,
		"volLetExt" : "",
		"checkVolumeDigitizedURL" : "",
		"showExplorerVolumeURL" : "",
		"target" : "" 
    };

    $.fn.volumeExplorer = function (options) {
    	var options = $.extend($.volumeExplorer.defaults, options);

        // Loop over all matching elements
        this.each(function (){
        	var volumeDigitized = false;
        	
        	if ((options["summaryId"] >0) || (options["volNum"] >0)) {
            	$.ajax({ type:"GET", url:options["checkVolumeDigitizedURL"], async:false, success:function(data) {
            		if (data.digitized == "true") {
            			var tabName = "<span id='titleTab" + data.volNum + data.volLetExt + "'>Explore Volume " + data.volNum + data.volLetExt + "</span>";
            			var tabExist = false;
            			var numTab = 0;
            			//Verify if a tab with same title already exist
        				$j("#tabs ul li a").each(function(){
        					var sameId = $j(this).find("span").find("span");
        					var toTest = "";
        					toTest += this.text;
        					if(!tabExist){
        						if(toTest != ""){
        							numTab++;
        						}
        					}
        					if(this.text == tabName || $j(sameId).attr("id") == "titleTab" + data.volNum + data.volLetExt){
        						tabExist = true;
        					}
        				});
        				
        				if(!tabExist){
        					tabName += "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab";
        					$("#tabs").tabs("add", options["showExplorerVolumeURL"], tabName);
                        	$("#tabs").tabs("select", $("#tabs").tabs("length")-1);
        				}else{
        					$j("#tabs").tabs("select", numTab);
        					$j("#tabs").tabs("url", numTab, options["showExplorerVolumeURL"]);
        					$j("#tabs").tabs("load", numTab);
        				}
                    	return false;
                    	
            		}
            	}
    			});
        	}
        });

        return $;
    };
})(jQuery);
