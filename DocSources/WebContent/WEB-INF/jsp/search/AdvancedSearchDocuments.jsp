<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<div id="multiOpenAccordion">
	<h1><a>WORD SEARCH</a></h1>
	<div>
		<form id="wordSearchForm" method="post" class="edit">
		<fieldset>
			<a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
			<input type="text" id="wordSearch" name="wordSearch" class="input_15c" value="" />
			in 
			<select id="fromDateMonthSearch" name="fromDateMonthSearch" class="selectform_LXlong">
				<option value="Synopsis and Extract" selected="selected">Synopsis and Extract</option>
				<option value="Document Synopsis">Document Synopsis</option>
				<option value="Document Extract">Document Extract</option>				
			</select>
			<a href="#" id="AddWordSearch">Add</a>
			<input type="hidden" id="category" value="Word Search">
		</fieldset>
		</form>
	</div>

	<h1><a><i>in</i> VOLUME</a></h1>
	<div>
		<form id="volumeSearchForm" method="post" class="edit">
		<fieldset>
			<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
			<select id="volumeSearchExactlyBetween" name="volumeSearchExactlyBetween" class="selectform_long">
				<option value="Exactly" selected="selected">Exactly</option>
				<option value="Between">Between</option>
			</select>
			<input type="text" id="volumeSearch"  value="" class="input_5c" maxlength="5"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addSearchFilter">Add</a>
			<input type="hidden" id="category" value="Volume">
		</fieldset>
		</form>
		
		<form id="dateSearchForm" method="post" class="edit">
		<fieldset>
		<a class="helpIcon" title="When searching dates, you should enter the year according to modern (i.e. Roman) reckoning (with the new year beginning on 1 January), even when seeking documents dated according to Florentine reckoning (with the new year beginning on 25 March).">?</a>
			<select id="dateSearch" name="dateSearch" class="selectform_long">
				<option value="From Date">From Date</option>
				<option value="To Date">To Date</option>
				<option value="Before">Before</option>
				<option value="After">After</option>
			</select>
			<input type="text" id="fromDateYearSearch" class="input_4c" maxlength="4"/>
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
				<option value="month">month</option>
			</select>
			<input type="text" id="fromDateDaySearch" name="fromDateDaySearch" class="input_2c" maxlength="2"/>
			<a href="#" id="addSearchFilter">Add</a>
			<input type="hidden" id="category" value="Date">
		</fieldset>
		</form>
	</div>	

	<h1><a><i>in</i> EXTRACT and/or SYNOPSIS</a></h1>
	<div>
		<form id="extractSearchForm" method="post" class="edit">
		<fieldset>
			<a class="helpIcon" title="That text will explain...">?</a>
			<label for="extractSearch" id="extractSearchLabel">Extract</label>
			<textarea id="extractSearch" name="extractSearch" class="txtadvsearch"></textarea>
			<a href="#" id="addSearchFilter">Add</a>
			<input type="hidden" id="category" value="Extract">
		</fieldset>
		</form>
		<br />
		<form id="synopsisSearchForm" method="post" class="edit">
		<fieldset>
			<a class="helpIcon" title="That text will explain...">?</a>
			<label for="synopsisSearch" id="synopsisSearchLabel">Synopsis</label>
			<textarea id="synopsisSearch" name="synopsisSearch" class="txtadvsearch"></textarea>
			<a href="#" id="addSearchFilter">Add</a>
			<input type="hidden" id="category" value="Synopsys">
		</fieldset>
		</form>
	</div>
	
	<h1><a><i>with</i> TOPICS</a></h1>
	<div>
		<form id="topicsSearchForm" method="post" class="edit">
		<fieldset>
			<a class="helpIcon" title="A set of 42 Topic Categories related to the arts and humanities defines the scope of this database. Each document in the system is indexed to the relevant Topic Categories and also to the geographical places relevant to those Topic Categories. For example, a letter sent from Florence to Madrid mentioning a musical performance in Ferrara will be indexed under Topics to 'Music and Musical Instruments - Firenze', 'Music and Musical Instruments - Madrid' and 'Music and Musical Instruments - Ferrara'.">?</a>
			<input type="text" id="topicsSearch" name="topicsSearch" class="input_25c"/><!-- AUTOCOMPLETE -->
			<a href="#" id="addSearchFilter">Add</a>
			<input type="hidden" id="category" value="Topics">
		</fieldset>
		</form>
	</div>

	<h1><a><i>search on</i> PEOPLE &amp; PLACES</a></h1>
	<div>
		<form id="personSearchForm" method="post" class="edit">
		<fieldset>
			<a class="helpIcon" title="General Person Search: search here for documents related to person name either if it is a sender, a recipient and/or referenced in a document.">?</a>
			<label for="personSearch" id="personSearchLabel">Person</label> 
			<input type="text" id="personSearch" class="input_25c" type="text" value=""/><!-- AUTOCOMPLETE -->
			<a href="#" id="addSearchFilter">Add</a>
			<input type="hidden" id="category" value="Person">
		</fieldset>
		</form>

		<form id="placeSearchForm" method="post" class="edit">
			<fieldset>
				<a class="helpIcon" title="General Place Search: search here for a document realated to place either if it is attached to a sender, a recipient and/or to a document topic.">?</a>
				<label for="placeSearch" id="placeSearchLabel">Place</label> 
				<input type="text" id="placeSearch" name="placeSearch" class="input_25c" value=""/><!-- AUTOCOMPLETE -->
				<a href="#" id="addSearchFilter">Add</a>
				<input type="hidden" id="category" value="Place">
			</fieldset>
		</form>
		
		<hr />
		
		<form id="senderSearchForm" method="post" class="edit">
			<fieldset>
				<a class="helpIcon" title="Search documents sent FROM Person/Organization.">?</a>
				<label for="senderSearch" id="senderSearchLabel">Sender</label> 
				<input type="text" id="senderSearchAutoCompleter" class="input_25c"/><!-- AUTOCOMPLETE -->
				<a href="#" id="addSearchFilter">Add</a>
				<input type="hidden" id="category" value="Sender">
			</fieldset>
		</form>
		
		<form id="fromSearchForm" method="post" class="edit">
			<fieldset>
				<a class="helpIcon" title="Search documents sent FROM Place/Location.">?</a>
				<label for="fromSearch" id="fromSearchLabel">From</label> 
				<input type="text" id="fromSearch" name="fromSearch" class="input_25c"/><!-- AUTOCOMPLETE -->
				<a href="#" id="addSearchFilter">Add</a>
				<input type="hidden" id="category" value="Sender">
			</fieldset>
		</form>
		
		<form id="recipientSearchForm" method="post" class="edit">
			<fieldset>
				<a class="helpIcon" title="Search documents sent TO Person/Organization.">?</a>
				<label for="recipientSearch" id="recipientSearchLabel">Recipient</label> 
				<input type="text" id="recipientSearch" name="recipientSearch" class="input_25c"/><!-- AUTOCOMPLETE -->
				<a href="#" id="addSearchFilter">Add</a>
				<input type="hidden" id="category" value="Recipient">
			</fieldset>
		</form>
		
		<form id="toSearchForm" method="post" class="edit">
			<fieldset>
				<a class="helpIcon" title="Search documents sent Place/Location.">?</a>
				<label for="toSearch" id="toSearchLabel">To</label> 
				<input type="text" id="toSearch" name="toSearch" class="input_25c"/><!-- AUTOCOMPLETE -->
				<a href="#" id="addSearchFilter">Add</a>
				<input type="hidden" id="category" value="To">
			</fieldset>
		</form>
		
		<form id="refersToSearchForm" method="post" class="edit">
			<fieldset>
				<a class="helpIcon" title="Search documents in which this Person's name is mentioned.">?</a>
				<label for="refersToSearch" id="refersToSearchLabel">Refers to</label> 
				<input type="text" id="refersToSearch" name="refersToSearch" class="input_25c"/><!-- AUTOCOMPLETE -->
				<a href="#" id="addSearchFilter">Add</a>
				<input type="hidden" id="category" value="Referers To">
			</fieldset>
		</form>
	</div>
</div>

<c:url var="searchSenderPeopleURL" value="/de/peoplebase/SearchSenderPeople.json"/>
	<script type="text/javascript">
		$j(document).ready(function() {
			$j("#wordSearchForm").advancedSearchForm();
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