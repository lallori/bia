<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="AdvancedSearchCountURL" value="/src/AdvancedSearchCount.json">
	</c:url>

	<c:url var="LoadingImageURL" value="/images/loading_autocomplete.gif"/>

<body class="search">
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
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.wordsearch"></fmt:message>">?</a>
										<input type="text" id="word" name="word" class="input_15c" value="" />
					               	</div>
					               	<div class="col_l">in</div>
					               	<div class="col_l">
					               		<select id="wordType" name="wordType" class="selectform_LXlong">
											<option value="SynopsisAndExtract" selected="selected">Synopsis and Transcription</option>
											<option value="Synopsis">Document Synopsis</option>
											<option value="Extract">Document Transcription</option>				
										</select>
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="Add to your search filter">
										<input type="hidden" id="category" value="Word Search">
					               	</div>
					            </div>
				            </form>
				        </div>
					</div>
					
					<h1 id="peoplePlaces"><a>People &amp; Places</a></h1>
				    <div class="documents">
				    	<div class="listAdvSearch">
				    		<form id="personSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.peopleandplaces.person"></fmt:message>">?</a>
										<label for="person" id="personLabel">Person</label>
					               	</div>
					               	<div class="col_l"><input type="text" id="person" class="input_25c" type="text" value=""/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="personAdd button_small" disabled="disabled">
										<input type="hidden" id="category" value="Person">
										<input type="hidden" id="personId" value="">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="placeSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.peopleandplaces.place"></fmt:message>">?</a>
										<label for="place" id="placeLabel">Place</label>
					               	</div>
					               	<div class="col_l"><input type="text" id="place" name="place" class="input_25c" value=""/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="placeAdd button_small" disabled="disabled">
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
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.peopleandplaces.sender"></fmt:message>">?</a>
										<label for="sender" id="senderLabel">Sender Name</label> 
					               	</div>
					               	<div class="col_l"><input type="text" id="sender" class="input_24c"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="senderAdd button_small">
										<input type="hidden" id="category" value="Sender">
										<input type="hidden" id="senderId" value="">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="fromSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.peopleandplaces.from"></fmt:message>">?</a>
										<label for="from" id="fromLabel">Place From</label>
					               	</div>
					               	<div class="col_l"><input type="text" id="from" name="from" class="input_24c"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="fromAdd button_small" disabled="disabled">
										<input type="hidden" id="category" value="From">
										<input type="hidden" id="fromId" value="">
					               	</div>
					            </div>
				            </form>
				            
							<form id="recipientSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.peopleandplaces.recipient"></fmt:message>">?</a>
										<label for="recipientSearch" id="recipientSearchLabel">Recipient Name</label>
					               	</div>
					               	<div class="col_l"><input type="text" id="recipient" name="recipient" class="input_24c"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="recipientAdd button_small">
										<input type="hidden" id="category" value="Recipient">
										<input type="hidden" id="recipientId" value="">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="toSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.peopleandplaces.to"></fmt:message>">?</a>
										<label for="to" id="toSearchLabel">Place To</label>
					               	</div>
					               	<div class="col_l"><input type="text" id="to" name="to" class="input_24c"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="toAdd button_small" disabled="disabled">
										<input type="hidden" id="category" value="To">
										<input type="hidden" id="toId" value="">
					               	</div>
					            </div>
				            </form>
				            
							<form id="refersToSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.peopleandplaces.refersto"></fmt:message>">?</a>
										<label for="refersTo" id="refersToLabel">Refers to</label>
					               	</div>
					               	<div class="col_l"><input type="text" id="refersTo" name="refersTo" class="input_24c"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="refersToAdd button_small">
										<input type="hidden" id="category" value="Referers To">
										<input type="hidden" id="refersToId" value="">
					               	</div>
					            </div>
				            </form>
				        </div>
					</div>
				
					<h1 id="extractSynopsis"><a>Transcription and/or Synopsis</a></h1>
					<div class="documents">
						<div class="listAdvSearch">
							<form id="extractSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.extractorsynopsis.extract"></fmt:message>">?</a>
										<label for="extract" id="extractLabel">Transcription</label>
					               	</div>
					            </div>
					            <div class="row">
					               	<div class="col_l"><textarea id="extract" name="extract" class="txtadvsearch"></textarea></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="Add to your search filter">
										<input type="hidden" id="category" value="Extract">
					               	</div>
					            </div>
				            </form>
				            
				            <form id="synopsisSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.extractorsynopsis.synopsis"></fmt:message>">?</a>
										<label for="synopsis" id="synopsisLabel">Synopsis</label>
					               	</div>
					            </div>
					            <div class="row">
					               	<div class="col_l"><textarea id="synopsis" name="synopsis" class="txtadvsearch"></textarea></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="Add to your search filter">
										<input type="hidden" id="category" value="Synopsys">
					               	</div>
					            </div>
				            </form>
				        </div>
					</div>
					
					<h1 id="topicsSearch"><a>Topics</a></h1>
					<div class="documents">
						<div class="listAdvSearch">
							<form id="topicSearchForm" method="post" class="edit">
					           	<div class="row">
					           		<div class="col_l">
					           			<a class="helpIcon" title="<fmt:message key="advsearch.documents.topics"></fmt:message>">?</a>
					           			<label for="topicType" id="topicSelectLabel">Select a Topic</label>
					           		</div>
					               	<div class="col_l">
										<select id="topicType" name="topicType" class="selectForm_Xlong">
											<c:forEach items="${topicsList}" var="topicList">
												<option value="${topicList.topicId}">${topicList}</option>
											</c:forEach>
										</select>
					               	</div>
<!-- 					               	<div class="col_r"> -->
<!-- 										<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="topicAdd button_small"> -->
<!-- 										<input type="hidden" id="category" value="Topics"> -->
<!-- 										<input type="hidden" id="topicType" name="topic" type="text" value=""/> -->
<!-- 										<input type="hidden" id="topicId" value=""/> -->
<!-- 					               	</div> -->
					            </div>
<%-- 				            </form> --%>
				            
<%-- 				            <form id="topicPlaceSearchForm" method="post" class="edit"> --%>
					            <div class="row">
					            	<div class="col_l">
					            		<a class="helpIcon" title="<fmt:message key="advsearch.documents.topics.place"></fmt:message>">?</a>
					            		<label for="topic" id="topicPlaceLabel">Related to Place</label>
					            	</div>
					               	<div class="col_l">
										<input type="text" id="topic" class="input_24c"/><!-- AUTOCOMPLETE -->
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="topicAdd button_small">
										<input type="hidden" id="category" value="topic">
										<input type="hidden" id="topicId" value="">
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
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.daterange"></fmt:message>">?</a>
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
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="visible button_small">
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
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="invisible button_small">
					               	</div>
					            </div>
				            </form>
				        </div>
					</div>
					
					<h1 id="volumeSearch"><a>Volume and/or Folio</a></h1>
					<div class="documents">
						<h3>Volume</h3>
						<div class="listAdvSearch">
							<form id="volumeSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.volumeorfolio.volume"></fmt:message>">?</a>
										<select id="volumeType" name="volumeType" class="selectform_long">
											<option value="Exactly" selected="selected">Exactly</option>
											<option value="Between">Between</option>
										</select>
					               	</div>
					               	<div class="col_l"><input type="text" id="volume"  name="volume" class="input_5c"/><!-- AUTOCOMPLETE --></div>
					               	<div class="col_l"><p class="invisibleVol">and</p></div>
					               	<div class="col_l"><input id="volumeBetween" name="volumeBetween" class="input_5c" type="text" style="visibility:hidden"/></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" value="Add" title="Add to your search filter" class="volumeAdd button_small" disabled="disabled">
										<input type="hidden" id="category" value="Volume">
					               	</div>
					            </div>
					        </form>
					            
					        <h3>Folio</h3>
					        <form id="folioSearchForm" method="post" class="edit">
					            <div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.volumeorfolio.folio"></fmt:message>">?</a>
										<select id="folioType" name="folioType" class="selectform_long">
											<option value="Exactly" selected="selected">Exactly</option>
											<option value="Between">Between</option>
										</select>
					               	</div>
					               	<div class="col_l"><input type="text" id="folio" name="folio" class="input_5c" maxlength="5" /></div>
					               	<div class="col_l"><p class="invisibleFol">and</p></div>
					               	<div class="col_l"><input id="folioBetween" name="folioBetween" class="input_5c" type="text" style="visibility:hidden"/></div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="Add this word search to your search filter">
										<input type="hidden" id="category" value="Folio">
					               	</div>
					            </div>
				            </form>
				            
				            
							<form id="folioModSearchForm" method="post" class="edit">
					           	<div class="row">
					               	<div class="col_l">
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.volumeorfolio.foliomod"></fmt:message>">?</a>
			                			<label for="folioMod" id="folioModLabel">Folio Mod</label> 
					               	</div>
					               	<div class="col_l" style="width:60px;">
					               		<label for="bis" id="bisLabel"><i>Bis</i></label>
			                			<input type="checkbox" name="bis" value="bis"\/>
					               	</div>
					               	<div class="col_l">
					               		<label for="ter" id="terLabel"><i>Ter</i></label>
			                			<input type="checkbox" name="ter" value="ter"\/>
					               	</div>
					               	<div class="col_l">
					               		<label for="Other" id="otherLabel"><i>Other</i></label>
			                			<input type="checkbox" name="other" value="other"\/>
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="Add this word search to your search filter">
										<input type="hidden" id="category" value="folioMod">
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
					               		<a class="helpIcon" title="<fmt:message key="advsearch.documents.docid"></fmt:message>">?</a>
										<input id="docId" name="docId" class="input_7c" type="text" maxlength="5" />
					               	</div>
					               	<div class="col_r">
					               		<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="Add this word search to your search filter">
										<input type="hidden" id="category" value="Doc Id">
					               	</div>
					            </div>
				            </form>
				        </div>
					</div>
					
					<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS">
					<h1 id="logicalDeleteSearch"><a>Logical Delete</a></h1>
					<div class="documents">
						<div class="listAdvSearch">
							<form id="logicalDeleteSearchForm" method="post" class="edit">
								<div class="row">
									<div class="col_l">
										<a class="helpIcon" title="<fmt:message key="advsearch.documents.logicaldelete"></fmt:message>">?</a>
										<label for="logicalDelete" id="logicalDeleteLabel">Deleted</label>
										<input type="checkbox" name="logicalDelete" value="true"/>
									</div>
									<div class="col_r">
										<input type="submit" id="addSearchFilter" class="button_small" value="Add" title="Add this word search to your search filter">
										<input type="hidden" id="category" value="Logical Delete">
									</div>
								</div>
							</form>
						</div>
					</div>
					</security:authorize>
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
                    $j("#folioModSearchForm").css('display', 'none');
                } else { 
                    $j('#folioBetween').css('visibility','hidden');
                    $j('.invisibleFol').css('visibility','hidden');
                    $j("#folioModSearchForm").css('display', 'table-row-group');
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

			$j("#wordSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#personSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#placeSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#senderSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#fromSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#recipientSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#toSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}"
			});
			$j("#refersToSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}"
			});
			$j("#extractSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#synopsisSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#topicSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : true
			});
			$j("#topicPlaceSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#dateSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#volumeSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#folioSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#folioModSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#docIdSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});
			$j("#logicalDeleteSearchForm").advancedSearchForm({
				AdvancedSearchCountURL : "${AdvancedSearchCountURL}",
				consoleLog : false
			});

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
			
			var $personValue = '';
			var $personAutocomplete = $j("#person").autocompletePerson({
				serviceUrl: '${searchPersonURL}',
			    loadingImageUrl:'${LoadingImageURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 600,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j(".personAdd").die();
					$j(".personAdd").attr("disabled");
					$j(".personAdd").removeAttr("disabled");
					$j(".personAdd").prop("disabled", false);
					$j('#personId').val(data);
					$personValue = $j("#person").val();
					$j("#person").live('keyup', function(){
						if($j("#person").val() != $personValue){
							$j(".personAdd").attr("disabled","disabled");
							$j("#personId").val("");
						}
						return false;
					});
					$j("#person").live('keypress', function(e){
						if(e.keyCode == 13 && $j("#person").val() != $personValue){
							e.stopPropagation();
							return false;
						}
					});
				}
			});
			
			//MD: To fix bug in Chrome
			$j("h1").click(function(){
				$personAutocomplete.killSuggestions();
				$placeAutocomplete.killSuggestions();
				$senderAutocomplete.killSuggestions();
				$fromAutocomplete.killSuggestions();
				$recipientAutocomplete.killSuggestions();
				$toAutocomplete.killSuggestions();
				$refersToAutocomplete.killSuggestions();
				$topicPlaceAutocomplete.killSuggestions();
				$volumeAutocomplete.killSuggestions();
				return false;
			});
			
			$j("#personSearchForm").submit(function(e){
				$j("#personId").val("");
				$j(".personAdd").attr("disabled","disabled");
			});
			
			var $placeValue = '';
			var $placeAutocomplete = $j("#place").autocompletePlace({
				serviceUrl: '${searchPlaceURL}',
			    loadingImageUrl:'${LoadingImageURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 450,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j(".placeAdd").die();
					$j(".placeAdd").removeAttr("disabled");
					$j('#placeId').val(data);
					$j(".placeAdd").attr("disabled");
					$j(".placeAdd").prop("disabled", false);
					$placeValue = $j("#place").val();
					$j("#place").live('keyup', function(){
						if($j("#place").val() != $placeValue){
							$j(".placeAdd").attr("disabled","disabled");
							$j("#placeId").val("");
						}
						return false;
					});
					$j("#place").live('keypress', function(e){
						if(e.keyCode == 13 && $j("#place").val() != $placeValue){
							e.stopPropagation();
							return false;
						}
					});
				}
			});	
			
			$j("#placeSearchForm").submit(function(){
				$j("#placeId").val("");
				$j(".placeAdd").attr("disabled","disabled");
			});
			
			var $senderValue = '';
			var $senderAutocomplete = $j("#sender").autocompletePerson({
				serviceUrl: '${searchPersonURL}',
			    loadingImageUrl:'${LoadingImageURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 600,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j(".senderAdd").die();
					$j(".senderAdd").removeAttr("disabled");
					$j('#senderId').val(data);
					$j(".senderAdd").attr("disabled");
					$j(".senderAdd").prop("disabled", false);
					$senderValue = $j("#sender").val();
					$j("#sender").live('keyup', function(){
						if($j("#sender").val() != $senderValue){
							$j(".senderAdd").attr("disabled","disabled");
							$j("#senderId").val("");
						}
						return false;
					});
					$j("#sender").live('keypress', function(e){
						if(e.keyCode == 13 && $j("#sender").val() != $senderValue){
							e.stopPropagation();
							return false;
						}
					});
				}
			});
			
			$j("#senderSearchForm").submit(function(){
				$j("#senderId").val("");
				$j(".senderAdd").attr("disabled","disabled");
			});		
			
			var $fromValue = '';
			var $fromAutocomplete = $j("#from").autocompletePlace({
				serviceUrl: '${searchPlaceURL}',
			    loadingImageUrl:'${LoadingImageURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 600,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j(".fromAdd").die();
					$j(".fromAdd").removeAttr("disabled");
					$j('#fromId').val(data);
					$j(".fromAdd").attr("disabled");
					$j(".fromAdd").prop("disabled", false);
					$fromValue = $j("#from").val();
					$j("#from").live('keyup', function(){
						if($j("#from").val() != $fromValue){
							$j(".fromAdd").attr("disabled","disabled");
							$j("#fromId").val("");
						}
						return false;
					});
					$j("#from").live('keypress', function(e){
						if(e.keyCode == 13 && $j("#from").val() != $fromValue){
							e.stopPropagation();
							return false;
						}
					});
				}
			});
			
			$j("#fromSearchForm").submit(function(){
				$j("#fromId").val("");
				$j(".fromAdd").attr("disabled","disabled");
			});
			
			var $recipientValue = '';
			var $recipientAutocomplete = $j("#recipient").autocompletePerson({
				serviceUrl: '${searchPersonURL}',
			    loadingImageUrl:'${LoadingImageURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 600,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j(".recipientAdd").die();
					$j(".recipientAdd").removeAttr("disabled");
					$j('#recipientId').val(data);
					$j(".recipientAdd").attr("disabled");
					$j(".recipientAdd").prop("disabled", false);
					$recipientValue = $j("#recipient").val();
					$j("#recipient").live('keyup', function(){
						if($j("#recipient").val() != $recipientValue){
							$j(".recipientAdd").attr("disabled","disabled");
							$j("#recipientId").val("");
						}
						return false;
					});
					$j("#recipient").live('keypress', function(e){
						if(e.keyCode == 13 && $j("#recipient").val() != $recipientValue){
							e.stopPropagation();
							return false;
						}
					});
				}
			});

			$j("#recipientSearchForm").submit(function(){
				$j("#recipientId").val("");
				$j(".recipientAdd").attr("disabled","disabled");
			});
			
			var $toValue = '';
			var $toAutocomplete = $j("#to").autocompletePlace({
				serviceUrl: '${searchPlaceURL}',
			    loadingImageUrl:'${LoadingImageURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 600,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j(".toAdd").die();
					$j(".toAdd").removeAttr("disabled");
					$j('#toId').val(data);
					$j(".toAdd").attr("disabled");
					$j(".toAdd").prop("disabled", false);
					$toValue = $j("#to").val();
					$j("#to").live('keyup', function(){
						if($j("#to").val() != $toValue){
							$j(".toAdd").attr("disabled","disabled");
							$j("#toId").val("");
						}
						return false;
					});
					$j("#to").live('keypress', function(e){
						if(e.keyCode == 13 && $j("#to").val() != $toValue){
							e.stopPropagation();
							return false;
						}
					});
				}
			});
			
			$j("#toSearchForm").submit(function(){
				$j("#toId").val("");
				$j(".toAdd").attr("disabled","disabled");
			});
			
			var $refersToValue = '';
			var $refersToAutocomplete = $j("#refersTo").autocompletePerson({
				serviceUrl: '${searchPersonURL}',
			    loadingImageUrl:'${LoadingImageURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 350,
				width: 600,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j(".refersToAdd").die();
					$j(".refersToAdd").removeAttr("disabled");
					$j('#refersToId').val(data);
					$j(".refersToAdd").attr("disabled");
					$j(".refersToAdd").prop("disabled", false);
					$refersToValue = $j("#refersTo").val();
					$j("#refersTo").live('keyup', function(){
						if($j("#refersTo").val() != $refersToValue){
							$j(".refersToAdd").attr("disabled","disabled");
							$j("#refersToId").val("");
						}
						return false;
					});
					$j("#refersTo").live('keypress', function(e){
						if(e.keyCode == 13 && $j("#refersTo").val() != $refersToValue){
							e.stopPropagation();
							return false;
						}
					});
				}
			});
			
			$j("#refersToSearchForm").submit(function(){
				$j("#refersToId").val("");
				$j(".refersToAdd").attr("disabled","disabled");
			});
			
// 			$j("#topicSelect").change(function(){
// 				 if($j(this).val() != "Select a Topic"){
// 				 	$j("#topicType").val($j(this).find("option:selected").text());
// // 				 	$j("#topicId").val($j(this).find("option:selected").val());
// 				 }
// 				 else{
// 					 $j("#topicType").val("");
// // 					 $j("#topicId").val("");
// 				 }
// 				 return false;
// 			 });

			var $topicPlaceValue = '';
			var $topicPlaceAutocomplete = $j("#topic").autocompletePlace({
				serviceUrl: '${searchPlaceURL}',
			    loadingImageUrl:'${LoadingImageURL}',
				minChars: 3,
				delimiter: null,
				maxHeight: 400,
				width: 450,
				zIndex: 9999,
				deferRequestBy: 0,
				noCache: true,
				onSelect: function(value, data){
					$j(".topicAdd").die();
					$j(".topicAdd").removeAttr("disabled");
					$j('#topicId').val(data);
					$j(".topicAdd").attr("disabled");
					$j(".topicAdd").prop("disabled", false);
					$topicPlaceValue = $j("#topic").val();
					$j("#topic").live('keyup', function(){
						if($j("#topic").val() != $topicPlaceValue){
							$j(".topicAdd").attr("disabled","disabled");
							$j("#topicId").val("");
						}
						return false;
					});
					$j("#topic").live('keypress', function(e){
						if(e.keyCode == 13 && $j("#topic").val() != $topicPlaceValue){
							e.stopPropagation();
							return false;
						}
					});
				}
			});	
			
			$j(".topicAdd").click(function(e){
				if($j("#topic").val() != '' && $j("#topicId").val() == ''){
					e.stopPropagation();
					return false;
				}
			});
			
			$j("#topicSearchForm").submit(function(){
				$j("#topicId").val("");
// 				$j(".topicAdd").attr("disabled","disabled");
			});
			
			var $volumeAutocomplete = $j("#volume").autocompleteGeneral({
				serviceUrl: '${searchVolumeURL}',
			    loadingImageUrl:'${LoadingImageURL}',
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
			
			
			
			var $volumeBetweenAutocomplete = $j("#volumeBetween").autocompleteGeneral({
				serviceUrl: '${searchVolumeURL}',
			    loadingImageUrl:'${LoadingImageURL}',
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
			
			$j("#volumeBetween").blur(function(){
				$volumeBetweenAutocomplete.killSuggestions();
				return false;
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