<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<div id="customSearchFilterDiv">
		<h1 class="advSearchTitle">Create your custom search filter</h1>
		<div id="accordion">
			<h1 id="wordSearch"><a>Word search</a></h1>
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
					<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter">
					<input type="hidden" id="category" value="Word Search">
				</form>
			</div>
			
			<<h1 id="peoplePlaces"><a><i>search on</i> People &amp; Places</a></h1>
		    <div>
		    	<form id="personSearchForm" method="post" class="edit">
					<a class="helpIcon" title="General Person Search: search here for documents related to person name either if it is a sender, a recipient and/or referenced in a document.">?</a>
					<label for="person" id="personLabel">Person</label> 
					<input type="text" id="person" class="input_25c" type="text" value=""/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="personAdd" disabled="disabled">
					<input type="hidden" id="category" value="Person">
					<input type="hidden" id="personId" value="">
				</form>
		        
		        <form id="placeSearchForm" method="post" class="edit">
					<a class="helpIcon" title="General Place Search: search here for a document realated to place either if it is attached to a sender, a recipient and/or to a document topic.">?</a>
					<label for="place" id="placeLabel">Place</label> 
					<input type="text" id="place" name="place" class="input_25c" value=""/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="placeAdd" disabled="disabled">
					<input type="hidden" id="category" value="Place">
					<input type="hidden" id="placeId" value="">
				</form>
				
				<hr />
				
				<form id="senderSearchForm" method="post" class="edit">
					<a class="helpIcon" title="Search documents sent FROM Person/Organization.">?</a>
					<label for="sender" id="senderLabel">Sender</label> 
					<input type="text" id="sender" class="input_25c"/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="senderAdd" disabled="disabled">
					<input type="hidden" id="category" value="Sender">
					<input type="hidden" id="senderId" value="">
				</form>
				
				<form id="fromSearchForm" method="post" class="edit">
					<a class="helpIcon" title="Search documents sent FROM Place/Location.">?</a>
					<label for="from" id="fromLabel">From</label> 
					<input type="text" id="from" name="from" class="input_25c"/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="fromAdd" disabled="disabled">
					<input type="hidden" id="category" value="From">
					<input type="hidden" id="fromId" value="">
				</form>
				
				<form id="recipientSearchForm" method="post" class="edit">
					<a class="helpIcon" title="Search documents sent TO Person/Organization.">?</a>
					<label for="recipientSearch" id="recipientSearchLabel">Recipient</label> 
					<input type="text" id="recipient" name="recipient" class="input_25c"/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="recipientAdd" disabled="disabled">
					<input type="hidden" id="category" value="Recipient">
					<input type="hidden" id="recipientId" value="">
				</form>
				
				<form id="toSearchForm" method="post" class="edit">
					<a class="helpIcon" title="Search documents sent Place/Location.">?</a>
					<label for="to" id="toSearchLabel">To</label> 
					<input type="text" id="to" name="to" class="input_25c"/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="toAdd" disabled="disabled">
					<input type="hidden" id="category" value="To">
					<input type="hidden" id="toId" value="">
				</form>
				
				<form id="refersToSearchForm" method="post" class="edit">
					<a class="helpIcon" title="Search documents in which this Person's name is mentioned.">?</a>
					<label for="refersTo" id="refersToLabel">Refers to</label> 
					<input type="text" id="refersTo" name="refersTo" class="input_25c"/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="refersToAdd" disabled="disabled">
					<input type="hidden" id="category" value="Referers To">
					<input type="hidden" id="refersToId" value="">
				</form>
		    </div>
		
			<h1 id="extractSynopsis"><a><i>in</i> Extract and/or Synopsis</a></h1>
			<div>
				<form id="extractSearchForm" method="post" class="edit">
					<a class="helpIcon" title="That text will explain...">?</a>
					<label for="extract" id="extractLabel">Extract</label><br />
					<textarea id="extract" name="extract" class="txtadvsearch"></textarea>
					<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter">
					<input type="hidden" id="category" value="Extract">
				</form>
				<br />
				<form id="synopsisSearchForm" method="post" class="edit">
					<a class="helpIcon" title="That text will explain...">?</a>
					<label for="synopsis" id="synopsisLabel">Synopsis</label><br />
					<textarea id="synopsis" name="synopsis" class="txtadvsearch"></textarea>
					<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter">
					<input type="hidden" id="category" value="Synopsys">
				</form>
			</div>
			
			<h1 id="topics"><a><i>with</i> Topics</a></h1>
			<div>
				<form id="topicSearchForm" method="post" class="edit">
					<a class="helpIcon" title="A set of 42 Topic Categories related to the arts and humanities defines the scope of this database. Each document in the system is indexed to the relevant Topic Categories and also to the geographical places relevant to those Topic Categories. For example, a letter sent from Florence to Madrid mentioning a musical performance in Ferrara will be indexed under Topics to 'Music and Musical Instruments - Firenze', 'Music and Musical Instruments - Madrid' and 'Music and Musical Instruments - Ferrara'.">?</a>
					<input type="text" id="topic" name="topic" class="input_25c"/><!-- AUTOCOMPLETE -->
					<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="topicAdd" disabled="disabled">
					<input type="hidden" id="category" value="Topics">
					<input type="hidden" id="topicId" value="">
				</form>
			</div>

			<h1 id="dateRange"><a>Date Range</a></h1>
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
						<c:forEach items="${months}" var="month">
							<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
						</c:forEach>
					</select>
					<input type="text" id="dateDay" name="dateDay" class="input_2c" maxlength="2" value="dd"/>
					<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="addDateRange">
					<input type="hidden" id="category" value="Date">
					<p class="invisible">and</p>
	                <input id="dateYearBetween" name="dateYearBetween" class="input_4c" type="text" value="yyyy" maxlength="4" style="visibility:hidden"/>
	                <select id="dateMonthBetween" name="dateMonthBetween" class="selectform" style="visibility:hidden">
						<c:forEach items="${months}" var="month">
							<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
						</c:forEach>
	                </select>
	                <input id="dateDayBetween" name="dateDayBetween" class="input_2c" type="text" value="dd" maxlength="2" style="visibility:hidden"/>
				</form>
			</div>
			
			<h1 id="volume"><a><i>in</i> Volume</a></h1>
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
					<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter">
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
			$j("#dateMonth option:eq(0)").text("mm");
			$j("#dateMonth option:eq(0)").attr('selected', 'selected');
			$j("#dateMonthBetween option:eq(0)").text("mm");
			$j("#dateMonthBetween option:eq(0)").attr('selected', 'selected');

			$j("#volumeType").change(function(){
				if(this.options[1].selected) {
					$j("#volumeBetween").css('visibility','visible'); 
					$j(".invisibleVol").css('visibility','visible'); 
				} else { 
					$j("#volumeBetween").css('visibility','hidden');
					$j(".invisibleVol").css('visibility','hidden');
				}
			});	

			$j("#dateType").change(function(){
				if(this.options[2].selected) { 
					$j("#dateYearBetween").css('visibility','visible');
					$j("#dateMonthBetween").css('visibility','visible');
					$j("#dateDayBetween").css('visibility','visible');
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

			$j('#accordion').accordion({
				active: false, 
				autoHeight: false
				});
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
					$j(".personAdd").attr("disabled");
					$j(".personAdd").removeAttr("disabled");
					$j(".personAdd").prop("disabled", false);
					$j('#personId').val(data);
				}
			});
			
			$j("#person").change(function(){
				$j(".personAdd").attr("disabled","disabled");
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
					$j(".placeAdd").removeAttr("disabled");
					$j('#placeId').val(data);
					$j(".placeAdd").attr("disabled");
					$j(".palceAdd").prop("disabled", false);
				}
			});	
			
			$j("#place").change(function(){
				$j(".placeAdd").attr("disabled","disabled");
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
					$j(".senderAdd").removeAttr("disabled");
					$j('#senderId').val(data);
					$j(".senderAdd").attr("disabled");
					$j(".senderAdd").prop("disabled", false);
				}
			});
			
			$j("#sender").change(function(){
				$j(".senderAdd").attr("disabled","disabled");
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
					$j(".fromAdd").removeAttr("disabled");
					$j('#fromId').val(data);
					$j(".fromAdd").attr("disabled");
					$j(".fromAdd").prop("disabled", false);
				}
			});
			
			$j("#from").change(function(){
				$j(".fromAdd").attr("disabled","disabled");
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
					$j(".recipientAdd").removeAttr("disabled");
					$j('#recipientId').val(data);
					$j(".recipientAdd").attr("disabled");
					$j(".recipientAdd").prop("disabled", false);
				}
			});	
			
			$j("#recipient").change(function(){
				$j(".recipientAdd").attr("disabled","disabled");
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
					$j(".toAdd").removeAttr("disabled");
					$j('#toId').val(data);
					$j(".toAdd").attr("disabled");
					$j(".toAdd").prop("disabled", false);
				}
			});	
			
			$j("#to").change(function(){
				$j(".toAdd").attr("disabled","disabled");
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
					$j(".refersToAdd").removeAttr("disabled");
					$j('#refersToId').val(data);
					$j(".refersToAdd").attr("disabled");
					$j(".refersToAdd").prop("disabled", false);
				}
			});
			
			$j("#refersTo").change(function(){
				$j(".refersToAdd").attr("disabled","disabled");
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
					$j(".topicAdd").removeAttr("disabled");
					$j('#topicId').val(data);
					$j(".topicAdd").attr("disabled");
					$j(".topicAdd").prop("disabled", false);
				}
			});	
			
			$j("#topic").change(function(){
				$j(".topicAdd").attr("disabled","disabled");
			});
			
			$j('#wordSearch').click(function(){
					$j.scrollTo({top:'0px',left:'0px'}, 800 );
					$j("#yourSearchFilterDiv").animate({"top": "0px"}, "slow");
					return false;
			});
			$j('#peoplePlaces').click(function(){
					$j.scrollTo({top:'113px',left:'0px'}, 800 );
					$j("#yourSearchFilterDiv").animate({"top": "70px"}, "slow");
					return false;
			});
			$j('#extractSynopsis').click(function(){
					$j.scrollTo({top:'140px',left:'0px'}, 800 );
					$j("#yourSearchFilterDiv").animate({"top": "100px"}, "slow");
					return false;
			});
			$j('#topics').click(function(){
					$j.scrollTo({top:'168px',left:'0px'}, 800 );
					$j("#yourSearchFilterDiv").animate({"top": "125px"}, "slow");
					return false;
			});
			$j('#dateRange').click(function(){
					$j.scrollTo({top:'195px',left:'0px'}, 800 );
					$j("#yourSearchFilterDiv").animate({"top": "150px"}, "slow");
					return false;
			});
			$j('#volume').click(function(){
					$j.scrollTo({top:'222px',left:'0px'}, 800 );
					$j("#yourSearchFilterDiv").animate({"top": "180px"}, "slow");
					return false;
			});
			
		});

	</script>