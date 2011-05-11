<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<b>ADVANCED SEARCH</b><a href="#" class="helpLink">?</a>

<br />
<br />

<div id="multiOpenAccordion">
	<h1><a>WORD SEARCH</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form:form id="wordSearchForm" method="post" cssClass="edit">
		<fieldset>
			<form:input id="wordSearch" path="wordSearch" cssClass="input_15c" />
			in 
			<form:select id="fromDateMonthSearch" path="fromDateMonthSearch" cssClass="selectform_LXlong">
				<form:option value="Synopsis and Extract" selected="selected">Synopsis and Extract</form:option>
				<form:option value="Document Synopsis">Document Synopsis</form:option>
				<form:option value="Document Extract">Document Extract</form:option>				
			</form:select>
			<a href="/DocSources/de/docbase/AddWordSearch.html" id="AddWordSearch">Add</a>
		</fieldset>
		</form:form>
	</div>

	<h1><a><i>in</i> VOLUME</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form:form id="volumeSearchForm" method="post" cssClass="edit">
		<fieldset>
			<form:select id="volumeSearchExactlyBetween" path="volumeSearchExactlyBetween" cssClass="selectform_long">
				<form:option value="Exactly" selected="selected">Exactly</form:option>
				<form:option value="Between">Between</form:option>
			</form:select>
			<form:input id="volumeSearch" path="volumeSearch" cssClass="input_5c" maxlength="5"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addSearchFilter">Add</a>
		</fieldset>
		</form:form>
		
		<a href="#" class="helpLinkFrom">?</a>
		<form:form id="dateSearchForm" method="post" cssClass="edit">
		<fieldset>
			<form:select id="dateSearch" path="dateSearch" class="selectform_long">
				<form:option value="From Date">From Date</form:option>
				<form:option value="To Date">To Date</form:option>
				<form:option value="Before">Before</form:option>
				<form:option value="After">After</form:option>
			</form:select>
			<form:input id="fromDateYearSearch" path="fromDateYearSearch" cssClass="input_4c" maxlength="4"/>
			<form:select id="fromDateMonthSearch" path="fromDateMonthSearch" class="selectform">
				<form:option value="January">January</form:option>
				<form:option value="February">February</form:option>
				<form:option value="March">March</form:option>
				<form:option value="April">April</form:option>
				<form:option value="May">May</form:option>
				<form:option value="June">June</form:option>
				<form:option value="July">July</form:option>
				<form:option value="August">August</form:option>
				<form:option value="September">September</form:option>
				<form:option value="October">October</form:option>
				<form:option value="November">November</form:option>
				<form:option value="December">December</form:option>
				<form:option value="month">month</form:option>
			</form:select>
			<form:input id="fromDateDaySearch" path="fromDateDaySearch" cssClass="input_2c" maxlength="2"/>
			<a href="#" id="addSearchFilter">Add</a>
		</fieldset>
		</form:form>
	</div>	

	<h1><a><i>in</i> EXTRACT and/or SYNOPSIS</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form:form id="extractSearchForm" method="post" cssClass="edit">
		<fieldset>
			<form:label for="extractSearch" id="extractSearchLabel" path="extractSearch">Extract</form:label>
			<form:textarea id="extractSearch" path="extractSearch" cssClass="txtadvsearch"></form:textarea>
			<a href="#" id="addSearchFilter">Add</a>
		</fieldset>
		</form:form>
		
		<form:form id="synopsisSearchForm" method="post" cssClass="edit">
		<fieldset>
			<form:label for="synopsisSearch" id="synopsisSearchLabel" path="sysopsisSearch">Synopsis</form:label>
			<form:textarea id="synopsisSearch" path="synopsisSearch" cssClass="txtadvsearch"></form:textarea>
			<a href="#" id="addSearchFilter">Add</a>
		</fieldset>
		</form:form>
	</div>
	
	<h1><a><i>with</i> TOPICS</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form:form id="topicsSearchForm" method="post" cssClass="edit">
		<fieldset>
			<form:input id="topicsSearch" path="topicsSearch" cssClass="input_25c"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addSearchFilter">Add</a>
		</fieldset>
		</form:form>
	</div>

	<h1><a><i>search on</i> PEOPLE & PLACES</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form:form id="personSearchForm" method="post" cssClass="edit">
		<fieldset>
			<form:label for="personSearch" id="personSearchLabel" path="personSearch">Person</form:label> 
			<form:input id="personSearch" path="personSearch" class="input_25c" type="text" value=""/><!-- AUTOCOMPLETE -->
			<a href="#" id="addSearchFilter">Add</a>
		</fieldset>
		</form:form>
		<form:form id="placeSearchForm" method="post" cssClass="edit">
		<fieldset>
			<form:label for="placeSearch" id="placeSearchLabel" path="placeSearch">Place</form:label> 
			<form:input id="placeSearch" path="placeSearch" cssClass="input_25c"/><!-- AUTOCOMPLETE -->
		</fieldset>
		</form:form>
		
		<form:form id="senderSearchForm" method="post" cssClass="edit">
			<fieldset>
				<form:label for="senderSearch" id="senderSearchLabel" path="ssenderSearch">Sender</form:label> 
				<form:input id="senderSearchAutoCompleter" path="senderSearch" cssClass="input_25c"/><!-- AUTOCOMPLETE -->
				<a href="#" id="addSearchFilter">Add</a>
				
			</fieldset>
		</form:form>
				
		
		
		<form:form id="fromSearchForm" method="post" cssClass="edit">
		<fieldset>
			<form:label for="fromSearch" id="fromSearchLabel" path="fromSearch">From</form:label> 
			<form:input id="fromSearch" path="fromSearch" cssClass="input_25c"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addSearchFilter">Add</a>
		</fieldset>
		</form:form>
		
		<form:form id="recipientSearchForm" method="post" cssClass="edit">
		<fieldset>
			<form:label for="recipientSearch" id="recipientSearchLabel" path="recipientSearch">Recipient</form:label> 
			<form:input id="recipientSearch" path="recipientSearch" cssClass="input_25c"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addSearchFilter">Add</a>
		</fieldset>
		</form:form>
		<form:form id="toSearchForm" method="post" cssClass="edit">
		<fieldset>
			<form:label for="toSearch" id="toSearchLabel" path="toSearch">To</form:label> 
			<form:input id="toSearch" path="toSearch" cssClass="input_25c"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addSearchFilter">Add</a>
		</fieldset>
		</form:form>
		
		<form:form id="referstoSearchForm" method="post" cssClass="edit">
		<fieldset>
			<form:label for="referstoSearch" id="referstoSearchLabel" path="referstoSearch">Refers to</form:label> 
			<form:input id="referstoSearch" path="referstoSearch" cssClass="input_25c"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addSearchFilter">Add</a>
		</fieldset>
		</form:form>
	</div>
</div>

<c:url var="searchSenderPeopleURL" value="/de/peoplebase/SearchSenderPeople.json"/>
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#multiOpenAccordion").multiAccordion({active: [0]});

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