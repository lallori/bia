<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="MoreDocumentsURL" value="/user/ShowMyHistoryReportByCategory.do">
		<c:param name="category" value="DOCUMENT"/>
	</c:url>

	<c:url var="MorePeopleURL" value="/user/ShowMyHistoryReportByCategory.do">
		<c:param name="category" value="PEOPLE"/>
	</c:url>

	<c:url var="MorePlacesURL" value="/user/ShowMyHistoryReportByCategory.do">
		<c:param name="category" value="PLACE"/>
	</c:url>

	<c:url var="MoreVolumesURL" value="/user/ShowMyHistoryReportByCategory.do">
		<c:param name="category" value="VOLUME"/>
	</c:url>

<div id="MyHistoryDiv">
	<div id="DocsHistory">
		<div class="title">
        	<h5>DOCUMENTS</h5>
        </div>
        
        <div class="historyList">
        	<c:forEach items="${historyReport['Document']}" var="currentHistory" varStatus="status">
        	<c:url var="showURL" value="/src/docbase/ShowDocument.do?entryId=${currentHistory.document.entryId}"/>
            <div class="historyRow">
                <div class="historyDate"><a href="${showURL}" class="showHistory"><fmt:formatDate pattern="MM/dd/yyyy" value="${currentHistory.dateAndTime}" /></a></div> 
                <div class="historyAction" title="Deleted">${currentHistory.action}</div>
                <div class="historyItem"><a href="${showURL}" class="showHistory">${currentHistory.document.MDPAndFolio}</a></div>
			</div>
        	</c:forEach>
		</div>
        
        <a href="${MoreDocumentsURL}" id="moreDocs">More<span class="arrow"></span></a>
    </div>

    <div id="VolumesHistory">
		<div class="title">
        	<h5>VOLUME</h5>
        </div>
        
        <div class="historyList">
        	<c:forEach items="${historyReport['Volume']}" var="currentHistory" varStatus="status">
        	<c:url var="showURL" value="/src/volbase/ShowVolume.do?summaryId=${currentHistory.volume.summaryId}"/>
            <div class="historyRow">
                <div class="historyDate"><a href="${showURL}" class="showHistory"><fmt:formatDate pattern="MM/dd/yyyy" value="${currentHistory.dateAndTime}" /></a></div> 
                <div class="historyAction" title="Deleted">${currentHistory.action}</div>
                <div class="historyItem"><a href="${showURL}" class="showHistory">${currentHistory.volume}</a></div>
			</div>
        	</c:forEach>
		</div>
        
        <a href="${MoreVolumesURL}" id="moreVolumes">More<span class="arrow"></span></a>
    </div>
    
    <div id="PlaceHistory">
		<div class="title">
        	<h5>PLACE</h5>
        </div>
        
        <div class="historyList">
        	<c:forEach items="${historyReport['Place']}" var="currentHistory" varStatus="status">
        		<c:url var="showURL" value="/src/geobase/ShowPlace.do?placeAllId=${currentHistory.place.placeAllId}"/>
            <div class="historyRow">
                <div class="historyDate"><a href="${showURL}" class="showHistory"><fmt:formatDate pattern="MM/dd/yyyy" value="${currentHistory.dateAndTime}" /></a></div> 
                <div class="historyAction" title="Deleted">${currentHistory.action}</div>
                <div class="historyItem"><a href="${showURL}" class="showHistory">${currentHistory.place.placeName}</a></div>
			</div>
        	</c:forEach>
		</div>
        
        <a href="${MorePlacesURL}" id="morePlaces">More<span class="arrow"></span></a>
    </div>
    
    <div id="PeopleHistory">
		<div class="title">
        	<h5>PEOPLE</h5>
        </div>
        
        <div class="historyList">
        	<c:forEach items="${historyReport['People']}" var="currentHistory" varStatus="status">
        	<c:url var="showURL" value="/src/peoplebase/ShowPerson.do?personId=${currentHistory.person.personId}"/>
            <div class="historyRow">
                <div class="historyDate"><a href="${showURL}" class="showHistory"><fmt:formatDate pattern="MM/dd/yyyy" value="${currentHistory.dateAndTime}" /></a></div> 
                <div class="historyAction" title="Deleted">${currentHistory.action}</div>
                <c:if test="${currentHistory.person.mapNameLf.length() <= 20}">
                	<div class="historyItem"><a href="${showURL}" class="showHistory">${currentHistory.person}</a></div>
                </c:if>
                <c:if test="${currentHistory.person.mapNameLf.length() > 20}">
                	<div class="historyItem"><a href="${showURL}" class="showHistory">${fn:substring(currentHistory.person.mapNameLf,0,16)}...</a></div>
                </c:if>
			</div>
        	</c:forEach>
		</div>
        
        <a href="${MorePeopleURL}" id="morePeople">More<span class="arrow"></span></a>
    </div>

    <div id="MyHistoryButtons">
        <a href="<c:url value="/user/ShowMyHistory.do"/>" id="generalHistory">Research History</a>
        <input id="close" type="submit" title="Close Chronology window" value="Close"/>
    </div>
</div>


<script type="text/javascript">
	$j(document).ready(function() {
		$j("#moreDocs").click(function() {															
			Modalbox.show($j(this).attr("href"), {title: "MY DOCUMENTS HISTORY ", width: 750});return false;
		});	
		$j("#morePeople").click(function() {															
				Modalbox.show($j(this).attr("href"), {title: "MY PEOPLE HISTORY", width: 750});return false;
		});	
		$j("#moreVolumes").click(function() {
			Modalbox.show($j(this).attr("href"), {title: "MY VOLUMES HISTORY", width: 750});return false;
		});							   	
		$j("#morePlaces").click(function() {
			Modalbox.show($j(this).attr("href"), {title: "MY PLACE HISTORY", width: 750});return false;
		});
		$j(".showHistory").click(function() {
			$j("#body_left").load($j(this).attr("href"));
			Modalbox.hide(); 
			return false;
		});
		$j("#generalHistory").click(function() {															
			Modalbox.show($j(this).attr("href"), {title: "RESEARCH HISTORY", width: 750});return false;}
		);	
		$j("#close").click(function(){
			Modalbox.hide(); return false;}
		);
	});
</script>