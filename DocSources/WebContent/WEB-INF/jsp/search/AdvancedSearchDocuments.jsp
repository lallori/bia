<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="customSearchFilterDiv">
		<div class="customSearchFilterTitle"></div>
		<div id="multiOpenAccordion">
			<h1><a>Word search</a></h1>
			<div>
				<form id="wordSearchForm" method="post" class="edit">
					<a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
					<input type="text" id="word" name="word" class="input_15c" value="" />
					in 
					<select id="wordType" name="wordType" class="selectform_LXlong">
						<option value="SynopsisAndExtract" selected="selected">Synopsis and Extract</option>
						<option value="Synopsis">Document Synopsis</option>
						<option value="Extract">Document Extract</option>				
					</select>
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="Word Search">
				</form>
			</div>
			
			<h1><a><i>search on</i> People &amp; Places</a></h1>
		    <div>
		    	<form id="personSearchForm" method="post" class="edit">
					<a class="helpIcon" title="General Person Search: search here for documents related to person name either if it is a sender, a recipient and/or referenced in a document.">?</a>
					<label for="person" id="personLabel">Person</label> 
					<input type="text" id="person" class="input_25c" type="text" value=""/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="Person">
					<input type="hidden" id="personId" value="">
				</form>
		        
		        <form id="placeSearchForm" method="post" class="edit">
					<a class="helpIcon" title="General Place Search: search here for a document realated to place either if it is attached to a sender, a recipient and/or to a document topic.">?</a>
					<label for="place" id="placeLabel">Place</label> 
					<input type="text" id="place" name="place" class="input_25c" value=""/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="Place">
					<input type="hidden" id="placeId" value="">
				</form>
				
				<hr />
				
				<form id="senderSearchForm" method="post" class="edit">
					<a class="helpIcon" title="Search documents sent FROM Person/Organization.">?</a>
					<label for="sender" id="senderLabel">Sender</label> 
					<input type="text" id="sender" class="input_25c"/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="Sender">
					<input type="hidden" id="senderId" value="">
				</form>
				
				<form id="fromSearchForm" method="post" class="edit">
					<a class="helpIcon" title="Search documents sent FROM Place/Location.">?</a>
					<label for="from" id="fromLabel">From</label> 
					<input type="text" id="from" name="from" class="input_25c"/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="From">
					<input type="hidden" id="fromId" value="">
				</form>
				
				<form id="recipientSearchForm" method="post" class="edit">
					<a class="helpIcon" title="Search documents sent TO Person/Organization.">?</a>
					<label for="recipientSearch" id="recipientSearchLabel">Recipient</label> 
					<input type="text" id="recipient" name="recipient" class="input_25c"/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="Recipient">
					<input type="hidden" id="recipientId" value="">
				</form>
				
				<form id="toSearchForm" method="post" class="edit">
					<a class="helpIcon" title="Search documents sent Place/Location.">?</a>
					<label for="to" id="toSearchLabel">To</label> 
					<input type="text" id="to" name="to" class="input_25c"/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="To">
					<input type="hidden" id="toId" value="">
				</form>
				
				<form id="refersToSearchForm" method="post" class="edit">
					<a class="helpIcon" title="Search documents in which this Person's name is mentioned.">?</a>
					<label for="refersTo" id="refersToLabel">Refers to</label> 
					<input type="text" id="refersTo" name="refersTo" class="input_25c"/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="Referers To">
					<input type="hidden" id="refersToId" value="">
				</form>
		    </div>
		
			<h1><a><i>in</i> Extract and/or Synopsis</a></h1>
			<div>
				<form id="extractSearchForm" method="post" class="edit">
					<a class="helpIcon" title="That text will explain...">?</a>
					<label for="extract" id="extractLabel">Extract</label>
					<textarea id="extract" name="extract" class="txtadvsearch"></textarea>
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="Extract">
				</form>
				<br />
				<form id="synopsisSearchForm" method="post" class="edit">
					<a class="helpIcon" title="That text will explain...">?</a>
					<label for="synopsis" id="synopsisLabel">Synopsis</label>
					<textarea id="synopsis" name="synopsis" class="txtadvsearch"></textarea>
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="Synopsys">
				</form>
			</div>
			
			<h1><a><i>with</i> Topics</a></h1>
			<div>
				<form id="topicSearchForm" method="post" class="edit">
					<a class="helpIcon" title="A set of 42 Topic Categories related to the arts and humanities defines the scope of this database. Each document in the system is indexed to the relevant Topic Categories and also to the geographical places relevant to those Topic Categories. For example, a letter sent from Florence to Madrid mentioning a musical performance in Ferrara will be indexed under Topics to 'Music and Musical Instruments - Firenze', 'Music and Musical Instruments - Madrid' and 'Music and Musical Instruments - Ferrara'.">?</a>
					<input type="text" id="topic" name="topic" class="input_25c"/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="Topics">
					<input type="hidden" id="topicId" value="">
				</form>
			</div>
			
			<h1><a>Date Range</a></h1>
			<div>
				<form id="dateSearchForm" method="post" class="edit">
					<a class="helpIcon" title="When searching dates, you should enter the year according to modern (i.e. Roman) reckoning (with the new year beginning on 1 January), even when seeking documents dated according to Florentine reckoning (with the new year beginning on 25 March).">?</a>
					<select id="dateType" name="dateType" class="selectform_Llong">
						<option value="After">Written after</option>
						<option value="Before">Written before</option>
						<option value="Between">Written between</option>
					</select>
					<input type="text" id="dateYear" class="input_4c" maxlength="4" value="yyyy"/>
					<select id="dateMonth" name="dateMonth" class="selectform">
		                <option value="mm" selected="selected">mm</option>
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
					</select>
					<input type="text" id="dateDay" name="dateDay" class="input_2c" maxlength="2" value="dd"/>
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter" class="addDateRange">
					<input type="hidden" id="category" value="Date">
					<p class="invisible">and</p>
	                <input id="dateYearBetween" name="dateYearBetween" class="input_4c" type="text" value="yyyy" maxlength="4" style="visibility:hidden"/>
	                <select id="dateMonthBetween" name="dateMonthBetween" class="selectform" style="visibility:hidden">
	                    <option value="mm" selected="selected">mm</option>
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
	                </select>
	                <input id="dateDayBetween" name="dateDayBetween" class="input_2c" type="text" value="dd" maxlength="2" style="visibility:hidden"/>
				</form>
			</div>
			
			<h1><a><i>in</i> Volume</a></h1>
			<div>
				<form id="volumeSearchForm" method="post" class="edit">
					<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
					<select id="volumeType" name="volumeType" class="selectform_long">
						<option value="Exactly" selected="selected">Exactly</option>
						<option value="Between">Between</option>
					</select>
					<input type="text" id="volume"  value="" class="input_5c" maxlength="5"/><!-- AUTOCOMPLETE -->
					<p class="invisibleVol">and</p>
					<input id="volumeBetween" name="volumeBetween" class="input_5c" type="text" value="" maxlength="5" style="visibility:hidden"/>
					<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
					<input type="hidden" id="category" value="Volume">
				</form>
			</div>	
		</div>
	</div>

	<c:url var="searchTopicURL" value="/src/SearchTopic.json"/>
	<c:url var="searchPersonURL" value="/src/SearchPerson.json"/>
	<c:url var="searchPlaceURL" value="/src/SearchPlace.json"/>
	
	<script type="text/javascript">
		$j(document).ready(function() {

			$j("#volumeType").change(function(){
				if(this.options[1].selected) {
					$j('#volumeBetween').css('visibility','visible'); 
					$j('.invisibleVol').css('visibility','visible'); 
				} else { 
					$j('#volumeBetween').css('visibility','hidden');
					$j('.invisibleVol').css('visibility','hidden');
				}
			});	

			$j("#dateType").change(function(){
				if(this.options[2].selected) { 
					$j('#dateYearBetween').css('visibility','visible');
					$j('#dateMonthBetween').css('visibility','visible');
					$j('#dateDayBetween').css('visibility','visible');
					$j('.invisible').css('visibility','visible');
					$j('.visible').css('visibility','hidden');
					$j('.addDateRange').css('margin-top','53px');
			   } else { 
					$j('#dateYearBetween').css('visibility','hidden');
					$j('#dateMonthBetween').css('visibility','hidden');
					$j('#dateDayBetween').css('visibility','hidden');
					$j('.invisible').css('visibility','hidden');
					$j('.visible').css('visibility','visible');
					$j('.addDateRange').css('margin-top','5px');
				}
			});
			
			$j('#dateYear').focus(function() {
				if(this.value=='yyyy') {
					this.value='';
				}
			});
			$j('#dateYearBetween').focus(function() {
				if(this.value=='yyyy') {
					this.value='';
				}
			});
			$j('#dateDay').focus(function() {
				if(this.value=='dd') {
					this.value='';
				}
			});
			$j('#dateDayBetween').focus(function() {
				if(this.value=='dd') {
					this.value='';
				}
			});

			$j("#wordSearchForm").advancedSearchForm();
			$j("#personSearchForm").advancedSearchForm();
			$j("#placeSearchForm").advancedSearchForm();
			$j("#senderSearchForm").advancedSearchForm();
			$j("#fromSearchForm").advancedSearchForm();
			$j("#recipientSearchForm").advancedSearchForm();
			$j("#toSearchForm").advancedSearchForm();
			$j("#refersToSearchForm").advancedSearchForm();
			$j("#extractSearchForm").advancedSearchForm();
			$j("#synopsisSearchForm").advancedSearchForm();
			$j("#topicSearchForm").advancedSearchForm();
			$j("#dateSearchForm").advancedSearchForm();
			$j("#volumeSearchForm").advancedSearchForm();

			$j('#multiOpenAccordion').multiAccordion({active: [0]});
			$j("#person").autocompletePerson({
				serviceUrl: '${searchPersonURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 600,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j('#personId').val(data);
				}
			});	
			$j("#place").autocompletePlace({
				serviceUrl: '${searchPlaceURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 450,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j('#placeId').val(data);
				}
			});	
			$j("#sender").autocompletePerson({
				serviceUrl: '${searchPersonURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 600,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j('#senderId').val(data);
				}
			});	
			$j("#from").autocompletePlace({
				serviceUrl: '${searchPlaceURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 600,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j('#fromId').val(data);
				}
			});	
			$j("#recipient").autocompletePerson({
				serviceUrl: '${searchPersonURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 600,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j('#recipientId').val(data);
				}
			});	
			$j("#to").autocompletePlace({
				serviceUrl: '${searchPlaceURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 600,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j('#toId').val(data);
				}
			});	
			$j("#refersTo").autocompletePerson({
				serviceUrl: '${searchPersonURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 350,
				width: 600,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j('#refersToId').val(data);
				}
			});
			$j("#topic").autocompleteGeneral({
				serviceUrl: '${searchTopicURL}',
				minChars: 1,
				delimiter: null,
				maxHeight: 400,
				width: 200,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j('#topicId').val(data);
				}
			});	
		});

	</script>