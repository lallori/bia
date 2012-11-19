<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://bia.medici.org/jsp:jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="AdvancedSearchURL" value="/src/AdvancedSearch.do"/>
	<c:url var="SaveUserSearchFilterURL" value="/src/SaveUserSearchFilter.do"/>
	<c:url var="AdvancedSearchCountURL" value="/src/AdvancedSearchCount.json" />

	<div id="yourSearchFilterDiv">
		<h1 class="advSearchTitle">Your search filter</h1>

		<form id="yourEasySearchFilterForm" action="${AdvancedSearchURL}" method="post">
			<p><u>Custom Search Filter</u></p>
			<br />
			<div id="wordSearchDiv">
			<c:forEach items="${searchFilter.filterData.words}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Word Search in <fmt:message key="advsearch.documents.wordType.${searchFilter.filterData.wordsTypes[iterator.index]}" />: </span><span class="wordSearch">${searchFilter.filterData.words[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.wordsTypes[iterator.index]}|${fn2:encode(searchFilter.filterData.words[iterator.index])}" name="word">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.person) && (not empty searchFilter.filterData.words)}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="personSearchDiv">
			<c:forEach items="${searchFilter.filterData.person}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Person: </span><span class="wordSearch">${searchFilter.filterData.person[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.personId[iterator.index]}|${fn2:encode(searchFilter.filterData.person[iterator.index])}" name="person">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.place) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="placeSearchDiv">
			<c:forEach items="${searchFilter.filterData.place}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Place: </span><span class="wordSearch">${searchFilter.filterData.place[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.placeId[iterator.index]}|${fn2:encode(searchFilter.filterData.place[iterator.index])}" name="place">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.sender) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="senderSearchDiv">
			<c:forEach items="${searchFilter.filterData.sender}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Sender: </span><span class="wordSearch">${searchFilter.filterData.sender[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.senderId[iterator.index]}|${fn2:encode(searchFilter.filterData.sender[iterator.index])}" name="sender">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.from) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place) || (not empty searchFilter.filterData.sender))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="fromSearchDiv">
			<c:forEach items="${searchFilter.filterData.from}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">From: </span><span class="wordSearch">${searchFilter.filterData.from[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.fromId[iterator.index]}|${fn2:encode(searchFilter.filterData.from[iterator.index])}" name="from">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.recipient) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place) || (not empty searchFilter.filterData.sender) || (not empty searchFilter.filterData.from))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="recipientSearchDiv">
			<c:forEach items="${searchFilter.filterData.recipient}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Recipient: </span><span class="wordSearch">${searchFilter.filterData.recipient[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.recipientId[iterator.index]}|${fn2:encode(searchFilter.filterData.recipient[iterator.index])}" name="recipient">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.to) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place) || (not empty searchFilter.filterData.sender) || (not empty searchFilter.filterData.from) || (not empty searchFilter.filterData.recipient))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="toSearchDiv">
			<c:forEach items="${searchFilter.filterData.to}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">To: </span><span class="wordSearch">${searchFilter.filterData.to[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.toId[iterator.index]}|${fn2:encode(searchFilter.filterData.to[iterator.index])}" name="to">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.refersTo) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place) || (not empty searchFilter.filterData.sender) || (not empty searchFilter.filterData.from) || (not empty searchFilter.filterData.recipient) || (not empty searchFilter.filterData.to))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="refersToSearchDiv">
			<c:forEach items="${searchFilter.filterData.refersTo}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Refers To: </span><span class="wordSearch">${searchFilter.filterData.refersTo[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.refersToId[iterator.index]}|${fn2:encode(searchFilter.filterData.refersTo[iterator.index])}" name="refersTo">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>	
			<c:if test="${(not empty searchFilter.filterData.extract) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place) || (not empty searchFilter.filterData.sender) || (not empty searchFilter.filterData.from) || (not empty searchFilter.filterData.recipient) || (not empty searchFilter.filterData.to) || (not empty searchFilter.filterData.refersTo))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="extractSearchDiv">
			<c:forEach items="${searchFilter.filterData.extract}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Transcription: </span><span class="wordSearch">${searchFilter.filterData.extract[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${fn2:encode(searchFilter.filterData.extract[iterator.index])}" name="extract">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.synopsis) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place) || (not empty searchFilter.filterData.sender) || (not empty searchFilter.filterData.from) || (not empty searchFilter.filterData.recipient) || (not empty searchFilter.filterData.to) || (not empty searchFilter.filterData.refersTo) || (not empty searchFilter.filterData.extract))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="synopsisSearchDiv">
			<c:forEach items="${searchFilter.filterData.synopsis}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Synopsis: </span><span class="wordSearch">${searchFilter.filterData.synopsis[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${fn2:encode(searchFilter.filterData.synopsis[iterator.index])}" name="synopsis">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.topics) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place) || (not empty searchFilter.filterData.sender) || (not empty searchFilter.filterData.from) || (not empty searchFilter.filterData.recipient) || (not empty searchFilter.filterData.to) || (not empty searchFilter.filterData.refersTo) || (not empty searchFilter.filterData.extract) || (not empty searchFilter.filterData.synopsis))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="topicSearchDiv">
			<c:forEach items="${searchFilter.filterData.topics}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Topic: </span><span class="wordSearch">${searchFilter.filterData.topics[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.topicsId[iterator.index]}|${fn2:encode(searchFilter.filterData.topics[iterator.index])}" name="topic">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.topicsPlace) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place) || (not empty searchFilter.filterData.sender) || (not empty searchFilter.filterData.from) || (not empty searchFilter.filterData.recipient) || (not empty searchFilter.filterData.to) || (not empty searchFilter.filterData.refersTo) || (not empty searchFilter.filterData.extract) || (not empty searchFilter.filterData.synopsis) || (not empty searchFilter.filterData.topics))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="topicPlaceSearchDiv">
			<c:forEach items="${searchFilter.filterData.topicsPlace}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Topic Place: </span><span class="wordSearch">${searchFilter.filterData.topicsPlace[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.topicsPlaceId[iterator.index]}|${fn2:encode(searchFilter.filterData.topicsPlace[iterator.index])}" name="topicPlace">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.datesTypes) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place) || (not empty searchFilter.filterData.sender) || (not empty searchFilter.filterData.from) || (not empty searchFilter.filterData.recipient) || (not empty searchFilter.filterData.to) || (not empty searchFilter.filterData.refersTo) || (not empty searchFilter.filterData.extract) || (not empty searchFilter.filterData.synopsis) || (not empty searchFilter.filterData.topics) || (not empty searchFilter.filterData.topicsPlace))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="dateSearchDiv">
			<c:forEach items="${searchFilter.filterData.datesTypes}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch"><fmt:message key="search.documents.dateType.${searchFilter.filterData.datesTypes[iterator.index]}" />: </span>
					<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'From'}"><span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'Before'}"><span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'Between'}"><span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]} , ${searchFilter.filterData.datesYearBetween[iterator.index]} ${months[searchFilter.filterData.datesMonthBetween[iterator.index]]} ${searchFilter.filterData.datesDayBetween[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'InOn'}"><span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<input type="hidden" value="${searchFilter.filterData.datesTypes[iterator.index]}|${searchFilter.filterData.datesYear[iterator.index]}|${searchFilter.filterData.datesMonth[iterator.index]}|${searchFilter.filterData.datesDay[iterator.index]}|${searchFilter.filterData.datesYearBetween[iterator.index]}|${searchFilter.filterData.datesMonthBetween[iterator.index]}|${searchFilter.filterData.datesDayBetween[iterator.index]}" name="date">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.volumes) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place) || (not empty searchFilter.filterData.sender) || (not empty searchFilter.filterData.from) || (not empty searchFilter.filterData.recipient) || (not empty searchFilter.filterData.to) || (not empty searchFilter.filterData.refersTo) || (not empty searchFilter.filterData.extract) || (not empty searchFilter.filterData.synopsis) || (not empty searchFilter.filterData.topics) || (not empty searchFilter.filterData.topicsPlace) || (not empty searchFilter.filterData.datesTypes))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="volumeSearchDiv">
			<c:forEach items="${searchFilter.filterData.volumes}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Volume in <fmt:message key="search.documents.volumeType.${searchFilter.filterData.volumesTypes[iterator.index]}" />: </span>
					<c:if test="${searchFilter.filterData.volumesTypes[iterator.index] == 'Exactly'}"><span class="wordSearch">${searchFilter.filterData.volumes[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<c:if test="${searchFilter.filterData.volumesTypes[iterator.index] == 'Between'}"><span class="wordSearch">${searchFilter.filterData.volumes[iterator.index]}, ${searchFilter.filterData.volumesBetween[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<input type="hidden" value="${searchFilter.filterData.volumesTypes[iterator.index]}|${searchFilter.filterData.volumes[iterator.index]}|${searchFilter.filterData.volumesBetween[iterator.index]}" name="volume">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.folios) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place) || (not empty searchFilter.filterData.sender) || (not empty searchFilter.filterData.from) || (not empty searchFilter.filterData.recipient) || (not empty searchFilter.filterData.to) || (not empty searchFilter.filterData.refersTo) || (not empty searchFilter.filterData.extract) || (not empty searchFilter.filterData.synopsis) || (not empty searchFilter.filterData.topics) || (not empty searchFilter.filterData.topicsPlace) || (not empty searchFilter.filterData.datesTypes) || (not empty searchFilter.filterData.volumes))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="folioSearchDiv">
			<c:forEach items="${searchFilter.filterData.folios}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Folio in ${searchFilter.filterData.foliosTypes[iterator.index]}: </span>
					<c:if test="${searchFilter.filterData.foliosTypes[iterator.index] == 'Exactly'}"><span class="wordSearch">${searchFilter.filterData.folios[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<c:if test="${searchFilter.filterData.foliosTypes[iterator.index] == 'Between'}"><span class="wordSearch">${searchFilter.filterData.folios[iterator.index]}, ${searchFilter.filterData.foliosBetween[iterator.index]}</span><a class="remove" href="#">(remove)</a></c:if>
					<input type="hidden" value="${searchFilter.filterData.foliosTypes[iterator.index]}|${searchFilter.filterData.folios[iterator.index]}|${searchFilter.filterData.foliosBetween[iterator.index]}" name="folio">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(not empty searchFilter.filterData.docIds) && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place) || (not empty searchFilter.filterData.sender) || (not empty searchFilter.filterData.from) || (not empty searchFilter.filterData.recipient) || (not empty searchFilter.filterData.to) || (not empty searchFilter.filterData.refersTo) || (not empty searchFilter.filterData.extract) || (not empty searchFilter.filterData.synopsis) || (not empty searchFilter.filterData.topics) || (not empty searchFilter.filterData.topicsPlace) || (not empty searchFilter.filterData.datesTypes) || (not empty searchFilter.filterData.volumes) || (not empty searchFilter.filterData.folios))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="docIdSearchDiv">
			<c:forEach items="${searchFilter.filterData.docIds}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Doc ID:</span><span class="wordSearch">${searchFilter.filterData.docIds[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.docIds[iterator.index]}" name="docId">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<c:if test="${(searchFilter.filterData.logicalDelete != null && searchFilter.filterData.logicalDelete.toString() == 'true') && ((not empty searchFilter.filterData.words) || (not empty searchFilter.filterData.person) || (not empty searchFilter.filterData.place) || (not empty searchFilter.filterData.sender) || (not empty searchFilter.filterData.from) || (not empty searchFilter.filterData.recipient) || (not empty searchFilter.filterData.to) || (not empty searchFilter.filterData.refersTo) || (not empty searchFilter.filterData.extract) || (not empty searchFilter.filterData.synopsis) || (not empty searchFilter.filterData.topics) || (not empty searchFilter.filterData.topicsPlace) || (not empty searchFilter.filterData.datesTypes) || (not empty searchFilter.filterData.volumes) || (not empty searchFilter.filterData.folios) || (not empty searchFilter.filterData.docIds))}"><hr><p class="andOrNotAdvancedSearchCenter">And</p><hr></c:if>
			<div id="logicalDeleteSearchDiv">
			<c:if test="${(searchFilter.filterData.logicalDelete != null)}">
				<div class="searchFilterDiv">
					<span class="categorySearch">Logical Delete:</span><span class="wordSearch">Yes</span><a class="remove" href="#">(remove)</a>
					<c:if test="${searchFilter.filterData.logicalDelete.toString() ==  'true'}">
						<input type="hidden" value="true" name="logicalDelete">
					</c:if>
					<c:if test="${searchFilter.filterData.logicalDelete.toString() == 'false' }">
						<input type="hidden" value="false" name="logicalDelete">
					</c:if>
				</div>
			</c:if>
			</div>
			
			<p class="yourSearchDiv">Records Found:
			<span class="recordsNum"></span></p>
			
			<br>
			<br>
			<a class="saveButton" href="#">Save</a>
			<input type="submit" title="Search" value="Search" id="advsearch">

			<input type="hidden" name="idSearchFilter" value="${command.idSearchFilter}">
			<input type="hidden" name="searchUUID" value="${command.searchUUID}">
			<input type="hidden" name="searchType" value="${command.searchType}">
		</form>
	</div>
	<script type="text/javascript">
		$j(document).ready(function() {
			//MD: Inserted the partial count on windows opening, because if we refine a search we can view the count
			$j.ajax({ type:"POST", url:'${AdvancedSearchCountURL}', data:$j("#yourEasySearchFilterForm").serialize(), async:false, success:function(json) {
 				// At this point we have count of total result. Review output page and put the total...
 				console.log("Advanced search result " + json.totalResult);
 				if(json.totalResult != undefined)
 					$j(".recordsNum").text(json.totalResult);
 				else
 					$j(".recordsNum").text("0");
 				return false;
			}});
			
			$j(".remove").live('click', function(){
				$j.ajax({ type:"POST", url:'${AdvancedSearchCountURL}', data:$j("#yourEasySearchFilterForm").serialize(), async:false, success:function(json) {
// 	 				At this point we have count of total result. Review output page and put the total...
	 				console.log("Advanced search result " + json.totalResult);
	 				if(json.totalResult != undefined)
	 					$j(".recordsNum").text(json.totalResult);
	 				else
	 					$j(".recordsNum").text("0");
				}});
				
				return false;
			});
			
			var $dialogSaveAs = $j('<div id="DialogSaveAs"></div>').dialog({
				autoOpen: false,
				width: 250,
				height: 180,
				modal: true,
				zIndex: 3999,
				overlay: {
					backgroundColor: '#000',
					opacity: 0.5
				},
				position: ['center',250],
				open: function(event, ui) { 
            		$j.ajax({ type:"GET", url: '${SaveUserSearchFilterURL}', data: $j("#yourEasySearchFilterForm").serialize(), async:false, success:function(html) { 
						$j("#DialogSaveAs").focus();
						$j("#DialogSaveAs").html(html);
						} 
					});
           		},
				dragStart: function(event, ui) {$j(".ui-widget-content").css('opacity', 0.30);},
				dragStop: function(event, ui) {$j(".ui-widget-content").css('opacity', 1);}
			});

			$j('.saveButton').click(function(){
				if ($dialogSaveAs.dialog("isOpen")) {
					$dialogSaveAs.dialog("close");
					return false;
				} else {
					$dialogSaveAs.dialog("open");
					return false;
				}
				return false;
			});

			$j("#yourEasySearchFilterForm").submit(function() {
				// this is search url form 
				var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();

				// If we found refine button of this search, user is in refine.
				if (window.opener.$j('#tabs').find("#refine${command.searchUUID}").length==1) {
					// calculate tab position
					var index = window.opener.$j("#tabs ul li").index(window.opener.$j("li:has(a[href='#" + window.opener.$j("#tabs").find("#refine${command.searchUUID}").parent().attr("id") + "'])"));
					window.opener.$j('#tabs ul li').eq(index).data('loaded', false).find('a').attr('href', formSubmitURL);
					window.opener.$j("#tabs").tabs("option", "active", index);
					window.opener.$j("#tabs").tabs("load" , index);
					//MD: Fix problem with title of tab (if it already exist, it still display "Loading...")
					window.opener.$j('#tabs ul li').eq(index).find('a').empty();
					window.opener.$j('#tabs ul li').eq(index).find('a').append("<span>Document Search</span>");
					window.close()
				} else {
					//otherwise it's in a new search so we add a new tab.
					window.opener.$j("#tabs").tabs("add", formSubmitURL, "Document Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
// 					window.opener.$j( "<li><a href='" + formSubmitURL + "'><span>Document Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab</span></li>" ).appendTo( "#tabs .ui-tabs-nav" );
// 					window.opener.$j( "#tabs" ).tabs( "refresh" );
					window.opener.$j("#tabs").tabs("select", window.opener.$j("#tabs").tabs("length")-1);
					window.close();
				}
				return false;
			});
		});
	</script>
