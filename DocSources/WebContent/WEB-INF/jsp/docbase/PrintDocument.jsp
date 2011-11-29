<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<h4>Documentary Sources for the Arts and Humanities 1537 - 1743<br />Document Report</h4>

	<h5 class="first">Document Details</h5>
	<table>
	    <tr> 
	      <td width="25%">Vol. Number</td>
	      <td width="30%" class="value">${document.volume.MDP}</td>
	      <td width="25%">Folio No.</td>
	      <td width="30%" class="value">${document.folioNum} / ${document.folioMod}</td>
	
	    </tr>
	    <tr>
	      <td width="25%">Citation Text</td>
	      <td width="70%" colspan="3" class="value">Archivio di Stato di Firenze, Mediceo del Principato ${document.volume.MDP} folio ${document.folioNum} / ${document.folioMod}. (Entry ${document.entryId} in the Documentary Sources database)</td>
	      
	    </tr>
	</table> 
 
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>

	<h5>Correspondents/People</h5>
	
	<table>
	    <tr> 
	      <td width="15%">Sender</td>
	      <td width="40%" class="value">${document.senderPeople.mapNameLf}</td>
	      <td width="15%">From</td>
	      <td width="40%" class="value">${document.senderPlace.placeNameFull}</td>
	    </tr>
	
	    <tr> 
	      <td width="15%">Recipient</td>
	      <td width="40%" class="value">${document.recipientPeople.mapNameLf}</td>
	      <td width="15%">To</td>
	      <td width="40%" class="value">${document.recipientPlace.placeNameFull}</td>
	    </tr>
	    <tr> 
	      <td width="15%">Date</td>
	
	      <td width="40%" class="value">${document.docYear} ${document.docMonthNum} ${document.docDay}</td>
	    </tr>
	</table> 
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
	
	<h5>Extract/Synopsis</h5>
	<table>
	    <tr> 
			<td width="150" style="padding-right:10px">Document Extract</td>
			<td width="150">Document Synopsis</td>
		</tr>
		<tr> 
			<td width="150" class="value" style="padding-right:10px">${document.synExtract.docExtract}</td>
			<td width="150" class="value">${document.synExtract.synopsis}</td>
		</tr>
	</table>
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
	
	
	<h5>Topics</h5>
	<table>
		<c:forEach items="${document.eplToLink}" var="currentTopicAndPlace">
		<tr> 
			<td width="60">Topic</td>
			<td width="300" class="value">${currentTopicAndPlace.topic.topicTitle}</td>
	    </tr>
	    <tr> 
			<td width="60">Topic Place</td>
			<td width="300" class="value">${currentTopicAndPlace.place.placeNameFull}</td>
	    </tr>
		</c:forEach>
	</table> 
	
	<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
