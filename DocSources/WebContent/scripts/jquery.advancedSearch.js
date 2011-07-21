/**
*  Advanced	Search plugin for jQuery, version 1.0
 * 
 * Developed by	Medici Archive Project (2010-2012).
 * 
 * This	file is	part of	DocSources.
 * 
 * DocSources is free software;	you can redistribute it	and/or modify
 * it under the terms of the GNU General Public	License	as published by
 * the Free Software Foundation; either version	2 of the License, or
 * (at your option) any later version.
 * 
 * DocSources is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A	PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51	Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a	special	exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable	to be covered by the GNU General Public	License.
 * This	exception does not however invalidate any other reasons	why the
 * executable file might be covered by the GNU General Public License.
 * 
 * Last	Review:	02/18/2011
*/
(function ($) {

	$.advancedSearchForm = {};

	$.advancedSearchForm.defaults =	{
	};

	$.fn.advancedSearchForm = function (options) {
		var options = $.extend($.advancedSearchForm.defaults, options);

		addListenerRemoveCondition();
		
		// We manage submit	button to populate right frame
		$(this).submit(function	(event) {
			//Prevent the browser's	default	onClick	handler
			event.preventDefault();

			// Extract form	Name
			var formName = $(this).attr('id');
			//Extracting field on which	this form works	on.
			var fieldName =	getSearchFieldName($(this));
			var searchType = getSearchType(formName, fieldName);

			console.log("AdvancedSearchForm	started. Form Name : " + formName);
			console.log("Field Name	: " + fieldName);
			console.log("Search	Type : " + searchType);

			var searchWord = "";
			var hiddenValue = "";

			// Manage form with type combobox
			if ($(searchType)) {
				//Date form
				if (formName.indexOf("date") >= 0) {
					if ($(this).find("option:selected").val() == 'Between') {
						if (checkDateFieldsBetweenAreEmpty(formName, fieldName)) {
							return true;
						}

						searchWord = getSearchWordForBetweenDate(formName, fieldName);
						hiddenValue = getHiddenParameterForBetweenDate(formName, fieldName);
						resetDateFields(formName, fieldName);
					} else {
						searchWord = getSearchWordForSingleDate(formName, fieldName);
						hiddenValue = getHiddenParameterForSingleDate(formName, fieldName);

						resetDateFields(formName, fieldName);
					}
				} else if (formName.indexOf("volume") >= 0) {
					if ($(this).find("option:selected").val() == 'Between') {
						searchWord = getSearchWordForBetweenField(formName, fieldName);
						hiddenValue = $(this).find("option:selected").val() + "|" + $('#' + formName).find('#' + fieldName).val() + '|' + $('#' + formName).find('#' + fieldName + 'Between').val();
						//Reset	input fields
						resetBetweenField(formName, fieldName);
					} else if ($(this).find("option:selected").val() == 'Exactly') {
						searchWord = $('#' + formName).find('#' + fieldName).val();
						hiddenValue = $(this).find("option:selected").text() + "|" + searchWord;
						$('#' + formName).find('#' + fieldName).val("");
					} 
				} else {
					searchWord = $('#' + formName).find('#' + fieldName).val();
					hiddenValue = $(this).find("option:selected").val() + "|" + escape($('#' + formName).find('#' + fieldName).val());
					$('#' + formName).find('#' + fieldName).val("");
				}
			} else {
				searchWord = getSearchWordForAutocompleterField(formName, fieldName);
				hiddenValue = getHiddenParameterForAutocompleterField(formName, fieldName);
			}
			
			console.log("Searching : " + searchWord);
			console.log("Final hidden parameter (" + formName + ") value: " + hiddenValue);

			// We construct	new html block with	condition and hidden field 
			var searchFilterDiv = getSearchFilterDiv(searchType, searchWord, fieldName, hiddenValue);

			if ($("#" + fieldName + "SearchDiv").find(".searchFilterDiv").length >0) {
				$("#" + fieldName + "SearchDiv").find(".searchFilterDiv").last().after('<p class="andOrNotAdvancedSearch">And</p>');
			}

			// we manage and conjunction
			insertBeforeAndConjunction(fieldName);
			insertAfterAndConjunction(fieldName);

			// We append new block at the end of "field" SearchDiv 
			$("#" + fieldName + "SearchDiv").append(searchFilterDiv);

			console.log("AdvancedSearchForm	" + formName + " completed.");
			
			return false;
		});

		return $;
	};


	/**
	 * This method add a listener on element with class remove to manage remove condition.
	 */
	function addListenerRemoveCondition() {
		$('.remove').die();
		$('.remove').live('click', function() {
			// we search for next form condition 
			if ($(this).parent().next().attr('class') == 'andOrNotAdvancedSearch') {
				$(this).parent().next().remove();
			} else if ($(this).parent().prev().attr('class') == 'andOrNotAdvancedSearch') {
				$(this).parent().prev().remove();
			}

			removeAfterAndConjunction($(this));
			// we remove parent	container element
			$j(this).parent().remove();
			return false;
		});
	}
	/**
	 * This method checks if every fields with date between are empty
	 */
	function checkDateFieldsBetweenAreEmpty(formName, fieldName) {
		if (($('#' + formName).find('#dateYear').val() == 'yyyy') &&
			($('#' + formName).find('#dateMonth').find('option:selected').val() == 'mm') &&
			($('#' + formName).find('#dateDay').val() == 'dd')) {
			return true;
		}

		if (($('#' + formName).find('#dateYearBetween').val() == 'yyyy') &&
			($('#' + formName).find('#dateMonthBetween').find('option:selected').val() == 'mm')	&&
			($('#' + formName).find('#dateDayBetween').val() == 'dd')) {
			return true;
		}
		
		return false;
	}

	/**
	 * This method create result div to attach to right section of advanced search filter page. 
	 */
	function getSearchFilterDiv(searchType, searchWord, fieldName, hiddenValue) {
		var retValue = $('<div class="searchFilterDiv">');
		$(retValue).append('<span class="categorySearch">' + searchType + ': </span>');
		$(retValue).append('<span class="wordSearch">' + searchWord + '</span>');
		$(retValue).append('<a href="#" class="remove">(remove)</a>');
		$(retValue).append('<input type="hidden" name="' + fieldName + '" value="' + hiddenValue + '"/>');
		return retValue;
	}

	/**
	 * This method returns field name of this advanced search form.  
	 */
	function getSearchFieldName(jquerySelector) {
		return jquerySelector.attr('id').substring(0, jquerySelector.attr('id').indexOf('SearchForm'));
	}
	
	/**
	 * This method returns type of this search form.   
	 */
	function getSearchType(formName, fieldName) {
		var retValue = "";
		if (formName.indexOf("date") >=	0) {
			retValue = $('#' + formName).find('#' + fieldName + 'Type').find('option:selected').val();
		} else {
			retValue = $('#' + formName).find('#category').val();
			if ($('#' + formName).find('#' + fieldName + 'Type').length==1) {
				retValue += ' in ' + $('#' + formName).find('#' + fieldName + 'Type').find('option:selected').val();	 
			}
		}
		
		return retValue;
	}

	/**
	 * This function return search word to diplay for autocompleter field.
	 */
	function getSearchWordForAutocompleterField(formName, fieldName) {
		return $('#' + formName).find('#' + fieldName).val();
	}
	
	/**
	 * This function return search word to diplay for a field which is composed of two elements.
	 */
	function getSearchWordForBetweenField(formName, fieldName) {
		return $('#' + formName).find('#' + fieldName).val() + ', ' + $('#' + formName).find('#' + fieldName + 'Between').val();
	}

	/**
	 * This method return search word to display in case of date field with between search type. 
	 */
	function getSearchWordForBetweenDate(formName, fieldName) {
		var searchWord = '';
		if ($('#' + formName).find('#dateYear').val() != 'yyyy')
			searchWord = $('#' + formName).find('#dateYear').val();
		if ( $('#' + formName).find('#dateMonth').find('option:selected').val()	!= 'mm')
			searchWord += '	' + $('#' + formName).find('#' + fieldName + 'Month').find('option:selected').text();
		if ($('#' + formName).find('#dateDay').val() != 'dd')
			searchWord += '	' + $('#' + formName).find('#dateDay').val();
		searchWord += '	and'; 
		if ($('#' + formName).find('#dateYearBetween').val() != 'yyyy')
			searchWord += '	 ' + $('#' + formName).find('#dateYearBetween').val();
		if ( $('#' + formName).find('#dateMonthBetween').find('option:selected').val() != 'mm')
			searchWord += '	' + $('#' + formName).find('#dateMonthBetween').find('option:selected').text();
		if ($('#' + formName).find('#dateDayBetween').val()	!= 'dd')
			searchWord += '	' + $('#' + formName).find('#dateDayBetween').val();
		
		return searchWord;
	}

	/**
	 * This function return search word to diplay for a field which is composed of two elements.
	 */
	function getSearchWordForSingleDate(formName, fieldName) {
		var searchWord = '';
		if ($('#' + formName).find('#dateYear').val() != 'yyyy') {
			searchWord += $('#' + formName).find('#dateYear').val();
		}
		if ( $('#' + formName).find('#dateMonth').find('option:selected').val()	!= 'mm') {
			searchWord += '	' + $('#' + formName).find('#dateMonth').find('option:selected').text();
		}
		if ($('#' + formName).find('#dateDay').val() != 'dd') {
			searchWord += '	' + $('#' + formName).find('#dateDay').val();
		}
		return searchWord;
	}

	/**
	 * This function return hidden parameter to diplay for autocompleter field.
	 */
	function getHiddenParameterForAutocompleterField(formName, fieldName) {
		console.log("Form has an autocompleter element");
		var hiddenParameter = "";
		// If element has autocompleter, its hiddenValue is a concatenation of id + description
		if ($('#' + formName).find('#' + fieldName + 'Id').size() > 0) {
			//Calculate hidden value (escaping text field to prevent field truncation
			hiddenParameter = $('#' + formName).find('#' + fieldName + 'Id').val() + "|" + escape($('#' + formName).find('#' + fieldName).val());
			// we reset	autocompleterId
			$('#' + formName).find('#' + fieldName + 'Id').val("");
		} else {
			hiddenParameter = $('#' + formName).find('#' + fieldName).val();
		}

		// we reset textfield value
		$('#' + formName).find('#' + fieldName).val("");

		return hiddenParameter;
	}

	/**
	 * This method return hidden parameter for single date.
	 */
	function getHiddenParameterForSingleDate(formName, fieldName) {
		return $('#' + formName).find("option:selected").val() + "|" + $('#' + formName).find('#dateYear').val() + '|' + $('#' + formName).find('#dateMonth').find('option:selected').val() + '|' + $('#' + formName).find('#dateDay').val();
	}

	/**
	 * This method return hidden parameter for date field in between condition
	 */
	function getHiddenParameterForBetweenDate(formName, fieldName) {
		var hiddenValue =  $('#' + formName).find("option:selected").val() + "|" + 
		   $('#' + formName).find('#dateYear').val() + '|' + 
		   $('#' + formName).find('#dateMonth').find('option:selected').val() + '|' + 
		   $('#' + formName).find('#dateDay').val()+ "|" + 
		   $('#' + formName).find('#dateYearBetween').val() + '|' + 
		   $('#' + formName).find('#dateMonthBetween').find('option:selected').val() + '|' + 
		   $('#' + formName).find('#dateDayBetween').val();
	}

	/**
	 * This metod insert "and conjunction" after the SearchDiv of correspondents 
	 * field identified by fieldName.  
	 */
	function insertAfterAndConjunction(fieldName) {
		var conditionDivIndex =	$("#yourEasySearchFilterForm > div").index($("div[id='" + fieldName + "SearchDiv']"))
		var nextConditions = 0;
		var nextConjunctions = 0;

		$("#yourEasySearchFilterForm div[id$=SearchDiv]").each(function(index) {
			if (index <= conditionDivIndex) {
				return true;
			}
			//count	previous filled	condition
			if ($(this).children().size()>0) {
				nextConditions++;
			}
			// check if is present "and conjunction" next to this condition
			if ($(this).prev().is('hr')) {
				nextConjunctions++;
			}
		});
		if (nextConjunctions ==	(nextConditions-1)) {
			$("<hr><p class=andOrNotAdvancedSearchCenter>And</p><hr>").insertAfter($("#yourEasySearchFilterForm div[id='" + fieldName + "SearchDiv']"));
		}
	
	}

	/**
	 * This metod insert "and conjunction" before the SearchDiv of correspondents 
	 * field identified by fieldName.  
	 */
	function insertBeforeAndConjunction(fieldName){
		var conditionDivIndex =	$("#yourEasySearchFilterForm > div").index($("div[id='" + fieldName + "SearchDiv']"))
		//managing "and	conjunction" before condition  
		//console.log("Number of div containing	search condition : " + $("#yourEasySearchFilterForm div[id$=SearchDiv]").length);
		var previousConditions = 0;
		var previousConjunctions = 0;
		//we calculate div position of actual condition
		$("#yourEasySearchFilterForm div[id$=SearchDiv]").each(function(index) {
			if (index >= conditionDivIndex)
				return true;
			//count	previous filled	condition
			if ($(this).children().size()>0) {
				previousConditions++;
			}
			// count if how many and conjunctions are present
			if ($(this).next().is('hr')) {
				previousConjunctions++;
			}
		});
		if (previousConjunctions == (previousConditions-1)) {
			$("<hr><p class=andOrNotAdvancedSearchCenter>And</p><hr>").insertBefore($("#yourEasySearchFilterForm div[id='" + fieldName + "SearchDiv']"));
		}
	}

	/**
	 * This metod remove "and conjunction" after the SearchDiv of correspondents 
	 * field that contains input href selector.  
	 */
	function removeAfterAndConjunction(jqueryHrefSelector) {
		// we remove condition only if field condition contains 1 element
		if ($(jqueryHrefSelector).parent().parent().children().size()>1) {
			return;
		}

		var conditionDivIndex =	$("#yourEasySearchFilterForm > div").index($("div[id='" + $(jqueryHrefSelector).parent().parent().attr("id") + "']"));
		var nextConditions = 0;
		var nextConjunctions = 0;

		// we need to count any next conjunction and condition
		$("#yourEasySearchFilterForm div[id$=SearchDiv]").each(function(index) {
			if (index <= conditionDivIndex) {
				return true;
			}
			//count	previous filled	condition
			if ($(this).children().size()>0) {
				nextConditions++;
			}
			// check if is present "and conjunction" next to this condition
			if ($(this).prev().is('hr')) {
				nextConjunctions++;
			}
		});

		// too many conjunctions remove first conjunction after this condition div
		$("#yourEasySearchFilterForm div[id$=SearchDiv]").each(function(index) {
			if (nextConjunctions ==	nextConditions) {
				if (index < conditionDivIndex) {
					return true;
				}
				// check if is present "and conjunction" next to this condition
				if ($(this).next().is('hr')) {
					$(this).next().remove(); //<hr>
					$(this).next().remove(); //<p class=andOrNotAdvancedSearchCenter>And</p>
					$(this).next().remove(); //<hr>
					nextConjunctions--;
				}
			}
		});
		
		return true;
	}

	/**
	 * This metod insert "and conjunction" before the SearchDiv of correspondents 
	 * field identified by fieldName.  
	 */
	function removeBeforeAndConjunction(fieldName){
		var conditionDivIndex =	$("#yourEasySearchFilterForm > div").index($("div[id='" + fieldName + "SearchDiv']"))
		//managing "and	conjunction" before condition  
		//console.log("Number of div containing	search condition : " + $("#yourEasySearchFilterForm div[id$=SearchDiv]").length);
		var previousConditions = 0;
		var previousConjunctions = 0;
		//we calculate div position of actual condition
		$("#yourEasySearchFilterForm div[id$=SearchDiv]").each(function(index) {
			if (index >= conditionDivIndex)
				return true;
			//count	previous filled	condition
			if ($(this).children().size()>0) {
				previousConditions++;
			}
			// count if how many and conjunctions are present
			if ($(this).next().is('hr')) {
				previousConjunctions++;
			}
		});
		if (previousConjunctions == (previousConditions-1)) {
			$("<hr><p class=andOrNotAdvancedSearchCenter>And</p><hr>").insertBefore($("#yourEasySearchFilterForm div[id='" + fieldName + "SearchDiv']"));
		}
	}

	/**
	 * This method will reset every dateFields.
	 */
	function resetDateFields(formName, fieldName) {
		$('#' + formName).find('#dateYear').val("yyyy");
		$('#' + formName).find('#dateMonth option:eq(0)').attr('selected', 'selected');
		$('#' + formName).find('#dateDay').val("dd");
		$('#' + formName).find('#dateYearBetween').val("yyyy");
		$('#' + formName).find('#dateMonthBetween option:eq(0)').attr('selected', 'selected');
		$('#' + formName).find('#dateDayBetween').val("dd");
		return true;
	}

	/**
	 * 
	 */
	function resetBetweenField(formName, fieldName) {
		$('#' + formName).find('#' + fieldName).val("");
		$('#' + formName).find('#' + fieldName + 'Between').val("");
	}
})(jQuery);
