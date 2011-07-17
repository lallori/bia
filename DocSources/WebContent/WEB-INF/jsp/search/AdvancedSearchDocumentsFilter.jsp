<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn2" uri="http://docsources.medici.org/jsp/jstl" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="AdvancedSearchDocumentsURL" value="/src/AdvancedSearchDocuments.do"/>
	<c:url var="SaveUserSearchFilterURL" value="/src/SaveUserSearchFilter.do"/>

	<div id="yourSearchFilterDiv">
		<div class="yourSearchFilterTitle"></div>
		<div class="easySearchModeOn"></div>
		<form id="yourEasySearchFilterForm" action="${AdvancedSearchDocumentsURL}" method="post">
			<p><u>Custom Search Filter</u></p>
			<br />
			<div id="wordSearchDiv">
			<c:forEach items="${searchFilter.filterData.words}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Word Search in <fmt:message key="search.documents.wordType.${searchFilter.filterData.wordsTypes[iterator.index]}" />: </span><span class="wordSearch">${searchFilter.filterData.words[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.wordsTypes[iterator.index]}|${fn2:encode(searchFilter.filterData.words[iterator.index])}" name="word">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<div id="personSearchDiv">
			<c:forEach items="${searchFilter.filterData.person}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Person: </span><span class="wordSearch">${searchFilter.filterData.person[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.personId[iterator.index]}|${fn2:encode(searchFilter.filterData.person[iterator.index])}" name="person">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<div id="placeSearchDiv">
			<c:forEach items="${searchFilter.filterData.place}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Place: </span><span class="wordSearch">${searchFilter.filterData.place[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.placeId[iterator.index]}|${fn2:encode(searchFilter.filterData.place[iterator.index])}" name="place">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<div id="senderSearchDiv">
			<c:forEach items="${searchFilter.filterData.sender}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Sender: </span><span class="wordSearch">${searchFilter.filterData.sender[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.senderId[iterator.index]}|${fn2:encode(searchFilter.filterData.sender[iterator.index])}" name="sender">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<div id="fromSearchDiv">
			<c:forEach items="${searchFilter.filterData.from}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">From: </span><span class="wordSearch">${searchFilter.filterData.from[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.fromId[iterator.index]}|${fn2:encode(searchFilter.filterData.from[iterator.index])}" name="from">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<div id="recipientSearchDiv">
			<c:forEach items="${searchFilter.filterData.recipient}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Recipient: </span><span class="wordSearch">${searchFilter.filterData.recipient[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.recipientId[iterator.index]}|${fn2:encode(searchFilter.filterData.recipient[iterator.index])}" name="recipient">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<div id="toSearchDiv">
			<c:forEach items="${searchFilter.filterData.to}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">To: </span><span class="wordSearch">${searchFilter.filterData.to[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.toId[iterator.index]}|${fn2:encode(searchFilter.filterData.to[iterator.index])}" name="to">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<div id="refersToSearchDiv">
			<c:forEach items="${searchFilter.filterData.refersTo}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">From: </span><span class="wordSearch">${searchFilter.filterData.refersTo[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.refersToId[iterator.index]}|${fn2:encode(searchFilter.filterData.refersTo[iterator.index])}" name="refersTo">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>	
			<div id="extractSearchDiv">
			<c:forEach items="${searchFilter.filterData.extract}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Extract: </span><span class="wordSearch">${searchFilter.filterData.extract[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${fn2:encode(searchFilter.filterData.extract[iterator.index])}" name="extract">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<div id="synopsisSearchDiv">
			<c:forEach items="${searchFilter.filterData.synopsis}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Synopsis: </span><span class="wordSearch">${searchFilter.filterData.extract[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${fn2:encode(searchFilter.filterData.synopsis[iterator.index])}" name="synopsis">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<div id="topicSearchDiv">
			<c:forEach items="${searchFilter.filterData.topics}" varStatus="iterator">
				<div class="searchFilterDiv">
					<span class="categorySearch">Topic: </span><span class="wordSearch">${searchFilter.filterData.topics[iterator.index]}</span><a class="remove" href="#">(remove)</a>
					<input type="hidden" value="${searchFilter.filterData.topicsId[iterator.index]}|${fn2:encode(searchFilter.filterData.topics[iterator.index])}" name="refersTo">
				</div>
				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch">And</p></c:if>
			</c:forEach>
			</div>
			<div id="dateSearchDiv">
			</div>
			<div id="volumeSearchDiv">
			</div>
			<br>
			<br>
			<input type="submit" title="Search" value="Search" id="advsearch">
			<a class="saveButton" href="#">Save</a>
			<a class="saveAsButton" href="#">Save as</a>

			<input type="hidden" name="idSearchFilter" value="${command.idSearchFilter}">
			<input type="hidden" name="searchUUID" value="${command.searchUUID}">
			<input type="hidden" name="searchType" value="DOCUMENT">
		</form>
	</div>
	<script type="text/javascript">
		$j(document).ready(function() {
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

			$j('.saveAsButton').click(function(){
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
				/*window.opener.$('div[id*="ui-tabs-"]').each(function(index) {
		            if($(this).hasClass('testClass'))
		                $(this).remove();
		            else
		                $(this).addClass('testClass');
		        });*/
				var formSubmitURL = $j(this).attr("action") + '?' + $j(this).serialize();
				window.opener.$j("#tabs").tabs("add", formSubmitURL, "Advanced Search</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				window.opener.$j("#tabs").tabs("select", window.opener.$j("#tabs").tabs("length")-1);
				return false;
			});
		});
	</script>
