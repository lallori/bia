/**
*  Advanced Search plugin for jQuery, version 1.0
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

    $.advancedSearchForm = {};

    $.advancedSearchForm.defaults = {
    };

    $.fn.advancedSearchForm = function (options) {
    	var options = $.extend($.advancedSearchForm.defaults, options);

    	// We manage submit button to populate right frame
        $(this).submit(function (event) {
            // Prevent the browser's default onClick handler
            event.preventDefault();
            // Extract form Name
            var formName = $(this).attr('id');
            // Extracting field on which this form works on.
            var fieldName = formName.substring(0, formName.indexOf('SearchForm'));

            console.log("AdvancedSearchForm started. Form Name : " + formName);
            console.log("Field Name : " + fieldName);

            var searchType = "";
        	if (formName.indexOf("date") >= 0) {
        		searchType = $('#' + formName).find('#' + fieldName + 'Type').find('option:selected').text();
        	} else {
        		searchType = $('#' + formName).find('#category').val();
        		if ($('#' + formName).find('#' + fieldName + 'Type').length==1) {
        			searchType += ' in ' + $('#' + formName).find('#' + fieldName + 'Type').find('option:selected').text();  
        		}
        	}
            console.log("Search Type : " + formName);

        	var searchWord = "";
            var hiddenValue = "";

        	// Date form is composed of three fields.
    		if ($('#' + formName).find('#' + fieldName + 'Type').size() > 0) {
    			if ($(this).find("option:selected").text() == 'Between') {
    	        	if (formName.indexOf("date") >= 0) {
    	        		searchWord = $('#' + formName).find('#' + fieldName + 'Year').val();
    	        		searchWord += ' ' + $('#' + formName).find('#' + fieldName + 'Month').find('option:selected').text();
    	        		searchWord += ' ' + $('#' + formName).find('#' + fieldName + 'Day').val();
    	        	} else {
    	        	}
    			} else if ($(this).find("option:selected").text() == 'Exactly') {
    				searchWord = $('#' + formName).find('#' + fieldName).val();
            		hiddenValue = $(this).find("option:selected").text() + "|" + searchWord;
    			} else {
    				searchWord = $('#' + formName).find('#' + fieldName).val();
    				hiddenValue = $(this).find("option:selected").val() + "|" + searchWord;
    			}
    		} else {
    			searchWord = $('#' + formName).find('#' + fieldName).val();
    			// If element has autocompleter, its hiddenValue is a concatenation of id + description
    			if ($('#' + formName).find('#' + fieldName + 'Id').size() > 0) {
    				console.log("Form has an autocompleter element")
    				hiddenValue = $('#' + formName).find('#' + fieldName + 'Id').val() + "|" + searchWord;
    			} else {
    				hiddenValue = searchWord;
    			}
    		}

            console.log("Search word : " + searchWord);
            console.log("Final hidden parameter (" + formName + ") value: " + hiddenValue);

            // We add html to right form :
            if ($("#searchFilterForm").find("#" + fieldName + "SearchDiv").length ==0) {
            	console.log("Creating " + fieldName + "SearchDiv");
                var fieldSearchDiv = $('<div id="' + fieldName + 'SearchDiv">');
            	$("#searchFilterForm").append(fieldSearchDiv);
            } else {
            }

            // We construct new html block with condition and hidden field 
            var searchFilterDiv = $('<div class="SearchFilterDiv">');
            $(searchFilterDiv).append('<span class="categorySearch">' + searchType + ': </span>');
            $(searchFilterDiv).append('<span class="wordSearch">' + searchWord + '</span>');
            $(searchFilterDiv).append('<a href="#" class="remove">(remove)</a>');
            $(searchFilterDiv).append('<input type="hidden" name="' + fieldName + '" value="' + hiddenValue + '"/>');

            if ($("#" + fieldName + "SearchDiv").find(".SearchFilterDiv").length >0) {
            	$("#" + fieldName + "SearchDiv").find(".SearchFilterDiv").last().after('<p class="andOrNotAdvancedSearch">And</p>');
            }

            // We append new block at the end of "field" SearchDiv 
            console.log("appending searchFilterDiv : " + searchFilterDiv);
            $("#" + fieldName + "SearchDiv").append(searchFilterDiv);

            console.log("AdvancedSearchForm " + formName + " completed.");
            return false;
        });

        return $;
    };
	return $;
})(jQuery);
