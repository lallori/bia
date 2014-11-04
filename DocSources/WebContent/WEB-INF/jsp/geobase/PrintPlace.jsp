<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a href="javascript:window.print()" class="print" title="Print"></a>
	
	<div id="top">
		<div id="logoMap"><img src="<c:url value="/images/1024/img_map_print.jpg" />" alt="The Medici Archive Project" /></div>
	</div>
	
	<h4><fmt:message key="geobase.printPlace.documentarySources"/><br /><fmt:message key="geobase.printPlace.placeReport"/></h4>
            
    <h5 class="first"><fmt:message key="geobase.printPlace.placeDetails"/></h5>
    <table>
        <tr> 
            <td width="20%"><fmt:message key="geobase.printPlace.placeID"/></td>
            <td width="20%" class="value">${place.placeAllId}</td>
            <td width="20%"><fmt:message key="geobase.printPlace.placeName"/></td>
            <td width="40%" class="value">${place.placeName}</td>
       </tr>
       <tr>
            <td width="20%"><fmt:message key="geobase.printPlace.withAccents"/></td>
            <td width="80%" class="value" colspan="3">${place.placeNameFull}</td>
        </tr>
        <tr>
        	<td width="20%"><fmt:message key="geobase.printPlace.placeType"/></td>
            <td width="80%" class="value" colspan="3">${place.plType}</td>
        </tr>
        <tr>
            <td width="20%"><fmt:message key="geobase.printPlace.placeParent"/></td>
            <td width="80%" colspan="3" class="value">${place.plParent}</td>
        </tr>
        <tr>
            <td width="20%"><fmt:message key="geobase.printPlace.placeNotes"/></td>
            <td width="80%" colspan="5" class="value">${place.placesMemo}</td>
        </tr>
    </table> 
    
    <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
    
    <h5><fmt:message key="geobase.printPlace.nameOr"/></h5>
    <table>
      <table>
   		 <c:forEach items="${placeNames}" var="currentName">
   		 	<c:if test="${currentName.prefFlag == 'P'}">
				<tr>
					<td width="20%"><fmt:message key="geobase.printPlace.principal"/></td>
					<td width="80%" class="value">${currentName.placeName}</td>
				</tr>
			</c:if>
			<c:if test="${currentName.prefFlag == 'V'}">
				<tr>
					<td width="20%"><fmt:message key="geobase.printPlace.variant"/></td>
					<td width="80%" class="value">${currentName.placeName}</td>
				</tr>
			</c:if>							
		</c:forEach> 		 
    </table>
    <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
    
    <h5><fmt:message key="geobase.printPlace.sendersAndRecipients"/></h5>
    <table>
        <tr>
        	<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1}">
            	<td width="100%">${senderPlace} <fmt:message key="geobase.printPlace.senders"/></td>
            </c:if>
            <c:if test="${senderPlace == 1 }">
            	<td width="100%">${senderPlace} <fmt:message key="geobase.printPlace.sender"/></td>
            </c:if>
            <c:if test="${senderPlace == 0 || senderPlace == null}">
				<td width="100%"><fmt:message key="geobase.printPlace.zeroSenders"/></td>
			</c:if>
            
        </tr>
        <tr>
        	<c:if test="${recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
				<td width="100%">${recipientPlace} <fmt:message key="geobase.printPlace.recipients"/></td>
			</c:if>
			<c:if test="${recipientPlace == 1}">
				<td width="100%">${recipientPlace} <fmt:message key="geobase.printPlace.recipient"/></td>
			</c:if>
			<c:if test="${recipientPlace == 0 || recipientPlace == null}">
				<td width="100%"><fmt:message key="geobase.printPlace.zeroRecipients"/></td>
			</c:if>
        </tr>
    </table>
    
   <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
    
    <h5><fmt:message key="geobase.printPlace.topicsList"/></h5>
    <table>
        <tr>
	        <c:if test="${topicsPlace != null && topicsPlace != 0 && topicsPlace != 1 && docInTopics != 1}"> 
	        	<td width="100%">${docInTopics} <fmt:message key="geobase.printPlace.documentsOn"/> ${topicsPlace} <fmt:message key="geobase.printPlace.topics"/></td>
	        </c:if>
			<c:if test="${topicsPlace == 1}">
	    		<td width="100%">${docInTopics} <fmt:message key="geobase.printPlace.documentOn"/> ${topicsPlace} <fmt:message key="geobase.printPlace.topic"/></td>
			</c:if>
			<c:if test="${docInTopics == 1 && topicsPlace != 1}">
	    		<td width="100%">${docInTopics} <fmt:message key="geobase.printPlace.documentOn"/> ${topicsPlace} <fmt:message key="geobase.printPlace.topics"/></td>
			</c:if>
			<c:if test="${topicsPlace == 0 || topicsPlace == null}">
	    		<td width="100%"><fmt:message key="geobase.printPlace.zeroDocuments"/></td>
			</c:if>
        </tr>
    </table> 
    
    <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
    
    <h5><fmt:message key="geobase.printPlace.birthAndDeathPlace"/></h5>
    <table>
        <tr>
        	<td width="100%">
	        	<c:if test="${birthPlace != 0}">${birthPlace} <fmt:message key="geobase.printPlace.births"/> </c:if>
	        	<c:if test="${birthPlace == 1}"><fmt:message key="geobase.printPlace.oneBirth"/> </c:if>
	        	<c:if test="${birthPlace == 0}"><fmt:message key="geobase.printPlace.zeroBirths"/> </c:if>
	        	<c:if test="${activeStartPlace != 0}">${activeStartPlace} <fmt:message key="geobase.printPlace.activeStarts"/></c:if>
	        	<c:if test="${activeStartPlace == 1}"><fmt:message key="geobase.printPlace.oneActiveStart"/></c:if>
	        	<c:if test="${activeStartPlace == 0}"><fmt:message key="geobase.printPlace.zeroActiveStarts"/></c:if>
        	</td>
        </tr>
        <tr> 
        	<td width="100%">
	        	<c:if test="${deathPlace != 0}">${deathPlace} <fmt:message key="geobase.printPlace.deaths"/> </c:if>
	        	<c:if test="${deathPlace == 1}"><fmt:message key="geobase.printPlace.oneDeath"/> </c:if>
	        	<c:if test="${deathPlace == 0}"><fmt:message key="geobase.printPlace.zeroDeaths"/> </c:if>
	        	<c:if test="${activeEndPlace != 0}">${activeEndPlace} <fmt:message key="geobase.printPlace.activeEnds"/></c:if>
	        	<c:if test="${activeEndPlace == 1}"><fmt:message key="geobase.printPlace.oneActiveEnd"/></c:if>
	        	<c:if test="${activeEndPlace == 0}"><fmt:message key="geobase.printPlace.zeroActiveEnds"/></c:if>
        	</td>
        </tr>
    </table> 
    
    <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
    
    
    <h5><fmt:message key="geobase.printPlace.geographicCoordinates"/></h5>
    <c:if test="${place.prefFlag == 'P'}">
    <table>
        <tr> 
        	<td width="20%"><fmt:message key="geobase.printPlace.latitude"/></td>
            <td width="80%">${place.placeGeographicCoordinates.degreeLatitude}° ${place.placeGeographicCoordinates.minuteLatitude}' ${place.placeGeographicCoordinates.secondLatitude}'' ${place.placeGeographicCoordinates.directionLatitude}</td>
        </tr>
        <tr> 
        	<td width="20%"><fmt:message key="geobase.printPlace.longitude"/></td>
            <td width="80%">${place.placeGeographicCoordinates.degreeLatitude}° ${place.placeGeographicCoordinates.minuteLatitude}' ${place.placeGeographicCoordinates.secondLatitude}'' ${place.placeGeographicCoordinates.directionLatitude}</td>
        </tr>
    </table> 
	</c:if>
	<%-- Variant no geographic coordinates--%>
<%-- 	<c:forEach items="${placeNames}" var="currentName"> --%>
<%-- 		<c:if test="${currentName.prefFlag == 'P'}"> --%>
<%-- 			<p style="margin:0 0 5px 10px"><font color="red">This place is a Variant Name for '${currentName.placeName}'. Find the 'Principal' name to visualize ${currentName.placeName} geographic coordinates</font></p> --%>
<%-- 		</c:if> --%>
<%-- 	</c:forEach> --%>

	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
	
	<h5><fmt:message key="geobase.printPlace.externalLinks"/></h5>
	<table>
	<c:forEach items="${place.placeExternalLinks}" var="currentExternalLink">
		<tr>
			<td width="20%"><fmt:message key="geobase.printPlace.nameLink"/></td>
			<td width="80%">${currentExternalLink.description}</td>
		</tr>
	</c:forEach>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
	 
	<h5><fmt:message key="geobase.printPlace.hierarchy"/></h5>
	<table>
		<tr>
	 		<td width="20%"><fmt:message key="geobase.printPlace.parent"/></td>
	 		<td width="80%">${place.parentPlace.placeAllId}</td>
	 	</tr>
	 	<tr>
	 		<td width="20%"><fmt:message key="geobase.printPlace.gparent"/></td>
	 		<td width="80%">${place.gParent}</td>
	 	</tr>
	 	<tr>
	 		<td width="20%"><fmt:message key="geobase.printPlace.ggParent"/></td>
	 		<td width="80%">${place.ggp}</td>
	 	</tr>
	 	<tr>
	 		<td width="20%"><fmt:message key="geobase.printPlace.gptwo"/></td>
	 		<td width="80%">${place.gp2}</td>
	 	</tr>
	 	<tr>
	 		<td width="20%"><fmt:message key="geobase.printPlace.parentTGN"/></td>
	 		<td width="80%">${place.plParentTermId}</td>
	 	</tr>
	 	<tr>
	 		<td width="20%"><fmt:message key="geobase.printPlace.parentGeo"/></td>
	 		<td width="80%">${place.plParentSubjectId}</td>
	 	</tr>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>