<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<b>ADVANCED SEARCH</b><a href="#" class="helpLink">?</a>
<p><u>Search Documents</u></p>
<br />
<div>
	<form id="wordSearchForm" method="post" class="edit">
		<label for="wordSearch" id="wordSearchLabel">Word Search: <a href="#" class="helpLink">?</a></label> 
		<input id="wordSearch" name="wordSearch" class="input_15c" type="text" value=""/>
		in 
		<select id="wordSearchIn" name="fromDateMonthSearch" class="selectform_MXlong">
			<option value="Synopsis and Extract" selected="selected">Synopsis and Extract</option>
			<option value="Document Synopsis">Document Synopsis</option>
			<option value="Document Extract">Document Extract</option>
		</select>
		<a href="/DocSources/de/docbase/AddWordSearch.html" id="AddWordSearch">Add</a>
	</form>
</div>

<div>
	<form id="volumeSearchForm" method="post" class="edit">
		<label for="volumeSearch" id="volumeSearchLabel">Volume:  <a href="#" class="helpLink">?</a></label> 
		<input id="volumeSearch" name="volumeSearch" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
		<a href="#" id="AddVolumeSearch">Add</a>
	</form>
</div>

<div>
	<form id="fromDateSearchForm" method="post" class="edit">
		<label for="fromDateSearch" id="fromDateSearchLabel">From Date:  <a href="#" class="helpLink">?</a></label>
		<input id="fromDateYearSearch" name="fromDateYearSearch" class="input_4c" type="text" value="year" maxlength="4"/>
		<select id="fromDateMonthSearch" name="fromDateMonthSearch" class="selectform">
			<option value="January">January</option>
			<option value="February">February</option>
			<option value="March">March</option>
			<option value="April">April</option>
			<option value="May">May</option>
			<option value="June">June</option>
			<option value="July">July</option>
			<option value="August">August</option>
			<option value="September">September</option>
			<option value="October">October</option>
			<option value="November">November</option>
			<option value="December">December</option>
			<option value="month" selected="selected">month</option>
		</select>
		<input id="fromDateDaySearch" name="fromDateDaySearch" class="input_2c" type="text" value="day" maxlength="2"/>
		<a href="#" id="addSearchFilter">Add</a>
	</form>
</div>

<div>
	<form id="toDateSearchForm" method="post" class="edit">
		<label for="toDateSearch" id="toDateSearchLabel">To Date:  <a href="#" class="helpLink">?</a></label>
		<input id="toDateYearSearch" name="toDateYearSearch" class="input_4c" type="text" value="year" maxlength="4"/>
		<select id="toDateMonthSearch" name="toDateMonthSearch" class="selectform">
			<option value="January">January</option>
			<option value="February">February</option>
			<option value="March">March</option>
			<option value="April">April</option>
			<option value="May">May</option>
			<option value="June">June</option>
			<option value="July">July</option>
			<option value="August">August</option>
			<option value="September">September</option>
			<option value="October">October</option>
			<option value="November">November</option>
			<option value="December">December</option>
			<option value="month" selected="selected">month</option>
		</select>
		<input id="toDateDaySearch" name="toDateDaySearch" class="input_2c" type="text" value="day" maxlength="2"/>
		<a href="#" id="addSearchFilter">Add</a>
	</form>
</div>

<div>
	<form id="extractSearchForm" method="post" class="edit">
		<label for="extractSearch" id="extractSearchLabel">Extract:  <a href="#" class="helpLink">?</a></label>
		<textarea id="extractSearch" name="extractSearch" class="txtadvsearch"></textarea>
		<a href="#" id="addSearchFilter">Add</a>
	</form>
</div>

<div>
	<form id="synopsisSearchForm" method="post" class="edit">
		<label for="synopsisSearch" id="synopsisSearchLabel">Synopsis:  <a href="#" class="helpLink">?</a></label>
		<textarea id="synopsisSearch" name="synopsisSearch" class="txtadvsearch"></textarea>
		<a href="#" id="addSearchFilter">Add</a>
	</form>
</div>

<div>
	<form id="topicsSearchForm" method="post" class="edit">
		<label for="topicsSearch" id="topicsSearchLabel">Topics:  <a href="#" class="helpLink">?</a></label> 
		<input id="topicsSearch" name="topicsSearch" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
		<a href="#" id="addSearchFilter">Add</a>
	</form>
</div>
<br />
<p><u>General search on People & Places:</u></p>
<br />
<div>
	<form id="personSearchForm" method="post" class="edit">
		<label for="personSearch" id="personSearchLabel">Person:  <a href="#" class="helpLink">?</a></label> 
		<input id="personSearch" name="personSearch" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
		<a href="#" id="addSearchFilter">Add</a>
	</form>
</div>

<div>
	<form id="placeSearchForm" method="post" class="edit">
		<label for="placeSearch" id="placeSearchLabel">Place:  <a href="#" class="helpLink">?</a></label> 
		<input id="placeSearch" name="placeSearch" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
		<a href="#" id="addSearchFilter">Add</a>
	</form>
</div>
<br />
<p><u>Specific search on People & Places:</u></p>
<br />
<div>
	<form id="senderSearchForm" method="post" class="edit">
		<label for="senderSearch" id="senderSearchLabel">Sender:  <a href="#" class="helpLink">?</a></label> 
		<input id="senderSearch" name="senderSearch" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
		<a href="#">Add</a>
	</form>
	<form id="fromSearchForm" method="post" class="edit">
		<label for="fromSearch" id="fromSearchLabel">From:  <a href="#" class="helpLink">?</a></label> 
		<input id="fromSearch" name="fromSearch" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
		<a href="#">Add</a>
	</form>
</div>

<div>
	<form id="recipientSearchForm" method="post" class="edit">
		<label for="recipientSearch" id="recipientSearchLabel">Recipient:  <a href="#" class="helpLink">?</a></label> 
		<input id="recipientSearch" name="recipientSearch" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
		<a href="#">Add</a>
	</form>
	<form id="toSearchForm" method="post" class="edit">
		<label for="toSearch" id="toSearchLabel">To:  <a href="#" class="helpLink">?</a></label> 
		<input id="toSearch" name="toSearch" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
		<a href="#">Add</a>
	</form>
</div>

<div>
	<form id="referstoSearchForm" method="post" class="edit">
		<label for="referstoSearch" id="referstoSearchLabel">Refers to:  <a href="#" class="helpLink">?</a></label> 
		<input id="referstoSearch" name="referstoSearch" class="input_20c" type="text" value=""/><!-- AUTOCOMPLETE -->
		<a href="#">Add</a>
	</form>
</div>
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#AddWordSearch").click(function(){
				var searchElement = "				<div class=\"searchFilterDiv\">																		   " +
				"					<span class=\"categorySearch\">Word Search</span>                                                  " +
				"					in                                                                                                 " +
				"					<span class=\"categorySearch\">" + $j("#wordSearchIn").val()+ "</span>                                        " +
				"					<span class=\"wordSearch\">" + $j("#wordSearch").val() + "</span>                                                            " +
				"					<a class=\"remove\" href=\"#\">(remove)</a>                                                        " +
				"				</div>                                                                                                 " +
				"				                                                                                                       " +
				"				<div class=\"andOrNotAdvancedSearchDiv\">                                                              " +
				"					<select id=\"andOrNotAdvancedSearch\" name=\"andOrNotAdvancedSearch\" class=\"selectform_medium\"> " +
				"						<option value=\"\" selected=\"selected\"></option>                                             " +
				"						<option value=\"And\">And</option>                                                             " +
				"						<option value=\"Or\">Or</option>                                                               " +
				"						<option value=\"Not\">Not</option>                                                             " +
				"						<option value=\"And (\">And (</option>                                                         " +
				"						<option value=\"Or (\">Or (</option>                                                           " +
				"						<option value=\")\">)</option>                                                                 " +
				"					</select>                                                                                          " +
				"				</div>                                                                                                 " +
				"			                                                                                                           ";

				if ($j("#wordSearchDiv").length ==0) {
					$j("#customSearchFilterForm").append("<div id=\"wordSearchDiv\">" +
					"				<hr>" +
					"</div>" +
					"	<div class=\"andOrNotAdvancedSearchDiv\"> " +
					"		<select class=\"selectform_medium\" name=\"andOrNotAdvancedSearch\" id=\"andOrNotAdvancedSearch\"> " +
					"			<option selected=\"selected\" value=\"And\">And</option> " +
					"			<option value=\"Or\">Or</option> " +
					"			<option value=\"Not\">Not</option> " +
					"		</select> " +
					"	</div> " +
					"	<hr>"
					);
				} 

				if ($j("#wordSearchDiv").children(".searchFilterDiv").length == 0) {
					$j("#wordSearchDiv").prepend(searchElement);
				} else {
					$j("#wordSearchDiv hr:last").before(searchElement);
				}
				return false;
			});
				
			$j("#AddVolumeSearch").click(function(){
				var searchElement = "				<div class=\"searchFilterDiv\">" +
				"					<span class=\"categorySearch\">Volume Search</span> " +
				"					in                                                                                                 " +
				"					<span class=\"categorySearch\">Volume: </span>                                        " +
				"					<span class=\"wordSearch\">" + $j("#volumeSearch").val() + "</span>                                                            " +
				"					<a class=\"remove\" href=\"#\">(remove)</a>                                                        " +
				"				</div>                                                                                                 " +
				"				                                                                                                       " +
				"				<div class=\"andOrNotAdvancedSearchDiv\">                                                              " +
				"					<select id=\"andOrNotAdvancedSearch\" name=\"andOrNotAdvancedSearch\" class=\"selectform_medium\"> " +
				"						<option value=\"\" selected=\"selected\"></option>                                             " +
				"						<option value=\"And\">And</option>                                                             " +
				"						<option value=\"Or\">Or</option>                                                               " +
				"						<option value=\"Not\">Not</option>                                                             " +
				"						<option value=\"And (\">And (</option>                                                         " +
				"						<option value=\"Or (\">Or (</option>                                                           " +
				"						<option value=\")\">)</option>                                                                 " +
				"					</select>                                                                                          " +
				"				</div>                                                                                                 " +
				"			                                                                                                           ";

				if ($j("#volumeSearchDiv").length ==0) {
					$j("#customSearchFilterForm").append("<div id=\"volumeSearchDiv\">" +
					"				<hr>" +
					"</div>" +
					"	<div class=\"andOrNotAdvancedSearchDiv\"> " +
					"		<select class=\"selectform_medium\" name=\"andOrNotAdvancedSearch\" id=\"andOrNotAdvancedSearch\"> " +
					"			<option selected=\"selected\" value=\"And\">And</option> " +
					"			<option value=\"Or\">Or</option> " +
					"			<option value=\"Not\">Not</option> " +
					"		</select> " +
					"	</div> " +
					"	<hr>"
					);
				} 

				if ($j("#volumeSearchDiv").children(".searchFilterDiv").length == 0) {
					$j("#volumeSearchDiv").prepend(searchElement);
				} else {
					$j("#volumeSearchDiv hr:last").before(searchElement);
					return false;
				}

				$j("#volumeSearchDiv").prepend(searchElement);
			});			
		});

	</script>