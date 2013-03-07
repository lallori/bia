/**
*  Advanced Search plugin for jQuery, version 1.5
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 * 
 * Last Review: 07/23/2011
*/
(function ($) {

	$.fn.reverse = function() {
	    return this.pushStack(this.get().reverse(), arguments);
	};

	$.advancedSearchForm = {};

	$.advancedSearchForm.defaults = {
		AdvancedSearchCountURL : "",
		consoleLog : true
	};

	$.fn.advancedSearchForm = function (options) {
		var options = $.extend($.advancedSearchForm.defaults, options);

		addListenerRemoveCondition();
		
		// We manage submit button to populate right frame
		$(this).submit(function (event) {
			//Prevent the browser's default onClick handler
			event.preventDefault();

			// Extract form Name
			var formName = $(this).attr('id');
			//Extracting field on which this form works on.
			var fieldName = getSearchFieldName($(this));
			var searchCategory = getSearchCategory(formName, fieldName);
			var searchType = getSearchType(formName, fieldName);

			if (options["consoleLog"] == true) {
				console.log("AdvancedSearchForm started. Form Name : " + formName);
				console.log("Field Name : " + fieldName);
				console.log("Search Category : " + searchCategory);
			}

			var searchWord = "";
			var hiddenValue = "";
			
			// Manage form with type combobox
			if (hasSearchType(formName, fieldName)) {
				//Date form
				if (isDateForm(formName)) {
					if ($(this).find("option:selected").val() == 'Between' || $(this).find("option:selected").val() == "Lived between") {
						if (checkDateFieldsBetweenAreEmpty(formName, fieldName)) {
							return true;
						}

						searchWord = getSearchWordForDateBetween(formName, fieldName);
						hiddenValue = getHiddenParameterForDate(formName);
					} else {
						searchWord = getSearchWordForDateSingle(formName, fieldName);
						hiddenValue = getHiddenParameterForDate(formName);
					}

					resetDateFields(formName, fieldName);
				} else if (isVolumeForm(formName) || isFolioForm(formName)) {
					if ($(this).find("option:selected").val() == 'Between') {
						searchWord = getSearchWordForBetweenField(formName, fieldName);
						hiddenValue = $(this).find("option:selected").val() + "|" + $('#' + formName).find('#' + fieldName).val() + '|' + $('#' + formName).find('#' + fieldName + 'Between').val();
						//Reset input fields
						resetBetweenField(formName, fieldName);
					} else if ($(this).find("option:selected").val() == 'Exactly') {
						searchWord = $('#' + formName).find('#' + fieldName).val();
						hiddenValue = $(this).find("option:selected").text() + "|" + searchWord;
						$('#' + formName).find('#' + fieldName).val("");
					} 
				}else if(isAutocompleterForm(formName, fieldName)){
					//MD: In this case we have a select with an autocompleter field. For example the section "Birth/Death Place" in Person search.
					//MD: Changed the searchType to have the correct category Search.
					if(isTopicForm(formName)){
						//Topic FIeld						
						searchType = "Topic";
						searchWord = $(this).find("option:selected").text() + " ";
						searchWord += "<i>" + getSearchWordForAutocompleterField(formName, fieldName) + "</i>";
						hiddenValue = $(this).find("option:selected").val() + "|" + $(this).find("option:selected").text() + "|";
						hiddenValue += getHiddenParameterForAutocompleterField(formName, fieldName);
					}else{
						searchType = $(this).find("option:selected").val();
						searchWord = getSearchWordForAutocompleterField(formName, fieldName);
						hiddenValue = $(this).find("option:selected").val() + "|";
						hiddenValue += getHiddenParameterForAutocompleterField(formName, fieldName);
					}
				}else {
					searchWord = $('#' + formName).find('#' + fieldName).val();
					hiddenValue = $(this).find("option:selected").val() + "|" + escape($('#' + formName).find('#' + fieldName).val());
					$('#' + formName).find('#' + fieldName).val("");
				}
			} else {
				if (isAutocompleterForm(formName, fieldName)) {
					searchWord = getSearchWordForAutocompleterField(formName, fieldName);
					hiddenValue = getHiddenParameterForAutocompleterField(formName, fieldName);
					resetAutocompleterField(formName, fieldName);
				} else {
					searchWord = getSearchWordForNormalField(formName, fieldName);
					hiddenValue = getHiddenParameterForNormalField(formName, fieldName);
					resetNormalField(formName, fieldName);
				}
			}
			
			//Manage form type checkbox
			if(isCheckboxForm(formName)){
				searchWord = getSearchWordForCheckboxField(formName, fieldName);
				hiddenValue = getHiddenParameterForCheckboxField(formName, fieldName);
				resetCheckboxField(formName, fieldName);
			}
			
			//MD: This is for ignore the apostrophe because the link generated is wrong if it's present.
			//hiddenValue = hiddenValue.replace("'", "")
			if (options["consoleLog"] == true) {
				console.log("Searching : " + searchWord);
				console.log("Final hidden parameter (" + formName + ") value: " + hiddenValue);
			}

			// If the searchWord is null, can't be inserted to the filter
			if(searchWord == null || searchWord == ""){
				return false;
			}
			
			// Some field can't be entered more than once
			if(fieldName == "digitized" && $("#" + fieldName + "SearchDiv").find(".searchFilterDiv").length > 0){
				return false;
			}
			if(fieldName == "cipher" && $("#" + fieldName + "SearchDiv").find(".searchFilterDiv").length > 0){
				return false;
			}
			if(fieldName == "index" && $("#" + fieldName + "SearchDiv").find(".searchFilterDiv").length > 0){
				return false;
			}
//			if(fieldName == "volume" && $("#" + fieldName + "SearchDiv").find(".searchFilterDiv").length > 0){
//				return false;
//			}
			
			// We construct new html block with condition and hidden field 
			var searchFilterDiv = getSearchFilterDiv(formName, fieldName, searchType, searchWord, hiddenValue);

			if ($("#" + fieldName + "SearchDiv").find(".searchFilterDiv").length >0) {
				$("#" + fieldName + "SearchDiv").find(".searchFilterDiv").last().after('<p class="andOrNotAdvancedSearch">And</p>');
			}

			// we manage and conjunction
			insertBeforeAndConjunction(fieldName);
			insertAfterAndConjunction(fieldName);

			// We append new block at the end of "field" SearchDiv 
			$("#" + fieldName + "SearchDiv").append(searchFilterDiv);

			if (options["consoleLog"] == true) {
				console.log("AdvancedSearchForm " + formName + " completed.");
			}
			
 			$j.ajax({ type:"POST", url:options["AdvancedSearchCountURL"], data:$j("#yourEasySearchFilterForm").serialize(), async:false, success:function(json) {
 				// At this point we have count of total result. Review output page and put the total...
 				if (options["consoleLog"] == true) {
 					console.log("Advanced search result " + json.totalResult);
 				}
 				$j(".recordsNum").text(json.totalResult);
			}});
 			
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

			// we extract main div of this href condition (example volumeSearchDiv), we need it to remove "and conjunction"
			var divSearch = $(this).parent().parent();

			// we remove parent container element which is condition.
			$(this).parent().remove();

			removeAfterAndConjunction($(divSearch));
			removeBeforeAndConjunction($(divSearch));
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
	function getSearchFilterDiv(formName, fieldName, searchType, searchWord, hiddenValue) {
		var retValue = $('<div class="searchFilterDiv">');
		$(retValue).append('<span class="categorySearch">' + searchType + ': </span>');
		$(retValue).append('<span class="wordSearch">' + searchWord + '</span>');
		$(retValue).append('<a href="#" class="remove">(remove)</a>');
		$(retValue).append('<input type="hidden" name="' + fieldName + '" value="' + hiddenValue + '"/>');
		return retValue;
	}

	/**
	 * This function return hidden parameter to diplay for autocompleter field.
	 */
	function getHiddenParameterForAutocompleterField(formName, fieldName) {
		//Calculate hidden value (escaping text field to prevent field truncation
		return $('#' + formName).find('#' + fieldName + 'Id').val() + "|" + escape($('#' + formName).find('#' + fieldName).val());
	}

	/**
	 * This method return hidden parameter for normal field  
	 */
	function getHiddenParameterForNormalField(formName, fieldName) {
		return escape($('#' + formName).find('#' + fieldName).val());
	}

	/**
	 * This method return hidden parameter for date field in between condition
	 */
	function getHiddenParameterForDate(formName) {
		var hiddenValue = $("#" + formName).find("#dateType").find("option:selected").val() + "|";
		if ($("#" + formName).find("#dateYear").val() != "yyyy") {
			hiddenValue += $("#" + formName).find("#dateYear").val();
		}
		hiddenValue += "|";
		if ( $("#" + formName).find("#dateMonth").find("option:selected").text() != "mm") {
			hiddenValue += $("#" + formName).find("#dateMonth").find("option:selected").val();
		}
		hiddenValue += "|";
		if ($("#" + formName).find("#dateDay").val() != "dd") {
			hiddenValue += $("#" + formName).find("#dateDay").val();
		}
		hiddenValue += "|"; 
		if ($("#" + formName).find("#dateYearBetween").val() != "yyyy") {
			hiddenValue += $("#" + formName).find("#dateYearBetween").val();
		}
		hiddenValue += "|";
		if ( $("#" + formName).find("#dateMonthBetween").find("option:selected").text() != "mm") {
			hiddenValue += $("#" + formName).find("#dateMonthBetween").find("option:selected").val();
		}
		hiddenValue += "|";
		if ($("#" + formName).find("#dateDayBetween").val() != "dd") {
			hiddenValue += $("#" + formName).find("#dateDayBetween").val();
		}

		return hiddenValue;
	}
	
	/**
	 * This method return hidden parameter for checkbox field
	 */
	function getHiddenParameterForCheckboxField(formName, fieldName){
		var hiddenValue = '';
		var values = [];
		$('#' + formName).find('input[type=checkbox]:checked').each(function() {
		       values.push($(this).val());
		});
		for(i = 0; i < values.length; i++){
			if(i == 0)
				hiddenValue += values[0];
			else{
				hiddenValue += ' ' + values[i];
			}
		}
		return hiddenValue;
	}

	/**
	 * This method returns search category.   
	 */
	function getSearchCategory(formName, fieldName) {
		return $('#' + formName).find('#category').val();;
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
			retValue = $('#' + formName).find('#' + fieldName + 'Type').find('option:selected').text();
		} else {
			retValue = $('#' + formName).find('#category').val();
			if ($('#' + formName).find('#' + fieldName + 'Type').length==1) {
				retValue += ' in ' + $('#' + formName).find('#' + fieldName + 'Type').find('option:selected').text();
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
	 * This function return search word to diplay for normal field (no searchType, no autocomplteer).
	 */
	function getSearchWordForNormalField(formName, fieldName) {
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
	function getSearchWordForDateBetween(formName, fieldName) {
		var searchWord = '';
		if ($('#' + formName).find('#dateYear').val() != 'yyyy')
			searchWord = $('#' + formName).find('#dateYear').val();
		if ( $('#' + formName).find('#dateMonth').find('option:selected').text() != 'mm')
			searchWord += ' ' + $('#' + formName).find('#' + fieldName + 'Month').find('option:selected').text();
		if ($('#' + formName).find('#dateDay').val() != 'dd')
			searchWord += ' ' + $('#' + formName).find('#dateDay').val();
		searchWord += ' and'; 
		if ($('#' + formName).find('#dateYearBetween').val() != 'yyyy')
			searchWord += ' ' + $('#' + formName).find('#dateYearBetween').val();
		if ( $('#' + formName).find('#dateMonthBetween').find('option:selected').text() != 'mm')
			searchWord += ' ' + $('#' + formName).find('#dateMonthBetween').find('option:selected').text();
		if ($('#' + formName).find('#dateDayBetween').val()	!= 'dd')
			searchWord += ' ' + $('#' + formName).find('#dateDayBetween').val();
		
		return searchWord;
	}

	/**
	 * This function return search word to diplay for a field which is composed of two elements.
	 */
	function getSearchWordForDateSingle(formName, fieldName) {
		var searchWord = '';
		if ($('#' + formName).find('#dateYear').val() != 'yyyy') {
			searchWord += $('#' + formName).find('#dateYear').val();
		}
		if ( $('#' + formName).find('#dateMonth').find('option:selected').text() != 'mm') {
			searchWord += ' ' + $('#' + formName).find('#dateMonth').find('option:selected').text();
		}
		if ($('#' + formName).find('#dateDay').val() != 'dd') {
			searchWord += ' ' + $('#' + formName).find('#dateDay').val();
		}
		return searchWord;
	}
	
	/**
	 * This function return search word for field languages which is composed by checkbox.
	 */
	function getSearchWordForCheckboxField(formName, fieldName){
		var searchWord = '';
		var values = [];
		$('#' + formName).find('input[type=checkbox]:checked').each(function() {
		       values.push($(this).val());
		});
		for(i = 0; i < values.length; i++){
			if(i == 0)
				searchWord += values[0];
			else{
				searchWord += ' ' + values[i];
			}
		}
		return searchWord;
	}
	
	/**
	 * This method returns type of this search form.   
	 */
	function hasSearchType(formName, fieldName) {
		if ($('#' + formName).find('#' + fieldName + 'Type').length==1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This metod insert "and conjunction" after the SearchDiv of correspondents 
	 * field identified by fieldName.  
	 */
	function insertAfterAndConjunction(fieldName) {
		var conditionDivIndex = $("#yourEasySearchFilterForm > div").index($("div[id='" + fieldName + "SearchDiv']"))
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
	 * This method check if form is an autocompleter.
	 */
	function isAutocompleterForm(formName, fieldName) {
		if ($('#' + formName).find('#' + fieldName + 'Id').size() > 0) {
			console.log("Form has an autocompleter element");
			return true;
		}
		return false;
	}
	
	/*function isAutocompleterAndSelectForm(formName, fieldName) {
		if($('#' + formName).find('#' + fieldName + 'Id').size() > 0 && $('#' + formName).find('#' + fieldName + 'Type').size > 0){
			return true;
		}else
			return false;
	}*/
	
	/**
	 * This method check if form is composed of checkbox
	 */
	function isCheckboxForm(formName){
		if($('#' + formName).find('input[type=checkbox]').length > 0)
			return true;
		else
			return false;
	}

	/**
	 * This method check if form manage date.
	 */
	function isDateForm(formName) {
		if (formName.indexOf("date") >= 0)
			return true;
		else
			return false;
	}
	
	/**
	 * This method check if form manage folio.
	 */
	function isFolioForm(formName) {
		if (formName.indexOf("folio") >= 0)
			return true;
		else
			return false;
	}
	
	/**
	 * This method check if form manage topic.
	 */
	function isTopicForm(formName) {
		if (formName.indexOf("topic") >= 0)
			return true;
		else
			return false;
	}

	/**
	 * This method check if form manage volume.
	 */
	function isVolumeForm(formName) {
		if (formName.indexOf("volume") >= 0)
			return true;
		else
			return false;
	}

	/**
	 * This metod remove "and conjunction" after the SearchDiv of correspondents 
	 * field that contains input href selector.  
	 */
	function removeAfterAndConjunction(jqueryHrefSelector) {
		// we remove condition only if field condition contains at least 1 element
		if ($(jqueryHrefSelector).children().size()>0) {
			return;
		}

		var conditionDivIndex =	$("#yourEasySearchFilterForm > div").index($("div[id='" + $(jqueryHrefSelector).attr("id") + "']"));
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
	 * This metod remove "and conjunction" after the SearchDiv of correspondents 
	 * field that contains input href selector.  
	 */
	function removeBeforeAndConjunction(jqueryHrefSelector){
		// we remove condition only if field condition contains at least 1 element
		if ($(jqueryHrefSelector).children().size()>0) {
			return;
		}

		var conditionDivIndex =	$("#yourEasySearchFilterForm > div").reverse().index($("div[id='" + $(jqueryHrefSelector).attr("id") + "']"));
		var prevConditions = 0;
		var prevConjunctions = 0;


		// we need to count any next conjunction and condition
		$("#yourEasySearchFilterForm div[id$=SearchDiv]").reverse().each(function(index) {
			if (index >= conditionDivIndex) {
				return true;
			}
			//count	previous filled	condition, we skip current condition element
			if (($(this).children().size()>0) && (index != conditionDivIndex)) {
				prevConditions++;
			}
			// check if is present "and conjunction" next to this condition
			if ($(this).next().is('hr')) {
				prevConjunctions++;
			}
		});

		// too many conjunctions remove first conjunction after this condition div
		$("#yourEasySearchFilterForm div[id$=SearchDiv]").reverse().each(function(index) {
			if ((prevConjunctions == prevConditions)) {
				if (index <= conditionDivIndex) {
					return true;
				}
				// check if is present "and conjunction" next to this condition
				if ($(this).next().is('hr')) {
					$(this).next().remove(); //<hr>
					$(this).next().remove(); //<p class=andOrNotAdvancedSearchCenter>And</p>
					$(this).next().remove(); //<hr>
					prevConjunctions--;
				}
			}
		});
		
		return true;
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
	 * This method will reset a between fields (not date).
	 */
	function resetBetweenField(formName, fieldName) {
		$('#' + formName).find('#' + fieldName).val("");
		$('#' + formName).find('#' + fieldName + 'Between').val("");
	}

	/**
	 * This method will reset an autocompleter field (not date).
	 */
	function resetAutocompleterField(formName, fieldName) {
		// we reset autocompleterId and correspondent field
		$('#' + formName).find('#' + fieldName + 'Id').val("");
		$('#' + formName).find('#' + fieldName).val("");
	}

	/**
	 * This method will reset a normal field.
	 */
	function resetNormalField(formName, fieldName) {
		$('#' + formName).find('#' + fieldName).val("");
	}
	
	/**
	 * This method will reset a checkbox field
	 */
	function resetCheckboxField(formName, fieldName){
		$('#' + formName).find('input[type=checkbox]:checked').each(function() {
		       $(this).attr('checked', false);
		});
	}

}) (jQuery);
