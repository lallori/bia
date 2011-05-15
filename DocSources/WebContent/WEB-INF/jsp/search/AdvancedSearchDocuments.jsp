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
		<form id="wordForm" method="post" class="edit">
		<fieldset>
			<input type="text" id="word" class="input_15c" />
			in 
			<select id="wordType" class="selectform_LXlong">
				<option value="Synopsis and Extract" selected="selected">Synopsis and Extract</option>
				<option value="Document Synopsis">Document Synopsis</option>
				<option value="Document Extract">Document Extract</option>				
			</select>
			<input type="submit" value="add">
			<input type="hidden" id="category" value="Word Search">
		</fieldset>
		</form>
	</div>

	<h1><a><i>in</i> VOLUME</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form id="volumeForm" method="post" class="edit">
		<fieldset>
			<select id="volumeType" class="selectform_long">
				<option value="Exactly" selected="selected">Exactly</option>
				<option value="Between">Between</option>
			</select>
			<input type="text" id="volume"  class="input_5c" maxlength="5"/><!-- AUTOCOMPLETE -->
			<input type="submit" value="add">
			<input type="hidden" id="category" value="Volume">
		</fieldset>
		</form>
		
		<a href="#" class="helpLinkFrom">?</a>
		<form id="dateForm" method="post" class="edit">
		<fieldset>
			<select id="dateType" class="selectform_long">
				<option value="From Date">From Date</option>
				<option value="To Date">To Date</option>
				<option value="Before">Before</option>
				<option value="After">After</option>
			</select>
			<input type="text" id="dateYear" class="input_4c" maxlength="4"/>
			<select id="dateMonth" class="selectform">
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
			<input type="text" id="dateDay" class="input_2c" maxlength="2"/>
			<input type="submit" value="add">
			<input type="hidden" id="category" value="Date">
		</fieldset>
		</form>
	</div>	

	<h1><a><i>in</i> EXTRACT and/or SYNOPSIS</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form id="extractForm" method="post" class="edit">
		<fieldset>
			<label for="extract" id="extractLabel">Extract</label>
			<textarea id="extract" class="txtadvsearch"></textarea>
			<input type="submit" value="add">
			<input type="hidden" id="category" value="Extract">
		</fieldset>
		</form>
		
		<form id="synopsisForm" method="post" class="edit">
		<fieldset>
			<label for="synopsis" id="synopsisLabel">Synopsis</label>
			<textarea id="synopsis" class="txtadvsearch"></textarea>
			<input type="submit" value="add">
			<input type="hidden" id="category" value="Synopsys">
		</fieldset>
		</form>
	</div>
	
	<h1><a><i>with</i> TOPICS</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form id="topicsForm" method="post" class="edit">
		<fieldset>
			<input type="text" id="topics" class="input_25c"/><!-- AUTOCOMPLETE -->
			<input type="submit" value="add">
			<input type="hidden" id="category" value="Topics">
		</fieldset>
		</form>
	</div>

	<h1><a><i>search on</i> PEOPLE &amp; PLACES</a></h1>
	<div>
		<a href="#" class="helpLinkFrom">?</a>
		<form id="personForm" method="post" class="edit">
		<fieldset>
			<label for="person" id="personLabel">Person</label> 
			<input type="text" id="person" class="input_25c" type="text" value=""/><!-- AUTOCOMPLETE -->
			<input type="submit" value="add">
			<input type="hidden" id="category" value="Person">
		</fieldset>
		</form>

		<form id="placeForm" method="post" class="edit">
			<fieldset>
				<label for="place" id="placeLabel">Place</label> 
				<input type="text" id="place" class="input_25c"/><!-- AUTOCOMPLETE -->
				<input type="submit" value="add">
				<input type="hidden" id="category" value="Place">
			</fieldset>
		</form>
		
		<form id="senderForm" method="post" class="edit">
			<fieldset>
				<label for="sender" id="senderLabel">Sender</label> 
				<input type="text" id="senderAutoCompleter" class="input_25c"/><!-- AUTOCOMPLETE -->
				<input type="submit" value="add">
				<input type="hidden" id="category" value="Sender">
			</fieldset>
		</form>
		
		<form id="fromForm" method="post" class="edit">
			<fieldset>
				<label for="from" id="fromLabel">From</label> 
				<input type="text" id="from" class="input_25c"/><!-- AUTOCOMPLETE -->
				<input type="submit" value="add">
				<input type="hidden" id="category" value="Sender">
			</fieldset>
		</form>
		
		<form id="recipientForm" method="post" class="edit">
			<fieldset>
				<label for="recipient" id="recipientLabel">Recipient</label> 
				<input type="text" id="recipient" class="input_25c"/><!-- AUTOCOMPLETE -->
				<input type="submit" value="add">
				<input type="hidden" id="category" value="Recipient">
			</fieldset>
		</form>
		
		<form id="toForm" method="post" class="edit">
			<fieldset>
				<label for="to" id="toLabel">To</label> 
				<input type="text" id="to" class="input_25c"/><!-- AUTOCOMPLETE -->
				<input type="submit" value="add">
					<input type="hidden" id="category" value="To">
			</fieldset>
		</form>
		
		<form id="refersToForm" method="post" class="edit">
			<fieldset>
				<label for="refersTo" id="refersToLabel">Refers to</label> 
				<input type="text" id="refersTo" class="input_25c"/><!-- AUTOCOMPLETE -->
				<input type="submit" value="add">
				<input type="hidden" id="category" value="Referers To">
			</fieldset>
		</form>
	</div>
</div>

<c:url var="searchSenderPeopleURL" value="/de/peoplebase/SearchSenderPeople.json"/>
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#wordForm").advancedSearchForm();
			$j("#volumeForm").advancedSearchForm();
			$j("#dateForm").advancedSearchForm();
			$j("#extractForm").advancedSearchForm();
			$j("#synopsisForm").advancedSearchForm();
			$j("#topicsForm").advancedSearchForm();
			$j("#personForm").advancedSearchForm();
			$j("#placeForm").advancedSearchForm();
			$j("#senderForm").advancedSearchForm();
			$j("#fromForm").advancedSearchForm();
			$j("#recipientForm").advancedSearchForm();
			$j("#toForm").advancedSearchForm();
			$j("#refersToForm").advancedSearchForm();

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
				var searchElement = "<div class=\"searchFilterDiv\">" +
				"<span class=\"categorySearch\">Word Search</span>" +
				"in " +
				"<span class=\"categorySearch\">" + $j("#wordSearchIn").val()+ "</span>" +
				"<span class=\"wordSearch\">" + $j("#wordSearch").val() + "</span>" +
				"<a class=\"remove\" href=\"#\">(remove)</a>" +
				"</div>" +
				"<p class=\"andOrNotAdvancedSearch\">And</p>";

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
				
			
					
		});

	</script>