<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<h2>SEARCH FORM</h2>

<div id="multiOpenAccordion">
	<h1><a>WORD SEARCH</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form id="wordSearchForm" method="post" class="edit">
		<fieldset>
			<input type="text" id="wordSearch" class="input_15c" />
			in 
			<select id="fromDateMonthSearch" class="selectform_LXlong">
				<option value="Synopsis and Extract" selected="selected">Synopsis and Extract</option>
				<option value="Document Synopsis">Document Synopsis</option>
				<option value="Document Extract">Document Extract</option>				
			</select>
			<a href="/DocSources/de/docbase/AddWordSearch.html" id="AddWordSearch">Add</a>
		</fieldset>
		</form>
	</div>

	<h1><a><i>in</i> VOLUME</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form id="volumeSearchForm" method="post" class="edit">
		<fieldset>
			<select id="volumeSearchExactlyBetween" class="selectform_long">
				<option value="Exactly" selected="selected">Exactly</option>
				<option value="Between">Between</option>
			</select>
			<input type="text" id="volumeSearch"  class="input_5c" maxlength="5"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addVolumeSearch">Add</a>
		</fieldset>
		</form>
		
		<a href="#" class="helpLinkFrom">?</a>
		<form id="dateSearchForm" method="post" class="edit">
		<fieldset>
			<select id="dateSearch" class="selectform_long">
				<option value="From Date">From Date</option>
				<option value="To Date">To Date</option>
				<option value="Before">Before</option>
				<option value="After">After</option>
			</select>
			<input type="text" id="fromDateYearSearch" class="input_4c" maxlength="4"/>
			<select id="fromDateMonthSearch" class="selectform">
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
				<option value="month">month</option>
			</select>
			<input type="text" id="fromDateDaySearch" class="input_2c" maxlength="2"/>
			<a href="#" id="addDateSearch">Add</a>
		</fieldset>
		</form>
	</div>	

	<h1><a><i>in</i> EXTRACT and/or SYNOPSIS</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form id="extractSearchForm" method="post" class="edit">
		<fieldset>
			<label for="extractSearch" id="extractSearchLabel">Extract</label>
			<textarea id="extractSearch" class="txtadvsearch"></textarea>
			<a href="#" id="addExtractSearch">Add</a>
		</fieldset>
		</form>
		
		<form id="synopsisSearchForm" method="post" class="edit">
		<fieldset>
			<label for="synopsisSearch" id="synopsisSearchLabel">Synopsis</label>
			<textarea id="synopsisSearch" class="txtadvsearch"></textarea>
			<a href="#" id="addSynopsisSearch">Add</a>
		</fieldset>
		</form>
	</div>
	
	<h1><a><i>with</i> TOPICS</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form id="topicsSearchForm" method="post" class="edit">
		<fieldset>
			<input type="text" id="topicsSearch" class="input_25c"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addTopicsSearch">Add</a>
		</fieldset>
		</form>
	</div>

	<h1><a><i>search on</i> PEOPLE &amp; PLACES</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form id="personSearchForm" method="post" class="edit">
		<fieldset>
			<label for="personSearch" id="personSearchLabel">Person</label> 
			<input type="text" id="personSearch" class="input_25c" type="text" value=""/><!-- AUTOCOMPLETE -->
			<a href="#" id="addPersonSearch">Add</a>
		</fieldset>
		</form>
		<form id="placeSearchForm" method="post" class="edit">
		<fieldset>
			<label for="placeSearch" id="placeSearchLabel">Place</label> 
			<input type="text" id="placeSearch" class="input_25c"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addPlaceSearch">Add</a>
		</fieldset>
		</form>
		
		<form id="senderSearchForm" method="post" class="edit">
			<fieldset>
				<label for="senderSearch" id="senderSearchLabel">Sender</label> 
				<input type="text" id="senderSearchAutoCompleter" class="input_25c"/><!-- AUTOCOMPLETE -->
				<a href="#" id="addSenderSearch">Add</a>
				
			</fieldset>
		</form>
				
		
		
		<form id="fromSearchForm" method="post" class="edit">
		<fieldset>
			<label for="fromSearch" id="fromSearchLabel">From</label> 
			<input type="text" id="fromSearch" class="input_25c"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addFromSearch">Add</a>
		</fieldset>
		</form>
		
		<form id="recipientSearchForm" method="post" class="edit">
		<fieldset>
			<label for="recipientSearch" id="recipientSearchLabel">Recipient</label> 
			<input type="text" id="recipientSearch" class="input_25c"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addRecipientSearch">Add</a>
		</fieldset>
		</form>
		<form id="toSearchForm" method="post" class="edit">
		<fieldset>
			<label for="toSearch" id="toSearchLabel">To</label> 
			<input type="text" id="toSearch" class="input_25c"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addToSearch">Add</a>
		</fieldset>
		</form>
		
		<form id="referstoSearchForm" method="post" class="edit">
		<fieldset>
			<label for="referstoSearch" id="referstoSearchLabel">Refers to</label> 
			<input type="text" id="referstoSearch" class="input_25c"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addReferstoSearch">Add</a>
		</fieldset>
		</form>
	</div>
</div>

<c:url var="searchSenderPeopleURL" value="/de/peoplebase/SearchSenderPeople.json"/>
	<script type="text/javascript">
		$j(document).ready(function() {
			$j('#multiOpenAccordion').multiAccordion({active: [0]});

			$j("#senderSearchAutoCompleter").autocompletePerson({
				serviceUrl: '${searchSenderPeopleURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 600,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j('#senderPeopleId').val(data);
				}
			});	
			
			$j("#AddWordSearch").click(function(){
				var searchElement = "				<div class=\"searchFilterDiv\">																		   " +
				"					<span class=\"categorySearch\">Word Search</span>                                                  " +
				"					in                                                                                                 " +
				"					<span class=\"categorySearch\">" + $j("#wordSearchIn").val()+ "</span>                                        " +
				"					<span class=\"wordSearch\">" + $j("#wordSearch").val() + "</span>                                                            " +
				"					<a class=\"remove\" href=\"#\">(remove)</a>                                                        " +
				"				</div>                                                                                                 " +
				"				                                                                                                       " +
				"				<p class=\"andOrNotAdvancedSearch\">And</p>   " +
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
				"				<p class=\"andOrNotAdvancedSearch\">And</p>   " +
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