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
		<h1 class="advSearchTitle"><fmt:message key="search.advancedSearchDocumentsFilter.yourSearchFilter.title"/></h1>

		<form id="yourEasySearchFilterForm" action="${AdvancedSearchURL}" method="post">
			<p><u><fmt:message key="search.advancedSearchDocumentsFilter.customSearchFilter.title"/></u></p>
			<br />
			<div id="wordSearchDiv">
				<c:forEach items="${searchFilter.filterData.words}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch">Word Search in <fmt:message key="search.advancedSearchDocumentsFilter.wordType.${searchFilter.filterData.wordsTypes[iterator.index]}" />: </span>
						<span class="wordSearch">${searchFilter.filterData.words[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${searchFilter.filterData.wordsTypes[iterator.index]}|${fn2:encode(searchFilter.filterData.words[iterator.index])}" name="word">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="personPre" value="${not empty searchFilter.filterData.words}" />
			<c:if test="${(not empty searchFilter.filterData.person) && personPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="personSearchDiv">
				<c:forEach items="${searchFilter.filterData.person}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch">Person: </span>
						<span class="wordSearch">${searchFilter.filterData.person[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${searchFilter.filterData.personId[iterator.index]}|${fn2:encode(searchFilter.filterData.person[iterator.index])}" name="person">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="placePre" value="${personPre || not empty searchFilter.filterData.person}" />
			<c:if test="${not empty searchFilter.filterData.place && placePre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="placeSearchDiv">
				<c:forEach items="${searchFilter.filterData.place}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.place"/>: </span>
						<span class="wordSearch">${searchFilter.filterData.place[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${searchFilter.filterData.placeId[iterator.index]}|${fn2:encode(searchFilter.filterData.place[iterator.index])}" name="place">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="senderPre" value="${placePre || not empty searchFilter.filterData.place}" />
			<c:if test="${not empty searchFilter.filterData.sender && senderPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="senderSearchDiv">
				<c:forEach items="${searchFilter.filterData.sender}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.sender"/>: </span>
						<span class="wordSearch">${searchFilter.filterData.sender[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${searchFilter.filterData.senderId[iterator.index]}|${fn2:encode(searchFilter.filterData.sender[iterator.index])}" name="sender">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="fromPre" value="${senderPre || not empty searchFilter.filterData.sender}" />
			<c:if test="${not empty searchFilter.filterData.from && fromPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="fromSearchDiv">
				<c:forEach items="${searchFilter.filterData.from}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.senderFrom"/>: </span>
						<span class="wordSearch">${searchFilter.filterData.from[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${searchFilter.filterData.fromId[iterator.index]}|${fn2:encode(searchFilter.filterData.from[iterator.index])}" name="from">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="recipientPre" value="${fromPre || not empty searchFilter.filterData.from}" />
			<c:if test="${not empty searchFilter.filterData.recipient && recipientPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="recipientSearchDiv">
				<c:forEach items="${searchFilter.filterData.recipient}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.recipient"/>: </span>
						<span class="wordSearch">${searchFilter.filterData.recipient[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${searchFilter.filterData.recipientId[iterator.index]}|${fn2:encode(searchFilter.filterData.recipient[iterator.index])}" name="recipient">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="toPre" value="${recipientPre || not empty searchFilter.filterData.recipient}" />
			<c:if test="${not empty searchFilter.filterData.to && toPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="toSearchDiv">
				<c:forEach items="${searchFilter.filterData.to}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.recipientTo"/>: </span>
						<span class="wordSearch">${searchFilter.filterData.to[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${searchFilter.filterData.toId[iterator.index]}|${fn2:encode(searchFilter.filterData.to[iterator.index])}" name="to">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="refersToPre" value="${toPre || not empty searchFilter.filterData.to}" />
			<c:if test="${not empty searchFilter.filterData.refersTo && refersToPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="refersToSearchDiv">
				<c:forEach items="${searchFilter.filterData.refersTo}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.refersTo"/>: </span>
						<span class="wordSearch">${searchFilter.filterData.refersTo[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${searchFilter.filterData.refersToId[iterator.index]}|${fn2:encode(searchFilter.filterData.refersTo[iterator.index])}" name="refersTo">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="extractPre" value="${refersToPre || not empty searchFilter.filterData.refersTo}" />
			<c:if test="${not empty searchFilter.filterData.extract && extractPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="extractSearchDiv">
				<c:forEach items="${searchFilter.filterData.extract}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.transcription"/>: </span>
						<span class="wordSearch">${searchFilter.filterData.extract[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${fn2:encode(searchFilter.filterData.extract[iterator.index])}" name="extract">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="synopsisPre" value="${extractPre || not empty searchFilter.filterData.extract}" />
			<c:if test="${not empty searchFilter.filterData.synopsis && synopsisPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="synopsisSearchDiv">
				<c:forEach items="${searchFilter.filterData.synopsis}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.synopsis"/>: </span>
						<span class="wordSearch">${searchFilter.filterData.synopsis[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${fn2:encode(searchFilter.filterData.synopsis[iterator.index])}" name="synopsis">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="topicPre" value="${synopsisPre || not empty searchFilter.filterData.synopsis}" />
			<c:if test="${not empty searchFilter.filterData.topics && topicPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="topicSearchDiv">
				<c:forEach items="${searchFilter.filterData.topics}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.topic"/>: </span>
						<span class="wordSearch">${searchFilter.filterData.topics[iterator.index]} <i>${searchFilter.filterData.topicsPlace[iterator.index]}</i></span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${searchFilter.filterData.topicsId[iterator.index]}|${fn2:encode(searchFilter.filterData.topics[iterator.index])}|${searchFilter.filterData.topicsPlaceId[iterator.index]}|${fn2:encode(searchFilter.filterData.topicsPlace[iterator.index])}" name="topic">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<!-- <c:set var="topicPlacePre" value="${topicPre || not empty searchFilter.filterData.topics}" /> -->
<%-- 			<c:if test="${(not empty searchFilter.filterData.topicsPlace) && topicPlacePre}"><hr><p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p><hr></c:if> --%>
<!-- 			<div id="topicPlaceSearchDiv"> -->
<%-- 			<c:forEach items="${searchFilter.filterData.topicsPlace}" varStatus="iterator"> --%>
<!-- 				<div class="searchFilterDiv"> -->
<%-- 					<span class="categorySearch">Topic Place: </span><span class="wordSearch">${searchFilter.filterData.topicsPlace[iterator.index]}</span><a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a> --%>
<%-- 					<input type="hidden" value="${searchFilter.filterData.topicsPlaceId[iterator.index]}|${fn2:encode(searchFilter.filterData.topicsPlace[iterator.index])}" name="topicPlace"> --%>
<!-- 				</div> -->
<%-- 				<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 			</div> -->
			
			<c:set var="datePre" value="${topicPre || not empty searchFilter.filterData.topics}" />
			<c:if test="${not empty searchFilter.filterData.datesTypes && datePre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="dateSearchDiv">
				<c:forEach items="${searchFilter.filterData.datesTypes}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="advsearch.documents.dateType.${searchFilter.filterData.datesTypes[iterator.index]}" />: </span>
						<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'From'}">
							<span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}</span>
							<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						</c:if>
						<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'Before'}">
							<span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}</span>
							<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						</c:if>
						<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'Between'}">
							<span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]} , ${searchFilter.filterData.datesYearBetween[iterator.index]} ${months[searchFilter.filterData.datesMonthBetween[iterator.index]]} ${searchFilter.filterData.datesDayBetween[iterator.index]}</span>
							<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						</c:if>
						<c:if test="${searchFilter.filterData.datesTypes[iterator.index] == 'InOn'}">
							<span class="wordSearch">${searchFilter.filterData.datesYear[iterator.index]} ${months[searchFilter.filterData.datesMonth[iterator.index]]} ${searchFilter.filterData.datesDay[iterator.index]}</span>
							<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						</c:if>
						<input type="hidden" value="${searchFilter.filterData.datesTypes[iterator.index]}|${searchFilter.filterData.datesYear[iterator.index]}|${searchFilter.filterData.datesMonth[iterator.index]}|${searchFilter.filterData.datesDay[iterator.index]}|${searchFilter.filterData.datesYearBetween[iterator.index]}|${searchFilter.filterData.datesMonthBetween[iterator.index]}|${searchFilter.filterData.datesDayBetween[iterator.index]}" name="date">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="volumePre" value="${datePre || not empty searchFilter.filterData.datesTypes}" />
			<c:if test="${not empty searchFilter.filterData.volumes && volumePre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="volumeSearchDiv">
				<c:forEach items="${searchFilter.filterData.volumes}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.volumeIn"/> <fmt:message key="advsearch.documents.volumeType.${searchFilter.filterData.volumesTypes[iterator.index]}" />: </span>
						<c:if test="${searchFilter.filterData.volumesTypes[iterator.index] == 'Exactly'}">
							<span class="wordSearch">${searchFilter.filterData.volumes[iterator.index]}</span>
							<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						</c:if>
						<c:if test="${searchFilter.filterData.volumesTypes[iterator.index] == 'Between'}">
							<span class="wordSearch">${searchFilter.filterData.volumes[iterator.index]}, ${searchFilter.filterData.volumesBetween[iterator.index]}</span>
							<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						</c:if>
						<input type="hidden" value="${searchFilter.filterData.volumesTypes[iterator.index]}|${searchFilter.filterData.volumes[iterator.index]}|${searchFilter.filterData.volumesBetween[iterator.index]}" name="volume">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="insertPre" value="${volumePre || not empty searchFilter.filterData.volumes}" />
			<c:if test="${not empty searchFilter.filterData.insertNums && insertPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="insertSearchDiv">
				<c:forEach items="${searchFilter.filterData.insertNums}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.insertIn"/>:</span>
						<span class="wordSearch">${searchFilter.filterData.insertNums[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${searchFilter.filterData.insertNums[iterator.index]}" name="insert">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="folioPre" value="${insertPre || not empty searchFilter.filterData.insertNums}" />
			<c:if test="${not empty searchFilter.filterData.folios && folioPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="folioSearchDiv">
				<c:forEach items="${searchFilter.filterData.folios}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.folioIn"/> ${searchFilter.filterData.foliosTypes[iterator.index]}: </span>
						<c:if test="${searchFilter.filterData.foliosTypes[iterator.index] == 'Exactly'}">
							<span class="wordSearch">${searchFilter.filterData.folios[iterator.index]}</span>
							<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						</c:if>
						<c:if test="${searchFilter.filterData.foliosTypes[iterator.index] == 'Between'}">
							<span class="wordSearch">${searchFilter.filterData.folios[iterator.index]}, ${searchFilter.filterData.foliosBetween[iterator.index]}</span>
							<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						</c:if>
						<input type="hidden" value="${searchFilter.filterData.foliosTypes[iterator.index]}|${searchFilter.filterData.folios[iterator.index]}|${searchFilter.filterData.foliosBetween[iterator.index]}" name="folio">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="folioModPre" value="${folioPre || not empty searchFilter.filterData.folios}" />
			<c:if test="${not empty searchFilter.filterData.folioMods && folioModPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="folioModSearchDiv">
				<c:forEach items="${searchFilter.filterData.folioMods}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.folioAddenda"/>: </span>
						<span class="wordSearch">${searchFilter.filterData.folioMods[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${searchFilter.filterData.folioMods[iterator.index]}" name="folioMod">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="docIdPre" value="${folioModPre || not empty searchFilter.filterData.folioMods}" />
			<c:if test="${not empty searchFilter.filterData.docIds && docIdPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="docIdSearchDiv">
				<c:forEach items="${searchFilter.filterData.docIds}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.docId"/>:</span>
						<span class="wordSearch">${searchFilter.filterData.docIds[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${searchFilter.filterData.docIds[iterator.index]}" name="docId">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="userPre" value="${docIdPre || not empty searchFilter.filterData.docIds}" />
			<c:if test="${not empty searchFilter.filterData.users && userPre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="userSearchDiv">
				<c:forEach items="${searchFilter.filterData.users}" varStatus="iterator">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.users"/>:</span>
						<span class="wordSearch">${searchFilter.filterData.users[iterator.index]}</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<input type="hidden" value="${searchFilter.filterData.users[iterator.index]}" name="user">
					</div>
					<c:if test="${!iterator.last}"><p class="andOrNotAdvancedSearch"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p></c:if>
				</c:forEach>
			</div>
			
			<c:set var="logicalDeletePre" value="${userPre || not empty searchFilter.filterData.users}" />
			<c:if test="${not empty searchFilter.filterData.logicalDelete && searchFilter.filterData.logicalDelete.toString() == 'true' && logicalDeletePre}">
				<hr>
				<p class="andOrNotAdvancedSearchCenter"><fmt:message key="search.advancedSearchDocumentsFilter.andBoolean"/></p>
				<hr>
			</c:if>
			<div id="logicalDeleteSearchDiv">
				<c:if test="${not empty searchFilter.filterData.logicalDelete}">
					<div class="searchFilterDiv">
						<span class="categorySearch"><fmt:message key="search.advancedSearchDocumentsFilter.logicalDelete"/>:</span>
						<span class="wordSearch">Yes</span>
						<a class="remove" href="#"><fmt:message key="search.advancedSearchDocumentsFilter.remove"/></a>
						<c:if test="${searchFilter.filterData.logicalDelete.toString() ==  'true'}">
							<input type="hidden" value="true" name="logicalDelete">
						</c:if>
						<c:if test="${searchFilter.filterData.logicalDelete.toString() == 'false' }">
							<input type="hidden" value="false" name="logicalDelete">
						</c:if>
					</div>
				</c:if>
			</div>
			
			<p class="yourSearchDiv"><fmt:message key="search.advancedSearchDocumentsFilter.recordsFound"/>:
			<span class="recordsNum"></span></p>
			
			<br>
			<br>
			<a class="saveButton" href="#">Save</a>
			<input type="submit" title="Search" value="Search" id="advsearch" class="button_small">

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
					var title = window.opener.$j('#tabs ul li').eq(index).find('a').text();
					window.opener.$j('#tabs ul li').eq(index).data('loaded', false).find('a').attr('href', formSubmitURL);
					window.opener.$j("#tabs").tabs("option", "active", index);
					window.opener.$j("#tabs").tabs("load" , index);
					//MD: Fix problem with title of tab (if it already exist, it still display "Loading...")
					window.opener.$j('#tabs ul li').eq(index).find('a').empty();
					window.opener.$j('#tabs ul li').eq(index).find('a').append("<span>" + title + "</span>");
					window.close()
				} else {
					//otherwise it's in a new search so we add a new tab.
					var numTab = 0;
					var tabExist = false;
					var regEx = new RegExp("(Document Search)+");
					window.opener.$j("#tabs ul li a").each(function(){
						//MD: Declare variable toTest for fix problem with IE
						var toTest = "";
						toTest += this.text;
						if(!tabExist){
							if(toTest != ""){
								numTab++;
							}
							if(regEx.test(toTest)){
								tabExist = true;
							}
						}
					});
					if(!tabExist){
						window.opener.$j("#documentSearchTabNumber").val(1);
					}
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
