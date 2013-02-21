<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<a href="javascript:window.print()" class="print" title="Print"></a>
	
	<style type="text/css">
		@media print{
			#newPage{
				page-break-after: always;
			}
		}
	</style>
	
	<c:forEach items="${elementsToPrint}" var="currentElement">
		<!-- Document print -->
		
	
			<div id="top">
				<div id="logoMap"><img src="<c:url value="/images/1024/img_map_print.jpg" />" alt="The Medici Archive Project" /></div>
			</div>
			
			<h4>Documentary Sources for the Arts and Humanities 1537 - 1743<br />Document Report</h4>
		
			<h5 class="first">Document Details</h5>
			<table>
			    <tr> 
			      <td width="25%">Vol. Number</td>
			      <td width="30%" class="value">${currentElement.volume.MDP}</td>
			      <td width="25%">Folio No.</td>
			      <td width="30%" class="value">${currentElement.folioNum} <c:if test="${not empty currentElement.folioMod}">/ ${currentElement.folioMod}</c:if></td>
		
			    </tr>
			    <tr>
			      <td width="25%">Citation Format</td>
			      <td width="70%" colspan="3" class="value">BIA: The Medici Archive Project, Doc ID# ${currentElement.entryId} (Archivio di Stato di Firenze, Mediceo del Principato ${currentElement.volume.MDP} 
			      <c:if test="${not empty currentElement.folioNum}">, folio ${currentElement.folioNum}</c:if>
			      <c:if test="${empty currentElement.folioNum}">, unnumbered folio</c:if>
			      <c:if test="${not empty currentElement.folioMod}">/ ${currentElement.folioMod}</c:if>)</td>
			   	</tr>
			</table> 
		 
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
		
			<h5>Correspondents/People</h5>
			
			<table>
			    <tr> 
			      <td width="15%">Sender</td>
			      <td width="40%" class="value">${currentElement.senderPeople.mapNameLf}</td>
			      <td width="15%">From</td>
			      <td width="40%" class="value">${currentElement.senderPlace.placeNameFull}</td>
			    </tr>
			
			    <tr> 
			      <td width="15%">Recipient</td>
			      <td width="40%" class="value">${currentElement.recipientPeople.mapNameLf}</td>
			      <td width="15%">To</td>
			      <td width="40%" class="value">${currentElement.recipientPlace.placeNameFull}</td>
			    </tr>
			    <tr> 
			      <td width="15%">Date</td>
			
			      <td width="40%" class="value">${currentElement.docYear} ${currentElement.docMonthNum} ${currentElement.docDay}</td>
			    </tr>
			</table> 
			
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
			
			<%-- <!-- su due colonne -->
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
			
			--%>
			
			<h5>Extract/Synopsis</h5>
			<table>
		        <tr> 
		        	<td width="100%">Document Extract</td>
		        </tr>
		        <tr>
		        	<td width="100%" class="value">${currentElement.synExtract.docExtract}</td>
		        </tr>
		        <tr>
		        	<td width="100%"></td>
		        </tr>
		        <tr>
					<td width="100%">Document Synopsis</td>
		        </tr>
		        <tr> 
		        	<td width="100%" class="value">${currentElement.synExtract.synopsis}</td>
		        </tr>
			</table>
				
			
			<img src="<c:url value="/images/1024/img_hr_print.png"/>" style="margin:10px 0 10px 85px"/>
			
			
			<h5>Topics</h5>
			<table>
				<c:forEach items="${currentElement.eplToLink}" var="currentTopicAndPlace">
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
<!-- 			<div id="footer"> -->
<!-- 				<p> -->
<!-- 					The Medici Archive Project, Inc. &copy; -->
<!-- 					<br />  -->
<!-- 					Written content on the Medici Archive Project website and the BIA platform are sole property of the Medici Archive Project, Inc.,a 501(c)(3) Tax Exempt Organization. In any subsequent -->
<!-- 					use the Medici Archive Project must be given appropriate acknowledgment in accordance with U.S. and foreign copyright laws.  -->
<!-- 				</p> -->
<!-- 				<p> To cite content from the BIA platform in a publication, use the "citation format" near the top  of the page.</p> -->
<!-- 			</div> -->
<!-- 			<div id="newPage"></div> -->
		
		
		

	</c:forEach>
	
	 <div id="footer">
		<p>
			The Medici Archive Project, Inc. &copy;
			<br /> 
			Written content on the Medici Archive Project website and the BIA platform are sole property of the Medici Archive Project, Inc.,a 501(c)(3) Tax Exempt Organization. In any subsequent
			use the Medici Archive Project must be given appropriate acknowledgment in accordance with U.S. and foreign copyright laws. 
		</p>
		<p> To cite content from the BIA platform in a publication, use the "citation format" near the top  of the page.</p>
	</div>
