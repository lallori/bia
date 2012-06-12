<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!-- 
		    	<div class="listAdvSearch">
		           	<div class="row">
		               	<div class="col_l"></div>
		            </div>
		        </div> 
		    -->
<body>
	<div id="advancedSearch">
		<div id="advancedSearch_top">SEARCH FOR DOCUMENTS</div>
		<div id="body_left">
			<div id="customSearchFilterDiv">
				<h1 class="advSearchTitle">Create your custom search filter</h1>
				<div id="accordion">
					<h1 id="wordSearch"><a>Word search</a></h1>
					<div class="documents">
						<div class="listAdvSearch">
							<form id="wordSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="Search here for words (in English) that appear in document synopses and/or words (in the original language and with the original spelling) that appear in document extracts.">?</a>
										<input type="text" id="word" name="word" class="input_15c" value="" />
					               	</div>
					               	<div class="col_l">in</div>
					               	<div class="col_l">
					               		<select id="wordType" name="wordType" class="selectform_LXlong">
											<option value="SynopsisAndExtract" selected="selected">Synopsis and Extract</option>
											<option value="Synopsis">Document Synopsis</option>
											<option value="Extract">Document Extract</option>				
										</select>
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter">
										<input type="hidden" id="category" value="Word Search">
					               	</div>
					            </div>
				            </form>
				        </div>
					</div>
					
					<h1 id="peoplePlaces"><a><i>search on</i> People &amp; Places</a></h1>
				    <div class="documents">
				    	<div class="listAdvSearch">
				    		<form id="personSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="General Person Search: search here for documents related to person name either if it is a sender, a recipient and/or referenced in a document.">?</a>
										<label for="person" id="personLabel">Person</label>
					               	</div>
					               	<div class="col_l"><input type="text" id="person" class="input_25c" type="text" value=""/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="personAdd" disabled="disabled">
										<input type="hidden" id="category" value="Person">
										<input type="hidden" id="personId" value="">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="placeSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="General Place Search: search here for a document realated to place either if it is attached to a sender, a recipient and/or to a document topic.">?</a>
										<label for="place" id="placeLabel">Place</label>
					               	</div>
					               	<div class="col_l"><input type="text" id="place" name="place" class="input_25c" value=""/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="placeAdd" disabled="disabled">
										<input type="hidden" id="category" value="Place">
										<input type="hidden" id="placeId" value="">
					               	</div>
					            </div>
				            </form>
				        </div>
		
						<hr />
						
						<div class="listAdvSearch">
							<form id="senderSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="Search documents sent FROM Person/Organization.">?</a>
										<label for="sender" id="senderLabel">Sender Name</label> 
					               	</div>
					               	<div class="col_l"><input type="text" id="sender" class="input_24c"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="senderAdd" disabled="disabled">
										<input type="hidden" id="category" value="Sender">
										<input type="hidden" id="senderId" value="">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="fromSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="Search documents sent FROM Place/Location.">?</a>
										<label for="from" id="fromLabel">Place From</label>
					               	</div>
					               	<div class="col_l"><input type="text" id="from" name="from" class="input_24c"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="fromAdd" disabled="disabled">
										<input type="hidden" id="category" value="From">
										<input type="hidden" id="fromId" value="">
					               	</div>
					            </div>
				            </form>
				            
							<form id="recipientSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="Search documents sent TO Person/Organization.">?</a>
										<label for="recipientSearch" id="recipientSearchLabel">Recipient Name</label>
					               	</div>
					               	<div class="col_l"><input type="text" id="recipient" name="recipient" class="input_24c"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="recipientAdd" disabled="disabled">
										<input type="hidden" id="category" value="Recipient">
										<input type="hidden" id="recipientId" value="">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="toSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="Search documents sent Place/Location.">?</a>
										<label for="to" id="toSearchLabel">Place To</label>
					               	</div>
					               	<div class="col_l"><input type="text" id="to" name="to" class="input_24c"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="toAdd" disabled="disabled">
										<input type="hidden" id="category" value="To">
										<input type="hidden" id="toId" value="">
					               	</div>
					            </div>
				            </form>
				            
							<form id="refersToSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="Search documents in which this Person's name is mentioned.">?</a>
										<label for="refersTo" id="refersToLabel">Refers to</label>
					               	</div>
					               	<div class="col_l"><input type="text" id="refersTo" name="refersTo" class="input_24c"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="refersToAdd" disabled="disabled">
										<input type="hidden" id="category" value="Referers To">
										<input type="hidden" id="refersToId" value="">
					               	</div>
					            </div>
				            </form>
				        </div>
					</div>
				
					<h1 id="extractSynopsis"><a><i>in</i> Extract and/or Synopsis</a></h1>
					<div class="documents">
						<div class="listAdvSearch">
							<form id="extractSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="That text will explain...">?</a>
										<label for="extract" id="extractLabel">Extract</label>
					               	</div>
					            </div>
					            <div class="row">
					               	<div class="col_l"><textarea id="extract" name="extract" class="txtadvsearch"></textarea></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter">
										<input type="hidden" id="category" value="Extract">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="synopsisSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="That text will explain...">?</a>
										<label for="synopsis" id="synopsisLabel">Synopsis</label>
					               	</div>
					            </div>
					            <div class="row">
					               	<div class="col_l"><textarea id="synopsis" name="synopsis" class="txtadvsearch"></textarea></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter">
										<input type="hidden" id="category" value="Synopsys">
					               	</div>
					            </div>
				            </form>
				        </div>
					</div>
					
					<h1 id="topicsSearch"><a><i>with</i> Topics</a></h1>
					<div class="documents">
						<div class="listAdvSearch">
							<form id="topicSearchForm" method="post" class="edit">
					           	<div class="row">
					           		<div class="col_l">
					           			<a class="helpIcon" title="A set of 42 Topic Categories related to the arts and humanities defines the scope of this database. Each document in the system is indexed to the relevant Topic Categories and also to the geographical places relevant to those Topic Categories. For example, a letter sent from Florence to Madrid mentioning a musical performance in Ferrara will be indexed under Topics to 'Music and Musical Instruments - Firenze', 'Music and Musical Instruments - Madrid' and 'Music and Musical Instruments - Ferrara'.">?</a>
					           			<label for="topicSelect" id="topicSelectLabel">Select a Topic</label>
					           		</div>
					               	<div class="col_l">
										<select id="topicSelect" name="topicSelect" class="selectForm_Xlong">
											<c:forEach items="${topicsList}" var="topicList">
												<option value="${topicList.topicId}">${topicList}</option>
											</c:forEach>
										</select>
					               	</div>
					               	<div class="col_r">
										<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="topicAdd">
										<input type="hidden" id="category" value="Topics">
										<input type="hidden" id="topic" name="topic" type="text" value=""/>
										<input type="hidden" id="topicId" value=""/>
					               	</div>
					            </div>
				            </form>
				            
				            <form id="topicPlaceSearchForm" method="post" class="edit">
					            <div class="row">
					            	<div class="col_l">
					            		<a class="helpIcon" title="">?</a>
					            		<label for="topicPlace" id="topicPlaceLabel">Related to Place</label>
					            	</div>
					               	<div class="col_l">
										<input type="text" id="topicPlace" class="input_24c"/><!-- AUTOCOMPLETE -->
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="topicPlaceAdd" disabled="disabled">
										<input type="hidden" id="category" value="topic Place">
										<input type="hidden" id="topicPlaceId" value="">
					               	</div>
					            </div>
				            </form>
				        </div>
					</div>
		
					<h1 id="dateRange"><a>Date Range</a></h1>
					<div class="documents">
						<div class="listAdvSearch">
							<form id="dateSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="When searching dates, you should enter the year according to modern (i.e. Roman) reckoning (with the new year beginning on 1 January), even when seeking documents dated according to Florentine reckoning (with the new year beginning on 25 March).">?</a>
										<select id="dateType" name="dateType" class="selectform_Llong">
											<option value="From">Written from</option>
											<option value="Before">Written before</option>
											<option value="Between">Written between</option>
											<option value="InOn">Written in/on</option>
										</select>
					               	</div>
					               	<div class="col_l"><input type="text" id="dateYear" class="input_4c" maxlength="4" value="yyyy"/></div>
					               	<div class="col_l">
					               		<select id="dateMonth" name="dateMonth" class="selectform">
											<c:forEach items="${months}" var="month">
												<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
											</c:forEach>
										</select>
					               	</div>
					               	<div class="col_l"><input type="text" id="dateDay" name="dateDay" class="input_2c" maxlength="2" value="dd"/></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="visible">
										<input type="hidden" id="category" value="Date">
					               	</div>
					            </div>
					            
					            <div class="row">
					               	<div class="col_l"><p class="invisible">and</p></div>
					               	<div class="col_l"><input id="dateYearBetween" name="dateYearBetween" class="input_4c" type="text" value="yyyy" maxlength="4" style="visibility:hidden"/></div>
					               	<div class="col_l">
					               		<select id="dateMonthBetween" name="dateMonthBetween" class="selectform" style="visibility:hidden">
											<c:forEach items="${months}" var="month">
												<option value="${month.monthNum}" selected="selected">${month.monthName}</option>
											</c:forEach>
						                </select>
					               	</div>
					               	<div class="col_l"><input id="dateDayBetween" name="dateDayBetween" class="input_2c" type="text" value="dd" maxlength="2" style="visibility:hidden"/></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="invisible">
					               	</div>
					            </div>
				            </form>
				        </div>
					</div>
					
					<h1 id="volumeSearch"><a><i>in</i> Volume and/or Folio</a></h1>
					<div class="documents">
						<h3>Volume</h3>
						<div class="listAdvSearch">
							<form id="volumeSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="This is the shelf number or call number assigned by the Archivio di Stato di Firenze to each volume of documents in the Medici Granducal Archive (Archivio Mediceo del Principato). This is the number that is used when ordering that volume for consultation in the Archivio and when citing it in publications.">?</a>
										<select id="volumeType" name="volumeType" class="selectform_long">
											<option value="Exactly" selected="selected">Exactly</option>
											<option value="Between">Between</option>
										</select>
					               	</div>
					               	<div class="col_l"><input type="text" id="volume"  name="volume" class="input_5c"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_l"><p class="invisibleVol">and</p></div>
					               	<div class="col_l"><input id="volumeBetween" name="volumeBetween" class="input_5c" type="text" style="visibility:hidden"/></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="volumeAdd" disabled="disabled">
										<input type="hidden" id="category" value="Volume">
					               	</div>
					            </div>
					        </form>
					            
					        <h3>Folio</h3>
					        <form id="folioSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="Text will go here">?</a>
										<select id="folioType" name="folioType" class="selectform_long">
											<option value="Exactly" selected="selected">Exactly</option>
											<option value="Between">Between</option>
										</select>
					               	</div>
					               	<div class="col_l"><input type="text" id="folio" name="folio" class="input_5c" maxlength="5" /></div>
					               	<div class="col_l"><p class="invisibleFol">and</p></div>
					               	<div class="col_l"><input id="folioBetween" name="folioBetween" class="input_5c" type="text" style="visibility:hidden"/></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
										<input type="hidden" id="category" value="Folio">
					               	</div>
					            </div>
				            </form>
				        </div>
					</div>
					
					<h1 id="docIdSearch"><a>Doc ID</a></h1>
					<div class="documents">
						<div class="listAdvSearch">
							<form id="docIdSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="Text will go here">?</a>
										<input id="docId" name="docId" class="input_7c" type="text" maxlength="5" />
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add this word search to your search filter">
										<input type="hidden" id="category" value="Doc Id">
					               	</div>
					            </div>
				            </form>
				        </div>
					</div>
				</div>
			</div>

	<c:url var="searchTopicURL" value="/src/SearchTopic.json"/>
	<c:url var="searchPersonURL" value="/src/SearchPerson.json"/>
	<c:url var="searchPlaceURL" value="/src/SearchPlace.json"/>
	<c:url var="searchVolumeURL" value="/src/SearchVolume.json"/>
	
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
			
			$j("#folioType").change(function(){
                if(this.options[1].selected) {
                    $j('#folioBetween').css('visibility','visible'); 
                    $j('.invisibleFol').css('visibility','visible'); 
                } else { 
                    $j('#folioBetween').css('visibility','hidden');
                    $j('.invisibleFol').css('visibility','hidden');
                }
            });


			$j("#dateType").change(function(){
				if(this.options[2].selected) { 
					$j("#dateYearBetween").css('visibility','visible');
					$j("#dateMonthBetween").css('visibility','visible');
					$j("#dateDayBetween").css('visibility','visible');
					$j('.invisible').css('visibility','visible');
					$j('.visible').css('visibility','hidden');
			   } else { 
					$j('#dateYearBetween').css('visibility','hidden');
					$j('#dateMonthBetween').css('visibility','hidden');
					$j('#dateDayBetween').css('visibility','hidden');
					$j('.invisible').css('visibility','hidden');
					$j('.visible').css('visibility','visible');
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
			$j("#topicPlaceSearchForm").advancedSearchForm();
			$j("#dateSearchForm").advancedSearchForm();
			$j("#volumeSearchForm").advancedSearchForm();
			$j("#folioSearchForm").advancedSearchForm();
			$j("#docIdSearchForm").advancedSearchForm();

			$j('#accordion').accordion({
				active: false, 
				autoHeight: false,
				collapsible:true
			});
			
			//These 3 function are dedicated for the date script
			$j("#dateSearchForm :input").keyup(function(){
				if($j("#dateType option:selected").val() == 'Between'){
					if($j("#dateYear").val() == 'yyyy' || $j("#dateYear").val() == '' || $j("#dateYearBetween").val() == 'yyyy' || $j("#dateYearBetween").val() == '')
						$j(".addDateRange").attr("disabled", "disabled");
					else{
						$j(".addDateRange").attr("disabled");
						$j(".addDateRange").removeAttr("disabled");
						$j(".addDateRange").prop("disabled", false);
					}
						
				}else{
					$j(".addDateRange").attr("disabled");
					$j(".addDateRange").removeAttr("disabled");
					$j(".addDateRange").prop("disabled", false);
				}
			});
			
			$j("#dateType").change(function(){
				if($j("#dateType option:selected").val() == 'Between'){
					if($j("#dateYear").val() == 'yyyy' || $j("#dateYear").val() == '' || $j("#dateYearBetween").val() == 'yyyy' || $j("#dateYearBetween").val() == '')
						$j(".addDateRange").attr("disabled", "disabled");
					else{
						$j(".addDateRange").attr("disabled");
						$j(".addDateRange").removeAttr("disabled");
						$j(".addDateRange").prop("disabled", false);
					}
						
				}else{
					$j(".addDateRange").attr("disabled");
					$j(".addDateRange").removeAttr("disabled");
					$j(".addDateRange").prop("disabled", false);
				}
			});
			
			$j("#dateSearchForm").submit(function(){
				$j('#dateType option[value="After"]').attr('selected', 'selected');
				$j('#dateYearBetween').css('visibility','hidden');
				$j('#dateMonthBetween').css('visibility','hidden');
				$j('#dateDayBetween').css('visibility','hidden');
				$j('.invisible').css('visibility','hidden');
				$j('.visible').css('visibility','visible');
			});
			
			var $personAutocomplete = $j("#person").autocompletePerson({
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
			
			$j("#person").blur(function(){
				$personAutocomplete.killSuggestions();
				return false;
			});
			
			$j("#person").keyup(function(){
				if($j("#personId").val() != '')
					$j(".personAdd").attr("disabled","disabled");
			});
			
			$j("#personSearchForm").submit(function(){
				$j("#personId").val("");
				$j(".personAdd").attr("disabled","disabled");
			});
			
			
			var $placeAutocomplete = $j("#place").autocompletePlace({
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
					$j(".placeAdd").prop("disabled", false);
				}
			});	
			
			$j("#place").blur(function(){
				$placeAutocomplete.killSuggestions();
				return false;
			});
			
			$j("#place").keyup(function(){
				if($j("#placeId").val() != '')
					$j(".placeAdd").attr("disabled","disabled");
			});
			
			$j("#placeSearchForm").submit(function(){
				$j("#placeId").val("");
				$j(".placeAdd").attr("disabled","disabled");
			});
			
			var $senderAutocomplete = $j("#sender").autocompletePerson({
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
			
			$j("#sender").blur(function(){
				$senderAutocomplete.killSuggestions();
				return false;
			});
			
			$j("#sender").keyup(function(){
				if($j("#senderId").val() != '')
					$j(".senderAdd").attr("disabled","disabled");
			});
			
			$j("#senderSearchForm").submit(function(){
				$j("#senderId").val("");
				$j(".senderAdd").attr("disabled","disabled");
			});		
			
			var $fromAutocomplete = $j("#from").autocompletePlace({
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
			
			$j("#from").blur(function(){
				$fromAutocomplete.killSuggestions();
				return false;
			});
			
			$j("#from").keyup(function(){
				if($j("#fromId").val() != '')
					$j(".fromAdd").attr("disabled","disabled");
			});
			
			$j("#fromSearchForm").submit(function(){
				$j("#fromId").val("");
				$j(".fromAdd").attr("disabled","disabled");
			});
			
			var $recipientAutocomplete = $j("#recipient").autocompletePerson({
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
			
			$j("#recipient").blur(function(){
				$recipientAutocomplete.killSuggestions();
				return false;
			});
			
			$j("#recipient").keyup(function(){
				if($j("#recipientId").val() != '')
					$j(".recipientAdd").attr("disabled","disabled");
			});
			
			$j("#recipientSearchForm").submit(function(){
				$j("#recipientId").val("");
				$j(".recipientAdd").attr("disabled","disabled");
			});
			
			var $toAutocomplete = $j("#to").autocompletePlace({
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
			
			$j("#to").blur(function(){
				$toAutocomplete.killSuggestions();
				return false;
			});
			
			$j("#to").keyup(function(){
				if($j("#toId").val() != '')
					$j(".toAdd").attr("disabled","disabled");
			});
			
			$j("#toSearchForm").submit(function(){
				$j("#toId").val("");
				$j(".toAdd").attr("disabled","disabled");
			});
			
			var $refersToAutocomplete = $j("#refersTo").autocompletePerson({
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
			
			$j("#refersTo").blur(function(){
				$refersToAutocomplete.killSuggestions();
				return false;
			});
			
			$j("#refersTo").keyup(function(){
				if($j("#refersToId").val() != '')
					$j(".refersToAdd").attr("disabled","disabled");
			});
			
			$j("#refersToSearchForm").submit(function(){
				$j("#refersToId").val("");
				$j(".refersToAdd").attr("disabled","disabled");
			});
			
			$j("#topicSelect").change(function(){
				 if($j(this).val() != "Select a Topic"){
				 	$j("#topic").val($j(this).find("option:selected").text());
				 	$j("#topicId").val($j(this).find("option:selected").val());
				 }
				 else{
					 $j("#topic").val("");
					 $j("#topicId").val("");
				 }
				 return false;
			 });
			
// 			var $topicAutocomplete = $j("#topic").autocompleteGeneral({
// 				serviceUrl: '${searchTopicURL}',
// 				minChars: 1,
// 				delimiter: null,
// 				maxHeight: 400,
// 				width: 200,
// 				zIndex: 9999,
// 				deferRequestBy: 0,
// 				noCache: true,
// 				onSelect: function(value, data){
// 					$j(".topicAdd").removeAttr("disabled");
// 					$j('#topicId').val(data);
// 					$j(".topicAdd").attr("disabled");
// 					$j(".topicAdd").prop("disabled", false);
// 				}
// 			});	
			
// 			$j("#topic").blur(function(){
// 				$topicAutocomplete.killSuggestions();
// 				return false;
// 			});
			
// 			$j("#topicSearchForm").submit(function(){
// 				$j("#topicId").val("");
// 				$j(".topicAdd").attr("disabled","disabled");
// 			});

			var $topicPlaceAutocomplete = $j("#topicPlace").autocompletePlace({
				serviceUrl: '${searchPlaceURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 450,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j(".topicPlaceAdd").removeAttr("disabled");
					$j('#topicPlaceId').val(data);
					$j(".topicPlaceAdd").attr("disabled");
					$j(".topicPlaceAdd").prop("disabled", false);
				}
			});	
			
			$j("#topicPlace").blur(function(){
				$placeAutocomplete.killSuggestions();
				return false;
			});
			
			$j("#topicPlace").keyup(function(){
				if($j("#topicPlaceId").val() != '')
					$j(".topicPlaceAdd").attr("disabled","disabled");
			});
			
			$j("#topicPlaceSearchForm").submit(function(){
				$j("#topicPlaceId").val("");
				$j(".topicPlaceAdd").attr("disabled","disabled");
			});
			
			var $volumeAutocomplete = $j("#volume").autocompleteGeneral({
				serviceUrl: '${searchVolumeURL}',
				minChars: 1,
				delimiter: null,
				maxHeight: 400,
				width: 200,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
				}
			});
			
			$j("#volume").blur(function(){
				$volumeAutocomplete.killSuggestions();
				return false;
			});
			
			$j("#volume").change(function(){
				if($j(this).val() != ''){
					$j(".volumeAdd").removeAttr("disabled");
					$j(".volumeAdd").attr("disabled");
					$j(".volumeAdd").prop("disabled", false);
				}					
			});
			
			$j(".volumeAdd").click(function(){
				if($j("#volume").val() == '')
					return false;
			})
			
			
			
			$j("#volumeBetween").autocompleteGeneral({
				serviceUrl: '${searchVolumeURL}',
				minChars: 1,
				delimiter: null,
				maxHeight: 400,
				width: 200,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
				}
			});
			
// 			$j("#topic").keyup(function(){
// 				if($j("#topicId").val() != '')
// 					$j(".topicAdd").attr("disabled","disabled");
// 			});
			
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
			$j('#topicsSearch').click(function(){
					$j.scrollTo({top:'168px',left:'0px'}, 800 );
					$j("#yourSearchFilterDiv").animate({"top": "125px"}, "slow");
					return false;
			});
			$j('#dateRange').click(function(){
					$j.scrollTo({top:'195px',left:'0px'}, 800 );
					$j("#yourSearchFilterDiv").animate({"top": "150px"}, "slow");
					return false;
			});
			$j('#volumeSearch').click(function(){
					$j.scrollTo({top:'222px',left:'0px'}, 800 );
					$j("#yourSearchFilterDiv").animate({"top": "180px"}, "slow");
					return false;
			});
			
			$j('#docIdSearch').click(function(){
				$j.scrollTo({top:'245px',left:'0px'}, 800 );
				$j("#yourSearchFilterDiv").animate({"top": "200px"}, "slow");
				return false;
			});
			
		});

	</script>