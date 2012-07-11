<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a href="javascript:window.print()" class="print" title="Print"></a>
	
	<div id="top">
		<div id="logoMap"><img src="<c:url value="/images/1024/img_map_print.jpg" />" alt="The Medici Archive Project" /></div>
	</div>
	
	<h4>Documentary Sources for the Arts and Humanities 1537 - 1743<br />Place Report</h4>
            
    <h5 class="first">Place Details</h5>
    <table>
        <tr> 
            <td width="20%">Place ID</td>
            <td width="20%" class="value">${place.placeAllId}</td>
            <td width="20%">Place name</td>
            <td width="40%" class="value">${place.placeName}</td>
       </tr>
       <tr>
            <td width="20%">(with accents)</td>
            <td width="80%" class="value" colspan="3">${place.placeNameFull}</td>
        </tr>
        <tr>
        	<td width="20%">Place type</td>
            <td width="80%" class="value" colspan="3">${place.plType}</td>
        </tr>
        <tr>
            <td width="20%">Place Parent</td>
            <td width="80%" colspan="3" class="value">${place.plParent}</td>
        </tr>
        <tr>
            <td width="20%">Place Notes</td>
            <td width="80%" colspan="5" class="value">${place.placesMemo}</td>
        </tr>
    </table> 
    
    <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
    
    <h5>Name or Name Variants</h5>
    <table>
      <table>
   		 <c:forEach items="${placeNames}" var="currentName">
   		 	<c:if test="${currentName.prefFlag == 'P'}">
				<tr>
					<td width="20%">Principal</td>
					<td width="80%" class="value">${currentName.placeName}</td>
				</tr>
			</c:if>
			<c:if test="${currentName.prefFlag == 'V'}">
				<tr>
					<td width="20%">Variant</td>
					<td width="80%" class="value">${currentName.placeName}</td>
				</tr>
			</c:if>							
		</c:forEach> 		 
    </table>
    <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
    
    <h5>Senders and Recipients</h5>
    <table>
        <tr>
        	<c:if test="${senderPlace != null && senderPlace != 0 && senderPlace != 1}">
            	<td width="100%">${senderPlace} Senders</td>
            </c:if>
            <c:if test="${senderPlace == 1 }">
            	<td width="100%">${senderPlace} Sender</td>
            </c:if>
            <c:if test="${senderPlace == 0 || senderPlace == null}">
				<td width="100%">0 Senders</td>
			</c:if>
            
        </tr>
        <tr>
        	<c:if test="${recipientPlace != null && recipientPlace != 0 && recipientPlace != 1}">
				<td width="100%">${recipientPlace} Recipients</td>
			</c:if>
			<c:if test="${recipientPlace == 1}">
				<td width="100%">${recipientPlace} Recipient</td>
			</c:if>
			<c:if test="${recipientPlace == 0 || recipientPlace == null}">
				<td width="100%">0 Recipients</td>
			</c:if>
        </tr>
    </table>
    
   <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
    
    <h5>Topics List</h5>
    <table>
        <tr>
	        <c:if test="${topicsPlace != null && topicsPlace != 0 && topicsPlace != 1 && docInTopics != 1}"> 
	        	<td width="100%">${docInTopics} Documents on ${topicsPlace} Topics</td>
	        </c:if>
			<c:if test="${topicsPlace == 1}">
	    		<td width="100%">${docInTopics} Document on ${topicsPlace} Topic</td>
			</c:if>
			<c:if test="${docInTopics == 1 && topicsPlace != 1}">
	    		<td width="100%">${docInTopics} Document on ${topicsPlace} Topics</td>
			</c:if>
			<c:if test="${topicsPlace == 0 || topicsPlace == null}">
	    		<td width="100%">0 Documents on 0 Topics</td>
			</c:if>
        </tr>
    </table> 
    
    <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
    
    <h5>Birth and Death Place</h5>
    <table>
        <tr>
        	<td width="100%">
	        	<c:if test="${birthPlace != 0}">${birthPlace} Births </c:if>
	        	<c:if test="${birthPlace == 1}">1 Birth </c:if>
	        	<c:if test="${birthPlace == 0}">0 Births </c:if>
	        	<c:if test="${activeStartPlace != 0}">${activeStartPlace} Active Starts</c:if>
	        	<c:if test="${activeStartPlace == 1}">1 Active Start</c:if>
	        	<c:if test="${activeStartPlace == 0}">0 Active Starts</c:if>
        	</td>
        </tr>
        <tr> 
        	<td width="100%">
	        	<c:if test="${deathPlace != 0}">${deathPlace} Deaths </c:if>
	        	<c:if test="${deathPlace == 1}">1 Death </c:if>
	        	<c:if test="${deathPlace == 0}">0 Deaths </c:if>
	        	<c:if test="${activeEndPlace != 0}">${activeEndPlace} Active Ends</c:if>
	        	<c:if test="${activeEndPlace == 1}">1 Active End</c:if>
	        	<c:if test="${activeEndPlace == 0}">0 Active Ends</c:if>
        	</td>
        </tr>
    </table> 
    
    <img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
    
    
    <h5>Geographic Coordinates</h5>
    <c:if test="${place.prefFlag == 'P'}">
    <table>
        <tr> 
        	<td width="20%">Latitude</td>
            <td width="80%">${place.placeGeographicCoordinates.degreeLatitude}° ${place.placeGeographicCoordinates.minuteLatitude}' ${place.placeGeographicCoordinates.secondLatitude}'' ${place.placeGeographicCoordinates.directionLatitude}</td>
        </tr>
        <tr> 
        	<td width="20%">Longitude</td>
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
	
	<h5>External Links</h5>
	<table>
	<c:forEach items="${place.placeExternalLinks}" var="currentExternalLink">
		<tr>
			<td width="20%">Name Link</td>
			<td width="80%">${currentExternalLink.description}</td>
		</tr>
	</c:forEach>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
	 
	<h5>Hierarchy</h5>
	<table>
		<tr>
	 		<td width="20%">Parent</td>
	 		<td width="80%">${place.parentPlace.placeAllId}</td>
	 	</tr>
	 	<tr>
	 		<td width="20%">GParent</td>
	 		<td width="80%">${place.gParent}</td>
	 	</tr>
	 	<tr>
	 		<td width="20%">GGParent</td>
	 		<td width="80%">${place.ggp}</td>
	 	</tr>
	 	<tr>
	 		<td width="20%">GP2</td>
	 		<td width="80%">${place.gp2}</td>
	 	</tr>
	 	<tr>
	 		<td width="20%">Parent_TGN_id</td>
	 		<td width="80%">${place.plParentTermId}</td>
	 	</tr>
	 	<tr>
	 		<td width="20%">Parent_GEOKEY</td>
	 		<td width="80%">${place.plParentSubjectId}</td>
	 	</tr>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>